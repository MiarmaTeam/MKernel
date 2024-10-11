package dev.gallardo.miarmacore.config.providers;

import dev.gallardo.miarmacore.MiarmaCore;
import dev.gallardo.miarmacore.config.CommandWrapper;
import dev.gallardo.miarmacore.config.PermissionWrapper;

public class CommandProvider {
    public static CommandWrapper getBaseCommand() {
        return CommandWrapper.of("miarmacore",
                MiarmaCore.CONFIG.getString("commands.miarmacore.description"),
                MiarmaCore.CONFIG.getString("commands.miarmacore.usage"),
                PermissionWrapper.of(MiarmaCore.CONFIG.getString("commands.miarmacore.permission")),
                new String[]{
                    MiarmaCore.CONFIG.getString("commands.miarmacore.messages.success")
                },
                new CommandWrapper[]{
                    CommandWrapper.of("reload",
                        MiarmaCore.CONFIG.getString("commands.miarmacore.subcommands.reload.description"),
                        MiarmaCore.CONFIG.getString("commands.miarmacore.subcommands.reload.usage"),
                        PermissionWrapper.of(MiarmaCore.CONFIG.getString("commands.miarmacore.subcommands.reload.permission")),
                        new String[]{
                            MiarmaCore.CONFIG.getString("commands.miarmacore.subcommands.reload.messages.success")
                        }
                    )
                });
    }
}
