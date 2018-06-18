package sorcery.items;

import sorcery.core.Sorcery;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemChocolateMilk extends ItemArcane {
	public ItemChocolateMilk() {
		this.setCreativeTab(Sorcery.tabSorceryMaterials);
		this.setMaxStackSize(1);
	}
	
	public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		par3EntityPlayer.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 20 * 20, 0));
		par3EntityPlayer.addPotionEffect(new PotionEffect(Potion.jump.id, 20 * 20, 0));
		par3EntityPlayer.heal(2);
		
		if(par3EntityPlayer.capabilities.isCreativeMode)
			return par1ItemStack;
		
		return new ItemStack(Items.glass_bottle);
	}
	
	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		return 32;
	}
	
	public EnumAction getItemUseAction(ItemStack par1ItemStack) {
		return EnumAction.drink;
	}
	
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
		
		return par1ItemStack;
	}
}
