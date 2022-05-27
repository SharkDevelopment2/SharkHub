package es.hulk.hub.util.menu;

import com.cryptomorin.xseries.XMaterial;
import es.hulk.hub.SharkHub;
import es.hulk.hub.util.CC;
import es.hulk.hub.util.bukkit.ItemBuilder;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public abstract class Menu {

    public static Map<String, Menu> currentlyOpenedMenus = new HashMap<>();
    @Getter
    private Map<Integer, Button> buttons = new HashMap<>();
    private boolean autoUpdate = false;
    private boolean updateAfterClick = true;
    private boolean closedByMenu = false;
    private boolean placeholder = false;
    private Button placeholderButton = Button.placeholder(Material.STAINED_GLASS_PANE, (short) 7, " ");
    private BukkitTask task;

    private ItemStack createItemStack(Player player, Button button) {
        return new ItemBuilder(button.getButtonItem(player)).build();
    }

    public void openMenu(final Player player) {
        this.buttons = this.getButtons(player);

        Menu previousMenu = Menu.currentlyOpenedMenus.get(player.getName());
        Inventory inventory = null;
        int size = this.getSize() == -1 ? this.size(this.buttons) : this.getSize();
        boolean update = false;
        String title = CC.translate(this.getTitle(player));

        if (title.length() > 32) {
            title = title.substring(0, 32);
        }

        if (player.getOpenInventory() != null) {
            if (previousMenu == null) {
                player.closeInventory();
            } else {
                int previousSize = player.getOpenInventory().getTopInventory().getSize();

                if (previousSize == size && player.getOpenInventory().getTitle().equals(title)) {
                    inventory = player.getOpenInventory().getTopInventory();
                    update = true;
                } else {
                    previousMenu.setClosedByMenu(true);
                    player.closeInventory();
                }
            }
        }

        if (inventory == null) {
            inventory = Bukkit.createInventory(player, size, title);
        }

        inventory.setContents(new ItemStack[inventory.getSize()]);

        currentlyOpenedMenus.put(player.getName(), this);

        for (Map.Entry<Integer, Button> buttonEntry : this.buttons.entrySet()) {
            inventory.setItem(buttonEntry.getKey(), createItemStack(player, buttonEntry.getValue()));
        }

        if (this.usePlaceholder()) {
            for (int index = 0; index < size; index++) {
                if (this.buttons.get(index) == null) {
                    this.buttons.put(index, new Button() {
                        @Override
                        public ItemStack getButtonItem(Player player) {
                            return getPlaceholderItem(player);
                        }
                    });
                    inventory.setItem(index, this.placeholderButton.getButtonItem(player));
                }
            }
        }

        if (update) {
            player.updateInventory();
        } else {
            player.openInventory(inventory);
        }

        this.setClosedByMenu(false);
        if (autoUpdate && task == null) {
            task = SharkHub.getInstance().getServer().getScheduler().runTaskTimer(SharkHub.getInstance(), () -> this.openMenu(player), 0, 20L);
        }
    }

    public int size(Map<Integer, Button> buttons) {
        int highest = 0;

        for (int buttonValue : buttons.keySet()) {
            if (buttonValue > highest) {
                highest = buttonValue;
            }
        }

        return (int) (Math.ceil((highest + 1) / 9D) * 9D);
    }

    public int getSlot(int x, int y) {
        return ((9 * y) + x);
    }

    public int getSize() {
        return -1;
    }

    public abstract String getTitle(Player player);

    public abstract Map<Integer, Button> getButtons(Player player);

    public void onClose(Player player) {
        if (task != null) {
            task.cancel();
        }
    }

    public boolean usePlaceholder() {
        return false;
    }

    public ItemStack getPlaceholderItem(Player player) {
        return new ItemBuilder(XMaterial.GLASS_PANE.parseMaterial()).data(3).build();
    }
}