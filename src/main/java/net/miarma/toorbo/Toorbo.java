package net.miarma.toorbo;

import net.miarma.toorbo.commands.CommandHandler;
import net.miarma.toorbo.config.ConfigWrapper;
import net.miarma.toorbo.config.CustomConfigManager;
import net.miarma.toorbo.common.minecraft.inventories.GlobalChest;
import net.miarma.toorbo.events.EventListener;
import net.miarma.toorbo.recipes.RecipeManager;
import net.miarma.toorbo.tasks.LocationTrackerTask;
import net.miarma.toorbo.util.FileUtil;
import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.nio.file.Files;
import java.util.logging.Logger;

public class Toorbo extends JavaPlugin {

    public static Toorbo PLUGIN;
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
        WORLD_BLOCKER_CONFIG = new CustomConfigManager(Toorbo.PLUGIN,"blockedWorlds.yml");

        if(!Files.exists(PLUGIN.getDataFolder().toPath().resolve("inventories/"))) {
            File file = new File(PLUGIN.getDataFolder(), "inventories/");
            file.mkdirs();
        }

        if(!Files.exists(PLUGIN.getDataFolder().toPath().resolve("warps/"))) {
            File file = new File(PLUGIN.getDataFolder(), "warps/");
            file.mkdirs();
        }

        FileUtil.createLangs("lang.yml");
        CommandAPI.onEnable();
        CommandHandler.onEnable();
        RecipeManager.onEnable();
        EventListener.onEnable();
        LocationTrackerTask.start();
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
