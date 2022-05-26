package es.hulk.hub.cosmetics.types.particles;

import lombok.AllArgsConstructor;
import es.hulk.hub.util.buttons.Button;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public class ParticleButton extends Button {

    @Override
    public ItemStack getItem(Player player) {
        return null;
    }
}
