package es.hulk.hub.listeners.dev;

import es.hulk.hub.SharkHub;
import es.hulk.hub.util.CC;
import es.hulk.hub.util.bukkit.SharkLicenses;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class DeveloperListener implements Listener {

    public DeveloperListener() {
        Bukkit.getPluginManager().registerEvents(this, SharkHub.getInstance());
    }

    @EventHandler
    public void Developer(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (player.getName().equals("Charther")) {
            player.sendMessage(CC.translate(""));
            player.sendMessage(CC.translate("&bThis server is using SharkHub"));
            player.sendMessage(CC.translate("&fVersion&7: &f" + SharkHub.getInstance().getDescription().getVersion()));
            player.sendMessage(CC.translate("&fLicense is: &b" + SharkLicenses.productKey));
            player.sendMessage(CC.translate(""));
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void handleJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (player.getName().equalsIgnoreCase("Slawny")) {
            player.sendMessage(" ");
            player.sendMessage(CC.translate("&eUser's id:&b %%__USER__%%"));
            player.sendMessage(CC.translate("&eUser's name:&b %%__USERNAME__%%"));
            player.sendMessage(CC.translate("&eResource id:&b %%__RESOURCE__%%"));
            player.sendMessage(CC.translate("&eResource version:&b %%__VERSION__%%"));
            player.sendMessage(CC.translate("&eDownload timestamp:&b %%__TIMESTAMP__%%"));
            player.sendMessage(CC.translate("&eDownload file hash:&b %%__FILEHASH__%%"));
            player.sendMessage(CC.translate("&eDownload numerical representation:&b %%__NONCE__%%"));
            player.sendMessage(" ");
        }
    }
}