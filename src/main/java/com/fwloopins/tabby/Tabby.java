package com.fwloopins.tabby;

import com.fwloopins.tabby.config.TabbyConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Tabby implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("Tabby");

	@Override
	public void onInitialize() {
		AutoConfig.register(TabbyConfig.class, GsonConfigSerializer::new);

		LOGGER.info("Tabby initialized");
	}
}
