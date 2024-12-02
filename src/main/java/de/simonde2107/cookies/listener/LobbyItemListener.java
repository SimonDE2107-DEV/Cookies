package de.simonde2107.cookies.listener;

import de.simonde2107.cookies.util.*;
import de.simonde2107.cookies.util.config.ConfigFileManager;
import de.simonde2107.cookies.util.config.Messages;
import de.simonde2107.cookies.util.config.Settings;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

public class LobbyItemListener implements Listener {


    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (event.getItem() != null && event.getItem().getItemMeta() != null && event.getMaterial() != null && event.getItem().getItemMeta().getDisplayName() != null) {
            if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                if (GameState.isLobby()) {

                    if (event.getItem().getItemMeta().getDisplayName().equals(Settings.getString("items.back_to_lobby.name"))) {
                        player.kickPlayer(" ");
                    } else if (event.getItem().getItemMeta().getDisplayName().equals(Settings.getString("items.start.name"))) {
                        player.performCommand("start");
                    } else if (event.getItem().getItemMeta().getDisplayName().equals(Settings.getString("items.team_selector.name"))) {
                        openTeamSelector(player);
                    }
                }
            }
        }
    }

    void openTeamSelector(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 1 * 9, Messages.getString("inventory_ui.team_selector_ui_name"));

        for (Team team : Team.teams) {
            inventory.addItem(new ItemBuilder(team.getTeamBed(), 1, "§8» §f§lTeam " + team.getColoredName()).lore(team.getMember()).build());
        }
        inventory.addItem(new ItemBuilder(Material.BARRIER, 1, "§8» §f§lTeam §cverlassen").build());

        player.openInventory(inventory);

        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 3, 3);
    }
}
