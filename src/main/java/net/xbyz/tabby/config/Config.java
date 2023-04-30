package net.xbyz.tabby.config;

import io.wispforest.owo.config.annotation.Modmenu;

@Modmenu(modId = "tabby-1.19.3")
@io.wispforest.owo.config.annotation.Config(name = "tabby-config", wrapperName = "TabbyConfig")
public class Config {
    public int maxCount = 200;
    public int maxRows = 40;
}
