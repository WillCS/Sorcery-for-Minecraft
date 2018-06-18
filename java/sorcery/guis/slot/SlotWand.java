package sorcery.guis.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import sorcery.lib.SorceryItems;
import sorcery.tileentities.TileEntityDesk;

public class SlotWand extends Slot {
	TileEntityDesk tile;
	
	public SlotWand(IInventory par1iInventory, int par2, int par3, int par4, TileEntityDesk tile) {
		super(par1iInventory, par2, par3, par4);
		this.tile = tile;
	}
	
	@Override
	public boolean isItemValid(ItemStack par1ItemStack) {
		if(this.getHasStack())
			return false;
		
		if(par1ItemStack.getItem() == SorceryItems.wand)
			return(tile.getStackInSlot(1) == null && tile.getStackInSlot(2) == null && tile.getStackInSlot(3) == null);
		
		return true;
	}
}
