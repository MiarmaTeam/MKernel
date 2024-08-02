package dev.gallardo.miarmacore.events;

import dev.gallardo.miarmacore.MiarmaCore;
import dev.gallardo.miarmacore.util.ConfigWrapper;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.jetbrains.annotations.Async;

public class EventHandlers {
    private static final ConfigWrapper cfg = MiarmaCore.getConf();

    public static void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new Listener() {
            @EventHandler
            public void onPlayerChat(AsyncPlayerChatEvent event) {
                event.getPlayer().sendMessage("A");
            }
        }, MiarmaCore.plugin);
    }

    public static void onEnable() {
        registerEvents();
    }
}
