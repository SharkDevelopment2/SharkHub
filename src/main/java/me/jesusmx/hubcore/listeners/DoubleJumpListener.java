package me.jesusmx.hubcore.listeners;

import com.cryptomorin.xseries.XMaterial;
import com.cryptomorin.xseries.XSound;
import com.cryptomorin.xseries.particles.XParticle;
import me.jesusmx.hubcore.SharkHub;
import me.jesusmx.hubcore.pvpmode.PvPModeHandler;
import me.jesusmx.hubcore.util.files.ConfigFile;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;

public class DoubleJumpListener implements Listener {

    private final ConfigFile config = SharkHub.getInstance().getMainConfig();

    private void normalDoubleJump(PlayerToggleFlightEvent event) {
        Player player = event.getPlayer();
        event.setCancelled(true);
        player.setAllowFlight(false);
        player.setFlying(false);
        playEffectSound(player);
    }

    @EventHandler
    public void normalDoubleJumpMoveEvent(PlayerMoveEvent event) {

        Player player = event.getPlayer();
        if(PvPModeHandler.isOnPvPMode(player)) return;
        if(player.getGameMode() == GameMode.CREATIVE) return;
        if (player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() != XMaterial.AIR.parseMaterial()) {
            player.setAllowFlight(true);
        }
    }

    @EventHandler
    public void doubleJumpSelection(PlayerToggleFlightEvent event) {
        Player player = event.getPlayer();
        if(PvPModeHandler.isOnPvPMode(player)) return;
        if(player.getGameMode() == GameMode.CREATIVE) return;

        switch(config.getString("DOUBLE_JUMP.MODE")) {
            case "INFINITE":
                infiniteDoubleJump(event);
            case "NORMAL":
                normalDoubleJump(event);
        }
    }

    public void infiniteDoubleJump(PlayerToggleFlightEvent event) {
        Player player = event.getPlayer();
        event.setCancelled(true);
        player.setFlying(false);
        playEffectSound(player);
    }

    private void playEffectSound(Player player) {
        player.setVelocity(player.getLocation().getDirection().multiply(config.getDouble("DOUBLE_JUMP.velocity")).setY(1));
        if (!config.getBoolean("DOUBLE_JUMP.PARTICLE.ENABLE")) {
            player.getWorld().spigot().playEffect(player.getLocation(), Effect.valueOf(config.getString("DOUBLE_JUMP.PARTICLE.VALUE").toUpperCase()), 26, 0, 0.2F, 0.5F, 0.2F, 0.2F, 12, 387);
        }
        if (config.getBoolean("DOUBLE_JUMP.SOUND.ENABLE")) {
            player.playSound(player.getLocation(), XSound.matchXSound(Sound.valueOf(config.getString("DOUBLE_JUMP.SOUND.VALUE").toUpperCase())).parseSound(), 1.0F, 1.0F);
        }
    }
}