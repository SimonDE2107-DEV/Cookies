package de.simonde2107.cookies.listener;

import de.simonde2107.cookies.Cookies;
import de.simonde2107.cookies.util.*;
import de.simonde2107.cookies.util.config.ConfigFileManager;
import de.simonde2107.cookies.util.config.Messages;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        event.setQuitMessage(Messages.getString("join_quit_messages.quit_msg").replace("{player}", player.getName()));

        if (GameState.isLobby()) {
            Team.kickFromAllTeams(player.getName());

            // CHECK NEW PLAYER COUNT 1 SEC LATER, BECAUES " Bukkit#getOnlinePlayers()#size() " DOESNT UPDATE DIRECTLY WHEN PLAYERS LEAVES
            Bukkit.getScheduler().runTaskLater(Cookies.getInstance(), () -> {
                if (Bukkit.getOnlinePlayers().size() <= 1) {
                    LobbyCounter.stop();
                }
            }, 20);
        }
    }
}