package sorcery.tileentities;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.ForgeDirection;
import sorcery.api.mojo.IMojoDistributor;
import sorcery.api.mojo.MojoBattery;

public class TileEntityMojoAbsorber extends TileEntityMojo implements IMojoDistributor {
	public int amount;
	
	public int getMojo() {
		return 0;
	}

	public int addMojo(int amount) {
		return 0;
	}

	public int fill(int amount) {
		return 0;
	}
	
	public int subtractMojo(int amount) {
		return 0;
	}

	public int setMojo(int amount) {
		return 0;
	}

	public int getCapacity() {
		return 0;
	}

	public boolean isFull() {
		return false;
	}

	public boolean canReceiveMojo() {
		return false;
	}

	public boolean canSendMojo() {
		return true;
	}
	
	@Override
	public ForgeDirection[] getDirectionsToSendTo() {
		return ForgeDirection.VALID_DIRECTIONS;
	}

	@Override
	public ForgeDirection[] getDirectionsToReceiveFrom() {
		return new ForgeDirection[0];
	}
	
	public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readFromNBT(par1NBTTagCompound);
		this.amount = par1NBTTagCompound.getInteger("amount");
	}
	
	public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setInteger("amount", this.amount);
	}
	
	public static TileEntityMojoStorage getBatteryWithCapacity(int capacity) {
		TileEntityMojoStorage tile = new TileEntityMojoStorage();
		tile.mojo = new MojoBattery(capacity);
		
		return tile;
	}
}
