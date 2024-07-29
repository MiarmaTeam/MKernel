package dev.gallardo.miarmacore.events;

import dev.gallardo.miarmacore.MiarmaCore;
import dev.gallardo.miarmacore.util.ConfigWrapper;
import dev.gallardo.miarmacore.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class EventHandlers {
    private static final ConfigWrapper cfg = MiarmaCore.getConf();

    public static void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new Listener() {
            @EventHandler
            public void onPlayerReceiveMessage(AsyncPlayerChatEvent event) {
                String message = event.getMessage();
                if(message.contains("[P]")) {
                    event.setMessage(
                        Utils.colorCodeParser(message.replace("[P]", cfg.getString("prefix")))
                    );
                }
            }
        }, MiarmaCore.getPlugin());
    }

    public static void onEnable() {
        registerEvents();
    }
}
