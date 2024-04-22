package com.calemi.chambers.api.chamber;

import net.minecraft.block.Blocks;
import net.minecraft.block.LecternBlock;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class PlacedTile {

    private Tile tile;
    private BlockBox bounds;

    private List<Doorway> doorways;

    public PlacedTile(Tile tile, BlockPos offsetFromOrigin, StructureTemplate template, StructurePlacementData placementData) {
        this.tile = tile;
        doorways = new ArrayList<>();

        bounds = template.calculateBoundingBox(placementData, offsetFromOrigin);

        List<StructureTemplate.StructureBlockInfo> doorBlockInfoList = template.getInfosForBlock(new BlockPos(0, 0, 0), placementData, Blocks.LECTERN);

        for (StructureTemplate.StructureBlockInfo doorBlockInfo : doorBlockInfoList) {

            BlockPos doorOffsetFromOrigin = offsetFromOrigin.add(doorBlockInfo.pos()).add(1, 0, 1);
            doorways.add(new Doorway(doorOffsetFromOrigin, doorBlockInfo.state().get(LecternBlock.FACING)));
        }
    }

    public List<Doorway> getDoorways() {
        return doorways;
    }

    public void clear(World world, BlockPos origin) {

        int minX = origin.getX() + bounds.getMinX();
        int minY = origin.getY() + bounds.getMinY();
        int minZ = origin.getZ() + bounds.getMinZ();

        int maxX = origin.getX() + bounds.getMaxX();
        int maxY = origin.getY() + bounds.getMaxY();
        int maxZ = origin.getZ() + bounds.getMaxZ();

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    world.setBlockState(new BlockPos(x, y, z), Blocks.AIR.getDefaultState(), 2);
                }
            }
        }
    }
}
