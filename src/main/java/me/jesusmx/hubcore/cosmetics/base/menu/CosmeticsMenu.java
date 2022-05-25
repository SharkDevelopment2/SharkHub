package me.jesusmx.hubcore.cosmetics.base.menu;

import com.cryptomorin.xseries.XMaterial;
import me.jesusmx.hubcore.SharkHub;
import me.jesusmx.hubcore.cosmetics.types.armor.button.ArmorMenuButton;
import me.jesusmx.hubcore.cosmetics.types.gadgets.button.GadgetsMenuButton;
import me.jesusmx.hubcore.cosmetics.types.hats.button.HatMenuButton;
import me.jesusmx.hubcore.util.CC;
import me.jesusmx.hubcore.util.bukkit.ItemBuilder;
import me.jesusmx.hubcore.util.buttons.Button;
import me.jesusmx.hubcore.util.buttons.Menu;
import me.jesusmx.hubcore.util.files.ConfigFile;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class CosmeticsMenu extends Menu {

    private final ConfigFile config = SharkHub.getInstance().getCosmeticsConfig();

    @Override
    public String getTitle(Player player) {
        return CC.translate(config.getString("cosmetics.title"));
    }

    @Override
    public int size() {
        return config.getInt("cosmetics.rows") * 9;
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();
        buttons.put(config.getInt("cosmetics.hats.slot"), new HatMenuButton());
        buttons.put(config.getInt("cosmetics.armor.slot"), new ArmorMenuButton());
        buttons.put(config.getInt("cosmetics.gadgets.slot"), new GadgetsMenuButton());
        return buttons;
    }

    @Override
    public boolean usePlaceholder() {
        return config.getBoolean("cosmetics.refill-glass.enabled");
    }

    @Override
    public ItemStack getPlaceholderItem(Player player) {
        return new ItemBuilder(XMaterial.GLASS_PANE.parseMaterial()).name(" ").data(config.getInt("cosmetics.refill-glass.data")).build();
    }
}
