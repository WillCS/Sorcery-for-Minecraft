package sorcery.blocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import sorcery.core.Sorcery;
import sorcery.lib.utils.Utils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemMagicLeaves extends ItemBlock {
	public ItemMagicLeaves(Block block) {
		super(block);
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
	}
	
	/** Returns the metadata of the block which this Item (ItemBlock) can place */
	public int getMetadata(int par1) {
		return par1 + 4;
	}
	
	@SideOnly(Side.CLIENT)
	/**
	 * Gets an IIcon index based on an item's damage value
	 */
	public IIcon getIconFromDamage(int par1) {
		return Sorcery.magicLeaves.getIcon(0, par1);
	}
	
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack par1ItemStack, int par2) {
		int j = par1ItemStack.getItemDamage();
		switch(j) {
			case 0:
				return Utils.encodeColour(59, 47, 141, 255);
			case 1:
				return Utils.encodeColour(59, 47, 141, 255);
			case 2:
				return Utils.encodeColour(59, 47, 141, 255);
			case 3:
				return Utils.encodeColour(59, 47, 141, 255);
		}
		
		return 0;
	}
	
	public String getUnlocalizedName(ItemStack par1ItemStack) {
		return "sorcery.block.sparkleLeaves." + par1ItemStack.getItemDamage();
	}
}