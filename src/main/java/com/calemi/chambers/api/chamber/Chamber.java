package com.calemi.chambers.api.chamber;

public class Chamber {

    private String name;
    private ChamberSettings settings;
    private TileSet tileSet;

    public Chamber(String name, ChamberSettings settings, TileSet... tileSets) {
        this.name = name;
        this.settings = settings;
        tileSet = new TileSet();

        for (TileSet tileset : tileSets) {
            this.tileSet.merge(tileset);
        }
    }

    public String getName() {
        return name;
    }

    public ChamberSettings getSettings() {
        return settings;
    }

    public TileSet getTileSet() {
        return tileSet;
    }
}
