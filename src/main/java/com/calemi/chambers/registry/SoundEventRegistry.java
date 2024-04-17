package com.calemi.chambers.registry;

import com.calemi.chambers.main.ChambersMain;
import com.calemi.chambers.main.ChambersRef;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;

public class SoundEventRegistry {

    private static SoundEvent regSound(String name, SoundEvent soundEvent) {
        return Registry.register(Registries.SOUND_EVENT, ChambersRef.id(name), soundEvent);
    }

    public static void init() {
        ChambersMain.LOGGER.info("Registering Sounds...");
    }
}
