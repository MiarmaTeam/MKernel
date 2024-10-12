package dev.gallardo.miarmacore.commands.misc;

import dev.gallardo.miarmacore.MiarmaCore;
import dev.gallardo.miarmacore.config.CommandWrapper;
import dev.gallardo.miarmacore.config.providers.CommandProvider;
import dev.gallardo.miarmacore.util.Utils;
import dev.gallardo.miarmacore.common.WebAPIAccessor;
import dev.jorel.commandapi.CommandAPICommand;

import static dev.gallardo.miarmacore.config.providers.CommandProvider.Arguments.PASSWORD_ARG;
import static dev.gallardo.miarmacore.util.Constants.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RegistroWebCommand {
    public static void register() {
        CommandWrapper registerWebCmd = CommandProvider.getRegisterWebCommand();
        new CommandAPICommand(registerWebCmd.getName())
            .withOptionalArguments(PASSWORD_ARG)
            .withFullDescription(registerWebCmd.getDescription())
            .withPermission(registerWebCmd.getPermission().base())
            .withShortDescription(registerWebCmd.getDescription())
            .withUsage(registerWebCmd.getUsage())
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
                    Utils.sendMessage(registerWebCmd.getMessages()[0], sender, true, true,
                            List.of("%user%", "%password%"), List.of(username, password));
                } else {
                    Utils.sendMessage(registerWebCmd.getMessages()[1], sender,true);
                }
            })
            .register();
    }
}
