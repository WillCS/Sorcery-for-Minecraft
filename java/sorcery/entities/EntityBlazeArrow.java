package sorcery.entities;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import sorcery.lib.SorceryItems;

public class EntityBlazeArrow extends EntityArrow {
	private boolean inGround;
	public int canBePickedUp;
	public int arrowShake;
	private int ticksInGround;

	public EntityBlazeArrow(World par1World) {
		super(par1World);
	}
	
	public EntityBlazeArrow(World par1World, double par2, double par4, double par6) {
		super(par1World, par2, par4, par6);
	}
	
	public EntityBlazeArrow(World par1World, EntityLivingBase par2EntityLivingBase, EntityLivingBase par3EntityLivingBase, float par4, float par5) {
		super(par1World, par2EntityLivingBase, par3EntityLivingBase, par4, par5);
	}
	
	public EntityBlazeArrow(World par1World, EntityLivingBase par2EntityLivingBase, float par3) {
		super(par1World, par2EntityLivingBase, par3);
	}

	public void onCollideWithPlayer(EntityPlayer par1EntityPlayer) {
		if(!this.worldObj.isRemote && this.inGround && this.arrowShake <= 0) {
			boolean flag = this.canBePickedUp == 1 || this.canBePickedUp == 2 && par1EntityPlayer.capabilities.isCreativeMode;
			
			if(this.canBePickedUp == 1 && !par1EntityPlayer.inventory.addItemStackToInventory(new ItemStack(SorceryItems.blazearrow, 1))) {
				flag = false;
			}
			
			if(flag) {
				this.playSound("random.pop", 0.2F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
				par1EntityPlayer.onItemPickup(this, 1);
				this.setDead();
			}
		}
	}
}
