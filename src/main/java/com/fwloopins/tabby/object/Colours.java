package com.fwloopins.tabby.object;

import net.minecraft.util.Formatting;
import org.jetbrains.annotations.NotNull;

public enum Colours {
    DARK_RED(Formatting.DARK_RED),
    RED(Formatting.RED),
    GOLD(Formatting.GOLD),
    YELLOW(Formatting.YELLOW),
    DARK_GREEN(Formatting.DARK_GREEN),
    GREEN(Formatting.GREEN),
    AQUA(Formatting.AQUA),
    DARK_AQUA(Formatting.DARK_AQUA),
    DARK_BLUE(Formatting.DARK_BLUE),
    BLUE(Formatting.BLUE),
    LIGHT_PURPLE(Formatting.LIGHT_PURPLE),
    DARK_PURPLE(Formatting.DARK_PURPLE),
    WHITE(Formatting.WHITE),
    GRAY(Formatting.GRAY),
    DARK_GRAY(Formatting.DARK_GRAY),
    BLACK(Formatting.BLACK);

    private final Formatting formatting;

    Colours(Formatting formatting) {
        this.formatting = formatting;
    }

    @NotNull
    public Formatting formatting() {
        return this.formatting;
    }
}
