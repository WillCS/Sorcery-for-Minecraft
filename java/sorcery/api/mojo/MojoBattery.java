package sorcery.api.mojo;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import sorcery.api.element.ElementStack;

public class MojoBattery implements IMojoStorage {
	
	private int mojo;
	private int capacity;
	
	public MojoBattery(int capacity) {
		this.capacity = capacity;
	}

	@Override
	public int getMojo() {
		return this.mojo;
	}
	
	@Override
	public int getCapacity() {
		return this.capacity;
	}

	@Override
	public int addMojo(int amount) {
		this.mojo += amount;
		
		if(this.mojo > this.capacity)
			this.mojo = this.capacity;
		
		return this.mojo;
	}
	
	@Override
	public int fill(int amount) {
		this.mojo += amount;
		
		if(this.mojo > this.capacity) {
			int ret = this.mojo - this.capacity;
			this.mojo = this.capacity;
			return ret;
		}
		
		return 0;
	}

	@Override
	public int subtractMojo(int amount) {
		this.mojo -= amount;
		
		if(this.mojo < 0)
			this.mojo = 0;
		
		return this.mojo;
	}

	@Override
	public int setMojo(int amount) {
		this.mojo = amount;
		
		if(this.mojo > this.capacity)
			this.mojo = this.capacity;
		
		return this.mojo;
	}
	
	@Override
	public boolean isFull() {
		return this.mojo >= this.capacity;
	}
	

	@Override
	public boolean canReceiveMojo() {
		return !this.isFull();
	}

	@Override
	public boolean canSendMojo() {
		return this.getMojo() != 0;
	}

	public void writeToNBT(NBTTagCompound tag, String name) {
		NBTTagCompound battery = new NBTTagCompound();
		battery.setInteger("mojo", this.getMojo());
		battery.setInteger("capacity", this.getCapacity());
		
		tag.setTag(name, battery);
	}
	
	public static MojoBattery readFromNBT(NBTTagCompound tag, String name) {
		if(tag.getCompoundTag(name).hasNoTags())
			return new MojoBattery(5000);
		
		NBTTagCompound battery = tag.getCompoundTag(name);
		MojoBattery ret = new MojoBattery(battery.getInteger("capacity"));
		ret.setMojo(battery.getInteger("mojo"));
		return ret;
	}

	@Override
	public ForgeDirection[] getDirectionsToReceiveFrom() {
		return null;
	}
}
