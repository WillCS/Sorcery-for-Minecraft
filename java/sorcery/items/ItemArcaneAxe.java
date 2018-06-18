package sorcery.items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import sorcery.lib.Properties;
import sorcery.lib.SorceryEnums;
import sorcery.lib.SorceryItems;

public class ItemArcaneAxe extends ItemAxe {
	
	public ItemArcaneAxe(ToolMaterial enumtoolmaterial) {
		super(enumtoolmaterial);
		this.setCreativeTab(CreativeTabs.tabTools);
	}
	
    public String getUnlocalizedName() {
        return "sorcery." + super.getUnlocalizedName();
    }
    
    public String getUnlocalizedName(ItemStack par1ItemStack) {
    	return "sorcery." + super.getUnlocalizedName(par1ItemStack);
    }
	
	public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack) {
		if(this.toolMaterial.equals(SorceryEnums.toolINFERNITE) && par2ItemStack.getItem() == SorceryItems.ingotInfernite) {
			return true;
		}
		
		if(this.toolMaterial.equals(SorceryEnums.toolNETHERRITE) && par2ItemStack.getItem() == SorceryItems.ingotNetherrite) {
			return true;
		}
		
		return false;
	}
	
	public void registerIcons(IIconRegister par1IIconRegister) {
		String newName = this.getUnlocalizedName().replace("sorcery.item.", "");
		this.itemIcon = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + newName);
	}
}
