package com.fwloopins.tabby.client.mixin;

import com.fwloopins.tabby.client.TabbyClient;
import com.fwloopins.tabby.client.config.DataManager;
import com.fwloopins.tabby.client.config.TabbyConfig;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Mixin(PlayerListHud.class)
public class PlayerListHudMixin {
	@Shadow @Final private MinecraftClient client;
	@Unique final List<String> colours = new ArrayList<>(Arrays.asList("DARK_RED", "RED", "GOLD", "YELLOW", "DARK_GREEN", "GREEN", "AQUA", "DARK_AQUA", "DARK_BLUE", "BLUE", "LIGHT_PURPLE", "DARK_PURPLE", "WHITE", "GRAY", "DARK_GRAY", "BLACK"));
	@Shadow @Nullable private Text footer;
	@Shadow @Nullable private Text header;
	@Unique TabbyConfig config = AutoConfig.getConfigHolder(TabbyConfig.class).getConfig();

	@ModifyConstant(constant = @Constant(longValue = 80L), method = "collectPlayerEntries")
	private long modifyCount(long count) {
		if (config.general.maxCount <= 0 && client.getNetworkHandler() != null)
			return client.getNetworkHandler().getListedPlayerListEntries().size();

		return config.general.maxCount;
	}

	@ModifyConstant(constant = @Constant(intValue = 20), method = "render")
	private int modifyMaxRows(int MAX_ROWS) {
		if (!config.general.adaptive)
			return Math.max(1, config.general.maxRows);

		if (config.general.maxCount <= 0 && client.getNetworkHandler() != null) {
			int onlinePlayers = client.getNetworkHandler().getListedPlayerListEntries().size();
			return Math.max(1, onlinePlayers / config.general.adaptiveDivisor);
		}

		return (int) Math.max(1, config.general.maxCount / config.general.adaptiveDivisor);
	}

	@Inject(method = "applyGameModeFormatting", at = @At("HEAD"))
	private void modifyNameColour(PlayerListEntry entry, MutableText name, CallbackInfoReturnable<Text> cir) {
		if (config.misc.customColours) {
			String text = name.getString();

			String uuid;
			try {
				if (client.getNetworkHandler() == null)
					throw new NullPointerException();

				PlayerListEntry playerListEntry = client.getNetworkHandler().getPlayerListEntry(text);

				if (playerListEntry != null) {
					uuid = playerListEntry.getProfile().getId().toString();
				} else {
					throw new NullPointerException();
				}
			} catch (NullPointerException e) {
				uuid = "";
			}

			JsonArray jsonArray;
			try {
				jsonArray = DataManager.getCachedJson().getAsJsonArray();
			} catch (Exception e) {
				if (config.misc.debug)
					TabbyClient.logError("Failed to read groups.json");

				return;
			}

			try {
				for (JsonElement group : jsonArray) {
					JsonArray usernamesArray = group.getAsJsonObject().getAsJsonArray("usernames");

					for (JsonElement username : usernamesArray) {
						if (username.getAsString().equals(text) || username.getAsString().equals(uuid)) {
							JsonObject jsonObject = group.getAsJsonObject();
							Style style = getStyle(jsonObject, text);

							name.setStyle(style);
						}
					}
				}
			} catch (Exception e) {
				if (config.misc.debug)
					TabbyClient.logError("Failed to parse groups.json, likely malformed JSON");
			}
		}
	}

	@Inject(method = "setHeader", at = @At("TAIL"))
	private void setHeader(Text header, CallbackInfo ci) {
		if (!config.misc.customHeader.isEmpty()) {
			if (config.misc.customHeader.equals("null")) {
				this.header = null;
			} else {
				this.header = Text.of(config.misc.customHeader);
			}
		}
	}

	@Inject(method = "setFooter", at = @At("TAIL"))
	private void setFooter(Text footer, CallbackInfo ci) {
		if (!config.misc.customFooter.isEmpty()) {
			if (config.misc.customFooter.equals("null")) {
				this.footer = null;
			} else {
				this.footer = Text.of(config.misc.customFooter);
			}
		}
	}

	@Unique
	private Style getStyle(JsonObject jsonObject, String username) {
		Style style = Style.EMPTY;

		String colour = jsonObject.has("colour") ? jsonObject.get("colour").getAsString() : null;

		boolean bold = jsonObject.has("bold") && jsonObject.get("bold").getAsBoolean();
		boolean italic = jsonObject.has("italic") && jsonObject.get("italic").getAsBoolean();
		boolean underline = jsonObject.has("underline") && jsonObject.get("underline").getAsBoolean();
		boolean strikethrough = jsonObject.has("strikethrough") && jsonObject.get("strikethrough").getAsBoolean();
		boolean obfuscated = jsonObject.has("obfuscated") && jsonObject.get("obfuscated").getAsBoolean();

		style.withBold(bold).withItalic(italic).withUnderline(underline).withStrikethrough(strikethrough).withObfuscated(obfuscated);

		if (colour == null) {
			return style;
		}

		if (colours.contains(colour)) {
			return style.withColor(Formatting.byName(colour));
		} else {
			try {
				int rgb = Integer.parseInt(colour, 16);

				return style.withColor(rgb);
			} catch (NumberFormatException e) {
				if (config.misc.debug)
					TabbyClient.logError("Invalid value for colour found while setting style for " + username);

				return style;
			}
		}
	}
}
