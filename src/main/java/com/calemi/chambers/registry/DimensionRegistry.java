package com.calemi.chambers.registry;

import com.calemi.chambers.main.ChambersRef;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.dimension.DimensionTypes;

import java.util.OptionalLong;

public class DimensionRegistry {

    public static final RegistryKey<DimensionOptions> CHAMBER_KEY = RegistryKey.of(RegistryKeys.DIMENSION, ChambersRef.id("chamber"));
    public static final RegistryKey<World> CHAMBER_LEVEL_KEY = RegistryKey.of(RegistryKeys.WORLD, ChambersRef.id("chamber"));
    public static final RegistryKey<DimensionType> CHAMBER_DIM_TYPE = RegistryKey.of(RegistryKeys.DIMENSION_TYPE, ChambersRef.id("chamber_type"));

    public static void init(Registerable<DimensionType> context) {
        context.register(CHAMBER_DIM_TYPE, new DimensionType(
                OptionalLong.of(12000), // fixedTime
                false, // hasSkylight
                false, // hasCeiling
                false, // ultraWarm
                false, // natural
                1.0, // coordinateScale
                true, // bedWorks
                true, // respawnAnchorWorks
                0, // minY
                256, // height
                256, // logicalHeight
                BlockTags.INFINIBURN_OVERWORLD, // infiniburn
                DimensionTypes.OVERWORLD_ID, // effectsLocation
                0.0F, // ambientLight
                new DimensionType.MonsterSettings(false, false, UniformIntProvider.create(0, 0), 0)));
    }
}
