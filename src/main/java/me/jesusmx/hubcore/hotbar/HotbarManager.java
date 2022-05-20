package me.jesusmx.hubcore.hotbar;

import lombok.Getter;
import me.jesusmx.hubcore.SharkHub;
import me.jesusmx.hubcore.util.bukkit.ItemBuilder;
import me.jesusmx.hubcore.util.files.ConfigFile;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

@Getter
public class HotbarManager {

    private List<Hotbar> hotbarItems = new ArrayList<>();
    private static final ConfigFile hotbarConfig = SharkHub.getInstance().getHotbarConfig();

    public void load() {
        this.hotbarItems.clear();

        ConfigurationSection section = hotbarConfig.getConfiguration().getConfigurationSection("HOTBAR");

        for (String key : section.getKeys(false)) {
            String displayName = hotbarConfig.getString("HOTBAR." + key + ".DISPLAY_NAME");
            Material material = Material.getMaterial(hotbarConfig.getString("HOTBAR." + key + ".MATERIAL"));
            int data = hotbarConfig.getInt("HOTBAR." + key + ".DATA");
            List<String> lore = hotbarConfig.getStringList("HOTBAR." + key + ".LORE");
            int amount = hotbarConfig.getInt("HOTBAR." + key + ".AMOUNT");

            int slot = hotbarConfig.getInt("HOTBAR." + key + ".SLOT");
            List<String> actions = hotbarConfig.getStringList("HOTBAR." + key + ".ACTIONS");

            this.hotbarItems.add(new Hotbar(key, displayName, material, data, lore, amount, slot, actions));
        }
    }

    public static Hotbar getItemByName(String name) {
        for (Hotbar hotbar : SharkHub.getInstance().getHotbarManager().getHotbarItems()) {
            if (hotbar.getName().equalsIgnoreCase(name)) {
                return hotbar;
            }
        }
        return null;
    }

    public static Hotbar getItemByAction(String action) {
        for (Hotbar hotbar : SharkHub.getInstance().getHotbarManager().getHotbarItems()) {
            if (hotbar.getActions().contains(action)) {
                return hotbar;
            }
        }
        return null;
    }

    public static ItemStack getHotbarItemStack(Hotbar hotbar) {
        return new ItemBuilder(hotbar.getMaterial())
                .name(hotbar.getDisplayName())
                .data(hotbar.getData())
                .lore(hotbar.getLore())
                .setAmount(hotbar.getAmount())
                .build();
    }

}
