package es.hulk.hub.menus.hub;

import com.cryptomorin.xseries.XMaterial;
import es.hulk.hub.SharkHub;
import es.hulk.hub.menus.server.Server;
import es.hulk.hub.util.CC;
import es.hulk.hub.util.ItemMaker;
import es.hulk.hub.util.ServerUtil;
import es.hulk.hub.util.files.ConfigFile;
import lombok.Getter;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

public class HubManager {

    @Getter
    private static List<Hub> hubList = new ArrayList<>();
    private static final ConfigFile hubConfig = SharkHub.getInstance().getHubselectorConfig();

    public static void load() {
        hubList.clear();

        ConfigurationSection section = hubConfig.getConfiguration().getConfigurationSection("HUB_SELECTOR.ITEMS");

        for (String hub : section.getKeys(false)) {

            String displayName = section.getString(hub + ".DISPLAY_NAME");
            Material material = Material.valueOf(section.getString(hub + ".MATERIAL"));
            int data = section.getInt(hub + ".DATA");
            int slot = section.getInt(hub + ".SLOT");
            List<String> lore = section.getStringList(hub + ".LORE");
            boolean queue = section.getBoolean(hub + ".QUEUE");
            String serverName = section.getString(hub + ".SERVER_NAME");

            hubList.add(new Hub(hub, displayName, material, data, slot, lore, queue, serverName));
        }

        CC.sendConsole("&bLoaded &e" + hubList.size() + " &bHubs");
    }

    public static ItemStack getItemStackFromServer(Player player, Hub hub) {
        return new ItemMaker(hub.getMaterial(), 1, (short) hub.getData())
                .lore(SharkHub.getInstance().isPlaceholderAPI() ? PlaceholderAPI.setPlaceholders(player, hub.getLore()) : hub.getLore())
                .displayName(SharkHub.getInstance().isPlaceholderAPI() ? PlaceholderAPI.setPlaceholders(player, hub.getDisplayName()) : hub.getDisplayName())
                .build();
    }

}
