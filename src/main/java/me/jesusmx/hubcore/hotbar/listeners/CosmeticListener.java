package me.jesusmx.hubcore.hotbar.listeners;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import me.jesusmx.hubcore.SharkHub;
import me.jesusmx.hubcore.cosmetics.base.menu.CosmeticsMenu;
import me.jesusmx.hubcore.hotbar.Hotbar;
import me.jesusmx.hubcore.hotbar.HotbarManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class CosmeticListener implements Listener {

    public CosmeticListener() {
        Bukkit.getPluginManager().registerEvents(this, SharkHub.getInstance());
    }

    @EventHandler
    public void onHubSelectorInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Hotbar cosmetic = HotbarManager.getItemByAction("OPEN_COSMETIC_MENU");
        assert cosmetic != null;
        ItemStack item = HotbarManager.getHotbarItemStack(cosmetic);

        if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (player.getItemInHand().isSimilar(item)) {
                new CosmeticsMenu().openMenu(player);
            }
        }
    }
}
