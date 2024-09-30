package dev.gallardo.miarmacore.util;

import dev.gallardo.miarmacore.MiarmaCore;
import org.bukkit.Bukkit;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import java.util.function.Consumer;

import static dev.gallardo.miarmacore.util.Constants.*;

public class UpdaterUtil {
    private MiarmaCore plugin;
    private int resourceId;

    public UpdaterUtil(MiarmaCore plugin, int resourceId) {
        this.plugin = plugin;
        this.resourceId = resourceId;
    }

	public void getLatestVersion(Consumer<String> consumer) {
        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
            try (InputStream inputStream = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + this.resourceId).openStream();
                 Scanner scanner = new Scanner(inputStream);){
                if (scanner.hasNext()) {
                    consumer.accept(scanner.next());
                }
            }
            catch (IOException exception) {
                LOGGER.info("There was an error finding an update!" + exception.getMessage());
            }
        });
    }
}
