package es.hulk.hub.managers;

import com.bizarrealex.aether.Aether;
import dev.hely.tab.TablistModule;
import lombok.Getter;
import es.hulk.hub.SharkHub;
import es.hulk.hub.providers.ScoreboardProvider;
import es.hulk.hub.providers.TablistProvider;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

@Getter
public class RegisterHandler {

    private Aether scoreboard;
    private TablistModule tablist = TablistModule.INSTANCE;

    public void init() {
        registerScoreboard();
        registerTablist();
        optimizeWorld();
    }

    public void disable() {
        tablist.onDisable(SharkHub.getInstance());
        for (Player online : Bukkit.getOnlinePlayers()) {
            tablist.getProvider().getProvider(online).clear();
            tablist.getProvider().getFooter(online).clear();
            tablist.getProvider().getHeader(online).clear();
        }
        tablist.getThread().stop();
    }

    public void registerTablist() {
        if (SharkHub.getInstance().getTablistConfig().getBoolean("TABLIST.ENABLE")) {
            TablistModule.INSTANCE.onEnable(SharkHub.getInstance());
            TablistModule.INSTANCE.setProvider(new TablistProvider());
        }
    }

    public void registerScoreboard() {
        if (SharkHub.getInstance().getScoreboardConfig().getBoolean("SCOREBOARD.ENABLE")) {
            scoreboard = new Aether(SharkHub.getInstance(), new ScoreboardProvider());
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
