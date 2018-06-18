package sorcery.api;

import net.minecraft.item.ItemStack;

public interface IForgeRecipes {
	void addRecipe(ItemStack mould, ItemStack result, int burnTime, ItemStack... ingredients);
}
