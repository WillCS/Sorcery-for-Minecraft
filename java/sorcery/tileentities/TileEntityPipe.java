package sorcery.tileentities;

import java.util.ArrayList;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.IFluidHandler;
import sorcery.fluid.Pipe;
import sorcery.lib.Vector;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityPipe extends TileEntitySorcery {
	public Pipe pipe;
	public int colour;
	public Vector pressure;

	public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readFromNBT(par1NBTTagCompound);
		this.pipe = Pipe.readFromNBT(par1NBTTagCompound.getCompoundTag("pipe"));
		this.colour = par1NBTTagCompound.getInteger("colour");
		this.pressure = Vector.readFromNBT(par1NBTTagCompound, "pressure");
	}
	
	public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeToNBT(par1NBTTagCompound);
		NBTTagCompound fluid = new NBTTagCompound();
		par1NBTTagCompound.setInteger("colour", this.colour);
		
		if(this.pipe != null) {
			NBTTagCompound pipeTag = new NBTTagCompound();
			this.pipe.writeToNBT(pipeTag);
			par1NBTTagCompound.setTag("pipe", pipeTag);
		}
		
		if(this.pressure != null)
			this.pressure.writeToNBT(par1NBTTagCompound, "pressure");
	}
	
	public boolean hasFluid() {
		return this.pipe.fluid.amount != 0;
	}
	
	public float getPressure() {
		return this.pressure.magnitude;
	}
	
	public Vector getPressureVector() {
		return this.pressure;
	}

	public void updateEntity() {

	}
	
	public boolean canConnect(ForgeDirection dir) {
		if(this.pipe.extra != 0)
			if(this.pipe.extraSide == dir.ordinal())
				return false;
		
		return this.canConnect(this.xCoord + dir.offsetX, this.yCoord + dir.offsetY, this.zCoord + dir.offsetZ);
	}
	
	public boolean canConnect(int x, int y, int z) {
		TileEntity tile = this.worldObj.getTileEntity(x, y, z);
		
		if(tile == null)
			return false;
		
		if(tile instanceof IFluidHandler)
			return true;
		
		if(tile instanceof TileEntityPipe) {
			if(this.pipe != null && ((TileEntityPipe)tile).pipe != null) {
				if(this.colour == -1 || ((TileEntityPipe)tile).colour == -1)
					return true;
				else if(this.colour == ((TileEntityPipe)tile).colour)
					return true;
			}
		}
		
		return false;
	}
	
	public int getConnections() {
		int i = 0;
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
			if(this.canConnect(dir))
				i++;
		
		return i;
	}
	
	public boolean isConnecting() {
		return this.getConnections() != 0;
	} 
	
	public ForgeDirection[] getPlacesToSendTo() {
		boolean[] dirs = new boolean[6];
		ArrayList<ForgeDirection> list = new ArrayList<ForgeDirection>();
		
		for(int i = 0; i < dirs.length; i++) {
			dirs[i] = true;
			
			if(i == this.pressure.direction.ordinal())
				dirs[i] = false;
			
			if(this.canConnect(ForgeDirection.getOrientation(i)))
				list.add(ForgeDirection.getOrientation(i));
		}
		
		return (ForgeDirection[])list.toArray();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getRenderBoundingBox() {
		return AxisAlignedBB.getBoundingBox(this.xCoord, this.yCoord, this.zCoord, this.xCoord + 1, this.yCoord + 1, this.zCoord + 1);
	}
}
