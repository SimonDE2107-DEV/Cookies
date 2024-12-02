package de.simonde2107.cookies.listener;

import de.simonde2107.cookies.util.config.ConfigFileManager;
import de.simonde2107.cookies.util.GameState;
import de.simonde2107.cookies.util.LobbyCounter;
import de.simonde2107.cookies.util.config.Messages;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class StatusMOTD implements Listener {

    @EventHandler
    public void onServerListPing(ServerListPingEvent event) {

        if (GameState.isLobby()) {
            event.setMaxPlayers(8);
            if (Bukkit.getOnlinePlayers().size() == 0) {
                event.setMotd(Messages.getString("join_sign_status.status_lobby_empty"));
            } else if (Bukkit.getOnlinePlayers().size() >= 1) {
                if (Bukkit.getOnlinePlayers().size() < 8) {
                    event.setMotd(Messages.getString("join_sign_status.status_lobby_filled").replace("{lobbyTimer}", LobbyCounter.countdownTime.toString()));
                } else if (Bukkit.getOnlinePlayers().size() >= 8) {
                    event.setMotd(Messages.getString("join_sign_status.status_lobby_full").replace("{lobbyTimer}", LobbyCounter.countdownTime.toString()));
                }
            }
        } else if (GameState.isIngame()) {
            event.setMotd(Messages.getString("join_sign_status.status_ingame"));
            event.setMaxPlayers(0);
        } else if (GameState.isSetup()) {
            event.setMotd(Messages.getString("join_sign_status.status_setup"));
            event.setMaxPlayers(1);
        }
    }
}
