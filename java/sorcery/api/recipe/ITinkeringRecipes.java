package sorcery.api.recipe;

import net.minecraft.item.ItemStack;

/** @author Vroominator */
public interface ITinkeringRecipes {
	void addRecipe(ItemStack itemStack, Object... objectArray);
	
	void addShapelessRecipe(ItemStack itemStack, Object... objectArray);
}
