package com.calemi.chambers.main;

import com.calemi.chambers.api.chamber.*;
import com.calemi.chambers.command.ChamberCommand;
import com.calemi.chambers.registry.*;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;

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

		temp();
	}

	private void temp() {
		Tile room1 = new Tile("room_1");
		Tile room2 = new Tile("room_2");
		Tile room3 = new Tile("room_3");
		Tile room4 = new Tile("room_4");
		TileSet tileSet = new TileSet();
		tileSet.getTileSet().add(new WeightedTile(room1, 1));
		tileSet.getTileSet().add(new WeightedTile(room2, 1));
		tileSet.getTileSet().add(new WeightedTile(room3, 1));
		tileSet.getTileSet().add(new WeightedTile(room4, 1));
		ChamberSettings settings = new ChamberSettings(5, 15, 3, 6, 2, 5);
		Chamber chamber = new Chamber("test_chamber", settings, tileSet);
		ChamberInstance chamberInstance = new ChamberInstance(0, chamber);
		new ChamberManager(Collections.singletonList(chamberInstance));
	}
}