package sorcery.api.element;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

public abstract class ItemElementStorage extends Item implements IElementProvider {
	public abstract ElementStack[] getElements(ItemStack item);
	
	public abstract ItemStack consume(ItemStack item, ElementStack element);
	
	public abstract ItemStack add(ItemStack item, ElementStack element);
	
	@SuppressWarnings("all")
	public void addInformation(ItemStack item, EntityPlayer player, List list, boolean b) {
		if(this.getElements(item) == null)
			return;
		
		list.add("");
		
		for(int i = 0; i < this.getElements(item).length; i++)
			list.add(EnumChatFormatting.GREEN.toString() + StatCollector.translateToLocalFormatted("sorcery.item.element.info", 
					this.getElements(item)[i].amount, this.getElements(item)[i].element.getLocalizedName()));
	}
	
	@Override
	public boolean isProvider(ItemStack item) {
		return true;
	}
	
	@Override
	public boolean isRechargeable(ItemStack item) {
		return false;
	}
	
	@Override
	public int getCapacity(ItemStack item) {
		return 0;
	}
}
