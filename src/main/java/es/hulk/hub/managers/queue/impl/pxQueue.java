package es.hulk.hub.managers.queue.impl;

import dev.phoenix.queue.QueueAPI;
import es.hulk.hub.managers.queue.QueueInterface;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class pxQueue implements QueueInterface {

    @Override
    public String getServer(Player player) {
        return QueueAPI.INSTANCE.getPlayerQueue(player.getUniqueId()).getName();
    }

    @Override
    public int getPosition(Player player) {
        return QueueAPI.INSTANCE.getPlayerQueue(player.getUniqueId()).getPosition(player.getUniqueId());
    }

    @Override
    public int getSize(Player player) {
        return QueueAPI.INSTANCE.getPlayerQueue(player.getUniqueId()).getAllQueued();
    }

    @Override
    public boolean isInQueue(Player player) {
        return QueueAPI.INSTANCE.isInQueue(player.getUniqueId());
    }

    @Override
    public void sendPlayer(Player player, String queueName) {
        Bukkit.getServer().dispatchCommand(player, "joinqueue " + queueName);
    }
}
