package dev.gallardo.miarmacore;

import dev.gallardo.miarmacore.commands.CommandHandler;
import dev.gallardo.miarmacore.config.ConfigWrapper;
import dev.gallardo.miarmacore.config.CustomConfigManager;
import dev.gallardo.miarmacore.common.minecraft.GlobalChest;
import dev.gallardo.miarmacore.events.EventListener;
import dev.gallardo.miarmacore.recipes.RecipeManager;
import dev.gallardo.miarmacore.tasks.LocationTracker;
import dev.gallardo.miarmacore.util.FileUtils;
import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.nio.file.Files;
import java.util.logging.Logger;

public class MiarmaCore extends JavaPlugin {

    public static MiarmaCore PLUGIN;
    public static final ConfigWrapper CONFIG = new ConfigWrapper();
    public static CustomConfigManager HOME_CONFIG;
    public static CustomConfigManager WORLD_BLOCKER_CONFIG;
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
        HOME_CONFIG = new CustomConfigManager(PLUGIN, "homes.yml");
        WORLD_BLOCKER_CONFIG = new CustomConfigManager(MiarmaCore.PLUGIN,"blockedWorlds.yml");

        if(!Files.exists(PLUGIN.getDataFolder().toPath().resolve("inventories/"))) {
            File file = new File(PLUGIN.getDataFolder(), "inventories/");
            file.mkdirs();
        }

        if(!Files.exists(PLUGIN.getDataFolder().toPath().resolve("warps/"))) {
            File file = new File(PLUGIN.getDataFolder(), "warps/");
            file.mkdirs();
        }

        FileUtils.createLangs("lang.yml");
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
