package es.hulk.hub.util;

import es.hulk.hub.SharkHub;
import es.hulk.hub.bungee.BungeeUtils;
import es.hulk.hub.managers.hcf.Hooker;
import es.hulk.hub.managers.hcf.Splitters;
import es.hulk.hub.managers.queue.QueueManager;
import es.hulk.hub.util.files.ConfigFile;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
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

    @Getter private static final String serverVersion = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];

    public static String getDate() {
        timeDate.setTimeZone(TimeZone.getTimeZone(config.getString("TIME.ZONE")));
        return timeDate.format(new Date());
    }

    public static String getHour() {
        timeHour.setTimeZone(TimeZone.getTimeZone(config.getString("TIME.ZONE")));
        return timeHour.format(new Date());
    }

    public static String replaceText(Player player, String str) {
        String replace = str;
        if (queues.getSystem().isInQueue(player)) {
            replace = replace
                    .replace("%queue_server%", queues.getSystem().getServer(player))
                    .replace("%queue_position%", String.valueOf(queues.getSystem().getPosition(player)))
                    .replace("%queue_size%", String.valueOf(queues.getSystem().getSize(player)));
        }

        if (config.getBoolean("SYSTEM.HCF_HOOKER")) {
            if (!Hooker.getVerified().isEmpty()) {
                for (String sk : Hooker.getVerified()) {
                    String path = "HCF_HOOKER.SERVERS." + sk;
                    String host = hcfConfig.getString(path + ".HOST");
                    int port = hcfConfig.getInt(path + ".PORT");
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
                        replace = replace.replace("%" + sk + "_lives%", rs[1]);
                        replace = replace.replace("%" + sk + "_deathban%", rs[2]);
                    } catch (IOException e) {
                        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "The connection with the hook " + sk + " has been lost");
                        Hooker.getVerified().remove(sk);
                        Hooker.getUnverified().add(sk);
                        replace = replace.replace("%" + sk + "_lives%", "0");
                        replace = replace.replace("%" + sk + "_deathban%", "Loading");
                    }
                }
            }
            for (String sk : Hooker.getUnverified()) {
                replace = replace.replace("%" + sk + "_lives%", "0");
                replace = replace.replace("%" + sk + "_deathban%", "Loading");
            }
        }

        replace = replace
                .replace("%rank%", SharkHub.getInstance().getRankManager().getRank().getName(player.getUniqueId()))
                .replace("%rank_color%", SharkHub.getInstance().getRankManager().getRank().getColor(player.getUniqueId()))
                .replace("%prefix%", SharkHub.getInstance().getRankManager().getRank().getPrefix(player.getUniqueId()))
                .replace("%sufixx%", SharkHub.getInstance().getRankManager().getRank().getSuffix(player.getUniqueId()))
                .replace("%global_players%", String.valueOf(BungeeUtils.getGlobalPlayers()))
                .replace("%player%", player.getName())
                .replace("%hour%", getHour())
                .replace("%slots%", Integer.toString(Bukkit.getServer().getMaxPlayers()))
                .replace("%date%", getDate());

        return replace;
    }

}
