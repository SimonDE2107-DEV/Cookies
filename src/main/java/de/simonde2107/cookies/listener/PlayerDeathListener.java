package de.simonde2107.cookies.listener;

import de.simonde2107.cookies.util.api.PlayerStats;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getPlayer();

        PlayerStats.addStatistic(player, PlayerStats.StatsType.DEATHS, 1);
        if (event.getPlayer().getKiller() != null) {
            Player killer = event.getPlayer().getKiller();
            PlayerStats.addStatistic(killer, PlayerStats.StatsType.KILLS, 1);
        }
    }
}
