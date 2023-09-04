package com.fwloopins.tabby.client;

import com.fwloopins.tabby.client.config.TabbyConfig;
import com.fwloopins.tabby.client.config.DataManager;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TabbyClient implements ClientModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("Tabby");

	@Override
	public void onInitializeClient() {
		AutoConfig.register(TabbyConfig.class, GsonConfigSerializer::new);

		DataManager.initFiles();
		DataManager.cacheJson();

		reload();

		logInfo("Tabby initialised");
	}

	public static void logInfo(String msg) {
		LOGGER.info("[Tabby] " + msg);
	}

	public static void logError(String msg) {
		LOGGER.error("[Tabby] " + msg);
	}

	private static void reload() {
		TabbyConfig config = AutoConfig.getConfigHolder(TabbyConfig.class).getConfig();

		KeyBinding reloadBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"key.reload",
				InputUtil.Type.KEYSYM,
				config.misc.reloadKey,
				"category.tabby"
		));

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (reloadBinding.wasPressed()) {
				DataManager.initFiles();
				DataManager.cacheJson();
			}
		});
	}
}
