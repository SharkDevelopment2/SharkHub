package es.hulk.tablist.version.protocol.impl;

import es.hulk.tablist.version.protocol.ProtocolCheck;
import org.bukkit.entity.Player;
import protocolsupport.api.ProtocolSupportAPI;

/**
 * Created By LeandroSSJ
 * Created on 07/04/2022
 */
public class ProtocolVersion_ProtocolSupport implements ProtocolCheck
{
    @Override
    public int getVersion(Player player) {
        return ProtocolSupportAPI.getProtocolVersion(player).getId();
    }
}