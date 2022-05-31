package es.hulk.hub.managers.queue.impl;

import es.hulk.hub.bungee.BungeeUtils;
import es.hulk.hub.managers.queue.QueueInterface;
import es.hulk.hub.util.CC;
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
        player.sendMessage(CC.translate("&cNo Queue implementation found! Disable queue boolean in your server-selector config"));
    }
}
