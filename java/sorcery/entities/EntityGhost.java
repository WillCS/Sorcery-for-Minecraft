package sorcery.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import sorcery.lib.Properties;
import sorcery.lib.SorceryItems;

public class EntityGhost extends EntityMob {
	public EntityGhost(World par1World) {
		super(par1World);
		this.isImmuneToFire = true;
		this.experienceValue = 5;
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, 0.25F, false));
		this.tasks.addTask(4, new EntityAIMoveTowardsRestriction(this, 0.25F));
		this.tasks.addTask(6, new EntityAIWander(this, 0.25F));
		this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(7, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
	}
	
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(20.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.4D);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4.0D);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30F);
    }
	
	protected void fall(float par1) {
	}
	
	/** Returns true if the newer Entity AI code should be run */
	protected boolean isAIEnabled() {
		return true;
	}
	
	/** Called frequently so the entity can update its state every tick as
	 * required. For example, zombies and skeletons use this to react to
	 * sunlight and start to burn. */
	public void onLivingUpdate() {
		super.onLivingUpdate();
	}
	
	public boolean attackEntityAsMob(Entity entity) {
		if(super.attackEntityAsMob(entity)) {
			if(entity instanceof EntityLiving) {
				byte length = 0;
				
				if(this.worldObj.difficultySetting.getDifficultyId() > 1) {
					if(this.worldObj.difficultySetting.getDifficultyId() == 2) {
						length = 7;
					} else if(this.worldObj.difficultySetting.getDifficultyId() == 3) {
						length = 15;
					}
				}
				
				if(length > 0) {
					((EntityLiving)entity).addPotionEffect(new PotionEffect(Potion.confusion.id, length * 20, 0));
				}
			}
			return true;
		} else {
			return false;
		}
	}
	
	/** Returns the sound this mob makes while it's alive. */
	protected String getLivingSound() {
		return Properties.ASSET_PREFIX + "shade.idle";
	}
	
	/** Returns the sound this mob makes when it is hurt. */
	protected String getHurtSound() {
		return Properties.ASSET_PREFIX + "shade.hurt";
	}
	
	/** Returns the sound this mob makes on death. */
	protected String getDeathSound() {
		return Properties.ASSET_PREFIX + "shade.death";
	}
	
	/** Returns the item ID for the item the mob drops on death. */
	protected Item getDropItem() {
		return SorceryItems.ectoplasm;
	}
	
	/** Get this Entity's EnumCreatureAttribute */
	public EnumCreatureAttribute getCreatureAttribute() {
		return EnumCreatureAttribute.UNDEAD;
	}
	
	protected void dropRareDrop(int par1) {
		switch(this.rand.nextInt(2)) {
			case 0:
				this.dropItem(SorceryItems.magicDust, 4);
				break;
			case 1:
				this.dropItem(SorceryItems.cog, 1);
		}
	}
}
