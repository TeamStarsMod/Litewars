package xyz.litewars.litewars.game;

import xyz.litewars.litewars.api.game.GameManager;
import xyz.litewars.litewars.api.game.Game;

import java.util.ArrayList;
import java.util.List;

public class SimpleGameManager implements GameManager {
    private final List<Game> runningGames = new ArrayList<>();
    @Override
    public Game[] getRunningGames () {
        return runningGames.toArray(new Game[0]);
    }

    @Override
    public Game newGameInstance () {
        Game re = new SimpleGameInstance();
        re.startWaiting();
        runningGames.add(re);
        return re;
    }
}
