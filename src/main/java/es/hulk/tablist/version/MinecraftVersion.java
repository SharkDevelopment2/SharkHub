package es.hulk.tablist.version;


import es.hulk.tablist.version.protocol.impl.ProtocolVersion_ProtocolLib;
import es.hulk.tablist.version.protocol.impl.ProtocolVersion_ProtocolSupport;
import es.hulk.tablist.version.protocol.impl.ProtocolVersion_ViaVersion;
import es.hulk.tablist.version.protocol.ProtocolCheck;
import es.hulk.tablist.version.protocol.impl.ProtocolVersion_v1_7;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

/**
 * Created By LeandroSSJ
 * Created on 07/04/2022
 */
public class MinecraftVersion {
    public static ProtocolCheck protocolCheck;

    public MinecraftVersion() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        String serverVersion = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];

        if (serverVersion.equalsIgnoreCase("v1_7_R4")) {
            protocolCheck = new ProtocolVersion_v1_7();
            return;
        }

        if (pluginManager.getPlugin("ViaVersion") != null) {
            protocolCheck = new ProtocolVersion_ViaVersion();
            return;
        }

        if (pluginManager.getPlugin("ProtocolSupport") != null) {
            protocolCheck = new ProtocolVersion_ProtocolSupport();
            return;
        }


        if (pluginManager.getPlugin("ProtocolLib") != null) {
            protocolCheck = new ProtocolVersion_ProtocolLib();
        }
    }

    public static PlayerVersion getProtocol(Player player) {
        return PlayerVersion.getVersionFromRaw(protocolCheck.getVersion(player));
    }
}
