package sorcery.api.element;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public abstract class ItemArmorElementStorage extends ItemArmor implements IElementProvider {
	public ItemArmorElementStorage(ItemArmor.ArmorMaterial material, int renderType, int armorType) {
		super(material, renderType, armorType);
	}
	
	public abstract ElementStack[] getElements(ItemStack item);
	
	public ItemStack consume(ItemStack item, int amount) {
		item.stackSize -= amount;
		
		return item;
	}
	
	public ItemStack consume(ItemStack item) {
		return this.consume(item, 1);
	}
	
	@SuppressWarnings("all")
	public void addInformation(ItemStack item, EntityPlayer player, List list, boolean b) {
		if(this.getElements(item) == null)
			return;
		
		if(this.getElements(item).length != 1)
			list.add("Contains:");
		
		for(int i = 0; i < this.getElements(item).length; i++)
			list.add(this.getElements(item)[i].amount + "x " + this.getElements(item)[i].element.getLocalizedName());
	}
}
