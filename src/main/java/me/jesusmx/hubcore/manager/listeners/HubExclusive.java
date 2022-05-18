package me.jesusmx.hubcore.manager.listeners;

import me.jesusmx.hubcore.SharkHub;
import me.jesusmx.hubcore.bungee.BungeeUtils;
import me.jesusmx.hubcore.util.files.ConfigFile;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class HubExclusive implements Listener {

    private final ConfigFile config = SharkHub.getInstance().getMainConfig();
    private final ConfigFile toggle = SharkHub.getInstance().getTogglesConfig();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        // Restricted hub, no is a item but xd
        if (toggle.getBoolean("addons.restricted-hub")) {
            if (player.hasPermission("hubcore.send-hub")) {
                String server = config.getString("restricted-hub.bungee-name");
                BungeeUtils.sendToServer(player, server);
            }
        }
    }
}
