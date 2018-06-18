package sorcery.items;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import sorcery.api.element.Element;
import sorcery.core.Sorcery;

public class ItemElementalCore extends ItemArcane {
	public ItemElementalCore() {
		this.setCreativeTab(Sorcery.tabSorceryMaterials);
		this.setMaxStackSize(1);
		this.setHasSubtypes(true);
	}
	
    public String getItemStackDisplayName(ItemStack par1ItemStack) {
        String unlocalized = StatCollector.translateToLocal(this.getUnlocalizedName() + ".name");
    	String elementName = Element.elementsList[par1ItemStack.getItemDamage()].getLocalizedName();
    	
        return String.format(unlocalized, elementName);
    }
	
	@Override
	public int getColorFromItemStack(ItemStack par1ItemStack, int i) {
		return(Element.elementsList[par1ItemStack.getItemDamage()].getIntColour());
	}
	
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
		for(int i = 1; i < Element.elementsList.length; i++) {
			if(Element.elementsList[i] != null)
				par3List.add(new ItemStack(this, 1, i));
		}
	}
	
	public EnumRarity getRarity(ItemStack par1ItemStack) {
		return EnumRarity.rare;
	}
}
