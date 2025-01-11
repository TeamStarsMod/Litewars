package xyz.litewars.litewars.api.arena;

import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import xyz.litewars.litewars.api.arena.interfaces.GameArena;
import xyz.litewars.litewars.api.game.Game;

public class Arena implements GameArena {
    private String name;
    private ArenaStatus arenaStatus = ArenaStatus.WAITING;
    private World world;
    private ArenaGroup arenaGroup;
    private Game bindGame;
    private YamlConfiguration yaml;

    public Arena (String name, YamlConfiguration yaml) {
        this.name = name;
        this.yaml = yaml;
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
}
