package es.hulk.hub.util;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings("deprecation")
public class ItemBuilder {

    private final ItemStack stack;
    private ItemMeta meta;

    /**
     * Creates a new instance with a given material
     * and a default quantity of 1.
     *
     * @param material the material to create from
     */
    public ItemBuilder(Material material) {
        this(material, 1);
    }

    public ItemBuilder(Material material, byte id) {
        this.stack = new ItemStack(material, 1, (short) 0, id);
    }

    public ItemBuilder(Material material, int amount) {
        this(material, amount, (byte) 0);
    }

    public ItemBuilder(ItemStack stack) {
        Preconditions.checkNotNull(stack, "ItemStack cannot be null");
        this.stack = stack;
    }
    
    public ItemBuilder setAmount(int amount) {
        this.stack.setAmount(amount);
        return this;
    }

    public ItemBuilder(Material material, int amount, byte data) {
        Preconditions.checkNotNull(material, "Material cannot be null");
        Preconditions.checkArgument(amount > 0, "Amount must be positive");
        this.stack = new ItemStack(material, amount, data);
    }

    public ItemBuilder name(String name) {
        if (this.meta == null) {
            this.meta = stack.getItemMeta();
        }

        meta.setDisplayName(CC.translate(name));
        return this;
    }

    public ItemBuilder lore(List<String> list) {
        if (this.meta == null) {
            this.meta = stack.getItemMeta();
        }

        meta.setLore(CC.translate(list));
        return this;
    }

    public ItemBuilder lore(String... lore) {
        if (this.meta == null) {
            this.meta = stack.getItemMeta();
        }

        meta.setLore(CC.translate(Arrays.asList(lore)));
        return this;
    }

    public ItemBuilder setLore(List<String> lore) {
        if (this.meta == null) {
            this.meta = stack.getItemMeta();
        }

        meta.setLore(CC.translate(lore));
        return this;
    }

    public ItemBuilder data(int data) {
        stack.setDurability((short) data);
        return this;
    }

    public ItemStack build() {
        if (meta != null) {
            stack.setItemMeta(meta);
        }

        return stack;
    }
}
