package com.calemi.chambers.api.chamber;

import net.minecraft.util.math.random.Random;

public class ChamberSettings {

    private int mainPathSizeMin;
    private int mainPathSizeMax;

    private int branchCountMin;
    private int branchCountMax;
    private int branchPathSizeMin;
    private int branchPathSizeMax;

    public ChamberSettings(int mainPathSizeMin, int mainPathSizeMax, int branchCountMin, int branchCountMax, int branchPathSizeMin, int branchPathSizeMax) {

        this.mainPathSizeMin = Math.abs(Math.min(mainPathSizeMin, mainPathSizeMax));
        this.mainPathSizeMax = Math.abs(Math.max(mainPathSizeMin, mainPathSizeMax));
        this.branchCountMin = Math.abs(Math.min(branchCountMin, branchCountMax));
        this.branchCountMax = Math.abs(Math.max(branchCountMin, branchCountMax));
        this.branchPathSizeMin = Math.abs(Math.min(branchPathSizeMin, branchPathSizeMax));
        this.branchPathSizeMax = Math.abs(Math.min(branchPathSizeMin, branchPathSizeMax));
    }

    public int rollMainPathSize(Random random) {
        return random.nextBetween(getMainPathSizeMin(), getMainPathSizeMax());
    }

    public int rollBranchCount(Random random) {
        return random.nextBetween(getBranchCountMin(), getBranchCountMax());
    }

    public int rollBranchPathSize(Random random) {
        return random.nextBetween(getBranchPathSizeMin(), getBranchPathSizeMax());
    }

    public int getMainPathSizeMin() {
        return mainPathSizeMin;
    }

    public int getMainPathSizeMax() {
        return mainPathSizeMax;
    }

    public int getBranchCountMin() {
        return branchCountMin;
    }

    public int getBranchCountMax() {
        return branchCountMax;
    }

    public int getBranchPathSizeMin() {
        return branchPathSizeMin;
    }

    public int getBranchPathSizeMax() {
        return branchPathSizeMax;
    }
}
