package me.jesusmx.hubcore.hotbar.listeners;

import me.jesusmx.hubcore.SharkHub;
import me.jesusmx.hubcore.hotbar.Hotbar;
import me.jesusmx.hubcore.hotbar.HotbarManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class VisibilityToggleListener implements Listener {

    public VisibilityToggleListener() {
        Bukkit.getPluginManager().registerEvents(this, SharkHub.getInstance());
    }

    @EventHandler
    public void onVisibilityToggleInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Hotbar show = HotbarManager.getItemByAction("VISIBILITY_TOGGLE_ON");
        Hotbar hide = HotbarManager.getItemByAction("VISIBILITY_TOGGLE_OFF");

        assert show != null;
        assert hide != null;

        ItemStack showItem = HotbarManager.getHotbarItemStack(show);
        ItemStack hideItem = HotbarManager.getHotbarItemStack(hide);

        if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (player.getItemInHand().isSimilar(showItem)) {
                for (Player online : Bukkit.getOnlinePlayers()) {
                    online.showPlayer(player);
                }

                player.setItemInHand(hideItem);
            } else if (player.getItemInHand().isSimilar(hideItem)) {
                for (Player online : Bukkit.getOnlinePlayers()) {
                    online.hidePlayer(player);
                }

                player.setItemInHand(showItem);
            }
        }
    }
}
