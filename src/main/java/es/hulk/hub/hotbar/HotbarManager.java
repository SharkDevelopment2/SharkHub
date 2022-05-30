package es.hulk.hub.hotbar;

import com.cryptomorin.xseries.XMaterial;
import es.hulk.hub.SharkHub;
import es.hulk.hub.hotbar.listeners.*;
import es.hulk.hub.util.CC;
import es.hulk.hub.util.ServerUtil;
import es.hulk.hub.util.bukkit.ItemBuilder;
import es.hulk.hub.util.files.ConfigFile;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

@Getter
public class HotbarManager {

    private List<Hotbar> hotbarItems = new ArrayList<>();
    private static final ConfigFile hotbarConfig = SharkHub.getInstance().getHotbarConfig();

    public HotbarManager() {
        new ServerSelectorListener();
        new HubSelectorListener();
        new VisibilityToggleListener();
        new EnderButtListener();
        new CosmeticHotbarListener();
        new PvPModeHotbarListener();
        new OtherHotbarActionImplementation();
    }

    public void load() {
        this.hotbarItems.clear();

        ConfigurationSection section = hotbarConfig.getConfiguration().getConfigurationSection("HOTBAR");

        for (String key : section.getKeys(false)) {
            boolean enable = hotbarConfig.getBoolean("HOTBAR." +key + ".ENABLE");
            String displayName = hotbarConfig.getString("HOTBAR." + key + ".DISPLAY_NAME");
            Material material = Material.getMaterial(hotbarConfig.getString("HOTBAR." + key + ".MATERIAL"));
            int data = hotbarConfig.getInt("HOTBAR." + key + ".DATA");
            List<String> lore = hotbarConfig.getStringList("HOTBAR." + key + ".LORE");
            int amount = hotbarConfig.getInt("HOTBAR." + key + ".AMOUNT");

            int slot = hotbarConfig.getInt("HOTBAR." + key + ".SLOT");
            List<String> actions = hotbarConfig.getStringList("HOTBAR." + key + ".ACTIONS");

            this.hotbarItems.add(new Hotbar(enable, key, displayName, material, data, lore, amount, slot, actions));
        }

        CC.sendConsole("&bLoaded &e" + hotbarItems.size() + " &bHotbar items");
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
        if (ServerUtil.getServerVersion().equalsIgnoreCase("v1_7_R4"))
            return new ItemBuilder(hotbar.getMaterial())
                    .name(hotbar.getDisplayName())
                    .data(hotbar.getData())
                    .lore(hotbar.getLore())
                    .setAmount(hotbar.getAmount())
                    .build();
        else
            return new ItemBuilder(XMaterial.matchXMaterial(hotbar.getMaterial()).parseMaterial())
                    .name(hotbar.getDisplayName())
                    .data(hotbar.getData())
                    .lore(hotbar.getLore())
                    .setAmount(hotbar.getAmount())
                    .build();
    }

    public static void setHotbarItems(Player player) {
        player.getInventory().clear();
        for (Hotbar hotbar : SharkHub.getInstance().getHotbarManager().getHotbarItems()) {
            if (hotbar.getActions().contains("VISIBILITY_TOGGLE_OFF")) continue;
            if (!hotbar.isEnabled()) continue;

            player.getInventory().setItem(hotbar.getSlot() - 1, getHotbarItemStack(hotbar));
        }
    }

    public static List<Hotbar> getItems() {
        return SharkHub.getInstance().getHotbarManager().getHotbarItems();
    }

}
