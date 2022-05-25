package me.jesusmx.hubcore.cosmetics.types.hats.menu;

import com.cryptomorin.xseries.XMaterial;
import me.jesusmx.hubcore.SharkHub;
import me.jesusmx.hubcore.cosmetics.types.hats.button.HatButton;
import me.jesusmx.hubcore.cosmetics.types.hats.button.HatRemoveButton;
import me.jesusmx.hubcore.util.CC;
import me.jesusmx.hubcore.util.bukkit.ItemBuilder;
import me.jesusmx.hubcore.util.buttons.Button;
import me.jesusmx.hubcore.util.buttons.Menu;
import me.jesusmx.hubcore.util.files.ConfigFile;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class HatsMenu extends Menu {

    private final ConfigFile config = SharkHub.getInstance().getHatsConfig();

    @Override
    public String getTitle(Player player) {
        return CC.translate(config.getString("HATS_MENU.TITLE"));
    }

    @Override
    public int size() {
        return config.getInt("HATS_MENU.SIZE") * 9;
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();
        for(String hats : config.getConfiguration().getConfigurationSection("HATS_MENU.HATS").getKeys(false)) {
            buttons.put(config.getInt("HATS_MENU.HATS." + hats + ".SLOT"), new HatButton(hats));
        }
        buttons.put(config.getInt("HATS_MENU.REMOVE_HAT.SLOT"), new HatRemoveButton());
        return buttons;
    }

    @Override
    public boolean usePlaceholder() {
        return config.getBoolean("HATS_MENU.REFILL_GLASS.ENABLE");
    }

    @Override
    public ItemStack getPlaceholderItem(Player player) {
        return new ItemBuilder(XMaterial.GLASS_PANE.parseMaterial())
                .name(" ")
                .data(config.getInt("HATS_MENU.REFILL_GLASS.GLASS_DATA"))
                .build();
    }
}
