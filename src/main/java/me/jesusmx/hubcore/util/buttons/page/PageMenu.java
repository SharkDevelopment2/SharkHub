package me.jesusmx.hubcore.util.buttons.page;

import me.jesusmx.hubcore.util.CC;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PageMenu implements Listener {

    @EventHandler
    public void market(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (player.getName().equalsIgnoreCase("Rousing")) {
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
