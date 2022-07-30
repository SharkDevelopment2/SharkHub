package es.hulk.hub.util.scoreboard.events;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
public class ScoreboardDestroyEvent extends Event implements Cancellable {

    public static HandlerList handlerList;
    private final Player player;
    private boolean cancelled;
    
    public ScoreboardDestroyEvent(Player player) {
        this.cancelled = false;
        this.player = player;
    }
    
    public void setCancelled(boolean b) {
        this.cancelled = b;
    }
    
    public HandlerList getHandlers() {
        return ScoreboardDestroyEvent.handlerList;
    }
    
    public Player getPlayer() {
        return this.player;
    }
    
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    public static HandlerList getHandlerList() {
        return ScoreboardDestroyEvent.handlerList;
    }
    
    static {
        ScoreboardDestroyEvent.handlerList = new HandlerList();
    }
}
