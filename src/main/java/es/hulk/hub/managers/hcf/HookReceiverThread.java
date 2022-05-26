package es.hulk.hub.managers.hcf;

import es.hulk.hub.SharkHub;
import es.hulk.hub.util.files.ConfigFile;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public class HookReceiverThread extends Thread {

    public HookReceiverThread() {
        super.setName("Hooker - HooksReceiver");
    }
    private final ConfigFile config = SharkHub.getInstance().getHcfConfig();

    @Override
    public void run() {
        while(true) {
            try {
                Socket socket = Hooker.server.accept();
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                String msg = dis.readUTF();
                if(msg.contains("hook") && msg.contains(Splitters.HOOK)) {
                    String[] entry = msg.split(Splitters.HOOK);
                    String server_name = entry[1];
                    dos.writeUTF("hook" + Splitters.HOOK +"successfully");
                    dos.flush();
                    if(!Hooker.getVerified().contains(server_name) && Hooker.getUnverified().contains(server_name)) {
                        Hooker.getUnverified().remove(server_name);
                        Hooker.getVerified().add(server_name);
                    }
                    Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Successfully hooked with " + server_name);
                }
                dis.close();
                dos.close();
                socket.close();
            } catch (IOException e) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Error Receiving a hooker request!");
                if(config.getBoolean("HCF_HOOKER.DEBUG_MODE")) e.printStackTrace();
            }
        }
    }
}
