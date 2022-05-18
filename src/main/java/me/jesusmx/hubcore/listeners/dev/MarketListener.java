package me.jesusmx.hubcore.listeners.dev;

import me.jesusmx.hubcore.util.CC;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class MarketListener implements Listener {

    @EventHandler
    public void mcmarket(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (player.getName().equalsIgnoreCase("Rousing")) {
            player.sendMessage(" ");
            player.sendMessage(CC.translate("&eUser's id:&b %%__USER__%%"));
            player.sendMessage(CC.translate("&eResource id:&b %%__RESOURCE__%%"));
            player.sendMessage(" ");
        }
    }
}
