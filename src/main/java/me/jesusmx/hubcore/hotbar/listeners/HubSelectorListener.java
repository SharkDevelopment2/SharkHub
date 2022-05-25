package me.jesusmx.hubcore.hotbar.listeners;

import me.jesusmx.hubcore.SharkHub;
import me.jesusmx.hubcore.hotbar.Hotbar;
import me.jesusmx.hubcore.hotbar.HotbarManager;
import me.jesusmx.hubcore.menus.hub.HubSelectorMenu;
import me.jesusmx.hubcore.util.CC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class HubSelectorListener implements Listener {

    public HubSelectorListener() {
        Bukkit.getPluginManager().registerEvents(this, SharkHub.getInstance());
    }

    @EventHandler
    public void onHubSelectorInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Hotbar hubSelector = HotbarManager.getItemByAction("OPEN_HUB_SELECTOR");

        if (hubSelector == null) {
            CC.sendConsole("&cCould not find hotbar item with action 'OPEN_HUB_SELECTOR'");
            return;
        }

        ItemStack item = HotbarManager.getHotbarItemStack(hubSelector);

        if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (player.getItemInHand().isSimilar(item)) {
                new HubSelectorMenu().openMenu(event.getPlayer());
            }
        }
    }
}
