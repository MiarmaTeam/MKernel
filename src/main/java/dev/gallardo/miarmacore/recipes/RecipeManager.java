package dev.gallardo.miarmacore.recipes;

import dev.gallardo.miarmacore.MiarmaCore;
import dev.gallardo.miarmacore.recipes.misc.RottenFleshCampfireRecipe;
import dev.gallardo.miarmacore.recipes.potions.ZombificationPotionRecipe;
import dev.gallardo.miarmacore.recipes.tools.AdminStickRecipe;
import dev.gallardo.miarmacore.recipes.tools.BolsitaRecipe;
import dev.gallardo.miarmacore.recipes.tools.ScissorsRecipe;
import dev.gallardo.miarmacore.recipes.tools.SpawnerBreakerRecipe;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;

import static dev.gallardo.miarmacore.util.Constants.*;

public class RecipeManager {
	public static void onEnable() {
		Bukkit.getServer().addRecipe(AdminStickRecipe.get());
		Bukkit.getServer().addRecipe(ScissorsRecipe.get());
		Bukkit.getServer().addRecipe(RottenFleshCampfireRecipe.get());
		Bukkit.getServer().addRecipe(SpawnerBreakerRecipe.get());
		Bukkit.getServer().addRecipe(ZombificationPotionRecipe.get());
		Bukkit.getServer().addRecipe(BolsitaRecipe.get());
	}
	
	public static void onDisable() {
		Bukkit.getServer().removeRecipe(new NamespacedKey(MiarmaCore.PLUGIN, ADMIN_STICK_KEY));
		Bukkit.getServer().removeRecipe(new NamespacedKey(MiarmaCore.PLUGIN, SCISSORS_KEY));
		Bukkit.getServer().removeRecipe(new NamespacedKey(MiarmaCore.PLUGIN, ROTTEN_CAMFIRE_KEY));
		Bukkit.getServer().removeRecipe(new NamespacedKey(MiarmaCore.PLUGIN, SPAWNER_BREAKER_KEY));
		Bukkit.getServer().removeRecipe(new NamespacedKey(MiarmaCore.PLUGIN, ZOMBIFICATION_POTION_KEY));
		Bukkit.getServer().removeRecipe(new NamespacedKey(MiarmaCore.PLUGIN, BOLSITA_KEY));
	}
}
