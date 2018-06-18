package sorcery.blocks;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import sorcery.api.element.Element;
import sorcery.core.Sorcery;

public class ItemRuneBlock extends ItemBlock {
	public ItemRuneBlock(Block block) {
		super(block);
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
		this.setCreativeTab(CreativeTabs.tabBlock);
	}
	
	public IIcon getIconFromDamage(int par1) {
		return ((Block)Sorcery.runeBlock).getIcon(1, par1);
	}
	
	public int getMetadata(int par1) {
		return par1;
	}
	
	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		return "sorcery.block.runeblock";
	}
	
    public String getItemDisplayName(ItemStack par1ItemStack) {
    	if(par1ItemStack.getItemDamage() == 0)
    		return StatCollector.translateToLocal(this.getUnlocalizedName(par1ItemStack) + ".blank.name");
    	
        String unlocalized = StatCollector.translateToLocal(this.getUnlocalizedName(par1ItemStack));
    	String elementName = Element.elementsList[par1ItemStack.getItemDamage()].getLocalizedName();
    	
        return String.format(unlocalized, elementName);
    }
}
