package dev.gallardo.miarmacore.recipes;

import dev.gallardo.miarmacore.MiarmaCore;
import dev.gallardo.miarmacore.recipes.misc.RottenFleshCampfireRecipe;
import dev.gallardo.miarmacore.recipes.potions.ZombificationPotionRecipe;
import dev.gallardo.miarmacore.recipes.tools.BolsitaRecipe;
import dev.gallardo.miarmacore.recipes.tools.ScissorsRecipe;
import dev.gallardo.miarmacore.recipes.tools.SpawnerBreakerRecipe;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;

public class RecipeManager {
	public static void onEnable() {
		Bukkit.getServer().addRecipe(ScissorsRecipe.get());
		Bukkit.getServer().addRecipe(RottenFleshCampfireRecipe.get());
		Bukkit.getServer().addRecipe(SpawnerBreakerRecipe.get());
		Bukkit.getServer().addRecipe(ZombificationPotionRecipe.get());
		Bukkit.getServer().addRecipe(BolsitaRecipe.get());
	}
	
	public static void onDisable() {
		Bukkit.getServer().removeRecipe(new NamespacedKey(MiarmaCore.PLUGIN, "scissors"));
		Bukkit.getServer().removeRecipe(new NamespacedKey(MiarmaCore.PLUGIN, "rotten_campfire"));
		Bukkit.getServer().removeRecipe(new NamespacedKey(MiarmaCore.PLUGIN, "spawner_breaker"));
		Bukkit.getServer().removeRecipe(new NamespacedKey(MiarmaCore.PLUGIN, "zombification_potion"));
		Bukkit.getServer().removeRecipe(new NamespacedKey(MiarmaCore.PLUGIN, "bolsita"));
	}
}
