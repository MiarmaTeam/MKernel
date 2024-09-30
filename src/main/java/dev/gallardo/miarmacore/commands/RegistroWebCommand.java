package dev.gallardo.miarmacore.commands;

import dev.gallardo.miarmacore.util.Utils;
import dev.gallardo.miarmacore.util.WebAPIAccessor;
import dev.jorel.commandapi.CommandAPICommand;
import static dev.gallardo.miarmacore.util.Constants.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RegistroWebCommand {
    public static void register() {
        new CommandAPICommand("registroweb")
            .withOptionalArguments(PASSWORD_ARG)
            .withFullDescription(CONFIG.getString("commands.register.description"))
            .withPermission("miarmacore.register")
            .withShortDescription(CONFIG.getString("commands.register.description"))
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
                                    CONFIG.getString("language.commands.register.success"),
                                    List.of("%user%", "%password%"),
                                    List.of(username, password)
                            ),
                            sender,true);
                } else {
                    Utils.sendMessage(CONFIG.getString("language.commands.register.error"),
                            sender,true);
                }
            })
            .register();
    }
}
