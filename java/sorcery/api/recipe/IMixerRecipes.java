package sorcery.api.recipe;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

/** @author Vroominator */
public interface IMixerRecipes {
	void addRecipe(FluidStack starterLiquid, FluidStack result, int cookTime, ItemStack... ingredients);
}
