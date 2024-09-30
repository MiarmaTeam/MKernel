package dev.gallardo.miarmacore.commands.roleplay;

import dev.gallardo.miarmacore.util.Utils;
import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.Bukkit;

import java.util.Arrays;
import java.util.stream.Collectors;

import static dev.gallardo.miarmacore.util.Constants.*;

public class MeCommand {
    public static void register() {
        CommandAPI.unregister("me");
        new CommandAPICommand(CONFIG.getString("commands.me.name"))
            .withArguments(MESSAGE)
            .withFullDescription(CONFIG.getString("commands.me.description"))
            .withPermission(CONFIG.getString("commands.me.permission"))
            .withShortDescription(CONFIG.getString("commands.me.description"))
            .executesPlayer((sender, args) -> {
                String joinedArgs = Arrays.stream(args.rawArgs()).collect(Collectors.joining(" "));
                String msg = "§6(" + sender.getName() + ") [Me] §7" + joinedArgs;
                Bukkit.getServer().getOnlinePlayers().stream()
                        .filter(p -> (p.getWorld() == sender.getWorld()) && (Utils.distance(sender, p) < 25 || sender.equals(p)))
                        .forEach(p -> p.sendMessage(msg));
            })
            .register();
    }
}
