package es.hulk.hub.bungee;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import es.hulk.hub.SharkHub;
import org.bukkit.entity.Player;

/*
This only is a util, not is me
Credits: Hulk/Miquel Angel - https://github.com/miquelangelamengual/HubCore
*/

@SuppressWarnings("UnstableApiUsage")
public class BungeeUtils {

    public static void sendToServer(Player player, String server) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        try {
            out.writeUTF("Connect");
            out.writeUTF(server);
        } catch (Exception exception) {
            System.out.println("Error while connecting to server. The error was: " + exception.getMessage());
            exception.printStackTrace();
        }
        player.sendPluginMessage(SharkHub.getInstance(), "BungeeCord", out.toByteArray());
    }
}
