package es.hulk.hub.menus.subselector.button;

import com.cryptomorin.xseries.XMaterial;
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

    private String mServer;
    private String server;
    private final ConfigFile config = SharkHub.getInstance().getSubselectorConfig();

    @Override
    public ItemStack getButtonItem(Player player) {
        return new ItemBuilder(XMaterial.matchXMaterial(Material.valueOf(config.getString(getConfigPath("ITEM")))).parseMaterial())
                .name(PlaceholderAPI.setPlaceholders(player, config.getString(getConfigPath("NAME"))))
                .lore(PlaceholderAPI.setPlaceholders(player, config.getStringList(getConfigPath("LORE"))))
                .data(config.getInt(getConfigPath("DATA")))
                .build();
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
        return "SUB_SELECTOR." + mServer + "." + server + "." + a;
    }
}
