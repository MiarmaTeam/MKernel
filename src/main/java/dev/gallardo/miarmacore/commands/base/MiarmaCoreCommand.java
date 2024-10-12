package dev.gallardo.miarmacore.commands.base;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import dev.gallardo.miarmacore.MiarmaCore;
import dev.gallardo.miarmacore.config.CommandWrapper;
import dev.gallardo.miarmacore.config.providers.CommandProvider;
import dev.gallardo.miarmacore.config.providers.MessageProvider;
import dev.gallardo.miarmacore.util.Utils;
import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MiarmaCoreCommand {
    public static void register() {
        CommandWrapper baseCmd = CommandProvider.getBaseCommand();
        CommandWrapper reloadSubCmd = baseCmd.getSubcommands()[0];
        CommandWrapper configSubCmd = baseCmd.getSubcommands()[1];
        new CommandAPICommand(baseCmd.getName())
                .withAliases(baseCmd.getAliases())
                .withPermission(baseCmd.getPermission().base())
                .withFullDescription(baseCmd.getDescription())
                .withShortDescription(baseCmd.getDescription())
                .withUsage(baseCmd.getUsage())
                .executes((sender, args) -> {
                    Utils.sendMessage(baseCmd.getMessages()[0], sender, true);
                })
                .withSubcommand(
                    new CommandAPICommand(reloadSubCmd.getName())
                        .withPermission(reloadSubCmd.getPermission().base())
                        .withFullDescription(reloadSubCmd.getDescription())
                        .withShortDescription(reloadSubCmd.getDescription())
                        .withUsage(reloadSubCmd.getUsage())
                        .executesPlayer((player, args) -> {
                            try {
                                MiarmaCore.CONFIG.reload();
                                Utils.sendMessage(reloadSubCmd.getMessages()[0], player,true);
                            } catch(Exception e) {
                                Utils.sendMessage(reloadSubCmd.getMessages()[1], player,true);
                            }
                        })
                )
                .withSubcommand(
                    new CommandAPICommand(configSubCmd.getName())
                        .withPermission(configSubCmd.getPermission().base())
                        .withFullDescription(configSubCmd.getDescription())
                        .withShortDescription(configSubCmd.getDescription())
                        .withUsage(configSubCmd.getUsage())
                        .executesPlayer((sender, args) -> {
                            Section confSec = MiarmaCore.CONFIG.getConfig().getSection("config.modules");
                            Map<String,Object> values = confSec.getStringRouteMappedValues(false);

                            int booleans = (int) values.values().stream()
                                    .filter(value -> value instanceof Boolean)
                                    .count();
                            int numberOfRows = (booleans / 9) + (booleans % 9 > 0 ? 1 : 0);

                            ChestGui gui = new ChestGui(numberOfRows,
                                    Utils.colorCodeParser(MessageProvider.Inventories.getConfigMenuTitle()));
                            OutlinePane pane = new OutlinePane(0, 0, 9, numberOfRows);

                            List<String> configItemsDisplayNames = values.entrySet().stream()
                                    .filter(x -> x.getValue() instanceof Boolean)
                                    .map(x -> Utils.colorCodeParser(MessageProvider.Inventories.getConfigMenuValueName())
                                            + x.getKey())
                                    .toList();
                            List<String> configItemsLores = values.values().stream()
                                    .filter(o -> o instanceof Boolean)
                                    .map(o -> Utils.colorCodeParser(MessageProvider.Inventories.getConfigMenuValueLore())
                                            + o)
                                    .toList();
                            List<ItemStack> configItems = new ArrayList<>();

                            for (int i = 0; i < booleans; i++) {
                                ItemStack item = new ItemStack(Material.PAPER, 1);
                                ItemMeta itemMeta = item.getItemMeta();
                                itemMeta.setDisplayName(configItemsDisplayNames.get(i));
                                itemMeta.setLore(List.of(configItemsLores.get(i)));
                                item.setItemMeta(itemMeta);
                                configItems.add(item);
                            }
                            for (int i = 0; i < configItems.size(); i++) {
                                GuiItem guiItem = new GuiItem(configItems.get(i), event -> {
                                    event.setCancelled(true);
                                    Utils.reloadConfigItem(event);
                                });

                                pane.addItem(guiItem);
                            }

                            gui.addPane(pane);
                            gui.show(sender);
                        })

                )
                .register();
    }
}
