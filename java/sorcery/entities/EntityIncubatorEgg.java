package sorcery.entities;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityIncubatorEgg extends EntityAnimal {
	public int eggType;
	
	public EntityIncubatorEgg(World par1World, int eggType) {
		super(par1World);
		this.setSize(0.3F, 1.0F);
		this.eggType = eggType;
		this.isImmuneToFire = true;
	}
	
	public boolean isAIEnabled() {
		return true;
	}
	
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(4F);
    }
	
	public void onLivingUpdate() {
	}
	
	public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeEntityToNBT(par1NBTTagCompound);
	}
	
	public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readEntityFromNBT(par1NBTTagCompound);
	}
	
	public EntityAnimal createChild(EntityAgeable par1EntityAnimal) {
		return new EntityIncubatorEgg(this.worldObj, this.eggType);
	}
}
