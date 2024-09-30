package dev.gallardo.miarmacore.commands.misc;

import dev.gallardo.miarmacore.MiarmaCore;
import dev.gallardo.miarmacore.util.Utils;
import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.List;

import static dev.gallardo.miarmacore.util.Constants.*;

public class PayXpCommand {
    public static void register() {
        new CommandAPICommand(MiarmaCore.CONFIG.getString("commands.payxp.name"))
            .withArguments(PLAYERS_ARG, LEVELS)
            .withFullDescription(MiarmaCore.CONFIG.getString("commands.payxp.description"))
            .withPermission(MiarmaCore.CONFIG.getString("commands.payxp.permission"))
            .withShortDescription(MiarmaCore.CONFIG.getString("commands.payxp.description"))
            .executesPlayer((sender, args) -> {
                Player victim = Bukkit.getPlayer(args.getRaw(0));
                Integer cantidad = Integer.valueOf(args.getRaw(1));

                if(args.count() > 2) {
                    Utils.sendMessage(MiarmaCore.CONFIG.getString("language.errors.tooManyArguments"), sender, true);
                }

                if(sender.getLevel()>0) {
                    sender.setLevel(sender.getLevel()-cantidad);
                    victim.setLevel(victim.getLevel()+cantidad);
                    Utils.sendMessage(MiarmaCore.CONFIG.getString("commands.payxp.messages.payYouOthers"), sender, true,
                            true, List.of("%target%", "%amount%"), List.of(victim.getName(), cantidad.toString()));
                    Utils.sendMessage(MiarmaCore.CONFIG.getString("commands.payxp.messages.payOthersYou"), victim, true,
                            true, List.of("%sender%", "%amount%"), List.of(sender.getName(), cantidad.toString()));
                    sender.playSound(sender, Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
                    victim.playSound(victim, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                } else {
                    Utils.sendMessage(MiarmaCore.CONFIG.getString("language.errors.notEnoughLevels"), sender, true);
                }
            })
            .register();
    }
}
