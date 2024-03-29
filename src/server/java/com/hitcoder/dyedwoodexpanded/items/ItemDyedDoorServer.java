package com.hitcoder.dyedwoodexpanded.items;

import com.fox2code.foxloader.registry.RegisteredItem;
import net.minecraft.src.game.MathHelper;
import net.minecraft.src.game.block.Block;
import net.minecraft.src.game.block.Material;
import net.minecraft.src.game.entity.player.EntityPlayer;
import net.minecraft.src.game.item.ItemDoor;
import net.minecraft.src.game.item.ItemStack;
import net.minecraft.src.game.level.World;

import static com.hitcoder.dyedwoodexpanded.DyedWoodExpanded.palette;
import static com.hitcoder.dyedwoodexpanded.DyedWoodExpandedServer.registeredDoorBlocks;
import static com.hitcoder.dyedwoodexpanded.DyedWoodExpandedServer.registeredDoorItems;

public class ItemDyedDoorServer extends ItemDoor implements RegisteredItem {
    public ItemDyedDoorServer(int id) {
        super(id-256, Material.wood, 0);
        this.maxStackSize = 64;
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
            }

            int rot = MathHelper.floor_double((double)((player.rotationYaw + 180.0F) * 4.0F / 360.0F) - 0.5) & 3;
            placeDoorBlock(world, x, y, z, rot, block);
            world.playSoundForClient(player, 902, (double)x + 0.5, (double)y + 0.5, (double)z + 0.5, block.blockID);
            --itemstack.stackSize;
            return true;
        }
    }
}
