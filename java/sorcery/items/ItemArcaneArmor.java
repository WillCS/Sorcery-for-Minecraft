package sorcery.items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import sorcery.lib.Properties;
import sorcery.lib.SorceryEnums;
import sorcery.lib.SorceryItems;

public class ItemArcaneArmor extends ItemArmor {
	
	public ArmorMaterial material;
	
	public ItemArcaneArmor(ArmorMaterial enumarmormaterial, int j, int k) {
		super(enumarmormaterial, j, k);
		setMaxStackSize(1);
		this.material = enumarmormaterial;
		this.setCreativeTab(CreativeTabs.tabCombat);
	}
	
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
    
	public String getArmorTexture(ItemStack itemstack, Entity entity, int slot, int layer) {
		if(itemstack.getItem() == SorceryItems.netherritehelmet || itemstack.getItem() == SorceryItems.netherritebody || itemstack.getItem() == SorceryItems.netherriteboots) {
			return Properties.ASSET_PREFIX + Properties.TEXTURE_FOLDER + "armor/netherrite_1.png";
		}
		if(itemstack.getItem() == SorceryItems.netherritelegs) {
			return Properties.ASSET_PREFIX + Properties.TEXTURE_FOLDER + "armor/netherrite_2.png";
		}
		if(itemstack.getItem() == SorceryItems.infernitehelmet || itemstack.getItem() == SorceryItems.infernitebody || itemstack.getItem() == SorceryItems.inferniteboots) {
			return Properties.ASSET_PREFIX + Properties.TEXTURE_FOLDER + "armor/inferno_1.png";
		}
		if(itemstack.getItem() == SorceryItems.infernitelegs) {
			return Properties.ASSET_PREFIX + Properties.TEXTURE_FOLDER + "armor/inferno_2.png";
		}
		return Properties.ASSET_PREFIX + Properties.TEXTURE_FOLDER + "armor/silver_1.png";
	}
	
	public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack) {
		if(this.material.equals(SorceryEnums.armorINFERNITE) && par2ItemStack.getItem() == SorceryItems.ingotInfernite) {
			return true;
		}
		
		if(this.material.equals(SorceryEnums.armorNETHERRITE) && par2ItemStack.getItem() == SorceryItems.ingotNetherrite) {
			return true;
		}
		
		return false;
	}
}
