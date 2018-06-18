package sorcery.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import sorcery.lib.Properties;

public class ItemCog extends ItemArcane {
	
	public ItemCog() {
		
		setCreativeTab(CreativeTabs.tabMaterials);
		setHasSubtypes(true);
	}
	
	private IIcon[] IIcons;
	
	@Override
	public IIcon getIconFromDamage(int i) {
		if(this.IIcons[i] != null)
			return this.IIcons[i];
		else
			return this.IIcons[0];
	}
	
	public String getItemStackDisplayName(ItemStack par1ItemStack) {
		return StatCollector.translateToLocal(this.getUnlocalizedName() + "." + Properties.COG_TYPES[par1ItemStack.getItemDamage()] + ".name");
	}

	public void registerIcons(IIconRegister par1IIconRegister) {
		this.IIcons = new IIcon[256];
		this.IIcons[0] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "cogIron");
		this.IIcons[1] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "cogGolden");
		this.IIcons[2] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "cogNetherrite");
		this.IIcons[3] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "cogInfernite");
		this.IIcons[4] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "cogWooden");
		this.IIcons[5] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "cogBrass");
		this.IIcons[6] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "cogSteel");
	}
	
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
		for(int var4 = 0; var4 < 7; ++var4) {
			par3List.add(new ItemStack(par1, 1, var4));
		}
	}
}
