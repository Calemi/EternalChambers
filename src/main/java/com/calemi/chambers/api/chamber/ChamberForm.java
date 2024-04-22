package com.calemi.chambers.api.chamber;

public class ChamberForm {

    private TileSet mainPathTileSet;
    private TileSet branchPathTileSet;
    private TileSet startTileSet;
    private TileSet endTileSet;

    public ChamberForm(TileSet mainPathTileSet, TileSet branchPathTileSet, TileSet startTileSet, TileSet endTileSet) {
        this.mainPathTileSet = mainPathTileSet;
        this.branchPathTileSet = branchPathTileSet;
        this.startTileSet = startTileSet;
        this.endTileSet = endTileSet;
    }

    public TileSet getMainPathTileSet() {
        return mainPathTileSet;
    }

    public TileSet getBranchPathTileSet() {
        return branchPathTileSet;
    }

    public TileSet getStartTileSet() {
        return startTileSet;
    }

    public TileSet getEndTileSet() {
        return endTileSet;
    }
}
