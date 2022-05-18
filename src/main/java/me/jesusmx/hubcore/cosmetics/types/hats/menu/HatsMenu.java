package me.jesusmx.hubcore.cosmetics.types.hats.menu;

import me.jesusmx.hubcore.SharkHub;
import me.jesusmx.hubcore.cosmetics.types.hats.button.HatButton;
import me.jesusmx.hubcore.cosmetics.types.hats.button.HatRemoveButton;
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

public class HatsMenu extends Menu {

    private final ConfigFile config = SharkHub.getInstance().getHatsConfig();

    @Override
    public String getTitle(Player player) {
        return CC.translate(config.getString("menu.title"));
    }

    @Override
    public int size() {
        return config.getInt("menu.rows") * 9;
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();
        for(String s : config.getConfiguration().getConfigurationSection("menu.hats").getKeys(false)) {
            buttons.put(config.getInt("menu.hats." + s + ".slot"), new HatButton(s));
        }
        buttons.put(config.getInt("menu.remove_hat.slot"), new HatRemoveButton());
        return buttons;
    }

    @Override
    public boolean usePlaceholder() {
        return config.getBoolean("menu.refill-glass.enabled");
    }

    @Override
    public ItemStack getPlaceholderItem(Player player) {
        return new ItemBuilder(Material.STAINED_GLASS_PANE)
                .name(" ")
                .data(config.getInt("menu.refill-glass.data"))
                .build();
    }
}
