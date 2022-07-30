package es.hulk.hub.util.rank.impl;

import es.hulk.hub.util.rank.Rank;
import org.bukkit.entity.Player;

import java.util.Comparator;
import java.util.UUID;

public class Default implements Rank {

    @Override
    public String getName(UUID uuid) {
        return "";
    }

    @Override
    public String getPrefix(UUID uuid) {
        return "";
    }

    @Override
    public String getSuffix(UUID uuid) {
        return "";
    }

    @Override
    public String getColor(UUID uuid) {
        return "";
    }

    @Override
    public String getRealName(Player player) {
        return null;
    }

    @Override
    public int getWeight(UUID uuid) {
        return 0;
    }
}
