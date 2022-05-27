package es.hulk.hub.menus.server;

import com.cryptomorin.xseries.XMaterial;
import es.hulk.hub.SharkHub;
import es.hulk.hub.util.CC;
import es.hulk.hub.util.bukkit.ItemBuilder;
import es.hulk.hub.util.menu.Button;
import es.hulk.hub.util.menu.Menu;
import es.hulk.hub.util.files.ConfigFile;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class ServerSelectorMenu extends Menu {

    private final ConfigFile config = SharkHub.getInstance().getSelectorConfig();

    @Override
    public String getTitle(Player player) {
        return CC.translate(config.getString("SERVER_SELECTOR.TITLE"));
    }

    @Override
    public int getSize() {
        return config.getInt("SERVER_SELECTOR.SIZE") * 9;
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();
        for(String str : config.getConfiguration().getConfigurationSection("SERVER_SELECTOR.ITEMS").getKeys(false)) {
            buttons.put(config.getInt("SERVER_SELECTOR.items." + str + ".SLOT"), new ServerButton(str));
        }
        return buttons;
    }

    @Override
    public boolean usePlaceholder() {
        return config.getBoolean("SERVER_SELECTOR.REFILL_GLASS.ENABLE");
    }

    @Override
    public ItemStack getPlaceholderItem(Player player) {
        return new ItemBuilder(XMaterial.GLASS_PANE.parseMaterial()).name(" ").data(config.getInt("SERVER_SELECTOR.REFILL_GLASS.GLASS_DATA")).build();
    }
}
