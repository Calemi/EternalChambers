package com.calemi.chambers.api.chamber;

import net.minecraft.world.World;

public class ChamberInstance {

    private int id;
    private Chamber chamber;

    private ChamberGenerator generator;

    public ChamberInstance(int id, Chamber chamber) {
        this.id = id;
        this.chamber = chamber;
        generator = new ChamberGenerator(this);
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
