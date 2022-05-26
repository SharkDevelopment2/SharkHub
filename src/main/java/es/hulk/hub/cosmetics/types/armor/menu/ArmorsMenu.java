package es.hulk.hub.cosmetics.types.armor.menu;

import es.hulk.hub.SharkHub;
import es.hulk.hub.cosmetics.types.armor.button.ArmorButton;
import es.hulk.hub.cosmetics.types.armor.button.ArmorRemoveButton;
import es.hulk.hub.util.CC;
import es.hulk.hub.util.files.ConfigFile;
import es.hulk.hub.util.buttons.Button;
import es.hulk.hub.util.buttons.Menu;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class ArmorsMenu extends Menu {

    private final ConfigFile config = SharkHub.getInstance().getArmorsConfig();

    @Override
    public String getTitle(Player player) {
        return CC.translate(config.getString("ARMORS_MENU.TITLE"));
    }

    @Override
    public int size() {
        return config.getInt("ARMORS_MENU.SIZE") * 9;
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();
        for(String s : config.getConfiguration().getConfigurationSection("ARMORS_MENU.ARMORS").getKeys(false)) {
            buttons.put(config.getInt("ARMORS_MENU.ARMORS." + s + ".SLOT") - 1, new ArmorButton(s));
        }
        buttons.put(config.getInt("ARMORS_MENU.REMOVE_ARMOR.SLOT"), new ArmorRemoveButton());
        return buttons;
    }
}
