package de.simonde2107.cookies.listener;

import de.simonde2107.cookies.util.*;
import de.simonde2107.cookies.util.config.ConfigFileManager;
import de.simonde2107.cookies.util.config.Messages;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class LoginListener implements Listener {

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        // disallow's joining while:
        // game is already running or ending
        // game is in lobby-status AND lobby-countdown is BELOW 3 Seconds.
        if (GameState.isIngame() || GameState.isEnding() || (GameState.isLobby() && LobbyCounter.countdownTime < 3)) {
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, Messages.getString("errors.game_already_started"));
        }
    }
}
