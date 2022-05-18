package me.jesusmx.hubcore.listeners;

import me.jesusmx.hubcore.SharkHub;
import me.jesusmx.hubcore.util.CC;
import me.jesusmx.hubcore.util.files.ConfigFile;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MovePlayerListener implements Listener {

    private ConfigFile config = SharkHub.getInstance().getSettingsConfig();
    private ConfigFile toggle = SharkHub.getInstance().getTogglesConfig();

    @EventHandler
    public void BorderPlayer(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (player.getLocation().getBlockY() < 0) {
        }
        if (toggle.getBoolean("world.border.enabled")) {
            int X = event.getTo().getBlockX();
            int Z = event.getTo().getBlockZ();
            int xMax = config.getInt("world.border.max-x");
            int zMax = config.getInt("world.border.max-z");
            String message = CC.translate(config.getString("world.border.message"));
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
