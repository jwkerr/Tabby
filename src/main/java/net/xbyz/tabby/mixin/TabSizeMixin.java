package net.xbyz.tabby.mixin;

import net.minecraft.client.gui.hud.PlayerListHud;
import net.xbyz.tabby.Tabby;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(PlayerListHud.class)
public class TabSizeMixin {
	@ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Ljava/lang/Math;min(II)I", ordinal = 0), index = 1)
	private int modifyCount(int count) {
		return Tabby.CONFIG.maxCount();
	}

	@ModifyConstant(constant = @Constant(intValue = 20), method = "render")
	private int modifyMaxRows(int MAX_ROWS) {
		return Tabby.CONFIG.maxRows();
	}
}
