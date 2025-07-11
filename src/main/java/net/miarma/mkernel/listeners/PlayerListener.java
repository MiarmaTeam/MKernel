package net.miarma.mkernel.listeners;

import net.miarma.mkernel.MKernel;
import net.miarma.mkernel.common.annotation.Version;
import net.miarma.mkernel.config.providers.ConfigProvider;
import net.miarma.mkernel.config.providers.MessageProvider;
import net.miarma.mkernel.util.FileUtil;
import net.miarma.mkernel.util.MessageUtil;
import net.miarma.mkernel.util.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.persistence.PersistentDataType;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import static net.miarma.mkernel.common.Constants.FROZEN_KEY;
import static net.miarma.mkernel.common.Constants.VANISH_KEY;

public class PlayerListener implements Listener {
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        // Spawn en lobby
        if (ConfigProvider.Modules.isSpawnAtLobbyEnabled()) {
            var lobbyConfig = ConfigProvider.Worlds.getLobby();
            String lobbyWorldName = lobbyConfig.name();

            boolean worldExists = Bukkit.getWorlds().stream()
                    .map(world -> world.getName().toLowerCase())
                    .anyMatch(name -> name.contains(lobbyWorldName.toLowerCase()));

            if (worldExists) {
                World world = Bukkit.getWorld(lobbyWorldName);
                if (world != null) {
                    Location lobbyLocation = new Location(
                            world,
                            lobbyConfig.x(),
                            lobbyConfig.y(),
                            lobbyConfig.z(),
                            lobbyConfig.yaw(),
                            lobbyConfig.pitch()
                    );
                    player.teleport(lobbyLocation);
                }
            } else {
                MKernel.LOGGER.severe(
                        MessageUtil.formatMessageConsole(MessageProvider.Errors.lobbyDoesNotExist(), true)
                );
            }
        }

        // Título de bienvenida
        if (ConfigProvider.Modules.isJoinTitleEnabled()) {
            String title = MessageUtil.parseColors(MessageProvider.Titles.getTitleFormat()) + player.getName();
            String subtitle = MessageUtil.parseColors(MessageProvider.Titles.getJoinSubtitle());

            for (Player p : Bukkit.getOnlinePlayers()) {
                p.sendTitle(title, subtitle, 30, 30, 30);
            }
        }

        // Inicializar persistencia y archivos
        player.getPersistentDataContainer().set(VANISH_KEY, PersistentDataType.BOOLEAN, false);

        File warpsFile = new File(MKernel.PLUGIN.getDataFolder(), "warps/" + player.getName() + ".yml");
        File inventoriesFile = new File(MKernel.PLUGIN.getDataFolder(), "inventories/" + player.getName() + ".yml");

        try {
            warpsFile.createNewFile();
            inventoriesFile.createNewFile();
        } catch (IOException e) {
            MKernel.LOGGER.severe("Error creating file for " + player.getName());
        }
    }

    
    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        if (!ConfigProvider.Modules.isLeaveTitleEnabled()) return;

        Player leaving = event.getPlayer();

        String title = MessageUtil.parseColors(MessageProvider.Titles.getTitleFormat()) + leaving.getName();
        String subtitle = MessageUtil.parseColors(MessageProvider.Titles.getLeaveSubtitle());

        for (Player online : Bukkit.getOnlinePlayers()) {
            if (online.equals(leaving)) continue; // Opcional: evitar mostrar título al que se va
            online.sendTitle(title, subtitle, 30, 30, 30);
        }
    }

    
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) throws IOException {
        Player player = event.getEntity();
        int levelBeforeDeath = player.getLevel();
        float expBeforeDeath = player.getExp();

        // Título de muerte
        if (ConfigProvider.Modules.isDeathTitleEnabled()) {
            String subtitle = MessageUtil.parseColors(
                    MessageUtil.parsePlaceholders(
                            MessageProvider.Titles.getDeathSubtitle(),
                            List.of("%player%"), List.of(player.getName())
                    )
            );

            for (Player online : Bukkit.getOnlinePlayers()) {
                online.playSound(online.getLocation(), Sound.ENTITY_WITHER_DEATH, 1f, 1f);
                online.sendTitle(subtitle, "", 30, 30, 30);
            }
        }

        // Recuperación de inventario
        if (!ConfigProvider.Modules.isRecoverInventoryEnabled()) return;

        Location deathLoc = player.getLocation();
        Location respawnLoc = player.getRespawnLocation() != null
                ? player.getRespawnLocation()
                : player.getWorld().getSpawnLocation();

        double maxDist = ConfigProvider.Values.getRecInvSpawnDistance();
        int radius = ConfigProvider.Values.getRecInvPlayerRadius();

        boolean isNearSpawn = deathLoc.distance(respawnLoc) <= maxDist;
        boolean hasNearbyPlayers = PlayerUtil.playersNearRadius(player, (Collection<Player>) Bukkit.getOnlinePlayers(), radius);

        if (isNearSpawn || hasNearbyPlayers) {
            List<String> coords = List.of(
                    String.valueOf(deathLoc.getBlockX()),
                    String.valueOf(deathLoc.getBlockY()),
                    String.valueOf(deathLoc.getBlockZ())
            );

            MessageUtil.sendMessage(
                    player,
                    MessageProvider.Events.getOnDeathItemsNotRecovered(),
                    true,
                    List.of("%x%", "%y%", "%z%"),
                    coords
            );
            return;
        }

        // Guardamos inventario
        FileUtil.saveInventory(player);

        // Limpiamos drops y XP
        event.getDrops().clear();
        event.setDroppedExp(0);
        event.setKeepInventory(true);
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.updateInventory();

        // Perdida de experiencia
        float lossRatio = ConfigProvider.Values.getXpLossOnDeath();
        int levelsLost = Math.round(levelBeforeDeath * lossRatio);
        int newLevel = Math.max(0, levelBeforeDeath - levelsLost);

        event.setNewLevel(newLevel);
        event.setNewExp((int) expBeforeDeath);

        MessageUtil.sendMessage(
                player,
                MessageProvider.Events.getOnDeathLostLevelsItems(),
                true,
                List.of("%levels%"),
                List.of(String.valueOf(levelsLost))
        );
    }

    
    @EventHandler
    public void onPlayerFreeze(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        boolean isFrozen = Boolean.TRUE.equals(
                player.getPersistentDataContainer().get(FROZEN_KEY, PersistentDataType.BOOLEAN)
        );

        if (!isFrozen) return;

        if (event.getFrom().getX() == event.getTo().getX()
                && event.getFrom().getY() == event.getTo().getY()
                && event.getFrom().getZ() == event.getTo().getZ()) {
            return;
        }

        event.setCancelled(true);
    }

}
