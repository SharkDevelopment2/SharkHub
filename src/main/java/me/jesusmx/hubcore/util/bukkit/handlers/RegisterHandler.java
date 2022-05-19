package me.jesusmx.hubcore.util.bukkit.handlers;

import io.github.nosequel.tab.shared.TabHandler;
import io.github.nosequel.tab.versions.v1_10_R1.v1_10_R1TabAdapter;
import io.github.nosequel.tab.versions.v1_12_R1.v1_12_R1TabAdapter;
import io.github.nosequel.tab.versions.v1_14_R1.v1_14_R1TabAdapter;
import io.github.nosequel.tab.versions.v1_15_R1.v1_15_R1TabAdapter;
import io.github.nosequel.tab.versions.v1_16_R3.v1_16_R3TabAdapter;
import io.github.nosequel.tab.versions.v1_7_R4.v1_7_R4TabAdapter;
import io.github.nosequel.tab.versions.v1_8_R3.v1_8_R3TabAdapter;
import io.github.nosequel.tab.versions.v1_9_R1.v1_9_R1TabAdapter;
import io.github.thatkawaiisam.assemble.Assemble;
import io.github.thatkawaiisam.assemble.AssembleStyle;
import lombok.Getter;
import me.jesusmx.hubcore.SharkHub;
import me.jesusmx.hubcore.providers.ScoreboardProvider;
import me.jesusmx.hubcore.providers.TablistProvider;
import org.bukkit.Bukkit;
import org.bukkit.World;

public class RegisterHandler {

    @Getter private static Assemble assemble;

    public static void tablist() {
        if (Bukkit.getVersion().contains("1.7")) {
            new TabHandler(new v1_7_R4TabAdapter(), new TablistProvider(), SharkHub.getInstance(), 20L);
        }
        if (Bukkit.getVersion().contains("1.8")) {
            new TabHandler(new v1_8_R3TabAdapter(), new TablistProvider(), SharkHub.getInstance(), 20L);
        }
        if (Bukkit.getVersion().contains("1.9")) {
            new TabHandler(new v1_9_R1TabAdapter(), new TablistProvider(), SharkHub.getInstance(), 20L);
        }
        if (Bukkit.getVersion().contains("1.10")) {
            new TabHandler(new v1_10_R1TabAdapter(), new TablistProvider(), SharkHub.getInstance(), 20L);
        }
        if (Bukkit.getVersion().contains("1.12")) {
            new TabHandler(new v1_12_R1TabAdapter(), new TablistProvider(), SharkHub.getInstance(), 20L);
        }
        if (Bukkit.getVersion().contains("1.14")) {
            new TabHandler(new v1_14_R1TabAdapter(), new TablistProvider(), SharkHub.getInstance(), 20L);
        }
        if (Bukkit.getVersion().contains("1.15")) {
            new TabHandler(new v1_15_R1TabAdapter(), new TablistProvider(), SharkHub.getInstance(), 20L);
        }
        if (Bukkit.getVersion().contains("1.16")) {
            new TabHandler(new v1_16_R3TabAdapter(), new TablistProvider(), SharkHub.getInstance(), 20L);
        }
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
