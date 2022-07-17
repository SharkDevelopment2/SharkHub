package es.hulk.hub.util.skull;

import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface SkullVersion {

    ItemStack createCustomSkull(String texture, String displayName, List<String> lore);
}
