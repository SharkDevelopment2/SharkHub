package me.jesusmx.hubcore.util.bukkit.handlers;

import io.github.thatkawaiisam.assemble.Assemble;
import io.github.thatkawaiisam.assemble.AssembleStyle;
import lombok.Getter;
import me.jesusmx.hubcore.SharkHub;
import me.jesusmx.hubcore.providers.ScoreboardProvider;
import org.bukkit.Bukkit;
import org.bukkit.World;

public class RegisterHandler {

    @Getter private static Assemble assemble;

    public static void tablist() {
    }

    public static void scoreboard() {
        assemble = new Assemble(SharkHub.getInstance(), new ScoreboardProvider());
        assemble.setTicks(2);
        assemble.setAssembleStyle(AssembleStyle.CUSTOM.descending(true).startNumber(16));
    }

    public static void world() {
        for (World world : Bukkit.getWorlds()) {
            world.setGameRuleValue("doDaylightCycle", "false");
            world.setGameRuleValue("doMobSpawning", "false");
            world.setTime(0L);
            world.setStorm(false);
        }
    }
}
