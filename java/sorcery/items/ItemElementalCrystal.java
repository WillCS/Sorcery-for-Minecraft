package sorcery.items;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import sorcery.api.element.Element;
import sorcery.core.Sorcery;
import sorcery.lib.utils.Utils;

public class ItemElementalCrystal extends ItemArcane {
	public ItemElementalCrystal() {
		this.setCreativeTab(Sorcery.tabSorceryMaterials);
		this.setMaxStackSize(64);
		this.setHasSubtypes(true);
	}
	
    public String getItemStackDisplayName(ItemStack par1ItemStack) {
    	if(par1ItemStack.getItemDamage() == 0)
    		return StatCollector.translateToLocal(this.getUnlocalizedName() + ".blank.name");
    	
        String unlocalized = StatCollector.translateToLocal(this.getUnlocalizedName() + ".name");
    	String elementName = Element.elementsList[par1ItemStack.getItemDamage()].getLocalizedName();
    	
        return String.format(unlocalized, elementName);
    }
	
	@Override
	public int getColorFromItemStack(ItemStack par1ItemStack, int i) {
		if(par1ItemStack.getItemDamage() == 0)
			return(Utils.encodeColour(255, 255, 255));
		return(Element.elementsList[par1ItemStack.getItemDamage()].getIntColour());
	}
	
	public void addCreativeItems(ArrayList itemList) {
		itemList.add(new ItemStack(this, 1, 0));
		for(int i = 1; i < Element.elementsList.length; i++) {
			if(Element.elementsList[i] != null)
				itemList.add(new ItemStack(this, 1, i));
		}
	}
	
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
		par3List.add(new ItemStack(this, 1, 0));
		for(int i = 1; i < Element.elementsList.length; i++) {
			if(Element.elementsList[i] != null)
				par3List.add(new ItemStack(this, 1, i));
		}
	}
}
