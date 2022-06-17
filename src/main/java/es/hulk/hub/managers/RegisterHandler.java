package es.hulk.hub.managers;

import com.bizarrealex.aether.Aether;
import dev.hely.tab.TablistModule;
import lombok.Getter;
import es.hulk.hub.SharkHub;
import es.hulk.hub.providers.ScoreboardProvider;
import es.hulk.hub.providers.TablistProvider;
import org.bukkit.Bukkit;
import org.bukkit.World;

public class RegisterHandler {

    @Getter private static Aether scoreboard;
    @Getter private static TablistModule tablist = TablistModule.INSTANCE;

    public static void init() {
        registerScoreboard();
        registerTablist();
        optimizeWorld();
    }

    public static void registerTablist() {
        if (SharkHub.getInstance().getTablistConfig().getBoolean("TABLIST.ENABLE")) {
            TablistModule.INSTANCE.onEnable(SharkHub.getInstance());
            TablistModule.INSTANCE.setProvider(new TablistProvider());
        }
    }

    public static void registerScoreboard() {
        if (SharkHub.getInstance().getScoreboardConfig().getBoolean("SCOREBOARD.ENABLE")) {
            scoreboard = new Aether(SharkHub.getInstance(), new ScoreboardProvider());
        }
    }

    public static void optimizeWorld() {
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
