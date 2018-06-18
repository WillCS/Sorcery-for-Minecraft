package sorcery.guis.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SlotExclusiveItem extends Slot {
	public Item item;
	
	public SlotExclusiveItem(IInventory par1iInventory, int par2, int par3, int par4, Item colourTag) {
		super(par1iInventory, par2, par3, par4);
		this.item = colourTag;
	}
	
	@Override
	public boolean isItemValid(ItemStack par1ItemStack) {
		return par1ItemStack.getItem() == this.item;
	}
}
