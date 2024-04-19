package com.calemi.chambers.api.chamber;

import net.minecraft.util.math.random.Random;

import java.util.ArrayList;

public class TileSet {

    private ArrayList<WeightedTile> tileSet;

    public TileSet() {
        tileSet = new ArrayList<>();
    }

    public ArrayList<WeightedTile> getTileSet() {
        return tileSet;
    }

    public void merge(TileSet other) {
        tileSet.addAll(other.getTileSet());
    }

    public Tile getRandomTile(Random random) {

        float totalWeight = 0;

        for (WeightedTile tile : tileSet) {
            totalWeight += tile.getWeight();
        }

        float randomWeight = random.nextFloat() * totalWeight;

        for (WeightedTile tile : tileSet) {

            randomWeight -= tile.getWeight();

            if (randomWeight <= 0) {
                return tile.getTile();
            }
        }

        return null;
    }
}
