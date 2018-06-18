package sorcery.guis.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import sorcery.api.ISpellBook;
import sorcery.items.ItemSpellTome;
import sorcery.lib.SorceryItems;
import sorcery.tileentities.TileEntityDesk;

public class SlotSpellbookColour extends Slot {
	public TileEntityDesk tile;
	
	public SlotSpellbookColour(IInventory par1iInventory, int par2, int par3, int par4, TileEntityDesk tile) {
		super(par1iInventory, par2, par3, par4);
		this.tile = tile;
	}
	
	@Override
	public boolean isItemValid(ItemStack par1ItemStack) {
		if(this.tile.getStackInSlot(0) == null)
			return false;
		
		if(!(this.tile.getStackInSlot(0).getItem() instanceof ISpellBook))
			return false;
		
		if(this.tile.getStackInSlot(0).getItem() instanceof ItemSpellTome)
			return false;
		
		if(par1ItemStack.getItem() == SorceryItems.colourTag)
			return true;
		
		return false;
	}
}
