package es.hulk.hub.managers;

import com.bizarrealex.aether.Aether;
import es.hulk.hub.providers.ScoreboardProvider;
import es.hulk.tablist.Omega;
import lombok.Getter;
import es.hulk.hub.SharkHub;
import es.hulk.hub.providers.TablistProvider;
import org.bukkit.Bukkit;
import org.bukkit.World;

@Getter
public class RegisterHandler {

    private final SharkHub plugin;
    private Aether scoreboard;
    private Omega tablist;

    public RegisterHandler(SharkHub plugin) {
        this.plugin = plugin;
    }

    public void init() {
        this.registerScoreboard();
        this.registerTablist();
        this.optimizeWorld();
    }

    public void disable() {
        tablist.disable();
    }

    public void registerTablist() {
        if (SharkHub.getInstance().getTablistConfig().getBoolean("TABLIST.ENABLE")) {
            this.tablist = new Omega(SharkHub.getInstance(), new TablistProvider());
        }
    }

    public void registerScoreboard() {
        if (SharkHub.getInstance().getScoreboardConfig().getBoolean("SCOREBOARD.ENABLE")) {
            scoreboard = new Aether(SharkHub.getInstance(), new ScoreboardProvider(plugin));
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
