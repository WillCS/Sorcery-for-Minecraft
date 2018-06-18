package sorcery.recipes;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import sorcery.api.element.ElementStack;
import sorcery.lib.ItemStackHelper;
import sorcery.tileentities.TileEntityInfusionStand;

public class InfuserRecipe {
	public final ItemStack[] ingredients;
	public final ItemStack result;
	public final ElementStack[] elements;

	public InfuserRecipe(ItemStack[] ingredients, ElementStack[] elements, ItemStack result) {
		this.elements = elements;
		this.result = result;
		this.ingredients = ingredients;
	}

	public boolean matches(TileEntityInfusionStand tile, World world) {
		if(!ItemStackHelper.instance.areItemStacksEqualFull(tile.inventory[0], this.ingredients[0]))
				return false;
		
		for(ItemStack item : this.ingredients) {
			if(!ItemStackHelper.instance.doesInventoryContainItemStackFull(tile.inventory, item))
				return false;
		}
		
		for(ElementStack element : this.elements) {
			int amountFound = 0;
			for(ElementStack invElement : tile.elements) {
				if(invElement.element == element.element) {
					amountFound += invElement.amount;
				}
			}
			
			if(amountFound < element.amount)
				return false;
		}
		return true;
	}

	public ItemStack getCraftingResult(TileEntityInfusionStand tile) {
		return this.result;
	}

	public int getRecipeSize() {
		return this.ingredients.length;
	}

	public ItemStack getRecipeOutput() {
		return this.result;
	}
}
