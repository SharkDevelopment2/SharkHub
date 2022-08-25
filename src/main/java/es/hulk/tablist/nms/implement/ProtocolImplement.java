package es.hulk.tablist.nms.implement;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.*;
import es.hulk.tablist.TabColumn;
import es.hulk.tablist.TabEntry;
import es.hulk.tablist.Tablist;
import es.hulk.tablist.nms.NMS;
import es.hulk.tablist.skin.Skin;
import es.hulk.tablist.util.CC;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Created By LeandroSSJ
 * Created on 19/05/2022
 */
public class ProtocolImplement implements NMS {

    @Override
    public TabEntry createEntry(Tablist tablist, String name, TabColumn column, int slot, int rawSlot, boolean legacy) {
        UUID uniqueId = UUID.randomUUID();

        PacketContainer packet = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.PLAYER_INFO);
        packet.getPlayerInfoAction().write(0, EnumWrappers.PlayerInfoAction.ADD_PLAYER);
        WrappedGameProfile profile = new WrappedGameProfile(uniqueId, !legacy ? name : Tablist.ENTRY.get(rawSlot - 1) + "");
        PlayerInfoData playerInfoData = new PlayerInfoData(profile, 1, EnumWrappers.NativeGameMode.SURVIVAL, WrappedChatComponent.fromText(!legacy ? "" : profile.getName()));

        if (!legacy) {
            playerInfoData.getProfile().getProperties().put("textures", new WrappedSignedProperty("textures", Skin.DEFAULT.getValue(), Skin.DEFAULT.getSignature()));
        }

        packet.getPlayerInfoDataLists().write(0, Collections.singletonList(playerInfoData));
        sendPacket(tablist.getPlayer(), packet);

        return new TabEntry(name, column, new OfflinePlayer() {
            public boolean isOnline() {
                return true;
            }

            public boolean willBeOnline() {
                return false;
            }

            public String getName() {
                return name;
            }

            public UUID getUniqueId() {
                return uniqueId;
            }

            public boolean isBanned() {
                return false;
            }

            public void setBanned(boolean banned) {
            }

            public boolean isWhitelisted() {
                return false;
            }

            public void setWhitelisted(boolean whitelisted) {
            }

            public Player getPlayer() {
                return null;
            }

            public long getFirstPlayed() {
                return 0;
            }

            public long getLastPlayed() {
                return 0;
            }

            public long getLastLogin() {
                return 0;
            }

            public long getLastLogout() {
                return 0;
            }

            public boolean hasPlayedBefore() {
                return false;
            }

            public Location getBedSpawnLocation() {
                return null;
            }

            public Map<String, Object> serialize() {
                return null;
            }

            public boolean isOp() {
                return false;
            }

            public void setOp(boolean op) {
            }
        }, rawSlot, "", slot, -1, Skin.DEFAULT);
    }


    @Override
    public void updateText(Tablist tablist, TabEntry entry, String[] newStrings) {
        PacketContainer packet = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.PLAYER_INFO);
        packet.getPlayerInfoAction().write(0, EnumWrappers.PlayerInfoAction.UPDATE_DISPLAY_NAME);

        WrappedGameProfile profile = new WrappedGameProfile(
                entry.getPlayer().getUniqueId(),
                entry.getName()
        );
        PlayerInfoData playerInfoData = new PlayerInfoData(
                profile,
                entry.getPing(),
                EnumWrappers.NativeGameMode.SURVIVAL,
                WrappedChatComponent.fromText(ChatColor.translateAlternateColorCodes('&', newStrings.length > 1 ? newStrings[0] + newStrings[1] : newStrings[0]))
        );

        packet.getPlayerInfoDataLists().write(0, Collections.singletonList(playerInfoData));
        sendPacket(tablist.getPlayer(), packet);
    }


    @Override
    public void updateTexture(Tablist tablist, TabEntry entry, Skin skin) {
        WrappedGameProfile profile = new WrappedGameProfile(entry.getPlayer().getUniqueId(), entry.getName());
        PlayerInfoData playerInfoData = new PlayerInfoData(profile, entry.getPing(), EnumWrappers.NativeGameMode.SURVIVAL, WrappedChatComponent.fromText(CC.translate(entry.getText())));

        playerInfoData.getProfile().getProperties().clear();
        playerInfoData.getProfile().getProperties().put("textures", new WrappedSignedProperty("textures", skin.getValue(), skin.getSignature()));


        PacketContainer remove = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.PLAYER_INFO);
        remove.getPlayerInfoAction().write(0, EnumWrappers.PlayerInfoAction.REMOVE_PLAYER);
        remove.getPlayerInfoDataLists().write(0, Collections.singletonList(playerInfoData));


        PacketContainer add = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.PLAYER_INFO);
        add.getPlayerInfoAction().write(0, EnumWrappers.PlayerInfoAction.ADD_PLAYER);
        add.getPlayerInfoDataLists().write(0, Collections.singletonList(playerInfoData));

        sendPacket(tablist.getPlayer(), remove);
        sendPacket(tablist.getPlayer(), add);
    }


    @Override
    public void updatePing(Tablist tablist, TabEntry entry, int ping) {
        PacketContainer packet = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.PLAYER_INFO);
        packet.getPlayerInfoAction().write(0, EnumWrappers.PlayerInfoAction.UPDATE_LATENCY);

        WrappedGameProfile profile = new WrappedGameProfile(
                entry.getPlayer().getUniqueId(),
                entry.getName());

        PlayerInfoData playerInfoData = new PlayerInfoData(
                profile,
                ping,
                EnumWrappers.NativeGameMode.SURVIVAL,
                WrappedChatComponent.fromText(ChatColor.translateAlternateColorCodes('&', entry.getText()))
        );

        packet.getPlayerInfoDataLists().write(0, Collections.singletonList(playerInfoData));
        sendPacket(tablist.getPlayer(), packet);
    }

    @Override
    public void updateHeaderAndFooter(Player player, List<String> header, List<String> footer) {
        PacketContainer headerAndFooter = new PacketContainer(PacketType.Play.Server.PLAYER_LIST_HEADER_FOOTER);

        headerAndFooter.getChatComponents().write(0, WrappedChatComponent.fromText(this.getListFromString(CC.translate(header))));
        headerAndFooter.getChatComponents().write(1, WrappedChatComponent.fromText(this.getListFromString(CC.translate(footer))));

        sendPacket(player, headerAndFooter);
    }

    @Override
    public Skin getSkin(Player player) {
        if (Skin.CACHE.containsKey(player.getUniqueId())) return Skin.CACHE.get(player.getUniqueId());

        try {

            WrappedGameProfile profile = WrappedGameProfile.fromPlayer(player);
            Collection<WrappedSignedProperty> property = profile.getProperties().get("textures");
            if (property != null && property.size() > 0) {
                WrappedSignedProperty actual = property.iterator().next();
                return Skin.CACHE.put(player.getUniqueId(), new Skin(actual.getValue(), actual.getSignature()));
            }

        } catch (Exception ignored) {
            return Skin.CACHE.put(player.getUniqueId(), Skin.STEVE);
        }

        return Skin.CACHE.put(player.getUniqueId(), Skin.STEVE);
    }

    private static void sendPacket(Player player, PacketContainer packetContainer) {
        try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(player, packetContainer);
        }
        catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public String getListFromString(List<String> list) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < list.size(); ++i) {
            stringBuilder.append(list.get(i));
            if (i != list.size() - 1) {
                stringBuilder.append('\n');
            }
        }
        return stringBuilder.toString();
    }
}
