package es.hulk.hub.menus.subselector;

import es.hulk.hub.SharkHub;
import es.hulk.hub.menus.subselector.button.BackButton;
import es.hulk.hub.menus.subselector.button.SubServerButton;
import es.hulk.hub.util.CC;
import es.hulk.hub.util.files.ConfigFile;
import es.hulk.hub.util.menu.Button;
import es.hulk.hub.util.menu.Menu;
import es.hulk.hub.util.menu.buttons.FillButton;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;

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
    public int getSize() {
        return config.getInt("SUB_SELECTOR.SIZE") * 9;
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();

        for(String s : config.getConfiguration().getConfigurationSection("SUB_SELECTOR." + server).getKeys(false)) {
            buttons.put(config.getInt("SUB_SELECTOR." + server + "." + s + ".SLOT"), new SubServerButton(server, s));
        }

        if (config.getBoolean("SUB_SELECTOR.BACK_BUTTON.ENABLE")) {
            buttons.put(getSize() - 1, new BackButton());
        }

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
