package com.calemi.chambers.api.chamber;

import com.calemi.ccore.api.location.Location;
import com.calemi.chambers.api.general.DirectionHelper;
import com.calemi.chambers.api.general.StructureHelper;
import com.calemi.chambers.registry.DimensionRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.block.LecternBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class Tile {

    private String tileName;

    public Tile(String tileName) {
        this.tileName = tileName;
    }

    public String getTileName() {
        return tileName;
    }

    public PlacedTile place(BlockPos chamberOrigin, BlockPos tileOffset, Direction tileDirection, World world) {

        ServerWorld destWorld = world.getServer().getWorld(DimensionRegistry.CHAMBER_LEVEL_KEY);
        BlockPos tilePos = chamberOrigin.add(tileOffset);
        StructureTemplate tileTemplate = StructureHelper.getStructureTemplate(world, getTileName());

        if (tileTemplate == null) {
            ChamberManager.singleton.debugLog("Structure Template could not be found!");
            return null;
        }

        List<Doorway> doorwaysInTile = findDoorways(new StructurePlacementData(), tileTemplate);

        if (doorwaysInTile.isEmpty()) {
            ChamberManager.singleton.debugLog("No doorways could be found in the Tile!");
            return null;
        }

        int chosenDoorwayIndex = world.getRandom().nextInt(doorwaysInTile.size());
        Doorway chosenDoorway = doorwaysInTile.get(chosenDoorwayIndex);
        BlockPos doorwayOffset = chosenDoorway.getOffsetPos();

        BlockRotation tileRotation = DirectionHelper.rotateTowardsTarget(chosenDoorway.getDirection(), tileDirection.getOpposite());

        BlockPos transformedDoorOffset = StructureTemplate.transformAround(doorwayOffset, BlockMirror.NONE, tileRotation, BlockPos.ORIGIN);

        Location tileLocation = new Location(destWorld, tilePos);

        //BASELINE OFFSET
        tileLocation.offset(-1, -doorwayOffset.getY(), -1);

        //OFFSET BASED ON DOOR LOCATION
        tileLocation.offset(-transformedDoorOffset.getX(), 0, -transformedDoorOffset.getZ());

        //OFFSET BASED ON DOOR DIRECTION
        tileLocation.offset(tileDirection.getOffsetX(), 0, tileDirection.getOffsetZ());

        StructurePlacementData placementData = new StructurePlacementData();
        placementData.setRotation(tileRotation);

        BlockPos offsetFromOrigin = tileLocation.getBlockPos().subtract(chamberOrigin);
        BlockBox tileBounds = tileTemplate.calculateBoundingBox(placementData, offsetFromOrigin);

        if (!isBoundsClear(tileBounds, chamberOrigin, world)) {
            ChamberManager.singleton.debugLog("Could not fit Tile at: " + tilePos);
            return null;
        }

        ChamberManager.singleton.debugLog("Generating Tile [" + getTileName() + "] at: " + tilePos + ", edge: " + tileLocation.getBlockPos());
        tileTemplate.place(destWorld, tileLocation.getBlockPos(), new BlockPos(0, 0, 0), placementData, world.getRandom(), 2);

        PlacedTile placedTile = new PlacedTile(this, offsetFromOrigin, tileTemplate, placementData);
        placedTile.getDoorways().remove(chosenDoorwayIndex);

        return placedTile;
    }

    public boolean isBoundsClear(BlockBox bounds, BlockPos chamberOrigin, World world) {

        int minX = chamberOrigin.getX() + bounds.getMinX();
        int minY = chamberOrigin.getY() + bounds.getMinY();
        int minZ = chamberOrigin.getZ() + bounds.getMinZ();

        int maxX = chamberOrigin.getX() + bounds.getMaxX();
        int maxY = chamberOrigin.getY() + bounds.getMaxY();
        int maxZ = chamberOrigin.getZ() + bounds.getMaxZ();

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {

                    if (!world.isAir(new BlockPos(x, y, z))) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public List<Doorway> findDoorways(StructurePlacementData placementData, StructureTemplate structureTemplate) {

        List<Doorway> doorways = new ArrayList<>();

        List<StructureTemplate.StructureBlockInfo> structureBlockInfoList = structureTemplate.getInfosForBlock(new BlockPos(0, 0, 0), placementData, Blocks.LECTERN);

        for (StructureTemplate.StructureBlockInfo structureBlockInfo : structureBlockInfoList) {
            doorways.add(new Doorway(structureBlockInfo.pos(), structureBlockInfo.state().get(LecternBlock.FACING)));
        }

        return doorways;
    }
}
