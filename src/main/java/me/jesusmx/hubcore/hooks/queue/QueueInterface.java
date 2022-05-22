package me.jesusmx.hubcore.hooks.queue;

import org.bukkit.entity.Player;

public interface QueueInterface {

    String getServer(Player player);
    int getPosition(Player player);
    int getSize(Player player);
    boolean isInQueue(Player player);
    void sendPlayer(Player player, String queueName);
}
