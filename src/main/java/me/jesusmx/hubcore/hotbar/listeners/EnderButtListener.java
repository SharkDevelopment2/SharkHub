package me.jesusmx.hubcore.hotbar.listeners;

import me.jesusmx.hubcore.SharkHub;
import me.jesusmx.hubcore.hotbar.Hotbar;
import me.jesusmx.hubcore.hotbar.HotbarManager;
import me.jesusmx.hubcore.util.CC;
import me.jesusmx.hubcore.util.files.ConfigFile;
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

        if (enderButt == null) {
            CC.sendConsole("&cCould not find hotbar item with action 'ENDER_BUTT'");
            return;
        }

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
