package dev.gallardo.miarmacore.commands.base;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import dev.gallardo.miarmacore.MiarmaCore;
import dev.gallardo.miarmacore.util.Utils;
import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static dev.gallardo.miarmacore.util.Constants.*;

public class MiarmaCoreCommand {
    public static void register() {
        new CommandAPICommand("miarmacore")
                .withAliases("mc","mcore")
                .withPermission(MiarmaCore.CONFIG.getString("commands.miarmacore.permission"))
                .withFullDescription(MiarmaCore.CONFIG.getString("commands.miarmacore.description"))
                .withShortDescription(MiarmaCore.CONFIG.getString("commands.miarmacore.description"))
                .withUsage(MiarmaCore.CONFIG.getString("commands.miarmacore.usage"))
                .executes((sender, args) -> {
                    Utils.sendMessage(
                        "[P] Desarrollado por &#2ca268&lGallardo7761&r para &#2ca268&lMiarmaCraft&r",
                        sender,
                        true
                    );
                })
                .withSubcommand(
                    new CommandAPICommand(MiarmaCore.CONFIG.getString("commands.miarmacore.subcommands.reload.name"))
                        .withPermission(
                                MiarmaCore.CONFIG.getString("commands.miarmacore.subcommands.reload.permission"))
                        .withFullDescription(MiarmaCore.CONFIG.getString("commands.miarmacore.subcommands.reload.description"))
                        .withShortDescription(MiarmaCore.CONFIG.getString("commands.miarmacore.subcommands.reload.description"))
                        .withUsage(MiarmaCore.CONFIG.getString("commands.miarmacore.subcommands.reload.usage"))
                        .executesPlayer((player, args) -> {
                            try {
                                MiarmaCore.CONFIG.reload();
                                Utils.sendMessage(
                                        MiarmaCore.CONFIG.getString("commands.miarmacore.subcommands.reload.messages.success"),
                                        player,true);
                            } catch(Exception e) {
                                Utils.sendMessage(
                                        MiarmaCore.CONFIG.getString("commands.miarmacore.subcommands.reload.messages.error"),
                                        player,true);
                            }
                        })
                )
                .withSubcommand(
                    new CommandAPICommand(MiarmaCore.CONFIG.getString("commands.miarmacore.subcommands.config.name"))
                        .withPermission("commands.miarmacore.subcommands.config.permission")
                        .withFullDescription(MiarmaCore.CONFIG.getString("commands.miarmacore.subcommands.config.description"))
                        .withShortDescription(MiarmaCore.CONFIG.getString("commands.miarmacore.subcommands.config.description"))
                        .withUsage(MiarmaCore.CONFIG.getString("commands.miarmacore.subcommands.config.usage"))
                        .executesPlayer((sender, args) -> {
                            Section confSec = MiarmaCore.CONFIG.getConfig().getSection("config.modules");
                            Map<String,Object> values = confSec.getStringRouteMappedValues(false);

                            int booleans = (int) values.values().stream()
                                    .map(Object::toString)
                                    .filter(x->x.equals("true") || x.equals("false"))
                                    .count();
                            int numberOfRows = (booleans / 9) + (booleans % 9 > 0 ? 1 : 0);

                            ChestGui gui = new ChestGui(booleans >= 9 ? numberOfRows : 1,
                                    Utils.colorCodeParser(
                                            MiarmaCore.CONFIG.getString("language.inventories.configMenu.title")));

                            OutlinePane pane = new OutlinePane(0, 0, booleans, numberOfRows);

                            List<String> configItemsDisplayNames = values.entrySet().stream()
                                    .filter(x->x.getValue().toString().equals("true") ||
                                            x.getValue().toString().equals("false"))
                                    .map(x->Utils.colorCodeParser(
                                            MiarmaCore.CONFIG.getString("language.inventories.configMenu.valueName"))
                                            +x.getKey()).toList();

                            List<String> configItemsLores = values.entrySet().stream()
                                    .filter(x->x.getValue().toString().equals("true") ||
                                            x.getValue().toString().equals("false"))
                                    .map(x->Utils.colorCodeParser(
                                            MiarmaCore.CONFIG.getString("language.inventories.configMenu.valueLore")) + x.getValue().toString()).toList();

                            List<ItemStack> configItems = new ArrayList<>();

                            for(int x = 0; x < booleans; x++) {
                                ItemStack item = new ItemStack(Material.PAPER,1);
                                ItemMeta itemMeta = item.getItemMeta();
                                itemMeta.setDisplayName(configItemsDisplayNames.get(x));
                                itemMeta.setLore(List.of(configItemsLores.get(x)));
                                item.setItemMeta(itemMeta);
                                configItems.add(item);
                            }

                            List<GuiItem> guiItems = configItems.stream().map(x->new GuiItem(x, event -> {
                                event.setCancelled(true);
                                Utils.reloadConfigItem(event);
                            })).toList();

                            guiItems.forEach(pane::addItem);
                            gui.addPane(pane);
                            gui.show(sender);
                        })
                )
                .register();
    }
}
