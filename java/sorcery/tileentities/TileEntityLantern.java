package sorcery.tileentities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Facing;
import net.minecraft.world.EnumSkyBlock;
import sorcery.lib.Pos3D;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityLantern extends TileEntitySorcery {
	public boolean isCheaty = false;
	public int fuel;
	public int lastTickFuel;
	
	public boolean hasFuel() {
		return this.fuel != 0;
	}
	
	public int consumeFuel(int amount) {
		int fuel = this.fuel;
		fuel -= amount;
		this.fuel = fuel;
		return fuel;
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		this.lastTickFuel = fuel;
		if(!this.isCheaty && this.hasFuel() && this.worldObj.rand.nextInt(21) == 0)
			this.consumeFuel(1);
		
		if(this.lastTickFuel == 1 && this.fuel == 0) {
			this.worldObj.updateLightByType(EnumSkyBlock.Block, this.xCoord, this.yCoord, this.zCoord);
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readFromNBT(par1NBTTagCompound);
		this.fuel = par1NBTTagCompound.getInteger("fuel");
		this.isCheaty = par1NBTTagCompound.getBoolean("cheaty");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setInteger("fuel", this.fuel);
		par1NBTTagCompound.setBoolean("cheaty", this.isCheaty);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getRenderBoundingBox() {
		return AxisAlignedBB.getBoundingBox(this.xCoord, this.yCoord, this.zCoord, this.xCoord + 1, this.yCoord + 1, this.zCoord + 1);
	}
	
	public Pos3D getConnectedBlock() {
		return new Pos3D(this.xCoord + Facing.offsetsXForSide[this.front], this.yCoord + Facing.offsetsYForSide[this.front], this.zCoord + Facing.offsetsZForSide[this.front]);
	}
}
