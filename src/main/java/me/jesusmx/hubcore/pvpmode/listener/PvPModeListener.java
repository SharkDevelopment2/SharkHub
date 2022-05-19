package me.jesusmx.hubcore.pvpmode.listener;

import me.jesusmx.hubcore.SharkHub;
import me.jesusmx.hubcore.pvpmode.cache.PvPModeHandler;
import me.jesusmx.hubcore.util.CC;
import me.jesusmx.hubcore.util.files.ConfigFile;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

public class PvPModeListener implements Listener {

    private final ConfigFile config = SharkHub.getInstance().getHotbarConfig();

    /*Para abrir el pvp-mode */
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if(PvPModeHandler.isOnPvPMode(player)) return;
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            ItemStack is = event.getItem();
            if (is == null || is.getType() == Material.AIR) return;
            if (!is.hasItemMeta() || !is.getItemMeta().hasDisplayName()) return;
            if (is.getType() == Material.valueOf(config.getString("pvp-mode.material")) && is.getItemMeta().getDisplayName().equalsIgnoreCase(CC.translate(config.getString("pvp-mode.name")))) {
                event.setCancelled(true);
                PvPModeHandler.togglePvPMode(event.getPlayer());
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerAttack(EntityDamageByEntityEvent event) {
        if(!(event.getEntity() instanceof Player)) return;
        if(!(event.getDamager() instanceof Player)) return;
        Player damaged = (Player) event.getEntity();
        Player damager = (Player) event.getDamager();
        System.out.println(damager.getName());
        System.out.println(damaged.getName());
        if(!PvPModeHandler.isOnPvPMode(damager)) return;
        if(!PvPModeHandler.isOnPvPMode(damaged)) return;
        event.setCancelled(false);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onItemConsume(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();
        if(!PvPModeHandler.isOnPvPMode(player)) return;
        event.setCancelled(false);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onFoodChange(FoodLevelChangeEvent event) {
        if(!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();
        if(!PvPModeHandler.isOnPvPMode(player)) return;
        event.setCancelled(false);
    }

    @SuppressWarnings("ConstantConditions")
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEntityDeath(PlayerDeathEvent event) {
        if(event.getEntity().getKiller() == null) return;
        if(!(event.getEntity().getKiller() instanceof Player)) return;
        event.getDrops().clear();
        Player killed = event.getEntity();
        Player killer = killed.getKiller();
        PvPModeHandler.getKills().putIfAbsent(killer.getUniqueId(), 0);
        int kills = PvPModeHandler.getKills().get(killer.getUniqueId()) + 1;
        PvPModeHandler.getKills().replace(killer.getUniqueId(), kills);
        PvPModeHandler.disablePvPMode(killed);
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        PvPModeHandler.disablePvPMode(event.getPlayer());
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        if(!PvPModeHandler.isOnPvPMode(event.getPlayer())) return;
        PvPModeHandler.togglePvPMode(event.getPlayer());
    }
}