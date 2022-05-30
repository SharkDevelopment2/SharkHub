package es.hulk.hub.cosmetics.types.gadgets.listener;

import com.cryptomorin.xseries.XMaterial;
import com.cryptomorin.xseries.XSound;
import es.hulk.hub.SharkHub;
import es.hulk.hub.util.ServerUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class GadgetsListener implements Listener {

    public GadgetsListener() {
        Bukkit.getPluginManager().registerEvents(this, SharkHub.getInstance());
    }

    @EventHandler(ignoreCancelled = true)
    public void onBowUse(EntityShootBowEvent event) {
        if(!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();
        event.setCancelled(true);
        player.launchProjectile(Arrow.class).setVelocity(event.getProjectile().getVelocity().multiply(1));
    }

    @EventHandler
    public void onSnowballUse(PlayerInteractEvent event) {
        if(event.getAction() != Action.RIGHT_CLICK_AIR) return;
        Player player = event.getPlayer();
        if (ServerUtil.getServerVersion().equalsIgnoreCase("v1_7_R4")) {
            if (player.getItemInHand().getType() != Material.SNOW_BALL) return;
        } else {
            if (player.getItemInHand().getType() != XMaterial.SNOWBALL.parseMaterial()) return;
        }
        event.setCancelled(true);
        player.launchProjectile(Snowball.class).setVelocity(player.getLocation().getDirection().multiply(1.5));
    }

    @EventHandler(ignoreCancelled = true)
    public void onSnowballHit(EntityDamageByEntityEvent event) {
        if(!(event.getEntity() instanceof Player)) return;
        if(!(event.getDamager() instanceof Snowball)) return;
        Player player = (Player) event.getEntity();
        Snowball snowball = (Snowball) event.getDamager();
        Player shooter = (Player) snowball.getShooter();
        event.setCancelled(true);
        shooter.hidePlayer(player);
        shooter.sendMessage(ChatColor.GREEN + "Hided the player " + player.getName());
    }

    @EventHandler
    public void onBowHit(ProjectileHitEvent event) {
        if(!(event.getEntity() instanceof Arrow)) return;
        Arrow arrow = (Arrow) event.getEntity();
        Player shooter = (Player) arrow.getShooter();
        arrow.remove();
        shooter.teleport(arrow.getLocation());
        shooter.playSound(shooter.getLocation(), XSound.matchXSound(Sound.ENDERMAN_TELEPORT).parseSound(), 1.0f, 1.0f);
    }

}