package sorcery.lib;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import sorcery.tileentities.nodes.TileEntityNodeBase;

public class Pos3D {
	private int x;
	private int y;
	private int z;
	
	public Pos3D(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Pos3D(int[] pos) {
		this.x = pos[0];
		this.y = pos[1];
		this.z = pos[2];
	}
	
	public int[] getPos() {
		return new int[]{x, y, z};
	}
	
	public void setPos(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void setPos(int[] pos) {
		this.x = pos[0];
		this.y = pos[1];
		this.z = pos[2];
	}
	
	public int xCoord() {
		return this.x;
	}
	
	public int yCoord() {
		return this.y;
	}
	
	public int zCoord() {
		return this.z;
	}
	
	public void writeToNBT(NBTTagCompound tag, String name) {
		tag.setIntArray(name, new int[]{x, y, z});
	}
	
	public static Pos3D readFromNBT(NBTTagCompound tag, String name) {
		if(tag.getIntArray(name).length != 0)
			return new Pos3D(tag.getIntArray(name));
		
		return null;
	}
	
	public Block getBlockAtPos(World world) {
		return world.getBlock(this.x, this.y, this.z);
	}
	
	public TileEntity getTileEntityAtPos(World world) {
		return world.getTileEntity(this.x, this.y, this.z);
	}
	
	public boolean isNode(World world) {
		TileEntity tile = this.getTileEntityAtPos(world);
		if(tile == null)
			return false;
		return tile instanceof TileEntityNodeBase;
	}
	
	public boolean isSamePos(Pos3D pos2) {
		if(pos2 == null)
			return false;
		
		if(this.x == pos2.xCoord() && this.y == pos2.yCoord() && this.z == pos2.zCoord())
			return true;
		
		return false;
	}
	
	public String toString() {
		return this.x + ", " + this.y + ", " + this.z;
	}
}
