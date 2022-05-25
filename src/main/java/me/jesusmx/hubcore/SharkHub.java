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
import me.jesusmx.hubcore.hotbar.HotbarManager;
import me.jesusmx.hubcore.listeners.*;
import me.jesusmx.hubcore.listeners.dev.DeveloperListener;
import me.jesusmx.hubcore.listeners.dev.MarketListener;
import me.jesusmx.hubcore.managers.RegisterHandler;
import me.jesusmx.hubcore.managers.SpawnManager;
import me.jesusmx.hubcore.managers.hcf.Hooker;
import me.jesusmx.hubcore.managers.queue.QueueManager;
import me.jesusmx.hubcore.managers.queue.custom.QueueHandler;
import me.jesusmx.hubcore.pvpmode.PvPModeHandler;
import me.jesusmx.hubcore.pvpmode.PvPModeListener;
import me.jesusmx.hubcore.util.CC;
import me.jesusmx.hubcore.util.bukkit.SharkLicenses;
import me.jesusmx.hubcore.util.bukkit.api.command.Command;
import me.jesusmx.hubcore.util.buttons.MenuListener;
import me.jesusmx.hubcore.util.files.ConfigFile;
import me.jesusmx.hubcore.util.rank.RankManager;
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
        CC.sendConsole(" &bPlugin&7: &aSharkHub");
        CC.sendConsole(" &bVersion&7: &a" + this.getDescription().getVersion());
        CC.sendConsole(" &bAuthor&7: &aElTitoHulk");
        CC.sendConsole(" ");
        CC.sendConsole(" &bRank System&7: &a" + rankManager.getRankSystem());
        CC.sendConsole(" &bQueue System&7: &a" + queueManager.getQueue());
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
        RegisterHandler.getAssemble().getBoards().clear();
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
        new MenuListener();
    }
}
