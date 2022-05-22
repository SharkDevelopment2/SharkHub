package me.jesusmx.hubcore.hooks.permissions;

import lombok.Getter;
import me.jesusmx.hubcore.hooks.permissions.type.*;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

@Getter
public class RankManager {

    private String system;
    private Rank rank;

    private Chat chat;
    private final RegisteredServiceProvider<Chat> chatProvider = Bukkit.getServer().getServicesManager().getRegistration(Chat.class);

    public void load() {
        if (Bukkit.getPluginManager().getPlugin("AquaCore") != null) {
            this.system = "AquaCore";
            this.rank = new AquaCoreRank();
        } else if (Bukkit.getPluginManager().getPlugin("Mizu") != null) {
            this.system = "Mizu";
            this.rank = new MizuRank();
        } else if (Bukkit.getPluginManager().getPlugin("HestiaCore") != null) {
            this.system = "HestiaCore";
            this.rank = new HestiaRank();
        } else if (Bukkit.getPluginManager().getPlugin("Zoom") != null) {
            this.system = "Zoom";
            this.rank = new ZoomRank();
        } else if (Bukkit.getPluginManager().getPlugin("Zoot") != null) {
            this.system = "Zoot";
            this.rank = new ZootRank();
        } else if (Bukkit.getPluginManager().getPlugin("Vault") != null) {
            this.system = "Vault";
            this.rank = new VaultRank();

            if (chatProvider != null) {
                chat = chatProvider.getProvider();
            }

        }
    }
}
