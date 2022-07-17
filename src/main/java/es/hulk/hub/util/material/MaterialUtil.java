package es.hulk.hub.util.material;

import org.bukkit.Material;

public class MaterialUtil {

    public Material getMaterial(String string) {
        try {
            return Material.getMaterial(Integer.parseInt(string));
        }
        catch (IllegalArgumentException ex) {
            return Material.getMaterial(string);
        }
    }

}
