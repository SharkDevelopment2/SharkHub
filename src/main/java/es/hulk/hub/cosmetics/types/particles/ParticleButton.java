package es.hulk.hub.cosmetics.types.particles;

import es.hulk.hub.util.menu.Button;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public class ParticleButton extends Button {

    @Override
    public ItemStack getButtonItem(Player p0) {
        return null;
    }
}
