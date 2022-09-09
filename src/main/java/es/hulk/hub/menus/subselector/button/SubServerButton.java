package es.hulk.hub.menus.subselector.button;

import com.cryptomorin.xseries.XMaterial;
import es.hulk.hub.SharkHub;
import es.hulk.hub.bungee.BungeeUtils;
import es.hulk.hub.util.ItemBuilder;
import es.hulk.hub.util.ServerUtil;
import es.hulk.hub.util.files.ConfigFile;
import es.hulk.hub.util.menu.Button;
import lombok.AllArgsConstructor;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public class SubServerButton extends Button {

    private String mServer;
    private String server;
    private final ConfigFile config = SharkHub.getInstance().getSubselectorConfig();

    @Override
    public ItemStack getButtonItem(Player player) {
        if (ServerUtil.getServerVersion().equalsIgnoreCase("v1_7_R4")) {
            return new ItemBuilder(Material.valueOf(config.getString(getConfigPath("ITEM"))))
                    .name(PlaceholderAPI.setPlaceholders(player, config.getString(getConfigPath("NAME"))))
                    .lore(PlaceholderAPI.setPlaceholders(player, config.getStringList(getConfigPath("LORE"))))
                    .data(config.getInt(getConfigPath("DATA")))
                    .build();
        } else {
            return new ItemBuilder(XMaterial.matchXMaterial(Material.valueOf(config.getString(getConfigPath("ITEM")))).parseMaterial())
                    .name(PlaceholderAPI.setPlaceholders(player, config.getString(getConfigPath("NAME"))))
                    .lore(PlaceholderAPI.setPlaceholders(player, config.getStringList(getConfigPath("LORE"))))
                    .data(config.getInt(getConfigPath("DATA")))
                    .build();
        }
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        String path = "SUB_SELECTOR." + mServer + "." + server;
        String serverName = config.getString(path + ".SERVER_NAME");

        if(config.getBoolean(path + ".COMMANDS.ENABLE")) {
            for (String command : config.getStringList(path + ".COMMANDS.COMMANDS")) {
                if (command.contains("[PLAYER]")) {
                    player.performCommand(command.replace("[PLAYER] ", player.getName()));
                } else {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
                }
            }
            return;
        }

        if (config.getBoolean(path + ".QUEUE")) {
            SharkHub.getInstance().getQueueManager().getSystem().sendPlayer(player, serverName);
            return;
        }

        BungeeUtils.sendToServer(player, serverName);
    }

    private String getConfigPath(String a) {
        return "SUB_SELECTOR." + mServer + "." + server + "." + a;
    }
}
