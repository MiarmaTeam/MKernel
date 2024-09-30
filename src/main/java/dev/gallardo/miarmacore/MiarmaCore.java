package dev.gallardo.miarmacore;

import dev.gallardo.miarmacore.commands.CommandHandler;
import dev.gallardo.miarmacore.common.minecraft.GlobalChest;
import dev.gallardo.miarmacore.events.EventListener;
import dev.gallardo.miarmacore.recipes.RecipeManager;
import dev.gallardo.miarmacore.tasks.LocationTracker;
import dev.gallardo.miarmacore.util.Utils;
import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import org.bukkit.plugin.java.JavaPlugin;
import static dev.gallardo.miarmacore.util.Constants.*;

public class MiarmaCore extends JavaPlugin {

    public static MiarmaCore PLUGIN;
    
    @Override
    public void onEnable() {
        super.onEnable();
        PLUGIN = this;
        // FILES
        CONFIG.onEnable();
        Utils.createLangs("lang.yml");
        // onEnable()'s
        CommandAPI.onEnable();
        CommandHandler.onEnable();
        RecipeManager.onEnable();
        EventListener.onEnable();
        // GLOBAL CHEST
        GlobalChest.loadConfig();
        GlobalChest.loadChest();
        // LOCATION TRACKER
        LocationTracker.startLocationTrackingTask();
        // PLUGIN ENABLED :-)
        this.getLogger().info("I've been enabled! :)");
    }

    @Override
    public void onLoad() {
        CommandAPI.onLoad(new CommandAPIBukkitConfig(this).verboseOutput(true));
    }

    @Override
    public void onDisable() {
        super.onDisable();
        GlobalChest.saveChest();
        this.getLogger().info("I've been disabled! :(");
    }
}
