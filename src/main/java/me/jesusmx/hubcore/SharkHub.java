package me.jesusmx.hubcore;

import lombok.Getter;
import me.jesusmx.hubcore.bungee.BungeeTask;
import me.jesusmx.hubcore.bungee.BungeeUtils;
import me.jesusmx.hubcore.commands.SharkCommand;
import me.jesusmx.hubcore.commands.features.pvpmode.PvPModeCommand;
import me.jesusmx.hubcore.commands.features.queue.JoinQueueCommand;
import me.jesusmx.hubcore.commands.features.queue.LeaveQueueCommand;
import me.jesusmx.hubcore.commands.features.queue.ToggleQueueCommand;
import me.jesusmx.hubcore.commands.media.*;
import me.jesusmx.hubcore.commands.others.SkullCommand;
import me.jesusmx.hubcore.commands.spawn.SetSpawnCommand;
import me.jesusmx.hubcore.commands.spawn.SpawnCommand;
import me.jesusmx.hubcore.cosmetics.base.command.CosmeticsCommand;
import me.jesusmx.hubcore.cosmetics.base.listener.CosmeticsListener;
import me.jesusmx.hubcore.cosmetics.types.gadgets.listener.GadgetsListener;
import me.jesusmx.hubcore.hooks.hcf.Hooker;
import me.jesusmx.hubcore.hooks.nametag.LunarNameTagsListener;
import me.jesusmx.hubcore.hooks.permissions.PermissionCore;
import me.jesusmx.hubcore.hooks.permissions.type.*;
import me.jesusmx.hubcore.hooks.queue.QueueManager;
import me.jesusmx.hubcore.hooks.queue.custom.QueueHandler;
import me.jesusmx.hubcore.listeners.*;
import me.jesusmx.hubcore.manager.listeners.*;
import me.jesusmx.hubcoreisteners.*;
import me.jesusmx.hubcore.listeners.items.EnderButtListener;
import me.jesusmx.hubcore.listeners.items.HidePlayersListener;
import me.jesusmx.hubcore.listeners.items.JoinPlayerListener;
import me.jesusmx.hubcore.menus.hub.listener.HubSelectorListener;
import me.jesusmx.hubcore.menus.server.listener.ServerSelectorListener;
import me.jesusmx.hubcore.pvpmode.cache.PvPModeHandler;
import me.jesusmx.hubcore.pvpmode.listener.PvPModeListener;
import me.jesusmx.hubcore.util.CC;
import me.jesusmx.hubcore.util.bukkit.SharkLicenses;
import me.jesusmx.hubcore.util.bukkit.api.command.Command;
import me.jesusmx.hubcore.util.bukkit.handlers.RegisterHandler;
import me.jesusmx.hubcore.util.buttons.MenuListener;
import me.jesusmx.hubcore.util.buttons.page.PageMenu;
import me.jesusmx.hubcore.util.files.ConfigFile;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

@Getter
public class SharkHub extends JavaPlugin {

    @Getter private static SharkHub instance;
    private ConfigFile togglesConfig, scoreboardConfig, settingsConfig, tablistConfig, mainConfig, selectorConfig, subselectorConfig, hubselectorConfig, queueConfig, messagesConfig, cosmeticsConfig, hatsConfig, armorsConfig, gadgetsConfig, particlesConfig, pvpmodeConfig, hcfConfig, nametagsConfig, hotbarConfig;
    public static Chat chat;
    private QueueManager queueManager;
    private QueueHandler queueHandler;
    private PermissionCore permissionCore;

    @Override
    public void onEnable() {
        SharkHub.instance = this;

        this.settingsConfig = new ConfigFile(this, "settings");
        this.mainConfig = new ConfigFile(this, "config");
        this.togglesConfig = new ConfigFile(this, "toggles");
        this.queueConfig = new ConfigFile(this, "queue");

        this.selectorConfig = new ConfigFile(this, "menus/server-selector");
        this.subselectorConfig = new ConfigFile(this, "menus/sub-selector");
        this.hubselectorConfig = new ConfigFile(this, "menus/hub-selector");

        this.scoreboardConfig = new ConfigFile(this, "features/providers/scoreboard");
        this.tablistConfig = new ConfigFile(this, "features/providers/tablist");

        this.pvpmodeConfig = new ConfigFile(this, "features/addons/pvp-mode");
        this.hcfConfig = new ConfigFile(this, "features/addons/hcf-hooks");
        this.nametagsConfig = new ConfigFile(this, "features/addons/nametags");

        this.messagesConfig = new ConfigFile(this, "features/messages");
        this.hotbarConfig = new ConfigFile(this, "features/hotbar");

        this.cosmeticsConfig = new ConfigFile(this, "features/cosmetics/menu");
        this.hatsConfig = new ConfigFile(this, "features/cosmetics/hats");
        this.armorsConfig = new ConfigFile(this, "features/cosmetics/armors");
        this.gadgetsConfig = new ConfigFile(this, "features/cosmetics/gadgets");
        this.particlesConfig = new ConfigFile(this, "features/cosmetics/particles");

        if(!new SharkLicenses(this, settingsConfig.getString("system.license"), "http://146.66.141.134:82/api/client", "2680396e39db6bacb0e6d673b2936c19790c8376").verify()) {
            Bukkit.getPluginManager().disablePlugin(this);
            Bukkit.getScheduler().cancelTasks(this);
            return;
        }

        if (!this.getDescription().getName().equals("SharkHub") || !this.getDescription().getAuthors().contains("JesusMX")) {
            Bukkit.getPluginManager().disablePlugin(this);
        }

        if (togglesConfig.getBoolean("world.optimized")) {
            RegisterHandler.world();
        }

        this.commands();
        this.listeners();
        this.managers();

        if (togglesConfig.getBoolean("features.scoreboard")) {
            RegisterHandler.scoreboard();
        }

        new BungeeTask().runTaskTimerAsynchronously(this, 10L, 10L);
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new BungeeUtils());

       this.permissions();

        if (togglesConfig.getBoolean("features.tablist")) {
            RegisterHandler.tablist();
        }

        RegisteredServiceProvider<Chat> chatProvider = this.getServer().getServicesManager().getRegistration(Chat.class);
        if (chatProvider != null) {
            chat = chatProvider.getProvider();
        }

        if (togglesConfig.getBoolean("addons.console-message")) {
            this.getServer().getConsoleSender().sendMessage((CC.CustomMessage(settingsConfig.getStringList("hubcore.style.messages"))));
        }
    }

    private void managers() {
        queueManager = new QueueManager();
        queueHandler = new QueueHandler();
        new PvPModeHandler();
        if (settingsConfig.getBoolean("system.hcf-hook")) {
            new Hooker();
        }
    }

    public void commands() {
        Arrays.asList(new DiscordCommand(), new TeamSpeakCommand(), new TwitterCommand(), new SetSpawnCommand(), new TelegramCommand(), new StoreCommand(), new SharkCommand(), new SpawnCommand(), new CosmeticsCommand(), new JoinQueueCommand(), new LeaveQueueCommand(), new ToggleQueueCommand(), new PvPModeCommand(), new SkullCommand()).forEach(Command::registerCommand);
    }

    public void listeners() {
        Arrays.asList(new MenuListener(), new WorldListener(), new ProtectionListener(), new DoubleJumpListener(), new JoinPlayerListener(), new ChatListener(), new MovePlayerListener(), new EnderButtListener(), new HidePlayersListener(), new CosmeticsListener(), new GadgetsListener(), new PvPModeListener(), new DeveloperListener(), new HubExclusive(), new LaunchPadListener(), new HubSelectorListener(), new ServerSelectorListener(), new PageMenu()).forEach(listener -> this.getServer().getPluginManager().registerEvents(listener, this));
        if(Bukkit.getPluginManager().getPlugin("LunarClientAPI") != null) {
            Bukkit.getPluginManager().registerEvents(new LunarNameTagsListener(), this);
        }
    }

    private String permissions() {
        String core = settingsConfig.getString("system.rank");
        switch (core) {
            case "AquaCore":
                permissionCore = new AquaCorePermissionCore();
                return "AquaCore";
            case "Vault":
                permissionCore = new VaultPermissionCore();
                return "Vault";
            case "Mizu":
                permissionCore = new MizuPermissionCore();
                return "Mizu";
            case "Hestia":
                permissionCore = new HestiaPermissionCore();
                return "Hestia";
            case "Zoom":
                permissionCore = new ZoomPermissionCore();
                return "Zoom";
            case "Zoot":
                permissionCore = new ZootPermissionCore();
                return "Zoot";
        }
        return "Nothing";
    }

    public Collection<? extends Player> getOnlinePlayers() {
        Collection<Player> collection = new ArrayList<>();
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            collection.add(player);
        }
        return collection;
    }
}
