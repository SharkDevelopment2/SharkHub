package es.hulk.hub.menus.server;

import com.cryptomorin.xseries.XMaterial;
import es.hulk.hub.menus.subselector.SubSelectorMenu;
import es.hulk.hub.util.ServerUtil;
import lombok.AllArgsConstructor;
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

@AllArgsConstructor
public class ServerButton extends Button {

    private String server;
    private final ConfigFile config = SharkHub.getInstance().getSelectorConfig();

    @Override
    public ItemStack getButtonItem(Player player) {
        if (config.getBoolean(getConfigSection("HEAD.ENABLE"))) {
            ItemStack item;
            if (ServerUtil.getServerVersion().equalsIgnoreCase("v1_7_R4")) {
                item = new ItemStack(Material.SKULL_ITEM, (short) 3);
            } else {
                item = new ItemStack(XMaterial.CREEPER_HEAD.parseMaterial(), (short) 3);
            }
            SkullMeta skull = (SkullMeta) item.getItemMeta();
            skull.setOwner(config.getString(getConfigSection("HEAD.NAME")));
            skull.setDisplayName(PlaceholderAPI.setPlaceholders(player, config.getString(getConfigSection("NAME"))));
            skull.setLore(PlaceholderAPI.setPlaceholders(player, config.getStringList(getConfigSection("LORE"))));
            item.setItemMeta(skull);
            return item;
        } else if (ServerUtil.getServerVersion().equalsIgnoreCase("v1_7_R4")) {
            return new ItemBuilder(Material.valueOf(config.getString(getConfigSection("ITEM"))))
                    .name(PlaceholderAPI.setPlaceholders(player, config.getString(getConfigSection("NAME"))))
                    .lore(PlaceholderAPI.setPlaceholders(player, config.getStringList(getConfigSection("LORE"))))
                    .data(config.getInt(getConfigSection("DATA")))
                    .build();
        } else {
            return new ItemBuilder(XMaterial.matchXMaterial(Material.valueOf(config.getString(getConfigSection("ITEM")))).parseMaterial())
                    .name(PlaceholderAPI.setPlaceholders(player, config.getString(getConfigSection("NAME"))))
                    .lore(PlaceholderAPI.setPlaceholders(player, config.getStringList(getConfigSection("LORE"))))
                    .data(config.getInt(getConfigSection("DATA")))
                    .build();
        }
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
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

