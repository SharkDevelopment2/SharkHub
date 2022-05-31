package es.hulk.hub.menus.hub.menu;

import es.hulk.hub.SharkHub;
import es.hulk.hub.bungee.BungeeUtils;
import es.hulk.hub.menus.hub.Hub;
import es.hulk.hub.menus.hub.HubManager;
import es.hulk.hub.util.files.ConfigFile;
import es.hulk.hub.util.menu.Button;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public class HubButton extends Button {

    private final ConfigFile config = SharkHub.getInstance().getHubselectorConfig();
    private final Hub hub;

    @Override
    public ItemStack getButtonItem(Player player) {
        return HubManager.getItemStackFromServer(player, hub);
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        if (hub.isQueue()) {
            SharkHub.getInstance().getQueueManager().getSystem().sendPlayer(player, hub.getServerName());
            return;
        }

        BungeeUtils.sendToServer(player, hub.getServerName());
    }
}
