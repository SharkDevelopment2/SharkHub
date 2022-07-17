package es.hulk.hub.util.skull.impl;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import es.hulk.hub.util.CC;
import es.hulk.hub.util.material.CompatibleMaterial;
import es.hulk.hub.util.skull.SkullVersion;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.List;
import java.util.UUID;

/**
 * Created by Risas
 * Project: PandaHub
 * Date: 14-04-2022
 * Twitter: @RisasDev
 * GitHub: https://github.com/RisasDev
 */

public class Skull_v1_7 implements SkullVersion {

    @Override
    public ItemStack createCustomSkull(String value, String displayName, List<String> lore) {
        ItemStack skull = new ItemStack(CompatibleMaterial.HUMAN_SKULL.getMaterial(), 1, (short) 3);

        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        skullMeta.setDisplayName(CC.translate(displayName));
        skullMeta.setLore(CC.translate(lore));

        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", value));

        Field profileField;

        try {
            profileField = skullMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(skullMeta, profile);
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }

        skull.setItemMeta(skullMeta);
        return skull;
    }
}
