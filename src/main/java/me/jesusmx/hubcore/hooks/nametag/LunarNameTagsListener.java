package me.jesusmx.hubcore.hooks.nametag;

import com.lunarclient.bukkitapi.LunarClientAPI;
import me.jesusmx.hubcore.SharkHub;
import me.jesusmx.hubcore.util.CC;
import me.jesusmx.hubcore.util.files.ConfigFile;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.List;
import java.util.stream.Collectors;

public class LunarNameTagsListener implements Listener {

    private ConfigFile config = SharkHub.getInstance().getNametagsConfig();
    private ConfigFile toggles = SharkHub.getInstance().getTogglesConfig();

    @EventHandler
    public void lunarTags(PlayerJoinEvent event) {
        if (!toggles.getBoolean("addons.lunar-nametags")) return;
        Bukkit.getScheduler().runTaskLaterAsynchronously(SharkHub.getInstance(), () -> {
            Player player = event.getPlayer();
            List<String> show = null;
            for (String s : config.getConfiguration().getConfigurationSection("lunar-nametags").getKeys(false)) {
                String path = "lunar-nametags." + s + ".";
                if (player.hasPermission(config.getString(path + "permission"))) {
                    show = config.getStringList(path + "show");
                    break;
                }
            }

            if (show == null || show.isEmpty()) return;
            show = show.stream().map(s -> s.replace("%player-name%", player.getDisplayName())).collect(Collectors.toList());
            List<String> finalShow = show;
            SharkHub.getInstance().getOnlinePlayers().forEach(target -> {
                LunarClientAPI.getInstance().overrideNametag(player, CC.translate(finalShow), target);
            });
        }, 5L);
    }
}
