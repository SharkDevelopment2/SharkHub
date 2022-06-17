package es.hulk.hub.util.rank.impl;

import dev.phoenix.phoenix.PhoenixAPI;
import dev.phoenix.phoenix.profile.Profile;
import es.hulk.hub.util.rank.Rank;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PhoenixCore implements Rank {

    @Override
    public String getName(UUID uuid) {
        Profile profile = PhoenixAPI.INSTANCE.getProfile(uuid);
        return profile.getBestRank().getName();
    }

    @Override
    public String getPrefix(UUID uuid) {
        Profile profile = PhoenixAPI.INSTANCE.getProfile(uuid);
        return profile.getBestRank().getPrefix();
    }

    @Override
    public String getSuffix(UUID uuid) {
        Profile profile = PhoenixAPI.INSTANCE.getProfile(uuid);
        return profile.getBestRank().getSuffix();
    }

    @Override
    public String getColor(UUID uuid) {
        Profile profile = PhoenixAPI.INSTANCE.getProfile(uuid);
        return profile.getBestRank().getColor();
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
