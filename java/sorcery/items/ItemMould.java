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

public class ItemMould extends ItemArcane {
	
	public ItemMould() {
		setCreativeTab(Sorcery.tabSorceryMaterials);
		setHasSubtypes(true);
	}
	
	private IIcon[] IIcons;
	
	public static String[] mouldNames = new String[]{"blank", "cog", "key", "lock", "keyring", "ring", "amulet", "necklace", "crossbowLimbs", "orb", "ingot", "plate"};

	@Override
    public String getItemStackDisplayName(ItemStack par1ItemStack) {
		if(par1ItemStack.getItemDamage() == 0)
    		return StatCollector.translateToLocal(this.getUnlocalizedName() + ".blank.name");
		
        String unlocalized = StatCollector.translateToLocal(this.getUnlocalizedName() + ".name");
    	String mouldName = StatCollector.translateToLocal("sorcery.mould." + mouldNames[par1ItemStack.getItemDamage()] + ".name");
    	
        return String.format(unlocalized, mouldName);
    }
	
	
	public IIcon getIconFromDamage(int i) {
		if(this.IIcons[i] != null)
			return this.IIcons[i];
		else
			return this.IIcons[0];
	}
	
	public void registerIcons(IIconRegister par1IIconRegister) {
		String name = this.getUnlocalizedName().replace("sorcery.item.", "");
		this.IIcons = new IIcon[256];
		if(!name.equals("cast"))
			this.IIcons[0] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + name + "Blank");
		this.IIcons[1] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + name + "Cog");
		//this.IIcons[2] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + name + "Key");
		//this.IIcons[3] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + name + "Lock");
		//this.IIcons[4] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + name + "Keyring");
		this.IIcons[5] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + name + "Ring");
		//this.IIcons[6] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + name + "Amulet");
		//this.IIcons[7] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + name + "Necklace");
		//this.IIcons[8] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + name + "CrossbowLimbs");
		this.IIcons[9] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + name + "Orb");
		this.IIcons[10] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + name + "Ingot");
		this.IIcons[11] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + name + "Plate");
	}
	
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
		for(int var4 = 0; var4 < 12; ++var4) {
			if(this.IIcons[var4] != null)
				par3List.add(new ItemStack(par1, 1, var4));
		}
	}
}
