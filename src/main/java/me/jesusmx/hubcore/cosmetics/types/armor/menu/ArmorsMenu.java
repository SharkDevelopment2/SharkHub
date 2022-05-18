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
        return CC.translate(config.getString("menu.title"));
    }

    @Override
    public int size() {
        return config.getInt("menu.rows") * 9;
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();
        for(String s : config.getConfiguration().getConfigurationSection("menu.armors").getKeys(false)) {
            buttons.put(config.getInt("menu.armors." + s + ".slot") - 1, new ArmorButton(s));
        }
        buttons.put(config.getInt("menu.remove_armor.slot"), new ArmorRemoveButton());
        return buttons;
    }
}
