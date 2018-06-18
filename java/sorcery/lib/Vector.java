package sorcery.lib;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

public class Vector {
	public ForgeDirection direction;
	public float magnitude;
	
	public Vector(float magnitude, ForgeDirection direction) {
		this.direction = direction;
		this.magnitude = magnitude;
	}
	
	public Vector(float magnitude, int direction) {
		this.direction = ForgeDirection.getOrientation(direction);
		this.magnitude = magnitude;
	}
	
	public void writeToNBT(NBTTagCompound compound, String name) {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setFloat("magnitude", this.magnitude);
		tag.setInteger("direction", this.direction.ordinal());
		compound.setTag(name, tag);
	}
	
	public static Vector readFromNBT(NBTTagCompound compound, String name) {
		NBTTagCompound tag = compound.getCompoundTag(name);
		return new Vector(tag.getFloat("magnitude"), ForgeDirection.getOrientation(tag.getInteger("direction")));
	}
}
