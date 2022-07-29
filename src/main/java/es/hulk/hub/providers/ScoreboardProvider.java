package es.hulk.hub.providers;

import com.bizarrealex.aether.AetherAnimation;
import com.bizarrealex.aether.scoreboard.Board;
import com.bizarrealex.aether.scoreboard.BoardAdapter;
import com.bizarrealex.aether.scoreboard.cooldown.BoardCooldown;
import com.google.common.collect.Lists;
import es.hulk.hub.SharkHub;
import es.hulk.hub.managers.customtimer.CustomTimer;
import es.hulk.hub.managers.queue.QueueManager;
import es.hulk.hub.pvpmode.PvPModeHandler;
import es.hulk.hub.util.CC;
import es.hulk.hub.util.JavaUtils;
import es.hulk.hub.util.ServerUtil;
import es.hulk.hub.util.files.ConfigFile;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Set;

public class ScoreboardProvider implements BoardAdapter {

    private final ConfigFile config;
    private final SharkHub plugin;

    public ScoreboardProvider(SharkHub plugin) {
        this.config = plugin.getScoreboardConfig();
        this.plugin = plugin;
    }

    @Override
    public String getTitle(Player player) {
        if (config.getBoolean("ANIMATED_SCOREBOARD.TITLE.ENABLE")) return AetherAnimation.title;
        else return CC.translate(config.getString("SCOREBOARD.TITLE"));
    }

    @Override
    public List<String> getScoreboard(Player player, Board board, Set<BoardCooldown> cooldowns) {
        List<String> lines = Lists.newArrayList();
        QueueManager queueM = SharkHub.getInstance().getQueueManager();

        if (PvPModeHandler.isOnPvPMode(player)) {
            for (String line : config.getStringList("SCOREBOARD.COMBAT")) {
                lines.add(CC.translate(player,
                        ServerUtil.replaceText(player, line
                                .replace("%kills%", String.valueOf(PvPModeHandler.getKills().getOrDefault(player.getUniqueId(), 0)))
                                .replace("%duration%", JavaUtils.formatLongHour(PvPModeHandler.getTime(player)))),
                        true));
            }
        } else {
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

                lines.add(CC.translate(player, ServerUtil.replaceText(player, line), true));
            }

        }
        return lines;
    }
}
