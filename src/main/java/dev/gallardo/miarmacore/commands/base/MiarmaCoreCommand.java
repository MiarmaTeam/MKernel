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
                                    .filter(value -> value instanceof Boolean)
                                    .count();

                            int numberOfRows = (booleans / 9) + (booleans % 9 > 0 ? 1 : 0);

                            // Crea el GUI del cofre con el número de filas necesarias
                            ChestGui gui = new ChestGui(numberOfRows,
                                    Utils.colorCodeParser(
                                            MiarmaCore.CONFIG.getString("language.inventories.configMenu.title")));

                            OutlinePane pane = new OutlinePane(0, 0, 9, numberOfRows); // 9 slots por fila

                            // Obtener los nombres y descripciones de los ítems
                            List<String> configItemsDisplayNames = values.entrySet().stream()
                                    .filter(x -> x.getValue() instanceof Boolean)
                                    .map(x -> Utils.colorCodeParser(MiarmaCore.CONFIG.getString("language.inventories.configMenu.valueName"))
                                            + x.getKey())
                                    .toList();

                            List<String> configItemsLores = values.values().stream()
                                    .filter(o -> o instanceof Boolean)
                                    .map(o -> Utils.colorCodeParser(MiarmaCore.CONFIG.getString("language.inventories.configMenu.valueLore"))
                                            + o)
                                    .toList();

                            List<ItemStack> configItems = new ArrayList<>();

                            // Crear los ítems del inventario
                            for (int i = 0; i < booleans; i++) {
                                ItemStack item = new ItemStack(Material.PAPER, 1);
                                ItemMeta itemMeta = item.getItemMeta();
                                itemMeta.setDisplayName(configItemsDisplayNames.get(i));
                                itemMeta.setLore(List.of(configItemsLores.get(i)));
                                item.setItemMeta(itemMeta);
                                configItems.add(item);
                            }

                            // Añadir los ítems al GUI asignando slots secuenciales
                            for (int i = 0; i < configItems.size(); i++) {
                                GuiItem guiItem = new GuiItem(configItems.get(i), event -> {
                                    event.setCancelled(true);
                                    Utils.reloadConfigItem(event);  // Recargar la configuración del ítem
                                });

                                pane.addItem(guiItem);  // Añade el ítem al siguiente slot disponible en el pane
                            }

                            gui.addPane(pane);  // Añadir el pane al GUI del cofre
                            gui.show(sender);  // Mostrar el GUI al jugador
                        })

                )
                .register();
    }
}
