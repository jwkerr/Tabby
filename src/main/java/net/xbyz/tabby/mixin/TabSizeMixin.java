package net.xbyz.tabby.mixin;

import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.xbyz.tabby.config.TabbyConfig;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(PlayerListHud.class)
public class TabSizeMixin {
	@Shadow @Final private MinecraftClient client;

	@ModifyConstant(constant = @Constant(longValue = 80L), method = "collectPlayerEntries")
	private long modifyCount(long count) {
		TabbyConfig config = AutoConfig.getConfigHolder(TabbyConfig.class).getConfig();
		if (config.maxCount <= 0) {
			return client.player.networkHandler.getListedPlayerListEntries().size();
		}
		return config.maxCount;
	}

	@ModifyConstant(constant = @Constant(intValue = 20), method = "render")
	private int modifyMaxRows(int MAX_ROWS) {
		TabbyConfig config = AutoConfig.getConfigHolder(TabbyConfig.class).getConfig();
		if (config.adaptive) {
			if (config.maxCount <= 0) {
				int onlinePlayers = client.player.networkHandler.getListedPlayerListEntries().size();
				return Math.max(1, onlinePlayers / config.adaptiveDivisor);
			}
			return (int) Math.max(1, config.maxCount / config.adaptiveDivisor);
		}
		return Math.max(1, config.maxRows);
	}
}
