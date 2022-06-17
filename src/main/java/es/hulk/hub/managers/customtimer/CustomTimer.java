package es.hulk.hub.managers.customtimer;

import es.hulk.hub.SharkHub;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.scheduler.BukkitRunnable;

@Getter @Setter
public class CustomTimer {

    private String name;
    private String displayName;
    private long startMillis;
    private long endMillis;
    private long getRemaining;

    public CustomTimer(String name, String displayName, long startMillis, long endMillis) {
        this.name = name;
        this.displayName = displayName;
        this.startMillis = startMillis;
        this.endMillis = endMillis;

        new BukkitRunnable() {
            @Override
            public void run() {
                if (endMillis < System.currentTimeMillis()) {
                    SharkHub.getInstance().getCustomTimerManager().deleteTimer(SharkHub.getInstance().getCustomTimerManager().getCustomTimer(name));
                    this.cancel();
                }
            }
        }.runTaskTimerAsynchronously(SharkHub.getInstance(), 0L, 20L);
    }

    public long getRemaining(){
       return endMillis - System.currentTimeMillis();
    }

    public void cancel() {
        SharkHub.getInstance().getCustomTimerManager().deleteTimer(this);
    }
}
