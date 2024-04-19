package com.calemi.chambers.api.chamber;

import com.calemi.ccore.api.location.Location;
import com.calemi.chambers.api.general.DirectionHelper;
import com.calemi.chambers.api.general.StructureHelper;
import com.calemi.chambers.main.ChambersMain;
import com.calemi.chambers.registry.DimensionRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.block.LecternBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;

import java.util.List;

public class ChamberHelper {

    private static final int CHAMBER_WORLD_SIZE = 16000;
    private static final int CHAMBER_Y = 128;
    private static final int CHAMBER_HORIZONTAL_OFFSET = 2000;

    public static List<StructureTemplate.StructureBlockInfo> findDoors(World world, StructureTemplate structureTemplate) {
        StructurePlacementData structurePlacementData = new StructurePlacementData();
        return structureTemplate.getInfosForBlock(new BlockPos(0, 0, 0), structurePlacementData, Blocks.LECTERN);
    }

    public static StructureTemplate.StructureBlockInfo getRandomDoor(Random random, List<StructureTemplate.StructureBlockInfo> structureBlockInfoList) {
        return structureBlockInfoList.get(random.nextInt(structureBlockInfoList.size()));
    }

    public static Direction getDoorDirection(StructureTemplate.StructureBlockInfo structureBlockInfo) {
        return structureBlockInfo.state().get(LecternBlock.FACING);
    }

    public static void generateChamberTile(World world, String tileName, int id) {

        if (world.isClient) {
            return;
        }

        ServerWorld destWorld = world.getServer().getWorld(DimensionRegistry.CHAMBER_LEVEL_KEY);

        if (id < 0 || id >= Math.pow((int)(CHAMBER_WORLD_SIZE / CHAMBER_HORIZONTAL_OFFSET), 2)) {
            debugLog("ID: " + id + " is out of bounds!");
            return;
        }

        BlockPos tilePos = getChamberOrigin(id);

        StructureTemplate tileStructureTemplate = StructureHelper.getStructureTemplate(world, tileName);

        if (tileStructureTemplate == null) {
            debugLog("Structure Template could not be found!");
            return;
        }

        List<StructureTemplate.StructureBlockInfo> doors = findDoors(world, tileStructureTemplate);

        if (doors.isEmpty()) {
            debugLog("No doors could be found in the Tile!");
            return;
        }

        Direction targetDirection = Direction.WEST;

        StructureTemplate.StructureBlockInfo door = getRandomDoor(world.getRandom(), doors);
        BlockPos doorOffset = door.pos();
        Direction doorDirection = getDoorDirection(door);

        BlockRotation tileRotation = DirectionHelper.rotateTowardsTarget(doorDirection, targetDirection.getOpposite());

        BlockPos transformedDoorOffset = StructureTemplate.transformAround(doorOffset, BlockMirror.NONE, tileRotation, BlockPos.ORIGIN);

        Location tileLocation = new Location(destWorld, tilePos);

        //BASELINE OFFSET
        tileLocation.offset(-1, -doorOffset.getY(), -1);

        //OFFSET BASED ON DOOR LOCATION
        tileLocation.offset(-transformedDoorOffset.getX(), 0, -transformedDoorOffset.getZ());

        //OFFSET BASED ON DOOR DIRECTION
        tileLocation.offset(targetDirection.getOffsetX(), 0, targetDirection.getOffsetZ());

        StructurePlacementData placementData = new StructurePlacementData();
        placementData.setRotation(tileRotation);

        debugLog("Generating Tile at: " + tileLocation);
        tileStructureTemplate.place((ServerWorldAccess) world, tileLocation.getBlockPos(), new BlockPos(0, 0, 0), placementData, world.getRandom(), 2);
    }

    public static BlockPos getChamberOrigin(int id) {

        //ORIGIN
        int x = -(CHAMBER_WORLD_SIZE / 2) + (CHAMBER_HORIZONTAL_OFFSET / 2);
        int z = -(CHAMBER_WORLD_SIZE / 2) + (CHAMBER_HORIZONTAL_OFFSET / 2);
        int y = CHAMBER_Y;

        //OFFSET

        int indexX = id % (CHAMBER_WORLD_SIZE / CHAMBER_HORIZONTAL_OFFSET);
        int indexZ = id / (CHAMBER_WORLD_SIZE / CHAMBER_HORIZONTAL_OFFSET);

        x += (indexX * CHAMBER_HORIZONTAL_OFFSET);
        z += (indexZ * CHAMBER_HORIZONTAL_OFFSET);

        return new BlockPos(x, CHAMBER_Y, z);
    }

    private static void debugLog(String msg) {
        if (ChamberManager.instance.debug) ChambersMain.LOGGER.info(msg);
    }
}
