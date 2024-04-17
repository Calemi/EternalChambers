package com.calemi.chambers.main;

import net.minecraft.util.Identifier;

public class ChambersRef {

    public static final String MOD_NAME = "Eternal Chambers";
    public static final String MOD_ID = "chambers";

    public static final String FTB_TEAMS_ID = "ftbteams";
    public static final String TRINKETS_ID = "trinkets";

    public static final Identifier GUI_TEXTURES = new Identifier(MOD_ID, "textures/gui/gui_textures.png");

    public static Identifier id(String key) {
        return new Identifier(MOD_ID, key);
    }
}
