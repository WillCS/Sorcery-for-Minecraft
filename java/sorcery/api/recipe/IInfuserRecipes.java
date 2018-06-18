package sorcery.api.recipe;

import net.minecraft.item.ItemStack;
import sorcery.api.element.ElementStack;
import sorcery.api.element.ItemElementStorage;

/** @author Vroominator */
public interface IInfuserRecipes {
	public void addRecipe(ItemStack[] ingredients, ElementStack[] elements, ItemStack result);
	
	public void addChargeableItem(ItemElementStorage chargeItem);
}
