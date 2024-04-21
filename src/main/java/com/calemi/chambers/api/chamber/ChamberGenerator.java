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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class ChamberGenerator {

    private static final int CHAMBER_WORLD_SIZE = 16000;
    private static final int CHAMBER_Y = 128;
    private static final int CHAMBER_HORIZONTAL_OFFSET = 2000;
    private static final float TIME_BETWEEN_STEPS = 1F;

    private ChamberGenStatus status;
    private int currentStep;

    private int tileCount;
    private ChamberInstance chamberInstance;

    private List<ChamberDoorway> doorwayCandidates;

    public ChamberGenerator(int tileCount, ChamberInstance chamberInstance) {
        status = ChamberGenStatus.CLEAR;
        this.tileCount = tileCount;
        this.chamberInstance = chamberInstance;
        doorwayCandidates = new ArrayList<>();
    }

    public ChamberGenStatus getStatus() {
        return status;
    }

    public int getTileCount() {
        return tileCount;
    }

    public void start() {

        if (getStatus() != ChamberGenStatus.CLEAR) {
            return;
        }

        status = ChamberGenStatus.GENERATING;
        currentStep = 0;
        doorwayCandidates.clear();
    }

    public void clear() {

        if (getStatus() != ChamberGenStatus.READY) {
            return;
        }

        status = ChamberGenStatus.CLEAR;
        currentStep = 0;
    }

    public void tick(World world) {

        if (!world.getRegistryKey().equals(DimensionRegistry.CHAMBER_LEVEL_KEY)) {
            return;
        }

        if (getStatus() == ChamberGenStatus.GENERATING) {

            if (world.getTime() % (TIME_BETWEEN_STEPS * 20) == 0) {
                generationStep(world);
                currentStep++;
            }
        }
    }

    private void generationStep(World world) {

        if (currentStep >= 10) {
            status = ChamberGenStatus.READY;
            return;
        }

        if (currentStep == 0) {
            tryGenerateChamberTile(world, BlockPos.ORIGIN, Direction.NORTH);
        }

        else {

            if (doorwayCandidates.isEmpty()) {
                ChamberManager.singleton.debugLog("Step failed! Could not find anymore doorways!");
                return;
            }

            int chosenDoorwayIndex = world.getRandom().nextInt(doorwayCandidates.size());
            ChamberDoorway chosenDoorway = doorwayCandidates.get(0);

            doorwayCandidates.remove(0);
            tryGenerateChamberTile(world, chosenDoorway.getOffsetPos(), chosenDoorway.getDirection());
        }
    }

    public boolean tryGenerateChamberTile(World world, BlockPos tileOffset, Direction tileDirection) {

        ServerWorld destWorld = world.getServer().getWorld(DimensionRegistry.CHAMBER_LEVEL_KEY);
        BlockPos tilePos = getChamberOrigin().add(tileOffset);

        if (chamberInstance.getID() < 0 || chamberInstance.getID() >= Math.pow((int)(CHAMBER_WORLD_SIZE / CHAMBER_HORIZONTAL_OFFSET), 2)) {
            ChamberManager.singleton.debugLog("ID: " + chamberInstance.getID() + " is out of bounds!");
            return false;
        }

        Tile tile = chamberInstance.getChamber().getTileSet().getRandomTile(world.getRandom());
        StructureTemplate tileStructureTemplate = StructureHelper.getStructureTemplate(world, tile.getTileName());

        if (tileStructureTemplate == null) {
            ChamberManager.singleton.debugLog("Structure Template could not be found!");
            return false;
        }

        List<ChamberDoorway> doorwaysInTile = findDoorways(new StructurePlacementData(), tileStructureTemplate);

        if (doorwaysInTile.isEmpty()) {
            ChamberManager.singleton.debugLog("No doorways could be found in the Tile!");
            return false;
        }

        int chosenDoorwayIndex = world.getRandom().nextInt(doorwaysInTile.size());
        ChamberDoorway chosenDoorway = doorwaysInTile.get(chosenDoorwayIndex);
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

        ChamberManager.singleton.debugLog("Generating Tile [" + tile.getTileName() + "] at: " + tilePos + ", edge: " + tileLocation.getBlockPos());
        tileStructureTemplate.place(destWorld, tileLocation.getBlockPos(), new BlockPos(0, 0, 0), placementData, world.getRandom(), 2);



        List<ChamberDoorway> test = findDoorways(placementData, tileStructureTemplate);

        test.remove(chosenDoorwayIndex);

        for (ChamberDoorway doorway : test) {

            ChamberManager.singleton.debugLog("Door Offset from Tile " + doorway.getOffsetPos());

            BlockPos tileOffsetFromOrigin = tileLocation.getBlockPos().subtract(getChamberOrigin());
            BlockPos offsetFromOrigin = tileOffsetFromOrigin.add(doorway.getOffsetPos()).add(1, 0, 1);
            doorway.setOffsetPos(offsetFromOrigin);

            ChamberManager.singleton.debugLog("Tile Offset from Origin " + tileOffsetFromOrigin);
            ChamberManager.singleton.debugLog("New Door Offset " + doorway.getOffsetPos());
        }

        doorwayCandidates.addAll(test);

        return true;
    }

    public List<ChamberDoorway> findDoorways(StructurePlacementData placementData, StructureTemplate structureTemplate) {

        List<ChamberDoorway> doorways = new ArrayList<>();

        List<StructureTemplate.StructureBlockInfo> structureBlockInfoList = structureTemplate.getInfosForBlock(new BlockPos(0, 0, 0), placementData, Blocks.LECTERN);

        for (StructureTemplate.StructureBlockInfo structureBlockInfo : structureBlockInfoList) {
            doorways.add(new ChamberDoorway(structureBlockInfo.pos(), structureBlockInfo.state().get(LecternBlock.FACING)));
        }

        return doorways;
    }

    public BlockPos getChamberOrigin() {

        //ORIGIN
        int x = -(CHAMBER_WORLD_SIZE / 2) + (CHAMBER_HORIZONTAL_OFFSET / 2);
        int z = -(CHAMBER_WORLD_SIZE / 2) + (CHAMBER_HORIZONTAL_OFFSET / 2);
        int y = CHAMBER_Y;

        //OFFSET

        int indexX = chamberInstance.getID() % (CHAMBER_WORLD_SIZE / CHAMBER_HORIZONTAL_OFFSET);
        int indexZ = chamberInstance.getID() / (CHAMBER_WORLD_SIZE / CHAMBER_HORIZONTAL_OFFSET);

        x += (indexX * CHAMBER_HORIZONTAL_OFFSET);
        z += (indexZ * CHAMBER_HORIZONTAL_OFFSET);

        return new BlockPos(x, CHAMBER_Y, z);
    }

    public enum ChamberGenStatus {
        CLEAR,
        GENERATING,
        READY
    }
}
