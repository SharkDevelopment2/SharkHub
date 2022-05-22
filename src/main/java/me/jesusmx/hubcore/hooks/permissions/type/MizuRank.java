package me.jesusmx.hubcore.hooks.permissions.type;

import com.broustudio.MizuAPI.MizuAPI;
import me.jesusmx.hubcore.hooks.permissions.Rank;
import org.bukkit.entity.Player;


public class MizuRank implements Rank {

    @Override
    public String getRankColor(Player player) {
        return MizuAPI.getAPI().getRankColor(MizuAPI.getAPI().getRank(player.getUniqueId()));
    }

    @Override
    public String getRank(Player player) {
        return MizuAPI.getAPI().getRank(player.getUniqueId());
    }

}
