package me.jesusmx.hubcore.providers;

import me.clip.placeholderapi.PlaceholderAPI;
import me.jesusmx.hubcore.SharkHub;
import me.jesusmx.hubcore.bungee.BungeeUtils;
import me.jesusmx.hubcore.hooks.hcf.Hooker;
import me.jesusmx.hubcore.hooks.hcf.Splitters;
import me.jesusmx.hubcore.hooks.queue.QueueManager;
import io.github.nosequel.tab.shared.entry.TabElement;
import io.github.nosequel.tab.shared.entry.TabElementHandler;
import io.github.nosequel.tab.shared.skin.SkinType;
import me.jesusmx.hubcore.util.CC;
import me.jesusmx.hubcore.util.files.ConfigFile;
import me.jesusmx.hubcore.util.string.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class TablistProvider implements TabElementHandler {

    private final ConfigFile config = SharkHub.getInstance().getTablistConfig();
    private final ConfigFile settings = SharkHub.getInstance().getSettingsConfig();
    private final ConfigFile hcf = SharkHub.getInstance().getHcfConfig();

    @Override
    public TabElement getElement(Player player) {
        TabElement element = new TabElement();
        element.setHeader(CC.translate(PlaceholderAPI.setPlaceholders(player, config.getString("tablist.header").replace("<line>", "\n"))));
        element.setFooter(CC.translate(PlaceholderAPI.setPlaceholders(player, config.getString("tablist.footer").replace("<line>", "\n"))));

        List<String> list = Arrays.asList("left", "middle", "right", "far-right");

        for (int i = 0; i < 4; ++i) {
            String s = list.get(i);
            for (int l = 0; l < 20; ++l) {

                String str = PlaceholderAPI.setPlaceholders(player, config.getString("tablist." + s + "." + (l + 1))
                        .replace("%player%", player.getDisplayName())
                        .replace("%rank%", SharkHub.getInstance().getPermissionCore().getRank(player)))
                        .replace("%players%", String.valueOf(BungeeUtils.getGlobalPlayers()))
                        .replace("%rank-color%", SharkHub.getInstance().getPermissionCore().getRankColor(player));

                QueueManager queues = SharkHub.getInstance().getQueueManager();
                String queue = CC.translate(config.getString("tablist.queue.server"));
                String position = CC.translate(config.getString("tablist.queue.position"));
                String size = CC.translate(config.getString("tablist.queue.in-queue"));

                if(queues.inQueue(player)) {
                    queue = queues.getQueueIn(player);
                    position = String.valueOf(queues.getPosition(player));
                    size = String.valueOf(queues.getInQueue(queue));
                }

                str = str.replace("%server-queue%", queue);
                str = str.replace("%position%", position);
                str = str.replace("%total%", size);

                if(settings.getBoolean("system.hcf-hook")) {
                    if(!Hooker.getVerified().isEmpty()) {
                        for (String sk : Hooker.getVerified()) {
                            String path = "hcf-hook.servers." + sk;
                            String host = hcf.getString(path + ".host");
                            int port = hcf.getInt(path + ".port");
                            try {
                                Socket socket = new Socket(host, port);
                                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                                DataInputStream dis = new DataInputStream(socket.getInputStream());
                                dos.writeUTF(sk + Splitters.REQUEST + player.getUniqueId());
                                dos.flush();
                                String response = dis.readUTF();
                                String[] rs = response.split(Splitters.REQUEST);
                                str = str.replace("%" + sk + "_lives%", rs[1]);
                                str = str.replace("%" + sk + "_deathban%", rs[2]);
                                dos.close();
                                dis.close();
                                socket.close();
                            } catch (IOException e) {
                                str = str.replace("%" + sk + "_lives%", "0");
                                str = str.replace("%" + sk + "_deathban%", "Loading");
                                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "The connection with the hook " + sk + " has been lost");
                                Hooker.getVerified().remove(sk);
                                Hooker.getUnverified().add(sk);
                            }
                        }
                    }
                    for(String sk : Hooker.getUnverified()) {
                        str = str.replace("%" + sk + "_LIVES%", "0");
                        str = str.replace("%" + sk + "_DEATHBAN%", "Loading");
                    }
                }

                SkinType skinType = SkinType.DARK_GRAY;
                if(str.toLowerCase(Locale.ROOT).contains("{skin=")) {
                    String skin = StringUtils.after(StringUtils.before(str, "}"), "{skin=");
                    if(skin.equalsIgnoreCase("$self")) {
                        skinType = SkinType.fromUsername(player.getName());
                    } else {
                        skinType = SkinType.fromUsername(skin);
                    }
                    str = StringUtils.after(str, skin + "} ");
                }
                element.add(i, l, str, 0, skinType.getSkinData());
            }
        }
        return element;
    }
}