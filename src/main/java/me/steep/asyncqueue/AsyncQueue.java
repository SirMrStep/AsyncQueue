package me.steep.asyncqueue;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class AsyncQueue extends JavaPlugin {

    private static final BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();
    private static BukkitTask task;
    private static AsyncQueue plugin;

    @Override
    public void onLoad() {
        plugin = this;
    }

    /**
     * Adds a Runnable to the queue and starts the queue it if it was previously inactive.
     * @param r The {@link java.lang.Runnable} to add to the queue.
     */
    public static void add(Runnable r) {

        queue.add(r);

        if (task == null || task.isCancelled()) {
            task = Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
                while (!queue.isEmpty()) {
                    queue.poll().run();
                }
            });
        }

    }

}
