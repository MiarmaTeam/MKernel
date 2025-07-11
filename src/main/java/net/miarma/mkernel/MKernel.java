package net.miarma.mkernel;

import net.miarma.mkernel.commands.CommandManager;
import net.miarma.mkernel.config.ConfigWrapper;
import net.miarma.mkernel.config.CustomConfigManager;
import net.miarma.mkernel.common.minecraft.inventories.GlobalChest;
import net.miarma.mkernel.listeners.ListenerManager;
import net.miarma.mkernel.recipes.RecipeManager;
import net.miarma.mkernel.tasks.LocationTrackerTask;
import net.miarma.mkernel.util.FileUtil;
import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.nio.file.Files;
import java.util.logging.Logger;

public class MKernel extends JavaPlugin {

    public static MKernel PLUGIN;
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
        WORLD_BLOCKER_CONFIG = new CustomConfigManager(MKernel.PLUGIN,"blockedWorlds.yml");

        if(!Files.exists(PLUGIN.getDataFolder().toPath().resolve("inventories/"))) {
            File file = new File(PLUGIN.getDataFolder(), "inventories/");
            file.mkdirs();
        }

        if(!Files.exists(PLUGIN.getDataFolder().toPath().resolve("warps/"))) {
            File file = new File(PLUGIN.getDataFolder(), "warps/");
            file.mkdirs();
        }

        FileUtil.createLangs("lang.yml");

        // CommandAPI
        CommandAPI.onEnable();

        // Mis managers propios
        CommandManager.onEnable();
        RecipeManager.onEnable();
        ListenerManager.onEnable();

        // Inicializaciones finales
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
