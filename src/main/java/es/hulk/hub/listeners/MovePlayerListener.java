package es.hulk.hub.listeners;

import es.hulk.hub.SharkHub;
import es.hulk.hub.util.CC;
import es.hulk.hub.util.files.ConfigFile;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MovePlayerListener implements Listener {

    public MovePlayerListener() {
        Bukkit.getPluginManager().registerEvents(this, SharkHub.getInstance());
    }

    private final ConfigFile config = SharkHub.getInstance().getMainConfig();

    @EventHandler
    public void BorderPlayer(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (player.getLocation().getBlockY() < 0) return;
        if (!config.getBoolean("WORLD_BORDER.ENABLE")) return;

        int X = event.getTo().getBlockX();
        int Z = event.getTo().getBlockZ();
        int xMax = config.getInt("WORLD_BORDER.MAX_X");
        int zMax = config.getInt("WORLD_BORDER.MAX_Z");
        String message = CC.translate(config.getString("WORLD_BORDER.MESSAGE"));

        if (X >= xMax || X <= -xMax || Z >= zMax || Z <= -zMax) {
            Bukkit.dispatchCommand(player, "spawn");
            player.sendMessage(CC.translate(message));
        }
    }
}
