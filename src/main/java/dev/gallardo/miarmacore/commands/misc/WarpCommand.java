package dev.gallardo.miarmacore.commands.misc;

import dev.gallardo.miarmacore.MiarmaCore;
import dev.gallardo.miarmacore.common.minecraft.Warp;
import dev.gallardo.miarmacore.config.CommandWrapper;
import dev.gallardo.miarmacore.config.providers.CommandProvider;
import dev.gallardo.miarmacore.config.providers.ConfigProvider;
import dev.gallardo.miarmacore.config.providers.MessageProvider;
import dev.gallardo.miarmacore.util.MessageUtils;
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

import static dev.gallardo.miarmacore.config.providers.CommandProvider.Arguments.WARP_NAME;

public class WarpCommand {
    public static void register() {
        CommandWrapper warpCmd = CommandProvider.getWarpCommand();
        CommandWrapper addSubCmd = warpCmd.getSubcommands()[0];
        CommandWrapper removeSubCmd = warpCmd.getSubcommands()[1];
        new CommandAPICommand(warpCmd.getName())
            .withPermission(warpCmd.getPermission().base())
            .withFullDescription(warpCmd.getDescription())
            .withShortDescription(warpCmd.getDescription())
            .withUsage(warpCmd.getUsage())
            .executesPlayer((sender, args) -> {
                File f = new File(MiarmaCore.PLUGIN.getDataFolder().getAbsolutePath(), "warps/"
                        + sender.getName() + ".yml");
                FileConfiguration c = YamlConfiguration.loadConfiguration(f);

                Set<Warp> warps = c.getKeys(false).stream()
                        .map(alias -> Warp.fromFile(c, alias))
                        .collect(Collectors.toSet());

                if (warps.isEmpty()) {
                    MessageUtils.sendMessage(sender, warpCmd.getMessages()[0], true);
                } else {
                    String warpList = warps.stream()
                            .map(Warp::toFormattedMessage)
                            .collect(Collectors.joining("\n"));
                    warpList = MessageUtils.formatMessage(warpList, false);

                    MessageUtils.sendMessage(sender, warpCmd.getMessages()[1], true,
                                                List.of("%warps%"), List.of(warpList));
                }

            })
            .withSubcommand(
                new CommandAPICommand(addSubCmd.getName())
                    .withPermission(addSubCmd.getPermission().base())
                    .withFullDescription(addSubCmd.getDescription())
                    .withShortDescription(addSubCmd.getDescription())
                    .withArguments(WARP_NAME)
                    .withUsage(addSubCmd.getUsage())
                    .executesPlayer((sender, args) -> {
                        File f = new File(MiarmaCore.PLUGIN.getDataFolder().getAbsolutePath(), "warps/"
                                + sender.getName() + ".yml");
                        FileConfiguration c = YamlConfiguration.loadConfiguration(f);

                        if (c.getKeys(false).size() >= ConfigProvider.Values.getMaxWarps()) {
                            MessageUtils.sendMessage(sender, MessageProvider.Errors.maxWarpsReached(), true);
                            return;
                        }

                        if(c.contains(args.getRaw(0))) {
                            MessageUtils.sendMessage(sender, addSubCmd.getMessages()[1], true,
                                                        List.of("%warp%"), List.of(args.getRaw(0)));
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

                        MessageUtils.sendMessage(sender, addSubCmd.getMessages()[0], true,
                                                    List.of("%warp%"), List.of(warpName));
                    })
            )
            .withSubcommand(
                new CommandAPICommand(removeSubCmd.getName())
                    .withPermission(removeSubCmd.getPermission().base())
                    .withFullDescription(removeSubCmd.getDescription())
                    .withShortDescription(removeSubCmd.getDescription())
                    .withArguments(WARP_NAME)
                    .withUsage(removeSubCmd.getUsage())
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
                            MessageUtils.sendMessage(sender, removeSubCmd.getMessages()[0], true,
                                                       List.of("%warp%"), List.of(warpName));
                        } else {
                            MessageUtils.sendMessage(sender, removeSubCmd.getMessages()[1], true,
                                                       List.of("%warp%"), List.of(warpName));
                        }
                    })
            )
            .register();

    }
}
