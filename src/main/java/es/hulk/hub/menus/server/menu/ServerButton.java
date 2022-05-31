package es.hulk.hub.menus.server.menu;

import es.hulk.hub.SharkHub;
import es.hulk.hub.bungee.BungeeUtils;
import es.hulk.hub.menus.server.Server;
import es.hulk.hub.menus.server.ServerManager;
import es.hulk.hub.menus.subselector.SubSelectorMenu;
import es.hulk.hub.util.menu.Button;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public class ServerButton extends Button {

    private final Server server;

    @Override
    public ItemStack getButtonItem(Player player) {
        return ServerManager.getItemStackFromServer(player, server);
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        if(server.isSubServer()) {
            new SubSelectorMenu(server.getName()).openMenu(player);
            return;
        }

        if(server.isCommandsEnabled()) {
            for (String command : server.getCommands()) {
                if (command.contains("[PLAYER]")) {
                    player.performCommand(command.replace("[PLAYER] ", player.getName()));
                } else {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
                }
            }
            return;
        }

        if (server.isQueue()) {
            SharkHub.getInstance().getQueueManager().getSystem().sendPlayer(player, server.getServerName());
            return;
        }

        BungeeUtils.sendToServer(player, server.getServerName());
    }

    private String getConfigSection(String a) {
        return "SERVER_SELECTOR.ITEMS." + server + "." + a;
    }
}

