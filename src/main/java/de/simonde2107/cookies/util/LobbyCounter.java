package de.simonde2107.cookies.util;

import de.simonde2107.cookies.Cookies;
import de.simonde2107.cookies.util.api.LocationAPI;
import de.simonde2107.cookies.util.api.Scoreboard;
import de.simonde2107.cookies.util.api.PlayerStats;
import de.simonde2107.cookies.util.config.ConfigFileManager;
import de.simonde2107.cookies.util.config.Messages;
import de.simonde2107.cookies.util.config.Settings;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class LobbyCounter {

    public static Integer countdownTime = 31;
    static Integer taskID;

    public static void startCounter() {
        if (countdownTime != 31 || taskID != null) {
            System.out.println("LobbyCounter soll starten obwohl er bereits gestartet wurde..");
            return;
        }

        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Cookies.getInstance(), () -> {
            if (countdownTime > 0) {
                countdownTime--;
                for (Player all : Bukkit.getOnlinePlayers()) {
                    all.setLevel(countdownTime);
                }
            }

            if (countdownTime == 30 || countdownTime == 20 || countdownTime == 15 || countdownTime == 10 || countdownTime <= 5) {
                for (Player all : Bukkit.getOnlinePlayers()) {
                    // SEND MESSAGE AND TITLE AT 30,20,15,10,5,4,3,2,1
                    all.sendMessage(Messages.getString("lobby_counter.game_begins").replace("{secs}", countdownTime.toString()));
                    all.sendTitle(Messages.getString("lobby_counter.title"), Messages.getString("lobby_counter.subtitle").replace("{secs}", countdownTime.toString()), 10, 70, 20);

                    // IF COUNTER IS EQUAL OR BELOW 5 (5,4,3,2,1), PLAY DIFFERENT SOUND
                    if (countdownTime <= 5) {
                        all.playSound(all.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 3, 3);
                    } else {
                        all.playSound(all.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 3, 3);
                    }
                }
                Bukkit.getScheduler().runTaskLater(Cookies.getInstance(), () -> {
                    for (Player all : Bukkit.getOnlinePlayers()) {
                        all.resetTitle();
                    }
                }, 10);
            }

            if (countdownTime == 2) {
                for (Player all : Bukkit.getOnlinePlayers()) {

                    if (Team.getTeam(all.getName()) == null) {
                        Team.setRandomTeam(all);
                    }
                    // PREVENT PLAYERS FROM CHANGING/LEAVING TEAM "LAST MINUTE" by clearing & closing EVERYONEs INV
                    all.getInventory().clear();
                    all.closeInventory();

                }
            }

            if (countdownTime == 0) {
                // STARTS GAME
                for (Player all : Bukkit.getOnlinePlayers()) {
                    LocationAPI.teleportLocation(all, Team.getTeam(all.getName()).getName());

                    Scoreboard.updateBoard(all);

                    PlayerStats.addPlayerIfNotExists(all);

                    all.getInventory().setItem(
                                            0,
                            new ItemBuilder(Settings.getMaterial("items.default_pickaxe.material"), 1,
                                            Settings.getString("items.default_pickaxe.name")).build());
                }
                LobbyCounter.stop();
                GameState.setState(GameState.INGAME);
            }
        }, 0L, 20L);
    }

    public static void stop() {
        if (taskID != null) {
            Bukkit.getScheduler().cancelTask(taskID);
        }
        countdownTime = 31;

        for (Player all : Bukkit.getOnlinePlayers()) {
            all.setLevel(0);
        }
    }
}
