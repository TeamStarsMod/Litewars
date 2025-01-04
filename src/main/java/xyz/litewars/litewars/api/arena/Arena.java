package xyz.litewars.litewars.api.arena;

import org.bukkit.World;
import xyz.litewars.litewars.api.arena.interfaces.GameArena;

public class Arena implements GameArena {
    private String name;
    private ArenaStatus arenaStatus = ArenaStatus.WAITING;
    private World world;

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
}
