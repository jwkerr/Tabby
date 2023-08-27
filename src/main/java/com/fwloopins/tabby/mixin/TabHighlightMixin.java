package com.fwloopins.tabby.mixin;

import com.fwloopins.tabby.Tabby;
import com.fwloopins.tabby.config.DataManager;
import com.fwloopins.tabby.config.TabbyConfig;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Mixin(PlayerListHud.class)
public class TabHighlightMixin {
    @Shadow @Final private MinecraftClient client;
    @Unique TabbyConfig config = AutoConfig.getConfigHolder(TabbyConfig.class).getConfig();
    @Unique final List<String> colours = new ArrayList<>(Arrays.asList("DARK_RED", "RED", "GOLD", "YELLOW", "DARK_GREEN", "GREEN", "AQUA", "DARK_AQUA", "DARK_BLUE", "BLUE", "LIGHT_PURPLE", "DARK_PURPLE", "WHITE", "GRAY", "DARK_GRAY", "BLACK"));

    @Inject(method = "applyGameModeFormatting", at = @At("RETURN"), cancellable = true)
    private void modifyNameColour(CallbackInfoReturnable<MutableText> cir) {
        if (!config.misc.customColours)
            return;

        MutableText text = cir.getReturnValue();
        String name = text.getString();

        String uuid;
        try {
            uuid = client.player.networkHandler.getPlayerListEntry(name).getProfile().getId().toString();
        } catch (NullPointerException e) {
            uuid = "";
        }

        JsonArray jsonArray;
        try {
            jsonArray = DataManager.getCachedJson().getAsJsonArray();
        } catch (Exception e) {
            if (config.misc.debug)
                Tabby.logError("Failed to read groups.json");

            cir.setReturnValue(text);
            return;
        }

        try {
            for (JsonElement group : jsonArray) {
                JsonArray usernamesArray = group.getAsJsonObject().getAsJsonArray("usernames");

                for (JsonElement username : usernamesArray) {
                    if (username.getAsString().equals(name) || username.getAsString().equals(uuid)) {
                        JsonObject jsonObject = group.getAsJsonObject();

                        String colour = jsonObject.get("colour").getAsString();

                        boolean bold = jsonObject.get("bold").getAsBoolean();
                        boolean italic = jsonObject.get("italic").getAsBoolean();
                        boolean underline = jsonObject.get("underline").getAsBoolean();
                        boolean strikethrough = jsonObject.get("strikethrough").getAsBoolean();
                        boolean obfuscated = jsonObject.get("obfuscated").getAsBoolean();

                        if (colours.contains(colour)) {
                            cir.setReturnValue(text.setStyle(Style.EMPTY.withColor(Formatting.byName(colour)).withBold(bold).withItalic(italic).withUnderline(underline).withStrikethrough(strikethrough).withObfuscated(obfuscated)));
                        } else {
                            int rgb = Integer.parseInt(colour, 16);
                            cir.setReturnValue(text.setStyle(Style.EMPTY.withColor(rgb).withBold(bold).withItalic(italic).withUnderline(underline).withStrikethrough(strikethrough).withObfuscated(obfuscated)));
                        }
                    }
                }
            }
        } catch (Exception e) {
            if (config.misc.debug)
                Tabby.logError("Failed to parse groups.json, likely malformed JSON");

            cir.setReturnValue(text);
        }
    }
}
