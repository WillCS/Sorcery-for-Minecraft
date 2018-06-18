package sorcery.tileentities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;
import sorcery.api.mojo.IMojoDistributor;
import sorcery.api.mojo.IMojoStorage;
import sorcery.api.mojo.IMojoWire;
import sorcery.mojo.EnumWireType;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityMojoWire extends TileEntitySorcery implements IMojoWire {
	public int colour;
	public EnumWireType type = EnumWireType.SILVER;
	
	public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readFromNBT(par1NBTTagCompound);
		this.colour = par1NBTTagCompound.getInteger("colour");
		this.type = EnumWireType.getWireTypeFrom(par1NBTTagCompound.getInteger("type"));
	}
	
	public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeToNBT(par1NBTTagCompound);
		NBTTagCompound fluid = new NBTTagCompound();
		par1NBTTagCompound.setInteger("colour", this.colour);
		par1NBTTagCompound.setInteger("type", this.type.id);
	}
	
	@Override
	public void updateEntity() {
		
	}
	
	public boolean canConnect(ForgeDirection dir) {
		TileEntity tile = this.worldObj.getTileEntity(
				this.xCoord + dir.offsetX, this.yCoord + dir.offsetY, this.zCoord + dir.offsetZ);
		
		if(tile instanceof IMojoStorage) {
			ForgeDirection inverse = dir.getOpposite();
			
			for(ForgeDirection temp : ((IMojoStorage)tile).getDirectionsToReceiveFrom()) {
				if(temp == inverse)
					return true;
			}
				
			return false;
		}
		
		return this.canConnect(this.xCoord + dir.offsetX, this.yCoord + dir.offsetY, this.zCoord + dir.offsetZ);
	}
	
	public boolean canConnect(int x, int y, int z) {
		TileEntity tile = this.worldObj.getTileEntity(x, y, z);
		
		if(tile == null)
			return false;
		
		if(tile instanceof IMojoStorage) {
			return false;
		}
		
		if(tile instanceof TileEntityMojoWire) {
			if(this.colour == -1 || ((TileEntityMojoWire)tile).colour == -1)
				return true;
			
			if(this.colour == ((TileEntityMojoWire)tile).colour)
				return true;
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
	
	public ForgeDirection findNearestAvailableReceiver(ArrayList<TileEntityMojoWire> list) {
		list.add(this);
		
		for(ForgeDirection dir : this.getPlacesToSendTo()) {
			if(this.isContainerInDirection(dir) && !this.getContainerInDirection(dir).isFull() && this.getContainerInDirection(dir).canReceiveMojo()) {
				return dir;
			}
		}
		
		for(ForgeDirection dir : this.getPlacesToSendTo()) {	
			if(this.isWireInDirection(dir) && ! list.contains(this.getWireInDirection(dir))) {
				if(this.getWireInDirection(dir).findNearestAvailableReceiver((ArrayList<TileEntityMojoWire>)list.clone()) != null) {
					return dir;
				}
			}
		}
		
		return ForgeDirection.UNKNOWN;
	}
	
	public IMojoStorage getNearestAvailableReceiver(ArrayList<TileEntityMojoWire> list) {
		list.add(this);
		
		for(ForgeDirection dir : this.getPlacesToSendTo()) {
			if(this.isContainerInDirection(dir) && !this.getContainerInDirection(dir).isFull() && this.getContainerInDirection(dir).canReceiveMojo()) {
				return this.getContainerInDirection(dir);
			}
		}
		
		for(ForgeDirection dir : this.getPlacesToSendTo()) {
			if(this.isWireInDirection(dir) && ! list.contains(this.getWireInDirection(dir))) {
				IMojoStorage storage = this.getWireInDirection(dir).getNearestAvailableReceiver((ArrayList<TileEntityMojoWire>)list.clone());
				if(storage != null) {
					return storage;
				}
			}
		}
		
		return null;
	}
	
	@Override
	public int[] getWireCapacitiesToReceiver(IMojoStorage receiver) {
		return this.getWireCapacitiesToReceiver(receiver, new ArrayList<TileEntityMojoWire>());
	}
	
	public int[] getWireCapacitiesToReceiver(IMojoStorage receiver, ArrayList<TileEntityMojoWire> list) {
		list.add(this);

		ForgeDirection dir = this.getDirectionToReceiver(receiver, new ArrayList<TileEntityMojoWire>());
		if(isReceiverInDirection(dir, receiver)) {
			int[] ret = new int[list.size()];
			for(int i = 0; i < list.size(); i++) {
				ret[i] = list.get(i).type.capacity;
			}
			
			return ret;
		} else {
			return this.getWireInDirection(dir).getWireCapacitiesToReceiver(receiver, list);
		}
	}
	
	public ForgeDirection[] getPlacesToSendTo() {
		ArrayList<ForgeDirection> list = new ArrayList<ForgeDirection>();
		
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			if(this.canConnect(dir))
				list.add(dir);
		}
		
		return list.toArray(new ForgeDirection[0]);
	}
	
	public boolean isWireInDirection(ForgeDirection dir) {
		if(!this.canConnect(dir))
			return false;
		
		TileEntity tile = this.worldObj.getTileEntity(
				this.xCoord + dir.offsetX, this.yCoord + dir.offsetY, this.zCoord + dir.offsetZ);

		return tile instanceof TileEntityMojoWire;
	}
	
	public boolean isContainerInDirection(ForgeDirection dir) {
		if(!this.canConnect(dir))
			return false;
		TileEntity tile = this.worldObj.getTileEntity(
				this.xCoord + dir.offsetX, this.yCoord + dir.offsetY, this.zCoord + dir.offsetZ);

		return tile instanceof IMojoStorage;
	}
	
	public TileEntityMojoWire getWireInDirection(ForgeDirection dir) {
		return (TileEntityMojoWire)this.worldObj.getTileEntity(
				this.xCoord + dir.offsetX, this.yCoord + dir.offsetY, this.zCoord + dir.offsetZ);
	}
	
	public IMojoStorage getContainerInDirection(ForgeDirection dir) {
		TileEntity tile = this.worldObj.getTileEntity(
				this.xCoord + dir.offsetX, this.yCoord + dir.offsetY, this.zCoord + dir.offsetZ);
		if(tile instanceof IMojoStorage)
			return (IMojoStorage)tile;
		else return null;
	}
	
	public ForgeDirection getDirectionToReceiver(IMojoStorage receiver, ArrayList<TileEntityMojoWire> list) {
		list.add(this);
		
		for(ForgeDirection dir : this.getPlacesToSendTo()) {
			if(this.isContainerInDirection(dir) && this.getContainerInDirection(dir) == receiver) {
				return dir;
			}
		}
		
		for(ForgeDirection dir : this.getPlacesToSendTo()) {
			if(this.isWireInDirection(dir) && ! list.contains(this.getWireInDirection(dir))) {
				if(this.getWireInDirection(dir).getDirectionToReceiver(receiver, (ArrayList<TileEntityMojoWire>)list.clone()) != ForgeDirection.UNKNOWN) {
					return dir;
				}
			}
		}
		
		return ForgeDirection.UNKNOWN;
	}
	
	public boolean isReceiverInDirection(ForgeDirection dir, IMojoStorage receiver) {
		return this.isContainerInDirection(dir) && this.getContainerInDirection(dir) == receiver;
	}
	
	@Override
	public boolean findNearestMojoDistributor() {
		return this.findNearestMojoDistributor(new ArrayList<TileEntityMojoWire>()) != ForgeDirection.UNKNOWN;
	}
	
	public ForgeDirection findNearestMojoDistributor(ArrayList<TileEntityMojoWire> list) {
		list.add(this);
		
		for(ForgeDirection dir : this.getPlacesToSendTo()) {
			if(this.isContainerInDirection(dir)) {
				if(this.getContainerInDirection(dir) instanceof IMojoDistributor && this.getContainerInDirection(dir).canSendMojo())
					return dir;
			}
		}
		
		for(ForgeDirection dir : this.getPlacesToSendTo()) {	
			if(this.isWireInDirection(dir) && ! list.contains(this.getWireInDirection(dir))) {
				if(this.getWireInDirection(dir).findNearestMojoDistributor((ArrayList<TileEntityMojoWire>)list.clone()) != null) {
					return dir;
				}
			}
		}
		
		return ForgeDirection.UNKNOWN;
	}

	@Override
	public IMojoDistributor getNearestMojoDistributor() {
		return this.getNearestMojoDistributor(new ArrayList<TileEntityMojoWire>());
	}
	
	public IMojoDistributor getNearestMojoDistributor(ArrayList<TileEntityMojoWire> list) {
		list.add(this);
		
		for(ForgeDirection dir : this.getPlacesToSendTo()) {
			if(this.isContainerInDirection(dir)) {
				if(this.getContainerInDirection(dir) instanceof IMojoDistributor && this.getContainerInDirection(dir).canSendMojo())
					return (IMojoDistributor)this.getContainerInDirection(dir);
			}
		}
		
		for(ForgeDirection dir : this.getPlacesToSendTo()) {	
			if(this.isWireInDirection(dir) && ! list.contains(this.getWireInDirection(dir))) {
				IMojoDistributor storage = this.getWireInDirection(dir).getNearestMojoDistributor((ArrayList<TileEntityMojoWire>)list.clone());
				if(storage != null) {
					return storage;
				}
			}
		}
		
		return null;
	}
	
	@Override
	public IMojoStorage[] getAllConnectedStorage() {
		ArrayList<IMojoStorage> list = new ArrayList<IMojoStorage>();
		return this.getAllConnectedStorage(list, new ArrayList<TileEntityMojoWire>());
	}
	
	public IMojoStorage[] getAllConnectedStorage(ArrayList<IMojoStorage> storage, ArrayList<TileEntityMojoWire> wires) {
		wires.add(this);
		
		for(ForgeDirection dir : this.getPlacesToSendTo()) {
			if(this.isContainerInDirection(dir) && this.getContainerInDirection(dir).canReceiveMojo()) {
				if(!storage.contains(this.getContainerInDirection(dir)))
					storage.add(this.getContainerInDirection(dir));
			}
		}
		
		for(ForgeDirection dir : this.getPlacesToSendTo()) {	
			if(this.isWireInDirection(dir) && ! wires.contains(this.getWireInDirection(dir))) {
				IMojoStorage[] tempList = this.getWireInDirection(dir).getAllConnectedStorage(storage, wires);
				for(IMojoStorage s: tempList) {
					if(!storage.contains(s))
						storage.add(s);
				}
			}
		}

		return storage.toArray(new IMojoStorage[0]);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getRenderBoundingBox() {
		return AxisAlignedBB.getBoundingBox(this.xCoord, this.yCoord, this.zCoord, this.xCoord + 1, this.yCoord + 1, this.zCoord + 1);
	}
	
	@Override
	public int getColour() {
		return this.colour;
	}

	@Override
	public boolean connectsToReceiver(IMojoStorage receiver) {
		return this.getDirectionToReceiver(receiver, new ArrayList<TileEntityMojoWire>()) != ForgeDirection.UNKNOWN;
	}

	@Override
	public int getLowestCapacityToReceiver(IMojoStorage receiver) {
		int[] capacities = this.getWireCapacitiesToReceiver(receiver);
		int lowest = Integer.MAX_VALUE;
		for(int i : capacities) {
			if(i < lowest)
				lowest = i;
		}
		
		return lowest;
	}
}