package me.jesusmx.hubcore.hooks.permissions.type;

import me.jesusmx.hubcore.hooks.permissions.Rank;
import me.activated.core.plugin.AquaCoreAPI;
import org.bukkit.entity.Player;

public class AquaCoreRank implements Rank {

    @Override
    public String getRankColor(Player player) {
        return AquaCoreAPI.INSTANCE.getPlayerRank(player.getUniqueId()).getDisplayName();
    }

    @Override
    public String getRank(Player player) {
        return AquaCoreAPI.INSTANCE.getPlayerRank(player.getUniqueId()).getName();
    }
}
