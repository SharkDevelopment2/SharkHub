package me.jesusmx.hubcore.menus.server.menu;

import me.jesusmx.hubcore.SharkHub;
import me.jesusmx.hubcore.menus.server.button.ServerButton;
import me.jesusmx.hubcore.util.CC;
import me.jesusmx.hubcore.util.bukkit.ItemBuilder;
import me.jesusmx.hubcore.util.buttons.Button;
import me.jesusmx.hubcore.util.buttons.Menu;
import me.jesusmx.hubcore.util.files.ConfigFile;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class ServerSelectorMenu extends Menu {

    private final ConfigFile config = SharkHub.getInstance().getSelectorConfig();

    @Override
    public String getTitle(Player player) {
        return CC.translate(config.getString("server-selector.title"));
    }

    @Override
    public int size() {
        return config.getInt("server-selector.rows") * 9;
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();
        for(String s : config.getConfiguration().getConfigurationSection("server-selector.items").getKeys(false)) {
            buttons.put(config.getInt("server-selector.items." + s + ".slot"), new ServerButton(s));
        }
        return buttons;
    }

    @Override
    public boolean usePlaceholder() {
        return config.getBoolean("server-selector.refill-glass.enabled");
    }

    @Override
    public ItemStack getPlaceholderItem(Player player) {
        return new ItemBuilder(Material.STAINED_GLASS_PANE).name(" ").data(config.getInt("server-selector.refill-glass.data")).build();
    }
}
