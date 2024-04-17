package com.calemi.chambers.registry;

import com.calemi.chambers.main.ChambersMain;
import com.calemi.chambers.main.ChambersRef;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class BlockRegistry {

    private static Block regBlockSolo(String name, Block block) {
        return Registry.register(Registries.BLOCK, ChambersRef.id(name), block);
    }

    private static Block regBlock(String name, Block block) {
        Registry.register(Registries.ITEM, ChambersRef.id(name), new BlockItem(block, new FabricItemSettings()));
        return Registry.register(Registries.BLOCK, ChambersRef.id(name), block);
    }

    public static void init() {
        ChambersMain.LOGGER.info("Registering Blocks...");
    }
}
