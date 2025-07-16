package xyz.litewars.litewars.api.arena;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import xyz.litewars.litewars.LitewarsRunningData;
import xyz.litewars.litewars.api.arena.interfaces.IGameArena;
import xyz.litewars.litewars.api.arena.team.Team;
import xyz.litewars.litewars.api.game.Game;
import xyz.litewars.litewars.utils.LocationUtils;

import java.util.ArrayList;
import java.util.List;

public class Arena implements IGameArena {
    private String name;
    private ArenaStatus arenaStatus = ArenaStatus.WAITING;
    private World world;
    private ArenaGroup arenaGroup = null;
    private Game bindGame = null;
    private final YamlConfiguration yaml;
    private Location waitingLobbyLocation;
    private final List<Team> teams = new ArrayList<>();

    public Arena (String name, YamlConfiguration yaml) {
        this.name = name;
        this.yaml = yaml;
        this.world = Bukkit.getWorld(yaml.getString("World"));
        this.name = yaml.getString("Name");
        this.waitingLobbyLocation = LocationUtils.getLocation(yaml.getFloatList("Waiting"), world);// 我感觉这一块有问题
        this.arenaGroup = LitewarsRunningData.arenaGroupMap.get(yaml.getString("ArenaGroup"));
    }

    @Override
    public void setName (String name) {
        this.name = name;
    }

    @Override
    public String getName () {
        return this.name;
    }

    @Override
    public void setStatus (ArenaStatus status) {
        this.arenaStatus = status;
    }

    @Override
    public ArenaStatus getStatus () {
        return this.arenaStatus;
    }

    @Override
    public World getArenaWorld () {
        return world;
    }

    @Override
    public void setArenaWorld (World world) {
        this.world = world;
    }

    @Override
    public ArenaGroup getArenaGroup() {
        return this.arenaGroup;
    }

    @Override
    public void setArenaGroup(ArenaGroup arenaGroup) {
        this.arenaGroup = arenaGroup;
        yaml.set("ArenaGroup", arenaGroup.getName());
    }

    @Override
    public Game getGame() {
        return bindGame;
    }

    @Override
    public void setGame(Game game) {
        this.bindGame = game;
    }

    @Override
    public YamlConfiguration getYaml () {
        return yaml;
    }

    @Override
    public Location getWaitingLobbyLocation () {
        return waitingLobbyLocation;
    }

    @Override
    public void setWaitingLobbyLocation(Location location) {
        this.waitingLobbyLocation = location;
        yaml.set("Waiting", LocationUtils.getLocationList(location));
    }

    @Override
    public List<Team> getTeams() {
        return teams;
    }

    @Override
    public void removeTeam(Team team) {
        this.teams.remove(team);
    }

    @Override
    public void addTeam (Team team) {
        this.teams.add(team);
    }
}
