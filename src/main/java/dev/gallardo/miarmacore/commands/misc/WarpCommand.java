package dev.gallardo.miarmacore.commands.misc;

import dev.gallardo.miarmacore.MiarmaCore;
import dev.gallardo.miarmacore.common.minecraft.Warp;
import dev.gallardo.miarmacore.util.Constants;
import dev.gallardo.miarmacore.util.Utils;
import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static dev.gallardo.miarmacore.util.Constants.WARP_NAME;

public class WarpCommand {
    public static void register() {
        new CommandAPICommand(MiarmaCore.CONFIG.getString("commands.warp.name"))
            .withPermission(MiarmaCore.CONFIG.getString("commands.warp.permission"))
            .withFullDescription(MiarmaCore.CONFIG.getString("commands.warp.description"))
            .withShortDescription(MiarmaCore.CONFIG.getString("commands.warp.description"))
            .withUsage(MiarmaCore.CONFIG.getString("commands.warp.usage"))
            .executesPlayer((sender, args) -> {
                File f = new File(MiarmaCore.PLUGIN.getDataFolder().getAbsolutePath(), "warps/"
                        + sender.getName() + ".yml");
                FileConfiguration c = YamlConfiguration.loadConfiguration(f);

                Set<Warp> warps = c.getKeys(false).stream()
                        .map(alias -> Warp.fromFile(c, alias))
                        .collect(Collectors.toSet());

                if (warps.isEmpty()) {
                    Utils.sendMessage(MiarmaCore.CONFIG.getString("commands.warp.messages.noWarpsStored"),
                            sender, true);
                } else {
                    String warpList = warps.stream()
                            .map(Warp::toFormattedMessage)
                            .collect(Collectors.joining("\n"));
                    warpList = Utils.formatMessage(warpList, false);

                    Utils.sendMessage(MiarmaCore.CONFIG.getString("commands.warp.messages.warpList"),
                        sender, true, true,
                        List.of("%warps%"), List.of(warpList));
                }

            })
            .withSubcommand(
                new CommandAPICommand(MiarmaCore.CONFIG.getString("commands.warp.subcommands.add.name"))
                    .withPermission(MiarmaCore.CONFIG.getString("commands.warp.subcommands.add.permission"))
                    .withFullDescription(MiarmaCore.CONFIG.getString("commands.warp.subcommands.add.description"))
                    .withShortDescription(MiarmaCore.CONFIG.getString("commands.warp.subcommands.add.description"))
                    .withArguments(WARP_NAME)
                    .withUsage(MiarmaCore.CONFIG.getString("commands.warp.subcommands.add.usage"))
                    .executesPlayer((sender, args) -> {
                        File f = new File(MiarmaCore.PLUGIN.getDataFolder().getAbsolutePath(), "warps/"
                                + sender.getName() + ".yml");
                        FileConfiguration c = YamlConfiguration.loadConfiguration(f);

                        if (c.getKeys(false).size() >= MiarmaCore.CONFIG.getInt("config.values.maxWarps")) {
                            Utils.sendMessage(
                                MiarmaCore.CONFIG.getString("language.errors.maxWarpsReached"),
                                sender, true
                            );
                            return;
                        }

                        String warpName = args.getRaw(0);
                        Location loc = sender.getLocation();
                        World world = sender.getWorld();
                        Warp warp = Warp.of(
                            warpName,
                            Math.round(loc.getX()),
                            Math.round(loc.getY()),
                            Math.round(loc.getZ()),
                            world.getName()
                        );
                        Warp.toFile(c, warp);

                        try {
                            c.save(f);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                        Utils.sendMessage(
                            MiarmaCore.CONFIG.getString("commands.warp.subcommands.add.messages.warpAdded"),
                            sender, true, true, List.of("%warp%"), List.of(warpName)
                        );
                    })
            )
            .withSubcommand(
                new CommandAPICommand(MiarmaCore.CONFIG.getString("commands.warp.subcommands.remove.name"))
                    .withPermission(MiarmaCore.CONFIG.getString("commands.warp.subcommands.remove.permission"))
                    .withFullDescription(MiarmaCore.CONFIG.getString("commands.warp.subcommands.remove.description"))
                    .withShortDescription(MiarmaCore.CONFIG.getString("commands.warp.subcommands.remove.description"))
                    .withArguments(WARP_NAME)
                    .withUsage(MiarmaCore.CONFIG.getString("commands.warp.subcommands.remove.usage"))
                    .executes((sender, args) -> {
                        File f = new File(MiarmaCore.PLUGIN.getDataFolder().getAbsolutePath(), "warps/"
                                + sender.getName() + ".yml");
                        FileConfiguration c = YamlConfiguration.loadConfiguration(f);

                        String warpName = args.getRaw(0);
                        if (c.contains(warpName)) {
                            c.set(warpName, null);
                            try {
                                c.save(f);
                            } catch (IOException e) {
                                MiarmaCore.LOGGER.severe("Error al guardar el archivo de warps de " + sender.getName());
                            }
                            Utils.sendMessage(
                                MiarmaCore.CONFIG.getString("commands.warp.subcommands.remove.messages.warpRemoved"),
                                sender, true, true, List.of("%warp%"), List.of(warpName)
                            );
                        } else {
                            Utils.sendMessage(
                                MiarmaCore.CONFIG.getString("commands.warp.subcommands.remove.messages.warpNotFound"),
                                sender, true, true, List.of("%warp%"), List.of(warpName)
                            );
                        }
                    })
            )
            .register();

    }
}
