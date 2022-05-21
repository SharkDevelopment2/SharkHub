package me.jesusmx.hubcore.providers;

import me.clip.placeholderapi.PlaceholderAPI;
import me.jesusmx.hubcore.SharkHub;
import me.jesusmx.hubcore.bungee.BungeeUtils;
import me.jesusmx.hubcore.pvpmode.cache.PvPModeHandler;
import me.jesusmx.hubcore.util.CC;
import io.github.thatkawaiisam.assemble.AssembleAdapter;
import me.jesusmx.hubcore.util.files.ConfigFile;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class ScoreboardProvider implements AssembleAdapter {

    private ConfigFile config = SharkHub.getInstance().getScoreboardConfig();
    private long lastMillisFooter = System.currentTimeMillis();
    private long lastMillisTitle = System.currentTimeMillis();
    private int iFooter = 0;
    private int iTitle = 0;

    @Override
    public String getTitle(Player player) {
        boolean enabled = config.getBoolean("scoreboard.title.animation.enabled");
        return enabled ? titles() : config.getString("scoreboard.title.static");
    }

    @Override
    public List<String> getLines(Player player) {
        List<String> toReturn = new ArrayList<>();
        if (PvPModeHandler.isOnPvPMode(player)) {
            toReturn = config.getStringList("scoreboard.mode.pvp-mode")
                    .stream()
                    .map(line -> line.replace("%rank%", SharkHub.getInstance().getPermissionCore().getRank(player)))
                    .map(line -> line.replace("%rank-color%", SharkHub.getInstance().getPermissionCore().getRankColor(player)))
                    .map(line -> line.replace("%online%", String.valueOf(BungeeUtils.getGlobalPlayers())))
                    .map(line -> line.replace("%kills%", String.valueOf(PvPModeHandler.getKills().getOrDefault(player.getUniqueId(), 0))))
                    .map(line -> line.replace("%duration%", String.valueOf(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - PvPModeHandler.getTime(player)))))
                    .collect(Collectors.toList());
        } else {
            if (SharkHub.getInstance().getQueueManager().inQueue(player)) {
                config.getStringList("scoreboard.mode.queue").stream()
                        .map(CC::translate)
                        .map(line -> line.replace("%players%", String.valueOf(BungeeUtils.getGlobalPlayers())))
                        .map(line -> line.replace("%rank%", SharkHub.getInstance().getPermissionCore().getRank(player)))
                        .map(line -> line.replace("%rank-color%", SharkHub.getInstance().getPermissionCore().getRankColor(player)))
                        .map(line -> line.replace("%server-queue%", String.valueOf(SharkHub.getInstance().getQueueManager().getQueueIn(player))))
                        .map(line -> line.replace("%position%", String.valueOf(SharkHub.getInstance().getQueueManager().getPosition(player))))
                        .map(line -> line.replace("%total%", String.valueOf(SharkHub.getInstance().getQueueManager().getInQueue(SharkHub.getInstance().getQueueManager().getQueueIn(player)))))
                        .forEach(toReturn::add);
            } else {
                if (player.isOp() && player.hasPermission("hubcore.scoreboard.staff")) {
                    config.getStringList("scoreboard.mode.staff").stream()
                            .map(line -> line.replace("%players%", String.valueOf(BungeeUtils.getGlobalPlayers())))
                            .map(line -> line.replace("%rank%", SharkHub.getInstance().getPermissionCore().getRank(player)))
                            .map(line -> line.replace("%rank-color%", SharkHub.getInstance().getPermissionCore().getRankColor(player)))
                            .forEach(toReturn::add);


                } else {
                    config.getStringList("scoreboard.mode.normal").stream()
                            .map(CC::translate)
                            .map(line -> line.replace("%players%", String.valueOf(BungeeUtils.getGlobalPlayers())))
                            .map(line -> line.replace("%rank%", SharkHub.getInstance().getPermissionCore().getRank(player)))
                            .map(line -> line.replace("%rank-color%", SharkHub.getInstance().getPermissionCore().getRankColor(player)))
                            .forEach(toReturn::add);
                }
            }
        }
        if (config.getBoolean("scoreboard.footer.animation.enabled")) {
            String footer = footer();
            toReturn = toReturn.stream().map(s -> s.replace("%footer%", footer)).collect(Collectors.toList());
        }
        if (config.getBoolean("scoreboard.title.animation.enabled")) {
            String title = titles();
            toReturn = toReturn.stream().map(s -> s.replace("%title%", title)).collect(Collectors.toList());
        }

        return SharkHub.getInstance().isPlaceholderAPI() ? PlaceholderAPI.setPlaceholders(player, toReturn) : toReturn;
    }

    private String footer() {
        List<String> footers = CC.translate(config.getStringList("scoreboard.footer.animation.animated"));
        long time = System.currentTimeMillis();
        long interval = TimeUnit.SECONDS.toMillis(config.getInt("scoreboard.footer.animation.interval"));

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
        List<String> titles = CC.translate(config.getStringList("scoreboard.title.animation.animated"));
        long time = System.currentTimeMillis();
        long interval = TimeUnit.SECONDS.toMillis(config.getInt("scoreboard.title.animation.interval"));

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