package sorcery.blocks;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import sorcery.core.Sorcery;

public class ItemBlockMojoMachine extends ItemBlock {
	public ItemBlockMojoMachine(Block block) {
		super(block);
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
		this.setCreativeTab(CreativeTabs.tabDecorations);
	}
	
	public IIcon getIconFromDamage(int par1) {
		return ((Block)Sorcery.machine).getIcon(1, par1);
	}
	
	public int getMetadata(int par1) {
		return par1;
	}
	
	public String getUnlocalizedName(ItemStack par1ItemStack) {
		return "sorcery.block.mojoMachine." + par1ItemStack.getItemDamage();
	}
	
}
