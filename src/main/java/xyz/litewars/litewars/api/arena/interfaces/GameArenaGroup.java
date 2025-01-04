package xyz.litewars.litewars.api.arena.interfaces;

import xyz.litewars.litewars.api.arena.Arena;
import xyz.litewars.litewars.api.arena.ArenaGroup;

import java.util.List;

public interface GameArenaGroup {
    void addArena (Arena arena);
    void removeArena (Arena arena);
    List<Arena> getArenas ();
    void addALL (ArenaGroup arenaGroup);
    String getName ();
    void setName (String name);
}
