package sorcery.items;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import sorcery.core.Sorcery;
import sorcery.lib.ItemStackHelper;
import sorcery.lib.SorceryItems;

public class ItemPowerRing extends ItemArcane {
	public ItemPowerRing() {
		this.setCreativeTab(Sorcery.tabSorceryTools);
		this.setMaxStackSize(1);
	}
	
	public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) {
		if(par3Entity instanceof EntityPlayer) {
			for(int i = 0; i < 9; i++) {
				if(((EntityPlayer)par3Entity).inventory.mainInventory[i] != null && ((EntityPlayer)par3Entity).inventory.mainInventory[i].getItem() == par1ItemStack.getItem()) {
					if(par1ItemStack.getItem() == SorceryItems.oxygenRing)
						par3Entity.setAir(300);
					else if(par1ItemStack.getItem() == SorceryItems.hoarderRing)
						this.collectItems((EntityPlayer)par3Entity);
				}
			}
		}
	}
	
	public void collectItems(EntityPlayer player) {
		AxisAlignedBB box = AxisAlignedBB.getBoundingBox(player.posX - 3, player.posY - 3, player.posZ - 3, player.posX + 3, player.posY + 3, player.posZ + 3);
		List<Entity> items = player.worldObj.getEntitiesWithinAABB(EntityItem.class, box);
		
		if(items.size() > 0) {
			for(int i = 0; i < items.size(); i++) {
				if(!player.worldObj.isRemote)
					ItemStackHelper.instance.tryToFillInvWithItem(player.inventory.mainInventory, ((EntityItem)items.get(i)).getEntityItem());
			}
		}
		
		List<Entity> orbs = player.worldObj.getEntitiesWithinAABB(EntityXPOrb.class, box);
		
		if(orbs.size() > 0) {
			for(int i = 0; i < orbs.size(); i++) {
				((EntityXPOrb)orbs.get(i)).setPosition(player.posX, player.posY, player.posZ);
			}
		}
	}
	
	public EnumRarity getRarity(ItemStack item) {
		return EnumRarity.uncommon;
	}
	
}
