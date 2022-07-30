package es.hulk.hub.providers;

import com.google.common.collect.Lists;
import es.hulk.hub.SharkHub;
import es.hulk.hub.managers.customtimer.CustomTimer;
import es.hulk.hub.managers.queue.QueueManager;
import es.hulk.hub.util.AetherAnimation;
import es.hulk.hub.util.CC;
import es.hulk.hub.util.JavaUtils;
import es.hulk.hub.util.ServerUtil;
import es.hulk.hub.util.files.ConfigFile;
import es.hulk.hub.util.scoreboard.ScoreboardAdapter;
import es.hulk.hub.util.scoreboard.ScoreboardStyle;
import org.bukkit.entity.Player;

import java.util.List;

public class ScoreboardProvider implements ScoreboardAdapter {

    private final ConfigFile config;
    private final SharkHub plugin;

    public ScoreboardProvider(SharkHub plugin) {
        this.config = plugin.getScoreboardConfig();
        this.plugin = plugin;
    }

    @Override
    public String getTitle(Player player) {
        if (config.getBoolean("ANIMATED_SCOREBOARD.TITLE.ENABLE")) return AetherAnimation.getScoreboardTitle();
        else return CC.translate(config.getString("SCOREBOARD.TITLE"));
    }

    @Override
    public List<String> getLines(Player player) {
        List<String> lines = Lists.newArrayList();
        QueueManager queueM = SharkHub.getInstance().getQueueManager();

        for (String line : config.getStringList("SCOREBOARD.NORMAL")) {
            if (line.contains("%queue")) {
                if (queueM.getSystem().isInQueue(player)) {
                    for (String queue : config.getStringList("SCOREBOARD.QUEUE")) {
                        lines.add(queue.replace("%queue_server%", queueM.getSystem().getServer(player))
                                .replace("%queue_position%", String.valueOf(queueM.getSystem().getPosition(player)))
                                .replace("%queue_size%", String.valueOf(queueM.getSystem().getSize(player))));
                    }
                }
                continue;
            }

            if (line.contains("%custom_timer%")) {
                for (CustomTimer customTimer : plugin.getCustomTimerManager().getCustomTimers()) {
                    for (String timers : config.getStringList("SCOREBOARD.CUSTOM_TIMER")) {
                        lines.add(timers
                                .replace("%displayname%", customTimer.getDisplayName())
                                .replace("%duration%", JavaUtils.formatLongHour(customTimer.getRemaining())));
                    }
                }
                continue;
            }

            if (line.contains("%footer%")) {
                lines.add(AetherAnimation.getScoreboardFooter());
                continue;
            }

            lines.add(CC.translate(player, line, true));
        }

        return lines;
    }

    @Override
    public ScoreboardStyle getBoardStyle(Player player) {
        return ScoreboardStyle.MODERN;
    }
}
