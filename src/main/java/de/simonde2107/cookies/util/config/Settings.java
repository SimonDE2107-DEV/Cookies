package de.simonde2107.cookies.util.config;

import de.simonde2107.cookies.Cookies;
import de.simonde2107.cookies.util.LobbyCounter;
import org.bukkit.Material;

public class Settings {

    public static Integer getInt(String path) {
        return Cookies.configFile.getInt(path);
    }

    public static String getString(String path) {
        String message = Cookies.configFile.getString(path);

        if (message == null) {
            return path + " wurde nicht gefunden";
        }

        message = message
                .replace("{usage}", Cookies.messagesFile.getString("strings.usage", ""))
                .replace("{error}", Cookies.messagesFile.getString("strings.error", ""))
                .replace("{gameName}", Cookies.messagesFile.getString("strings.gameName", ""))
                .replace("{lobbyTimer}", LobbyCounter.countdownTime.toString())
                .replace("{mapName}", Cookies.configFile.getString("settings.map_name", ""))
                .replace("{prefix}", Cookies.messagesFile.getString("strings.prefix", ""));

        // (Color-Codes)
        message = message.replaceAll("&([0-9a-fk-orA-FK-OR])", "§$1");

        return message;
    }

    public static Material getMaterial(String path) {
        String materialName = getString(path);
        try {
            return Material.valueOf(materialName);
        } catch (IllegalArgumentException e) {
            System.out.println("Ungültiger Materialname: " + materialName + ". Standardwert: AIR verwendet.");
            return Material.AIR;
        }
    }
}
