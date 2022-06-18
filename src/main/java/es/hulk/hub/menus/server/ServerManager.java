package es.hulk.hub.menus.server;

import com.cryptomorin.xseries.XMaterial;
import es.hulk.hub.SharkHub;
import es.hulk.hub.util.CC;
import es.hulk.hub.util.ItemMaker;
import es.hulk.hub.util.ServerUtil;
import es.hulk.hub.util.bukkit.ItemBuilder;
import es.hulk.hub.util.files.ConfigFile;
import lombok.Getter;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ServerManager {

    private final List<Server> serverList = new ArrayList<>();
    private final ConfigFile serverConfig = SharkHub.getInstance().getSelectorConfig();

    public void load() {
        this.serverList.clear();

        ConfigurationSection section = serverConfig.getConfiguration().getConfigurationSection("SERVER_SELECTOR.ITEMS");

        for (String str : section.getKeys(false)) {

            String name = str;
            String displayName = serverConfig.getString("SERVER_SELECTOR.ITEMS." + str + ".DISPLAY_NAME");
            Material material = Material.getMaterial(serverConfig.getString("SERVER_SELECTOR.ITEMS." + str + ".MATERIAL"));
            int data = serverConfig.getInt("SERVER_SELECTOR.ITEMS." + str + ".DATA");

            boolean headEnabled = serverConfig.getBoolean("SERVER_SELECTOR.ITEMS." + str + ".HEAD.ENABLE");
            String headName = serverConfig.getString("SERVER_SELECTOR.ITEMS." + str + ".HEAD.NAME");

            boolean queue = serverConfig.getBoolean("SERVER_SELECTOR.ITEMS." + str + ".QUEUE");
            String serverName = serverConfig.getString("SERVER_SELECTOR.ITEMS." + str + ".SERVER_NAME");

            int slot = serverConfig.getInt("SERVER_SELECTOR.ITEMS." + str + ".SLOT");
            List<String> lore = serverConfig.getStringList("SERVER_SELECTOR.ITEMS." + str + ".LORE");

            boolean subServer = serverConfig.getBoolean("SERVER_SELECTOR.ITEMS." + str + ".SUB_SERVER");
            int amount = serverConfig.getInt("SERVER_SELECTOR.ITEMS." + str + ".AMOUNT");

            boolean commandsEnabled = serverConfig.getBoolean("SERVER_SELECTOR.ITEMS." + str + ".COMMANDS.ENABLE");
            List<String> commands = serverConfig.getStringList("SERVER_SELECTOR.ITEMS." + str + ".COMMANDS.COMMANDS");

            this.serverList.add(new Server(name, displayName, material, data, headEnabled, headName, queue, serverName, slot, lore, subServer, amount, commandsEnabled, commands));
        }

        CC.sendConsole("&bLoaded &e" + serverList.size() + " &bServers");
    }

    public static ItemStack getItemStackFromServer(Player player, Server server) {
        if (server.isHeadEnabled()) {
            ItemStack item;
            if (ServerUtil.getServerVersion().equalsIgnoreCase("v1_7_R4")) {
                item = new ItemStack(Material.SKULL_ITEM, (short) 3);
            } else {
                item = new ItemStack(XMaterial.CREEPER_HEAD.parseMaterial(), (short) 3);
            }
            SkullMeta skull = (SkullMeta) item.getItemMeta();
            skull.setOwner(server.getHeadName());
            skull.setDisplayName(PlaceholderAPI.setPlaceholders(player, server.getDisplayName()));
            skull.setLore(PlaceholderAPI.setPlaceholders(player, server.getLore()));
            item.setItemMeta(skull);
            return item;
        }
        return new ItemMaker(server.getMaterial(), server.getAmount(), (short) server.getData())
                .lore(SharkHub.getInstance().isPlaceholderAPI() ? PlaceholderAPI.setPlaceholders(player, server.getLore()) : server.getLore())
                .displayName(SharkHub.getInstance().isPlaceholderAPI() ? PlaceholderAPI.setPlaceholders(player, server.getDisplayName()) : server.getDisplayName())
                .build();
    }


}
