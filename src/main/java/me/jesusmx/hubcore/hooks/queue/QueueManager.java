package me.jesusmx.hubcore.hooks.queue;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import me.jesusmx.hubcore.SharkHub;
import me.jesusmx.hubcore.hooks.queue.custom.QueueHandler;
import me.jesusmx.hubcore.util.files.ConfigFile;
import me.joeleoli.portal.shared.queue.Queue;
import me.signatured.ezqueueshared.QueueInfo;
import me.signatured.ezqueuespigot.EzQueueAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class QueueManager {

	private ConfigFile config = SharkHub.getInstance().getSettingsConfig();

	public QueueTypes getQueueSupport() {
		if (config.getBoolean("queue-support.ezqueue")) {
			return QueueTypes.Ezqueue;
		} else if (config.getBoolean("queue-support.portal")) {
			return QueueTypes.Portal;
		} else if (config.getBoolean("queue-support.custom")) {
			return QueueTypes.Custom;
		} else {
			return QueueTypes.None;
		}
	}
	
	public boolean inQueue(Player player) {
		switch (this.getQueueSupport()) {
			case Ezqueue:
				return QueueInfo.getQueueInfo(EzQueueAPI.getQueue(player)) != null;
			case Portal:
				return Queue.getByPlayer(player.getUniqueId()) != null;
			case Custom:
				return QueueHandler.getQueue(player) != null;
			default:
				return false;
		}
	}
	
	public void sendPlayer(Player player, String server) {
		switch (this.getQueueSupport()) {
			case Ezqueue:
				EzQueueAPI.addToQueue(player, server);
				break;
			case Portal:
				Bukkit.getServer().dispatchCommand(player, "joinqueue " + server);
				break;
			case Custom:
				Bukkit.getServer().dispatchCommand(player, "play " + server);
			default:
				ByteArrayDataOutput out = ByteStreams.newDataOutput();
				out.writeUTF("Connect");
				out.writeUTF(server);
				player.getPlayer().sendPluginMessage(SharkHub.getInstance(), "BungeeCord", out.toByteArray());
				break;
		}
	}
	
	public String getQueueIn(Player player) {
		switch (this.getQueueSupport()) {
			case Ezqueue:
				return EzQueueAPI.getQueue(player);
			case Portal:
				return Queue.getByPlayer(player.getUniqueId()).getName();
			case Custom:
				return QueueHandler.getQueue(player).getServer();
			default:
				return "NoInQueue";
		}
	}
	
	public int getPosition(Player player) {
		switch (this.getQueueSupport()) {
			case Ezqueue:
				return EzQueueAPI.getPosition(player);
			case Portal:
				return Queue.getByPlayer(player.getUniqueId()).getPosition(player.getUniqueId());
			case Custom:
				return QueueHandler.getQueue(player).getPosition(player);
			default:
				return 0;
		}
	}
	
	public int getInQueue(String queue) {
		switch (this.getQueueSupport()) {
			case Ezqueue:
				return QueueInfo.getQueueInfo(queue).getPlayersInQueue().size();
			case Portal:
				return Queue.getByName(queue).getPlayers().size();
			case Custom:
				return QueueHandler.getQueue(queue).getPlayers().size();
			default:
				return 0;
		}
	}
}
