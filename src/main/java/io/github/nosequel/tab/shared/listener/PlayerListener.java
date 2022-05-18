package io.github.nosequel.tab.shared.listener;

import lombok.RequiredArgsConstructor;
import io.github.nosequel.tab.shared.TabHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

@RequiredArgsConstructor
public class PlayerListener implements Listener {

    private TabHandler handler;

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        this.handler.sendUpdate(event.getPlayer());
    }
}