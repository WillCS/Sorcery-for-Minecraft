package sorcery.items;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import sorcery.core.Sorcery;
import sorcery.lib.Properties;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemColourTag extends ItemArcane {
	public ItemColourTag() {
		
		this.setCreativeTab(Sorcery.tabSorceryMaterials);
		this.setMaxStackSize(1);
		this.setHasSubtypes(true);
	}
	
	public IIcon[] IIcons;
	
	public void registerIcons(IIconRegister par1IIconRegister) {
		this.IIcons = new IIcon[2];
		this.IIcons[0] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "colourTag");
		this.IIcons[1] = par1IIconRegister.registerIcon("paper");
	}
	
    public String getItemStackDisplayName(ItemStack par1ItemStack) {
        String unlocalized = StatCollector.translateToLocal(this.getUnlocalizedName() + ".name");
    	String colourName = StatCollector.translateToLocal("sorcery.colour." + Properties.COLOURS[par1ItemStack.getItemDamage()] + ".name");
    	
        return String.format(unlocalized, colourName);
    }
	
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return true;
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamageForRenderPass(int par1, int par2) {
		return par2 == 1 ? this.IIcons[0] : this.IIcons[1];
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public int getColorFromItemStack(ItemStack par1ItemStack, int par2) {
		if(par2 > 0) {
			return ItemDye.field_150922_c[par1ItemStack.getItemDamage()];
			
		} else {
			return 16777215;
		}
	}
	
	public void addCreativeItems(ArrayList itemList) {
		for(int x = 0; x < 16; x++) {
			itemList.add(new ItemStack(this, 1, x));
		}
	}
	
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
		for(int var4 = 0; var4 < 16; ++var4) {
			par3List.add(new ItemStack(par1, 1, var4));
		}
	}
}
