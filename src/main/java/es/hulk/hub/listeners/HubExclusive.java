package es.hulk.hub.listeners;

import es.hulk.hub.SharkHub;
import es.hulk.hub.bungee.BungeeUtils;
import es.hulk.hub.util.files.ConfigFile;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class HubExclusive implements Listener {

    public HubExclusive() {
        Bukkit.getPluginManager().registerEvents(this, SharkHub.getInstance());
    }

    private final ConfigFile config = SharkHub.getInstance().getMainConfig();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (config.getBoolean("RESTRICTED_HUB.ENABLE")) {
            if (player.hasPermission(config.getString("RESTRICTED_HUB.PERMISSION"))) {
                String server = config.getString("RESTRICTED_HUB.SERVER");
                BungeeUtils.sendToServer(player, server);
            }
        }
    }
}
