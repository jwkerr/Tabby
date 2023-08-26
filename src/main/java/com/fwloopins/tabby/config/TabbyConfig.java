package com.fwloopins.tabby.config;

import com.fwloopins.tabby.object.Colours;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@me.shedaniel.autoconfig.annotation.Config(name = "Tabby")
public class TabbyConfig implements ConfigData {
    @ConfigEntry.Category("General")
    @ConfigEntry.Gui.TransitiveObject
    public General general = new General();

    @ConfigEntry.Category("Colour")
    @ConfigEntry.Gui.TransitiveObject
    public Colour colour = new Colour();

    public static class General {
        @Comment("The maximum players that can be rendered, set to 0 for the max to always be the current online player count")
        public long maxCount = 0L;
        @ConfigEntry.BoundedDiscrete(min = 1, max = 100)
        @Comment("The maximum rows that can be rendered, this value is not used if adaptive is set to true")
        public int maxRows = 40;
        @Comment("Set to true for the amount of rows to adapt based off how many players there are")
        public boolean adaptive = false;
        @ConfigEntry.BoundedDiscrete(min = 1, max = 10)
        @Comment("The amount to divide the players online by to determine how many rows will be rendered when adaptive is set to true\nFormula: x / y = maxRows\nWhere x is the value of maxCount and y is the adaptiveDivisor value")
        public int adaptiveDivisor = 5;
        @Comment("Change header to a custom string, leave blank for no change, write null for nothing to be rendered")
        public String customHeader = "";
        @Comment("Change footer to a custom string, leave blank for no change, write null for nothing to be rendered")
        public String customFooter = "";
    }

    public static class Colour {
        @Comment("Set to true to enable custom highlight colours for specific names in tab\nThis feature is currently hacked together and experimental")
        public boolean customColours = false;
        @Comment("List of names separated by a space for first colour choice")
        public String namesOne = "Fruitloopins";
        @ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.DROPDOWN)
        @Comment("Highlight colour for first list of names")
        public Colours highlightColourOne = Colours.GOLD;
        @Comment("List of names separated by a space for second colour choice")
        public String namesTwo = "";
        @ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.DROPDOWN)
        @Comment("Highlight colour for second list of names")
        public Colours highlightColourTwo = Colours.DARK_RED;
        @Comment("List of names separated by a space for third colour choice")
        public String namesThree = "";
        @ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.DROPDOWN)
        @Comment("Highlight colour for third list of names")
        public Colours highlightColourThree = Colours.DARK_GREEN;
        @Comment("List of names separated by a space for fourth colour choice")
        public String namesFour = "";
        @ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.DROPDOWN)
        @Comment("Highlight colour for fourth list of names")
        public Colours highlightColourFour = Colours.DARK_BLUE;
    }
}
