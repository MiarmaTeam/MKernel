package dev.gallardo.miarmacore.commands.roleplay;

import dev.gallardo.miarmacore.MiarmaCore;
import dev.gallardo.miarmacore.util.Utils;
import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.Bukkit;

import java.util.Arrays;
import java.util.stream.Collectors;

import static dev.gallardo.miarmacore.util.Constants.*;

public class DoCommand {
    public static void register() {
        new CommandAPICommand(MiarmaCore.CONFIG.getString("commands.do.name"))
            .withArguments(MESSAGE)
            .withFullDescription(MiarmaCore.CONFIG.getString("commands.do.description"))
            .withPermission(MiarmaCore.CONFIG.getString("commands.do.permission"))
            .withShortDescription(MiarmaCore.CONFIG.getString("commands.do.description"))
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
