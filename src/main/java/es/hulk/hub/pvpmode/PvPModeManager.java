package es.hulk.hub.pvpmode;

import com.google.common.collect.Lists;
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
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
public class PvPModeManager {

    private final Set<PvPMode> pvpmodes = Sets.newHashSet();
    private final ConfigFile data;
    private final ConfigFile menu;

    private final SharkHub plugin;

    public PvPModeManager(SharkHub plugin) {
        this.plugin = plugin;

        this.data = plugin.getPvpmodeDataConfig();
        this.menu = plugin.getPvpmodeMenuConfig();
    }

    public void load() {
        this.pvpmodes.clear();
        ConfigurationSection dataSection = this.data.getConfiguration().getConfigurationSection("PVPMODES");
        if (dataSection == null) return;

        for (String str : dataSection.getKeys(false)) {

            String displayName = menu.getString("PVPMODE_MENU.ITEMS." + str + ".DISPLAY_NAME");
            ItemStack itemStack = new ItemBuilder(Material.getMaterial(menu.getString("PVPMODE_MENU.ITEMS." + str + ".MATERIAL")))
                    .data(menu.getInt("PVPMODE_MENU.ITEMS." + str + ".DATA"))
                    .setAmount(1)
                    .name(menu.getString("PVPMODE_MENU.ITEMS." + str + ".DISPLAY_NAME"))
                    .lore(menu.getStringList("PVPMODE_MENU.ITEMS." + str + ".LORE"))
                    .build();
            int slot = menu.getInt("PVPMODE_MENU.ITEMS." + str + ".SLOT");

            ItemStack[] armor = InventoryUtil.deserializeInventory(data.getString("PVPMODES." + str + ".ARMOR"));
            ItemStack[] inventory = InventoryUtil.deserializeInventory(data.getString("PVPMODES." + str + ".INVENTORY"));
            Location location = LocationSerializer.deserializeLocation(data.getString("PVPMODES." + str + ".LOCATION"));

            List<Player> players = Lists.newArrayList();

            this.pvpmodes.add(new PvPMode(str, displayName, itemStack, slot, armor, inventory, location, players));
        }
    }

    public PvPMode getPvPModeByName(String name) {
        for (PvPMode pvpmode : pvpmodes) {
            if (pvpmode.getName().equals(name)) return pvpmode;
        }
        return null;
    }

}
