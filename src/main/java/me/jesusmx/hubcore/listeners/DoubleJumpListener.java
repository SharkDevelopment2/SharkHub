package me.jesusmx.hubcore.listeners;

import me.jesusmx.hubcore.SharkHub;
import me.jesusmx.hubcore.pvpmode.cache.PvPModeHandler;
import me.jesusmx.hubcore.util.files.ConfigFile;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;

public class DoubleJumpListener implements Listener {

    private ConfigFile config = SharkHub.getInstance().getMainConfig();
    private ConfigFile toggle = SharkHub.getInstance().getTogglesConfig();

    private void normal(PlayerToggleFlightEvent event) {
        Player player = event.getPlayer();
        event.setCancelled(true);
        player.setAllowFlight(false);
        player.setFlying(false);
        player.setVelocity(player.getLocation().getDirection().multiply(config.getDouble("double-jump.velocity")).setY(1));
        if (!config.getBoolean("double-jump.particle.enabled")) {
            player.getWorld().spigot().playEffect(player.getLocation(), Effect.valueOf(config.getString("double-jump.particle.effect").toUpperCase()), 26, 0, 0.2F, 0.5F, 0.2F, 0.2F, 12, 387);
        }
        if (config.getBoolean("double-jump.sound.enabled")) {
            player.playSound(player.getLocation(), Sound.valueOf(config.getString("double-jump.sound.effect").toUpperCase()), 1.0F, 1.0F);
        }
    }

    @EventHandler
    public void e(PlayerMoveEvent event) {
        if(!toggle.getBoolean("normal.double-jump.normal")) return;

        Player player = event.getPlayer();
        if(PvPModeHandler.isOnPvPMode(player)) return;
        if(player.getGameMode() == GameMode.CREATIVE) return;
        if (player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() != Material.AIR) {
            player.setAllowFlight(true);
        }
    }

    @EventHandler
    public void a(PlayerToggleFlightEvent event) {
        Player player = event.getPlayer();
        if(PvPModeHandler.isOnPvPMode(player)) return;
        if(player.getGameMode() == GameMode.CREATIVE) return;
        if(toggle.getBoolean("normal.double-jump.infinite")) {
            infinite(event);
        } else {
            normal(event);
        }
    }

    public void infinite(PlayerToggleFlightEvent event) {
        Player player = event.getPlayer();
        event.setCancelled(true);
        player.setFlying(false);
        player.setVelocity(player.getLocation().getDirection().multiply(config.getDouble("double-jump.velocity")).setY(1));
        try {
            if (config.getBoolean("double-jump.sound.enabled")) {
                player.playSound(player.getLocation(), Sound.valueOf(config.getString("double-jump.sound.effect").toUpperCase()), 1.0F, 1.0F);
            }
            player.getWorld().spigot().playEffect(player.getLocation(), Effect.valueOf(config.getString("double-jump.particle").toUpperCase()), 26, 0, 0.2F, 0.5F, 0.2F, 0.2F, 12, 387);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}