package net.xbyz.tabby.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@me.shedaniel.autoconfig.annotation.Config(name = "Tabby")
public class TabbyConfig implements ConfigData {
    @Comment("The maximum players that can be rendered")
    public long maxCount = 200l;
    @Comment("The maximum rows that can be rendered")
    public int maxRows = 40;
}