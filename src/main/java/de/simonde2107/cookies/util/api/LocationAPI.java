package de.simonde2107.cookies.util.api;

import de.simonde2107.cookies.Cookies;
import de.simonde2107.cookies.util.config.ConfigFileManager;
import de.simonde2107.cookies.util.config.Messages;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LocationAPI {

    public static void teleportLocation(Player player, String locationName) {
        locationName = locationName.toLowerCase();
        if (locationExists(locationName)) {
            player.teleport(getLocation(locationName));
            player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0F, 1.0F);
            player.playEffect(player.getLocation(), Effect.ENDER_SIGNAL, 3);
        } else {
            player.sendMessage(Messages.getString("errors.location_doesnt_exist")
                    .replace("{location}", locationName));
        }
    }

    public static Location getLocation(String locationName) {
        locationName = locationName.toLowerCase();
        ResultSet resultSet = Cookies.mySQL.executeQuery(
                "SELECT world, x, y, z, yaw, pitch FROM locations WHERE name = ?", locationName);

        try {
            if (resultSet.next()) {
                String world = resultSet.getString("world");
                double x = resultSet.getDouble("x");
                double y = resultSet.getDouble("y");
                double z = resultSet.getDouble("z");
                float yaw = resultSet.getFloat("yaw");
                float pitch = resultSet.getFloat("pitch");

                Location location = new Location(Bukkit.getWorld(world), x, y, z);
                location.setYaw(yaw);
                location.setPitch(pitch);
                return location;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void setLocation(Player player, String locationName, Location location) {
        locationName = locationName.toLowerCase();

        if (!locationExists(locationName)) {
            Cookies.mySQL.executeUpdate(
                    "INSERT INTO locations (name, world, x, y, z, yaw, pitch) VALUES (?, ?, ?, ?, ?, ?, ?)",
                    locationName, location.getWorld().getName(),
                    location.getX(), location.getY(), location.getZ(),
                    location.getYaw(), location.getPitch()
            );

            player.sendMessage(Messages.getString("success_messages.location_successfully_set")
                    .replace("{location}", locationName));
        } else {
            player.sendMessage(Messages.getString("errors.location_already_exists")
                    .replace("{location}", locationName));
        }
    }

    public static boolean locationExists(String locationName) {
        locationName = locationName.toLowerCase();
        ResultSet resultSet = Cookies.mySQL.executeQuery("SELECT 1 FROM locations WHERE name = ?", locationName);

        try {
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
