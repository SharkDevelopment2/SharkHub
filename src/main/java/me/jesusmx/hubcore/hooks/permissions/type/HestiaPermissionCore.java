package me.jesusmx.hubcore.hooks.permissions.type;

import me.jesusmx.hubcore.hooks.permissions.PermissionCore;
import me.quartz.hestia.HestiaAPI;
import org.bukkit.entity.Player;

public class HestiaPermissionCore implements PermissionCore {

    @Override
    public String getRankColor(Player player) {
        return HestiaAPI.instance.getRankColor(player.getUniqueId()).toString();
    }

    @Override
    public String getRank(Player player) {
        return HestiaAPI.instance.getRank(player.getUniqueId());
    }
}
