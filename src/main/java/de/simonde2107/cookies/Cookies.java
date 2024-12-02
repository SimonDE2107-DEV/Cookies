package de.simonde2107.cookies;

import de.simonde2107.cookies.command.SetCommand;
import de.simonde2107.cookies.command.StartCommand;
import de.simonde2107.cookies.command.StatsCommand;
import de.simonde2107.cookies.listener.*;
import de.simonde2107.cookies.util.*;
import de.simonde2107.cookies.util.config.ConfigFileManager;
import de.simonde2107.cookies.util.api.LocationAPI;
import de.simonde2107.cookies.util.api.MySQL;
import de.simonde2107.cookies.util.config.Messages;
import de.simonde2107.cookies.util.config.Settings;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class Cookies extends JavaPlugin {

    public static YamlConfiguration configFile;
    public static YamlConfiguration messagesFile;

    public static HashMap<String,Integer> cookieAccount = new HashMap<>();


    static Cookies cookies;

    public static Cookies getInstance() {
        return cookies;
    }

    public static MySQL mySQL;

    static void applyGameRules() {
        Bukkit.getScheduler().runTaskLater(cookies, () -> {
            for (World world : Bukkit.getWorlds()) {
                world.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, Boolean.FALSE);
                world.setGameRule(GameRule.DO_MOB_SPAWNING, Boolean.FALSE);
                world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, Boolean.FALSE);
                world.setGameRule(GameRule.DO_WEATHER_CYCLE, Boolean.FALSE);
            }
        }, 30 * 20);


    }

    boolean allWarpsExist() {
        String[] warps = {"LOBBY", "ROT", "BLAU", "GRÜN", "GELB", "SCHWARZ", "ORANGE", "PINK", "LILA"};

        for (String warp : warps) {
            if (!LocationAPI.locationExists(warp)) {
                getLogger().info(Messages.getString("errors.location_doesnt_exist").replace("{location}", warp));
                return false;
            }
        }
        return true;
    }

    void



    initMySQL() {
        mySQL = new MySQL
                (
                        Settings.getString("database.host"),
                        Settings.getString("database.port"),
                        Settings.getString("database.database"),
                        Settings.getString("database.user"),
                        Settings.getString("database.password")
                );
        mySQL.connect();

        mySQL.executeUpdate(
                "CREATE TABLE IF NOT EXISTS player_statistics (" +
                        "uuid VARCHAR(36) PRIMARY KEY," +
                        "played_rounds INT DEFAULT 0," +
                        "won_rounds INT DEFAULT 0," +
                        "total_cookies INT DEFAULT 0," +
                        "kills INT DEFAULT 0," +
                        "deaths INT DEFAULT 0" +
                        ");"
        );
        mySQL.executeUpdate(
                "CREATE TABLE IF NOT EXISTS locations (" +
                        "name VARCHAR(64) PRIMARY KEY, " +
                        "world VARCHAR(64), " +
                        "x DOUBLE, " +
                        "y DOUBLE, " +
                        "z DOUBLE, " +
                        "yaw FLOAT, " +
                        "pitch FLOAT)"
        );
    }

    @Override
    public void onEnable() {
        cookies = this;

        ConfigFileManager.init();
        Team.init();

        initMySQL();

        if (allWarpsExist()) {
            GameState.setState(GameState.LOBBY);
        } else {
            GameState.setState(GameState.SETUP);
        }

        registerCommand();
        registerListener
                (
                        new AutoTNTIgnite(), new BuildListener(), new CookieBlocks(), new ItemShop(),
                        new LobbyFallPreventer(), new JoinListener(), new LobbyItemListener(), new LobbyPreventionListener(),
                        new LoginListener(), new PlayerDeathListener(), new QuitListener(), new StatusMOTD(),
                        new TeamChatPrefix(), new TeamSelectorClickListener()
                );
        applyGameRules();
    }

    @Override
    public void onDisable() {
        mySQL.disconnect();
        cookies = null;
    }

    void registerCommand() {
        getCommand("set").setExecutor(new SetCommand());
        getCommand("start").setExecutor(new StartCommand());
        getCommand("stats").setExecutor(new StatsCommand());
    }

    void registerListener(Listener... listeners) {
        for (Listener listener : listeners) {
            Bukkit.getPluginManager().registerEvents(listener, this);
        }
    }
}
