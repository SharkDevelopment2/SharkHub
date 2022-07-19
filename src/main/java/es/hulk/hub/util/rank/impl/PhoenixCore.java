package es.hulk.hub.util.rank.impl;

import dev.phoenix.phoenix.PhoenixAPI;
import dev.phoenix.phoenix.profile.Profile;
import es.hulk.hub.util.rank.Rank;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PhoenixCore implements Rank {

    @Override
    public String getName(UUID uuid) {
        Player player = Bukkit.getPlayer(uuid);
        return PhoenixAPI.INSTANCE.getPlayerRank(player.getUniqueId()).getName();
    }

    @Override
    public String getPrefix(UUID uuid) {
        Player player = Bukkit.getPlayer(uuid);
        return PhoenixAPI.INSTANCE.getPlayerRank(player.getUniqueId()).getPrefix();
    }

    @Override
    public String getSuffix(UUID uuid) {
        Player player = Bukkit.getPlayer(uuid);
        return PhoenixAPI.INSTANCE.getPlayerRank(player.getUniqueId()).getSuffix();
    }

    @Override
    public String getColor(UUID uuid) {
        Player player = Bukkit.getPlayer(uuid);
        return PhoenixAPI.INSTANCE.getPlayerRank(player.getUniqueId()).getColor();
    }

    @Override
    public String getRealName(Player player) {
        return "";
    }

    @Override
    public int getWeight(UUID uuid) {
        return 0;
    }
}
