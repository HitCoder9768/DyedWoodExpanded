package com.hitcoder.dyedwoodexpanded.blocks;

import java.util.Random;

import com.fox2code.foxloader.registry.RegisteredBlock;
import net.minecraft.src.client.physics.AxisAlignedBB;
import net.minecraft.src.client.physics.MovingObjectPosition;
import net.minecraft.src.client.renderer.Vec3D;
import net.minecraft.src.game.block.Block;
import net.minecraft.src.game.block.Material;
import net.minecraft.src.game.block.texture.Face;
import net.minecraft.src.game.entity.player.EntityPlayer;
import net.minecraft.src.game.item.Item;
import net.minecraft.src.game.level.IBlockAccess;
import net.minecraft.src.game.level.World;

import static com.hitcoder.dyedwoodexpanded.DyedWoodExpandedClient.*;

public class BlockDyedDoor extends Block implements RegisteredBlock {
    public BlockDyedDoor(int id){
        super(id, Material.wood);
    }

    @Override
    public void allocateTextures() {
        String s = this.getBlockName().replace("tile.", "");
        String l = s + "_lower";
        String u = s + "_upper";

        for(int i = 0; i < 8; ++i) {
            this.addTexture(l, Face.BOTTOM, i);
            this.addTexture(l, Face.TOP, i + 8);
            this.addTexture(l, Face.EAST, i);
            this.addTexture(l, Face.WEST, i);
            this.addTexture(l, Face.SOUTH, i);
            this.addTexture(l, Face.NORTH, i);
            this.addTexture(u, Face.EAST, i + 8);
            this.addTexture(u, Face.WEST, i + 8);
            this.addTexture(u, Face.SOUTH, i + 8);
            this.addTexture(u, Face.NORTH, i + 8);
        }

        for(int i = 0; i < 8; i += 4) {
            this.addTexture(l, Face.EAST, 0 + i, true, false);
            this.addTexture(l, Face.SOUTH, 0 + i, true, false);
            this.addTexture(l, Face.SOUTH, 1 + i, true, false);
            this.addTexture(l, Face.WEST, 1 + i, true, false);
            this.addTexture(l, Face.WEST, 2 + i, true, false);
            this.addTexture(l, Face.NORTH, 2 + i, true, false);
            this.addTexture(l, Face.NORTH, 3 + i, true, false);
            this.addTexture(l, Face.EAST, 3 + i, true, false);
            this.addTexture(u, Face.EAST, 8 + i, true, false);
            this.addTexture(u, Face.SOUTH, 8 + i, true, false);
            this.addTexture(u, Face.SOUTH, 9 + i, true, false);
            this.addTexture(u, Face.WEST, 9 + i, true, false);
            this.addTexture(u, Face.WEST, 10 + i, true, false);
            this.addTexture(u, Face.NORTH, 10 + i, true, false);
            this.addTexture(u, Face.NORTH, 11 + i, true, false);
            this.addTexture(u, Face.EAST, 11 + i, true, false);
        }
    }

    private void setDoorRotation(int metadata) {
        float a = 0.1875F;
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F);
        if (metadata == 0) {
            this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, a);
        }

        if (metadata == 1) {
            this.setBlockBounds(1.0F - a, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        }

        if (metadata == 2) {
            this.setBlockBounds(0.0F, 0.0F, 1.0F - a, 1.0F, 1.0F, 1.0F);
        }

        if (metadata == 3) {
            this.setBlockBounds(0.0F, 0.0F, 0.0F, a, 1.0F, 1.0F);
        }
    }

    @Override
    public boolean blockActivated(World world, int x, int y, int z, EntityPlayer player) {
        int metadata = world.getBlockMetadata(x, y, z);
        if ((metadata & 8) != 0) {
            if (world.getBlockId(x, y - 1, z) == this.blockID) {
                this.blockActivated(world, x, y - 1, z, player);
            }
        } else {
            if (world.getBlockId(x, y + 1, z) == this.blockID) {
                world.setBlockMetadataWithNotify(x, y + 1, z, (metadata ^ 4) + 8);
            }

            world.setBlockMetadataWithNotify(x, y, z, metadata ^ 4);
            world.markBlocksDirty(x, y - 1, z, x, y, z);
            world.playSoundForClient(player, 1003, (double)x, (double)y, (double)z, 0);
        }

        return true;
    }

    private void onPoweredBlockChange(World world, int x, int y, int z, boolean powered) {
        int metadata = world.getBlockMetadata(x, y, z);
        if ((metadata & 8) != 0) {
            if (world.getBlockId(x, y - 1, z) == this.blockID) {
                this.onPoweredBlockChange(world, x, y - 1, z, powered);
            }
        } else {
            boolean state = (world.getBlockMetadata(x, y, z) & 4) > 0;
            if (state != powered) {
                if (world.getBlockId(x, y + 1, z) == this.blockID) {
                    world.setBlockMetadataWithNotify(x, y + 1, z, (metadata ^ 4) + 8);
                }

                world.setBlockMetadataWithNotify(x, y, z, metadata ^ 4);
                world.markBlocksDirty(x, y - 1, z, x, y, z);
                world.playSoundForClient((EntityPlayer)null, 1003, (double)x, (double)y, (double)z, 0);
            }
        }
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, int id) {
        int metadata = world.getBlockMetadata(x, y, z);
        if ((metadata & 8) != 0) {
            if (world.getBlockId(x, y - 1, z) != this.blockID) {
                world.setBlockWithNotify(x, y, z, 0);
            }

            if (id > 0 && Block.blocksList[id].canProvidePower()) {
                this.onNeighborBlockChange(world, x, y - 1, z, id);
            }
        } else {
            boolean failure = false;
            if (world.getBlockId(x, y + 1, z) != this.blockID) {
                world.setBlockWithNotify(x, y, z, 0);
                failure = true;
            }

            if (!world.doesBlockHaveTop(x, y - 1, z)) {
                world.setBlockWithNotify(x, y, z, 0);
                failure = true;
                if (world.getBlockId(x, y + 1, z) == this.blockID) {
                    world.setBlockWithNotify(x, y + 1, z, 0);
                }
            }

            if (failure) {
                if (!world.multiplayerWorld) {
                    this.dropBlockAsItem(world, x, y, z, metadata);
                }
            } else if (id > 0 && Block.blocksList[id].canProvidePower()) {
                boolean powered = world.isBlockIndirectlyGettingPowered(x, y, z)
                        || world.isBlockIndirectlyGettingPowered(x, y + 1, z);
                this.onPoweredBlockChange(world, x, y, z, powered);
            }
        }
    }

    @Override
    public int idDropped(int metadata, Random random) {
        if ((metadata & 8) != 0) {
            return 0;
        }
        int i = 0;
        for (String c : palette){
            System.out.println(this.getBlockName() + "/" + c);
            if (this.getBlockName().replace("tile.","").replace("_door","").equals(c)){
                System.out.println(registeredDoorItems[i].getRegisteredItemId());
                return registeredDoorItems[i].getRegisteredItemId();
            }
            i++;
        }

        return 0;
    }

    @Override
    public int idPicked(World world, int x, int y, int z) {
        int i = 0;
        for (String c : palette){
            System.out.println(this.getBlockName() + "/" + c);
            if (this.getBlockName().replace("tile.","").replace("_door","").equals(c)){
                return registeredDoorItems[i].getRegisteredItemId();
            }
            i++;
        }

        return 0;
    }

    @Override
    public boolean canPlaceBlockAt(World world, int x, int y, int z) {
        if (y >= world.highestY - 1) {
            return false;
        } else {
            return world.doesBlockHaveTop(x, y - 1, z)
                    && super.canPlaceBlockAt(world, x, y, z)
                    && super.canPlaceBlockAt(world, x, y + 1, z);
        }
    }

    @Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z) {
        this.setBlockBoundsBasedOnState(world, x, y, z);
        return super.getSelectedBoundingBoxFromPool(world, x, y, z);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        this.setBlockBoundsBasedOnState(world, x, y, z);
        return super.getCollisionBoundingBoxFromPool(world, x, y, z);
    }

    @Override
    public MovingObjectPosition collisionRayTrace(World world, int x, int y, int z, Vec3D vec3D, Vec3D arg6) {
        this.setBlockBoundsBasedOnState(world, x, y, z);
        return super.collisionRayTrace(world, x, y, z, vec3D, arg6);
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess blockAccess, int x, int y, int z) {
        this.setDoorRotation(this.getState(blockAccess.getBlockMetadata(x, y, z)));
    }

    @Override
    public int getMobilityFlag() {
        return 1;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    public static boolean isOpen(int metadata) {
        return (metadata & 4) != 0;
    }

    private int getState(int metadata) {
        return (metadata & 4) == 0 ? metadata - 1 & 3 : metadata & 3;
    }
}
