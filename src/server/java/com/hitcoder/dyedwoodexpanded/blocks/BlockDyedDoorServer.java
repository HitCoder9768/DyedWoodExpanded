package com.hitcoder.dyedwoodexpanded.blocks;

import net.minecraft.src.game.block.BlockDoor;
import net.minecraft.src.game.block.Material;
import net.minecraft.src.game.level.World;

import java.util.Random;

import static com.hitcoder.dyedwoodexpanded.DyedWoodExpanded.palette;
import static com.hitcoder.dyedwoodexpanded.DyedWoodExpandedServer.registeredDoorItems;

public class BlockDyedDoorServer extends BlockDoor {
    public BlockDyedDoorServer(int id) {
        super(id, Material.wood);
    }

    @Override
    public int idDropped(int metadata, Random random) {
        if ((metadata & 8) != 0) {
            return 0;
        }
        int i = 0;
        for (String c : palette){
            System.out.println(this.getBlockName() + "/" + c + "/" + registeredDoorItems[i].getRegisteredItemId());
            if (this.getBlockName().replace("tile.","").replace("_door","").equals(c)){
                return registeredDoorItems[i].getRegisteredItemId();
            }
            i++;
        }

        return 0;
    }
}
