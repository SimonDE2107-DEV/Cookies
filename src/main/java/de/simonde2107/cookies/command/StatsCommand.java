package de.simonde2107.cookies.command;

import de.simonde2107.cookies.util.api.PlayerStats;
import de.simonde2107.cookies.util.config.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class StatsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Messages.getString("strings.only_ingame"));
            return true;
        }

        if (args.length != 0 && args.length != 1) {
            player.sendMessage(Messages.getString("commands.stats_usage"));
            return true;
        }

        if (args[0] != null) {
            sendStats(player, player);
        } else {
            Player target = Bukkit.getPlayer(args[0]);
            // TODO: add uuidfetcher and allow checking stats of target-player who is offline

            if (target != null && target.isOnline()) {
                sendStats(player, target);
            } else {
                player.sendMessage(Messages.getString("strings.not_online"));
            }
        }
        return true;
    }

    void sendStats(Player player, Player target) {
        // TODO: If using /stats without arg, then show "Deine Statistiken".
        //  If checking stats of other player, then show "'PlayerName''s Statistiken"
        player.sendMessage("§7---------- [ §b§l" + target.getName() + "§7's §eStatistiken §7] ----------");
        player.sendMessage("§7Kills: §c" + PlayerStats.getStatistic(target, PlayerStats.StatsType.KILLS));
        player.sendMessage("§7Tode: §c" + PlayerStats.getStatistic(target, PlayerStats.StatsType.DEATHS));
        player.sendMessage("§7Insg. gesammelte Cookies: §c" + PlayerStats.getStatistic(target, PlayerStats.StatsType.TOTAL_COOKIES));
        player.sendMessage("§7Gespielte Runden: §c" + PlayerStats.getStatistic(target, PlayerStats.StatsType.PLAYED_ROUNDS));
        player.sendMessage("§7Gewonnene Runden: §c" + PlayerStats.getStatistic(target, PlayerStats.StatsType.WON_ROUNDS));
        player.sendMessage("§7--------------------");
    }
}
