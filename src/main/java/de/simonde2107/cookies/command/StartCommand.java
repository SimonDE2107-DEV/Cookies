package de.simonde2107.cookies.command;

import de.simonde2107.cookies.util.config.ConfigFileManager;
import de.simonde2107.cookies.util.GameState;
import de.simonde2107.cookies.util.LobbyCounter;
import de.simonde2107.cookies.util.config.Messages;
import de.simonde2107.cookies.util.config.Settings;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StartCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Messages.getString("strings.only_ingame"));
            return true;
        }

        if (!player.hasPermission(Settings.getString("permissions.start"))) {
            player.sendMessage(Messages.getString("strings.no_perms"));
            return true;
        }

        if (!GameState.isLobby()) {
            if (GameState.isSetup()) {
                player.sendMessage(Messages.getString("errors.game_needs_to_be_set_up"));
            } else {
                player.sendMessage(Messages.getString("errors.game_already_started"));
            }
            return true;
        }

        if (args.length == 0) {
            handleNormalStart(player);
        } else if (args.length == 1 && args[0].equalsIgnoreCase("force")) {
            // force start the game while being the only player online. Used for dev-testing purpurses
            handleForceStart(player);
        } else {
            player.sendMessage(Messages.getString("commands.start_usage"));
        }

        return true;
    }

    private void handleNormalStart(Player player) {
        int onlinePlayers = Bukkit.getOnlinePlayers().size();

        if (onlinePlayers >= 2) {
            if (LobbyCounter.countdownTime >= 12) {
                LobbyCounter.countdownTime = 11;
                player.sendMessage(Messages.getString("success_messages.countdown_reduced"));
            } else {
                player.sendMessage(Messages.getString("errors.game_is_already_starting"));
            }
        } else {
            player.sendMessage(Messages.getString("errors.not_enough_players_online"));
        }
    }

    private void handleForceStart(Player player) {
        if (!player.hasPermission(Settings.getString("permissions.start_force"))) {
            player.sendMessage(Messages.getString("strings.no_perms"));
            return;
        }

        int onlinePlayers = Bukkit.getOnlinePlayers().size();

        if (onlinePlayers > 1) {
            player.sendMessage(Messages.getString("errors.start_force_unnecessary"));
            return;
        }

        if (LobbyCounter.countdownTime >= 4) {
            LobbyCounter.startCounter();
            LobbyCounter.countdownTime = 3;
            player.sendMessage(Messages.getString("success_messages.countdown_reduced_forcefully"));
        } else {
            player.sendMessage(Messages.getString("errors.game_is_already_starting"));
        }
    }
}
