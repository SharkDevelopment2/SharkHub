package es.hulk.hub.menus.server;

import es.hulk.hub.SharkHub;
import es.hulk.hub.util.files.ConfigFile;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

public class ServerManager {

    @Getter private static final List<Server> serverList = new ArrayList<>();
    private static final ConfigFile serverConfig = SharkHub.getInstance().getSelectorConfig();

    public static void load() {
        serverList.clear();

        ConfigurationSection section = serverConfig.getConfiguration().getConfigurationSection("SERVER_SELECTOR.ITEMS");

        for (String str : section.getKeys(false)) {

            String name = serverConfig.getString("SERVER_SELECTOR.ITEMS." + str);
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

            serverList.add(new Server(name, displayName, material, data, headEnabled, headName, queue, serverName, slot, lore, subServer, amount, commandsEnabled, commands));
        }
    }

    public static Server getServerByServerName(String serverName) {
        for (Server server : serverList) {
            if (server.getServerName().equalsIgnoreCase(serverName)) {
                return server;
            }
        }
        return null;
    }


}
