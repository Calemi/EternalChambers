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

    private ChamberInstance chamberInstance;
    private Chamber chamber;

    private int mainPathSize;
    private int branchPathSize;
    private int branchCount;

    private ChamberGenStatus status;
    private int currentMainStep;
    private int currentBranchStep;
    private int currentBranchCount;

    private List<PlacedTile> placedTiles;
    private PlacedTile currentBranchTile;

    public ChamberGenerator(ChamberInstance chamberInstance) {
        status = ChamberGenStatus.CLEAR;
        this.chamberInstance = chamberInstance;
        this.chamber = chamberInstance.getChamber();
        placedTiles = new ArrayList<>();
    }

    public ChamberGenStatus getStatus() {
        return status;
    }

    public void start(World world) {

        if (getStatus() != ChamberGenStatus.CLEAR) {
            return;
        }

        mainPathSize = chamber.getSettings().rollMainPathSize(world.getRandom());
        branchPathSize = chamber.getSettings().rollBranchPathSize(world.getRandom());
        branchCount = chamber.getSettings().rollBranchCount(world.getRandom());

        status = ChamberGenStatus.GENERATING_MAIN;

        currentMainStep = 0;
        currentBranchStep = 0;
        currentBranchCount = 0;

        placedTiles.clear();
    }

    public void clear(World world) {

        for (PlacedTile placedTile : placedTiles) {
            placedTile.clear(world, getChamberOrigin());
        }

        status = ChamberGenStatus.CLEAR;
        currentMainStep = 0;
    }

    public void restart(World world) {
        clear(world);
        start(world);
    }

    public void tick(World world) {

        if (world.getTime() % (TIME_BETWEEN_STEPS * 60) == 0) {

            if (!world.getRegistryKey().equals(DimensionRegistry.CHAMBER_LEVEL_KEY)) {
                return;
            }

            if (getStatus() == ChamberGenStatus.GENERATING_MAIN) {
                genMainStep(world);
            }

            else if (getStatus() == ChamberGenStatus.GENERATING_BRANCH) {
                genBranchStep(world);
            }
        }
    }

    private void genMainStep(World world) {

        if (currentMainStep >= mainPathSize) {
            status = ChamberGenStatus.GENERATING_BRANCH;
            return;
        }

        if (currentMainStep == 0) {

            if (!tryGenerateTile(chamber.getForm().getStartTileSet(), BlockPos.ORIGIN, Direction.NORTH, world, 0)) {
                restart(world);
                return;
            }

            currentMainStep++;
        }

        else {

            if (placedTiles.isEmpty()) {
                ChamberManager.singleton.debugLog("Step failed! Could not find anymore tiles!");
                return;
            }

            PlacedTile placedTile = placedTiles.get(placedTiles.size() - 1);

            Doorway chosenDoorway = placedTile.getRandomDoorway(world);

            TileSet mainPathTileSet = chamber.getForm().getMainPathTileSet();
            if (currentMainStep == mainPathSize - 1) mainPathTileSet = chamber.getForm().getEndTileSet();

            if (!tryGenerateTile(mainPathTileSet, chosenDoorway, world, 0)) {
                restart(world);
                return;
            }

            currentMainStep++;
        }
    }

    private void genBranchStep(World world) {

        if (currentBranchStep >= branchPathSize) {
            currentBranchCount++;
            currentBranchStep = 0;
            branchPathSize = chamber.getSettings().rollBranchPathSize(world.getRandom());
            currentBranchTile = null;
            return;
        }

        if (currentBranchCount > branchCount) {
            status = ChamberGenStatus.READY;
            return;
        }

        if (currentBranchTile == null) {

            Doorway nonConnectedDoorway = null;

            for (PlacedTile placedTile : placedTiles) {

                Doorway chosenDoorway = placedTile.getRandomDoorway(world);

                if (chosenDoorway != null) {
                    nonConnectedDoorway = chosenDoorway;
                    break;
                }
            }

            if (nonConnectedDoorway != null) {
                tryGenerateTile(chamber.getForm().getBranchPathTileSet(), nonConnectedDoorway, world, 0);
            }

            currentBranchStep++;
        }

        else {

            Doorway chosenDoorway = currentBranchTile.getRandomDoorway(world);

            if (chosenDoorway != null) {
                tryGenerateTile(chamber.getForm().getBranchPathTileSet(), chosenDoorway, world, 0);
            }

            currentBranchStep++;
        }
    }

    public boolean tryGenerateTile(TileSet tileset, Doorway doorway, World world, int tries) {

        if (tryGenerateTile(tileset, doorway.getOffsetPos(), doorway.getDirection(), world, tries)) {

            for (PlacedTile placedTile : placedTiles) {

                if (placedTile.getDoorways().remove(doorway)) {
                    break;
                }
            }

            return true;
        }

        return false;
    }

    public boolean tryGenerateTile(TileSet tileset, BlockPos tileOffset, Direction tileDirection, World world, int tries) {

        if (tries >= MAX_FAILED_ATTEMPTS) {
            ChamberManager.singleton.debugLog("Reached max failed attempts!");
            return false;
        }

        Tile tile = tileset.getRandomTile(world.getRandom());
        PlacedTile placedTile = tile.place(getChamberOrigin(), tileOffset, tileDirection, world);

        if (placedTile == null) {
            return tryGenerateTile(tileset, tileOffset, tileDirection, world, ++tries);
        }

        placedTiles.add(placedTile);
        currentBranchTile = placedTile;

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
