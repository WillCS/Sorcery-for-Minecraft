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
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import sorcery.lib.SorceryItems;

public class EntityGoldenChicken extends EntityChicken {
	public boolean field_70885_d = false;
	public float field_70886_e = 0.0F;
	public float destPos = 0.0F;
	public float field_70884_g;
	public float field_70888_h;
	public float field_70889_i = 1.0F;
	
	/** The time until the next egg is spawned. */
	public int timeUntilNextEgg;
	
	public EntityGoldenChicken(World par1World) {
		super(par1World);
		this.setSize(0.3F, 0.7F);
		this.timeUntilNextEgg = this.rand.nextInt(6000) + 6000;
		float var2 = 0.25F;
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityAIPanic(this, 0.38F));
		this.tasks.addTask(2, new EntityAIMate(this, var2));
		this.tasks.addTask(3, new EntityAITempt(this, 0.25F, Items.wheat_seeds, false));
		this.tasks.addTask(4, new EntityAIFollowParent(this, 0.28F));
		this.tasks.addTask(5, new EntityAIWander(this, var2));
		this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
		this.tasks.addTask(7, new EntityAILookIdle(this));
	}
	
	/** Returns true if the newer Entity AI code should be run */
	public boolean isAIEnabled() {
		return true;
	}
	
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(4F);
    }
	
	/** Called frequently so the entity can update its state every tick as
	 * required. For example, zombies and skeletons use this to react to
	 * sunlight and start to burn. */
	@Override
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
		
		if(!this.onGround && this.field_70889_i < 1.0F) {
			this.field_70889_i = 1.0F;
		}
		
		this.field_70889_i = (float)((double)this.field_70889_i * 0.9D);
		
		if(!this.onGround && this.motionY < 0.0D) {
			this.motionY *= 0.6D;
		}
		
		this.field_70886_e += this.field_70889_i * 2.0F;
		
		if(!this.isChild() && !this.worldObj.isRemote && --this.timeUntilNextEgg <= 0) {
			this.worldObj.playSoundAtEntity(this, "mob.chickenplop", 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
			this.dropItem(SorceryItems.goldegg, 1);
			this.timeUntilNextEgg = this.rand.nextInt(6000) + 6000;
		}
	}
	
	/** Called when the mob is falling. Calculates and applies fall damage. */
	protected void fall(float par1) {
	}
	
	/** Returns the sound this mob makes while it's alive. */
	protected String getLivingSound() {
		return "mob.chicken.say";
	}
	
	/** Returns the sound this mob makes when it is hurt. */
	protected String getHurtSound() {
		return "mob.chicken.hurt";
	}
	
	/** Returns the sound this mob makes on death. */
	protected String getDeathSound() {
		return "mob.chicken.hurt";
	}
	
	/** Returns the item ID for the item the mob drops on death. */
	protected Item getDropItem() {
		return SorceryItems.goldfeather;
	}
	
	/** Drop 0-2 items of this living's type */
	protected void dropFewItems(boolean par1, int par2) {
		int var3 = this.rand.nextInt(3) + this.rand.nextInt(1 + par2);
		
		for(int var4 = 0; var4 < var3; ++var4) {
			this.dropItem(SorceryItems.goldfeather, 1);
		}
		
		if(this.isBurning()) {
			this.dropItem(Items.cooked_chicken, 1);
		} else {
			this.dropItem(Items.chicken, 1);
		}
	}
	
	/** This function is used when two same-species animals in 'love mode' breed
	 * to generate the new baby animal. */
	public EntityAnimal func_90011_a(EntityAgeable par1EntityAnimal) {
		return new EntityGoldenChicken(this.worldObj);
	}
}
