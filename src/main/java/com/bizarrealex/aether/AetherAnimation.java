package com.bizarrealex.aether;

import es.hulk.hub.SharkHub;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Hulk
 * at 21/02/2022 13:06
 * Project: Atlas
 * Class: ScoreboardAnimation
 */

public class AetherAnimation {

    public static String title, footer;

    public static void init() {
        List<String> titles = SharkHub.getInstance().getScoreboardConfig().getStringList("ANIMATED_SCOREBOARD.TITLE.LINES");
        List<String> footers = SharkHub.getInstance().getScoreboardConfig().getStringList("ANIMATED_SCOREBOARD.FOOTER.LINES");

        title = titles.get(0);
        footer = footers.get(0);

        if (SharkHub.getInstance().getScoreboardConfig().getBoolean("ANIMATED_SCOREBOARD.TITLE.ENABLE")) {
            AtomicInteger atomicInteger = new AtomicInteger();

            SharkHub.getInstance().getServer().getScheduler().runTaskTimerAsynchronously(SharkHub.getInstance(), () -> {
                if (atomicInteger.get() == titles.size()) atomicInteger.set(0);

                title = titles.get(atomicInteger.getAndIncrement());

            }, 0L, (long) (SharkHub.getInstance().getScoreboardConfig().getDouble("ANIMATED_SCOREBOARD.TITLE.TIME") * 20L));
        }

        if (SharkHub.getInstance().getScoreboardConfig().getBoolean("ANIMATED_SCOREBOARD.FOOTER.ENABLE")) {
            AtomicInteger atomicInteger = new AtomicInteger();

            SharkHub.getInstance().getServer().getScheduler().runTaskTimerAsynchronously(SharkHub.getInstance(), () -> {
                if (atomicInteger.get() == footers.size()) atomicInteger.set(0);

                footer = footers.get(atomicInteger.getAndIncrement());

            }, 0L, (long) (SharkHub.getInstance().getScoreboardConfig().getDouble("ANIMATED_SCOREBOARD.FOOTER.TIME") * 20L));
        }
    }

    public static String getScoreboardTitle() {
        return title;
    }

    public static String getScoreboardFooter() {
        return footer;
    }

}
