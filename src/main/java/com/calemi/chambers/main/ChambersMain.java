package com.calemi.chambers.main;

import com.calemi.chambers.api.chamber.ChamberManager;
import com.calemi.chambers.command.ChamberCommand;
import com.calemi.chambers.registry.*;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChambersMain implements ModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("Eternal Chambers");

	@Override
	public void onInitialize() {
		LOGGER.info("Initializing Main...");

		BlockRegistry.init();
		ItemRegistry.init();
		ItemGroupRegistry.init();
		BlockEntityTypeRegistry.init();
		ScreenHandlerTypeRegistry.init();
		SoundEventRegistry.init();
		PacketRegistry.init();
		ChamberCommand.init();

		new ChamberManager();
	}
}