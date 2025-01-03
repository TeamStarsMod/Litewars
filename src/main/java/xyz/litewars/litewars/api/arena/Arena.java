package xyz.litewars.litewars.api.arena;

import org.bukkit.entity.Player;
import xyz.litewars.litewars.api.arena.interfaces.GameArena;

import java.util.ArrayList;
import java.util.List;

public class Arena implements GameArena {
    private String name;
    private final List<Player> players = new ArrayList<>();
    private ArenaStatus arenaStatus = ArenaStatus.WAITING;

    public Arena (String name) {
        this.name = name;
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
    public void addPlayer (Player player) {
        this.players.add(player);
    }

    @Override
    public void removePlayer (Player player) {
        this.players.remove(player);
    }

    @Override
    public List<Player> getPlayers () {
        return this.players;
    }

    @Override
    public void setStatus (ArenaStatus status) {
        this.arenaStatus = status;
    }

    @Override
    public ArenaStatus getStatus () {
        return this.arenaStatus;
    }
}
