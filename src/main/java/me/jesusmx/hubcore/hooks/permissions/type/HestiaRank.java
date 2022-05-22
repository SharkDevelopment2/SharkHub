package me.jesusmx.hubcore.hooks.permissions.type;

import me.jesusmx.hubcore.hooks.permissions.Rank;
import me.quartz.hestia.HestiaAPI;
import org.bukkit.entity.Player;

public class HestiaRank implements Rank {

    @Override
    public String getRankColor(Player player) {
        return HestiaAPI.instance.getRankColor(player.getUniqueId()).toString();
    }

    @Override
    public String getRank(Player player) {
        return HestiaAPI.instance.getRank(player.getUniqueId());
    }
}
