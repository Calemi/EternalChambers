package com.calemi.chambers.api.general;

import com.calemi.chambers.registry.DimensionRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.network.packet.s2c.play.PositionFlag;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.EnumSet;

public class TeleportHelper {

    public static boolean isInChamber(Entity entity) {
        return entity.getWorld().getRegistryKey().equals(DimensionRegistry.CHAMBER_LEVEL_KEY);
    }

    public static boolean isInOverworld(Entity entity) {
        return entity.getWorld().getRegistryKey().equals(ServerWorld.OVERWORLD);
    }

    public static void teleportToChamber(Entity entity, BlockPos dest) {

        if (entity.getWorld() instanceof ServerWorld world) {

            ServerWorld destWorld = world.getServer().getWorld(DimensionRegistry.CHAMBER_LEVEL_KEY);

            if (destWorld == null) {
                return;
            }

            teleportToWorld(entity, dest, destWorld);
        }
    }

    public static void teleportToOverworld(Entity entity, BlockPos dest) {

        if (entity.getWorld() instanceof ServerWorld world) {

            ServerWorld destWorld = world.getServer().getWorld(ServerWorld.OVERWORLD);

            if (destWorld == null) {
                return;
            }

            teleportToWorld(entity, dest, destWorld);
        }
    }

    private static void teleportToWorld(Entity entity, BlockPos dest, ServerWorld destWorld) {
        entity.teleport(destWorld, dest.getX() + 0.5D, dest.getY(), dest.getZ() + 0.5D, EnumSet.noneOf(PositionFlag.class), entity.getYaw(), entity.getPitch());
    }
}
