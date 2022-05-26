package es.hulk.hub.managers.queue.impl;

import es.hulk.hub.managers.queue.QueueInterface;
import me.signatured.ezqueuespigot.EzQueueAPI;
import org.bukkit.entity.Player;

public class EzQueue implements QueueInterface {

    @Override
    public String getServer(Player player) {
        return EzQueueAPI.getQueue(player.getUniqueId());
    }

    @Override
    public int getPosition(Player player) {
        return EzQueueAPI.getPosition(player.getUniqueId());
    }

    @Override
    public int getSize(Player player) {
        return EzQueueAPI.getQueueSize(getServer(player));
    }

    @Override
    public boolean isInQueue(Player player) {
        return EzQueueAPI.getQueue(player.getUniqueId()) != null;
    }

    @Override
    public void sendPlayer(Player p0, String p1) {
        EzQueueAPI.addToQueue(p0, p1);
    }
}
