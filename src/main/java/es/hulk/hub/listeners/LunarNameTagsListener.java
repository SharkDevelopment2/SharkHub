package es.hulk.hub.listeners;

import com.lunarclient.bukkitapi.LunarClientAPI;
import es.hulk.hub.SharkHub;
import es.hulk.hub.util.CC;
import es.hulk.hub.util.files.ConfigFile;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;
import java.util.List;

public class LunarNameTagsListener implements Listener {

    public LunarNameTagsListener() {
        Bukkit.getPluginManager().registerEvents(this, SharkHub.getInstance());
    }

    private final ConfigFile config = SharkHub.getInstance().getNametagsConfig();

    @EventHandler
    public void putLunarNametags(PlayerJoinEvent event) {
        if (!config.getBoolean("LUNAR_NAMETAGS.ENABLE")) return;

        Bukkit.getScheduler().runTaskLaterAsynchronously(SharkHub.getInstance(), () -> {
            Player player = event.getPlayer();
            List<String> nametags = new ArrayList<>();
            ConfigurationSection lunarSection = config.getConfiguration().getConfigurationSection("LUNAR_NAMETAGS");

            for (String str : lunarSection.getKeys(false)) {
                String path = "LUNAR_NAMETAGS." + str + ".";
                if (player.hasPermission(config.getString(path + "PERMISSION"))) {
                    nametags.addAll(config.getStringList(path + "DISPLAY"));
                    break;
                }
            }

            if (nametags.isEmpty()) return;

            for (Player online : Bukkit.getOnlinePlayers()) {
                LunarClientAPI.getInstance().overrideNametag(player, CC.translate(nametags), online);
            }

        }, 5L);
    }
}
