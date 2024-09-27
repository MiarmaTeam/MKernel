package dev.gallardo.miarmacore.commands;

import dev.gallardo.miarmacore.MiarmaCore;
import dev.gallardo.miarmacore.common.TpaRequest;
import dev.gallardo.miarmacore.common.TpaRequests;
import dev.gallardo.miarmacore.util.ConfigWrapper;
import dev.gallardo.miarmacore.util.Utils;
import dev.gallardo.miarmacore.util.WebAPIAccessor;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.Argument;
import dev.jorel.commandapi.arguments.ArgumentSuggestions;
import dev.jorel.commandapi.arguments.GreedyStringArgument;
import dev.jorel.commandapi.arguments.PlayerArgument;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@SuppressWarnings("all")
public class CommandHandler {

    private static final ConfigWrapper cfg = MiarmaCore.getConf();
    private static TpaRequests tpaRequests = TpaRequests.getInstance();

    private static final Argument<?> passwordArg = new GreedyStringArgument(cfg.getString("arguments.password"));
    private static Argument<?> playerArg = new PlayerArgument(cfg.getString("arguments.player"))
            .replaceSuggestions(ArgumentSuggestions.strings(info -> Bukkit.getOnlinePlayers().stream()
                    .map(Player::getName).toList().toArray(new String[Bukkit.getOnlinePlayers().size()])));

    private static void registerCommands() {
        new CommandAPICommand("miarmacore")
            .withAliases("mc","mcore")
            .withFullDescription(cfg.getString("commands.miarmacore.description"))
            .withShortDescription(cfg.getString("commands.miarmacore.description"))
            .executes((sender, args) -> {
                Utils.sendMessage(
            "[P] Desarrollado por &#2ca268&lGallardo7761&r para &#2ca268&lMiarmaCraft&r",
                    sender,
              true
                );
            })
            .withSubcommand(
                new CommandAPICommand("reload")
                    .withPermission("miarmacore.reload")
                    .withFullDescription(cfg.getString("commands.miarmacore.reload.description"))
                    .withShortDescription(cfg.getString("commands.miarmacore.reload.description"))
                    .executesPlayer((player, args) -> {
                        try {
                            cfg.reload();
                            Utils.sendMessage(cfg.getString("language.commands.miarmacore.reload.success"),
                                    player,true);
                        } catch(Exception e) {
                            Utils.sendMessage(cfg.getString("language.commands.miarmacore.reload.error"),
                                    player,true);
                        }
                    })
            )
            .register();

        new CommandAPICommand("registroweb")
            .withOptionalArguments(passwordArg)
            .withFullDescription(cfg.getString("commands.register.description"))
            .withPermission("miarmacore.register")
            .withShortDescription(cfg.getString("commands.register.description"))
            .executesPlayer((sender,args) -> {
                boolean userPassword = args.rawArgs() != null && args.rawArgs().length > 0;
                String password;

                String username = sender.getName();

                String rol = sender.hasPermission("group.admin") ? "admin" : "user";

                if(userPassword) {
                    password = Arrays.stream(args.rawArgs()).collect(Collectors.joining(" "));
                } else {
                    password = Utils.generateRandomPassword(8);
                }

                if(WebAPIAccessor.register(username,password,rol)) {
                    Utils.sendMessage(
                        Utils.placeholderParser(
                            cfg.getString("language.commands.register.success"),
                            List.of("%user%", "%password%"),
                            List.of(username, password)
                        ),
                        sender,true);
                } else {
                    Utils.sendMessage(cfg.getString("language.commands.register.error"),
                            sender,true);
                }
            })
            .register();

        /*new CommandAPICommand("tpa")
            .withArguments(playerArg)
            .withPermission("miarmacore.tpa")
            .withFullDescription(cfg.getString("commands.tpa.description"))
            .withShortDescription(cfg.getString("commands.tpa.description"))
            .withUsage(cfg.getString("commands.tpa.usage"))
            .executesPlayer((sender, args) -> {
                if (!(sender instanceof Player)) {
                    sender.sendMessage(cfg.getString("language.errors.onlyPlayerCommand"));
                    return;
                }

                Player target = Bukkit.getPlayer(args.getRaw(0));

                if (target == null || !target.isOnline()) {
                    Utils.sendMessage(cfg.getString("language.errors.playerNotFound"), sender, true);
                    return;
                }

                if (target.equals(sender)) {
                    Utils.sendMessage(cfg.getString("language.errors.cantTeleportToYourself"), sender, true);
                    return;
                }

                boolean requestExists = tpaRequests.getRequests().stream()
                        .anyMatch(request ->
                                (request.getFrom().equals(sender) && request.getTo().equals(target)) ||
                                        (request.getFrom().equals(target) && request.getTo().equals(sender))
                        );

                if (requestExists) {
                    Utils.sendMessage(cfg.getString("language.errors.requestAlreadySent"), sender, true);
                    return;
                }

                tpaRequests.addRequest(sender, target, TpaType.TPA);
                MiarmaCore.logger.info(tpaRequests.toString());

                Utils.sendMessage(
                        Utils.placeholderParser(
                                cfg.getString("language.commands.tpa.tpaToPlayer"),
                                List.of("%target%"),
                                List.of(target.getName())
                        ),
                        sender, true
                );

                Utils.sendMessage(
                        Utils.placeholderParser(
                                cfg.getString("language.commands.tpa.tpaFromPlayer"),
                                List.of("%sender%"),
                                List.of(sender.getName())
                        ),
                        target, true
                );
            })
            .register();

        new CommandAPICommand("tpahere")
            .withArguments(playerArg)
            .withPermission("miarmacore.tpahere")
            .withFullDescription(cfg.getString("commands.tpahere.description"))
            .withShortDescription(cfg.getString("commands.tpahere.description"))
            .withUsage(cfg.getString("commands.tpahere.usage"))
            .executesPlayer((sender, args) -> {
                if (!(sender instanceof Player)) {
                    sender.sendMessage(cfg.getString("language.errors.onlyPlayerCommand"));
                    return;
                }

                Player target = Bukkit.getPlayer(args.getRaw(0));

                if (target == null || !target.isOnline()) {
                    Utils.sendMessage(cfg.getString("language.errors.playerNotFound"), sender, true);
                    return;
                }

                if (target.equals(sender)) {
                    Utils.sendMessage(cfg.getString("language.errors.cantTeleportToYourself"), sender, true);
                    return;
                }

                // Verificar si ya hay una solicitud activa entre el sender y el target
                boolean requestExists = tpaRequests.getRequests().stream()
                        .anyMatch(request ->
                                (request.getFrom().equals(sender) && request.getTo().equals(target)) ||
                                        (request.getFrom().equals(target) && request.getTo().equals(sender))
                        );

                if (requestExists) {
                    Utils.sendMessage(cfg.getString("language.errors.requestAlreadySent"), sender, true);
                    return;
                }

                // Añadir la solicitud si no existe
                tpaRequests.addRequest(target, sender, TpaType.TPA_HERE);
                MiarmaCore.logger.info(tpaRequests.toString());

                Utils.sendMessage(
                        Utils.placeholderParser(
                                cfg.getString("language.commands.tpahere.tpaToPlayer"),
                                List.of("%target%"),
                                List.of(target.getName())
                        ),
                        sender, true
                );

                Utils.sendMessage(
                        Utils.placeholderParser(
                                cfg.getString("language.commands.tpahere.tpaFromPlayer"),
                                List.of("%sender%"),
                                List.of(sender.getName())
                        ),
                        target, true
                );
            })
            .register();

        new CommandAPICommand("tpaccept")
            .withArguments(playerArg)
            .withPermission("miarmacore.tpaccept")
            .withFullDescription(cfg.getString("commands.tpaccept.description"))
            .withShortDescription(cfg.getString("commands.tpaccept.description"))
            .withUsage(cfg.getString("commands.tpaccept.usage"))
            .executesPlayer((sender, args) -> {
                MiarmaCore.logger.info(tpaRequests.toString());

                if (!(sender instanceof Player)) {
                    sender.sendMessage(cfg.getString("language.errors.onlyPlayerCommand"));
                    return;
                }

                Player target = Bukkit.getPlayer(args.getRaw(0));

                if (target == null || !target.isOnline()) {
                    Utils.sendMessage(cfg.getString("language.errors.playerNotFound"), sender, true);
                    return;
                }

                // Buscar la solicitud de TPA correcta entre el sender y el target
                Optional<TpaRequest> requestOpt = tpaRequests.getRequests().stream()
                        .filter(request ->
                                request.getFrom().equals(target) && request.getTo().equals(sender)
                        )
                        .findFirst();

                if (requestOpt.isEmpty()) {
                    Utils.sendMessage(cfg.getString("language.errors.noRequestFound"), sender, true);
                    return;
                }

                TpaRequest request = requestOpt.get();
                tpaRequests.removeRequest(request); // Eliminar la solicitud después de aceptarla

                // Teletransportar al target según el tipo de solicitud (TPA o TPA_HERE)
                if (request.getType() == TpaType.TPA) {
                    sender.teleport(target.getLocation());
                } else if (request.getType() == TpaType.TPA_HERE) {
                    target.teleport(sender.getLocation());
                }

                Utils.sendMessage(cfg.getString("language.commands.tpaccept.success"), sender, true);
                Utils.sendMessage(cfg.getString("language.commands.tpaccept.successToSender"), target, true);
            })
            .register();

        new CommandAPICommand("tpdeny")
            .withArguments(playerArg)
            .withPermission("miarmacore.tpdeny")
            .withFullDescription(cfg.getString("commands.tpdeny.description"))
            .withShortDescription(cfg.getString("commands.tpdeny.description"))
            .withUsage(cfg.getString("commands.tpdeny.usage"))
            .executesPlayer((sender, args) -> {
                MiarmaCore.logger.info(tpaRequests.toString());

                if (!(sender instanceof Player)) {
                    sender.sendMessage(cfg.getString("language.errors.onlyPlayerCommand"));
                    return;
                }

                Player target = Bukkit.getPlayer(args.getRaw(0));

                if (target == null || !target.isOnline()) {
                    Utils.sendMessage(cfg.getString("language.errors.playerNotFound"), sender, true);
                    return;
                }

                // Buscar la solicitud de TPA correcta entre el sender y el target
                Optional<TpaRequest> requestOpt = tpaRequests.getRequests().stream()
                        .filter(request ->
                                request.getFrom().equals(target) && request.getTo().equals(sender)
                        )
                        .findFirst();

                if (requestOpt.isEmpty()) {
                    Utils.sendMessage(cfg.getString("language.errors.noRequestFound"), sender, true);
                    return;
                }

                TpaRequest request = requestOpt.get();
                tpaRequests.removeRequest(request); // Eliminar la solicitud después de denegarla

                // Notificar a ambos jugadores
                Utils.sendMessage(cfg.getString("language.commands.tpdeny.success"), sender, true);
                Utils.sendMessage(cfg.getString("language.commands.tpdeny.senderDenied"), target, true);
            })
            .register();*/

    }

    public static void onEnable() {
        registerCommands();
    }
}
