package me.jesusmx.hubcore.util;

import me.jesusmx.hubcore.SharkHub;
import me.jesusmx.hubcore.bungee.BungeeUtils;
import me.jesusmx.hubcore.managers.hcf.Hooker;
import me.jesusmx.hubcore.managers.hcf.Splitters;
import me.jesusmx.hubcore.managers.queue.QueueManager;
import me.jesusmx.hubcore.util.files.ConfigFile;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class ServerUtil {

    private static final QueueManager queues = SharkHub.getInstance().getQueueManager();
    private static final ConfigFile config = SharkHub.getInstance().getMainConfig();
    private static final ConfigFile hcfConfig = SharkHub.getInstance().getHcfConfig();

    private static final SimpleDateFormat timeDate = new SimpleDateFormat(config.getString("TIME.DATE"));
    private static final SimpleDateFormat timeHour = new SimpleDateFormat(config.getString("TIME.HOUR"));

    public static String getDate() {
        timeDate.setTimeZone(TimeZone.getTimeZone(config.getString("TIME.ZONE")));
        return timeDate.format(new Date());
    }

    public static String getHour() {
        timeHour.setTimeZone(TimeZone.getTimeZone(config.getString("TIME.ZONE")));
        return timeHour.format(new Date());
    }

    public static String replaceText(Player player, String str) {
        if (queues.getSystem().isInQueue(player)) {
            return str
                    .replace("%rank%", SharkHub.getInstance().getRankManager().getRank().getName(player.getUniqueId()))
                    .replace("%rank_color%", SharkHub.getInstance().getRankManager().getRank().getColor(player.getUniqueId()))
                    .replace("%prefix%", SharkHub.getInstance().getRankManager().getRank().getPrefix(player.getUniqueId()))
                    .replace("%sufixx%", SharkHub.getInstance().getRankManager().getRank().getSuffix(player.getUniqueId()))
                    .replace("%global_players%", String.valueOf(BungeeUtils.getGlobalPlayers()))
                    .replace("%player%", player.getName())
                    .replace("%hour%", getHour())
                    .replace("%slots%", Integer.toString(Bukkit.getServer().getMaxPlayers()))
                    .replace("%date%", getDate())
                    .replace("%queue_server%", queues.getSystem().getServer(player))
                    .replace("%queue_position%", String.valueOf(queues.getSystem().getPosition(player)))
                    .replace("%queue_size%", String.valueOf(queues.getSystem().getSize(player)));
        }
        return str
                .replace("%rank%", SharkHub.getInstance().getRankManager().getRank().getName(player.getUniqueId()))
                .replace("%rank_color%", SharkHub.getInstance().getRankManager().getRank().getColor(player.getUniqueId()))
                .replace("%prefix%", SharkHub.getInstance().getRankManager().getRank().getPrefix(player.getUniqueId()))
                .replace("%sufixx%", SharkHub.getInstance().getRankManager().getRank().getSuffix(player.getUniqueId()))
                .replace("%global_players%", String.valueOf(BungeeUtils.getGlobalPlayers()))
                .replace("%player%", player.getName())
                .replace("%hour%", getHour())
                .replace("%slots%", Integer.toString(Bukkit.getServer().getMaxPlayers()))
                .replace("%date%", getDate());
    }

}
