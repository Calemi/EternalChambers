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

    }).build());

    public static void init() {
        ChambersMain.LOGGER.info("Registering Item Groups...");
    }
}
