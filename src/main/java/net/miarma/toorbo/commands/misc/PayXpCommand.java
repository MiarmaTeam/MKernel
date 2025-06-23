package net.miarma.toorbo.commands.misc;

import net.miarma.toorbo.config.CommandWrapper;
import net.miarma.toorbo.config.providers.CommandProvider;
import net.miarma.toorbo.config.providers.MessageProvider;
import net.miarma.toorbo.util.MessageUtil;
import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.List;

import static net.miarma.toorbo.config.providers.CommandProvider.Arguments.*;

public class PayXpCommand {
    public static void register() {
        CommandWrapper payXpCmd = CommandProvider.getPayXpCommand();
        new CommandAPICommand(payXpCmd.getName())
            .withArguments(PLAYER_ARG, LEVELS)
            .withFullDescription(payXpCmd.getDescription())
            .withPermission(payXpCmd.getPermission().base())
            .withShortDescription(payXpCmd.getDescription())
            .executesPlayer((sender, args) -> {
                Player victim = Bukkit.getPlayer(args.getRaw(0));
                Integer cantidad = Integer.valueOf(args.getRaw(1));

                if(args.count() > 2) {
                    MessageUtil.sendMessage(sender, MessageProvider.Errors.tooManyArguments(), true);
                }

                if(sender.getLevel()>0) {
                    sender.setLevel(sender.getLevel()-cantidad);
                    victim.setLevel(victim.getLevel()+cantidad);
                    MessageUtil.sendMessage(sender, payXpCmd.getMessages()[0], true,
                                                 List.of("%target%", "%amount%"),
                                                 List.of(victim.getName(), cantidad.toString()));
                    MessageUtil.sendMessage(victim, payXpCmd.getMessages()[1], true,
                                                 List.of("%sender%", "%amount%"),
                                                 List.of(sender.getName(), cantidad.toString()));
                    sender.playSound(sender, Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
                    victim.playSound(victim, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                } else {
                    MessageUtil.sendMessage(sender, MessageProvider.Errors.notEnoughLevels(), true);
                }
            })
            .register();
    }
}
