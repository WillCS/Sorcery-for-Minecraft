package sorcery.items;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import sorcery.core.Sorcery;
import sorcery.lib.Properties;

public class ItemMachineHull extends ItemArcane {
	
	public ItemMachineHull() {
		this.setCreativeTab(Sorcery.tabSorceryTech);
		setHasSubtypes(true);
	}

	public String getItemStackDisplayName(ItemStack par1ItemStack) {
		return StatCollector.translateToLocal(this.getUnlocalizedName() + "." + par1ItemStack.getItemDamage() + ".name");
	}
	
	@Override
	public IIcon getIconFromDamage(int i) {
		return Blocks.iron_block.getBlockTextureFromSide(0);
	}
	
	public void registerIcons(IIconRegister par1IIconRegister) {
	}

	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
		for(int var4 = 0; var4 < 4; ++var4) {
			par3List.add(new ItemStack(par1, 1, var4));
		}
	}
}
