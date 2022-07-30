package es.hulk.hub.util.scoreboard.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ScoreboardCreateEvent extends Event implements Cancellable {

    public static HandlerList handlerList;
    private final Player player;
    private boolean cancelled;
    
    public ScoreboardCreateEvent(Player player) {
        this.cancelled = false;
        this.player = player;
    }
    
    public void setCancelled(final boolean b) {
        this.cancelled = b;
    }
    
    public HandlerList getHandlers() {
        return ScoreboardCreateEvent.handlerList;
    }
    
    public Player getPlayer() {
        return this.player;
    }
    
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    public static HandlerList getHandlerList() {
        return ScoreboardCreateEvent.handlerList;
    }
    
    static {
        ScoreboardCreateEvent.handlerList = new HandlerList();
    }
}
