package xyz.litewars.litewars.game;

import org.bukkit.entity.Player;
import xyz.litewars.litewars.api.arena.Arena;
import xyz.litewars.litewars.api.game.Game;

import java.util.ArrayList;
import java.util.List;

public class GameManager implements xyz.litewars.litewars.api.game.GameManager {
    private final List<Game> runningGames = new ArrayList<>();
    @Override
    public List<Game> getRunningGames () {
        return runningGames;
    }

    @Override
    public Game newGameInstance (Arena bindArena, Player player) {
        Game re = new GameInstance(bindArena);
        re.addPlayer(player);
        re.startWaiting();
        runningGames.add(re);
        return re;
    }
}
