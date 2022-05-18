package me.jesusmx.hubcore.cosmetics.types.particles;

import lombok.AllArgsConstructor;
import me.jesusmx.hubcore.util.buttons.Button;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public class ParticleButton extends Button {

    @Override
    public ItemStack getItem(Player player) {
        return null;
    }
}
