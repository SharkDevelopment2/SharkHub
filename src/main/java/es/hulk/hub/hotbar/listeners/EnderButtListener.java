package es.hulk.hub.hotbar.listeners;

import es.hulk.hub.SharkHub;
import es.hulk.hub.hotbar.Hotbar;
import es.hulk.hub.hotbar.HotbarManager;
import es.hulk.hub.util.files.ConfigFile;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class EnderButtListener implements Listener {

    private final ConfigFile config = SharkHub.getInstance().getMainConfig();

    public EnderButtListener() {
        Bukkit.getPluginManager().registerEvents(this, SharkHub.getInstance());
    }

    @EventHandler
    public void onEnderButtInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Hotbar enderButt = HotbarManager.getItemByAction("ENDER_BUTT");

        if (enderButt == null) return;
        ItemStack item = HotbarManager.getHotbarItemStack(enderButt);

        if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (player.getItemInHand().isSimilar(item)) {
                player.setVelocity(player.getLocation().getDirection().multiply(config.getDouble("ENDER_BUTT.VELOCITY")));
                player.playSound(player.getLocation(), Sound.valueOf(config.getString("ENDER_BUTT.SOUND")), 1.0F, 1.0F);
                player.updateInventory();
                event.setCancelled(true);
            }
        }
    }
}
