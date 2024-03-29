package com.hitcoder.dyedwoodexpanded;

import com.fox2code.foxloader.loader.Mod;
import com.fox2code.foxloader.registry.*;

public class DyedWoodExpanded extends Mod {

    public static RegisteredBlock testStairs, testFence;
    public static RegisteredBlock testDoor;

    public static final String[] palette = {
            "white", "light_gray", "gray", "black", "brown",
            "pink", "purple", "indigo", "blue", "cyan", "light_blue",
            "green", "lime", "yellow", "orange", "red"
    };

    @Override
    public void onPreInit(){
        System.out.println("Init DyedWoodExpanded........");
    }
}
