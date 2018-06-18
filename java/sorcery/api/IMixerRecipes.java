package sorcery.api;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public interface IMixerRecipes {
	void addRecipe(FluidStack starterLiquid, FluidStack result, int cookTime, ItemStack... ingredients);
}
