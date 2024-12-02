package de.simonde2107.cookies.listener;

import de.simonde2107.cookies.util.config.ConfigFileManager;
import de.simonde2107.cookies.util.ItemBuilder;
import de.simonde2107.cookies.util.config.Messages;
import de.simonde2107.cookies.util.config.Settings;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.inventory.Inventory;

public class ItemShop implements Listener {

    Inventory inventory;

    @EventHandler
    public void onChat(PlayerChatEvent event) {
        Player player = event.getPlayer();

        if (event.getMessage().equals("shop")) {
            event.setCancelled(true);

            inventory = Bukkit.createInventory(null, 6 * 9, Messages.getString("inventory_ui.item_shop_ui_name"));

            inventory.setItem(1, new ItemBuilder(Settings.getMaterial("items.machines.material"), 1, Settings.getString("items.machines.name")).build());
            inventory.setItem(2, new ItemBuilder(Settings.getMaterial("items.fight.material"), 1, Settings.getString("items.fight.name")).build());
            inventory.setItem(3, new ItemBuilder(Material.IRON_PICKAXE, 1, "").build());
            inventory.setItem(4, new ItemBuilder(Material.GOLDEN_APPLE, 1, "").build());
            inventory.setItem(5, new ItemBuilder(Material.SANDSTONE, 1, "").build());
            inventory.setItem(6, new ItemBuilder(Material.TNT, 1, "").build());
            inventory.setItem(7, new ItemBuilder(Material.POTION, 1, "").build());

            for (int i = 9; i < 18; i++) {
                inventory.setItem(i, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE, 1, "").build());
            }

            player.openInventory(inventory);
        }
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (event.getView() != null && event.getView().getTitle() != null) {
            if (event.getCurrentItem() != null && event.getCurrentItem().getType() != Material.AIR && event.getCurrentItem().getType() != null) {
                if (event.getCurrentItem().getItemMeta() != null && event.getCurrentItem().getItemMeta().getDisplayName() != null) {

                    if (event.getView().getTitle().equals(Messages.getString("inventory_ui.item_shop_ui_name"))) {

                        if (event.getCurrentItem().getItemMeta().getDisplayName().equals(Settings.getString("items.machines.name"))) {
                            openMachinesManu(player);
                        } else if (event.getCurrentItem().getItemMeta().getDisplayName().equals(Settings.getString("items.fight.name"))) {
                            openFightsMenu(player);
                        } else if (event.getCurrentItem().getItemMeta().getDisplayName().equals(Settings.getString("items.pickaxes.name"))) {
                            openPickaxesMenu(player);
                        }

                        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 3, 3);
                    }
                }
            }
        }
    }

    void openMachinesManu(Player player) {
        for (int i = 22; i < 44; i++) {
            inventory.setItem(i, null);
        }
        inventory.setItem(22, new ItemBuilder(Settings.getMaterial("items.machines.material"), 1, Settings.getString("items.machines.name")).build());

        inventory.setItem(39, new ItemBuilder(Settings.getMaterial("items.cookie_plant.material"), 1, Settings.getString("items.cookie_plant.name")).build());
        inventory.setItem(40, new ItemBuilder(Settings.getMaterial("items.cookie_tree.material"), 1, Settings.getString("items.cookie_tree.name")).build());
        inventory.setItem(41, new ItemBuilder(Settings.getMaterial("items.cookie_furnace.material"), 1, Settings.getString("items.cookie_furnace.name")).build());

        player.updateInventory();
    }

    void openFightsMenu(Player player) {
        for (int i = 22; i < 44; i++) {
            inventory.setItem(i, null);
        }
        inventory.setItem(22, new ItemBuilder(Settings.getMaterial("items.fight.material"), 1, Settings.getString("items.fight.name")).build());

        inventory.setItem(39, new ItemBuilder(Settings.getMaterial("items.sword.material"), 1, Settings.getString("items.sword.name")).build());
        inventory.setItem(40, new ItemBuilder(Settings.getMaterial("items.bow.material"), 1, Settings.getString("items.bow.name")).build());
        inventory.setItem(41, new ItemBuilder(Settings.getMaterial("items.arrows.material"), 1, Settings.getString("items.arrows.name")).build());


        player.updateInventory();
    }

    void openPickaxesMenu(Player player) {
        for (int i = 22; i < 44; i++) {
            inventory.setItem(i, null);
        }
        inventory.setItem(22, new ItemBuilder(Settings.getMaterial("items.tools.material"), 1, Settings.getString("items.tools.name")).build());

        inventory.setItem(39, new ItemBuilder(Settings.getMaterial("items.tool-1.material"), 1, Settings.getString("items.tool-1.name")).build());
        inventory.setItem(40, new ItemBuilder(Settings.getMaterial("items.tool-2.material"), 1, Settings.getString("items.tool-2.name")).build());
        inventory.setItem(41, new ItemBuilder(Settings.getMaterial("items.tool-3.material"), 1, Settings.getString("items.tool-3.name")).build());


        player.updateInventory();
    }

}
