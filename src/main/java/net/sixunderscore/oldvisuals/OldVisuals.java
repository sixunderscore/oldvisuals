package net.sixunderscore.oldvisuals;

import net.fabricmc.api.ModInitializer;

import net.sixunderscore.oldvisuals.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OldVisuals implements ModInitializer {
	public static final String MOD_ID = "OldVisuals";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		Config.load();
	}
}