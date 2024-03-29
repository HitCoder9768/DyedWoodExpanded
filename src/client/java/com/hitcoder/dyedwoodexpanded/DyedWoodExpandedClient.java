package com.hitcoder.dyedwoodexpanded;

import com.fox2code.foxloader.loader.ClientMod;
import com.fox2code.foxloader.registry.*;
import com.hitcoder.dyedwoodexpanded.blocks.BlockDyedDoor;
import com.hitcoder.dyedwoodexpanded.blocks.BlockDyedFence;
import com.hitcoder.dyedwoodexpanded.blocks.BlockDyedPlanks;
import com.hitcoder.dyedwoodexpanded.items.ItemDyedDoor;

public class DyedWoodExpandedClient extends DyedWoodExpanded implements ClientMod {

    public static RegisteredBlock[] registeredDoorBlocks = new RegisteredBlock[16];
    public static RegisteredItem[] registeredDoorItems = new RegisteredItem[16];

    public static RegisteredBlock[] registeredStairBlocks = new RegisteredBlock[16];
    public static RegisteredBlock[] registeredFenceBlocks = new RegisteredBlock[16];

    public static RegisteredBlock[] registeredDyedPlanksBlocks = new RegisteredBlock[16];

    private void registerBlocks(){
        for (int i = 0; i < palette.length; i++){
            registeredDyedPlanksBlocks[i] = registerNewBlock(palette[i]+"_dyed_planks", new BlockBuilder()
                    .setBlockMaterial(GameRegistry.BuiltInMaterial.WOOD)
                    .setBlockStepSounds(GameRegistry.BuiltInStepSounds.WOOD)
                    .setBlockName(palette[i]+"_planks")
                    .setBurnRate(20,50).setBurnTime(600)
                    .setGameBlockSource(BlockDyedPlanks.class)
                    .setEffectiveTool(RegisteredToolType.AXE)
                    .setBlockHardness(2.0F)
                    .setBlockResistance(5.0F));

            registeredDoorBlocks[i] = registerNewBlock(palette[i]+"_door_block",
                    new BlockBuilder().setBlockMaterial(GameRegistry.BuiltInMaterial.WOOD)
                            .setBlockStepSounds(GameRegistry.BuiltInStepSounds.WOOD)
                            .setBlockName(palette[i]+"_door")
                            .setBurnRate(20,50).setBurnTime(600)
                            .setGameBlockSource(BlockDyedDoor.class)
                            .setEffectiveTool(RegisteredToolType.AXE)
                            .setBlockHardness(3.0F)
                            .setBlockResistance(5.0F));

            System.out.println("registered "+palette[i]+"_door_block"+" as ID "+registeredDoorBlocks[i].getRegisteredBlockId());

            registeredStairBlocks[i] = registerNewBlock(palette[i]+"_stairs", new BlockBuilder()
                    .setBlockMaterial(GameRegistry.BuiltInMaterial.WOOD)
                    .setBlockStepSounds(GameRegistry.BuiltInStepSounds.WOOD)
                    .setBlockName(palette[i] + "_stairs").setBurnRate(20,50).setBurnTime(600)
                    .setBlockType(GameRegistry.BuiltInBlockType.STAIRS)
                    .setBlockSource(registeredDyedPlanksBlocks[i])
                    .setEffectiveTool(RegisteredToolType.AXE)
                    .setBlockHardness(2.0F)
                    .setBlockResistance(5.0F));

            registeredFenceBlocks[i] = registerNewBlock(palette[i]+"_fence", new BlockBuilder()
                    .setBlockMaterial(GameRegistry.BuiltInMaterial.WOOD)
                    .setBlockStepSounds(GameRegistry.BuiltInStepSounds.WOOD)
                    .setBlockName(palette[i] + "_fence").setBurnRate(20,50).setBurnTime(600)
                    .setGameBlockSource(BlockDyedFence.class)
                    .setEffectiveTool(RegisteredToolType.AXE)
                    .setBlockHardness(2.0F)
                    .setBlockResistance(5.0F));
        }
    }

    private void registerItems(){
        for (int i = 0; i < palette.length; i++){
            registeredDoorItems[i] = registerNewItem(palette[i]+"_door",
                    new ItemBuilder().setItemName(palette[i]+"_door")
                            .setGameItemSource(ItemDyedDoor.class));
            System.out.println("registered "+palette[i]+"_door"+" as ID "+registeredDoorItems[i].getRegisteredItemId());
        }
    }

    private void registerRecipes(){
        RegisteredItem colouredPlanks = GameRegistry.getInstance().getRegisteredItem(214);
        RegisteredItemStack colouredPlanksItem = colouredPlanks.newRegisteredItemStack();

        for (int i = 0; i < palette.length; i++) {

            colouredPlanksItem.setRegisteredDamage(i);

            RegisteredItemStack d = registeredDoorItems[i].newRegisteredItemStack();
            RegisteredItemStack s = registeredStairBlocks[i].newRegisteredItemStack();
            RegisteredItemStack f = registeredFenceBlocks[i].newRegisteredItemStack();
            d.setRegisteredStackSize(2);
            s.setRegisteredStackSize(6);
            f.setRegisteredStackSize(4);

            registerRecipe(d, new Object[]{
                            "PP",
                            "PP",
                            "PP",
                            Character.valueOf('P'),
                            colouredPlanksItem
                    });

            registerRecipe(s, new Object[]{
                            "P  ",
                            "PP ",
                            "PPP",
                            Character.valueOf('P'),
                            colouredPlanksItem
                    });

            registerRecipe(f, new Object[]{
                            "PSP",
                            "PSP",
                            Character.valueOf('P'),
                            colouredPlanksItem,
                            Character.valueOf('S'),
                            ItemDyedDoor.stick
                    });
        }
    }
    public void onInit(){
        System.out.println("Init Client for Dyed Wood Expanded");

        registerBlocks();
        registerItems();
        registerRecipes();
    }
}
