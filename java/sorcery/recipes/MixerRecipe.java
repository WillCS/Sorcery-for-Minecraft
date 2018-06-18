package sorcery.recipes;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import sorcery.lib.ItemStackHelper;
import sorcery.tileentities.TileEntityMixer;

public class MixerRecipe {
	/** Is the FluidStack that you get when craft the recipe. */
	public final FluidStack recipeResult;
	
	/** Is a List of ItemStacks that composes the recipe. */
	public final List<ItemStack> recipeIngredients;
	
	/** Is the FluidStack required to begin mixing */
	public final FluidStack starterFluid;
	
	/** Is how many ticks the recipe must cook for */
	public final int cookTime;
	
	public MixerRecipe(FluidStack starterFluid, FluidStack result, int cookTime, List<ItemStack> ingredients) {
		this.starterFluid = starterFluid;
		this.recipeResult = result;
		this.cookTime = cookTime;
		this.recipeIngredients = ingredients;
	}
	
	public FluidStack getRecipeResult() {
		return this.recipeResult;
	}
	
	/** Used to check if a recipe matches current mixer slots */
	public boolean matches(TileEntityMixer tile) {
		ArrayList<ItemStack> ingredients = new ArrayList<ItemStack>(this.recipeIngredients);
		int ingredientAmount = ingredients.size();
		int j;
		int k = 0;
		
		for(int i = 0; i < ingredientAmount; i++) {
			for(j = 0; j < 3; j++)
				;
			{
				if(ItemStackHelper.getInstance().areItemStacksEqual(tile.inventory[j], (ItemStack)ingredients.get(i))) {
					k++;
				}
			}
		}
		
		if(tile.tanks[0].getFluid().containsFluid(this.starterFluid)) {
			return(k == ingredientAmount);
		}
		return false;
	}
	
	/** Used to check if the forge has enough lava stored */
	public boolean isEnoughFuelInTank(TileEntityMixer tile) {
		return(this.cookTime <= tile.tanks[0].getFluid().amount);
	}
	
	/** Returns the FluidStack that is the result of this recipe */
	public FluidStack getCraftingResult(TileEntityMixer tile) {
		return this.recipeResult.copy();
	}
	
	/** Returns the size of the recipe area */
	public int getRecipeSize() {
		return this.recipeIngredients.size();
	}
}
