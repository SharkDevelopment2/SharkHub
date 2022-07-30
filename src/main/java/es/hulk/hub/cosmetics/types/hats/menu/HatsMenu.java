package es.hulk.hub.cosmetics.types.hats.menu;

import es.hulk.hub.SharkHub;
import es.hulk.hub.cosmetics.types.hats.button.HatButton;
import es.hulk.hub.cosmetics.types.hats.button.HatRemoveButton;
import es.hulk.hub.util.CC;
import es.hulk.hub.util.files.ConfigFile;
import es.hulk.hub.util.menu.Button;
import es.hulk.hub.util.menu.Menu;
import es.hulk.hub.util.menu.buttons.FillButton;
import org.bukkit.entity.Player;

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
    public boolean isPlaceholder() {
        return config.getBoolean("SERVER_SELECTOR.REFILL_GLASS.ENABLE");
    }

    @Override
    public Button getPlaceholderButton() {
        return new FillButton();
    }
}
