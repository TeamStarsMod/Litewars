package xyz.litewars.litewars.api.arena.interfaces;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import xyz.litewars.litewars.api.arena.ArenaGroup;
import xyz.litewars.litewars.api.arena.ArenaStatus;
import xyz.litewars.litewars.api.arena.team.Team;
import xyz.litewars.litewars.api.game.Game;

import java.util.List;

public interface IGameArena {
    void setName(String name);
    String getName();
    void setStatus (ArenaStatus status);
    ArenaStatus getStatus ();
    World getArenaWorld ();
    void setArenaWorld (World world);
    ArenaGroup getArenaGroup ();
    void setArenaGroup (ArenaGroup arenaGroup);
    Game getGame();
    void setGame(Game game);
    YamlConfiguration getYaml ();
    Location getWaitingLobbyLocation ();
    void setWaitingLobbyLocation (Location location);
    List<Team> getTeams();
    void addTeam (Team team);
    void removeTeam (Team team);
}
