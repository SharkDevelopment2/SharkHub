package es.hulk.hub.bungee;

import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import lombok.Getter;
import es.hulk.hub.SharkHub;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
This only is a util, not is me
Credits: Hulk/Miquel Angel - https://github.com/miquelangelamengual/HubCore
*/

@SuppressWarnings("UnstableApiUsage")
public class BungeeUtils implements PluginMessageListener {

    @Getter private static final Map<String, BungeeServer> serversByName = new HashMap<>();
    @Getter private static final List<BungeeServer> servers = new ArrayList<>();
    @Getter private static final List<String> serversName = new ArrayList<>();
    @Getter private static int globalPlayers = 0;

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals("BungeeCord")) return;
        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String subChannel = in.readUTF();
        if (subChannel.equals("PlayerCount")) {
            try {
                String name = in.readUTF();
                int playerCount = in.readInt();
                if (name.equalsIgnoreCase("ALL")) {
                    globalPlayers = playerCount;
                } else if (serversName.contains(name)) {
                    BungeeServer server = serversByName.get(name);
                    if (server == null) server = new BungeeServer(name);
                    server.setPlayerCount(playerCount);
                }
            } catch (Exception ignored) {}
        } else if (subChannel.equals("GetServers")) {
            String[] serverList = in.readUTF().split(", ");
            for (String serverName : serverList) {
                if (!serversName.contains(serverName)) {
                    new BungeeServer(serverName);
                }
            }
        }
    }

    public static void refreshGlobalCount() {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("PlayerCount");
        out.writeUTF("ALL");
        Player player = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
        if (player != null) player.sendPluginMessage(SharkHub.getInstance(), "BungeeCord", out.toByteArray());
    }

    public static void refreshServerList() {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("GetServers");
        Player player = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
        if (player != null) player.sendPluginMessage(SharkHub.getInstance(), "BungeeCord", out.toByteArray());
    }

    public static void sendToServer(Player p, String server) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        try {
            out.writeUTF("Connect");
            out.writeUTF(server);
        } catch (Exception e) {
            System.out.println("Error while connecting to server. The error was: " + e.getMessage());
            e.printStackTrace();
        }
        p.sendPluginMessage(SharkHub.getInstance(), "BungeeCord", out.toByteArray());
    }

    public static void refreshServerCount() {
        for (BungeeServer server : servers) {
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("PlayerCount");
            out.writeUTF(server.getName());
            Player player = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
            if (player != null) player.sendPluginMessage(SharkHub.getInstance(), "BungeeCord", out.toByteArray());
        }
    }
}
