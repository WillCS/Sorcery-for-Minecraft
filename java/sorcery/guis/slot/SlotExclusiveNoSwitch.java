package sorcery.guis.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SlotExclusiveNoSwitch extends Slot {
	public Item item;
	
	public SlotExclusiveNoSwitch(IInventory par1iInventory, int par2, int par3, int par4, Item colourTag) {
		super(par1iInventory, par2, par3, par4);
		this.item = colourTag;
	}
	
	@Override
	public boolean isItemValid(ItemStack par1ItemStack) {
		if(this.item == par1ItemStack.getItem())
			return !this.getHasStack();
		
		return false;
	}
}
