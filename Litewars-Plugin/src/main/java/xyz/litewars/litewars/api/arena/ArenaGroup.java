package xyz.litewars.litewars.api.arena;

import xyz.litewars.litewars.api.arena.interfaces.GameArenaGroup;
import java.util.ArrayList;
import java.util.List;

public class ArenaGroup implements GameArenaGroup {
    private final List<Arena> arenas = new ArrayList<>();
    private String name;

    public ArenaGroup(String name) {
        this.name = name;
    }

    @Override
    public void addArena (Arena arena) {
        this.arenas.add(arena);
    }

    @Override
    public void removeArena (Arena arena) {
        this.arenas.remove(arena);
    }

    @Override
    public List<Arena> getArenas () {
        return this.arenas;
    }

    @Override
    public void addALL (ArenaGroup arenaGroup) {
        this.arenas.addAll(arenaGroup.arenas);
    }

    @Override
    public String getName () {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}
