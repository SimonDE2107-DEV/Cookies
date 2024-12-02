package de.simonde2107.cookies.listener;

import de.simonde2107.cookies.util.*;
import de.simonde2107.cookies.util.api.LocationAPI;
import de.simonde2107.cookies.util.config.Messages;
import de.simonde2107.cookies.util.config.Settings;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        player.getInventory().clear();
        player.getInventory().setArmorContents(null);

        player.setExp(0);
        player.setLevel(0);

        player.setHealth(20);
        player.setFoodLevel(20);

        player.setGameMode(GameMode.SURVIVAL);

        player.setPlayerListName("§fKein Team" + "§8・§f" + player.getName());

        // GIVE LOBBY ITEMS
        player.getInventory().setItem(Settings.getInt("items.team_selector.slot"),
                new ItemBuilder(Material.WHITE_BED, 1, Settings.getString("items.team_selector.name")).build());
        player.getInventory().setItem(Settings.getInt("items.back_to_lobby.slot"),
                new ItemBuilder(Settings.getMaterial("items.back_to_lobby.material"), 1, Settings.getString("items.back_to_lobby.name")).build());
        if (player.hasPermission(Settings.getString("permissions.start"))) {
            player.getInventory().setItem(Settings.getInt("items.start.slot"),
                    new ItemBuilder(Settings.getMaterial("items.start.material"), 1, Settings.getString("items.start.name")).build());
        }

        if (GameState.isLobby() || GameState.isSetup()) {
            event.setJoinMessage(Messages.getString("join_quit_messages.join_msg").replace("{player}", player.getName()));
        }

        LocationAPI.teleportLocation(player, "LOBBY");

        if (Bukkit.getOnlinePlayers().size() == 2) {
            LobbyCounter.startCounter();
        }
    }
}
