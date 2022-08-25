package es.hulk.tablist;

import es.hulk.tablist.listener.TabListener;
import es.hulk.tablist.module.Module;
import es.hulk.tablist.nms.implement.*;
import es.hulk.tablist.provider.TabProvider;
import es.hulk.tablist.nms.NMS;
import es.hulk.tablist.thread.TabThread;
import es.hulk.tablist.version.MinecraftVersion;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created By LeandroSSJ
 * Created on 19/05/2022
 */
@Getter
public enum TablistModule implements Module {


    INSTANCE;

    private boolean enabled;
    private JavaPlugin plugin;
    private TabProvider provider;
    private NMS nms;
    private Thread thread;

    private final Map<UUID, Tablist> tabStorage = new ConcurrentHashMap<>();

    @Override
    public void onEnable(JavaPlugin plugin) {
        if (enabled) return;

        this.enabled = true;
        this.plugin = plugin;

        new MinecraftVersion();

        this.setupNMS();

        thread = new TabThread();
    }

    private void setupNMS() {
        String serverVersion = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];

        if (serverVersion.equalsIgnoreCase("v1_7_R4")) {
            this.nms = new v1_7_10Implement();
            System.out.println("[Horizon] Registered NMS with v1.7R4 Tablist");
            return;
        }

        if (serverVersion.equalsIgnoreCase("v1_8_R1")) {
            this.nms = new v1_8_R1Implement();
            System.out.println("[Horizon] Registered NMS with v1.8R1 Tablist");
            return;
        }

        if (serverVersion.equalsIgnoreCase("v1_8_R3")) {
            this.nms = new v1_8_R3Implement();
            System.out.println("[Horizon] Registered NMS with v1.8R3 Tablist");
            return;
        }

        if (serverVersion.equalsIgnoreCase("v1_9_R1")) {
            this.nms = new v1_9_R1Implement();
            System.out.println("[Horizon] Registered NMS with v1.9R1 Tablist");
            return;
        }

        if (serverVersion.equalsIgnoreCase("v1_10_R1")) {
            this.nms = new v1_10_R1Implement();
            System.out.println("[Horizon] Registered NMS with v1.10R1 Tablist");
            return;
        }

        if (serverVersion.equalsIgnoreCase("v1_12_R1")) {
            this.nms = new v1_12_R1Implement();
            System.out.println("[Horizon] Registered NMS with v1.12R1 Tablist");
            return;
        }

        if (serverVersion.equalsIgnoreCase("v1_14_R1")) {
            this.nms = new v1_14_R1Implement();
            System.out.println("[Horizon] Registered NMS with v1.14R1 Tablist");
            return;
        }

        if (serverVersion.equalsIgnoreCase("v1_15_R1")) {
            this.nms = new v1_15_R1Implement();
            System.out.println("[Horizon] Registered NMS with v1.15R1 Tablist");
            return;
        }

        if (serverVersion.equalsIgnoreCase("v1_16_R3")) {
            this.nms = new v1_16_R3Implement();
            System.out.println("[Horizon] Registered NMS with v1.16R3 Tablist");
            return;
        }

        if (Bukkit.getPluginManager().getPlugin("ProtocolLib") != null) {
            this.nms = new ProtocolImplement();
            System.out.println("[Horizon] Registered NMS with ProtocolLib Tablist");
        } else {
            System.out.println("[Horizon] Unable to register ProtocolLib Tablist! Please add ProtocolLib ");
        }
    }

    public void disable() {
        if (this.thread != null) {
            this.thread.stop();
            this.thread = null;
        }
        for (UUID uuid : tabStorage.keySet()) {
            Player player = Bukkit.getPlayer(uuid);

            if (player == null || !player.isOnline()) {
                continue;
            }

            tabStorage.remove(uuid);
        }
    }


    public void setProvider(TabProvider provider) {
        this.provider = provider;

        if (!thread.isAlive()) {
            thread.start();
        }

        plugin.getServer().getPluginManager().registerEvents(new TabListener(), plugin);
    }

}
