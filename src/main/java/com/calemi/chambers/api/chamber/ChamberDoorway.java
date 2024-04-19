package com.calemi.chambers.api.chamber;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class ChamberDoorway {

    private BlockPos offsetPos;
    private Direction direction;

    public ChamberDoorway(BlockPos offsetPos, Direction direction) {
        this.offsetPos = offsetPos;
        this.direction = direction;
    }

    public BlockPos getOffsetPos() {
        return offsetPos;
    }

    public void setOffsetPos(BlockPos offsetPos) {
        this.offsetPos = offsetPos;
    }

    public Direction getDirection() {
        return direction;
    }
}
