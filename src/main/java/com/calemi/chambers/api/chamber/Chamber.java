package com.calemi.chambers.api.chamber;

public class Chamber {

    private String name;
    private TileSet tileSet;

    public Chamber(String name, TileSet... tileSets) {

        this.name = name;
        tileSet = new TileSet();

        for (TileSet tileset : tileSets) {
            this.tileSet.merge(tileset);
        }
    }

    public String getName() {
        return name;
    }

    public TileSet getTileSet() {
        return tileSet;
    }
}
