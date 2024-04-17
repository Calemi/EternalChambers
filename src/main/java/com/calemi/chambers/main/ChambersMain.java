package com.calemi.chambers.main;

import com.calemi.chambers.registry.*;
import net.fabricmc.api.ModInitializer;

import net.kyrptonaught.customportalapi.api.CustomPortalBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
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

		CustomPortalBuilder.beginPortal()
				.frameBlock(Blocks.GOLD_BLOCK)
				.lightWithItem(Items.GOLD_INGOT)
				.destDimID(new Identifier(ChambersRef.MOD_ID, "chamber"))
				.tintColor(0xc76efa)
				.registerPortal();
	}
}