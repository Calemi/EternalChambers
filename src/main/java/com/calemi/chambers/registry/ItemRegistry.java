package com.calemi.chambers.registry;

import com.calemi.chambers.item.ChamberTeleporterItem;
import com.calemi.chambers.main.ChambersMain;
import com.calemi.chambers.main.ChambersRef;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.FoodComponents;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ItemRegistry {

    public static Item CHAMBER_TELEPORTER = regItem("chamber_teleporter", new ChamberTeleporterItem());

    //FoodItems
    public static Item MEAT1 = regItem("meat1", new Item(new FabricItemSettings().food(new FoodComponent.Builder().hunger(1).build())));
    public static Item MEAT2 = regItem("meat2", new Item(new FabricItemSettings().food(new FoodComponent.Builder().hunger(1).build())));
    public static Item MEAT3 = regItem("meat3", new Item(new FabricItemSettings().food(new FoodComponent.Builder().hunger(1).build())));
    public static Item MEAT4 = regItem("meat4", new Item(new FabricItemSettings().food(new FoodComponent.Builder().hunger(1).build())));
    public static Item MEAT5 = regItem("meat5", new Item(new FabricItemSettings().food(new FoodComponent.Builder().hunger(1).build())));
    public static Item MEAT6 = regItem("meat6", new Item(new FabricItemSettings().food(new FoodComponent.Builder().hunger(1).build())));
    public static Item MEAT7 = regItem("meat7", new Item(new FabricItemSettings().food(new FoodComponent.Builder().hunger(1).build())));
    public static Item MEAT8 = regItem("meat8", new Item(new FabricItemSettings().food(new FoodComponent.Builder().hunger(1).build())));
    public static Item MEAT9 = regItem("meat9", new Item(new FabricItemSettings().food(new FoodComponent.Builder().hunger(1).build())));
    public static Item MEAT10 = regItem("meat10", new Item(new FabricItemSettings().food(new FoodComponent.Builder().hunger(1).build())));
    public static Item MEAT11 = regItem("meat11", new Item(new FabricItemSettings().food(new FoodComponent.Builder().hunger(1).build())));

    private static Item regItem(String name, Item item) {
        return Registry.register(Registries.ITEM, ChambersRef.id(name), item);
    }

    public static void init() {
        ChambersMain.LOGGER.info("Registering Items...");
    }
}