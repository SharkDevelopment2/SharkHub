package es.hulk.hub.util.scoreboard;

import es.hulk.hub.util.scoreboard.events.ScoreboardCreateEvent;
import es.hulk.hub.util.scoreboard.events.ScoreboardDestroyEvent;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@Getter
public class ScoreboardListener implements Listener {

	private final Scoreboard assemble;

	public ScoreboardListener(Scoreboard assemble) {
		this.assemble = assemble;
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		ScoreboardCreateEvent createEvent = new ScoreboardCreateEvent(event.getPlayer());

		Bukkit.getPluginManager().callEvent(createEvent);
		if (createEvent.isCancelled()) return;

		getAssemble().getBoards().put(event.getPlayer().getUniqueId(), new ScoreboardManager(event.getPlayer(), getAssemble()));
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		ScoreboardDestroyEvent destroyEvent = new ScoreboardDestroyEvent(event.getPlayer());

		Bukkit.getPluginManager().callEvent(destroyEvent);
		if (destroyEvent.isCancelled()) {
			return;
		}

		getAssemble().getBoards().remove(event.getPlayer().getUniqueId());
		event.getPlayer().setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
	}

    public Scoreboard getAssemble() {
        return this.assemble;
    }
}
