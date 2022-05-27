package es.hulk.hub.hotbar.listeners;

import es.hulk.hub.SharkHub;
import es.hulk.hub.hotbar.Hotbar;
import es.hulk.hub.hotbar.HotbarManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class OtherHotbarActionImplementation implements Listener {

    public OtherHotbarActionImplementation() {
        Bukkit.getPluginManager().registerEvents(this, SharkHub.getInstance());
    }

    @EventHandler
    public void otherHotbarImplementation(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            for (Hotbar hotbar : HotbarManager.getItems()) {
                ItemStack hotbarItemStack = HotbarManager.getHotbarItemStack(hotbar);
                for (String action : hotbar.getActions()) {
                    if (action.contains("[PLAYER]")) {
                        if (hotbarItemStack.equals(player.getItemInHand())) player.performCommand(action.replace("[PLAYER] ", ""));
                    } else if (action.contains("[CONSOLE]")) {
                        if (hotbarItemStack.equals(player.getItemInHand())) Bukkit.dispatchCommand(Bukkit.getConsoleSender(), action.replace("[CONSOLE] ", ""));
                    } else if (action.contains("[MESSAGE]")) {
                        if (hotbarItemStack.equals(player.getItemInHand())) player.sendMessage(action.replace("[MESSAGE] ", ""));
                    }
                }
            }
        }
    }
}
