package net.xbyz.tabby.mixin;

import net.minecraft.client.gui.hud.PlayerListHud;
import net.xbyz.tabby.Tabby;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(PlayerListHud.class)
public class TabSizeMixin {
	@ModifyConstant(constant = @Constant(longValue = 80L), method = "render")
	private long modifyListLimit(long limit) {
		return Tabby.CONFIG.maxCount();
	}

	@ModifyConstant(constant = @Constant(intValue = 20), method = "render")
	private int modifyMaxRows(int MAX_ROWS) {
		return Tabby.CONFIG.maxRows();
	}
}
