package sorcery.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import sorcery.core.Sorcery;
import sorcery.lib.Properties;

public class ItemMagicBucket extends ItemArcane {
	public ItemMagicBucket() {
		this.setHasSubtypes(true);
		this.setCreativeTab(Sorcery.tabSorceryMaterials);
	}
	
	private IIcon[] IIcons;
	
	 public String getItemStackDisplayName(ItemStack par1ItemStack) {
	        String unlocalized = StatCollector.translateToLocal(this.getUnlocalizedName() + ".name");
	    	String fluidName = StatCollector.translateToLocal("fluid." + Properties.FLUIDS[par1ItemStack.getItemDamage()] + ".name");
	    	
	        return String.format(unlocalized, fluidName);
	    }
	 
	@Override
	public IIcon getIconFromDamage(int meta) {
		if(this.IIcons[meta] != null)
			return this.IIcons[meta];
		
		return this.IIcons[0];
	}
	
	public void registerIcons(IIconRegister par1IIconRegister) {
		this.IIcons = new IIcon[16];
		this.IIcons[0] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "bucketMojo");
		this.IIcons[2] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "bucketChocolateMilk");
		this.IIcons[3] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "bucketAntidote");
		this.IIcons[4] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "bucketGlowstone");
	}
	
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
		for(int i = 0; i < 5; i++) {
			if(this.IIcons[i] != null)
				par3List.add(new ItemStack(par1, 1, i));
		}
	}
}
