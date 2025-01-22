package xyz.litewars.litewars.api.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import xyz.litewars.litewars.api.game.Game;

public class AsyncGameWaitingEvent extends Event {
    private final Game game;
    private static final HandlerList handlers = new HandlerList();

    public AsyncGameWaitingEvent(Game game){
        super(true);
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
