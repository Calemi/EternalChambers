package com.calemi.chambers.api.chamber;

import com.calemi.chambers.main.ChambersMain;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.world.World;

import java.util.List;

public class ChamberManager {

    public final boolean debug = true;
    public static ChamberManager singleton;

    private List<ChamberInstance> chamberInstances;

    public ChamberManager(List<ChamberInstance> chamberInstances) {
        singleton = this;

        this.chamberInstances = chamberInstances;

        ServerTickEvents.START_WORLD_TICK.register((world) -> {

            ChamberManager.singleton.tick(world);
        });
    }

    public ChamberInstance getChamberInstance(int id) {

        for (ChamberInstance chamberInstance : chamberInstances) {

            if (chamberInstance.getID() == id) {
                return chamberInstance;
            }
        }

        return null;
    }

    public void tick(World world) {
        chamberInstances.forEach((c) -> c.tick(world));
    }

    public void debugLog(String msg) {
        if (debug) ChambersMain.LOGGER.info(msg);
    }
}
