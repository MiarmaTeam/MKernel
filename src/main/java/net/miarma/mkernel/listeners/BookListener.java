package net.miarma.mkernel.listeners;

import net.miarma.mkernel.common.annotation.Version;
import net.miarma.mkernel.config.providers.ConfigProvider;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.inventory.meta.BookMeta;

import java.util.List;

public class BookListener implements Listener {
    
    @EventHandler
    public void onBookWrite(PlayerEditBookEvent event) {
        if (!ConfigProvider.Modules.isBookColorsEnabled()) return;

        BookMeta meta = event.getNewBookMeta();
        List<String> coloredPages = meta.getPages().stream()
                .map(page -> page.replace('&', '§'))
                .toList();

        meta.setPages(coloredPages);
        event.setNewBookMeta(meta);
    }
}
