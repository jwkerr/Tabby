package com.fwloopins.tabby.config;

import com.fwloopins.tabby.Tabby;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import net.fabricmc.loader.api.FabricLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class DataManager {
    private static JsonArray cachedJson = null;

    public static void initFiles() {
        Path dir = FabricLoader.getInstance().getConfigDir().resolve("Tabby");

        try {
            if (!Files.exists(dir.resolve("groups.json"))) {
                InputStream json = DataManager.class.getClassLoader().getResourceAsStream("assets/tabby/groups.json");

                if (!Files.exists(dir))
                    Files.createDirectory(dir);

                Files.copy(json, dir.resolve("groups.json"));
            }
        } catch (IOException e) {
            Tabby.logError("Failed to copy groups.json to " + dir + "\n" + e);
        }
    }

    public static void cacheJson() {
        try {
            BufferedReader reader = Files.newBufferedReader(FabricLoader.getInstance().getConfigDir().resolve("Tabby/groups.json"), StandardCharsets.UTF_8);
            JsonElement element = JsonParser.parseReader(reader);

            cachedJson = element.getAsJsonArray();
        } catch (IOException e) {
            Tabby.logError("Failed to read groups.json\n" + e);
        }
    }

    public static JsonArray getCachedJson() {
        return cachedJson;
    }
}
