package es.hulk.hub.menus.hub;

import com.cryptomorin.xseries.XMaterial;
import me.clip.placeholderapi.PlaceholderAPI;
import es.hulk.hub.SharkHub;
import es.hulk.hub.util.bukkit.ItemBuilder;
import es.hulk.hub.util.menu.Button;
import es.hulk.hub.util.files.ConfigFile;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class HubButton extends Button {

    private final ConfigFile config = SharkHub.getInstance().getHubselectorConfig();
    private final String server;

    public HubButton(String server) {
        this.server = server;
    }

    @Override
    public ItemStack getButtonItem(Player player) {
        return new ItemBuilder(XMaterial.matchXMaterial(Material.valueOf(config.getString(getSection("ITEM")))).parseMaterial())
                .name(PlaceholderAPI.setPlaceholders(player, config.getString(getSection("NAME"))))
                .lore(PlaceholderAPI.setPlaceholders(player, config.getStringList(getSection("LORE"))))
                .data(config.getInt(getSection("DATA")))
                .build();
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        SharkHub.getInstance().getQueueManager().getSystem().sendPlayer(player, server);
    }

    private String getSection(String section) {
        return "HUB_SELECTOR.ITEMS." + server + "." + section;
    }
}
