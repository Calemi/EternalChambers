package com.calemi.chambers.api.general;

import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.Direction;

public class DirectionHelper {

    public static BlockRotation rotateTowardsTarget(Direction source, Direction target) {

        if (source == target) {
            return BlockRotation.NONE;
        }

        if (source.getOpposite() == target) {
            return BlockRotation.CLOCKWISE_180;
        }

        if (source.rotateYClockwise() == target) {
            return BlockRotation.CLOCKWISE_90;
        }

        if (source.rotateYCounterclockwise() == target) {
            return BlockRotation.COUNTERCLOCKWISE_90;
        }

        return BlockRotation.NONE;
    }
}
