package com.fwloopins.tabby.client.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import org.lwjgl.glfw.GLFW;

@me.shedaniel.autoconfig.annotation.Config(name = "Tabby")
public class TabbyConfig implements ConfigData {
    @ConfigEntry.Category("General")
    @ConfigEntry.Gui.TransitiveObject
    public General general = new General();

    @ConfigEntry.Category("Misc")
    @ConfigEntry.Gui.TransitiveObject
    public Misc misc = new Misc();

    public static class General {
        @ConfigEntry.Gui.Tooltip
        public long maxCount = 0L;
        @ConfigEntry.BoundedDiscrete(min = 1, max = 100)
        @ConfigEntry.Gui.Tooltip
        public int maxRows = 40;
        @ConfigEntry.Gui.Tooltip
        public boolean adaptive = false;
        @ConfigEntry.BoundedDiscrete(min = 1, max = 10)
        @ConfigEntry.Gui.Tooltip(count = 3)
        public int adaptiveDivisor = 5;
    }

    public static class Misc {
        @ConfigEntry.Gui.Tooltip(count = 2)
        public boolean customColours = false;
        @ConfigEntry.Gui.Tooltip
        public String customHeader = "";
        @ConfigEntry.Gui.Tooltip
        public String customFooter = "";
        @ConfigEntry.Gui.Tooltip
        public int reloadKey = GLFW.GLFW_KEY_F9;
        @ConfigEntry.Gui.Tooltip(count = 2)
        public boolean debug = false;
    }
}
