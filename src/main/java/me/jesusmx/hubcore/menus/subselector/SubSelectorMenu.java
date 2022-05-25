package me.jesusmx.hubcore.menus.subselector;

import com.cryptomorin.xseries.XMaterial;
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
        return CC.translate(config.getString("SUB_SELECTOR.TITLE").replace("%SERVER%", server));
    }

    @Override
    public int size() {
        return config.getInt("SUB_SELECTOR.SIZE") * 9;
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();
        for(String s : config.getConfiguration().getConfigurationSection("SUB_SELECTOR." + server).getKeys(false)) {
            buttons.put(config.getInt("SUB_SELECTOR." + server + "." + s + ".SLOT"), new SubServerButton(server, s));
        }

        if (config.getBoolean("SUB_SELECTOR.BACK_BUTTON.ENABLE")) {
            buttons.put(size() - 1, new BackButton());
        }

        return buttons;
    }

    @Override
    public boolean usePlaceholder() {
        return config.getBoolean("SUB_SELECTOR.REFILL_GLASS.ENABLE");
    }

    @Override
    public ItemStack getPlaceholderItem(Player player) {
        return new ItemBuilder(XMaterial.GLASS_PANE.parseMaterial()).name(" ").data(config.getInt("SUB_SELECTOR.REFILL_GLASS.GLASS_DATA")).build();
    }
}
