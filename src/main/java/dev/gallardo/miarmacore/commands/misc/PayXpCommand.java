package dev.gallardo.miarmacore.commands.misc;

import dev.gallardo.miarmacore.config.CommandWrapper;
import dev.gallardo.miarmacore.config.providers.CommandProvider;
import dev.gallardo.miarmacore.config.providers.MessageProvider;
import dev.gallardo.miarmacore.util.MessageUtils;
import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.List;

import static dev.gallardo.miarmacore.config.providers.CommandProvider.Arguments.LEVELS;
import static dev.gallardo.miarmacore.config.providers.CommandProvider.Arguments.PLAYERS_ARG;

public class PayXpCommand {
    public static void register() {
        CommandWrapper payXpCmd = CommandProvider.getPayXpCommand();
        new CommandAPICommand(payXpCmd.getName())
            .withArguments(PLAYERS_ARG, LEVELS)
            .withFullDescription(payXpCmd.getDescription())
            .withPermission(payXpCmd.getPermission().base())
            .withShortDescription(payXpCmd.getDescription())
            .executesPlayer((sender, args) -> {
                Player victim = Bukkit.getPlayer(args.getRaw(0));
                Integer cantidad = Integer.valueOf(args.getRaw(1));

                if(args.count() > 2) {
                    MessageUtils.sendMessage(MessageProvider.Errors.tooManyArguments(), sender, true);
                }

                if(sender.getLevel()>0) {
                    sender.setLevel(sender.getLevel()-cantidad);
                    victim.setLevel(victim.getLevel()+cantidad);
                    MessageUtils.sendMessage(payXpCmd.getMessages()[0], sender, true,
                            true, List.of("%target%", "%amount%"), List.of(victim.getName(), cantidad.toString()));
                    MessageUtils.sendMessage(payXpCmd.getMessages()[1], victim, true,
                            true, List.of("%sender%", "%amount%"), List.of(sender.getName(), cantidad.toString()));
                    sender.playSound(sender, Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
                    victim.playSound(victim, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                } else {
                    MessageUtils.sendMessage(MessageProvider.Errors.notEnoughLevels(), sender, true);
                }
            })
            .register();
    }
}
