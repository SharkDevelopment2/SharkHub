package me.jesusmx.hubcore.listeners;

import me.jesusmx.hubcore.SharkHub;
import me.jesusmx.hubcore.util.files.ConfigFile;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class LaunchPadListener implements Listener {

    private final ConfigFile config = SharkHub.getInstance().getMainConfig();
    private final ConfigFile toggle = SharkHub.getInstance().getTogglesConfig();

    @EventHandler
    public void onLaunchEvent(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (!toggle.getBoolean("normal.launch-pad")) return;
        if (player.getLocation().getBlock().getType() == Material.valueOf(config.getString("launch-pad.material"))) {
            player.setVelocity(player.getLocation().getDirection().multiply(2.0).setY(1.0));
            player.playSound(player.getLocation(), Sound.valueOf(config.getString("launch-pad.sound")), 2.0f, 2.0f);
        }
    }
}
