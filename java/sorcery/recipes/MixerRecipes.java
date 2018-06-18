package sorcery.recipes;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import org.apache.logging.log4j.Level;

import sorcery.api.recipe.IMixerRecipes;
import sorcery.lib.ItemStackHelper;
import sorcery.lib.SorceryItems;
import sorcery.lib.utils.Utils;
import sorcery.tileentities.TileEntityMixer;

public class MixerRecipes implements IMixerRecipes {
	private static final MixerRecipes instance = new MixerRecipes();
	
	private List<MixerRecipe> recipeList = new ArrayList<MixerRecipe>();
	
	private int BUCKET_VOLUME = FluidContainerRegistry.BUCKET_VOLUME;
	
	/** Used to call methods addSmelting and getSmeltingResult. */
	public static final MixerRecipes getInstance() {
		return instance;
	}
	
	public void addMixerRecipes() {
		this.addRecipe(new FluidStack(FluidRegistry.getFluid("water"), BUCKET_VOLUME * 1),
			new FluidStack(FluidRegistry.getFluid("mojo"), BUCKET_VOLUME * 1), 75 * 20, new ItemStack[]{
				new ItemStack(SorceryItems.magicDust, 4),
				new ItemStack(Items.nether_wart, 4)});
		
		this.addRecipe(new FluidStack(FluidRegistry.getFluid("milk"), BUCKET_VOLUME),
			new FluidStack(FluidRegistry.getFluid("chocmilk"), BUCKET_VOLUME), 30 * 20, new ItemStack[]{
					new ItemStack(Items.sugar, 4), new ItemStack(Items.dye, 4, 3)});
		
		this.addRecipe(new FluidStack(FluidRegistry.getFluid("milk"), BUCKET_VOLUME),
			new FluidStack(FluidRegistry.getFluid("antidote"), BUCKET_VOLUME), 60 * 20, new ItemStack[]{
				new ItemStack(Blocks.brown_mushroom, 3),
				new ItemStack(Items.nether_wart, 3),
				new ItemStack(Items.redstone, 8),
				new ItemStack(SorceryItems.magicDust, 4)});
		
		Utils.log(Level.INFO, recipeList.size() + " Mixer Recipes");
	}
	
	public List<MixerRecipe> getRecipeList() {
		return this.recipeList;
	}
	
	/** Add a recipe for use in the Mixer Block
	 * 
	 * @param starterLiquid
	 *            FluidStack that must be present in the first tank
	 * @param result
	 *            FluidStack that is produced as a result of this recipe
	 * @param cookTime
	 *            How many ticks it takes for this recipe to cook
	 * @param ingredients
	 *            Up to 4 ItemStacks that are used as ingredients for this
	 *            recipe */
	public void addRecipe(FluidStack starterLiquid, FluidStack result, int cookTime, ItemStack... ingredients) {
		ArrayList<ItemStack> list = new ArrayList<ItemStack>();
		ItemStack[] ingredientItems = ingredients;
		int ingredientAmount = ingredients.length;
		
		for(int i = 0; i < ingredientAmount; i++) {
			ItemStack ingredientToAdd = ingredientItems[i];
			list.add(ingredientToAdd);
		}
		
		this.recipeList.add(new MixerRecipe(starterLiquid, result, cookTime, list));
	}
	
	public MixerRecipe findMatchingRecipe(TileEntityMixer tile) {
		try {
			int j = 0;
			
			for(int i = 0; i < 4; i++) {
				if(tile.inventory[i] != null) {
					j++;
				}
			}
			
			if(j == tile.howManyItemsInCookingSlots() && j != 0) {
				int k;
				int l;
				// int ab;
				int m = 0;
				
				for(int i = 0; i < this.recipeList.size(); i++) {
					for(l = 0; l < this.recipeList.get(i).recipeIngredients.size(); l++) {
						if(ItemStackHelper.instance.doesInventoryContainItemStackInRange(tile.inventory, (ItemStack)(((MixerRecipe)this.recipeList.get(i)).recipeIngredients.get(l)), 0, 4)) {
							m++;
						}
						/*
						 * if(tile.inventory[k] != null &&
						 * ItemStackHelper.getInstance
						 * ().areItemsEqual((tile.inventory[k]),
						 * (ItemStack)this.
						 * recipeList.get(i).recipeIngredients.get(l))) { m++; }
						 * else if(tile.inventory[k] != null &&
						 * ItemStackHelper.instance
						 * .areOreItemStacksEqual((tile.inventory[k]),
						 * (ItemStack
						 * )this.recipeList.get(i).recipeIngredients.get(l))) {
						 * m++; }
						 */
					}
					if(m == this.recipeList.get(i).recipeIngredients.size()) {
						if(tile.tanks[0].getFluid() != null && tile.tanks[0].getFluid().containsFluid(this.recipeList.get(i).starterFluid)) {
							return this.recipeList.get(i);
						}
					} else {
						m = 0;
					}
				}
			}
			return null;
		} catch(Exception exception) {
			exception.printStackTrace();
		}
		return null;
	}
	
	public boolean isMatchingRecipeAvailable(TileEntityMixer tile) {
		MixerRecipe recipe = this.findMatchingRecipe(tile);
		
		return(recipe != null);
	}
}
