package me.jesusmx.hubcore.providers;

import me.clip.placeholderapi.PlaceholderAPI;
import me.jesusmx.hubcore.SharkHub;
import me.jesusmx.hubcore.bungee.BungeeUtils;
import me.jesusmx.hubcore.pvpmode.cache.PvPModeHandler;
import me.jesusmx.hubcore.util.CC;
import io.github.thatkawaiisam.assemble.AssembleAdapter;
import me.jesusmx.hubcore.util.ServerUtil;
import me.jesusmx.hubcore.util.files.ConfigFile;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class ScoreboardProvider implements AssembleAdapter {

    private final ConfigFile config = SharkHub.getInstance().getScoreboardConfig();
    private long lastMillisFooter = System.currentTimeMillis();
    private long lastMillisTitle = System.currentTimeMillis();
    private int iFooter = 0;
    private int iTitle = 0;

    @Override
    public String getTitle(Player player) {
        boolean enabled = config.getBoolean("SCOREBOARD.TITLE.ANIMATION.ENABLED");
        return enabled ? titles() : config.getString("SCOREBOARD.TITLE.STATIC");
    }

    @Override
    public List<String> getLines(Player player) {
        List<String> toReturn = new ArrayList<>();
        if (PvPModeHandler.isOnPvPMode(player)) {
            for (String str : config.getStringList("SCOREBOARD.MODES.PVP_MODE")) {
                toReturn.add(ServerUtil.replaceText(player, str
                        .replace("%KILLS%", String.valueOf(PvPModeHandler.getKills().getOrDefault(player.getUniqueId(), 0)))
                        .replace("%DURATION%", String.valueOf(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - PvPModeHandler.getTime(player))))));
            }
        } else {
            if (SharkHub.getInstance().getQueueManager().getSystem().isInQueue(player)) {
                for (String str : config.getStringList("SCOREBOARD.MODES.QUEUE")) {
                    toReturn.add(ServerUtil.replaceText(player, str));
                }
            } else {
                if (player.isOp() && player.hasPermission("hubcore.scoreboard.staff")) {
                    for (String str : config.getStringList("SCOREBOARD.MODES.STAFF")) {
                        toReturn.add(ServerUtil.replaceText(player, str));
                    }
                } else {
                    for (String str : config.getStringList("SCOREBOARD.MODES.NORMAL")) {
                        toReturn.add(ServerUtil.replaceText(player, str));
                    }
                }
            }
        }
        if (config.getBoolean("SCOREBOARD.FOOTER.ANIMATION.ENABLED")) {
            toReturn = toReturn.stream().map(s -> s.replace("%FOOTER%", footer())).collect(Collectors.toList());
        }
        if (config.getBoolean("SCOREBOARD.TITLE.ANIMATION.ENABLED")) {
            toReturn = toReturn.stream().map(s -> s.replace("%TITLES%", titles())).collect(Collectors.toList());
        }

        return SharkHub.getInstance().isPlaceholderAPI() ? PlaceholderAPI.setPlaceholders(player, toReturn) : toReturn;
    }

    private String footer() {
        List<String> footers = CC.translate(config.getStringList("SCOREBOARD.FOOTER.ANIMATION.ANIMATED"));
        long time = System.currentTimeMillis();
        long interval = TimeUnit.SECONDS.toMillis(config.getInt("SCOREBOARD.FOOTER.ANIMATION.INTERVAL"));

        if (lastMillisFooter + interval <= time) {
            if (iFooter != footers.size() - 1) {
                iFooter++;
            } else {
                iFooter = 0;
            }
            lastMillisFooter = time;
        }
        return footers.get(iFooter);
    }

    private String titles() {
        List<String> titles = CC.translate(config.getStringList("SCOREBOARD.TITLE.ANIMATION.ANIMATED"));
        long time = System.currentTimeMillis();
        long interval = TimeUnit.SECONDS.toMillis(config.getInt("SCOREBOARD.TITLE.ANIMATION.INTERVAL"));

        if (lastMillisTitle + interval <= time) {
            if (iTitle != titles.size() - 1) {
                iTitle++;
            } else {
                iTitle = 0;
            }
            lastMillisTitle = time;
        }
        return titles.get(iTitle);
    }
}