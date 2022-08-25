package es.hulk.tablist.version.protocol.impl;

import es.hulk.tablist.version.protocol.ProtocolCheck;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;

/**
 * Created By LeandroSSJ
 * Created on 07/04/2022
 */
public class ProtocolVersion_v1_7 implements ProtocolCheck {

    @Override
    public int getVersion(Player player) {
        return ((CraftPlayer) player).getHandle().playerConnection.networkManager.getVersion();
    }
}
