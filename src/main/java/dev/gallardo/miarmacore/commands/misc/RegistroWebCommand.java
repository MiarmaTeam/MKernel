package dev.gallardo.miarmacore.commands.misc;

import dev.gallardo.miarmacore.util.Utils;
import dev.gallardo.miarmacore.common.WebAPIAccessor;
import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.entity.Player;

import static dev.gallardo.miarmacore.util.Constants.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RegistroWebCommand {
    public static void register() {
        new CommandAPICommand(CONFIG.getString("commands.registerweb.name"))
            .withOptionalArguments(PASSWORD_ARG)
            .withFullDescription(CONFIG.getString("commands.registerweb.description"))
            .withPermission(CONFIG.getString("commands.registerweb.permission"))
            .withShortDescription(CONFIG.getString("commands.registerweb.description"))
            .withUsage(CONFIG.getString("commands.registerweb.usage"))
            .executesPlayer((sender,args) -> {
                String username = sender.getName();
                String password;
                String rol = sender.hasPermission("group.admin") ? "admin" : "user";
                boolean userPassword = args.rawArgs() != null && args.rawArgs().length > 0;

                if(userPassword) {
                    password = Arrays.stream(args.rawArgs()).collect(Collectors.joining(" "));
                } else {
                    password = Utils.generateRandomPassword(8);
                }

                if(WebAPIAccessor.register(username,password,rol)) {
                    Utils.sendMessage(
                            Utils.placeholderParser(
                                    CONFIG.getString("commands.registerweb.messages.success"),
                                    List.of("%user%", "%password%"),
                                    List.of(username, password)
                            ),
                            sender,true);
                } else {
                    Utils.sendMessage(CONFIG.getString("commands.registerweb.messages.error"),
                            sender,true);
                }
            })
            .register();
    }
}
