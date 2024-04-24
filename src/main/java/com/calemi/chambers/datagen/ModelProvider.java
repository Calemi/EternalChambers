package com.calemi.chambers.datagen;

import com.calemi.chambers.registry.BlockRegistry;
import com.calemi.chambers.registry.ItemRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.*;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

public class ModelProvider extends FabricModelProvider {

    public ModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator generator) {

        TextureMap textureMap = (new TextureMap())
                .put(TextureKey.PARTICLE, TextureMap.getSubId(BlockRegistry.PINK_DOORWAY_MARKER, "_front"))
                .put(TextureKey.NORTH, TextureMap.getSubId(BlockRegistry.PINK_DOORWAY_MARKER, "_front"))
                .put(TextureKey.SOUTH, TextureMap.getSubId(BlockRegistry.PINK_DOORWAY_MARKER, "_back"))
                .put(TextureKey.WEST, TextureMap.getSubId(BlockRegistry.PINK_DOORWAY_MARKER, "_side"))
                .put(TextureKey.EAST, TextureMap.getSubId(BlockRegistry.PINK_DOORWAY_MARKER, "_side"))
                .put(TextureKey.UP, TextureMap.getSubId(BlockRegistry.PINK_DOORWAY_MARKER, "_side"))
                .put(TextureKey.DOWN, TextureMap.getSubId(BlockRegistry.PINK_DOORWAY_MARKER, "_side"));

        Identifier modelIdentifier = Models.CUBE_DIRECTIONAL.upload(BlockRegistry.PINK_DOORWAY_MARKER, textureMap, generator.modelCollector);

        generator.blockStateCollector.accept(VariantsBlockStateSupplier.create(BlockRegistry.PINK_DOORWAY_MARKER)
                        .coordinate(BlockStateVariantMap.create(Properties.HORIZONTAL_FACING)
                                .register(Direction.NORTH, BlockStateVariant.create().put(VariantSettings.MODEL, modelIdentifier))
                                .register(Direction.EAST, BlockStateVariant.create().put(VariantSettings.MODEL, modelIdentifier).put(VariantSettings.Y, VariantSettings.Rotation.R90))
                                .register(Direction.SOUTH, BlockStateVariant.create().put(VariantSettings.MODEL, modelIdentifier).put(VariantSettings.Y, VariantSettings.Rotation.R180))
                                .register(Direction.WEST, BlockStateVariant.create().put(VariantSettings.MODEL, modelIdentifier).put(VariantSettings.Y, VariantSettings.Rotation.R270))));
    }

    @Override
    public void generateItemModels(ItemModelGenerator generator) {
        generator.register(ItemRegistry.MEAT_1, Models.GENERATED);
        generator.register(ItemRegistry.MEAT_2, Models.GENERATED);
        generator.register(ItemRegistry.MEAT_3, Models.GENERATED);
        generator.register(ItemRegistry.MEAT_4, Models.GENERATED);
        generator.register(ItemRegistry.MEAT_5, Models.GENERATED);
        generator.register(ItemRegistry.MEAT_6, Models.GENERATED);
        generator.register(ItemRegistry.MEAT_7, Models.GENERATED);
        generator.register(ItemRegistry.MEAT_8, Models.GENERATED);
        generator.register(ItemRegistry.MEAT_9, Models.GENERATED);
        generator.register(ItemRegistry.MEAT_10, Models.GENERATED);
        generator.register(ItemRegistry.MEAT_11, Models.GENERATED);
    }
}
