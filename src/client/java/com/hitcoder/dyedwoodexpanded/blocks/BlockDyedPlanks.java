package com.hitcoder.dyedwoodexpanded.blocks;

import net.minecraft.src.game.block.Block;
import net.minecraft.src.game.block.Material;
import net.minecraft.src.game.block.texture.Face;
import net.minecraft.src.game.item.ItemBlockPlanks;

public class BlockDyedPlanks extends Block {
    public BlockDyedPlanks(int id) {
        super(id, Material.wood);
    }
    // why the HECK does this BREAK it im gonna cry
//    @Override
//    protected void allocateTextures() {
//        System.out.println(this.getBlockName());
//        this.addTexture(this.getBlockName().replace("_dyed_planks","_planks"), Face.ALL, 0);
//    }
}
