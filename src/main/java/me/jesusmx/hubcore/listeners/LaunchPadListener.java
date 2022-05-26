package me.jesusmx.hubcore.listeners;

import com.cryptomorin.xseries.XMaterial;
import com.cryptomorin.xseries.XSound;
import me.jesusmx.hubcore.SharkHub;
import me.jesusmx.hubcore.util.files.ConfigFile;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class LaunchPadListener implements Listener {

    public LaunchPadListener() {
        Bukkit.getPluginManager().registerEvents(this, SharkHub.getInstance());
    }

    private final ConfigFile config = SharkHub.getInstance().getMainConfig();

    @EventHandler
    public void onLaunchEvent(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (!config.getBoolean("LAUNCH_PAD.ENABLE")) return;
        if (player.getLocation().getBlock().getType() == XMaterial.matchXMaterial(Material.valueOf(config.getString("LAUNCH_PAD.MATERIAL"))).parseMaterial()) {
            player.setVelocity(player.getLocation().getDirection().multiply(2.0).setY(1.0));
            player.playSound(player.getLocation(), XSound.matchXSound(Sound.valueOf(config.getString("LAUNCH_PAD.SOUND"))).parseSound(), 2.0f, 2.0f);
        }
    }
}
