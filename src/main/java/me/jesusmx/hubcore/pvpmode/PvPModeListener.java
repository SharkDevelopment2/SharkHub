package me.jesusmx.hubcore.pvpmode;

import me.jesusmx.hubcore.SharkHub;
import me.jesusmx.hubcore.util.CC;
import me.jesusmx.hubcore.util.files.ConfigFile;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PvPModeListener implements Listener {

    public PvPModeListener() {
        Bukkit.getPluginManager().registerEvents(this, SharkHub.getInstance());

    }

    private final ConfigFile messages = SharkHub.getInstance().getMessagesConfig();

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerAttack(EntityDamageByEntityEvent event) {
        if(!(event.getEntity() instanceof Player)) return;
        if(!(event.getDamager() instanceof Player)) return;
        Player damaged = (Player) event.getEntity();
        Player damager = (Player) event.getDamager();
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
        Player victim = event.getEntity();
        Player killer = victim.getKiller();
        PvPModeHandler.getKills().putIfAbsent(killer.getUniqueId(), 0);
        int killerKills = PvPModeHandler.getKills().get(killer.getUniqueId()) + 1;
        int victimKills = PvPModeHandler.getKills().get(victim.getUniqueId()) + 1;
        PvPModeHandler.getKills().replace(killer.getUniqueId(), killerKills);
        PvPModeHandler.getKills().replace(victim.getUniqueId(), victimKills);
        PvPModeHandler.disablePvPMode(victim);
        event.setDeathMessage(CC.translate(messages.getString("PVP_MODE.DEATH_MESSAGE")
                .replace("%KILLS_VICTIM%", String.valueOf(victimKills)))
                .replace("%VICTIM%", victim.getDisplayName())
                .replace("%KILLER_KILLS%", String.valueOf(killerKills))
                .replace("%KILLER%", killer.getDisplayName())
        );
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