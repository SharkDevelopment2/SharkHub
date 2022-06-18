package es.hulk.hub.cosmetics.types.hats.menu;

import es.hulk.hub.cosmetics.types.hats.button.HatButton;
import es.hulk.hub.cosmetics.types.hats.button.HatRemoveButton;
import es.hulk.hub.SharkHub;
import es.hulk.hub.util.CC;
import es.hulk.hub.util.ItemBuilder;
import es.hulk.hub.util.files.ConfigFile;
import es.hulk.hub.util.menu.Button;
import es.hulk.hub.util.menu.Menu;
import org.bukkit.Material;
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
    public int getSize() {
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
        return new ItemBuilder(Material.STAINED_GLASS_PANE)
                .name(" ")
                .data(config.getInt("HATS_MENU.REFILL_GLASS.GLASS_DATA"))
                .build();
    }
}
