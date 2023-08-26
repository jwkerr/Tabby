package com.fwloopins.tabby.mixin;

import com.fwloopins.tabby.config.TabbyConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Mixin(PlayerListHud.class)
public abstract class TabSizeMixin {
	@Shadow @Final private MinecraftClient client;
	@Shadow public abstract void setFooter(@Nullable Text footer);

	@Shadow public abstract void setHeader(@Nullable Text header);

	@Unique
	TabbyConfig config = AutoConfig.getConfigHolder(TabbyConfig.class).getConfig();

	@ModifyConstant(constant = @Constant(longValue = 80L), method = "collectPlayerEntries")
	private long modifyCount(long count) {
		if (config.general.maxCount <= 0)
			return client.player.networkHandler.getListedPlayerListEntries().size();

		return config.general.maxCount;
	}

	@ModifyConstant(constant = @Constant(intValue = 20), method = "render")
	private int modifyMaxRows(int MAX_ROWS) {
		setHeaderAndFooter();

		if (!config.general.adaptive)
			return Math.max(1, config.general.maxRows);

		if (config.general.maxCount <= 0) {
			int onlinePlayers = client.player.networkHandler.getListedPlayerListEntries().size();
			return Math.max(1, onlinePlayers / config.general.adaptiveDivisor);
		}

		return (int) Math.max(1, config.general.maxCount / config.general.adaptiveDivisor);
	}

	@Unique
	private void setHeaderAndFooter() {
		if (!config.general.customHeader.isEmpty()) {
			if (config.general.customHeader.equals("null")) {
				setHeader(null);
			} else {
				setHeader(Text.of(config.general.customHeader));
			}
		}

		if (!config.general.customFooter.isEmpty()) {
			if (config.general.customFooter.equals("null")) {
				setFooter(null);
			} else {
				setFooter(Text.of(config.general.customFooter));
			}
		}
	}
}
