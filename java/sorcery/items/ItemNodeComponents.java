package sorcery.items;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import sorcery.core.Sorcery;
import sorcery.lib.Properties;

public class ItemNodeComponents extends ItemArcane {
	public ItemNodeComponents() {
		this.setMaxStackSize(64);
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
		this.setCreativeTab(Sorcery.tabSorceryMaterials);
	}
	
	private IIcon[] IIcons;
	
	public IIcon getIconFromDamage(int i) {
		if(this.IIcons[i] != null)
			return this.IIcons[i];
		else
			return this.IIcons[0];
	}
	
	public void registerIcons(IIconRegister par1IIconRegister) {
		String node = "node_";
		String module = "module_";
		
		this.IIcons = new IIcon[16];
		this.IIcons[0] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + node + "stand");
		this.IIcons[1] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + node + "laser");
		this.IIcons[2] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + node + "focus");
		// this.IIcons[3] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX
		// + node + "crystal");
		
		this.IIcons[8] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + node + module + "collector");
		this.IIcons[9] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + node + module + "buffer");
	}
	
	@Override
    public String getItemStackDisplayName(ItemStack par1ItemStack) {
        return StatCollector.translateToLocal(this.getUnlocalizedName() + "." + par1ItemStack.getItemDamage() + ".name");
    }
	
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
		for(int var4 = 0; var4 < 16; ++var4) {
			if(this.IIcons[var4] != null)
				par3List.add(new ItemStack(par1, 1, var4));
		}
	}
}
