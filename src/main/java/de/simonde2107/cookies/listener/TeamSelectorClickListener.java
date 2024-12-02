package de.simonde2107.cookies.listener;

import de.simonde2107.cookies.util.ItemBuilder;
import de.simonde2107.cookies.util.Team;
import de.simonde2107.cookies.util.config.Messages;
import de.simonde2107.cookies.util.config.Settings;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class TeamSelectorClickListener implements Listener {

    @EventHandler
    public void onInvClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (!isValidClick(event)) {
            return;
        }

        if (event.getView().getTitle().equals(Messages.getString("inventory_ui.team_selector_ui_name"))) {
            Material clickedMaterial = event.getCurrentItem().getType();

            if (clickedMaterial == Material.BARRIER) {
                Team.kickFromAllTeams(player.getName());
                giveTeamSelector(player, Material.WHITE_BED);
                player.setPlayerListName("§fKein Team" + "§8・§f" + player.getName());
                player.sendMessage(Messages.getString("success_messages.team_left"));
            } else {
                for (Team team : Team.teams) {
                    if (team.getTeamBed() != null && clickedMaterial == team.getTeamBed()) {
                        addToTeam(player, team);
                        break;
                    }
                }
            }

            player.closeInventory();
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 3, 3);
        }
    }

    boolean isValidClick(InventoryClickEvent event) {
        return event.getView() != null &&
                event.getView().getTitle() != null &&
                event.getCurrentItem() != null &&
                event.getCurrentItem().getType() != Material.AIR &&
                event.getCurrentItem().getItemMeta() != null &&
                event.getCurrentItem().getItemMeta().getDisplayName() != null;
    }

    void giveTeamSelector(Player player, Material material) {
        player.getInventory().setItem(Settings.getInt("items.team_selector.slot"),
                new ItemBuilder(material, 1,
                        Settings.getString("items.team_selector.name")).build());
    }


    public static void addToTeam(Player player, Team team) {
        if (team.isEmpty()) {
            Team.kickFromAllTeams(player.getName());
            team.addMember(player.getName());

            player.sendMessage(Messages.getString("success_messages.new_team")
                    .replace("{team}", team.getColoredName()));

            player.getInventory().setItem(Settings.getInt("items.team_selector.slot"),
                    new ItemBuilder(team.getTeamBed(), 1,
                            Settings.getString("items.team_selector.name")).build());

            Bukkit.getOnlinePlayers().forEach(Player::updateInventory);
            player.setPlayerListName(team.getColoredName() + "§8・" + team.getColor() + player.getName());
        } else {
            player.sendMessage(Messages.getString("errors.team_is_full"));
        }
    }
}
