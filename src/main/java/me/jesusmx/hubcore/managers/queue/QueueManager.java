package me.jesusmx.hubcore.managers.queue;

import lombok.Getter;
import me.jesusmx.hubcore.SharkHub;
import me.jesusmx.hubcore.managers.queue.impl.Custom;
import me.jesusmx.hubcore.managers.queue.impl.Default;
import me.jesusmx.hubcore.managers.queue.impl.EzQueue;
import me.jesusmx.hubcore.managers.queue.impl.Portal;
import me.jesusmx.hubcore.util.files.ConfigFile;
import org.bukkit.Bukkit;

@Getter
public class QueueManager {

	private final ConfigFile config = SharkHub.getInstance().getMainConfig();

	private String queue;
	private QueueInterface system;

	public void load() {
		if (Bukkit.getPluginManager().getPlugin("Portal") != null) {
			this.queue = "Portal";
			this.system = new Portal();
		} else if (Bukkit.getPluginManager().getPlugin("EzQueue") != null) {
			this.queue = "EzQueue";
			this.system = new EzQueue();
		} else if (config.getBoolean("SYSTEM.CUSTOM_QUEUE")) {
			this.queue = "Custom";
			this.system = new Custom();
		} else {
			this.queue = "None";
			this.system = new Default();
		}
	}
}
