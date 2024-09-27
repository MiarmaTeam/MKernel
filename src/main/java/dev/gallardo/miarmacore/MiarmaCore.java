package dev.gallardo.miarmacore;

import dev.gallardo.miarmacore.commands.CommandHandler;
import dev.gallardo.miarmacore.util.ConfigWrapper;
import dev.gallardo.miarmacore.util.Utils;
import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class MiarmaCore extends JavaPlugin {

    public static MiarmaCore plugin;
    private static final ConfigWrapper config = new ConfigWrapper();
    public static Logger logger;

    public static ConfigWrapper getConf() {
    	return config;
    }

    @Override
    public void onEnable() {
        super.onEnable();
        plugin = this;
        logger = plugin.getLogger();
        config.onEnable();
        Utils.createLangs("lang.yml");
        CommandAPI.onEnable();
        CommandHandler.onEnable();
        this.getLogger().info("I've been enabled! :)");
    }

    @Override
    public void onLoad() {
        CommandAPI.onLoad(new CommandAPIBukkitConfig(this).verboseOutput(true));
    }

    @Override
    public void onDisable() {
        super.onDisable();
        this.getLogger().info("I've been disabled! :(");
    }
}
