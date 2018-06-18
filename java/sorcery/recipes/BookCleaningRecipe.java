package sorcery.recipes;

import java.util.List;

import sorcery.lib.SorceryItems;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.world.World;

public class BookCleaningRecipe extends ShapelessRecipes {
	public BookCleaningRecipe(ItemStack par1ItemStack, List par2List) {
		super(par1ItemStack, par2List);
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		for(int i = 0; i < inv.getSizeInventory(); i++) {
			if(inv.getStackInSlot(i) != null) {
				ItemStack j = inv.getStackInSlot(i);
				if(j.getItem() != null && j.getItem() == SorceryItems.dustyBook && j.hasTagCompound()) {
					ItemStack book = new ItemStack(SorceryItems.unknownBook);
					book.stackTagCompound = j.stackTagCompound;
					return book;
				}
			}
		}
		return new ItemStack(SorceryItems.unknownBook);
	}
}
