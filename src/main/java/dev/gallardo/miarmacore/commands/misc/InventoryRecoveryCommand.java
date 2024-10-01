package dev.gallardo.miarmacore.commands.misc;

import dev.gallardo.miarmacore.MiarmaCore;
import dev.gallardo.miarmacore.common.RecoveryConfirmationManager;
import dev.jorel.commandapi.CommandAPICommand;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;

public class InventoryRecoveryCommand {
    public static void register() {
        new CommandAPICommand(MiarmaCore.CONFIG.getString("commands.recinv.name"))
                .withPermission(MiarmaCore.CONFIG.getString("commands.recinv.permission"))
                .withShortDescription(MiarmaCore.CONFIG.getString("commands.recinv.description"))
                .withFullDescription(MiarmaCore.CONFIG.getString("commands.recinv.description"))
                .executesPlayer((sender, args) -> {
                    int xpLevels = sender.getLevel();
                    int requiredLevels = MiarmaCore.CONFIG.getInt("commands.recinv.requiredLevels");

                    if (xpLevels < requiredLevels) {
                        sender.sendMessage("No tienes suficientes niveles de XP para recuperar tu inventario. Necesitas al menos " + requiredLevels + " niveles.");
                        return;
                    }

                    // Crear los botones de Sí y No
                    TextComponent confirmMessage = new TextComponent("¿Quieres gastar " + requiredLevels + " niveles para recuperar tu inventario? ");

                    TextComponent yesButton = new TextComponent("[Sí]");
                    yesButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/recinvconfirm yes"));
                    yesButton.setColor(net.md_5.bungee.api.ChatColor.GREEN);

                    TextComponent noButton = new TextComponent("[No]");
                    noButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/recinvconfirm no"));
                    noButton.setColor(net.md_5.bungee.api.ChatColor.RED);

                    confirmMessage.addExtra(yesButton);
                    confirmMessage.addExtra(" ");
                    confirmMessage.addExtra(noButton);

                    // Enviar el mensaje al jugador
                    sender.spigot().sendMessage(confirmMessage);

                    // Guardar el estado de que este jugador ha iniciado el proceso de confirmación
                    RecoveryConfirmationManager.addPendingConfirmation(sender.getUniqueId());
                })
                .register();
    }
}
