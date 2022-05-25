package me.jesusmx.hubcore.hotbar.listeners;

import me.jesusmx.hubcore.SharkHub;
import me.jesusmx.hubcore.hotbar.Hotbar;
import me.jesusmx.hubcore.hotbar.HotbarManager;
import me.jesusmx.hubcore.util.CC;
import me.jesusmx.hubcore.util.files.ConfigFile;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class VisibilityToggleListener implements Listener {

    private final ConfigFile messages = SharkHub.getInstance().getMessagesConfig();

    public VisibilityToggleListener() {
        Bukkit.getPluginManager().registerEvents(this, SharkHub.getInstance());
    }

    @EventHandler
    public void onVisibilityToggleInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Hotbar show = HotbarManager.getItemByAction("VISIBILITY_TOGGLE_ON");
        Hotbar hide = HotbarManager.getItemByAction("VISIBILITY_TOGGLE_OFF");

        if (show == null || hide == null) {
            CC.sendConsole("&cCould not find hotbar item with action 'VISIBILITY_TOGGLE_ON' or 'VISIBILITY_TOGGLE_OFF'");
            return;
        }

        ItemStack showItem = HotbarManager.getHotbarItemStack(show);
        ItemStack hideItem = HotbarManager.getHotbarItemStack(hide);

        if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (player.getItemInHand().isSimilar(showItem)) {
                for (Player online : Bukkit.getOnlinePlayers()) {
                    online.showPlayer(player);
                }

                player.sendMessage(CC.translate(messages.getString("PLAYER_VISIBILITY.HIDE")));
                player.setItemInHand(hideItem);
            } else if (player.getItemInHand().isSimilar(hideItem)) {
                for (Player online : Bukkit.getOnlinePlayers()) {
                    online.hidePlayer(player);
                }

                player.sendMessage(CC.translate(messages.getString("PLAYER_VISIBILITY.SHOW")));
                player.setItemInHand(showItem);
            }
        }
    }
}
