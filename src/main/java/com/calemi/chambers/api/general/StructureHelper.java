package com.calemi.chambers.api.general;

import com.calemi.chambers.main.ChambersRef;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.structure.StructureTemplateManager;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.Optional;

public class StructureHelper {

    public static StructureTemplate getStructureTemplate(World world, String structureName) {
        Identifier structureId = ChambersRef.id(structureName);
        StructureTemplateManager structureManager = world.getServer().getStructureTemplateManager();
        Optional<StructureTemplate> structureTemplateOptional = structureManager.getTemplate(structureId);
        return structureTemplateOptional.orElse(null);
    }
}
