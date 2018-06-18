package sorcery.api.recipe;

import net.minecraft.item.ItemStack;

/** @author Vroominator */
public interface IForgeRecipes {
	/** Sets the temperature required to 50, or (if applicable) the highest
	 * melting point of the items in the ingredients */
	void addRecipe(ItemStack mould, ItemStack result, int burnTime, ItemStack... ingredients);
	
	void addRecipe(ItemStack mould, ItemStack result, int temperature, int burnTime, ItemStack... ingredients);
}
