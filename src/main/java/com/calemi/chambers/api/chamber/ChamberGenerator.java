package com.calemi.chambers.api.chamber;

import com.calemi.chambers.registry.DimensionRegistry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class ChamberGenerator {

    private static final int CHAMBER_WORLD_SIZE = 16000;
    private static final int CHAMBER_Y = 128;
    private static final int CHAMBER_HORIZONTAL_OFFSET = 2000;
    private static final float TIME_BETWEEN_STEPS = 0.1F;
    private static final float MAX_FAILED_ATTEMPTS = 20;

    private ChamberGenStatus status;
    private int currentStep;

    private int tileCount;
    private ChamberInstance chamberInstance;

    private int mainPathSize;
    private int branchCount;
    private int branchPathSize;
    private List<PlacedTile> placedTiles;

    public ChamberGenerator(ChamberInstance chamberInstance) {
        status = ChamberGenStatus.CLEAR;
        this.chamberInstance = chamberInstance;
        placedTiles = new ArrayList<>();
    }

    public ChamberGenStatus getStatus() {
        return status;
    }

    public int getTileCount() {
        return tileCount;
    }

    public void start(World world) {

        if (getStatus() != ChamberGenStatus.CLEAR) {
            return;
        }

        mainPathSize = chamberInstance.getChamber().getSettings().rollMainPathSize(world.getRandom());
        branchCount = chamberInstance.getChamber().getSettings().rollBranchCount(world.getRandom());
        branchPathSize = chamberInstance.getChamber().getSettings().rollBranchPathSize(world.getRandom());

        status = ChamberGenStatus.GENERATING_MAIN;
        currentStep = 0;
        placedTiles.clear();
        ChamberManager.singleton.debugLog("RESTART");
    }

    public void clear(World world) {

        for (PlacedTile placedTile : placedTiles) {
            placedTile.clear(world, getChamberOrigin());
        }

        status = ChamberGenStatus.CLEAR;
        currentStep = 0;
    }

    public void restart(World world) {
        clear(world);
        start(world);
    }

    public void tick(World world) {

        if (!world.getRegistryKey().equals(DimensionRegistry.CHAMBER_LEVEL_KEY)) {
            return;
        }

        if (getStatus() == ChamberGenStatus.GENERATING_MAIN) {

            if (world.getTime() % (TIME_BETWEEN_STEPS * 20) == 0) {
                generationStep(world);
            }
        }
    }

    private void generationStep(World world) {

        if (currentStep >= mainPathSize) {
            status = ChamberGenStatus.READY;
            return;
        }

        if (currentStep == 0) {

            if (!tryGenerateTile(BlockPos.ORIGIN, Direction.NORTH, world, 0)) {
                restart(world);
            }

            currentStep++;
        }

        else {

            if (placedTiles.isEmpty()) {
                ChamberManager.singleton.debugLog("Step failed! Could not find anymore tiles!");
                return;
            }

            PlacedTile placedTile = placedTiles.get(placedTiles.size() - 1);

            if (placedTile.getDoors().isEmpty()) {
                ChamberManager.singleton.debugLog("Step failed! Could not find anymore doors!");
                return;
            }

            int chosenDoorwayIndex = world.getRandom().nextInt(placedTile.getDoors().size());
            Doorway chosenDoorway = placedTile.getDoors().get(0);

            if (!tryGenerateTile(chosenDoorway.getOffsetPos(), chosenDoorway.getDirection(), world, 0)) {
                restart(world);
                return;
            }

            currentStep++;
        }
    }

    public boolean tryGenerateTile(BlockPos tileOffset, Direction tileDirection, World world, int tries) {

        if (tries >= MAX_FAILED_ATTEMPTS) {
            ChamberManager.singleton.debugLog("Reached max failed attempts!");
            return false;
        }

        Tile tile = chamberInstance.getChamber().getTileSet().getRandomTile(world.getRandom());
        PlacedTile placedTile = tile.place(getChamberOrigin(), tileOffset, tileDirection, world);

        if (placedTile == null) {
            return tryGenerateTile(tileOffset, tileDirection, world, ++tries);
        }

        placedTiles.add(placedTile);

        return true;
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
        GENERATING_MAIN,
        GENERATING_BRANCH,
        READY
    }
}
