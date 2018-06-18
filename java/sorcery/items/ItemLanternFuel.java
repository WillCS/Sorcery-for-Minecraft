package sorcery.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import sorcery.core.Sorcery;
import sorcery.lib.SorceryItems;
import sorcery.tileentities.TileEntityLantern;

public class ItemLanternFuel extends ItemArcane {
	public ItemLanternFuel() {
		this.setCreativeTab(Sorcery.tabSorceryTools);
		this.setMaxStackSize(1);
	}
	
	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		return 50;
	}
	
	public EnumAction getItemUseAction(ItemStack par1ItemStack) {
		return EnumAction.block;
	}
	
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		if(par3EntityPlayer.inventory.hasItem(SorceryItems.lantern)) {
			ItemStack lantern = null;
			for(int i = 0; i < par3EntityPlayer.inventory.mainInventory.length; i++) {
				if(par3EntityPlayer.inventory.mainInventory[i] != null ) {
					if(par3EntityPlayer.inventory.mainInventory[i].getItem() instanceof ItemLantern) {
						lantern = par3EntityPlayer.inventory.mainInventory[i];
						if(((ItemLantern)SorceryItems.lantern).getFuel(par1ItemStack) != 1000) {
							par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
							return par1ItemStack;
						}
					}
				}
			}
		}
		return par1ItemStack;
	}
	
	@Override
	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
		if(par3World.getTileEntity(par4, par5, par6) != null && par3World.getTileEntity(par4, par5, par6) instanceof TileEntityLantern) {
			TileEntityLantern lantern = (TileEntityLantern)par3World.getTileEntity(par4, par5, par6);
			if(lantern.hasFuel() || par3World.isRemote) {
				return false;
			} else {
				lantern.fuel = 1000;
				par2EntityPlayer.setCurrentItemOrArmor(1, new ItemStack(Items.glass_bottle));
			}
		}
		return false;
	}

	public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5){
		if(par3Entity instanceof EntityPlayer && par5) {
			EntityPlayer player = (EntityPlayer)par3Entity;
			if(player.isUsingItem() && player.getItemInUse().equals(par1ItemStack)) {
				if(player.getItemInUseDuration() >= 40 && player.inventory.hasItem(SorceryItems.lantern)) {
					player.stopUsingItem();
					if(!player.capabilities.isCreativeMode)
						player.setCurrentItemOrArmor(0, new ItemStack(Items.glass_bottle));
					
					for(int i = 0; i < player.inventory.mainInventory.length; i++) {
						if(player.inventory.mainInventory[i] != null && player.inventory.mainInventory[i].getItem() instanceof ItemLantern) {
							if(((ItemLantern)SorceryItems.lantern).getFuel(player.inventory.mainInventory[i]) != 1000) {
								((ItemLantern)SorceryItems.lantern).fill(player.inventory.mainInventory[i],
										new FluidStack(FluidRegistry.getFluid("glowstone"), 1000), true);
								return;
							}
						}
					}
					
				}
			}
		}
	}
	
}
