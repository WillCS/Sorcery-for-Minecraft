package sorcery.entities;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import sorcery.core.Sorcery;
import sorcery.items.ItemLantern;
import sorcery.items.ItemLanternFuel;
import sorcery.lib.SorceryItems;

public class EntityLantern extends Entity {

	public EntityLantern(World par1World) {
		super(par1World);
		this.setSize(0.4F, 0.8F);
	}

	@Override
	protected void entityInit() {
		this.dataWatcher.addObject(16, new Integer(0));
		this.dataWatcher.addObject(17, new Integer(0));
	}
	
	public boolean isCheaty() {
		return this.dataWatcher.getWatchableObjectInt(17) == 0 ? false : true;
	}
	
	public int getFuel() {
		return this.dataWatcher.getWatchableObjectInt(16);
	}
	
	public boolean hasFuel() {
		return this.getFuel() != 0;
	}
	
	public int consumeFuel(int amount) {
		int fuel = this.getFuel();
		fuel -= amount;
		this.setFuel(fuel);
		return fuel;
	}
	
	public void setFuel(int amount) {
		this.dataWatcher.updateObject(16, new Integer(amount));
	}

	@Override
	public void onEntityUpdate() {
		super.onEntityUpdate();
		this.motionY -= 0.04D;
		float sideMotion = 0.99F;

		if(this.onGround) {
			 Block block = this.worldObj.getBlock(MathHelper.floor_double(this.posX),
					MathHelper.floor_double(this.boundingBox.minY) - 1,
					MathHelper.floor_double(this.posZ));

			if(!block.isAir(this.worldObj, MathHelper.floor_double(this.posX),
					MathHelper.floor_double(this.boundingBox.minY) - 1,
					MathHelper.floor_double(this.posZ))) {
				sideMotion = block.slipperiness * 0.99F;
			}
		}

        this.motionX *= (double)sideMotion;
        this.motionZ *= (double)sideMotion;
        
		this.moveEntity(this.motionX, this.motionY, this.motionZ);
		
		if(!this.hasFuel()) return;
		else if(this.worldObj.rand.nextInt(21) == 0 && !this.isCheaty())
			this.consumeFuel(1);

		int x = (int)this.posX;
		int y = (int)this.posY;
		int z = (int)this.posZ;

		if(this.worldObj.isAirBlock(x, y, z)) {
			this.worldObj.setBlock(x, y, z, Sorcery.airBlock);
			this.worldObj.scheduleBlockUpdate(x, y, z, Sorcery.airBlock, 5);
		} else if(this.worldObj.isAirBlock(x, y + 1, z)) {
			this.worldObj.setBlock(x, y + 1, z, Sorcery.airBlock);
			this.worldObj.scheduleBlockUpdate(x, y + 1, z, Sorcery.airBlock, 5);
		} else if(this.worldObj.isAirBlock(x, y + 2, z)) {
			this.worldObj.setBlock(x, y + 2, z, Sorcery.airBlock);
			this.worldObj.scheduleBlockUpdate(x, y + 2, z, Sorcery.airBlock, 5);
		}
	}

	@Override
	public boolean interactFirst(EntityPlayer par1EntityPlayer) {
		if(this.worldObj.isRemote) return false;
		
		if(par1EntityPlayer.getHeldItem() != null && par1EntityPlayer.getHeldItem().getItem() instanceof ItemLanternFuel) {
			if(this.getFuel() != 1000) {
				this.setFuel(1000);
				par1EntityPlayer.setCurrentItemOrArmor(0, new ItemStack(Items.glass_bottle));
			}
		}
		
		if(par1EntityPlayer.isSneaking() && par1EntityPlayer.getHeldItem() == null) {
			par1EntityPlayer.setCurrentItemOrArmor(0, ((ItemLantern)SorceryItems.lantern).getLanternWithFuel(this.getFuel()));
			this.kill();
			return true;
		}
		return false;
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbttagcompound) {
		this.dataWatcher.updateObject(16, nbttagcompound.getInteger("fuel"));
		this.dataWatcher.updateObject(17, nbttagcompound.getInteger("cheaty"));
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbttagcompound) {
		nbttagcompound.setInteger("fuel", this.getFuel());
		nbttagcompound.setInteger("cheaty", this.isCheaty() == true ? 0 : 1);
	}

	@Override
	public boolean canBeCollidedWith() {
		return true;
	}

	@Override
	public boolean canBePushed() {
		return true;
	}
}
