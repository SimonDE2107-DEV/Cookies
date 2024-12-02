package de.simonde2107.cookies.util.api;

import de.simonde2107.cookies.util.config.Messages;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;

import java.util.HashMap;

public class Scoreboard {

    org.bukkit.scoreboard.Scoreboard scoreboard;
    Objective objective;

    static HashMap<String, Integer> cookieMap = new HashMap<>();

    public Scoreboard(String title) {
        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        objective = scoreboard.registerNewObjective("cookies", "dummy", title);
        objective.setDisplaySlot(org.bukkit.scoreboard.DisplaySlot.SIDEBAR);
    }

    void setScore(String lineName, int score) {
        Score line = objective.getScore(lineName);
        line.setScore(score);
    }


    public static void addCookie(Player player, int amount) {
        cookieMap.put(player.getName(), cookieMap.getOrDefault(player.getName(), 0) + amount);
    }


    static String getLineFromMessagesFile(String line, Player player, String cookies) {
        return Messages.getString("scoreboard." + line)
                .replace("{deaths}", String.valueOf(PlayerStats.getStatistic(player, PlayerStats.StatsType.DEATHS)))
                .replace("{kills}", String.valueOf(PlayerStats.getStatistic(player, PlayerStats.StatsType.KILLS)))
                .replace("{cookies}", cookies);
    }

    public static void updateBoard(Player player) {
        String cookies = cookieMap.getOrDefault(player.getName(), 0).toString();


        Scoreboard scoreboard = new Scoreboard(Messages.getString("scoreboard.title"));
        for (int i = 0; i <= 9; i++) {
            String line = getLineFromMessagesFile("line_" + (9 - i), player, cookies);
            scoreboard.setScore(line, i);
        }
        player.setScoreboard(scoreboard.scoreboard);
    }
}
