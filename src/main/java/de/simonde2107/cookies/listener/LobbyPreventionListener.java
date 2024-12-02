package de.simonde2107.cookies.listener;

import de.simonde2107.cookies.util.GameState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

public class LobbyPreventionListener implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
            event.setCancelled(GameState.isLobby());
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (GameState.isLobby()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDropItem(PlayerDropItemEvent event) {
        event.setCancelled(GameState.isLobby());
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        event.setCancelled(GameState.isLobby());
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        event.setCancelled(GameState.isLobby());
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent event) {
        event.setCancelled(GameState.isLobby());
    }

    @EventHandler
    public void onSwapHandItem(PlayerSwapHandItemsEvent event) {
        event.setCancelled(GameState.isLobby());
    }
}
