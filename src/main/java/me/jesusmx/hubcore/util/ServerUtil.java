package me.jesusmx.hubcore.util;

import me.jesusmx.hubcore.SharkHub;
import me.jesusmx.hubcore.hooks.hcf.Hooker;
import me.jesusmx.hubcore.hooks.hcf.Splitters;
import me.jesusmx.hubcore.hooks.queue.QueueManager;
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
    private static final ConfigFile settingsConfig = SharkHub.getInstance().getSettingsConfig();
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
        if (queues.inQueue(player)) {
            return str
                    .replace("%QUEUE_SERVER%", queues.getQueueIn(player))
                    .replace("%QUEUE_POSITION%", String.valueOf(queues.getPosition(player)))
                    .replace("%QUEUE_SIZE%", String.valueOf(queues.getInQueue(queues.getQueueIn(player))));
        }
        if (settingsConfig.getBoolean("system.hcf-hook")) {
            if (!Hooker.getVerified().isEmpty()) {
                for (String sk : Hooker.getVerified()) {
                    String path = "hcf-hook.servers." + sk;
                    String host = hcfConfig.getString(path + ".host");
                    int port = hcfConfig.getInt(path + ".port");
                    try {
                        Socket socket = new Socket(host, port);
                        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                        DataInputStream dis = new DataInputStream(socket.getInputStream());
                        dos.writeUTF(sk + Splitters.REQUEST + player.getUniqueId());
                        dos.flush();
                        String response = dis.readUTF();
                        String[] rs = response.split(Splitters.REQUEST);
                        dos.close();
                        dis.close();
                        socket.close();
                        return str
                                .replace("%" + sk + "_lives%", rs[1])
                                .replace("%" + sk + "_deathban%", rs[2]);
                    } catch (IOException e) {
                        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "The connection with the hook " + sk + " has been lost");
                        Hooker.getVerified().remove(sk);
                        Hooker.getUnverified().add(sk);
                        return str
                                .replace("%" + sk + "_lives%", "0")
                                .replace("%" + sk + "_deathban%", "Loading");
                    }
                }
            }
            for (String sk : Hooker.getUnverified()) {
                return str
                        .replace("%" + sk + "_lives%", "0")
                        .replace("%" + sk + "_deathban%", "Loading");
            }
        }
        return str
                .replace("%RANK%", SharkHub.getInstance().getPermissionCore().getRank(player))
                .replace("%PLAYER%", player.getName())
                .replace("%HOUR%", getHour())
                .replace("%SLOTS%", Integer.toString(Bukkit.getServer().getMaxPlayers()))
                .replace("%DATE%", getDate());
    }

}
