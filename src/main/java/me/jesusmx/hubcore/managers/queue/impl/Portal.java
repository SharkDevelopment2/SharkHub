package me.jesusmx.hubcore.managers.queue.impl;

import me.jesusmx.hubcore.managers.queue.QueueInterface;
import me.joeleoli.portal.shared.queue.Queue;
import org.bukkit.entity.Player;

public class Portal implements QueueInterface {

    @Override
    public String getServer(Player player) {
        Queue queue = Queue.getByPlayer(player.getUniqueId());
        return queue.getName();
    }

    @Override
    public int getPosition(Player player) {
        Queue queue = Queue.getByPlayer(player.getUniqueId());
        return queue.getPosition(player.getUniqueId());
    }

    @Override
    public int getSize(Player player) {
        Queue queue = Queue.getByPlayer(player.getUniqueId());
        return queue.getPlayers().size();
    }

    @Override
    public boolean isInQueue(Player player) {
        Queue queue = Queue.getByPlayer(player.getUniqueId());
        return queue.containsPlayer(player.getUniqueId());
    }

    @Override
    public void sendPlayer(Player player, String q) {
        Queue queue = Queue.getByPlayer(player.getUniqueId());
        queue.sendPlayer(player, q);
    }
}
