package me.jesusmx.hubcore.menus.server;

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
        if (config.getBoolean(getConfigSection("HEAD.ENABLE"))) {
            ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
            SkullMeta skull = (SkullMeta) item.getItemMeta();
            skull.setOwner(config.getString(getConfigSection("HEAD.NAME")));
            skull.setDisplayName(PlaceholderAPI.setPlaceholders(player, config.getString(getConfigSection("NAME"))));
            skull.setLore(PlaceholderAPI.setPlaceholders(player, config.getStringList(getConfigSection("LORE"))));
            item.setItemMeta(skull);
            return item;
        } else {
            return new ItemBuilder(Material.valueOf(config.getString(getConfigSection("ITEM"))))
                    .name(PlaceholderAPI.setPlaceholders(player, config.getString(getConfigSection("NAME"))))
                    .lore(PlaceholderAPI.setPlaceholders(player, config.getStringList(getConfigSection("LORE"))))
                    .data(config.getInt(getConfigSection("DATA")))
                    .build();
        }
    }

    @Override
    public void click(Player player, int slot, ClickType clickType, int hotbarButton) {
        if(config.getBoolean("SERVER_SELECTOR.ITEMS." + server + ".SUB_SERVER")) {
            new SubSelectorMenu(server).openMenu(player);
        } else {
            if(config.getBoolean("SERVER_SELECTOR.ITEMS." + server + ".COMMANDS.ENABLE")) {
                Bukkit.dispatchCommand(player, config.getString("SERVER_SELECTOR.ITEMS." + server + ".COMMANDS.COMMAND"));
            } else {
                SharkHub.getInstance().getQueueManager().getSystem().sendPlayer(player, server);
            }
        }
    }

    private String getConfigSection(String a) {
        return "SERVER_SELECTOR.ITEMS." + server + "." + a;
    }
}

