package es.hulk.hub.util.buttons;

import com.cryptomorin.xseries.XMaterial;
import es.hulk.hub.util.CC;
import es.hulk.hub.util.bukkit.ItemBuilder;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;

public abstract class Menu {

    @Getter
    private static Map<Player, Menu> openedMenus = new HashMap<>();
    private Map<Integer, Button> buttons = new HashMap<>();

    public abstract String getTitle(Player player);

    public abstract int size();

    public abstract Map<Integer, Button> getButtons(Player player);

    public void openMenu(Player player) {
        this.buttons = this.getButtons(player);
        int size = size();//this.getSize() == -1 ? this.calculateSize(buttons) : this.getSize();
        boolean update = false;

        String title = this.getTitle(player);
        if (title.length() > 32) {
            title = title.substring(0, 32);
        }

        Inventory inventory = Bukkit.createInventory(player, size, title);
        Menu previousMenu = openedMenus.get(player);

        if (player.getOpenInventory().getTopInventory() != null) {
            if (previousMenu == null) {
                player.closeInventory();
            } else {
                int previousSize = player.getOpenInventory().getTopInventory().getSize();
                String previousTitle = player.getOpenInventory().getTopInventory().getTitle();

                if (previousSize == size && previousTitle.equals(title)) {
                    inventory = player.getOpenInventory().getTopInventory();
                    update = true;
                } else {
                    player.closeInventory();
                }
            }
        }

        for (Map.Entry<Integer, Button> buttonEntry : buttons.entrySet()) {
            ItemStack stack = buttonEntry.getValue().getItem(player);
            ItemMeta meta = stack.getItemMeta();
            meta.setDisplayName(CC.translate(stack.getItemMeta().getDisplayName()));
            meta.setLore(CC.translate(stack.getItemMeta().getLore()));
            stack.setItemMeta(meta);
            inventory.setItem(buttonEntry.getKey(), stack);
        }

        if (this.usePlaceholder()) {
            for (int index = 0; index < size; index++) {
                if (this.buttons.get(index) == null) {
                    this.buttons.put(index, new Button() {
                        @Override
                        public ItemStack getItem(Player player) {
                            return getPlaceholderItem(player);
                        }
                    });
                    inventory.setItem(index, this.buttons.get(index).getItem(player));
                }
            }
        }

        if (update) {
            player.updateInventory();
        } else {
            player.openInventory(inventory);
        }
        openedMenus.put(player, this);
        this.onOpen(player);
    }

    public void onOpen(Player player) {

    }

    public void onClose(Player player) {

    }

    public int getSize() {
        return -1;
    }

    public boolean usePlaceholder() {
        return false;
    }

    public ItemStack getPlaceholderItem(Player player) {
        return new ItemBuilder(XMaterial.GLASS_PANE.parseMaterial()).data(3)
                .build();
    }

}