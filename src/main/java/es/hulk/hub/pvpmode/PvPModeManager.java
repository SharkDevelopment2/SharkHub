package es.hulk.hub.pvpmode;

import es.hulk.hub.SharkHub;
import es.hulk.hub.util.InventoryUtil;
import es.hulk.hub.util.ItemBuilder;
import es.hulk.hub.util.LocationSerializer;
import es.hulk.hub.util.files.ConfigFile;
import lombok.Getter;
import net.minecraft.util.com.google.common.collect.Sets;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

@Getter
public class PvPModeManager {

    private final Set<PvPMode> pvpmodes = Sets.newHashSet();
    private final ConfigFile config;
    private final SharkHub plugin;

    public PvPModeManager(SharkHub plugin) {
        this.plugin = plugin;
        this.config = plugin.getPvpmodeConfig();
    }

    public void load() {
        this.pvpmodes.clear();
        ConfigurationSection section = this.config.getConfiguration().getConfigurationSection("PVPMODES");
        if (section == null) return;

        for (String str : section.getKeys(false)) {

            ItemStack itemStack = new ItemBuilder(Material.getMaterial(config.getString("HOTBAR." + str + ".MATERIAL")))
                    .data(config.getInt("HOTBAR." + str + ".DATA"))
                    .setAmount(1)
                    .name(config.getString("HOTBAR." + str + ".DISPLAY_NAME"))
                    .lore(config.getStringList("HOTBAR." + str + ".LORE"))
                    .build();
            int slot = config.getInt("HOTBAR." + str + ".SLOT");

            ItemStack[] armor = InventoryUtil.deserializeInventory(config.getString("PVPMODES." + str + ".ARMOR"));
            ItemStack[] inventory = InventoryUtil.deserializeInventory(config.getString("PVPMODES." + str + ".INVENTORY"));
            Location location = LocationSerializer.deserializeLocation(config.getString("PVPMODES." + str + ".LOCATION"));

            this.pvpmodes.add(new PvPMode(str, itemStack, slot, armor, inventory, location));
        }
    }

    public PvPMode getPvPModeByName(String name) {
        for (PvPMode pvpmode : pvpmodes) {
            if (pvpmode.getName().equals(name)) return pvpmode;
        }
        return null;
    }

}
