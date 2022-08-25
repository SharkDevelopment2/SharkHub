package es.hulk.hub.providers;

import es.hulk.hub.SharkHub;
import es.hulk.hub.util.CC;
import es.hulk.hub.util.ServerUtil;
import es.hulk.hub.util.files.ConfigFile;
import es.hulk.hub.util.rank.Rank;
import es.hulk.tablist.TabColumn;
import es.hulk.tablist.TabLayout;
import es.hulk.tablist.provider.TabProvider;
import es.hulk.tablist.skin.Skin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

public class TablistProvider implements TabProvider {
    private final ConfigFile tablistConfig = SharkHub.getInstance().getTablistConfig();

    @Override
    public Set<TabLayout> getProvider(Player player) {
        Set<TabLayout> layoutSet = new HashSet<>();
        Rank rank = SharkHub.getInstance().getRankManager().getRank();
        String tablistType = tablistConfig.getString("TABLIST.TYPE");
        List<UUID> sorted = Bukkit.getOnlinePlayers().stream().map(
                Player::getUniqueId)
                .sorted(Comparator.comparing(rank::getWeight).thenComparing(rank::getWeight).reversed())
                .collect(Collectors.toList());

        for (int i = 1; i <= 20; i++) {
            if (tablistType.equals("VANILLA")) {
                int playerSize = 0;
                int column = 0;
                int row = 1;

                for (UUID uuid : sorted) {
                    Player online = Bukkit.getPlayer(uuid);
                    playerSize++;
                    if (playerSize >= 60) break;

                    String path = tablistConfig.getString("TABLIST.PLAYER_PREFIX").replace("%color%", rank.getColor(online.getUniqueId()));
                    String prefix = CC.translate(player, path, true);

                    layoutSet.add(new TabLayout(TabColumn.getColumn(column++), row)
                            .setText(prefix + online.getName())
                            .setSkin(Skin.getSkin(online))
                            .setPing(ServerUtil.getPing(online)));

                    if (column == 4) {
                        column = 0;
                        row++;
                    }
                }
            } else if (tablistType.equals("CUSTOM")) {

                layoutSet.add(new TabLayout(TabColumn.LEFT, i)
                        .setText(CC.translate(player, ServerUtil.replaceText(player, getLines("LEFT", i, "TEXT")), true))
                        .setSkin(getSkin(player, getLines("LEFT", i, "HEAD")))
                        .setPing(tablistConfig.getInt("TABLIST.PING")));
                layoutSet.add(new TabLayout(TabColumn.MIDDLE, i)
                        .setText(CC.translate(player, ServerUtil.replaceText(player, getLines("MIDDLE", i, "TEXT")), true))
                        .setSkin(getSkin(player, getLines("MIDDLE", i, "HEAD")))
                        .setPing(tablistConfig.getInt("TABLIST.PING")));
                layoutSet.add(new TabLayout(TabColumn.RIGHT, i)
                        .setText(CC.translate(player, ServerUtil.replaceText(player, getLines("RIGHT", i, "TEXT")), true))
                        .setSkin(getSkin(player, getLines("RIGHT", i, "HEAD")))
                        .setPing(tablistConfig.getInt("TABLIST.PING")));
                layoutSet.add(new TabLayout(TabColumn.FAR_RIGHT, i)
                        .setText(CC.translate(player, ServerUtil.replaceText(player, getLines("FAR_RIGHT", i, "TEXT")), true))
                        .setSkin(getSkin(player, getLines("FAR_RIGHT", i, "HEAD")))
                        .setPing(tablistConfig.getInt("TABLIST.PING")));
            }
        }

        return layoutSet;
    }

    @Override
    public List<String> getFooter(Player player) {
        return headerFooterList(tablistConfig.getStringList("TABLIST.FOOTER"), player);
    }

    @Override
    public List<String> getHeader(Player player) {
        return headerFooterList(tablistConfig.getStringList("TABLIST.HEADER"), player);
    }

    public Skin getSkin(Player player, String skinTab) {
        Skin skinDefault = Skin.DEFAULT;

        if (skinTab.contains("%PLAYER%")) {
            skinDefault = Skin.getSkin(player);
        }
        if (skinTab.contains("%DISCORD%")) {
            skinDefault = Skin.DISCORD_SKIN;
        }
        if (skinTab.contains("%YOUTUBE%")) {
            skinDefault = Skin.YOUTUBE_SKIN;
        }
        if (skinTab.contains("%TWITTER%")) {
            skinDefault = Skin.TWITTER_SKIN;
        }
        if (skinTab.contains("%FACEBOOK%")) {
            skinDefault = Skin.FACEBOOK_SKIN;
        }
        if (skinTab.contains("%STORE%")) {
            skinDefault = Skin.STORE_SKIN;
        }
        if (skinTab.contains("%GREEN%")) {
            skinDefault = Skin.getDot(ChatColor.GREEN);
        }
        if (skinTab.contains("%BLUE%")) {
            skinDefault = Skin.getDot(ChatColor.BLUE);
        }
        if (skinTab.contains("%DARK_BLUE%")) {
            skinDefault = Skin.getDot(ChatColor.DARK_BLUE);
        }
        if (skinTab.contains("%DARK_AQUA%")) {
            skinDefault = Skin.getDot(ChatColor.DARK_AQUA);
        }
        if (skinTab.contains("%DARK_PURPLE%")) {
            skinDefault = Skin.getDot(ChatColor.DARK_PURPLE);
        }
        if (skinTab.contains("%LIGHT_PURPLE%")) {
            skinDefault = Skin.getDot(ChatColor.LIGHT_PURPLE);
        }
        if (skinTab.contains("%GRAY%")) {
            skinDefault = Skin.getDot(ChatColor.GRAY);
        }
        if (skinTab.contains("%RED%")) {
            skinDefault = Skin.getDot(ChatColor.RED);
        }
        if (skinTab.contains("%YELLOW%")) {
            skinDefault = Skin.getDot(ChatColor.YELLOW);
        }
        if (skinTab.contains("%DARK_GREEN%")) {
            skinDefault = Skin.getDot(ChatColor.DARK_GREEN);
        }
        if (skinTab.contains("%DARK_RED%")) {
            skinDefault = Skin.getDot(ChatColor.DARK_RED);
        }
        if (skinTab.contains("%GOLD%")) {
            skinDefault = Skin.getDot(ChatColor.GOLD);
        }
        if (skinTab.contains("%AQUA%")) {
            skinDefault = Skin.getDot(ChatColor.AQUA);
        }
        if (skinTab.contains("%WHITE%")) {
            skinDefault = Skin.getDot(ChatColor.WHITE);
        }
        if (skinTab.contains("%DARK_GRAY%")) {
            skinDefault = Skin.getDot(ChatColor.DARK_GRAY);
        }
        if (skinTab.contains("%BLACK%")) {
            skinDefault = Skin.getDot(ChatColor.BLACK);
        }
        if (skinTab.contains("%WARNING%")) {
            skinDefault = Skin.WARNING_SKIN;
        }
        if (skinTab.contains("%WEBSITE%")) {
            skinDefault = Skin.WEBSITE_SKIN;
        }
        if (skinTab.contains("%QUEUE%")) {
            skinDefault = Skin.QUEUE_SKIN;
        }
        if (skinTab.contains("%INFORMATION%")) {
            skinDefault = Skin.INFORMATION_SKIN;
        }
        if (skinTab.contains("%WOOD_SHIELD%")) {
            skinDefault = Skin.WOOD_SHIELD_SKIN;
        }
        if (skinTab.contains("%DIAMOND_SHIELD%")) {
            skinDefault = Skin.DIAMOND_SHIELD_SKIN;
        }
        if (skinTab.contains("%BOW%")) {
            skinDefault = Skin.BOW_SKIN;
        }
        if (skinTab.contains("%POTION%")) {
            skinDefault = Skin.POTION_SKIN;
        }
        if (skinTab.contains("%TELEGRAM%")) {
            skinDefault = Skin.TELEGRAM_SKIN;
        }
        if (skinTab.contains("%ENDERCHEST%")) {
            skinDefault = Skin.ENDERCHEST_SKIN;
        }
        if (skinTab.contains("%COIN%")) {
            skinDefault = Skin.COIN_SKIN;
        }
        if (skinTab.contains("%HEART%")) {
            skinDefault = Skin.HEART_SKIN;
        }
        if (skinTab.contains("%EARTH%")) {
            skinDefault = Skin.EARTH_SKIN;
        }
        if (skinTab.contains("%CROWN%")) {
            skinDefault = Skin.CROWN_SKIN;
        }
        return skinDefault;
    }

    private List<String> headerFooterList(List<String> path, Player player) {
        List<String> list = new ArrayList<>();

        for (String str : path) {
            list.add(CC.translate(player, str, true));
        }
        return list;
    }

    private String getLines(String column, int position, String textOrHead) {
        return tablistConfig.getString("TABLIST.LINES." + column + "." + position + "." + textOrHead);
    }
}
