package sorcery.recipes;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

import org.apache.logging.log4j.Level;

import sorcery.api.element.ElementStack;
import sorcery.api.element.ItemElementStorage;
import sorcery.api.recipe.IInfuserRecipes;
import sorcery.lib.utils.Utils;

/** Dummy Class for use with NEI */
public class InfuserRecipes implements IInfuserRecipes {
	private static final InfuserRecipes instance = new InfuserRecipes();
	
	private List<InfuserRecipe> recipeList = new ArrayList<InfuserRecipe>();
	
	/** Used to call methods addSmelting and getSmeltingResult. */
	public static final InfuserRecipes getInstance() {
		return instance;
	}
	
	public void addInfuserRecipes() {
		Utils.log(Level.INFO, recipeList.size() + " Elemental Infuser Recipes");
	}
	
	public List<InfuserRecipe> getRecipeList() {
		return this.recipeList;
	}
	
	public void addRecipe(ItemStack[] ingredients, ElementStack[] elements, ItemStack result) {
		this.recipeList.add(new InfuserRecipe(ingredients, elements, result));
	}
	
	public void addChargeableItem(ItemElementStorage chargeItem) {
		this.recipeList.add(new InfuserChargingRecipe(chargeItem));
	}
}
