package es.hulk.tablist.version.protocol.impl;

import es.hulk.tablist.version.protocol.ProtocolCheck;
import org.bukkit.entity.Player;
import us.myles.ViaVersion.api.Via;

/**
 * Created By LeandroSSJ
 * Created on 07/04/2022
 */
public class ProtocolVersion_ViaVersion implements ProtocolCheck
{
    @Override
    public int getVersion(Player player) {
        return Via.getAPI().getPlayerVersion(player);
    }
}