package me.jesusmx.hubcore.util.bukkit.handlers;

import es.hulk.tablist.Omega;
import io.github.thatkawaiisam.assemble.Assemble;
import io.github.thatkawaiisam.assemble.AssembleStyle;
import lombok.Getter;
import me.jesusmx.hubcore.SharkHub;
import me.jesusmx.hubcore.providers.ScoreboardProvider;
import me.jesusmx.hubcore.providers.TablistProvider;
import me.jesusmx.hubcore.util.files.ConfigFile;
import org.bukkit.Bukkit;
import org.bukkit.World;

public class RegisterHandler {

    @Getter private static Assemble assemble;
    @Getter private static Omega tablist;

    public static void init() {
        registerScoreboard();
        registerTablist();
        optimizeWorld();
    }

    public static void registerTablist() {
        if (SharkHub.getInstance().getTablistConfig().getBoolean("TABLIST.ENABLE")) {
            tablist = new Omega(SharkHub.getInstance(), new TablistProvider());
        }
    }

    public static void registerScoreboard() {
        if (SharkHub.getInstance().getScoreboardConfig().getBoolean("SCOREBOARD.ENABLE")) {
            assemble = new Assemble(SharkHub.getInstance(), new ScoreboardProvider());
            assemble.setTicks(2);
            assemble.setAssembleStyle(AssembleStyle.CUSTOM.descending(true).startNumber(16));
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
