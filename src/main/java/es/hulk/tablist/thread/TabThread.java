package es.hulk.tablist.thread;

import es.hulk.tablist.TablistModule;
import org.bukkit.entity.Player;

/**
 * Created By LeandroSSJ
 * Created on 31/07/2022
 */
public class TabThread extends Thread {

    public TabThread() {
        this.setName("Horizon - Tab Thread");
        this.setDaemon(true);
    }


    @Override
    public void run() {
        while (true) {
            try {
                TablistModule.INSTANCE.getTabStorage().forEach((uniqueId, tab) -> tab.update());
            } catch (Exception exception) {
                exception.printStackTrace();
            }

            try {
                Thread.sleep(300L);
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
        }
    }
}
