package es.hulk.tablist.version.protocol.impl;

import com.comphenix.protocol.ProtocolLibrary;
import es.hulk.tablist.version.protocol.ProtocolCheck;
import org.bukkit.entity.Player;

/**
 * Created By LeandroSSJ
 * Created on 07/04/2022
 */
public class ProtocolVersion_ProtocolLib implements ProtocolCheck
{
    @Override
    public int getVersion(Player player) {
        return ProtocolLibrary.getProtocolManager().getProtocolVersion(player);
    }
}
