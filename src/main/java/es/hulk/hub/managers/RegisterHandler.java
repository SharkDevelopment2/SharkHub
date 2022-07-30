package es.hulk.hub.managers;

import dev.hely.tab.Tablist;
import dev.hely.tab.TablistModule;
import es.hulk.hub.SharkHub;
import es.hulk.hub.providers.ScoreboardProvider;
import es.hulk.hub.providers.TablistProvider;
import es.hulk.hub.util.scoreboard.Scoreboard;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.World;

@Getter
public class RegisterHandler {

    private final SharkHub plugin;
    private Scoreboard scoreboard;

    public RegisterHandler(SharkHub plugin) {
        this.plugin = plugin;
    }

    public void init() {
        this.registerScoreboard();
        this.registerTablist();
        this.optimizeWorld();
    }

    public void disable() {
        this.scoreboard.getBoards().clear();
        TablistModule.INSTANCE.disable();
    }

    public void registerTablist() {
        if (SharkHub.getInstance().getTablistConfig().getBoolean("TABLIST.ENABLE")) {
            TablistModule.INSTANCE.onEnable(plugin);
            TablistModule.INSTANCE.setProvider(new TablistProvider());
        }
    }

    public void registerScoreboard() {
        if (SharkHub.getInstance().getScoreboardConfig().getBoolean("SCOREBOARD.ENABLE")) {
            this.scoreboard = new Scoreboard(SharkHub.getInstance(), new ScoreboardProvider(plugin));
        }
    }

    public void optimizeWorld() {
        if (SharkHub.getInstance().getMainConfig().getBoolean("OPTIMIZE_WORLD")) {
            for (World world : Bukkit.getWorlds()) {
                world.setGameRuleValue("doDaylightCycle", "false");
                world.setGameRuleValue("doMobSpawning", "false");
                world.setTime(0L);
                world.setStorm(false);
            }
        }
    }
}
