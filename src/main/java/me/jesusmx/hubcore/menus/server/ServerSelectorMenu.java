package me.jesusmx.hubcore.menus.server;

import me.jesusmx.hubcore.SharkHub;
import me.jesusmx.hubcore.menus.server.ServerButton;
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
        return CC.translate(config.getString("SERVER_SELECTOR.TITLE"));
    }

    @Override
    public int size() {
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
        return new ItemBuilder(Material.STAINED_GLASS_PANE).name(" ").data(config.getInt("SERVER_SELECTOR.REFILL_GLASS.GLASS_DATA")).build();
    }
}
