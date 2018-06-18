package sorcery.items;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import sorcery.api.element.Element;
import sorcery.core.Sorcery;
import sorcery.lib.Properties;

public class ItemJewellery extends ItemArcane {
	public ItemJewellery() {
		this.setMaxStackSize(1);
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
		this.setCreativeTab(Sorcery.tabSorceryMaterials);
	}
	
	private IIcon[] IIcons;
	
	public IIcon getIconFromDamage(int i) {
		if(i == 32767) return this.IIcons[1];
		if(this.IIcons[i] != null)
			return this.IIcons[i];
		else
			return this.IIcons[0];
	}
	
	public void registerIcons(IIconRegister par1IIconRegister) {
		this.IIcons = new IIcon[256];
		this.IIcons[0] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "jewelleryRingSilver");
		this.IIcons[1] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "jewelleryRingGold");
		this.IIcons[2] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "jewelleryRingNetherrite");
		this.IIcons[3] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "jewelleryRingInfernite");
	}
	
    public String getItemStackDisplayName(ItemStack par1ItemStack) {
        return StatCollector.translateToLocal(this.getUnlocalizedName() + "." + par1ItemStack.getItemDamage() + ".name");
    }
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
		for(int var4 = 0; var4 < 4; ++var4) {
			par3List.add(new ItemStack(par1, 1, var4));
		}
	}
}
