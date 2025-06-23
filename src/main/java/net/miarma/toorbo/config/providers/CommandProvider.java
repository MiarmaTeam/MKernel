package net.miarma.toorbo.config.providers;

import net.miarma.toorbo.Toorbo;
import net.miarma.toorbo.common.minecraft.Warp;
import net.miarma.toorbo.config.CommandWrapper;
import net.miarma.toorbo.config.PermissionWrapper;
import net.miarma.toorbo.util.ItemUtil;
import net.miarma.toorbo.util.PlayerUtil;
import dev.jorel.commandapi.arguments.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

import static net.miarma.toorbo.util.Constants.RECIPES;

public class CommandProvider {
    public static class Arguments {
        public static final Argument<?> PASSWORD_ARG = new GreedyStringArgument(Toorbo.CONFIG.getString("arguments.password"));

        public static Argument<?> PLAYER_ARG = new PlayerArgument(Toorbo.CONFIG.getString("arguments.player"))
                .replaceSuggestions(ArgumentSuggestions.strings(info -> Bukkit.getOnlinePlayers().stream()
                        .map(Player::getName).toList().toArray(new String[Bukkit.getOnlinePlayers().size()])));

        public static Argument<?> PLAYERS_OPT_ARG = new PlayerArgument(Toorbo.CONFIG.getString("arguments.player"))
                .replaceSuggestions(ArgumentSuggestions.strings(info -> Bukkit.getOnlinePlayers().stream()
                        .map(x -> x.getName()).toList().toArray(new String[Bukkit.getOnlinePlayers().size()])));

        public static Argument<?> LEVELS = new IntegerArgument(Toorbo.CONFIG.getString("arguments.levels"));

        public static Argument<?> WORLDS = new StringArgument(Toorbo.CONFIG.getString("arguments.world"))
                .replaceSuggestions(ArgumentSuggestions.strings(info -> Bukkit.getWorlds().stream().map(x -> x.getName())
                        .toList().toArray(new String[Bukkit.getWorlds().size()])));

        public static Argument<?> MESSAGE = new GreedyStringArgument(Toorbo.CONFIG.getString("arguments.message"));

        public static Argument<?> WARP_NAME = new GreedyStringArgument(Toorbo.CONFIG.getString("arguments.warpName"));

        public static Argument<?> WARPS = new StringArgument(Toorbo.CONFIG.getString("arguments.warp"))
                .replaceSuggestions(ArgumentSuggestions.strings(info -> {
                    Player p = (Player) info.sender();
                    List<Warp> warps = PlayerUtil.getWarps(p);
                    return warps.stream().map(Warp::getAlias).toList().toArray(new String[warps.size()]);
                }));

        public static Argument<?> ITEMS = new StringArgument(Toorbo.CONFIG.getString("arguments.item"))
                .replaceSuggestions(ArgumentSuggestions.strings(info -> RECIPES.stream()
                        .map(ItemUtil::getKey)
                        .toList().toArray(new String[RECIPES.size()])));

    }

    public static CommandWrapper getBaseCommand() {
        return CommandWrapper.command("miarmacore")
            .withDescription(Toorbo.CONFIG.getString("commands.miarmacore.description"))
            .withAliases("mc", "mcore")
            .withUsage(Toorbo.CONFIG.getString("commands.miarmacore.usage"))
            .withPermission(PermissionWrapper.of(Toorbo.CONFIG.getString("commands.miarmacore.permission")))
            .withMessages("[P] Desarrollado por &#2ca268&lGallardo7761&r para &#2ca268&lMiarmaCraft&r")
            .withSubcommands(
                CommandWrapper.command(Toorbo.CONFIG.getString("commands.miarmacore.subcommands.reload.name"))
                    .withDescription(Toorbo.CONFIG.getString("commands.miarmacore.subcommands.reload.description"))
                    .withUsage(Toorbo.CONFIG.getString("commands.miarmacore.subcommands.reload.usage"))
                    .withPermission(PermissionWrapper.of(Toorbo.CONFIG.getString("commands.miarmacore.subcommands.reload.permission")))
                    .withMessages(
                        Toorbo.CONFIG.getString("commands.miarmacore.subcommands.reload.messages.success"),
                        Toorbo.CONFIG.getString("commands.miarmacore.subcommands.reload.messages.error")
                    )
                    .build(),
                CommandWrapper.command(Toorbo.CONFIG.getString("commands.miarmacore.subcommands.config.name"))
                    .withDescription(Toorbo.CONFIG.getString("commands.miarmacore.subcommands.config.description"))
                    .withUsage(Toorbo.CONFIG.getString("commands.miarmacore.subcommands.config.usage"))
                    .withPermission(PermissionWrapper.of(Toorbo.CONFIG.getString("commands.miarmacore.subcommands.config.permission")))
                    .build()
            )
            .build();

    }

    public static CommandWrapper getRegisterWebCommand() {
        return CommandWrapper.command(Toorbo.CONFIG.getString("commands.registerweb.name"))
            .withDescription(Toorbo.CONFIG.getString("commands.registerweb.description"))
            .withPermission(PermissionWrapper.of(Toorbo.CONFIG.getString("commands.registerweb.permission")))
            .withUsage(Toorbo.CONFIG.getString("commands.registerweb.usage"))
            .withMessages(
                Toorbo.CONFIG.getString("commands.registerweb.messages.success"),
                Toorbo.CONFIG.getString("commands.registerweb.messages.error")
            )
            .build();
    }

    public static CommandWrapper getTpaCommand() {
        return CommandWrapper.command(Toorbo.CONFIG.getString("commands.tpa.name"))
            .withDescription(Toorbo.CONFIG.getString("commands.tpa.description"))
            .withPermission(PermissionWrapper.of(Toorbo.CONFIG.getString("commands.tpa.permission")))
            .withUsage(Toorbo.CONFIG.getString("commands.tpa.usage"))
            .withMessages(
                Toorbo.CONFIG.getString("commands.tpa.messages.tpaFromPlayer"),
                Toorbo.CONFIG.getString("commands.tpa.messages.tpaToPlayer")
            )
            .build();
    }

    public static CommandWrapper getTpaHereCommand() {
        return CommandWrapper.command(Toorbo.CONFIG.getString("commands.tpahere.name"))
            .withDescription(Toorbo.CONFIG.getString("commands.tpahere.description"))
            .withPermission(PermissionWrapper.of(Toorbo.CONFIG.getString("commands.tpahere.permission")))
            .withUsage(Toorbo.CONFIG.getString("commands.tpahere.usage"))
            .withMessages(
                Toorbo.CONFIG.getString("commands.tpahere.messages.tpaFromPlayer"),
                Toorbo.CONFIG.getString("commands.tpahere.messages.tpaToPlayer")
            )
            .build();
    }

    public static CommandWrapper getTpaAcceptCommand() {
        return CommandWrapper.command(Toorbo.CONFIG.getString("commands.tpaccept.name"))
            .withDescription(Toorbo.CONFIG.getString("commands.tpaccept.description"))
            .withUsage(Toorbo.CONFIG.getString("commands.tpaccept.usage"))
            .withPermission(PermissionWrapper.of(Toorbo.CONFIG.getString("commands.tpaccept.permission")))
            .withMessages(
                Toorbo.CONFIG.getString("commands.tpaccept.messages.accepted"),
                Toorbo.CONFIG.getString("commands.tpaccept.messages.acceptedToTarget")
            )
            .build();
    }

    public static CommandWrapper getTpDenyCommand() {
        return CommandWrapper.command(Toorbo.CONFIG.getString("commands.tpdeny.name"))
            .withDescription(Toorbo.CONFIG.getString("commands.tpdeny.description"))
            .withUsage(Toorbo.CONFIG.getString("commands.tpdeny.usage"))
            .withPermission(PermissionWrapper.of(Toorbo.CONFIG.getString("commands.tpdeny.permission")))
            .withMessages(
                Toorbo.CONFIG.getString("commands.tpdeny.messages.denied"),
                Toorbo.CONFIG.getString("commands.tpdeny.messages.deniedToTarget")
            )
            .build();
    }

    public static CommandWrapper getSpawnCommand() {
        return CommandWrapper.command(Toorbo.CONFIG.getString("commands.spawn.name"))
            .withDescription(Toorbo.CONFIG.getString("commands.spawn.description"))
            .withPermission(PermissionWrapper.of(
                Toorbo.CONFIG.getString("commands.spawn.permissions.base"),
                Toorbo.CONFIG.getString("commands.spawn.permissions.others")
            ))
            .withMessages(
                Toorbo.CONFIG.getString("commands.spawn.messages.teleported"),
                Toorbo.CONFIG.getString("commands.spawn.messages.spawnYouOthers"),
                Toorbo.CONFIG.getString("commands.spawn.messages.spawnOthersYou")
            )
            .build();
    }

    public static CommandWrapper getLobbyCommand() {
        return CommandWrapper.command(Toorbo.CONFIG.getString("commands.lobby.name"))
            .withDescription(Toorbo.CONFIG.getString("commands.lobby.description"))
            .withPermission(PermissionWrapper.of(
                Toorbo.CONFIG.getString("commands.lobby.permissions.base"),
                Toorbo.CONFIG.getString("commands.lobby.permissions.others")
            ))
            .withMessages(
                Toorbo.CONFIG.getString("commands.lobby.messages.teleported"),
                Toorbo.CONFIG.getString("commands.lobby.messages.lobbyYouOthers"),
                Toorbo.CONFIG.getString("commands.lobby.messages.lobbyOthersYou")
            )
            .build();
    }

    public static CommandWrapper getSendCoordsCommand() {
        return CommandWrapper.command(Toorbo.CONFIG.getString("commands.sendcoords.name"))
            .withDescription(Toorbo.CONFIG.getString("commands.sendcoords.description"))
            .withPermission(PermissionWrapper.of(
                Toorbo.CONFIG.getString("commands.sendcoords.permission")
            ))
            .withUsage(Toorbo.CONFIG.getString("commands.sendcoords.usage"))
            .withMessages(
                Toorbo.CONFIG.getString("commands.sendcoords.messages.sent"),
                Toorbo.CONFIG.getString("commands.sendcoords.messages.coordsMsg")
            )
            .build();
    }

    public static CommandWrapper getBlockWorldCommand() {
        return CommandWrapper.command(Toorbo.CONFIG.getString("commands.blockworld.name"))
            .withAliases(
                Toorbo.CONFIG.getConfig().getStringList("commands.blockworld.aliases").toArray(new String[0])
            )
            .withDescription(Toorbo.CONFIG.getString("commands.blockworld.description"))
            .withPermission(PermissionWrapper.of(
                Toorbo.CONFIG.getString("commands.blockworld.permission")
            ))
            .withUsage(Toorbo.CONFIG.getString("commands.blockworld.usage"))
            .withMessages(
                Toorbo.CONFIG.getString("commands.blockworld.messages.worldHasBeenBlocked"),
                Toorbo.CONFIG.getString("commands.blockworld.messages.worldHasBeenUnblocked")
            )
            .build();
    }

    public static CommandWrapper getPayXpCommand() {
        return CommandWrapper.command(Toorbo.CONFIG.getString("commands.payxp.name"))
            .withDescription(Toorbo.CONFIG.getString("commands.payxp.description"))
            .withPermission(PermissionWrapper.of(
                Toorbo.CONFIG.getString("commands.payxp.permission")
            ))
            .withUsage(Toorbo.CONFIG.getString("commands.payxp.usage"))
            .withMessages(
                Toorbo.CONFIG.getString("commands.payxp.messages.payYouOthers"),
                Toorbo.CONFIG.getString("commands.payxp.messages.payOthersYou")
            )
            .build();
    }

    public static CommandWrapper getSpecialItemCommand() {
        return CommandWrapper.command(Toorbo.CONFIG.getString("commands.specialitem.name"))
            .withDescription(Toorbo.CONFIG.getString("commands.specialitem.description"))
            .withPermission(PermissionWrapper.of(
                Toorbo.CONFIG.getString("commands.specialitem.permission")
            ))
            .withUsage(Toorbo.CONFIG.getString("commands.specialitem.usage"))
            .withMessages(
                Toorbo.CONFIG.getString("commands.specialitem.messages.itemReceived")
            )
            .build();
    }

    public static CommandWrapper getOpMeCommand() {
        return CommandWrapper.command(Toorbo.CONFIG.getString("commands.opme.name"))
            .withDescription(Toorbo.CONFIG.getString("commands.opme.description"))
            .withPermission(PermissionWrapper.of(
                Toorbo.CONFIG.getString("commands.opme.permission")
            ))
            .withMessages(
                Toorbo.CONFIG.getString("commands.opme.messages.opped"),
                Toorbo.CONFIG.getString("commands.opme.messages.alreadyOp")
            )
            .build();
    }

    public static CommandWrapper getDeopMeCommand() {
        return CommandWrapper.command(Toorbo.CONFIG.getString("commands.deopme.name"))
            .withDescription(Toorbo.CONFIG.getString("commands.deopme.description"))
            .withPermission(PermissionWrapper.of(
                Toorbo.CONFIG.getString("commands.deopme.permission")
            ))
            .withMessages(
                Toorbo.CONFIG.getString("commands.deopme.messages.deOpped"),
                Toorbo.CONFIG.getString("commands.deopme.messages.youAreNotOp")
            )
            .build();
    }

    public static CommandWrapper getDisposalCommand() {
        return CommandWrapper.command(Toorbo.CONFIG.getString("commands.disposal.name"))
            .withDescription(Toorbo.CONFIG.getString("commands.disposal.description"))
            .withPermission(PermissionWrapper.of(
                Toorbo.CONFIG.getString("commands.disposal.permissions.base"),
                Toorbo.CONFIG.getString("commands.disposal.permissions.others")
            ))
            .build();
    }

    public static CommandWrapper getGlobalChestCommand() {
        return CommandWrapper.command(Toorbo.CONFIG.getString("commands.globalchest.name"))
            .withDescription(Toorbo.CONFIG.getString("commands.globalchest.description"))
            .withAliases(
                Toorbo.CONFIG.getConfig().getStringList("commands.globalchest.aliases").toArray(new String[0])
            )
            .withPermission(PermissionWrapper.of(
                Toorbo.CONFIG.getString("commands.globalchest.permissions.base"),
                Toorbo.CONFIG.getString("commands.globalchest.permissions.others")
            ))
            .build();
    }

    public static CommandWrapper getDoCommand() {
        return CommandWrapper.command(Toorbo.CONFIG.getString("commands.do.name"))
            .withDescription(Toorbo.CONFIG.getString("commands.do.description"))
            .withPermission(PermissionWrapper.of(
                Toorbo.CONFIG.getString("commands.do.permission")
            ))
            .build();
    }

    public static CommandWrapper getMeCommand() {
        return CommandWrapper.command(Toorbo.CONFIG.getString("commands.me.name"))
            .withDescription(Toorbo.CONFIG.getString("commands.me.description"))
            .withPermission(PermissionWrapper.of(
                Toorbo.CONFIG.getString("commands.me.permission")
            ))
            .build();
    }

    public static CommandWrapper getSetHomeCommand() {
        return CommandWrapper.command(Toorbo.CONFIG.getString("commands.sethome.name"))
            .withDescription(Toorbo.CONFIG.getString("commands.sethome.description"))
            .withPermission(PermissionWrapper.of(
                Toorbo.CONFIG.getString("commands.sethome.permission")
            ))
            .withMessages(
                Toorbo.CONFIG.getString("commands.sethome.messages.homeSet")
            )
            .build();
    }

    public static CommandWrapper getHomeCommand() {
        return CommandWrapper.command(Toorbo.CONFIG.getString("commands.home.name"))
            .withDescription(Toorbo.CONFIG.getString("commands.home.description"))
            .withPermission(PermissionWrapper.of(
                Toorbo.CONFIG.getString("commands.home.permission")
            ))
            .withMessages(
                Toorbo.CONFIG.getString("commands.home.messages.teleported"),
                Toorbo.CONFIG.getString("commands.home.messages.homeDoesNotExist")
            )
            .build();
    }

    public static CommandWrapper getRecInvCommand() {
        return CommandWrapper.command(Toorbo.CONFIG.getString("commands.recinv.name"))
            .withDescription(Toorbo.CONFIG.getString("commands.recinv.description"))
            .withPermission(PermissionWrapper.of(Toorbo.CONFIG.getString("commands.recinv.permission")))
            .withMessages(
                Toorbo.CONFIG.getString("commands.recinv.messages.inventoryRecovered"),
                Toorbo.CONFIG.getString("commands.recinv.messages.notEnoughLevels"),
                Toorbo.CONFIG.getString("commands.recinv.messages.noItemsToRecover")
            )
            .build();
    }

    public static CommandWrapper getGmcCommand() {
        return CommandWrapper.command(Toorbo.CONFIG.getString("commands.gmc.name"))
            .withDescription(Toorbo.CONFIG.getString("commands.gmc.description"))
            .withPermission(PermissionWrapper.of(
                Toorbo.CONFIG.getString("commands.gmc.permissions.base"),
                Toorbo.CONFIG.getString("commands.gmc.permissions.others")
            ))
            .withUsage(Toorbo.CONFIG.getString("commands.gmc.usage"))
            .withMessages(
                Toorbo.CONFIG.getString("commands.gmc.messages.self"),
                Toorbo.CONFIG.getString("commands.gmc.messages.others")
            )
            .build();
    }

    public static CommandWrapper getGmsCommand() {
        return CommandWrapper.command(Toorbo.CONFIG.getString("commands.gms.name"))
            .withDescription(Toorbo.CONFIG.getString("commands.gms.description"))
            .withPermission(PermissionWrapper.of(
                Toorbo.CONFIG.getString("commands.gms.permissions.base"),
                Toorbo.CONFIG.getString("commands.gms.permissions.others")
            ))
            .withUsage(Toorbo.CONFIG.getString("commands.gms.usage"))
            .withMessages(
                Toorbo.CONFIG.getString("commands.gms.messages.self"),
                Toorbo.CONFIG.getString("commands.gms.messages.others")
            )
            .build();
    }

    public static CommandWrapper getGmspCommand() {
        return CommandWrapper.command(Toorbo.CONFIG.getString("commands.gmsp.name"))
            .withDescription(Toorbo.CONFIG.getString("commands.gmsp.description"))
            .withPermission(PermissionWrapper.of(
                Toorbo.CONFIG.getString("commands.gmsp.permissions.base"),
                Toorbo.CONFIG.getString("commands.gmsp.permissions.others")
            ))
            .withUsage(Toorbo.CONFIG.getString("commands.gmsp.usage"))
            .withMessages(
                Toorbo.CONFIG.getString("commands.gmsp.messages.self"),
                Toorbo.CONFIG.getString("commands.gmsp.messages.others")
            )
            .build();
    }

    public static CommandWrapper getGmaCommand() {
        return CommandWrapper.command(Toorbo.CONFIG.getString("commands.gma.name"))
            .withDescription(Toorbo.CONFIG.getString("commands.gma.description"))
            .withPermission(PermissionWrapper.of(
                Toorbo.CONFIG.getString("commands.gma.permissions.base"),
                Toorbo.CONFIG.getString("commands.gma.permissions.others")
            ))
            .withUsage(Toorbo.CONFIG.getString("commands.gma.usage"))
            .withMessages(
                Toorbo.CONFIG.getString("commands.gma.messages.self"),
                Toorbo.CONFIG.getString("commands.gma.messages.others")
            )
            .build();
    }

    public static CommandWrapper getVanishCommand() {
        return CommandWrapper.command(Toorbo.CONFIG.getString("commands.vanish.name"))
            .withDescription(Toorbo.CONFIG.getString("commands.vanish.description"))
            .withPermission(PermissionWrapper.of(Toorbo.CONFIG.getString("commands.vanish.permission")))
            .withMessages(
                Toorbo.CONFIG.getString("commands.vanish.messages.vanished"),
                Toorbo.CONFIG.getString("commands.vanish.messages.unvanished")
            )
            .build();
    }

    public static CommandWrapper getWarpCommand() {
        return CommandWrapper.command(Toorbo.CONFIG.getString("commands.warp.name"))
                .withDescription(Toorbo.CONFIG.getString("commands.warp.description"))
                .withPermission(PermissionWrapper.of(Toorbo.CONFIG.getString("commands.warp.permission")))
                .withUsage(Toorbo.CONFIG.getString("commands.warp.usage"))
                .withMessages(
                    Toorbo.CONFIG.getString("commands.warp.messages.noWarpsStored"),
                    Toorbo.CONFIG.getString("commands.warp.messages.warpList")
                )
                .withSubcommands(
                    CommandWrapper.command(Toorbo.CONFIG.getString("commands.warp.subcommands.add.name"))
                        .withDescription(Toorbo.CONFIG.getString("commands.warp.subcommands.add.description"))
                        .withPermission(PermissionWrapper.of(Toorbo.CONFIG.getString("commands.warp.subcommands.add.permission")))
                        .withUsage(Toorbo.CONFIG.getString("commands.warp.subcommands.add.usage"))
                        .withMessages(
                            Toorbo.CONFIG.getString("commands.warp.subcommands.add.messages.warpAdded"),
                            Toorbo.CONFIG.getString("commands.warp.subcommands.add.messages.warpAlreadyExists")
                        )
                        .build(),
                    CommandWrapper.command(Toorbo.CONFIG.getString("commands.warp.subcommands.remove.name"))
                        .withDescription(Toorbo.CONFIG.getString("commands.warp.subcommands.remove.description"))
                        .withPermission(PermissionWrapper.of(Toorbo.CONFIG.getString("commands.warp.subcommands.remove.permission")))
                        .withUsage(Toorbo.CONFIG.getString("commands.warp.subcommands.remove.usage"))
                        .withMessages(
                            Toorbo.CONFIG.getString("commands.warp.subcommands.remove.messages.warpRemoved"),
                            Toorbo.CONFIG.getString("commands.warp.subcommands.remove.messages.warpNotFound")
                        )
                        .build()
                )
                .build();

    }

    public static CommandWrapper getSpyCommand() {
        return CommandWrapper.command(Toorbo.CONFIG.getString("commands.spy.name"))
            .withDescription(Toorbo.CONFIG.getString("commands.spy.description"))
            .withPermission(PermissionWrapper.of(Toorbo.CONFIG.getString("commands.spy.permission")))
            .withMessages(
                Toorbo.CONFIG.getString("commands.spy.messages.enabled"),
                Toorbo.CONFIG.getString("commands.spy.messages.disabled")
            )
            .build();
    }

    public static CommandWrapper getFreezeCommand() {
        return CommandWrapper.command(Toorbo.CONFIG.getString("commands.freeze.name"))
            .withDescription(Toorbo.CONFIG.getString("commands.freeze.description"))
            .withUsage(Toorbo.CONFIG.getString("commands.freeze.usage"))
            .withPermission(PermissionWrapper.of(Toorbo.CONFIG.getString("commands.freeze.permission")))
            .withMessages(
                Toorbo.CONFIG.getString("commands.freeze.messages.frozen"),
                Toorbo.CONFIG.getString("commands.freeze.messages.unfrozen"),
                Toorbo.CONFIG.getString("commands.freeze.messages.beenFrozen"),
                Toorbo.CONFIG.getString("commands.freeze.messages.beenUnfrozen")
            )
            .build();
    }

    public static CommandWrapper getHealCommand() {
        return CommandWrapper.command(Toorbo.CONFIG.getString("commands.heal.name"))
            .withDescription(Toorbo.CONFIG.getString("commands.heal.description"))
            .withUsage(Toorbo.CONFIG.getString("commands.heal.usage"))
            .withPermission(PermissionWrapper.of(
                Toorbo.CONFIG.getString("commands.heal.permissions.base"),
                Toorbo.CONFIG.getString("commands.heal.permissions.others")
            ))
            .withMessages(
                Toorbo.CONFIG.getString("commands.heal.messages.healedSelf"),
                Toorbo.CONFIG.getString("commands.heal.messages.healedPlayer"),
                Toorbo.CONFIG.getString("commands.heal.messages.beenHealed")
            )
            .build();
    }

    public static CommandWrapper getLaunchCommand() {
        return CommandWrapper.command(Toorbo.CONFIG.getString("commands.launch.name"))
            .withDescription(Toorbo.CONFIG.getString("commands.launch.description"))
            .withUsage(Toorbo.CONFIG.getString("commands.launch.usage"))
            .withPermission(PermissionWrapper.of(
                Toorbo.CONFIG.getString("commands.launch.permission")
            ))
            .withMessages(
                Toorbo.CONFIG.getString("commands.launch.messages.launched")
            )
            .build();
    }

    public static CommandWrapper getSunCommand() {
        return CommandWrapper.command(Toorbo.CONFIG.getString("commands.sun.name"))
            .withDescription(Toorbo.CONFIG.getString("commands.sun.description"))
            .withPermission(PermissionWrapper.of(
                Toorbo.CONFIG.getString("commands.sun.permission")
            ))
            .withMessages(
                Toorbo.CONFIG.getString("commands.sun.messages.sunSet")
            )
            .build();
    }

    public static CommandWrapper getRainCommand() {
        return CommandWrapper.command(Toorbo.CONFIG.getString("commands.rain.name"))
            .withDescription(Toorbo.CONFIG.getString("commands.rain.description"))
            .withPermission(PermissionWrapper.of(
                Toorbo.CONFIG.getString("commands.rain.permission")
            ))
            .withMessages(
                Toorbo.CONFIG.getString("commands.rain.messages.rainSet")
            )
            .build();
    }

    public static CommandWrapper getThunderCommand() {
        return CommandWrapper.command(Toorbo.CONFIG.getString("commands.thunder.name"))
            .withDescription(Toorbo.CONFIG.getString("commands.thunder.description"))
            .withPermission(PermissionWrapper.of(
                Toorbo.CONFIG.getString("commands.thunder.permission")
            ))
            .withMessages(
                Toorbo.CONFIG.getString("commands.thunder.messages.thunderSet")
            )
            .build();
    }

    public static CommandWrapper getDayCommand() {
        return CommandWrapper.command(Toorbo.CONFIG.getString("commands.day.name"))
            .withDescription(Toorbo.CONFIG.getString("commands.day.description"))
            .withPermission(PermissionWrapper.of(
                Toorbo.CONFIG.getString("commands.day.permission")
            ))
            .withMessages(
                Toorbo.CONFIG.getString("commands.day.messages.daySet")
            )
            .build();
    }

    public static CommandWrapper getNightCommand() {
        return CommandWrapper.command(Toorbo.CONFIG.getString("commands.night.name"))
            .withDescription(Toorbo.CONFIG.getString("commands.night.description"))
            .withPermission(PermissionWrapper.of(
                Toorbo.CONFIG.getString("commands.night.permission")
            ))
            .withMessages(
                Toorbo.CONFIG.getString("commands.night.messages.nightSet")
            )
            .build();
    }
}
