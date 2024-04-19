package com.calemi.chambers.api.chamber;

import net.minecraft.world.World;

public class ChamberInstance {

    private ChamberGenerator generator;

    private int id;
    private Chamber chamber;

    public ChamberInstance(int id, Chamber chamber) {
        generator = new ChamberGenerator(5, this);
        this.id = id;
        this.chamber = chamber;
    }

    public int getID() {
        return id;
    }

    public Chamber getChamber() {
        return chamber;
    }

    public ChamberGenerator getGenerator() {
        return generator;
    }

    public void tick(World world) {
        getGenerator().tick(world);
    }
}
