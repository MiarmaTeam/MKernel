package net.miarma.toorbo.recipes;

import net.miarma.toorbo.Toorbo;
import net.miarma.toorbo.recipes.misc.RottenFleshCampfireRecipe;
import net.miarma.toorbo.recipes.potions.ZombificationPotionRecipe;
import net.miarma.toorbo.recipes.tools.AdminStickRecipe;
import net.miarma.toorbo.recipes.tools.BolsitaRecipe;
import net.miarma.toorbo.recipes.tools.ScissorsRecipe;
import net.miarma.toorbo.recipes.tools.SpawnerBreakerRecipe;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;

import static net.miarma.toorbo.util.Constants.*;

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
		Bukkit.getServer().removeRecipe(new NamespacedKey(Toorbo.PLUGIN, ADMIN_STICK_KEY));
		Bukkit.getServer().removeRecipe(new NamespacedKey(Toorbo.PLUGIN, SCISSORS_KEY));
		Bukkit.getServer().removeRecipe(new NamespacedKey(Toorbo.PLUGIN, ROTTEN_CAMFIRE_KEY));
		Bukkit.getServer().removeRecipe(new NamespacedKey(Toorbo.PLUGIN, SPAWNER_BREAKER_KEY));
		Bukkit.getServer().removeRecipe(new NamespacedKey(Toorbo.PLUGIN, ZOMBIFICATION_POTION_KEY));
		Bukkit.getServer().removeRecipe(new NamespacedKey(Toorbo.PLUGIN, BOLSITA_KEY));
	}
}
