package es.hulk.tablist.nms.implement;

import es.hulk.tablist.TabColumn;
import es.hulk.tablist.TabEntry;
import es.hulk.tablist.Tablist;
import es.hulk.tablist.nms.NMS;
import es.hulk.tablist.skin.Skin;
import es.hulk.tablist.util.CC;
import net.minecraft.server.v1_7_R4.*;
import net.minecraft.util.com.mojang.authlib.GameProfile;
import net.minecraft.util.com.mojang.authlib.properties.Property;
import org.apache.commons.lang.StringEscapeUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.spigotmc.ProtocolInjector;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created By LeandroSSJ
 * Created on 19/05/2022
 */
public class v1_7_10Implement implements NMS {

    private static final MinecraftServer server = MinecraftServer.getServer();
    private static final WorldServer world = server.getWorldServer(0);
    private static final PlayerInteractManager manager = new PlayerInteractManager(world);

    @Override
    public TabEntry createEntry(Tablist tablist, String name, TabColumn column, int slot, int rawSlot, boolean legacy) {
        UUID uniqueId = UUID.randomUUID();


        GameProfile profile = new GameProfile(uniqueId, !legacy ? name : Tablist.ENTRY.get(rawSlot - 1) + "");
        EntityPlayer entity = new EntityPlayer(server, world, profile, manager);

        if (!legacy) {
            profile.getProperties().put("textures", new Property("textures", Skin.DEFAULT.getValue(), Skin.DEFAULT.getSignature()));
        }

        entity.ping = 1;

        sendPacket(tablist.getPlayer(), PacketPlayOutPlayerInfo.addPlayer(entity));

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
        GameProfile profile = new GameProfile(entry.getPlayer().getUniqueId(), entry.getName());
        EntityPlayer entity = new EntityPlayer(server, world, profile, manager);

        entity.listName = CC.translate(newStrings.length > 1 ? newStrings[0] + newStrings[1] : newStrings[0]);

        sendPacket(tablist.getPlayer(), PacketPlayOutPlayerInfo.updateDisplayName(entity));
    }

    @Override
    public void updateTexture(Tablist tablist, TabEntry entry, Skin skin) {
        GameProfile profile = new GameProfile(entry.getPlayer().getUniqueId(), entry.getName());
        EntityPlayer entity = new EntityPlayer(server, world, profile, manager);

        profile.getProperties().clear();
        profile.getProperties().put("textures", new Property("textures", skin.getValue(), skin.getSignature()));

        sendPacket(tablist.getPlayer(), PacketPlayOutPlayerInfo.removePlayer(entity));
        sendPacket(tablist.getPlayer(), PacketPlayOutPlayerInfo.addPlayer(entity));
    }

    @Override
    public void updatePing(Tablist tablist, TabEntry entry, int ping) {
        GameProfile profile = new GameProfile(entry.getPlayer().getUniqueId(), entry.getName());
        EntityPlayer entity = new EntityPlayer(server, world, profile, manager);

        entity.ping = ping;

        sendPacket(tablist.getPlayer(), PacketPlayOutPlayerInfo.updatePing(entity));
    }

    @Override
    public void updateHeaderAndFooter(Player player, List<String> header, List<String> footer) {
        IChatBaseComponent headerComponent = ChatSerializer.a("{text:\"" + StringEscapeUtils.escapeJava(this.getListFromString(CC.translate(header)))  + "\"}");
        IChatBaseComponent footerComponent = ChatSerializer.a("{text:\"" + StringEscapeUtils.escapeJava(this.getListFromString(CC.translate(footer))) + "\"}");
        ProtocolInjector.PacketTabHeader packetTabHeader = new ProtocolInjector.PacketTabHeader(headerComponent, footerComponent);

        sendPacket(player, packetTabHeader);
    }

    @Override
    public Skin getSkin(Player player) {
        if (Skin.CACHE.containsKey(player.getUniqueId())) return Skin.CACHE.get(player.getUniqueId());

        try {
            CraftPlayer craftPlayer = (CraftPlayer) player;
            EntityPlayer entityPlayer = craftPlayer.getHandle();

            Property property = entityPlayer.getProfile().getProperties().get("textures").stream().findFirst().orElse(null);

            if (property != null) {
                return Skin.CACHE.put(player.getUniqueId(), new Skin(property.getValue(), property.getSignature()));
            }
        } catch (Exception ignored) {
            return Skin.CACHE.put(player.getUniqueId(), Skin.STEVE);
        }

        return Skin.CACHE.put(player.getUniqueId(), Skin.STEVE);
    }


    private void sendPacket(Player player, Packet packet) {
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
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
