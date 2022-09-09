package es.hulk.hub.managers.queue;

import es.hulk.hub.SharkHub;
import es.hulk.hub.managers.queue.impl.*;
import es.hulk.hub.util.files.ConfigFile;
import lombok.Getter;
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
		} else if (Bukkit.getPluginManager().getPlugin("pxQueue") != null) {
			this.queue = "pxQueue";
			this.system = new pxQueue();
		} else if (config.getBoolean("SYSTEM.CUSTOM_QUEUE")) {
			this.queue = "Custom";
			this.system = new Custom();
		} else {
			this.queue = "None";
			this.system = new Default();
		}
	}
}
