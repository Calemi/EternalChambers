package com.calemi.chambers.registry;

import com.calemi.chambers.item.ChamberTeleporterItem;
import com.calemi.chambers.main.ChambersMain;
import com.calemi.chambers.main.ChambersRef;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ItemRegistry {

    public static Item CHAMBER_TELEPORTER = regItem("chamber_teleporter", new ChamberTeleporterItem());

    private static Item regItem(String name, Item item) {
        return Registry.register(Registries.ITEM, ChambersRef.id(name), item);
    }

    public static void init() {
        ChambersMain.LOGGER.info("Registering Items...");
    }
}