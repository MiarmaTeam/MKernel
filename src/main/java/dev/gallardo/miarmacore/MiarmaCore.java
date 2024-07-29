package dev.gallardo.miarmacore;

import dev.gallardo.miarmacore.commands.CommandHandler;
import dev.gallardo.miarmacore.events.EventHandlers;
import dev.gallardo.miarmacore.util.ConfigWrapper;
import dev.gallardo.miarmacore.util.Utils;
import dev.jorel.commandapi.CommandAPI;
import org.bukkit.plugin.java.JavaPlugin;

public final class MiarmaCore extends JavaPlugin {

    private static final MiarmaCore plugin = MiarmaCore.getPlugin(MiarmaCore.class);
    private static final ConfigWrapper config = new ConfigWrapper();

    public static ConfigWrapper getConf() {
    	return config;
    }

    public static MiarmaCore getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        super.onEnable();
        config.onEnable();
        Utils.createLangs();
        CommandAPI.onEnable();
        CommandHandler.onEnable();
        EventHandlers.onEnable();
        this.getLogger().info("I've been enabled! :)");
    }

    @Override
    public void onDisable() {
        super.onDisable();
        this.getLogger().info("I've been disabled! :(");
    }
}
