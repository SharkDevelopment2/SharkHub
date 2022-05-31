package es.hulk.hub.menus.subselector.button;

import com.cryptomorin.xseries.XMaterial;
import es.hulk.hub.menus.server.Server;
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

@AllArgsConstructor
public class SubServerButton extends Button {

    private Server mServer;
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
        if(config.getBoolean("SUB_SELECTOR." + server + ".COMMANDS.ENABLE")) {
            Bukkit.dispatchCommand(player, config.getString("SUB_SELECTOR.ITEMS." + server + ".COMMANDS.COMMAND"));
        } else {
            SharkHub.getInstance().getQueueManager().getSystem().sendPlayer(player, server);
        }
    }

    private String getConfigPath(String a) {
        return "SUB_SELECTOR." + mServer.getName() + "." + server + "." + a;
    }
}
