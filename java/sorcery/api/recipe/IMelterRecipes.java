package sorcery.api.recipe;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

/** @author Vroominator */
public interface IMelterRecipes {
	void addRecipe(ItemStack ingredient, int meltTime, FluidStack result);
}
