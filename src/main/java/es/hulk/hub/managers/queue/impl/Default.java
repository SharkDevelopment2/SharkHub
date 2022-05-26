package es.hulk.hub.managers.queue.impl;

import es.hulk.hub.bungee.BungeeUtils;
import es.hulk.hub.managers.queue.QueueInterface;
import org.bukkit.entity.Player;

public class Default implements QueueInterface {
    @Override
    public String getServer(Player player) {
        return "";
    }

    @Override
    public int getPosition(Player player) {
        return 0;
    }

    @Override
    public int getSize(Player player) {
        return 0;
    }

    @Override
    public boolean isInQueue(Player player) {
        return false;
    }

    @Override
    public void sendPlayer(Player player, String server) {
        BungeeUtils.sendToServer(player, server);
    }
}
