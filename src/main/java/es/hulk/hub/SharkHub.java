package es.hulk.hub;

import es.hulk.hub.bungee.BungeeTask;
import es.hulk.hub.bungee.BungeeUtils;
import es.hulk.hub.commands.SharkCommand;
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
import es.hulk.hub.listeners.dev.DeveloperListener;
import es.hulk.hub.listeners.dev.MarketListener;
import es.hulk.hub.managers.RegisterHandler;
import es.hulk.hub.managers.SpawnManager;
import es.hulk.hub.managers.hcf.Hooker;
import es.hulk.hub.managers.queue.QueueManager;
import es.hulk.hub.managers.queue.custom.QueueHandler;
import es.hulk.hub.pvpmode.PvPModeHandler;
import es.hulk.hub.pvpmode.PvPModeListener;
import es.hulk.hub.util.CC;
import es.hulk.hub.util.ServerUtil;
import es.hulk.hub.util.bukkit.SharkLicenses;
import es.hulk.hub.util.bukkit.api.command.Command;
import es.hulk.hub.util.files.ConfigFile;
import es.hulk.hub.util.menu.ButtonListener;
import es.hulk.hub.util.rank.RankManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

@Getter
public class SharkHub extends JavaPlugin {

    @Getter private static SharkHub instance;
    private ConfigFile scoreboardConfig, tablistConfig, mainConfig, selectorConfig, subselectorConfig, hubselectorConfig, queueConfig, messagesConfig, cosmeticsConfig, hatsConfig, armorsConfig, gadgetsConfig, particlesConfig, pvpmodeConfig, hcfConfig, nametagsConfig, hotbarConfig, spawnConfig;
    private QueueManager queueManager;
    private QueueHandler queueHandler;
    private RankManager rankManager;
    private HotbarManager hotbarManager;
    private SpawnManager spawnManager;

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

        if(!new SharkLicenses(this, mainConfig.getString("SYSTEM.LICENSE"), "http://193.122.150.129:82/api/client", "7a14d8912679db679f8dfc9a31e4637331edd378").verify()) {
            Bukkit.getPluginManager().disablePlugin(this);
            Bukkit.getScheduler().cancelTasks(this);
            return;
        }

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            isPlaceholderAPI = true;
        }

        rankManager.loadRank();
        queueManager.load();

        CC.sendConsole("&7&m-----------------------------------------------------");
        CC.sendConsole("");
        CC.sendConsole("&bShark Development");
        CC.sendConsole("");
        CC.sendConsole("&bPlugin&7: &aSharkHub");
        CC.sendConsole("&bVersion&7: &a" + this.getDescription().getVersion());
        CC.sendConsole("&bAuthor&7: &aElTitoHulk");
        CC.sendConsole(" ");
        CC.sendConsole("&bRank System&7: &a" + rankManager.getRankSystem());
        CC.sendConsole("&bQueue System&7: &a" + queueManager.getQueue());
        CC.sendConsole("&bServer Version&7: &a" + ServerUtil.getServerVersion());
        CC.sendConsole("");
        hotbarManager.load();
        RegisterHandler.init();
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
        RegisterHandler.getTablist().disable();
        spawnManager.saveLocation();
    }

    private void loadConfigs() {
        this.mainConfig = new ConfigFile(this, "config");
        this.queueConfig = new ConfigFile(this, "queue");

        this.selectorConfig = new ConfigFile(this, "menus/server-selector");
        this.subselectorConfig = new ConfigFile(this, "menus/sub-selector");
        this.hubselectorConfig = new ConfigFile(this, "menus/hub-selector");

        this.scoreboardConfig = new ConfigFile(this, "features/providers/scoreboard");
        this.tablistConfig = new ConfigFile(this, "features/providers/tablist");

        this.pvpmodeConfig = new ConfigFile(this, "data/pvpmode-content");
        this.hcfConfig = new ConfigFile(this, "features/addons/hcf-hooks");
        this.nametagsConfig = new ConfigFile(this, "features/addons/nametags");

        this.messagesConfig = new ConfigFile(this, "features/messages");
        this.hotbarConfig = new ConfigFile(this, "features/hotbar");

        this.cosmeticsConfig = new ConfigFile(this, "features/cosmetics/menu");
        this.hatsConfig = new ConfigFile(this, "features/cosmetics/hats");
        this.armorsConfig = new ConfigFile(this, "features/cosmetics/armors");
        this.gadgetsConfig = new ConfigFile(this, "features/cosmetics/gadgets");
        //this.particlesConfig = new ConfigFile(this, "features/cosmetics/particles");

        this.spawnConfig = new ConfigFile(this, "data/spawn-location");
    }

    private void registerManagers() {
        this.rankManager = new RankManager(this);
        this.spawnManager = new SpawnManager();
        this.queueManager = new QueueManager();
        this.queueHandler = new QueueHandler();
        this.hotbarManager = new HotbarManager();
        PvPModeHandler.init();
        if (mainConfig.getBoolean("SYSTEM.HCF_HOOKER")) new Hooker();
    }

    public void loadCommands() {
        Arrays.asList(new DiscordCommand(),
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
                new SkullCommand()).forEach(Command::registerCommand);
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
        new LunarNameTagsListener();
        new DeveloperListener();
        new MarketListener();
        new ButtonListener();
        new GadgetsListener();
    }
}
