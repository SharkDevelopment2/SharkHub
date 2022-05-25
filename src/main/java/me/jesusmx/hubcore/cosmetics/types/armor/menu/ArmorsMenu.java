package me.jesusmx.hubcore.cosmetics.types.armor.menu;

import me.jesusmx.hubcore.SharkHub;
import me.jesusmx.hubcore.cosmetics.types.armor.button.ArmorButton;
import me.jesusmx.hubcore.cosmetics.types.armor.button.ArmorRemoveButton;
import me.jesusmx.hubcore.util.CC;
import me.jesusmx.hubcore.util.buttons.Button;
import me.jesusmx.hubcore.util.buttons.Menu;
import me.jesusmx.hubcore.util.files.ConfigFile;
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
