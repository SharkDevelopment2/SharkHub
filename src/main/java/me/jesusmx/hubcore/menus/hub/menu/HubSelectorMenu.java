package me.jesusmx.hubcore.menus.hub.menu;

import me.jesusmx.hubcore.SharkHub;
import me.jesusmx.hubcore.menus.hub.button.HubButton;
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

public class HubSelectorMenu extends Menu {

    private ConfigFile config = SharkHub.getInstance().getHubselectorConfig();

    @Override
    public String getTitle(Player player) {
        return CC.translate(config.getString("hub-selector.title"));
    }

    @Override
    public int size() {
        return config.getInt("hub-selector.rows") * 9;
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();
        for(String s : config.getConfiguration().getConfigurationSection("hub-selector.items").getKeys(false)) {
            buttons.put(config.getInt("hub-selector.items." + s + ".slot"), new HubButton(s));
        }
        return buttons;
    }

    @Override
    public boolean usePlaceholder() {
        return config.getBoolean("hub-selector.refill-glass.enabled");
    }

    @Override
    public ItemStack getPlaceholderItem(Player player) {
        return new ItemBuilder(Material.STAINED_GLASS_PANE).name(" ").data(config.getInt("hub-selector.refill-glass.data")).build();
    }
}

