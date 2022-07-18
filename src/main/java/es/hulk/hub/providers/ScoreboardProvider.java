package es.hulk.hub.providers;

import com.bizarrealex.aether.scoreboard.Board;
import com.bizarrealex.aether.scoreboard.BoardAdapter;
import com.bizarrealex.aether.scoreboard.cooldown.BoardCooldown;
import es.hulk.hub.SharkHub;
import es.hulk.hub.managers.customtimer.CustomTimer;
import es.hulk.hub.managers.queue.QueueManager;
import es.hulk.hub.pvpmode.PvPModeHandler;
import es.hulk.hub.util.CC;
import es.hulk.hub.util.JavaUtils;
import es.hulk.hub.util.ServerUtil;
import es.hulk.hub.util.files.ConfigFile;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class ScoreboardProvider implements BoardAdapter {

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
    public List<String> getScoreboard(Player player, Board board, Set<BoardCooldown> cooldowns) {
        List<String> toReturn = new ArrayList<>();
        QueueManager queueM = SharkHub.getInstance().getQueueManager();
        if (PvPModeHandler.isOnPvPMode(player)) {
            for (String str : config.getStringList("SCOREBOARD.MODES.PVP_MODE")) {
                if (str.contains("custom_timer%")) {
                    customTimerLines(toReturn);
                    continue;
                }
                toReturn.add(ServerUtil.replaceText(player, str.replace("%kills%", String.valueOf(PvPModeHandler.getKills().getOrDefault(player.getUniqueId(), 0))).replace("%duration%", String.valueOf(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - PvPModeHandler.getTime(player))))));
            }
        } else if (SharkHub.getInstance().getQueueManager().getSystem().isInQueue(player)) {
            for (String str : config.getStringList("SCOREBOARD.MODES.QUEUE")) {
                if (str.contains("custom_timer%")) {
                    customTimerLines(toReturn);
                    continue;
                }
                str = str.replace("%queue_server%", queueM.getSystem().getServer(player))
                        .replace("%queue_position%", String.valueOf(queueM.getSystem().getPosition(player)))
                        .replace("%queue_size%", String.valueOf(queueM.getSystem().getSize(player)));
                toReturn.add(ServerUtil.replaceText(player, str));
            }
        } else {
            for (String str : config.getStringList("SCOREBOARD.MODES.NORMAL")) {
                if (str.contains("custom_timer%")) {
                    customTimerLines(toReturn);
                    continue;
                }
                toReturn.add(ServerUtil.replaceText(player, str));
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

    public void customTimerLines(List<String> lines) {
        for (CustomTimer customTimer : SharkHub.getInstance().getCustomTimerManager().getCustomTimers()) {
            for (String customTimerLine : config.getStringList("SCOREBOARD.CUSTOM_TIMER")) {
                lines.add(
                        customTimerLine.replace("%displayname%",
                                customTimer.getDisplayName()).replace("%duration%", JavaUtils.formatLongHour(customTimer.getRemaining())));
            }
        }
    }
}