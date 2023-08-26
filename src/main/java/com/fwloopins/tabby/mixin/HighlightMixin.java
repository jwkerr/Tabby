package com.fwloopins.tabby.mixin;

import com.fwloopins.tabby.config.TabbyConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.text.MutableText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Mixin(PlayerListHud.class)
public class HighlightMixin {
    @Unique
    TabbyConfig config = AutoConfig.getConfigHolder(TabbyConfig.class).getConfig();

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
