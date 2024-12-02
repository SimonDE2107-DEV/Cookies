package de.simonde2107.cookies.util.api;

import de.simonde2107.cookies.Cookies;
import org.bukkit.entity.Player;

public class CookiesAccount {

    public static int getCookies(Player player) {
        String uuid = player.getUniqueId().toString().toLowerCase();
        return Cookies.cookieAccount.getOrDefault(uuid, 0);
    }

    public static void addCookies(Player player, int value) {
        String uuid = player.getUniqueId().toString().toLowerCase();
        int currentCookies = getCookies(player);
        Cookies.cookieAccount.put(uuid, currentCookies + value);

        PlayerStats.addStatistic(player, PlayerStats.StatsType.TOTAL_COOKIES, value);
        Scoreboard.updateBoard(player);
    }
}
