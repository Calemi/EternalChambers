package com.calemi.chambers.registry;

import com.calemi.chambers.main.ChambersMain;
import com.calemi.chambers.main.ChambersRef;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;

public class ItemGroupRegistry {

    public static final ItemGroup CHAMBERS_MAIN = Registry.register(Registries.ITEM_GROUP, ChambersRef.id(ChambersRef.MOD_ID),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.chambers.main")).icon(() -> new ItemStack(Items.NETHERITE_SWORD)).entries((displayContext, entries) -> {
                entries.add(BlockRegistry.PINK_DOORWAY_MARKER);
                entries.add(ItemRegistry.MEAT_1);
                entries.add(ItemRegistry.MEAT_2);
                entries.add(ItemRegistry.MEAT_3);
                entries.add(ItemRegistry.MEAT_4);
                entries.add(ItemRegistry.MEAT_5);
                entries.add(ItemRegistry.MEAT_6);
                entries.add(ItemRegistry.MEAT_7);
                entries.add(ItemRegistry.MEAT_8);
                entries.add(ItemRegistry.MEAT_9);
                entries.add(ItemRegistry.MEAT_10);
                entries.add(ItemRegistry.MEAT_11);
    }).build());

    public static void init() {
        ChambersMain.LOGGER.info("Registering Item Groups...");
    }
}
