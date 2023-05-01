package net.xbyz.tabby.mixin;

import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.xbyz.tabby.config.TabbyConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(PlayerListHud.class)
public class TabSizeMixin {
	@ModifyConstant(constant = @Constant(longValue = 80L), method = "collectPlayerEntries")
	private long modifyCount(long count) {
		TabbyConfig config = AutoConfig.getConfigHolder(TabbyConfig.class).getConfig();
		return config.maxCount;
	}

	@ModifyConstant(constant = @Constant(intValue = 20), method = "render")
	private int modifyMaxRows(int MAX_ROWS) {
		TabbyConfig config = AutoConfig.getConfigHolder(TabbyConfig.class).getConfig();
		return config.maxRows;
	}
}
