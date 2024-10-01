package dev.gallardo.miarmacore;

import dev.gallardo.miarmacore.commands.CommandHandler;
import dev.gallardo.miarmacore.common.ConfigWrapper;
import dev.gallardo.miarmacore.common.CustomConfigManager;
import dev.gallardo.miarmacore.common.minecraft.GlobalChest;
import dev.gallardo.miarmacore.events.EventListener;
import dev.gallardo.miarmacore.recipes.RecipeManager;
import dev.gallardo.miarmacore.tasks.LocationTracker;
import dev.gallardo.miarmacore.util.Utils;
import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class MiarmaCore extends JavaPlugin {

    public static MiarmaCore PLUGIN;
    public static final ConfigWrapper CONFIG = new ConfigWrapper();
    public static CustomConfigManager HOMES_CONFIG = new CustomConfigManager(MiarmaCore.PLUGIN,"homes.yml");
    public static CustomConfigManager BLOCK_WORLD_CONFIG = new CustomConfigManager(MiarmaCore.PLUGIN,"blockedWorlds.yml");
    public static Logger LOGGER;

    @Override
    public void onLoad() {
        CommandAPI.onLoad(new CommandAPIBukkitConfig(this).verboseOutput(true));
    }
    
    public void onEnable() {
        super.onEnable();
        PLUGIN = this;
        LOGGER = PLUGIN.getLogger();
        CONFIG.onEnable();
        Utils.createLangs("lang.yml");
        CommandAPI.onEnable();
        CommandHandler.onEnable();
        RecipeManager.onEnable();
        EventListener.onEnable();
        LocationTracker.startLocationTrackingTask();
        GlobalChest.loadConfig();
        GlobalChest.loadChest();
        this.getLogger().info("I've been enabled! :)");
    }

    @Override
    public void onDisable() {
        super.onDisable();
        GlobalChest.saveChest();
        this.getLogger().info("I've been disabled! :(");
    }
}
