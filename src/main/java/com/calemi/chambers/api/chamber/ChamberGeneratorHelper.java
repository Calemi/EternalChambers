package com.calemi.chambers.api.chamber;

import net.minecraft.block.Blocks;
import net.minecraft.block.LecternBlock;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class ChamberGeneratorHelper {

    public static List<ChamberDoorway> findDoorways(World world, StructurePlacementData placementData, StructureTemplate structureTemplate) {

        List<ChamberDoorway> doorways = new ArrayList<>();

        List<StructureTemplate.StructureBlockInfo> structureBlockInfoList = structureTemplate.getInfosForBlock(new BlockPos(0, 0, 0), placementData, Blocks.LECTERN);

        for (StructureTemplate.StructureBlockInfo structureBlockInfo : structureBlockInfoList) {
            doorways.add(new ChamberDoorway(structureBlockInfo.pos(), structureBlockInfo.state().get(LecternBlock.FACING)));
        }

        return doorways;
    }
}
