package sorcery.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import sorcery.core.Sorcery;

public class ItemUnicornMeat extends ItemArcane {
	private boolean alwaysEdible;
	
	public ItemUnicornMeat() {
		this.alwaysEdible = true;
		setMaxStackSize(16);
		this.setCreativeTab(Sorcery.tabSorceryMaterials);
	}
	
	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		return 32;
	}
	
	public ItemStack onFoodEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		int random = par2World.rand.nextInt(9);
		--par1ItemStack.stackSize;
		par2World.playSoundAtEntity(par3EntityPlayer, "random.burp", 0.5F, par2World.rand.nextFloat() * 0.1F + 0.9F);
		if(!par2World.isRemote && random == 0) {
			par3EntityPlayer.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 20 * 20, 0));
		}
		if(!par2World.isRemote && random == 1) {
			par3EntityPlayer.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 20 * 20, 0));
		}
		if(!par2World.isRemote && random == 2) {
			par3EntityPlayer.addPotionEffect(new PotionEffect(Potion.confusion.id, 20 * 20, 0));
		}
		if(!par2World.isRemote && random == 3) {
			par3EntityPlayer.addPotionEffect(new PotionEffect(Potion.hunger.id, 20 * 20, 0));
		}
		if(!par2World.isRemote && random == 4) {
			par3EntityPlayer.addPotionEffect(new PotionEffect(Potion.waterBreathing.id, 20 * 20, 0));
		}
		if(!par2World.isRemote && random == 5) {
			par3EntityPlayer.addPotionEffect(new PotionEffect(Potion.blindness.id, 20 * 20, 0));
		}
		if(!par2World.isRemote && random == 6) {
			par3EntityPlayer.addPotionEffect(new PotionEffect(Potion.heal.id, 1, 0));
		}
		if(!par2World.isRemote && random == 7) {
			par3EntityPlayer.addPotionEffect(new PotionEffect(Potion.harm.id, 1, 0));
		}
		if(!par2World.isRemote && random == 8) {
			par3EntityPlayer.addPotionEffect(new PotionEffect(Potion.resistance.id, 20 * 20, 0));
		} else {
		}
		return par1ItemStack;
	}
	
	public ItemUnicornMeat setAlwaysEdible() {
		this.alwaysEdible = true;
		return this;
	}
	
	public EnumAction getItemUseAction(ItemStack par1ItemStack) {
		return EnumAction.eat;
	}
	
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		if(par3EntityPlayer.canEat(this.alwaysEdible)) {
			par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
		}
		
		return par1ItemStack;
	}
}
