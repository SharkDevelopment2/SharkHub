package es.hulk.hub.listeners;

import es.hulk.hub.SharkHub;
import es.hulk.hub.util.CC;
import es.hulk.hub.util.files.ConfigFile;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.List;

public class ProtectionListener implements Listener {

    public ProtectionListener() {
        Bukkit.getPluginManager().registerEvents(this, SharkHub.getInstance());
    }

    private final ConfigFile config = SharkHub.getInstance().getMainConfig();

    @EventHandler
    public void onPreCommand(PlayerCommandPreprocessEvent event) {
        if (!config.getBoolean("BLOCKED_COMMANDS.ENABLE")) return;

        Player player = event.getPlayer();
        String command = event.getMessage().split(" ")[0];
        List<String> blockedMessage = config.getStringList("BLOCKED_COMMANDS.COMMANDS");

        if (command.startsWith("/")) command = command.substring(1);
        if (blockedMessage.contains(command.toLowerCase())) {
            player.sendMessage(CC.translate(config.getString("BLOCKED_COMMANDS.MESSAGE")));
            event.setCancelled(true);
        }
    }
}
