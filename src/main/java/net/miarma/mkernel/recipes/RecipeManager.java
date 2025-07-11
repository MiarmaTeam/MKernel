package net.miarma.mkernel.recipes;

import net.miarma.mkernel.MKernel;
import net.miarma.mkernel.common.minecraft.VersionedRegistrar;
import net.miarma.mkernel.recipes.misc.RottenFleshCampfireRecipe;
import net.miarma.mkernel.recipes.potions.ZombificationPotionRecipe;
import net.miarma.mkernel.recipes.tools.AdminStickRecipe;
import net.miarma.mkernel.recipes.tools.ScissorsRecipe;
import net.miarma.mkernel.recipes.tools.SpawnerBreakerRecipe;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;

import static net.miarma.mkernel.common.Constants.*;

public class RecipeManager {
	public static void onEnable() {
		VersionedRegistrar.registerRecipe(AdminStickRecipe.class);
		VersionedRegistrar.registerRecipe(ScissorsRecipe.class);
		VersionedRegistrar.registerRecipe(RottenFleshCampfireRecipe.class);
		VersionedRegistrar.registerRecipe(SpawnerBreakerRecipe.class);
		VersionedRegistrar.registerRecipe(ZombificationPotionRecipe.class);
	}

	public static void onDisable() {
		Bukkit.removeRecipe(new NamespacedKey(MKernel.PLUGIN, ADMIN_STICK_KEY));
		Bukkit.removeRecipe(new NamespacedKey(MKernel.PLUGIN, SCISSORS_KEY));
		Bukkit.removeRecipe(new NamespacedKey(MKernel.PLUGIN, ROTTEN_CAMFIRE_KEY));
		Bukkit.removeRecipe(new NamespacedKey(MKernel.PLUGIN, SPAWNER_BREAKER_KEY));
		Bukkit.removeRecipe(new NamespacedKey(MKernel.PLUGIN, ZOMBIFICATION_POTION_KEY));
	}
}

