package com.calemi.chambers.api.chamber;

public class WeightedTile {

    private Tile tile;
    private float weight;

    public WeightedTile(Tile tile, float weight) {
        this.tile = tile;
        this.weight = weight;
    }

    public Tile getTile() {
        return tile;
    }

    public float getWeight() {
        return weight;
    }
}
