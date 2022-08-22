package es.hulk.hub.pvpmode.menu;

import com.google.common.collect.Maps;
import es.hulk.hub.SharkHub;
import es.hulk.hub.pvpmode.PvPMode;
import es.hulk.hub.util.CC;
import es.hulk.hub.util.files.ConfigFile;
import es.hulk.hub.util.menu.Button;
import es.hulk.hub.util.menu.Menu;
import org.bukkit.entity.Player;

import java.util.Map;

public class PvPMenu extends Menu {

    private final SharkHub plugin;
    private final ConfigFile pvpModeMenu;

    public PvPMenu(SharkHub plugin) {
        this.plugin = plugin;
        this.pvpModeMenu = plugin.getPvpmodeMenuConfig();

        this.setUpdateAfterClick(true);
    }

    @Override
    public String getTitle(Player player) {
        return CC.translate(pvpModeMenu.getString("PVPMODE_MENU.TITLE"));
    }

    @Override
    public int size(Map<Integer, Button> buttons) {
        return pvpModeMenu.getInt("PVPMODE_MENU.SIZE");
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = Maps.newHashMap();

        for (PvPMode pvpMode : plugin.getPvpmodeManager().getPvpmodes()) {
            buttons.put(pvpMode.getSlot(), new PvPButton(pvpMode));
        }

        return buttons;
    }
}
