package es.hulk.hub.listeners;

import com.cryptomorin.xseries.XSound;
import es.hulk.hub.SharkHub;
import es.hulk.hub.util.files.ConfigFile;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;

public class DoubleJumpListener implements Listener {

    public DoubleJumpListener() {
        Bukkit.getPluginManager().registerEvents(this, SharkHub.getInstance());
    }

    private final ConfigFile config = SharkHub.getInstance().getMainConfig();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (!SharkHub.getInstance().getMainConfig().getBoolean("DOUBLE_JUMP.ENABLE")) return;
        event.getPlayer().setAllowFlight(true);
    }

    @EventHandler
    public void onPlayerDoubleJump(PlayerToggleFlightEvent event) {
        Player player = event.getPlayer();
        if (!SharkHub.getInstance().getMainConfig().getBoolean("DOUBLE_JUMP.ENABLE")) return;
        if (player.getGameMode() == GameMode.CREATIVE || player.isFlying()) return;

        event.setCancelled(true);
        this.playEffectSound(player);
    }

    private void playEffectSound(Player player) {
        player.setVelocity(player.getLocation().getDirection().multiply(config.getDouble("DOUBLE_JUMP.VELOCITY")).setY(1));
        if (!config.getBoolean("DOUBLE_JUMP.PARTICLE.ENABLE")) {
            player.getWorld().spigot().playEffect(player.getLocation(), Effect.valueOf(config.getString("DOUBLE_JUMP.PARTICLE.VALUE").toUpperCase()), 26, 0, 0.2F, 0.5F, 0.2F, 0.2F, 12, 387);
        }
        if (config.getBoolean("DOUBLE_JUMP.SOUND.ENABLE")) {
            player.playSound(player.getLocation(), XSound.matchXSound(Sound.valueOf(config.getString("DOUBLE_JUMP.SOUND.VALUE").toUpperCase())).parseSound(), 1.0F, 1.0F);
        }
    }
}