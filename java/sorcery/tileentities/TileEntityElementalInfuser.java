package sorcery.tileentities;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import sorcery.api.IInfusable;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityElementalInfuser extends TileEntityMojo {
	public int pitch = 0;
	public int yaw = 0;
	public int spin = 0;
	public int[] targetCoords = null;
	
	public boolean turning;
	
	public TileEntityElementalInfuser() {
		this.pitch = 90;
	}
	
	@Override
	public void updateEntity() {
		if(this.turning && this.getTarget() != null && this.getTarget() instanceof IInfusable)
			this.turnTo((int)this.getPitchTo(this.targetCoords[0], this.targetCoords[1], this.targetCoords[2]),
					(int)this.getYawTo(this.targetCoords[0], this.targetCoords[2]));
		
		if(this.getTarget() == null && this.pitch != 90) {
			this.turning = true;
			this.turnTo(90, this.yaw);
		}
	}
	
	public TileEntity getTarget() {
		if(this.targetCoords == null)
			return null;
		
		return this.worldObj.getTileEntity(this.targetCoords[0], this.targetCoords[1], this.targetCoords[2]);
	}
	
	public void turnTo(int pitch, int yaw) {
		if(yaw != this.yaw) {
			int amt = yaw - this.yaw;
			int dir = amt/Math.abs(amt);
			
			if(Math.abs(amt) > 180)
				dir *= -1;
			
			if(Math.abs(amt) > 10)
				dir *= 2;
			
			this.yaw += dir;
			
			if(this.yaw > 360)
				this.yaw -= 360;
			if(this.yaw < 0)
				this.yaw += 360;
		}
		
		if(pitch != this.pitch) {
			int amt = pitch - this.pitch;
			int dir = amt/Math.abs(amt);
			this.pitch += dir;
		}
		
		if(this.pitch == pitch && this.yaw == yaw)
			this.turning = false;
	}
	
	public double getYawTo(double x, double z) {
		double deltaX = x - this.xCoord;
		double deltaZ = z - this.zCoord;
		return 180 + Math.atan2(deltaZ, deltaX) * 180 / Math.PI;
	}
	
	public double getPitchTo(double x, double y, double z) {
		double deltaX = Math.sqrt(Math.pow(x - this.xCoord, 2) + Math.pow(z - this.zCoord, 2));
		float yOffset = ((IInfusable)this.getTarget()).getInfusionCenter();
		double deltaY = y - (double)(this.yCoord) + 1.5 + yOffset;
		return -Math.atan2(deltaY, deltaX) * 180 / Math.PI;
	}

	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readFromNBT(par1NBTTagCompound);
		this.pitch = par1NBTTagCompound.getInteger("pitch");
		this.yaw = par1NBTTagCompound.getInteger("yaw");
		this.spin = par1NBTTagCompound.getInteger("spin");
		this.turning = par1NBTTagCompound.getBoolean("turning");
		this.targetCoords = new int[3];
		this.targetCoords[0] = par1NBTTagCompound.getInteger("targetX");
		this.targetCoords[1] = par1NBTTagCompound.getInteger("targetY");
		this.targetCoords[2] = par1NBTTagCompound.getInteger("targetZ");
		
		if(this.targetCoords[0] == 0 && this.targetCoords[1] ==0 && this.targetCoords[2] == 0)
			this.targetCoords = null;
			
	}
	
	@Override
	public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setInteger("pitch", this.pitch);
		par1NBTTagCompound.setInteger("yaw", this.yaw);
		par1NBTTagCompound.setInteger("spin", this.spin);
		par1NBTTagCompound.setBoolean("turning", this.turning);
		if(this.targetCoords != null) {
			par1NBTTagCompound.setInteger("targetX", this.targetCoords[0]);
			par1NBTTagCompound.setInteger("targetY", this.targetCoords[1]);
			par1NBTTagCompound.setInteger("targetZ", this.targetCoords[2]);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getRenderBoundingBox() {
		return AxisAlignedBB.getBoundingBox(
				this.xCoord - 3, this.yCoord - 3, this.zCoord - 3, this.xCoord + 3, this.yCoord + 3, this.zCoord + 3);
	}

	@Override
	public boolean onWrenched(World world, EntityPlayer player, ItemStack item, int x, int y, int z, int side) {
		if(item.hasTagCompound()) {
			if(item.stackTagCompound.hasKey("storedPos")) {
				int x2 = item.stackTagCompound.getCompoundTag("storedPos").getInteger("x");
				int y2 = item.stackTagCompound.getCompoundTag("storedPos").getInteger("y");
				int z2 = item.stackTagCompound.getCompoundTag("storedPos").getInteger("z");
				
				if(world.getTileEntity(x2, y2, z2) != null && world.getTileEntity(x2, y2, z2) instanceof IInfusable) {
					double distance = Math.sqrt(Math.pow(Math.abs(this.xCoord - x2), 2) +
							Math.pow(Math.abs(this.yCoord - y2), 2) +
							Math.pow(Math.abs(this.zCoord - z2), 2));
					
					if(distance >= 5 && distance < 10 && this.yCoord > y2 + 2) {
						this.targetCoords = new int[3];
						this.targetCoords[0] = x2;
						this.targetCoords[1] = y2;
						this.targetCoords[2] = z2;
						this.turning = true;
						return true;
					}
				}
			}
		}
		return false;
	}
}
