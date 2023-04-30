package net.xbyz.tabby.config;

import io.wispforest.owo.config.annotation.Modmenu;

@Modmenu(modId = "tabby")
@io.wispforest.owo.config.annotation.Config(name = "tabby-config", wrapperName = "TabbyConfig")
public class Config {
    public long maxCount = 200l;
    public int maxRows = 40;
}
