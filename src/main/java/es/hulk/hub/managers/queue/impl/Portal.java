package es.hulk.hub.managers.queue.impl;

import es.hulk.hub.managers.queue.QueueInterface;
import es.hulk.hub.util.CC;
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
        return queue != null;
    }

    @Override
    public void sendPlayer(Player player, String q) {
        Queue queue = Queue.getByName(q);

        if (queue == null) {
            player.sendMessage(CC.translate("&cQueue '" + q + "' its offline or doesnt exist."));
            return;
        }

        queue.sendPlayer(player, q);
    }
}
