package me.jesusmx.hubcore.menus.hub;

import me.jesusmx.hubcore.SharkHub;
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
        return CC.translate(config.getString("HUB_SELECTOR.TITLE"));
    }

    @Override
    public int size() {
        return config.getInt("HUB_SELECTOR.SIZE") * 9;
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();
        for(String str : config.getConfiguration().getConfigurationSection("HUB_SELECTOR.ITEMS").getKeys(false)) {
            buttons.put(config.getInt("HUB_SELECTOR.ITEMS." + str + ".SLOT"), new HubButton(str));
        }
        return buttons;
    }

    @Override
    public boolean usePlaceholder() {
        return config.getBoolean("HUB_SELECTOR.REFILL_GLASS.ENABLE");
    }

    @Override
    public ItemStack getPlaceholderItem(Player player) {
        return new ItemBuilder(Material.STAINED_GLASS_PANE).name(" ").data(config.getInt("HUB_SELECTOR.REFILL_GLASS.GLASS_DATA")).build();
    }
}

