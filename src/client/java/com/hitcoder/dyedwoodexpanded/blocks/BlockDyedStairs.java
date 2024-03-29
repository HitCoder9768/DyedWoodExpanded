package com.hitcoder.dyedwoodexpanded.blocks;

import net.minecraft.src.game.block.Block;
import net.minecraft.src.game.block.Material;
import net.minecraft.src.game.block.texture.Face;
import net.minecraft.src.game.item.ItemBlockPlanks;

public class BlockDyedStairs extends Block {
    protected BlockDyedStairs(int id) {
        super(id, Material.wood);
    }
    @Override
    protected void allocateTextures() {
        for(int i = 0; i < 3; ++i) {
            this.addTexture(ItemBlockPlanks.plankType[i] + "_planks", Face.ALL, i);
        }
    }
}
