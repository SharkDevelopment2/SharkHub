package me.jesusmx.hubcore.hooks.permissions.type;

import club.frozed.core.ZoomAPI;
import me.jesusmx.hubcore.hooks.permissions.Rank;
import org.bukkit.entity.Player;


public class ZoomRank implements Rank {

    @Override
    public String getRankColor(Player player) {
        return null;
    }

    @Override
    public String getRank(Player player) {
        return ZoomAPI.getRankName(player);
    }
}
