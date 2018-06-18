package sorcery.guis.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import sorcery.api.ISpellBook;
import sorcery.api.ISpellCollection;
import sorcery.items.ItemSpellbook;
import sorcery.lib.SorceryItems;
import sorcery.tileentities.TileEntityDesk;

public class SlotSpellbook extends Slot {
	TileEntityDesk tile;
	
	public SlotSpellbook(IInventory par1iInventory, int par2, int par3, int par4, TileEntityDesk tile) {
		super(par1iInventory, par2, par3, par4);
		this.tile = tile;
	}
	
	@Override
	public boolean isItemValid(ItemStack par1ItemStack) {
		if(this.getHasStack())
			return false;
		
		if(par1ItemStack.getItem() instanceof ISpellBook && par1ItemStack.getItem() instanceof ISpellCollection) {
			if(tile.getStackInSlot(3) != null)
				return !((ItemSpellbook)SorceryItems.spellbook).hasColour(par1ItemStack);
			else return true;
		}
		
		return true;
	}
}
