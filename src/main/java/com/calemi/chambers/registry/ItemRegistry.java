package com.calemi.chambers.registry;

import com.calemi.chambers.main.ChambersMain;
import com.calemi.chambers.main.ChambersRef;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ItemRegistry {

    //FoodItems
    public static Item MEAT_1 = regItem("meat_1", new Item(new FabricItemSettings().food(FoodComponentRegistry.MEAT_1)));
    public static Item MEAT_2 = regItem("meat_2", new Item(new FabricItemSettings().food(FoodComponentRegistry.MEAT_1)));
    public static Item MEAT_3 = regItem("meat_3", new Item(new FabricItemSettings().food(FoodComponentRegistry.MEAT_1)));
    public static Item MEAT_4 = regItem("meat_4", new Item(new FabricItemSettings().food(FoodComponentRegistry.MEAT_1)));
    public static Item MEAT_5 = regItem("meat_5", new Item(new FabricItemSettings().food(FoodComponentRegistry.MEAT_1)));
    public static Item MEAT_6 = regItem("meat_6", new Item(new FabricItemSettings().food(FoodComponentRegistry.MEAT_1)));
    public static Item MEAT_7 = regItem("meat_7", new Item(new FabricItemSettings().food(FoodComponentRegistry.MEAT_1)));
    public static Item MEAT_8 = regItem("meat_8", new Item(new FabricItemSettings().food(FoodComponentRegistry.MEAT_1)));
    public static Item MEAT_9 = regItem("meat_9", new Item(new FabricItemSettings().food(FoodComponentRegistry.MEAT_1)));
    public static Item MEAT_10 = regItem("meat_10", new Item(new FabricItemSettings().food(FoodComponentRegistry.MEAT_1)));
    public static Item MEAT_11 = regItem("meat_11", new Item(new FabricItemSettings().food(FoodComponentRegistry.MEAT_1)));

    private static Item regItem(String name, Item item) {
        return Registry.register(Registries.ITEM, ChambersRef.id(name), item);
    }

    public static void init() {
        ChambersMain.LOGGER.info("Registering Items...");
    }
}