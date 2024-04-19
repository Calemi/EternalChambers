package com.calemi.chambers.api.general;

import com.calemi.ccore.api.location.Location;
import com.calemi.chambers.main.ChambersRef;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.structure.StructureTemplateManager;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;

import java.util.Optional;

public class StructureHelper {

    public static StructureTemplate getStructureTemplate(World world, String structureName) {
        Identifier structureId = ChambersRef.id(structureName);
        StructureTemplateManager structureManager = world.getServer().getStructureTemplateManager();
        Optional<StructureTemplate> structureTemplateOptional = structureManager.getTemplate(structureId);
        return structureTemplateOptional.orElse(null);
    }

    public static BlockRotation getBlockRotation(Direction direction) {

        switch (direction) {
            case NORTH -> {
                return BlockRotation.CLOCKWISE_90;
            }

            case EAST -> {
                return BlockRotation.CLOCKWISE_180;
            }

            case SOUTH -> {
                return BlockRotation.COUNTERCLOCKWISE_90;
            }

            case WEST -> {
                return BlockRotation.NONE;
            }
        }

        return BlockRotation.NONE;
    }
}
