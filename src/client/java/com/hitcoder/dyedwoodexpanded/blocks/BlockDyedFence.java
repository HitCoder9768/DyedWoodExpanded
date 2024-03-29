package com.hitcoder.dyedwoodexpanded.blocks;

import com.fox2code.foxloader.registry.RegisteredBlock;
import net.minecraft.src.client.renderer.block.icon.Icon;
import net.minecraft.src.game.block.BlockFence;
import net.minecraft.src.game.block.Material;
import net.minecraft.src.game.block.texture.Face;
import net.minecraft.src.game.block.Block;
import net.minecraft.src.game.item.ItemBlockPlanks;
import net.minecraft.src.game.level.IBlockAccess;

import static com.hitcoder.dyedwoodexpanded.DyedWoodExpandedClient.palette;
import static com.hitcoder.dyedwoodexpanded.DyedWoodExpandedClient.registeredFenceBlocks;

public class BlockDyedFence extends BlockFence {
    public BlockDyedFence(int id) {
        super(id);
    }
    
    @Override
    protected void allocateTextures(){
        this.addTexture(this.getBlockName().replace("tile.", "").replace("_fence", "") + "_planks", Face.ALL, 0);
    }

    @Override
    public Icon getIcon(int blockFace, int metadata) {
        int m = 0;
        for (int i = 0; i < palette.length; i++){
            if (palette[i].equals(this.getBlockName().replace("tile.", "").replace("_fence", ""))){
                m = i;
            }
        }
        return Block.coloredPlanks.getIcon(0,m);
    }

    @Override
    public boolean isFenceAt(IBlockAccess blockAccess, int x, int y, int z, boolean wrenched){
        int id = blockAccess.getBlockId(x, y, z);
        if (id == Block.fence.blockID || id == Block.fenceRock.blockID){
            return true;
        }
        for (RegisteredBlock f : registeredFenceBlocks){
            if (id == f.getRegisteredBlockId()){
                return true;
            }
        }
        Block block = Block.blocksList[id];
        if (block != null && block.isFenceCompatible) {
            return true;
        } else if (block != null && block.blockMaterial.getIsOpaque() && block.renderAsNormalBlock() && wrenched) {
            return block.blockMaterial != Material.pumpkin;
        } else {
            return false;
        }
    }
}
