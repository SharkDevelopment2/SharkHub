package es.hulk.hub;

import es.hulk.hub.bungee.BungeeTask;
import es.hulk.hub.bungee.BungeeUtils;
import es.hulk.hub.commands.SharkCommand;
import es.hulk.hub.commands.customtimer.CustomTimerCommand;
import es.hulk.hub.commands.features.pvpmode.PvPModeCommand;
import es.hulk.hub.commands.features.queue.JoinQueueCommand;
import es.hulk.hub.commands.features.queue.LeaveQueueCommand;
import es.hulk.hub.commands.features.queue.ToggleQueueCommand;
import es.hulk.hub.commands.media.*;
import es.hulk.hub.commands.others.SkullCommand;
import es.hulk.hub.commands.spawn.SetSpawnCommand;
import es.hulk.hub.commands.spawn.SpawnCommand;
import es.hulk.hub.cosmetics.base.command.CosmeticsCommand;
import es.hulk.hub.cosmetics.types.gadgets.listener.GadgetsListener;
import es.hulk.hub.hotbar.HotbarManager;
import es.hulk.hub.listeners.*;
import es.hulk.hub.managers.RegisterHandler;
import es.hulk.hub.managers.SpawnManager;
import es.hulk.hub.managers.customtimer.CustomTimerManager;
import es.hulk.hub.managers.hcf.Hooker;
import es.hulk.hub.managers.queue.QueueManager;
import es.hulk.hub.managers.queue.custom.QueueHandler;
import es.hulk.hub.menus.hub.HubManager;
import es.hulk.hub.menus.server.ServerManager;
import es.hulk.hub.pvpmode.PvPModeHandler;
import es.hulk.hub.pvpmode.PvPModeListener;
import es.hulk.hub.util.CC;
import es.hulk.hub.util.ServerUtil;
import es.hulk.hub.util.SharkLicenses;
import es.hulk.hub.util.command.CommandManager;
import es.hulk.hub.util.files.ConfigFile;
import es.hulk.hub.util.menu.ButtonListener;
import es.hulk.hub.util.rank.RankManager;
import es.hulk.hub.util.skull.SkullManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;

@Getter
public class SharkHub extends JavaPlugin {

    @Getter
    private static SharkHub instance;
    private ConfigFile scoreboardConfig, tablistConfig, mainConfig, selectorConfig, subselectorConfig, hubselectorConfig, queueConfig, messagesConfig, cosmeticsConfig, hatsConfig, armorsConfig, gadgetsConfig, particlesConfig, pvpmodeConfig, hcfConfig, hotbarConfig, spawnConfig;
    private QueueManager queueManager;
    private QueueHandler queueHandler;
    private RankManager rankManager;
    private HotbarManager hotbarManager;
    private SpawnManager spawnManager;
    private CustomTimerManager customTimerManager;
    private CommandManager commandManager;
    private ServerManager serverManager;
    private HubManager hubManager;
    private RegisterHandler registerHandler;
    private SkullManager skullManager;
    private boolean isPlaceholderAPI = false;

    @Override
    public void onEnable() {
        instance = this;

        this.loadConfigs();
        this.registerManagers();

        if (!this.getDescription().getName().equals("SharkHub") || !this.getDescription().getAuthors().contains("ElTitoHulk")) {
            for (int i = 0; i < 4; i++) {
                Bukkit.getConsoleSender().sendMessage(CC.translate("&4THIS PLUGIN HAS BEEN DISABLED, DONT CHANGE AUTHOR OR NAME"));
            }
            Bukkit.getPluginManager().disablePlugin(this);
        }

        if (!new SharkLicenses(this, mainConfig.getString("SYSTEM.LICENSE"), "http://193.122.150.129:82/api/client", "d4dfb74a90e8f5f65e8e6a6dd7e2c56dbb7f33c0").verify()) {
            Bukkit.getPluginManager().disablePlugin(this);
            Bukkit.getScheduler().cancelTasks(this);
            return;
        }

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            isPlaceholderAPI = true;
        }

        this.rankManager.loadRank();
        this.queueManager.load();

        CC.sendConsole("&7&m-----------------------------------------------------");
        CC.sendConsole("");
        CC.sendConsole("&b&lShark Development");
        CC.sendConsole("");
        CC.sendConsole("&bPlugin&7: &aSharkHub");
        CC.sendConsole("&bVersion&7: &a" + this.getDescription().getVersion());
        CC.sendConsole("&bAuthor&7: &aElTitoHulk");
        CC.sendConsole(" ");
        CC.sendConsole("&bRank System&7: &a" + rankManager.getRankSystem());
        CC.sendConsole("&bQueue System&7: &a" + queueManager.getQueue());
        CC.sendConsole("&bServer Version&7: &a" + ServerUtil.getServerVersion());
        CC.sendConsole("");
        this.hotbarManager.load();
        this.serverManager.load();
        this.hubManager.load();
        this.registerHandler.init();
        CC.sendConsole("");
        CC.sendConsole("&7&m-----------------------------------------------------");

        this.loadCommands();
        this.loadListeners();

        new BungeeTask().runTaskTimerAsynchronously(this, 10L, 10L);
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new BungeeUtils());
    }

    @Override
    public void onDisable() {
        this.registerHandler.disable();
        this.spawnManager.saveLocation();
    }

    private void loadConfigs() {
        this.mainConfig = new ConfigFile(this, "config");
        this.queueConfig = new ConfigFile(this, "queue");

        this.selectorConfig = new ConfigFile(this, "menus/server-selector");
        this.subselectorConfig = new ConfigFile(this, "menus/sub-selector");
        this.hubselectorConfig = new ConfigFile(this, "menus/hub-selector");

        this.scoreboardConfig = new ConfigFile(this, "providers/scoreboard");
        this.tablistConfig = new ConfigFile(this, "providers/tablist");

        this.hcfConfig = new ConfigFile(this, "addons/hcf-hooks");

        this.messagesConfig = new ConfigFile(this, "messages");
        this.hotbarConfig = new ConfigFile(this, "hotbar");

        this.cosmeticsConfig = new ConfigFile(this, "cosmetics/menu");
        this.hatsConfig = new ConfigFile(this, "cosmetics/hats");
        this.armorsConfig = new ConfigFile(this, "cosmetics/armors");
        this.gadgetsConfig = new ConfigFile(this, "cosmetics/gadgets");

        this.spawnConfig = new ConfigFile(this, "data/spawn-location");
        this.pvpmodeConfig = new ConfigFile(this, "data/pvpmode-content");
    }

    private void registerManagers() {
        this.skullManager = new SkullManager();
        this.registerHandler = new RegisterHandler();
        this.hubManager = new HubManager();
        this.serverManager = new ServerManager();
        this.commandManager = new CommandManager(this);
        this.customTimerManager = new CustomTimerManager();
        this.rankManager = new RankManager(this);
        this.spawnManager = new SpawnManager();
        this.queueManager = new QueueManager();
        this.queueHandler = new QueueHandler();
        this.hotbarManager = new HotbarManager();
        PvPModeHandler.init();
        if (mainConfig.getBoolean("SYSTEM.HCF_HOOKER")) new Hooker();
    }

    public void loadCommands() {
        List<Object> commands = Arrays.asList(
                new DiscordCommand(),
                new TeamSpeakCommand(),
                new TwitterCommand(),
                new SetSpawnCommand(),
                new TelegramCommand(),
                new StoreCommand(),
                new SharkCommand(),
                new SpawnCommand(),
                new CosmeticsCommand(),
                new JoinQueueCommand(),
                new LeaveQueueCommand(),
                new ToggleQueueCommand(),
                new PvPModeCommand(),
                new SkullCommand(),
                new CustomTimerCommand());

        for (Object obj : commands) {
            CommandManager.getInstance().registerCommands(obj, null);
        }
    }

    public void loadListeners() {
        new ChatListener();
        new DoubleJumpListener();
        new HubExclusive();
        new JoinListener();
        new LaunchPadListener();
        new MovePlayerListener();
        new ProtectionListener();
        new WorldListener();
        new PvPModeListener();
        new ButtonListener();
        new GadgetsListener();
    }
}
