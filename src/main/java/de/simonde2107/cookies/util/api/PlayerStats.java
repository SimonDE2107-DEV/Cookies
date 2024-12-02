package de.simonde2107.cookies.util.api;

import de.simonde2107.cookies.Cookies;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;


public class PlayerStats {

    public static int getStatistic(Player player, StatsType statsType) {
        String uuid = player.getUniqueId().toString().toLowerCase();
        ResultSet resultSet = Cookies.mySQL.executeQuery(
                "SELECT " + statsType.getColumnName() + " FROM player_statistics WHERE uuid = ?",
                uuid
        );
        try {
            if (resultSet.next()) {
                return resultSet.getInt(statsType.getColumnName());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void addStatistic(Player player, StatsType statsType, int value) {
        String uuid = player.getUniqueId().toString().toLowerCase();
        int currentValue = getStatistic(player, statsType);
        int newValue = currentValue + value;

        Cookies.mySQL.executeUpdate(
                "INSERT INTO player_statistics (uuid, " + statsType.getColumnName() + ") VALUES (?, ?) " +
                        "ON DUPLICATE KEY UPDATE " + statsType.getColumnName() + " = ?",
                uuid, newValue, newValue
        );
        Scoreboard.updateBoard(player);
    }

    public static void addPlayerIfNotExists(Player player) {
        String uuid = player.getUniqueId().toString().toLowerCase();
        if (!playerExists(player)) {

            Cookies.mySQL.executeUpdate(
                    "INSERT INTO player_statistics (uuid) VALUES (?)",
                    uuid
            );

        }
    }

    public static boolean playerExists(Player player) {
        String uuid = player.getUniqueId().toString().toLowerCase();
        ResultSet resultSet = Cookies.mySQL.executeQuery("SELECT 1 FROM player_statistics WHERE uuid = ?", uuid);
        try {
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public enum StatsType {
        PLAYED_ROUNDS("played_rounds"),
        WON_ROUNDS("won_rounds"),
        TOTAL_COOKIES("total_cookies"),
        KILLS("kills"),
        DEATHS("deaths");

        private final String columnName;

        StatsType(String columnName) {
            this.columnName = columnName;
        }

        public String getColumnName() {
            return columnName;
        }
    }
}
