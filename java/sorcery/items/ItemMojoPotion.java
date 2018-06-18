package sorcery.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import sorcery.api.Item3DosePotion;
import sorcery.core.Sorcery;
import sorcery.lib.SpellHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemMojoPotion extends Item3DosePotion {
	public ItemMojoPotion() {
		this.setCreativeTab(Sorcery.tabSorcerySpellcasting);
	}
	
	public EnumRarity getRarity(ItemStack item) {
		return EnumRarity.uncommon;
	}
	
	public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		SpellHelper.instance.setPlayerMojo(par3EntityPlayer, SpellHelper.instance.getPlayerMojo(par3EntityPlayer) - 100);
		if(SpellHelper.instance.getPlayerMojo(par3EntityPlayer) < 0)
			SpellHelper.instance.setPlayerMojo(par3EntityPlayer, 0);
		return super.onEaten(par1ItemStack, par2World, par3EntityPlayer);
	}
	
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack par1ItemStack) {
		return true;
	}
	
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		if(SpellHelper.instance.getPlayerMojo(par3EntityPlayer) != 0)
			return super.onItemRightClick(par1ItemStack, par2World, par3EntityPlayer);
		
		return par1ItemStack;
	}
	
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack par1ItemStack, int par2) {
		return par2 == 1 ? super.getColorFromItemStack(par1ItemStack, par2) : (232 << 16) + (13 << 8) + 199;
	}
	
    public String getUnlocalizedName() {
        return "sorcery." + super.getUnlocalizedName();
    }
    
    public String getUnlocalizedName(ItemStack par1ItemStack) {
    	return "sorcery." + super.getUnlocalizedName(par1ItemStack);
    }
}
