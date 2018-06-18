package sorcery.items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import sorcery.lib.Properties;

public class ItemArcane extends Item {
	
	public void registerIcons(IIconRegister par1IIconRegister) {
		String newName = this.getUnlocalizedName().replace("sorcery.item.", "");
		this.itemIcon = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + newName);
	}
	
    public String getUnlocalizedName() {
        return "sorcery." + super.getUnlocalizedName();
    }
    
    public String getUnlocalizedName(ItemStack par1ItemStack) {
    	return "sorcery." + super.getUnlocalizedName(par1ItemStack);
    }
}
