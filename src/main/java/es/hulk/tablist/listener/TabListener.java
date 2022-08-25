package es.hulk.tablist.listener;

import es.hulk.tablist.Tablist;
import es.hulk.tablist.TablistModule;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.Team;

import java.util.UUID;

/**
 * Created By LeandroSSJ
 * Created on 31/07/2022
 */
public class TabListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID uniqueId = player.getUniqueId();

        TablistModule.INSTANCE.getTabStorage().put(uniqueId, new Tablist(player, TablistModule.INSTANCE.getProvider()));
    }

    private void handleDisconnect(Player player) {
        Team team = player.getScoreboard().getTeam("\\u000181");
        if (team != null) {
            team.unregister();
        }

        TablistModule.INSTANCE.getTabStorage().remove(player.getUniqueId());
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerQuit(PlayerQuitEvent event) {
        handleDisconnect(event.getPlayer());
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
    public void onPlayerKick(PlayerKickEvent event) {
        handleDisconnect(event.getPlayer());
    }
}
