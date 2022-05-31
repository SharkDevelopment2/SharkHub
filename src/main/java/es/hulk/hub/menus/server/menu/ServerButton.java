package es.hulk.hub.menus.server.menu;

import com.cryptomorin.xseries.XMaterial;
import es.hulk.hub.bungee.BungeeUtils;
import es.hulk.hub.menus.server.Server;
import es.hulk.hub.menus.subselector.SubSelectorMenu;
import es.hulk.hub.util.ServerUtil;
import lombok.RequiredArgsConstructor;
import me.clip.placeholderapi.PlaceholderAPI;
import es.hulk.hub.SharkHub;
import es.hulk.hub.util.bukkit.ItemBuilder;
import es.hulk.hub.util.menu.Button;
import es.hulk.hub.util.files.ConfigFile;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

@RequiredArgsConstructor
public class ServerButton extends Button {

    private final Server server;

    @Override
    public ItemStack getButtonItem(Player player) {
        if (server.isHeadEnabled()) {
            ItemStack item;
            if (ServerUtil.getServerVersion().equalsIgnoreCase("v1_7_R4")) {
                item = new ItemStack(Material.SKULL_ITEM, (short) 3);
            } else {
                item = new ItemStack(XMaterial.CREEPER_HEAD.parseMaterial(), (short) 3);
            }
            SkullMeta skull = (SkullMeta) item.getItemMeta();
            skull.setOwner(server.getHeadName());
            skull.setDisplayName(PlaceholderAPI.setPlaceholders(player, server.getDisplayName()));
            skull.setLore(PlaceholderAPI.setPlaceholders(player, server.getLore()));
            item.setItemMeta(skull);
            return item;

        } if (ServerUtil.getServerVersion().equalsIgnoreCase("v1_7_R4")) {
            return new ItemBuilder(server.getMaterial())
                    .name(PlaceholderAPI.setPlaceholders(player, server.getDisplayName()))
                    .lore(PlaceholderAPI.setPlaceholders(player, server.getLore()))
                    .data(server.getData())
                    .build();
        } else {
            return new ItemBuilder(XMaterial.matchXMaterial(server.getMaterial()).parseMaterial())
                    .name(PlaceholderAPI.setPlaceholders(player, server.getDisplayName()))
                    .lore(PlaceholderAPI.setPlaceholders(player, server.getLore()))
                    .data(server.getData())
                    .build();
        }
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        if(server.isSubServer()) {
            new SubSelectorMenu(server).openMenu(player);
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

