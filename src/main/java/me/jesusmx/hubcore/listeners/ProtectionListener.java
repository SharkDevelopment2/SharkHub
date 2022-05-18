package me.jesusmx.hubcore.listeners;

import me.jesusmx.hubcore.SharkHub;
import me.jesusmx.hubcore.util.CC;
import me.jesusmx.hubcore.util.files.ConfigFile;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.List;

public class ProtectionListener implements Listener {

    private ConfigFile toggle = SharkHub.getInstance().getTogglesConfig();
    private ConfigFile config = SharkHub.getInstance().getSettingsConfig();

    @EventHandler
    public void onPreCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        if (!toggle.getBoolean("addons.protection")) return;
        String command = event.getMessage().split(" ")[0];
        List<String> blockedMessage = config.getStringList("addons.protection.blocked-commands");
        if (command.startsWith("/")) {
            command = command.substring(1);
        }
        if (blockedMessage.contains(command.toLowerCase())) {
            player.sendMessage(CC.translate(config.getString("addons.protection.message")));
            event.setCancelled(true);
        }
    }
}
