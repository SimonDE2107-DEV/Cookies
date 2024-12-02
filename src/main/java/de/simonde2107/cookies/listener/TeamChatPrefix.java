package de.simonde2107.cookies.listener;

import de.simonde2107.cookies.util.Team;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class TeamChatPrefix implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        Team playerTeam = Team.getTeam(player.getName());

        if (playerTeam != null) {
            event.setFormat(
                    playerTeam.getColoredName() +
                            "§8・" +
                            playerTeam.getColor() +
                            player.getName() +
                            "§8: §b" +
                            event.getMessage()
            );
        } else {
            event.setFormat(
                    "§fKein Team" +
                            "§8・" +
                            "§f" +
                            player.getName() +
                            "§8: §b" +
                            event.getMessage()
            );
        }
    }
}
