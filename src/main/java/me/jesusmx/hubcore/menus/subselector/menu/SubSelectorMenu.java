package me.jesusmx.hubcore.menus.subselector.menu;

import lombok.AllArgsConstructor;
import me.jesusmx.hubcore.SharkHub;
import me.jesusmx.hubcore.menus.subselector.button.BackButton;
import me.jesusmx.hubcore.menus.subselector.button.SubServerButton;
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

@AllArgsConstructor
public class SubSelectorMenu extends Menu {

    private String server;
    private final ConfigFile config = SharkHub.getInstance().getSubselectorConfig();

    @Override
    public String getTitle(Player player) {
        return CC.translate(config.getString("sub-selector.title").replace("%server%", server));
    }

    @Override
    public int size() {
        return config.getInt("sub-selector.rows") * 9;
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();
        for(String s : config.getConfiguration().getConfigurationSection("sub-selector." + server).getKeys(false)) {
            buttons.put(config.getInt("sub-selector." + server + "." + s + ".slot"), new SubServerButton(server, s));
        }
        if (config.getBoolean("sub-selector.back-button.enabled"))
        buttons.put(size() - 1, new BackButton());
        return buttons;
    }

    @Override
    public boolean usePlaceholder() {
        return config.getBoolean("sub-selector.refill-glass.enabled");
    }

    @Override
    public ItemStack getPlaceholderItem(Player player) {
        return new ItemBuilder(Material.STAINED_GLASS_PANE).name(" ").data(config.getInt("sub-selector.refill-glass.data")).build();
    }
}
