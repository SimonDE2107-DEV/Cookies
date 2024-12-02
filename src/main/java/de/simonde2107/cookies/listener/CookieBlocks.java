package de.simonde2107.cookies.listener;

import de.simonde2107.cookies.util.api.CookiesAccount;
import de.simonde2107.cookies.util.ItemBuilder;
import de.simonde2107.cookies.util.api.PlayerStats;
import de.simonde2107.cookies.util.api.Scoreboard;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class CookieBlocks implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if (event.getBlock().getType() == Material.GOLD_BLOCK) {
            event.setCancelled(true);

            Block block = event.getBlock();
            Location blockLocation = block.getLocation();

            event.getBlock().getWorld().dropItem(blockLocation.add(0, 1, 0), new ItemBuilder(Material.COOKIE, 1, "").build());
        }
    }

    @EventHandler
    public void onItemPickup(PlayerPickupItemEvent event) {
        Player player = event.getPlayer();

        if (event.getItem().getItemStack().getType() == Material.COOKIE) {
            event.setCancelled(true);

            int collectedCookies = event.getItem().getItemStack().getAmount();

            CookiesAccount.addCookies(player, collectedCookies);
            Scoreboard.addCookie(player, collectedCookies);
            PlayerStats.addStatistic(player, PlayerStats.StatsType.TOTAL_COOKIES, collectedCookies);

            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 3, 3);

            event.getItem().remove();
        }
    }
}
