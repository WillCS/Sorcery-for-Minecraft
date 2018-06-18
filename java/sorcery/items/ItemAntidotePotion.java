package sorcery.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import sorcery.api.Item3DosePotion;
import sorcery.core.Sorcery;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemAntidotePotion extends Item3DosePotion {
	public ItemAntidotePotion() {
		this.setCreativeTab(Sorcery.tabSorceryTools);
	}
	
	public EnumRarity getRarity(ItemStack item) {
		return EnumRarity.uncommon;
	}
	
	public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		for(int i = 0; i < par3EntityPlayer.getActivePotionEffects().size(); i++) {
			PotionEffect potion = (PotionEffect)(par3EntityPlayer.getActivePotionEffects().toArray()[i]);
			if(potion != null && Potion.potionTypes[potion.getPotionID()].isBadEffect()) {
				par3EntityPlayer.removePotionEffect(potion.getPotionID());
			}
		}
		return super.onEaten(par1ItemStack, par2World, par3EntityPlayer);
	}
	
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack par1ItemStack) {
		return true;
	}
	
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		return super.onItemRightClick(par1ItemStack, par2World, par3EntityPlayer);
	}
	
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack par1ItemStack, int par2) {
		return par2 == 1 ? super.getColorFromItemStack(par1ItemStack, par2) : (210 << 16) + (152 << 8) + 47;
	}
	
    public String getUnlocalizedName() {
        return "sorcery." + super.getUnlocalizedName();
    }
    
    public String getUnlocalizedName(ItemStack par1ItemStack) {
    	return "sorcery." + super.getUnlocalizedName(par1ItemStack);
    }
}
