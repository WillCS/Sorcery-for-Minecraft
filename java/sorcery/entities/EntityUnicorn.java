package sorcery.entities;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSaddle;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import sorcery.lib.SorceryItems;

public class EntityUnicorn extends EntityAnimal {
	public EntityUnicorn(World par1World) {
		super(par1World);
		this.setSize(1.3F, 1.7F);
		this.getNavigator().setAvoidsWater(true);
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityAIPanic(this, 0.38F));
		this.tasks.addTask(2, new EntityAIMate(this, 0.2F));
		this.tasks.addTask(3, new EntityAITempt(this, 0.25F, Items.golden_apple, false));
		this.tasks.addTask(4, new EntityAIFollowParent(this, 0.25F));
		this.tasks.addTask(5, new EntityAIWander(this, 0.2F));
		this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
		this.tasks.addTask(7, new EntityAILookIdle(this));
	}
	
	protected void entityInit() {
		super.entityInit();
		this.dataWatcher.addObject(16, Byte.valueOf((byte)0));
	}
	
	@Override
	public boolean interact(EntityPlayer par1EntityPlayer) {
		if(super.interact(par1EntityPlayer)) {
			return true;
		} else if(par1EntityPlayer.getHeldItem() != null && par1EntityPlayer.getHeldItem().getItem() instanceof ItemSaddle && !this.isChild() && !this.getSaddled()) {
			this.setSaddled(true);
			if(!par1EntityPlayer.capabilities.isCreativeMode)
				--par1EntityPlayer.getHeldItem().stackSize;
		} else if(this.getSaddled() && !this.worldObj.isRemote && (this.riddenByEntity == null || this.riddenByEntity == par1EntityPlayer)) {
			par1EntityPlayer.mountEntity(this);
			return true;
		}
		return false;
	}
	
	public boolean isAIEnabled() {
		return true;
	}
	
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20F);
    }
	
	public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeEntityToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setBoolean("Saddle", this.getSaddled());
	}
	
	public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readEntityFromNBT(par1NBTTagCompound);
		this.setSaddled(par1NBTTagCompound.getBoolean("Saddle"));
	}
	
	protected void playStepSound(int par1, int par2, int par3, int par4) {
		this.playSound("mob.cow.step", 0.15F, 1.0F);
	}
	
	protected String getLivingSound() {
		return "mob.horse.idle";
	}
	
	protected String getHurtSound() {
		return "mob.horse.hit";
	}
	
	protected String getDeathSound() {
		return "mob.horse.death";
	}
	
	protected float getSoundVolume() {
		return 0.2F;
	}
	
	public boolean getSaddled() {
		return (this.dataWatcher.getWatchableObjectByte(16) & 1) != 0;
	}
	
	public void setSaddled(boolean par1) {
		if(par1) {
			this.dataWatcher.updateObject(16, Byte.valueOf((byte)1));
		} else {
			this.dataWatcher.updateObject(16, Byte.valueOf((byte)0));
		}
	}
	
	protected Item getDropItem() {
		return SorceryItems.unicornhorn;
	}
	
	protected void dropFewItems(boolean par1, int par2) {
		int var3 = this.rand.nextInt(3) + this.rand.nextInt(1 + par2);
		int var4;
		
		for(var4 = 0; var4 < var3; ++var4) {
			this.dropItem(SorceryItems.unicornhair, 1);
		}
		this.dropItem(SorceryItems.unicornhorn, 1);
		
		if(this.getSaddled()) {
			this.dropItem(Items.saddle, 1);
		}
	}
	
	protected void dropRareDrop(int par1) {
		if(par1 > 0) {
			this.dropItem(SorceryItems.unicornmeat, 1);
		}
	}
	
	public EntityAnimal createChild(EntityAgeable par1EntityAnimal) {
		return new EntityUnicorn(this.worldObj);
	}
	
	@Override
	public boolean isBreedingItem(ItemStack par1ItemStack) {
		return par1ItemStack != null && par1ItemStack .getItem() == Items.golden_apple;
	}
}
