package dev.gallardo.miarmacore.tasks;

import dev.gallardo.miarmacore.MiarmaCore;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.scheduler.BukkitRunnable;

public class DBDataTask {
    public static void start() {
        new BukkitRunnable() {
            @Override
            public void run() {
                
            }
        }.runTaskTimerAsynchronously(MiarmaCore.plugin, 0, 6000);
    }
}
