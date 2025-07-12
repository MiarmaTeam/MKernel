package net.miarma.mkernel.listeners;

import net.miarma.mkernel.MKernel;
import net.miarma.mkernel.common.minecraft.VersionedRegistrar;

public class ListenerManager {
    public static void onEnable() {
        VersionedRegistrar.registerListener(MKernel.PLUGIN, new BlockListener());
        VersionedRegistrar.registerListener(MKernel.PLUGIN, new BookListener());
        VersionedRegistrar.registerListener(MKernel.PLUGIN, new CampfireListener());
        VersionedRegistrar.registerListener(MKernel.PLUGIN, new ChatListener());
        VersionedRegistrar.registerListener(MKernel.PLUGIN, new CropsListener());
        VersionedRegistrar.registerListener(MKernel.PLUGIN, new DeathListener());
        VersionedRegistrar.registerListener(MKernel.PLUGIN, new EntityListener());
        VersionedRegistrar.registerListener(MKernel.PLUGIN, new InventoryListener());
        VersionedRegistrar.registerListener(MKernel.PLUGIN, new ItemListener());
        VersionedRegistrar.registerListener(MKernel.PLUGIN, new PlayerListener());
        VersionedRegistrar.registerListener(MKernel.PLUGIN, new PotionListener());
        VersionedRegistrar.registerListener(MKernel.PLUGIN, new SpyListener());
        VersionedRegistrar.registerListener(MKernel.PLUGIN, new WorldListener());
        VersionedRegistrar.registerListener(MKernel.PLUGIN, new HappyGhastListener());
    }
}
