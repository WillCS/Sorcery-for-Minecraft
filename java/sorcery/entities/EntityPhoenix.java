package sorcery.entities;

import java.util.ArrayList;

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
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import sorcery.lib.Properties;
import sorcery.lib.SorceryItems;

public class EntityPhoenix extends EntityAnimal implements IShearable {
	public boolean field_753_a = false;
	public float destPos = 0.0F;
	
	public float field_70886_e;
	public float field_70884_g;
	public float field_70888_h;
	public float field_70889_i = 1.0F;
	
	/** The time until the next egg is spawned. */
	public int timeUntilNextEgg;
	
	public int featherGrowBackTime;
	
	public int flyingCoolDownTimer;
	
	public EntityPhoenix(World par1World) {
		super(par1World);
		if(this.isChild())
			this.setSize(0.6F, 1.5F);
		else
			this.setSize(0.3F, 1.0F);
		this.timeUntilNextEgg = this.rand.nextInt(20 * 60 * 10) + 20 * 60 * 5;
		float var2 = 0.25F;
		this.getNavigator().setAvoidsWater(true);
		this.getNavigator().setCanSwim(true);
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityAIPanic(this, 0.38F));
		this.tasks.addTask(2, new EntityAIMate(this, var2));
		this.tasks.addTask(3, new EntityAITempt(this, 0.25F, Items.wheat_seeds, false));
		this.tasks.addTask(4, new EntityAIFollowParent(this, 0.28F));
		this.tasks.addTask(5, new EntityAIWander(this, var2));
		this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
		this.tasks.addTask(7, new EntityAILookIdle(this));
		this.isImmuneToFire = true;
	}
	
	/** Returns true if the newer Entity AI code should be run */
	public boolean isAIEnabled() {
		return true;
	}
	
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10F);
    }
	
	protected void entityInit() {
		super.entityInit();
		this.dataWatcher.addObject(16, new Integer(0));
	}
	
	/** Called frequently so the entity can update its state every tick as
	 * required. For example, zombies and skeletons use this to react to
	 * sunlight and start to burn. */
	public void onLivingUpdate() {
		super.onLivingUpdate();
		this.field_70888_h = this.field_70886_e;
		this.field_70884_g = this.destPos;
		this.destPos = (float)((double)this.destPos + (double)(this.onGround ? -1 : 4) * 0.3D);
		
		if(this.destPos < 0.0F) {
			this.destPos = 0.0F;
		}
		
		if(this.destPos > 1.0F) {
			this.destPos = 1.0F;
		}
		
		if (!this.onGround && this.field_70889_i < 1.0F)
        {
            this.field_70889_i = 1.0F;
        }

        this.field_70889_i = (float)((double)this.field_70889_i * 0.9D);
		
		if(!this.onGround && this.motionY < 0.0D) {
			this.motionY *= 0.6D;
		}
		
		this.field_70886_e += this.field_70889_i * 2.0F;
		
		if(!this.isChild() && !this.worldObj.isRemote && --this.timeUntilNextEgg <= 0) {
			this.worldObj.playSoundAtEntity(this, "mob.chicken.plop", 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
			this.dropItem(SorceryItems.phoenixegg, 1);
			this.timeUntilNextEgg = this.rand.nextInt(6000) + 6000;
		}
		
		if(this.getSheared()) {
			this.featherGrowBackTime++;
		}
		
		if(this.featherGrowBackTime >= (20 * 60 * 10)) {
			this.featherGrowBackTime = 0;
			this.setSheared(false);
		}
	}
	
	/** Called when the mob is falling. Calculates and applies fall damage. */
	protected void fall(float par1) {
	}
	
	/** (abstract) Protected helper method to write subclass entity data to NBT. */
	public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeEntityToNBT(par1NBTTagCompound);
		// epar1NBTTagCompound.setInteger("eggTime", this.timeUntilNextEgg);
		par1NBTTagCompound.setInteger("featherTime", this.featherGrowBackTime);
		par1NBTTagCompound.setInteger("coolDown", this.flyingCoolDownTimer);
		par1NBTTagCompound.setBoolean("isSheared", this.getSheared());
	}
	
	/** (abstract) Protected helper method to read subclass entity data from NBT. */
	public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readEntityFromNBT(par1NBTTagCompound);
		// this.timeUntilNextEgg = par1NBTTagCompound.getInteger("EggTime");
		this.featherGrowBackTime = par1NBTTagCompound.getInteger("featherTime");
		this.flyingCoolDownTimer = par1NBTTagCompound.getInteger("coolDown");
		this.setSheared(par1NBTTagCompound.getBoolean("isSheared"));
	}
	
	/** Returns the sound this mob makes while it's alive. */
	protected String getLivingSound() {
		return Properties.ASSET_PREFIX + "phoenix.idle";
	}
	
	/** Returns the sound this mob makes when it is hurt. */
	protected String getHurtSound() {
		return Properties.ASSET_PREFIX + "phoenix.hurt";
	}
	
	/** Returns the sound this mob makes on death. */
	protected String getDeathSound() {
		return Properties.ASSET_PREFIX + "phoenix.hurt";
	}
	
	protected float getSoundVolume() {
		return 0.5F;
	}
	
	public boolean isBreedingItem(ItemStack par1ItemStack) {
		return par1ItemStack != null && par1ItemStack.getItem() == Items.wheat_seeds;
	}
	
	/** Returns the item ID for the item the mob drops on death. */
	protected Item getDropItem() {
		return Items.feather;
	}
	
	public boolean interact(EntityPlayer par1EntityPlayer) {
		if(super.interact(par1EntityPlayer)) {
			return true;
		} else if(!this.worldObj.isRemote && (par1EntityPlayer.riddenByEntity == null || par1EntityPlayer.riddenByEntity == this) && !(par1EntityPlayer.getHeldItem() != null && par1EntityPlayer.getHeldItem().getItem() instanceof ItemShears)) {
			if(this.isRiding()) {
				this.ridingEntity.riddenByEntity = null;
				this.ridingEntity = null;
				this.setPositionAndRotation(par1EntityPlayer.posX, par1EntityPlayer.posY, par1EntityPlayer.posZ, par1EntityPlayer.rotationPitch, par1EntityPlayer.rotationYaw);
			} else {
				this.ridingEntity = par1EntityPlayer;
				par1EntityPlayer.riddenByEntity = this;
			}
			return true;
		} else {
			return false;
		}
	}
	
	/** Drop 0-2 items of this living's type */
	protected void dropFewItems(boolean par1, int par2) {
		int var3 = this.rand.nextInt(3) + this.rand.nextInt(1 + par2);
		
		for(int var4 = 0; var4 < var3; ++var4) {
			this.dropItem(SorceryItems.phoenixfeather, 1);
		}
		this.dropItem(SorceryItems.phoenixtear, 1);
		this.dropItem(SorceryItems.phoenixdust, 1);
	}
	
	/** This function is used when two same-species animals in 'love mode' breed
	 * to generate the new baby animal. */
	public EntityAnimal createChild(EntityAgeable par1EntityAnimal) {
		if(!this.worldObj.isRemote)
			this.dropItem(SorceryItems.phoenixegg, this.rand.nextInt(3) + 1);
			
		return null;
	}
	
	public boolean getSheared() {
		return(this.dataWatcher.getWatchableObjectInt(16) == 0 ? false : true);
	}
	
	public void setSheared(boolean par1) {
		if(par1) {
			this.dataWatcher.updateObject(16, new Integer(1));
		} else {
			this.dataWatcher.updateObject(16, new Integer(0));
		}
	}
	
	@Override
	public boolean isShearable(ItemStack item, IBlockAccess world, int x, int y, int z) {
		return !this.getSheared();
	}
	
	@Override
	public ArrayList<ItemStack> onSheared(ItemStack item, IBlockAccess world, int x, int y, int z, int fortune) {
		ArrayList<ItemStack> items = new ArrayList<ItemStack>();
		this.setSheared(true);
		this.worldObj.playSoundAtEntity(this, "mob.sheep.shear", 1.0F, 1.0F);
		int i = 1 + rand.nextInt(3);
		for(int j = 0; j < i; j++) {
			items.add(new ItemStack(SorceryItems.phoenixfeather, 1));
		}
		return items;
	}
}
