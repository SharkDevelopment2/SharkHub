package es.hulk.hub.pvpmode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

@Getter @Setter
@AllArgsConstructor
public class PvPMode {

    private String name;
    private ItemStack item;
    private int slot;

    private ItemStack[] armor;
    private ItemStack[] inventory;
    private Location location;

}
