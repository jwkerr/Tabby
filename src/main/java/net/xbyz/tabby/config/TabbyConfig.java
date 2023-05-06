package net.xbyz.tabby.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@me.shedaniel.autoconfig.annotation.Config(name = "Tabby")
public class TabbyConfig implements ConfigData {
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
}