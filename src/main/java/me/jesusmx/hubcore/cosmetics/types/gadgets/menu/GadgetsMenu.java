package me.jesusmx.hubcore.cosmetics.types.gadgets.menu;

import me.jesusmx.hubcore.SharkHub;
import me.jesusmx.hubcore.cosmetics.types.gadgets.items.SnowBallButton;
import me.jesusmx.hubcore.cosmetics.types.gadgets.items.TeleportBowButton;
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

public class GadgetsMenu extends Menu {

    private final ConfigFile config = SharkHub.getInstance().getGadgetsConfig();

    @Override
    public String getTitle(Player player) {
        return CC.translate(config.getString("menu.title"));
    }

    @Override
    public int size() {
        return 27;
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        //11, 15
        Map<Integer, Button> buttons = new HashMap<>();
        buttons.put(config.getInt("menu.bow.slot"), new TeleportBowButton());
        buttons.put(config.getInt("menu.snow.slot"), new SnowBallButton());
        return buttons;
    }

    @Override
    public boolean usePlaceholder() {
        return config.getBoolean("menu.refill-glass.enabled");
    }

    @Override
    public ItemStack getPlaceholderItem(Player player) {
        return new ItemBuilder(Material.STAINED_GLASS_PANE).name(" ").data(config.getInt("menu.refill-glass.data")).build();
    }
}