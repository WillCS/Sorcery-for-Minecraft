package sorcery.tileentities;

import java.util.ArrayList;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import sorcery.api.mojo.IMojoStorage;
import sorcery.api.mojo.MojoBattery;

public class TileEntityMojo extends TileEntitySorcery implements IMojoStorage {
	protected MojoBattery mojo = new MojoBattery(10000);
	
	@Override
	public int getMojo() {
		return this.mojo.getMojo();
	}

	@Override
	public int addMojo(int amount) {
		return this.mojo.addMojo(amount);
	}

	@Override
	public int fill(int amount) {
		return this.mojo.fill(amount);
	}

	@Override
	public int subtractMojo(int amount) {
		return this.mojo.subtractMojo(amount);
	}

	@Override
	public int setMojo(int amount) {
		return this.mojo.setMojo(amount);
	}

	@Override
	public int getCapacity() {
		return this.mojo.getCapacity();
	}

	@Override
	public boolean isFull() {
		if(this.mojo == null)
			return true;
		
		return this.mojo.isFull();
	}

	@Override
	public boolean canReceiveMojo() {
		if(this.mojo == null)
			return false;
		
		return this.mojo.canReceiveMojo();
	}

	@Override
	public boolean canSendMojo() {
		if(this.mojo == null)
			return false;
		
		return this.mojo.canSendMojo();
	}
	
	public boolean isWireInDirection(ForgeDirection dir) {
		TileEntity tile = this.worldObj.getTileEntity(
				this.xCoord + dir.offsetX, this.yCoord + dir.offsetY, this.zCoord + dir.offsetZ);

		return tile instanceof TileEntityMojoWire;
	}

	public TileEntityMojoWire getWireInDirection(ForgeDirection dir) {
		return (TileEntityMojoWire)this.worldObj.getTileEntity(
				this.xCoord + dir.offsetX, this.yCoord + dir.offsetY, this.zCoord + dir.offsetZ);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readFromNBT(par1NBTTagCompound);
		this.mojo = MojoBattery.readFromNBT(par1NBTTagCompound, "mojo");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeToNBT(par1NBTTagCompound);
		if(this.mojo != null)
			this.mojo.writeToNBT(par1NBTTagCompound, "mojo");
	}

	@Override
	public ForgeDirection[] getDirectionsToReceiveFrom() {
		ArrayList<ForgeDirection> ret = new ArrayList<ForgeDirection>();
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			if(ForgeDirection.VALID_DIRECTIONS[this.front] != dir)
				ret.add(dir);
		}
		
		return ret.toArray(new ForgeDirection[0]);
	}
}
