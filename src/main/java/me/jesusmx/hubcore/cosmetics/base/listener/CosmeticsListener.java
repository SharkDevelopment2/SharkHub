package me.jesusmx.hubcore.cosmetics.base.listener;

import me.jesusmx.hubcore.SharkHub;
import me.jesusmx.hubcore.cosmetics.base.menu.CosmeticsMenu;
import me.jesusmx.hubcore.util.CC;
import me.jesusmx.hubcore.util.files.ConfigFile;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class CosmeticsListener implements Listener {

    /*Para abrir el cosmetics*/
    private final ConfigFile config = SharkHub.getInstance().getHotbarConfig();
    private final ConfigFile messages = SharkHub.getInstance().getMessagesConfig();

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            ItemStack is = event.getItem();
            if (is == null || is.getType() == Material.AIR) return;
            if (!is.hasItemMeta() || !is.getItemMeta().hasDisplayName()) return;
            if (is.getType() == Material.valueOf(config.getString("cosmetics.material")) && is.getItemMeta().getDisplayName().equalsIgnoreCase(CC.translate(config.getString("cosmetics.name")))) {
                if (player.hasPermission("hubcore.command.open-cosmetics")) {
                    new CosmeticsMenu().openMenu(event.getPlayer());
                } else {
                    event.setCancelled(true);
                    player.sendMessage(CC.translate(messages.getString("cosmetics.menu.no-permission")));
                }
            }
        }
    }
}
