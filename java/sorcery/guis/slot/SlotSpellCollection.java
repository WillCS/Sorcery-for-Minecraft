package sorcery.guis.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import sorcery.api.ISpellCollection;

public class SlotSpellCollection extends Slot {
	
	public SlotSpellCollection(IInventory par1iInventory, int par2, int par3, int par4) {
		super(par1iInventory, par2, par3, par4);
	}
	
	@Override
	public boolean isItemValid(ItemStack par1ItemStack) {
		if(par1ItemStack.getItem() instanceof ISpellCollection)
			return !this.getHasStack();
		
		return false;
	}
}
