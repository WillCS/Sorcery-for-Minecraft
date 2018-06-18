package sorcery.api;

import net.minecraft.item.ItemStack;

public interface ITinkeringRecipes {
	/** They're both exactly the same as crafting table recipes */
	
	void addRecipe(ItemStack itemStack, Object... objectArray);
	
	void addShapelessRecipe(ItemStack itemStack, Object... objectArray);
}
