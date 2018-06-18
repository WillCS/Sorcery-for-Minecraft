package sorcery.tileentities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import sorcery.entities.EntityGoldenChicken;
import sorcery.entities.EntityPhoenix;
import sorcery.lib.Properties;
import sorcery.lib.SorceryItems;

public class TileEntityIncubator extends TileEntitySorcery {
	/** The time until the stored egg hatches */
	public int eggTime;
	
	/** The type of egg stored 1 = Chicken 2 = Golden Chicken 3 = Phoenix */
	private int eggID;
	
	public double yaw;
	public double yaw2 = 0.0D;
	private int minSpawnDelay = 200;
	private int maxSpawnDelay = 800;
	private int spawnCount = 4;
	private Entity spawnedMob;
	
	public TileEntityIncubator() {
		this.eggTime = 0;
		this.eggID = 0;
	}
	
	public int getMobID() {
		return this.eggID;
	}
	
	public void setEggID(int id) {
		this.eggID = id;
	}
	
	/** Called when the Incubator is clicked on */
	
	public void addEggToIncubator(int egg) {
		this.setEggID(egg);
		this.eggTime = 1;
	}
	
	public void updateEntity() {
		super.updateEntity();
		if(this.getMobID() != 0 && this.getEggTime() != 0) {
			this.updateEggTime();
			
			if(this.getEggTime() >= 12000) {
				this.hatchEgg();
				this.eggTime = 0;
				this.setEggID(0);
			}
		}
		super.updateEntity();
	}

	private void updateEggTime() {
		this.eggTime++;
	}
	
	private int getEggTime() {
		return this.eggTime;
	}
	
	private void hatchEgg() {
		if(!worldObj.isRemote) {
			EntityLiving entity = this.getEntityFromID(this.eggID);
			this.spawnedMob = entity;
			int x = this.xCoord;
			int y = this.yCoord;
			int z = this.zCoord;
			((EntityAnimal)entity).setGrowingAge(-24000);
			entity.setPosition(x + 0.5, y + 0.5, z + 0.5);
			worldObj.spawnEntityInWorld((Entity)entity);
			entity.spawnExplosionParticle();
			worldObj.playSoundAtEntity(entity, Properties.ASSET_PREFIX + "egg.crack", 1F, 0.9F);
			worldObj.markBlockForUpdate(x, y, z);
		}
	}
	
	/** Reads a tile entity from NBT. */
	public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readFromNBT(par1NBTTagCompound);
		this.eggID = par1NBTTagCompound.getInteger("EggID");
		this.eggTime = par1NBTTagCompound.getShort("EggTime");

		if(par1NBTTagCompound.hasKey("MinSpawnDelay")) {
			this.minSpawnDelay = par1NBTTagCompound.getShort("MinSpawnDelay");
			this.maxSpawnDelay = par1NBTTagCompound.getShort("MaxSpawnDelay");
			this.spawnCount = par1NBTTagCompound.getShort("SpawnCount");
		}
	}
	
	/** Writes a tile entity to NBT. */
	public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setInteger("EggID", this.eggID);
		par1NBTTagCompound.setShort("EggTime", (short)this.eggTime);
		par1NBTTagCompound.setShort("MinSpawnDelay", (short)this.minSpawnDelay);
		par1NBTTagCompound.setShort("MaxSpawnDelay", (short)this.maxSpawnDelay);
		par1NBTTagCompound.setShort("SpawnCount", (short)this.spawnCount);
	}
	
	public EntityLiving getEntityFromID(int id) {
		switch(id) {
			case 2:
				return new EntityGoldenChicken(this.worldObj);
			case 3:
				return new EntityPhoenix(this.worldObj);
			default:
				return new EntityChicken(this.worldObj);
		}
	}
	
	public Item getEggFromID(int id) {
		switch(id) {
			case 2:
				return SorceryItems.goldegg;
			case 3:
				return SorceryItems.phoenixegg;
			default:
				return Items.egg;
		}
	}
}
