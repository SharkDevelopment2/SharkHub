package es.hulk.hub.listeners.dev;

import es.hulk.hub.SharkHub;
import es.hulk.hub.util.CC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class MarketListener implements Listener {

    public MarketListener() {
        Bukkit.getPluginManager().registerEvents(this, SharkHub.getInstance());
    }

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
