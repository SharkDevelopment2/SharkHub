package es.hulk.hub.bungee;

import org.bukkit.scheduler.BukkitRunnable;

public class BungeeTask extends BukkitRunnable {

    @Override
    public void run() {
        BungeeUtils.refreshGlobalCount();
        BungeeUtils.refreshServerList();
        BungeeUtils.refreshServerCount();
    }
}
