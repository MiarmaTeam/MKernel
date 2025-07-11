package net.miarma.mkernel.common.minecraft;

import net.miarma.mkernel.MKernel;
import net.miarma.mkernel.common.annotation.Version;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Recipe;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Method;

public class VersionedRegistrar {

    // === LISTENERS ===
    public static void registerListener(Plugin plugin, Listener listener) {
        Class<?> clazz = listener.getClass();

        Version version = clazz.getAnnotation(Version.class);
        if (version != null && !isVersionCompatible(version.value())) {
            MKernel.LOGGER.warning("No se registró " + clazz.getSimpleName() +
                    " porque requiere la versión " + version.value());
            return;
        }

        Listener wrapped = new FilteredListener(listener);
        Bukkit.getPluginManager().registerEvents(wrapped, plugin);
    }

    private record FilteredListener(Listener original) implements Listener {
        @EventHandler
        public void handle(Event event) {
            for (Method method : original.getClass().getDeclaredMethods()) {
                if (!method.isAnnotationPresent(EventHandler.class)) continue;
                if (!method.getParameterTypes()[0].isAssignableFrom(event.getClass())) continue;

                Version version = method.getAnnotation(Version.class);
                if (version != null && !isVersionCompatible(version.value())) continue;

                try {
                    method.setAccessible(true);
                    method.invoke(original, event);
                } catch (Exception e) {
                    MKernel.LOGGER.severe("Error manejando el evento " + event.getClass().getSimpleName());
                }
            }
        }
    }

    // === COMANDOS ===
    public static void registerCommand(Class<?> commandClass) {
        Version version = commandClass.getAnnotation(Version.class);
        if (version != null && !isVersionCompatible(version.value())) {
            MKernel.LOGGER.warning("No se registró el comando " + commandClass.getSimpleName()
                    + " porque requiere la versión " + version.value());
            return;
        }

        try {
            Method registerMethod = commandClass.getMethod("register");
            registerMethod.invoke(null);
        } catch (Exception e) {
            MKernel.LOGGER.severe("Error al registrar el comando " + commandClass.getSimpleName() + ": " + e.getMessage());
        }
    }


    // === RECETAS ===
    public static void registerRecipe(Class<?> clazz) {
        Version version = clazz.getAnnotation(Version.class);
        if (version != null && !isVersionCompatible(version.value())) {
            MKernel.LOGGER.warning("No se registró la receta " + clazz.getSimpleName() +
                    " porque requiere la versión " + version.value());
            return;
        }

        try {
            Method method = clazz.getMethod("getRecipe");
            Recipe recipe = (Recipe) method.invoke(null);

            if (recipe != null) {
                Bukkit.addRecipe(recipe);
            } else {
                MKernel.LOGGER.warning("La receta en " + clazz.getSimpleName() + " devolvió null.");
            }

        } catch (Exception e) {
            MKernel.LOGGER.warning("No se pudo registrar receta: " + clazz.getSimpleName());
        }
    }

    // === LÓGICA DE VERSIÓN ===
    public static boolean isVersionCompatible(String required) {
        String current = Bukkit.getBukkitVersion().split("-")[0]; // Ej: "1.21.6"
        return compareVersions(current, required) >= 0;
    }

    public static int compareVersions(String v1, String v2) {
        String[] a1 = v1.split("\\.");
        String[] a2 = v2.split("\\.");

        for (int i = 0; i < Math.max(a1.length, a2.length); i++) {
            int n1 = i < a1.length ? Integer.parseInt(a1[i]) : 0;
            int n2 = i < a2.length ? Integer.parseInt(a2[i]) : 0;

            if (n1 != n2) return Integer.compare(n1, n2);
        }
        return 0;
    }
}
