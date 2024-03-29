package es.hulk.hub.listeners;

import com.cryptomorin.xseries.messages.ActionBar;
import com.cryptomorin.xseries.messages.Titles;
import es.hulk.hub.SharkHub;
import es.hulk.hub.hotbar.HotbarManager;
import es.hulk.hub.util.CC;
import es.hulk.hub.util.ServerUtil;
import es.hulk.hub.util.files.ConfigFile;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinListener implements Listener {

    public JoinListener() {
        Bukkit.getPluginManager().registerEvents(this, SharkHub.getInstance());
    }

    private final ConfigFile config = SharkHub.getInstance().getMainConfig();

    @EventHandler
    public void registerListeners(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        HotbarManager.setHotbarItems(player);
        event.setJoinMessage(null);
        SharkHub.getInstance().getSpawnManager().sendToSpawn(player);

        player.setHealth(20.0D);
        player.setFoodLevel(20);
        player.setAllowFlight(true);

        if (config.getBoolean("JOIN_PLAYER.MESSAGE.ENABLE")) {
            for (String str : config.getStringList("JOIN_PLAYER.MESSAGE.LINES")) {
                player.sendMessage(ServerUtil.replaceText(player, SharkHub.getInstance().isPlaceholderAPI() ? PlaceholderAPI.setPlaceholders(player, str) : str));
            }
        }

        if (config.getBoolean("JOIN_PLAYER.SOUND.ENABLE"))
            player.playSound(player.getLocation(), Sound.valueOf(config.getString("JOIN_PLAYER.SOUND.VALUE").toUpperCase()), 1.0F, 1.0F);

        if (config.getBoolean("JOIN_PLAYER.SPEED.ENABLE")) {
            player.setWalkSpeed(Float.parseFloat(config.getString("JOIN_PLAYER.SPEED.VALUE")));
            player.setFlySpeed(Float.parseFloat(config.getString("JOIN_PLAYER.SPEED.VALUE")));
        }

        if (config.getBoolean("JOIN_PLAYER.ACTION_BAR.ENABLE")) {
            ActionBar.sendActionBar(SharkHub.getInstance(),
                    player,
                    ServerUtil.replaceText(player, CC.translate(player, config.getString("JOIN_PLAYER.ACTION_BAR.MESSAGE"), true)),
                    (long) config.getDouble("JOIN_PLAYER.ACTION_BAR.DURATION"));
        }

        if (config.getBoolean("JOIN_PLAYER.TITLES.ENABLE")) {
            String path = "JOIN_PLAYER.TITLES.";
            String title = ServerUtil.replaceText(player, CC.translate(config.getString(path + ".TITLE")));
            String subTitle = ServerUtil.replaceText(player, CC.translate(config.getString(path + ".SUBTITLE")));

            int fadeIn = config.getInt(path + "FADEIN");
            int stay = config.getInt(path + "STAY");
            int fadeOut = config.getInt(path + "FADEOUT");

            Titles.sendTitle(player, fadeIn, stay, fadeOut, title, subTitle);
        }


        if (config.getBoolean("VIP_MESSAGE.JOIN.ENABLE")) {
            if (config.getString("VIP_MESSAGE.JOIN.PERMISSION") == null) return;
            if (player.hasPermission(config.getString("VIP_MESSAGE.JOIN.PERMISSION"))) {
                String str = config.getString("VIP_MESSAGE.JOIN.MESSAGE");
                Bukkit.broadcastMessage(CC.translate(player, ServerUtil.replaceText(player, CC.translate(str)), true));
            }
        }
    }

    @EventHandler
    public void vipQuitMessage(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        event.setQuitMessage(null);

        if (config.getBoolean("VIP_MESSAGE.LEAVE.ENABLE")) {
            if (config.getString("VIP_MESSAGE.LEAVE.PERMISSION") == null) return;
            if (player.hasPermission(config.getString("VIP_MESSAGE.LEAVE.PERMISSION"))) {
                String str = config.getString("VIP_MESSAGE.LEAVE.MESSAGE");
                Bukkit.broadcastMessage(CC.translate(player, ServerUtil.replaceText(player, CC.translate(str)), true));
            }
        }
    }
}