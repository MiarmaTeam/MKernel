package dev.gallardo.miarmacore.config.providers;

import dev.gallardo.miarmacore.MiarmaCore;
import dev.gallardo.miarmacore.common.minecraft.TpaRequest;
import dev.gallardo.miarmacore.config.CommandWrapper;
import dev.gallardo.miarmacore.config.PermissionWrapper;
import dev.gallardo.miarmacore.util.ItemUtils;
import dev.jorel.commandapi.arguments.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static dev.gallardo.miarmacore.util.Constants.RECIPES;
import static dev.gallardo.miarmacore.util.Constants.TPA_REQUESTS;

public class CommandProvider {
    public static class Arguments {
        public static final Argument<?> PASSWORD_ARG = new GreedyStringArgument(MiarmaCore.CONFIG.getString("arguments.password"));

        public static Argument<?> PLAYER_ARG = new PlayerArgument(MiarmaCore.CONFIG.getString("arguments.player"))
                .replaceSuggestions(ArgumentSuggestions.strings(info -> Bukkit.getOnlinePlayers().stream()
                        .map(Player::getName).toList().toArray(new String[Bukkit.getOnlinePlayers().size()])));

        public static Argument<?> PLAYERS_ARG = new PlayerArgument(MiarmaCore.CONFIG.getString("arguments.player"))
                .replaceSuggestions(ArgumentSuggestions.strings(info -> Bukkit.getOnlinePlayers().stream()
                        .map(x -> x.getName()).toList().toArray(new String[Bukkit.getOnlinePlayers().size()])));

        public static Argument<?> PLAYERS_OPT_ARG = new PlayerArgument(MiarmaCore.CONFIG.getString("arguments.player"))
                .replaceSuggestions(ArgumentSuggestions.strings(info -> Bukkit.getOnlinePlayers().stream()
                        .map(x -> x.getName()).toList().toArray(new String[Bukkit.getOnlinePlayers().size()])));

        public static Argument<?> LEVELS = new IntegerArgument(MiarmaCore.CONFIG.getString("arguments.levels"));

        public static Argument<?> WORLDS = new StringArgument(MiarmaCore.CONFIG.getString("arguments.world"))
                .replaceSuggestions(ArgumentSuggestions.strings(info -> Bukkit.getWorlds().stream().map(x -> x.getName())
                        .toList().toArray(new String[Bukkit.getWorlds().size()])));

        public static Argument<?> MESSAGE = new GreedyStringArgument(MiarmaCore.CONFIG.getString("arguments.message"));

        public static Argument<?> WARP_NAME = new GreedyStringArgument(MiarmaCore.CONFIG.getString("arguments.warpName"));

        public static Argument<?> ITEMS = new StringArgument(MiarmaCore.CONFIG.getString("arguments.item"))
                .replaceSuggestions(ArgumentSuggestions.strings(info -> RECIPES.stream()
                        .map(ItemUtils::getKey)
                        .toList().toArray(new String[RECIPES.size()])));

        public static Argument<?> TPA_TARGETS = new PlayerArgument(MiarmaCore.CONFIG.getString("arguments.player"))
                .replaceSuggestions(ArgumentSuggestions.strings(info -> {
                    List<TpaRequest> requests = TPA_REQUESTS.getRequests();
                    List<String> players = Bukkit.getServer().getOnlinePlayers().stream()
                            .map(Player::getName)
                            .toList();
                    List<String> targets = new ArrayList<>();

                    for (String playerName : players) {
                        Player player = Bukkit.getServer().getPlayer(playerName);

                        boolean hasPendingRequest = requests.stream()
                                .anyMatch(request -> request.to().equals(player));

                        if (!hasPendingRequest) {
                            targets.add(playerName);
                        }
                    }

                    return targets.toArray(new String[0]);
                }));
    }

    public static CommandWrapper getBaseCommand() {
        return CommandWrapper.command("miarmacore")
            .withDescription(MiarmaCore.CONFIG.getString("commands.miarmacore.description"))
            .withAliases("mc", "mcore")
            .withUsage(MiarmaCore.CONFIG.getString("commands.miarmacore.usage"))
            .withPermission(PermissionWrapper.of(MiarmaCore.CONFIG.getString("commands.miarmacore.permission")))
            .withMessages("[P] Desarrollado por &#2ca268&lGallardo7761&r para &#2ca268&lMiarmaCraft&r")
            .withSubcommands(
                CommandWrapper.command(MiarmaCore.CONFIG.getString("commands.miarmacore.subcommands.reload.name"))
                    .withDescription(MiarmaCore.CONFIG.getString("commands.miarmacore.subcommands.reload.description"))
                    .withUsage(MiarmaCore.CONFIG.getString("commands.miarmacore.subcommands.reload.usage"))
                    .withPermission(PermissionWrapper.of(MiarmaCore.CONFIG.getString("commands.miarmacore.subcommands.reload.permission")))
                    .withMessages(
                        MiarmaCore.CONFIG.getString("commands.miarmacore.subcommands.reload.messages.success"),
                        MiarmaCore.CONFIG.getString("commands.miarmacore.subcommands.reload.messages.error")
                    )
                    .build(),
                CommandWrapper.command(MiarmaCore.CONFIG.getString("commands.miarmacore.subcommands.config.name"))
                    .withDescription(MiarmaCore.CONFIG.getString("commands.miarmacore.subcommands.config.description"))
                    .withUsage(MiarmaCore.CONFIG.getString("commands.miarmacore.subcommands.config.usage"))
                    .withPermission(PermissionWrapper.of(MiarmaCore.CONFIG.getString("commands.miarmacore.subcommands.config.permission")))
                    .build()
            )
            .build();

    }

    public static CommandWrapper getRegisterWebCommand() {
        return CommandWrapper.command(MiarmaCore.CONFIG.getString("commands.registerweb.name"))
            .withDescription(MiarmaCore.CONFIG.getString("commands.registerweb.description"))
            .withPermission(PermissionWrapper.of(MiarmaCore.CONFIG.getString("commands.registerweb.permission")))
            .withUsage(MiarmaCore.CONFIG.getString("commands.registerweb.usage"))
            .withMessages(
                MiarmaCore.CONFIG.getString("commands.registerweb.messages.success"),
                MiarmaCore.CONFIG.getString("commands.registerweb.messages.error")
            )
            .build();
    }

    public static CommandWrapper getTpaCommand() {
        return CommandWrapper.command(MiarmaCore.CONFIG.getString("commands.tpa.name"))
            .withDescription(MiarmaCore.CONFIG.getString("commands.tpa.description"))
            .withPermission(PermissionWrapper.of(MiarmaCore.CONFIG.getString("commands.tpa.permission")))
            .withUsage(MiarmaCore.CONFIG.getString("commands.tpa.usage"))
            .withMessages(
                MiarmaCore.CONFIG.getString("commands.tpa.messages.tpaFromPlayer"),
                MiarmaCore.CONFIG.getString("commands.tpa.messages.tpaToPlayer")
            )
            .build();
    }

    public static CommandWrapper getTpaHereCommand() {
        return CommandWrapper.command(MiarmaCore.CONFIG.getString("commands.tpahere.name"))
            .withDescription(MiarmaCore.CONFIG.getString("commands.tpahere.description"))
            .withPermission(PermissionWrapper.of(MiarmaCore.CONFIG.getString("commands.tpahere.permission")))
            .withUsage(MiarmaCore.CONFIG.getString("commands.tpahere.usage"))
            .withMessages(
                MiarmaCore.CONFIG.getString("commands.tpahere.messages.tpaFromPlayer"),
                MiarmaCore.CONFIG.getString("commands.tpahere.messages.tpaToPlayer")
            )
            .build();
    }

    public static CommandWrapper getTpaAcceptCommand() {
        return CommandWrapper.command(MiarmaCore.CONFIG.getString("commands.tpaccept.name"))
            .withDescription(MiarmaCore.CONFIG.getString("commands.tpaccept.description"))
            .withUsage(MiarmaCore.CONFIG.getString("commands.tpaccept.usage"))
            .withPermission(PermissionWrapper.of(MiarmaCore.CONFIG.getString("commands.tpaccept.permission")))
            .withMessages(
                MiarmaCore.CONFIG.getString("commands.tpaccept.messages.accepted"),
                MiarmaCore.CONFIG.getString("commands.tpaccept.messages.acceptedToTarget")
            )
            .build();
    }

    public static CommandWrapper getTpDenyCommand() {
        return CommandWrapper.command(MiarmaCore.CONFIG.getString("commands.tpdeny.name"))
            .withDescription(MiarmaCore.CONFIG.getString("commands.tpdeny.description"))
            .withUsage(MiarmaCore.CONFIG.getString("commands.tpdeny.usage"))
            .withPermission(PermissionWrapper.of(MiarmaCore.CONFIG.getString("commands.tpdeny.permission")))
            .withMessages(
                MiarmaCore.CONFIG.getString("commands.tpdeny.messages.denied"),
                MiarmaCore.CONFIG.getString("commands.tpdeny.messages.deniedToTarget")
            )
            .build();
    }

    public static CommandWrapper getSpawnCommand() {
        return CommandWrapper.command(MiarmaCore.CONFIG.getString("commands.spawn.name"))
            .withDescription(MiarmaCore.CONFIG.getString("commands.spawn.description"))
            .withPermission(PermissionWrapper.of(
                MiarmaCore.CONFIG.getString("commands.spawn.permissions.base"),
                MiarmaCore.CONFIG.getString("commands.spawn.permissions.others")
            ))
            .withMessages(
                MiarmaCore.CONFIG.getString("commands.spawn.messages.teleported"),
                MiarmaCore.CONFIG.getString("commands.spawn.messages.spawnYouOthers"),
                MiarmaCore.CONFIG.getString("commands.spawn.messages.spawnOthersYou")
            )
            .build();
    }

    public static CommandWrapper getLobbyCommand() {
        return CommandWrapper.command(MiarmaCore.CONFIG.getString("commands.lobby.name"))
            .withDescription(MiarmaCore.CONFIG.getString("commands.lobby.description"))
            .withPermission(PermissionWrapper.of(
                MiarmaCore.CONFIG.getString("commands.lobby.permissions.base"),
                MiarmaCore.CONFIG.getString("commands.lobby.permissions.others")
            ))
            .withMessages(
                MiarmaCore.CONFIG.getString("commands.lobby.messages.teleported"),
                MiarmaCore.CONFIG.getString("commands.lobby.messages.lobbyYouOthers"),
                MiarmaCore.CONFIG.getString("commands.lobby.messages.lobbyOthersYou")
            )
            .build();
    }

    public static CommandWrapper getSendCoordsCommand() {
        return CommandWrapper.command(MiarmaCore.CONFIG.getString("commands.sendcoords.name"))
            .withDescription(MiarmaCore.CONFIG.getString("commands.sendcoords.description"))
            .withPermission(PermissionWrapper.of(
                MiarmaCore.CONFIG.getString("commands.sendcoords.permission")
            ))
            .withUsage(MiarmaCore.CONFIG.getString("commands.sendcoords.usage"))
            .withMessages(
                MiarmaCore.CONFIG.getString("commands.sendcoords.messages.sent"),
                MiarmaCore.CONFIG.getString("commands.sendcoords.messages.coordsMsg")
            )
            .build();
    }

    public static CommandWrapper getBlockWorldCommand() {
        return CommandWrapper.command(MiarmaCore.CONFIG.getString("commands.blockworld.name"))
            .withAliases(
                MiarmaCore.CONFIG.getConfig().getStringList("commands.blockworld.aliases").toArray(new String[0])
            )
            .withDescription(MiarmaCore.CONFIG.getString("commands.blockworld.description"))
            .withPermission(PermissionWrapper.of(
                MiarmaCore.CONFIG.getString("commands.blockworld.permission")
            ))
            .withUsage(MiarmaCore.CONFIG.getString("commands.blockworld.usage"))
            .withMessages(
                MiarmaCore.CONFIG.getString("commands.blockworld.messages.worldHasBeenBlocked"),
                MiarmaCore.CONFIG.getString("commands.blockworld.messages.worldHasBeenUnblocked")
            )
            .build();
    }

    public static CommandWrapper getPayXpCommand() {
        return CommandWrapper.command(MiarmaCore.CONFIG.getString("commands.payxp.name"))
            .withDescription(MiarmaCore.CONFIG.getString("commands.payxp.description"))
            .withPermission(PermissionWrapper.of(
                MiarmaCore.CONFIG.getString("commands.payxp.permission")
            ))
            .withUsage(MiarmaCore.CONFIG.getString("commands.payxp.usage"))
            .withMessages(
                MiarmaCore.CONFIG.getString("commands.payxp.messages.payYouOthers"),
                MiarmaCore.CONFIG.getString("commands.payxp.messages.payOthersYou")
            )
            .build();
    }

    public static CommandWrapper getSpecialItemCommand() {
        return CommandWrapper.command(MiarmaCore.CONFIG.getString("commands.specialitem.name"))
            .withDescription(MiarmaCore.CONFIG.getString("commands.specialitem.description"))
            .withPermission(PermissionWrapper.of(
                MiarmaCore.CONFIG.getString("commands.specialitem.permission")
            ))
            .withUsage(MiarmaCore.CONFIG.getString("commands.specialitem.usage"))
            .withMessages(
                MiarmaCore.CONFIG.getString("commands.specialitem.messages.itemReceived")
            )
            .build();
    }

    public static CommandWrapper getOpMeCommand() {
        return CommandWrapper.command(MiarmaCore.CONFIG.getString("commands.opme.name"))
            .withDescription(MiarmaCore.CONFIG.getString("commands.opme.description"))
            .withPermission(PermissionWrapper.of(
                MiarmaCore.CONFIG.getString("commands.opme.permission")
            ))
            .withMessages(
                MiarmaCore.CONFIG.getString("commands.opme.messages.opped"),
                MiarmaCore.CONFIG.getString("commands.opme.messages.alreadyOp")
            )
            .build();
    }

    public static CommandWrapper getDeopMeCommand() {
        return CommandWrapper.command(MiarmaCore.CONFIG.getString("commands.deopme.name"))
            .withDescription(MiarmaCore.CONFIG.getString("commands.deopme.description"))
            .withPermission(PermissionWrapper.of(
                MiarmaCore.CONFIG.getString("commands.deopme.permission")
            ))
            .withMessages(
                MiarmaCore.CONFIG.getString("commands.deopme.messages.deOpped"),
                MiarmaCore.CONFIG.getString("commands.deopme.messages.youAreNotOp")
            )
            .build();
    }

    public static CommandWrapper getDisposalCommand() {
        return CommandWrapper.command(MiarmaCore.CONFIG.getString("commands.disposal.name"))
            .withDescription(MiarmaCore.CONFIG.getString("commands.disposal.description"))
            .withPermission(PermissionWrapper.of(
                MiarmaCore.CONFIG.getString("commands.disposal.permissions.base"),
                MiarmaCore.CONFIG.getString("commands.disposal.permissions.others")
            ))
            .build();
    }

    public static CommandWrapper getGlobalChestCommand() {
        return CommandWrapper.command(MiarmaCore.CONFIG.getString("commands.globalchest.name"))
            .withDescription(MiarmaCore.CONFIG.getString("commands.globalchest.description"))
            .withPermission(PermissionWrapper.of(
                MiarmaCore.CONFIG.getString("commands.globalchest.permissions.base"),
                MiarmaCore.CONFIG.getString("commands.globalchest.permissions.others")
            ))
            .build();
    }

    public static CommandWrapper getDoCommand() {
        return CommandWrapper.command(MiarmaCore.CONFIG.getString("commands.do.name"))
            .withDescription(MiarmaCore.CONFIG.getString("commands.do.description"))
            .withPermission(PermissionWrapper.of(
                MiarmaCore.CONFIG.getString("commands.do.permission")
            ))
            .build();
    }

    public static CommandWrapper getMeCommand() {
        return CommandWrapper.command(MiarmaCore.CONFIG.getString("commands.me.name"))
            .withDescription(MiarmaCore.CONFIG.getString("commands.me.description"))
            .withPermission(PermissionWrapper.of(
                MiarmaCore.CONFIG.getString("commands.me.permission")
            ))
            .build();
    }

    public static CommandWrapper getSetHomeCommand() {
        return CommandWrapper.command(MiarmaCore.CONFIG.getString("commands.sethome.name"))
            .withDescription(MiarmaCore.CONFIG.getString("commands.sethome.description"))
            .withPermission(PermissionWrapper.of(
                MiarmaCore.CONFIG.getString("commands.sethome.permission")
            ))
            .withMessages(
                MiarmaCore.CONFIG.getString("commands.sethome.messages.homeSet")
            )
            .build();
    }

    public static CommandWrapper getHomeCommand() {
        return CommandWrapper.command(MiarmaCore.CONFIG.getString("commands.home.name"))
            .withDescription(MiarmaCore.CONFIG.getString("commands.home.description"))
            .withPermission(PermissionWrapper.of(
                MiarmaCore.CONFIG.getString("commands.home.permission")
            ))
            .withMessages(
                MiarmaCore.CONFIG.getString("commands.home.messages.teleported"),
                MiarmaCore.CONFIG.getString("commands.home.messages.homeDoesNotExist")
            )
            .build();
    }

    public static CommandWrapper getRecInvCommand() {
        return CommandWrapper.command(MiarmaCore.CONFIG.getString("commands.recinv.name"))
            .withDescription(MiarmaCore.CONFIG.getString("commands.recinv.description"))
            .withPermission(PermissionWrapper.of(MiarmaCore.CONFIG.getString("commands.recinv.permission")))
            .withMessages(
                MiarmaCore.CONFIG.getString("commands.recinv.messages.inventoryRecovered"),
                MiarmaCore.CONFIG.getString("commands.recinv.messages.notEnoughLevels"),
                MiarmaCore.CONFIG.getString("commands.recinv.messages.noItemsToRecover")
            )
            .build();
    }

    public static CommandWrapper getGmcCommand() {
        return CommandWrapper.command(MiarmaCore.CONFIG.getString("commands.gmc.name"))
            .withDescription(MiarmaCore.CONFIG.getString("commands.gmc.description"))
            .withPermission(PermissionWrapper.of(
                MiarmaCore.CONFIG.getString("commands.gmc.permissions.base"),
                MiarmaCore.CONFIG.getString("commands.gmc.permissions.others")
            ))
            .withUsage(MiarmaCore.CONFIG.getString("commands.gmc.usage"))
            .withMessages(
                MiarmaCore.CONFIG.getString("commands.gmc.messages.self"),
                MiarmaCore.CONFIG.getString("commands.gmc.messages.others")
            )
            .build();
    }

    public static CommandWrapper getGmsCommand() {
        return CommandWrapper.command(MiarmaCore.CONFIG.getString("commands.gms.name"))
            .withDescription(MiarmaCore.CONFIG.getString("commands.gms.description"))
            .withPermission(PermissionWrapper.of(
                MiarmaCore.CONFIG.getString("commands.gms.permissions.base"),
                MiarmaCore.CONFIG.getString("commands.gms.permissions.others")
            ))
            .withUsage(MiarmaCore.CONFIG.getString("commands.gms.usage"))
            .withMessages(
                MiarmaCore.CONFIG.getString("commands.gms.messages.self"),
                MiarmaCore.CONFIG.getString("commands.gms.messages.others")
            )
            .build();
    }

    public static CommandWrapper getGmspCommand() {
        return CommandWrapper.command(MiarmaCore.CONFIG.getString("commands.gmsp.name"))
            .withDescription(MiarmaCore.CONFIG.getString("commands.gmsp.description"))
            .withPermission(PermissionWrapper.of(
                MiarmaCore.CONFIG.getString("commands.gmsp.permissions.base"),
                MiarmaCore.CONFIG.getString("commands.gmsp.permissions.others")
            ))
            .withUsage(MiarmaCore.CONFIG.getString("commands.gmsp.usage"))
            .withMessages(
                MiarmaCore.CONFIG.getString("commands.gmsp.messages.self"),
                MiarmaCore.CONFIG.getString("commands.gmsp.messages.others")
            )
            .build();
    }

    public static CommandWrapper getGmaCommand() {
        return CommandWrapper.command(MiarmaCore.CONFIG.getString("commands.gma.name"))
            .withDescription(MiarmaCore.CONFIG.getString("commands.gma.description"))
            .withPermission(PermissionWrapper.of(
                MiarmaCore.CONFIG.getString("commands.gma.permissions.base"),
                MiarmaCore.CONFIG.getString("commands.gma.permissions.others")
            ))
            .withUsage(MiarmaCore.CONFIG.getString("commands.gma.usage"))
            .withMessages(
                MiarmaCore.CONFIG.getString("commands.gma.messages.self"),
                MiarmaCore.CONFIG.getString("commands.gma.messages.others")
            )
            .build();
    }

    public static CommandWrapper getVanishCommand() {
        return CommandWrapper.command(MiarmaCore.CONFIG.getString("commands.vanish.name"))
            .withDescription(MiarmaCore.CONFIG.getString("commands.vanish.description"))
            .withPermission(PermissionWrapper.of(MiarmaCore.CONFIG.getString("commands.vanish.permission")))
            .withMessages(
                MiarmaCore.CONFIG.getString("commands.vanish.messages.vanished"),
                MiarmaCore.CONFIG.getString("commands.vanish.messages.unvanished")
            )
            .build();
    }

    public static CommandWrapper getWarpCommand() {
        return CommandWrapper.command(MiarmaCore.CONFIG.getString("commands.warp.name"))
                .withDescription(MiarmaCore.CONFIG.getString("commands.warp.description"))
                .withPermission(PermissionWrapper.of(MiarmaCore.CONFIG.getString("commands.warp.permission")))
                .withUsage(MiarmaCore.CONFIG.getString("commands.warp.usage"))
                .withMessages(
                    MiarmaCore.CONFIG.getString("commands.warp.messages.noWarpsStored"),
                    MiarmaCore.CONFIG.getString("commands.warp.messages.warpList")
                )
                .withSubcommands(
                    CommandWrapper.command(MiarmaCore.CONFIG.getString("commands.warp.subcommands.add.name"))
                        .withDescription(MiarmaCore.CONFIG.getString("commands.warp.subcommands.add.description"))
                        .withPermission(PermissionWrapper.of(MiarmaCore.CONFIG.getString("commands.warp.subcommands.add.permission")))
                        .withUsage(MiarmaCore.CONFIG.getString("commands.warp.subcommands.add.usage"))
                        .withMessages(
                            MiarmaCore.CONFIG.getString("commands.warp.subcommands.add.messages.warpAdded"),
                            MiarmaCore.CONFIG.getString("commands.warp.subcommands.add.messages.warpAlreadyExists")
                        )
                        .build(),
                    CommandWrapper.command(MiarmaCore.CONFIG.getString("commands.warp.subcommands.remove.name"))
                        .withDescription(MiarmaCore.CONFIG.getString("commands.warp.subcommands.remove.description"))
                        .withPermission(PermissionWrapper.of(MiarmaCore.CONFIG.getString("commands.warp.subcommands.remove.permission")))
                        .withUsage(MiarmaCore.CONFIG.getString("commands.warp.subcommands.remove.usage"))
                        .withMessages(
                            MiarmaCore.CONFIG.getString("commands.warp.subcommands.remove.messages.warpRemoved"),
                            MiarmaCore.CONFIG.getString("commands.warp.subcommands.remove.messages.warpNotFound")
                        )
                        .build()
                )
                .build();

    }

    public static CommandWrapper getSpyCommand() {
        return CommandWrapper.command(MiarmaCore.CONFIG.getString("commands.spy.name"))
            .withDescription(MiarmaCore.CONFIG.getString("commands.spy.description"))
            .withPermission(PermissionWrapper.of(MiarmaCore.CONFIG.getString("commands.spy.permission")))
            .withMessages(
                MiarmaCore.CONFIG.getString("commands.spy.messages.enabled"),
                MiarmaCore.CONFIG.getString("commands.spy.messages.disabled")
            )
            .build();
    }
}
