package de.simonde2107.cookies.listener;

import de.simonde2107.cookies.util.GameState;
import de.simonde2107.cookies.util.api.LocationAPI;
import de.simonde2107.cookies.util.config.Settings;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class LobbyFallPreventer implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (GameState.isLobby()) {
            if (player.getLocation().getY() <= Settings.getInt("settings.lobby_tp_height")) {
                LocationAPI.teleportLocation(player, "LOBBY");
            }
        }
    }
}
