package me.jesusmx.hubcore.menus.server.button;

import lombok.AllArgsConstructor;
import me.clip.placeholderapi.PlaceholderAPI;
import me.jesusmx.hubcore.SharkHub;
import me.jesusmx.hubcore.menus.subselector.menu.SubSelectorMenu;
import me.jesusmx.hubcore.util.bukkit.ItemBuilder;
import me.jesusmx.hubcore.util.buttons.Button;
import me.jesusmx.hubcore.util.files.ConfigFile;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

@AllArgsConstructor
public class ServerButton extends Button {

    private String server;
    private final ConfigFile config = SharkHub.getInstance().getSelectorConfig();

    @Override
    public ItemStack getItem(Player player) {
        if (config.getBoolean(d("head.enabled"))) {
            ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
            SkullMeta skull = (SkullMeta) item.getItemMeta();
            skull.setOwner(config.getString(d("head.name")));
            skull.setDisplayName(PlaceholderAPI.setPlaceholders(player, config.getString(d("name"))));
            skull.setLore(PlaceholderAPI.setPlaceholders(player, config.getStringList(d("lore"))));
            item.setItemMeta(skull);
            return item;
        } else {
            return new ItemBuilder(Material.valueOf(config.getString(d("item"))))
                    .name(PlaceholderAPI.setPlaceholders(player, config.getString(d("name"))))
                    .lore(PlaceholderAPI.setPlaceholders(player, config.getStringList(d("lore"))))
                    .data(config.getInt(d("data")))
                    .build();
        }
    }

    @Override
    public void click(Player player, int slot, ClickType clickType, int hotbarButton) {
        if(config.getBoolean("server-selector.items." + server + ".sub-server")) {
            new SubSelectorMenu(server).openMenu(player);
        } else {
            if(config.getBoolean("server-selector.items." + server + ".command.enabled")) {
                Bukkit.dispatchCommand(player, config.getString("server-selector.items." + server + ".command.command"));
            } else {
                SharkHub.getInstance().getQueueManager().getSystem().sendPlayer(player, server);
            }
        }
    }

    private String d(String a) {
        return "server-selector.items." + server + "." + a;
    }
}

