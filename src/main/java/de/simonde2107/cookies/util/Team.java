package de.simonde2107.cookies.util;

import de.simonde2107.cookies.listener.TeamSelectorClickListener;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;

public class Team {

    public static Team ROT;
    public static Team BLAU;
    public static Team GRUEN;
    public static Team GELB;
    public static Team PINK;
    public static Team LILA;
    public static Team SCHWARZ;
    public static Team ORANGE;

    public static ArrayList<Team> teams = new ArrayList<>();

    public static void init() {
        ROT = new Team("Rot", "§4§l", Material.RED_BED);
        BLAU = new Team("Blau", "§9§l", Material.BLUE_BED);
        GRUEN = new Team("Grün", "§a§l", Material.GREEN_BED);
        GELB = new Team("Gelb", "§e§l", Material.YELLOW_BED);
        PINK = new Team("Pink", "§d§l", Material.PINK_BED);
        LILA = new Team("Lila", "§5§l", Material.MAGENTA_BED);
        SCHWARZ = new Team("Schwarz", "§8§l", Material.BLACK_BED);
        ORANGE = new Team("Orange", "§6§l", Material.ORANGE_BED);

        teams.add(ROT);
        teams.add(BLAU);
        teams.add(GRUEN);
        teams.add(GELB);
        teams.add(SCHWARZ);
        teams.add(ORANGE);
        teams.add(PINK);
        teams.add(LILA);
    }

    public static void kickFromAllTeams(String player) {
        for (Team team : teams) {
            if (team.isMember(player)) {
                team.removePlayer();
            }
        }
    }


    public static void setRandomTeam(Player player) {
        // SHUFFLE TEAMS SO THAT THE RANDOM TEAM IS NOT ALWAYS RED, THEN BLUE, ...
        Collections.shuffle(teams);
        for (Team team : teams) {
            if (team.isEmpty()) {
                TeamSelectorClickListener.addToTeam(player, team);
                break;
            }
        }
    }

    public static Team getTeam(String player) {
        for (Team team : teams) {
            if (team.isMember(player)) {
                return team;
            }
        }

        return null;
    }


    String name;
    String colorCode;
    String member;
    Material teamBed;


    public Team(String name, String color, Material teamBed) {
        this.name = name;
        this.colorCode = color;
        this.member = "";
        this.teamBed = teamBed;
    }

    public String getColor() {
        return colorCode;
    }

    public Material getTeamBed() {
        return teamBed;
    }

    public String getName() {
        return name;
    }

    public String getColoredName() {
        return colorCode + name;
    }


    public String getMember() {
        return isEmpty() ? "Team ist leer" : member;
    }

    public void addMember(String player) {
        member = player;
    }

    public void removePlayer() {
        member = "";
    }

    public boolean isEmpty() {
        return member.isEmpty();
    }

    public boolean isMember(String player) {
        return member.equals(player);
    }
}
