package com.hitcoder.dyedwoodexpanded.items;

import com.fox2code.foxloader.registry.RegisteredItem;
import net.minecraft.src.game.MathHelper;
import net.minecraft.src.game.block.Block;
import net.minecraft.src.game.block.Material;
import net.minecraft.src.game.entity.player.EntityPlayer;
import net.minecraft.src.game.item.ItemDoor;
import net.minecraft.src.game.item.ItemStack;
import net.minecraft.src.game.level.World;

import static com.hitcoder.dyedwoodexpanded.DyedWoodExpandedClient.*;

public class ItemDyedDoor extends ItemDoor implements RegisteredItem {
    private Material doorMaterial;

    public ItemDyedDoor(int id) {
        super(id-256, Material.wood, 0);
    }

    @Override
    public boolean onItemUse(
            ItemStack itemstack,
            EntityPlayer player,
            World world,
            int x,
            int y,
            int z,
            int facing,
            float xVec,
            float yVec,
            float zVec
    ) {
        if (facing != 1) {
            return false;
        } else {
            ++y;
            Block block;
            int i = 0;
            for (String item : palette){
                if (this.getItemName().replace("item.","").replace("_door","").equals(item)){
                    break;
                }
                i++;
            }
            block = (Block) registeredDoorBlocks[i];

            if (!block.canPlaceBlockAt(world, x, y, z)) {
                return false;
            } else {
                int rot = MathHelper.floor_double((double)((player.rotationYaw + 180.0F) * 4.0F / 360.0F) - 0.5) & 3;
                placeDoorBlock(world, x, y, z, rot, block);
                world.playSoundEffect(
                        (double)x + 0.5,
                        (double)y + 0.5,
                        (double)z + 0.5,
                        block.stepSound.blockSound(),
                        (block.stepSound.getVolume() + 1.0F) / 2.0F,
                        block.stepSound.getPitch() * 0.8F
                );
                --itemstack.stackSize;
                return true;
            }
        }
    }
}
