package dev.gallardo.miarmacore.commands.roleplay;

import dev.gallardo.miarmacore.config.CommandWrapper;
import dev.gallardo.miarmacore.config.providers.CommandProvider;
import dev.gallardo.miarmacore.util.Utils;
import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.Bukkit;

import java.util.Arrays;
import java.util.stream.Collectors;

import static dev.gallardo.miarmacore.config.providers.CommandProvider.Arguments.MESSAGE;
import static dev.gallardo.miarmacore.util.Constants.*;

public class DoCommand {
    public static void register() {
        CommandWrapper doCmd = CommandProvider.getDoCommand();
        new CommandAPICommand(doCmd.getName())
            .withArguments(MESSAGE)
            .withFullDescription(doCmd.getDescription())
            .withPermission(doCmd.getPermission().base())
            .withShortDescription(doCmd.getDescription())
            .executesPlayer((sender, args) -> {
                String joinedArgs = Arrays.stream(args.rawArgs()).collect(Collectors.joining(" "));
                String msg = "§9(" + sender.getName() + ") [Do] §7" + joinedArgs;
                Bukkit.getServer().getOnlinePlayers().stream()
                        .filter(p -> (p.getWorld() == sender.getWorld()) && (Utils.distance(sender, p) < 25 || sender.equals(p)))
                        .forEach(p -> p.sendMessage(msg));
            })
            .register();
    }
}
