package com.calemi.chambers.registry;

import net.minecraft.item.FoodComponent;

public class FoodComponentRegistry {

    public static FoodComponent MEAT_1 = new FoodComponent.Builder().hunger(1).saturationModifier(0.1F).meat().build();
}
