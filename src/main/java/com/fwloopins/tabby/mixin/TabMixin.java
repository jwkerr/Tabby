package com.fwloopins.tabby.mixin;

import com.fwloopins.tabby.config.TabbyConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.text.MutableText;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Mixin(PlayerListHud.class)
public class TabMixin {
	@Shadow @Final private MinecraftClient client;
	@Unique
	TabbyConfig config = AutoConfig.getConfigHolder(TabbyConfig.class).getConfig();

	@ModifyConstant(constant = @Constant(longValue = 80L), method = "collectPlayerEntries")
	private long modifyCount(long count) {
		if (config.general.maxCount <= 0) {
			return client.player.networkHandler.getListedPlayerListEntries().size();
		}
		return config.general.maxCount;
	}

	@ModifyConstant(constant = @Constant(intValue = 20), method = "render")
	private int modifyMaxRows(int MAX_ROWS) {
		if (config.general.adaptive) {
			if (config.general.maxCount <= 0) {
				int onlinePlayers = client.player.networkHandler.getListedPlayerListEntries().size();
				return Math.max(1, onlinePlayers / config.general.adaptiveDivisor);
			}
			return (int) Math.max(1, config.general.maxCount / config.general.adaptiveDivisor);
		}
		return Math.max(1, config.general.maxRows);
	}

	// Hacky clusterfuck
	@Inject(method = "applyGameModeFormatting", at = @At("RETURN"), cancellable = true)
	private void modifyNameColour(CallbackInfoReturnable<MutableText> cir) {
		if (config.colour.customColours) {
			MutableText text = cir.getReturnValue();
			String[] splitOne = config.colour.namesOne.split(" ");
			String[] splitTwo = config.colour.namesTwo.split(" ");
			String[] splitThree = config.colour.namesThree.split(" ");
			String[] splitFour = config.colour.namesFour.split(" ");
			List<String> nameListOne = new ArrayList<>(Arrays.asList(splitOne));
			List<String> nameListTwo = new ArrayList<>(Arrays.asList(splitTwo));
			List<String> nameListThree = new ArrayList<>(Arrays.asList(splitThree));
			List<String> nameListFour = new ArrayList<>(Arrays.asList(splitFour));

			if (nameListOne.contains(text.getString())) {
				cir.setReturnValue(text.formatted(config.colour.highlightColourOne.formatting()));
			} else if (nameListTwo.contains(text.getString())) {
				cir.setReturnValue(text.formatted(config.colour.highlightColourTwo.formatting()));
			} else if (nameListThree.contains(text.getString())) {
				cir.setReturnValue(text.formatted(config.colour.highlightColourThree.formatting()));
			} else if (nameListFour.contains(text.getString())) {
				cir.setReturnValue(text.formatted(config.colour.highlightColourFour.formatting()));
			}
		}
	}
}
