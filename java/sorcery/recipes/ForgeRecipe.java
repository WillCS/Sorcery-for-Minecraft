package sorcery.recipes;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import sorcery.lib.ItemStackHelper;
import sorcery.tileentities.TileEntityForge;

public class ForgeRecipe {
	/** Is the ItemStack that you get when craft the recipe. */
	public final ItemStack recipeResult;
	
	/** Is a List of ItemStacks that composes the recipe. */
	public final List<ItemStack> recipeIngredients;
	
	/** Is the item that must be in the mould slot for the recipe to cook */
	public final ItemStack mould;
	
	/** Is how many ticks the recipe must cook for */
	public final int burnTime;
	
	/** The temperature needed for this recipe */
	public final int temperature;
	
	public ForgeRecipe(ItemStack mould, ItemStack result, int temperature, int burnTime, List<ItemStack> ingredients) {
		this.mould = mould;
		this.recipeResult = result;
		this.temperature = temperature;
		this.burnTime = burnTime;
		this.recipeIngredients = ingredients;
	}
	
	public ItemStack getRecipeResult() {
		return this.recipeResult;
	}
	
	/** Used to check if a recipe matches current forge slots */
	public boolean matches(TileEntityForge tile) {
		ArrayList<ItemStack> ingredients = new ArrayList<ItemStack>(this.recipeIngredients);
		int ingredientAmount = ingredients.size();
		int j;
		int k = 0;
		
		for(int i = 0; i < ingredientAmount; i++) {
			for(j = 0; j < 4; j++)
				;
			{
				if(ItemStackHelper.getInstance().areItemStacksEqual(tile.inventory[j], (ItemStack)ingredients.get(i))) {
					k++;
				}
			}
		}
		
		if(ItemStackHelper.getInstance().areItemStacksEqual(tile.inventory[5], this.mould)) {
			return(k == ingredientAmount);
		}
		return false;
	}
	
	/** Used to check if the forge has enough lava stored */
	public boolean isEnoughFuelInTank(TileEntityForge tile) {
		return(this.burnTime <= tile.getTankAmount(0));
	}
	
	/** Returns an Item that is the result of this recipe */
	public ItemStack getCraftingResult(TileEntityForge tile) {
		return this.recipeResult.copy();
	}
	
	/** Returns the size of the recipe area */
	public int getRecipeSize() {
		return this.recipeIngredients.size();
	}
}
