package me.jesusmx.hubcore.listeners;

import me.jesusmx.hubcore.SharkHub;
import me.jesusmx.hubcore.util.CC;
import me.jesusmx.hubcore.util.files.ConfigFile;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MovePlayerListener implements Listener {

    private final ConfigFile config = SharkHub.getInstance().getMainConfig();

    @EventHandler
    public void BorderPlayer(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (player.getLocation().getBlockY() < 0) return;
        if (config.getBoolean("WORLD_BORDER.ENABLE")) {
            int X = event.getTo().getBlockX();
            int Z = event.getTo().getBlockZ();
            int xMax = config.getInt("WORLD_BORDER.MAX_X");
            int zMax = config.getInt("WORLD_BORDER.MAX_Z");
            String message = CC.translate(config.getString("WORLD_BORDER.MESSAGE"));
            if (X >= xMax) {
                player.teleport(player.getWorld().getSpawnLocation());
                player.sendMessage(CC.translate(message));
            }
            if (Z >= zMax) {
                player.teleport(player.getWorld().getSpawnLocation());
                player.sendMessage(CC.translate(message));
            }
            if (X <= -xMax) {
                player.teleport(player.getWorld().getSpawnLocation());
                player.sendMessage(CC.translate(message));
            }
            if (Z <= -zMax) {
                player.teleport(player.getWorld().getSpawnLocation());
                player.sendMessage(CC.translate(message));
            }
        }
    }
}
