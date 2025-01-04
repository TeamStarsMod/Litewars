package xyz.litewars.litewars.api.arena.interfaces;

import org.bukkit.World;
import xyz.litewars.litewars.api.arena.ArenaStatus;

public interface GameArena {
    //我想想要写什么
    void setName(String name);
    String getName();
    void setStatus (ArenaStatus status);
    ArenaStatus getStatus ();
    World getArenaWorld ();
    void setArenaWorld (World world);
}
