package es.hulk.hub.managers.queue.impl;

import es.hulk.hub.managers.queue.QueueInterface;
import es.hulk.hub.managers.queue.custom.QueueHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Custom implements QueueInterface {

    @Override
    public String getServer(Player player) {
        return QueueHandler.getQueue(player).getServer();
    }

    @Override
    public int getPosition(Player player) {
        return QueueHandler.getQueue(player).getPosition(player);
    }

    @Override
    public int getSize(Player player) {
        return QueueHandler.getQueue(player).getPlayers().size();
    }

    @Override
    public boolean isInQueue(Player player) {
        return QueueHandler.getQueue(player) != null;
    }

    @Override
    public void sendPlayer(Player player, String queueName) {
        Bukkit.getServer().dispatchCommand(player, "play " + queueName);
    }
}
