package es.hulk.hub.util.scoreboard;

import es.hulk.hub.util.scoreboard.events.ScoreboardCreatedEvent;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class ScoreboardManager {

	private final List<ScoreboardEntry> entries = new ArrayList<>();
	private final List<String> identifiers = new ArrayList<>();
	private final UUID uuid;

	private org.bukkit.scoreboard.Scoreboard scoreboard;
	private Objective objective;
	private Scoreboard assemble;

	public ScoreboardManager(Player player, Scoreboard assemble) {
		this.uuid = player.getUniqueId();
		this.assemble = assemble;
		this.setup(player);
		this.assemble = assemble;

	}

	public org.bukkit.scoreboard.Scoreboard getScoreboard() {
		Player player = Bukkit.getPlayer(getUuid());
		if (getAssemble().isHook() || player.getScoreboard() != Bukkit.getScoreboardManager().getMainScoreboard()) {
			return player.getScoreboard();
		} else {
			return Bukkit.getScoreboardManager().getNewScoreboard();
		}
	}

	public Objective getObjective() {
		org.bukkit.scoreboard.Scoreboard scoreboard = getScoreboard();
		if (scoreboard.getObjective("Scoreboard") == null) {
			Objective objective = scoreboard.registerNewObjective("Scoreboard", "dummy");
			objective.setDisplaySlot(DisplaySlot.SIDEBAR);
			objective.setDisplayName(getAssemble().getAdapter().getTitle(Bukkit.getPlayer(getUuid())));
			return objective;
		} else {
			return scoreboard.getObjective("Scoreboard");
		}
	}


	private void setup(Player player) {
		org.bukkit.scoreboard.Scoreboard scoreboard = getScoreboard();
		player.setScoreboard(scoreboard);
		getObjective();

		//Send Update
		ScoreboardCreatedEvent createdEvent = new ScoreboardCreatedEvent(this);
		Bukkit.getPluginManager().callEvent(createdEvent);
	}

	public ScoreboardEntry getEntryAtPosition(int pos) {
		if (pos >= this.entries.size()) {
			return null;
		} else {
			return this.entries.get(pos);
		}
	}

	public String getUniqueIdentifier(int position) {
		String identifier = getRandomChatColor(position) + ChatColor.WHITE;

		while (this.identifiers.contains(identifier)) {
			identifier = identifier + getRandomChatColor(position) + ChatColor.WHITE;
		}

		// This is rare, but just in case, make the method recursive
		if (identifier.length() > 16) {
			return this.getUniqueIdentifier(position);
		}

		// Add our identifier to the list so there are no duplicates
		this.identifiers.add(identifier);

		return identifier;
	}

	// Gets a random ChatColor and returns the String value of it
	private static String getRandomChatColor(int position) {
		return ChatColor.values()[position].toString();
	}

    public List<ScoreboardEntry> getEntries() {
        return this.entries;
    }
    
    public List<String> getIdentifiers() {
        return this.identifiers;
    }
    
    public UUID getUuid() {
        return this.uuid;
    }
    
    public Scoreboard getAssemble() {
        return this.assemble;
    }
}

