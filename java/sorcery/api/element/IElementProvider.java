package sorcery.api.element;

import net.minecraft.item.ItemStack;

public interface IElementProvider {
	public abstract ElementStack[] getElements(ItemStack item);
	
	public abstract ItemStack consume(ItemStack item, ElementStack element);
	
	public abstract ItemStack add(ItemStack item, ElementStack element);

	public boolean isProvider(ItemStack item);
	
	public boolean isRechargeable(ItemStack item);
	
	/** Only used by rechargeables */
	public int getCapacity(ItemStack item);
}
