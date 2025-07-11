package net.miarma.mkernel.listeners;

import net.miarma.mkernel.common.annotation.Version;
import net.miarma.mkernel.config.providers.MessageProvider;
import net.miarma.mkernel.util.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

import static net.miarma.mkernel.common.Constants.SPY_KEY;

public class SpyListener implements Listener {
    
    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        Player sender = event.getPlayer();
        String message = event.getMessage();

        for (Player receiver : Bukkit.getOnlinePlayers()) {
            if (receiver.equals(sender)) continue;

            PersistentDataContainer data = receiver.getPersistentDataContainer();
            boolean isSpying = Boolean.TRUE.equals(data.get(SPY_KEY, PersistentDataType.BOOLEAN));

            if (isSpying) {
                MessageUtil.sendMessage(
                        receiver,
                        MessageProvider.Events.getOnCommandSpyMessage(),
                        false,
                        List.of("%player%", "%message%"),
                        List.of(sender.getName(), message)
                );
            }
        }
    }
}
