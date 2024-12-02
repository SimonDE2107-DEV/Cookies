package de.simonde2107.cookies.command;

import de.simonde2107.cookies.util.config.ConfigFileManager;
import de.simonde2107.cookies.util.api.LocationAPI;
import de.simonde2107.cookies.util.config.Messages;
import de.simonde2107.cookies.util.config.Settings;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class SetCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Messages.getString("strings.only_ingame"));
            return true;
        }

        if (!player.hasPermission(Settings.getString("permissions.set"))) {
            player.sendMessage(Messages.getString("strings.no_perms"));
            return true;
        }

        if (args.length != 1) {
            player.sendMessage(Messages.getString("commands.set_usage"));
            return true;
        }

        String location = args[0].toUpperCase();
        Set<String> VALID_LOCATIONS_SETCMD = Set.of("LOBBY", "ROT", "BLAU", "GRÜN", "GELB", "SCHWARZ", "ORANGE", "PINK", "LILA");
        if (VALID_LOCATIONS_SETCMD.contains(location)) {
            LocationAPI.setLocation(player, location, player.getLocation());
        } else {
            player.sendMessage(Messages.getString("commands.set_usage"));
        }
        return true;
    }
}
