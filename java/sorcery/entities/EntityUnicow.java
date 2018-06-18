package sorcery.entities;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import sorcery.lib.SorceryItems;

public class EntityUnicow extends EntityCow {
	public EntityUnicow(World par1World) {
		super(par1World);
	}
	
	/** Returns the item ID for the item the mob drops on death. */
	protected Item getDropItem() {
		return Items.leather;
	}
	
	/** Drop 0-2 items of this living's type. @param par1 - Whether this entity
	 * has recently been hit by a player. @param par2 - Level of Looting used to
	 * kill this mob. */
	protected void dropFewItems(boolean par1, int par2) {
		int j = this.rand.nextInt(3) + this.rand.nextInt(1 + par2);
		int k;
		
		for(k = 0; k < j; ++k) {
			this.dropItem(Items.leather, 1);
		}
		
		j = this.rand.nextInt(2) + this.rand.nextInt(1 + par2);
		
		for(k = 0; k < j; ++k) {
			this.dropItem(SorceryItems.unicornhair, 1);
		}
		
		this.dropItem(SorceryItems.unicornhorn, 1);
		
		j = this.rand.nextInt(3) + 1 + this.rand.nextInt(1 + par2);
		
		for(k = 0; k < j; ++k) {
			if(this.isBurning()) {
				this.dropItem(Items.cooked_beef, 1);
			} else {
				this.dropItem(Items.beef, 1);
			}
		}
	}
	
	public EntityCow createChild(EntityAgeable par1EntityAgeable) {
		return new EntityUnicow(this.worldObj);
	}
}
