package es.hulk.hub.pvpmode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
public class PvPMode {

    private String name;
    private String displayName;
    private ItemStack item;
    private int slot;

    private ItemStack[] armor;
    private ItemStack[] inventory;
    private Location location;
    private List<Player> players;

}
