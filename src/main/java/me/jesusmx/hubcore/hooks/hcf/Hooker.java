package me.jesusmx.hubcore.hooks.hcf;

import lombok.Getter;
import me.jesusmx.hubcore.SharkHub;
import me.jesusmx.hubcore.util.CC;
import me.jesusmx.hubcore.util.files.ConfigFile;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Hooker {

    @Getter private static final Set<String> unverified = new HashSet<>();
    @Getter private static final Set<String> verified = new HashSet<>();
    protected static ServerSocket server;
    protected static int port;
    private ConfigFile config = SharkHub.getInstance().getHcfConfig();

    public Hooker() {
        try {
            if (SharkHub.getInstance().getMainConfig().getBoolean("SYSTEM.HCF_HOOKER")) return;

            unverified.addAll(config.getConfiguration().getConfigurationSection("hcf-hook.servers").getKeys(false));
            port = config.getInt("hcf-hook.hub-port");
            server = new ServerSocket(port);
            Bukkit.getScheduler().runTaskLaterAsynchronously(SharkHub.getInstance(), () -> Bukkit.getConsoleSender().sendMessage(CC.translate(" &7• &fHCF-Hooks: &bChecking " + unverified.size() + " hooks")), 1L);
            List<String> toRemove = new ArrayList<>();
            List<String> toAdd = new ArrayList<>();
            for(String s : Hooker.getUnverified()) {
                String path = "hcf-hook.servers." + s;
                String host =  config.getString(path + ".host");
                int port = config.getInt(path + ".port");
                try {
                    Socket socket = new Socket(host, port);
                    DataInputStream dis = new DataInputStream(socket.getInputStream());
                    DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                    dos.writeUTF(Splitters.REQUEST + "ENABLED" + Splitters.REQUEST + s);
                    dos.flush();
                    String msg = dis.readUTF();
                    if(msg.contains("SUCCESSFULLY")) {
                        toAdd.add(s);
                        toRemove.add(s);
                    }
                    dis.close();
                    dos.close();
                    socket.close();

                } catch (IOException ignored) {}
            }
            toRemove.forEach(Hooker.getUnverified()::remove);
            Hooker.getVerified().addAll(toAdd);
            new HookReceiverThread().start();
            Bukkit.getScheduler().runTaskLaterAsynchronously(SharkHub.getInstance(), () -> Bukkit.getConsoleSender().sendMessage(CC.translate(" &7• &fHCF-Hooks: &aVerified " + verified.size() + " hooks, You have " + ChatColor.GREEN + unverified.size() + " &aunverified hooks!")), 2L);
        } catch (IOException e) {
            Bukkit.getConsoleSender().sendMessage(" &7• &fHCF-Hooks: &cError initializing the hooker");
            if(config.getBoolean("hcf-hook.debug-mode")) e.printStackTrace();
        }
    }
}
