package sorcery.recipes;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import sorcery.api.element.Element;
import sorcery.api.element.ElementStack;
import sorcery.api.element.ItemElementStorage;
import sorcery.tileentities.TileEntityInfusionStand;

public class InfuserChargingRecipe extends InfuserRecipe {
	private ItemElementStorage itemReference;
	
	public InfuserChargingRecipe(ItemElementStorage chargeItem) {
		super(new ItemStack[] {new ItemStack(chargeItem)}, new ElementStack[0], new ItemStack(chargeItem));
		this.itemReference = chargeItem;
	}

	public boolean matches(TileEntityInfusionStand tile, World world) {
		if(tile.inventory[0] == null)
			return false;
		
		if(tile.inventory[0].getItem().equals(itemReference)) {
			int amountFound = 0;
			for(ElementStack charge : itemReference.getElements(tile.inventory[0])) {
				for(ElementStack element : tile.elements) {
					if(element.element == charge.element) {
						amountFound++;
					}
				}
			}
			
			if(amountFound == 0)
				return false;
		}
		return true;
	}

	public ItemStack getCraftingResult(TileEntityInfusionStand tile) {
		ArrayList<Element> stacks = new ArrayList<Element>();
		if(tile.inventory[0].getItem().equals(itemReference)) {
			for(ElementStack charge : itemReference.getElements(tile.inventory[0])) {
				for(ElementStack element : tile.elements) {
					if(element.element == charge.element) {
						boolean found = false;
						for(Element e : stacks) {
							if(e == element.element)
								found = true;
						}
						
						if(!found)
							stacks.add(element.element);
					}
				}
			}
			
			if(stacks.isEmpty())
				return tile.inventory[0];
			
			ItemStack ret = tile.inventory[0].copy();
			
			for(ElementStack element : itemReference.getElements(tile.inventory[0])) {
				if(stacks.contains(element.element) && element.amount < itemReference.getCapacity(tile.inventory[0])) {
					ret = itemReference.add(ret, new ElementStack(element.element, 1));
				}
			}
			
			return ret;
		}
		
		return tile.inventory[0];
	}

	public int getRecipeSize() {
		return this.ingredients.length;
	}

	public ItemStack getRecipeOutput() {
		return this.result;
	}
}
