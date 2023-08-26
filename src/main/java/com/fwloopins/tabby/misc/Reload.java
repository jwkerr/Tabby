package com.fwloopins.tabby.misc;

import com.fwloopins.tabby.config.DataManager;
import com.fwloopins.tabby.config.TabbyConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;

public class Reload {
    static TabbyConfig config = AutoConfig.getConfigHolder(TabbyConfig.class).getConfig();

    public static void reload() {
        KeyBinding reloadBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.reload",
                InputUtil.Type.KEYSYM,
                config.misc.reloadKey,
                "category.tabby"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (reloadBinding.wasPressed()) {
                DataManager.cacheJson();
            }
        });
    }
}
