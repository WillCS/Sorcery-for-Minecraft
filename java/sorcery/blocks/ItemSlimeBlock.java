package sorcery.blocks;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import sorcery.core.Sorcery;
import sorcery.lib.Properties;

public class ItemSlimeBlock extends ItemBlock {
	public ItemSlimeBlock(Block block) {
		super(block);
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
		this.setCreativeTab(CreativeTabs.tabBlock);
	}
	
	public IIcon getIconFromDamage(int par1) {
		return ((Block)Sorcery.slimeBlock).getIcon(1, par1);
	}
	
	public int getMetadata(int par1) {
		return par1;
	}
	
	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		return "sorcery.block.slimeblock";
	}
	
    public String getItemDisplayName(ItemStack par1ItemStack) {
        String unlocalized = StatCollector.translateToLocal(this.getUnlocalizedName(par1ItemStack) + ".name");
    	String colourName = StatCollector.translateToLocal("sorcery.colour." + Properties.COLOURS[15 - par1ItemStack.getItemDamage()] + ".name");
    	
        return String.format(unlocalized, colourName);
    }
}
