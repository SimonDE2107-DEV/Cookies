package de.simonde2107.cookies.util.config;

import de.simonde2107.cookies.Cookies;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class ConfigFileManager {


    public static void init() {
        File configFile = new File(Cookies.getInstance().getDataFolder(), "config.yml");
        File messagesFile = new File(Cookies.getInstance().getDataFolder(), "messages.yml");

        if (!configFile.exists()) {
            Cookies.getInstance().getLogger().info("[Cookies] config.yml nicht gefunden! Erstelle eine neue...");
            Cookies.getInstance().saveResource("config.yml", false);
        }

        if (!messagesFile.exists()) {
            Cookies.getInstance().getLogger().info("[Cookies] messages.yml nicht gefunden! Erstelle eine neue...");
            Cookies.getInstance().saveResource("messages.yml", false);
        }

        Cookies.configFile = YamlConfiguration.loadConfiguration(configFile);
        Cookies.messagesFile = YamlConfiguration.loadConfiguration(messagesFile);
    }
}

