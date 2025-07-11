package net.miarma.mkernel.listeners;

import net.md_5.bungee.api.ChatColor;
import net.miarma.mkernel.common.annotation.Version;
import net.miarma.mkernel.config.providers.ConfigProvider;
import net.miarma.mkernel.config.providers.MessageProvider;
import net.miarma.mkernel.util.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.persistence.PersistentDataContainer;

import java.util.List;

public class ChatListener implements Listener {
    
    @EventHandler
    public void onPlayerMessage(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        if (ConfigProvider.Modules.isAdminChatEnabled() && event.getMessage().startsWith("#")) {
            handleAdminChat(event);
            return;
        }

        if (ConfigProvider.Modules.isChatFormatEnabled() &&
                player.hasPermission(ConfigProvider.Permissions.getChatFormatPermission())) {
            event.setMessage(MessageUtil.parseColors(event.getMessage()));
        }
    }

    private void handleAdminChat(AsyncPlayerChatEvent event) {
        Player sender = event.getPlayer();

        if (!sender.hasPermission(ConfigProvider.Permissions.getAdminChatPermission())) {
            event.setCancelled(true);
            MessageUtil.sendMessage(sender, MessageProvider.Errors.noPermission(), true);
            return;
        }

        String formattedMsg = MessageUtil.parseColors(
                MessageProvider.getAdminPrefix() + " &7" + sender.getName() + "&b: &r"
        ) + event.getMessage().substring(1).replaceFirst("^\\s*", "");

        event.setCancelled(true);

        Bukkit.getOnlinePlayers().stream()
                .filter(p -> p.hasPermission(ConfigProvider.Permissions.getAdminChatPermission()))
                .forEach(p -> p.sendRawMessage(formattedMsg));
    }

    
    @EventHandler
    public void onMention(AsyncPlayerChatEvent event) {
        if (!ConfigProvider.Modules.isMentionsEnabled()) return;

        Player sender = event.getPlayer();
        if (!sender.hasPermission(ConfigProvider.Permissions.getMentionsPermission())) return;

        String message = event.getMessage();
        String updatedMessage = message;

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            String mentionTag = "@" + onlinePlayer.getName();

            if (message.contains(mentionTag)) {
                String formattedMention = MessageUtil.parseColors(
                        MessageProvider.Events.getOnMentionFormat() + onlinePlayer.getName()) + ChatColor.RESET;
                updatedMessage = updatedMessage.replace(mentionTag, formattedMention);
                MessageUtil.sendMessage(onlinePlayer, MessageProvider.Events.getOnMentionMessage(), true,
                        List.of("%player%"), List.of(sender.getName()));
                onlinePlayer.playSound(onlinePlayer, Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1f);
            }
        }

        if (!message.equals(updatedMessage)) {
            event.setMessage(updatedMessage);
        }
    }

}
