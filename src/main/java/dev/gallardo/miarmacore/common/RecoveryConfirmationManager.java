package dev.gallardo.miarmacore.common;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class RecoveryConfirmationManager {
    
    // Set para almacenar los jugadores que tienen una confirmación pendiente
    private static final Set<UUID> pendingConfirmations = new HashSet<>();

    /**
     * Añade un jugador a la lista de confirmaciones pendientes.
     * @param playerUUID El UUID del jugador.
     */
    public static void addPendingConfirmation(UUID playerUUID) {
        pendingConfirmations.add(playerUUID);
    }

    /**
     * Elimina un jugador de la lista de confirmaciones pendientes.
     * @param playerUUID El UUID del jugador.
     */
    public static void removePendingConfirmation(UUID playerUUID) {
        pendingConfirmations.remove(playerUUID);
    }

    /**
     * Verifica si un jugador tiene una confirmación pendiente.
     * @param playerUUID El UUID del jugador.
     * @return true si el jugador tiene una confirmación pendiente, false si no.
     */
    public static boolean hasPendingConfirmation(UUID playerUUID) {
        return pendingConfirmations.contains(playerUUID);
    }
}
