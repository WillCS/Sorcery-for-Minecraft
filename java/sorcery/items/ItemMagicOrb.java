package sorcery.items;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import sorcery.api.element.Element;
import sorcery.api.element.ElementStack;
import sorcery.core.Sorcery;

public class ItemMagicOrb extends ItemArcane {
	public ItemMagicOrb() {
		this.setCreativeTab(Sorcery.tabSorceryMaterials);
		this.setMaxStackSize(1);
		this.setMaxDamage(1000);
	}
	
    public String getItemStackDisplayName(ItemStack par1ItemStack) {
    	int id = par1ItemStack.getTagCompound().getInteger("element");
        String unlocalized = StatCollector.translateToLocal(this.getUnlocalizedName() + ".name");
    	String elementName = Element.elementsList[id].getLocalizedName();
    	
        return String.format(unlocalized, elementName);
    }

	/*@Override
	public int getDisplayDamage(ItemStack stack) {
		return 1000 - (int)this.getDurabilityForDisplay(stack);
	}
    
	@Override
    public boolean showDurabilityBar(ItemStack stack) {
		return this.getDurabilityForDisplay(stack) != 0;
    }
	
	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		if(!stack.hasTagCompound()) {
			stack.stackTagCompound = new NBTTagCompound();
			stack.stackTagCompound.setInteger("amount", 1);
		}
		
		int capacity = 1000;
		int amount = stack.stackTagCompound.getInteger("amount");
		
		double percentage = (double)amount / (double)capacity;
		double scaledAmount = 1000 - (percentage * 1000);
		System.out.println(scaledAmount);
		return scaledAmount;
	}*/
    
    public ItemStack getElementalItem(ElementStack element) {
    	ItemStack ret = new ItemStack(this, 1, 1000 - element.amount);
    	ret.stackTagCompound = new NBTTagCompound();
    	ret.getTagCompound().setInteger("element", element.getID());
    	
    	return ret;
    }
	
	@Override
	public EnumRarity getRarity(ItemStack item) {
		return EnumRarity.uncommon;
	}
	
	@Override
	public int getColorFromItemStack(ItemStack par1ItemStack, int i) {
		if(!par1ItemStack.hasTagCompound())
			par1ItemStack.stackTagCompound = new NBTTagCompound();
    	int id = par1ItemStack.getTagCompound().getInteger("element");
    	
		if(id == 32767)
			return Element.water.getIntColour();
		
		if(Element.elementsList[id] != null)
			return(Element.elementsList[id].getIntColour());
		
		return 0;
	}
	
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
		for(Element element : Element.elementsList) {
			if(element != null)
				par3List.add(this.getElementalItem(new ElementStack(element, 1000)));
		}
	}
}
