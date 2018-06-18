package sorcery.tileentities;

import java.util.ArrayList;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import sorcery.api.mojo.IMojoDistributor;
import sorcery.api.mojo.IMojoStorage;
import sorcery.api.mojo.IMojoStorageItem;
import sorcery.api.mojo.IMojoWire;
import sorcery.api.mojo.MojoBattery;

public class TileEntityMojoStorage extends TileEntitySorcery implements IInventory, IMojoDistributor {
	public ItemStack[] inventory = new ItemStack[1];
	public MojoBattery mojo;
	public ArrayList<IMojoStorage> network = new ArrayList<IMojoStorage>();
	public boolean cheaty = false;
	public boolean charging;
	public int maxInput;
	public int maxOutput;

	@Override
	public void updateEntity() {
		for(ForgeDirection dir : this.getDirectionsToSendTo()) {
			TileEntity tile = this.worldObj.getTileEntity(this.xCoord + dir.offsetX, this.yCoord + dir.offsetY, this.zCoord + dir.offsetZ);
			if(tile != null && tile instanceof IMojoWire) {
				IMojoWire wire = (IMojoWire)tile;
				IMojoStorage[] list = wire.getAllConnectedStorage();
				
				for(IMojoStorage storage : list) {
					if(storage != null && storage != this && !this.network.contains(storage)) {
						this.network.add(storage);
					}
				}
			}
		}
		
		this.manageRequests();
		
		this.doCharge();
		
		if(this.cheaty)
			this.mojo.setMojo(this.getCapacity());
	}
	
	public void doCharge() {
		if(this.inventory[0] != null && this.inventory[0].getItem() instanceof IMojoStorageItem) {
			IMojoStorageItem item = (IMojoStorageItem)this.inventory[0].getItem();
			ItemStack stack = this.inventory[0];
			
			if(this.charging && this.maxOutput > 0 && this.getMojo() != 0) {
				if(item.getMojo(stack) < item.getCapacity(stack)) {
					int mojoToCharge = this.getMojo();
					if(mojoToCharge > this.maxOutput)
						mojoToCharge = this.maxOutput;
					if(mojoToCharge > (item.getCapacity(stack) - item.getMojo(stack)))
						mojoToCharge = item.getCapacity(stack) - item.getMojo(stack);
					item.addMojo(stack, mojoToCharge);
					this.subtractMojo(mojoToCharge);
				}
			} else if(!this.charging && this.maxInput > 0 && this.getMojo() != this.getCapacity()) {
				if(item.getMojo(stack) > 0) {
					int mojoToReceive = item.getMojo(stack);
					if(mojoToReceive > this.maxInput)
						mojoToReceive = this.maxInput;
					if(mojoToReceive > (this.getCapacity() - this.getMojo()))
						mojoToReceive = this.getCapacity() - this.getMojo();
					item.subtractMojo(stack, mojoToReceive);
					this.addMojo(mojoToReceive);
				}
			}
		}
	}
	
	public void manageRequests() {
		for(IMojoStorage storage : this.network) {
			if(storage == null) {
				this.network.remove(storage);
				continue;
			}
		
			if(storage.canReceiveMojo()) {
				for(ForgeDirection dir : this.getDirectionsToSendTo()) {
					TileEntity tile = this.worldObj.getTileEntity(this.xCoord + dir.offsetX, this.yCoord + dir.offsetY, this.zCoord + dir.offsetZ);
					if(tile != null && tile instanceof IMojoWire) {
						IMojoWire wire = (IMojoWire)tile;
						
						if(wire.connectsToReceiver(storage)) {
							int low = wire.getLowestCapacityToReceiver(storage);
							
							if(this.maxOutput < low)
								low = this.maxOutput;
							
							if(storage.getCapacity() - storage.getMojo() < low)
								low = storage.getCapacity() - storage.getMojo();
							
							if(this.getMojo() < low)
								low = this.getMojo();
							
							if(this.cheaty) {
								storage.fill(low);
								break;
							} else if(this.getMojo() >= low) {
								storage.fill(low);
								if(!this.cheaty)
								this.mojo.subtractMojo(low);
								break;
							}
						}
					}
				}
			}
		}
	}
	
	public boolean canSendTo(IMojoStorage storage) {
		for(ForgeDirection dir : this.getDirectionsToSendTo()) {
			TileEntity tile = this.worldObj.getTileEntity(this.xCoord + dir.offsetX, this.yCoord + dir.offsetY, this.zCoord + dir.offsetZ);
			if(tile != null && tile instanceof IMojoWire) {
				IMojoWire wire = (IMojoWire)tile;
				IMojoStorage[] list = wire.getAllConnectedStorage();
				
				for(IMojoStorage temp : list) {
					if(temp == storage)
						return true;
				}
			}
		}
		
		return false;
	}
	
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
		if(amount > this.maxInput) {
			int leftOver = this.mojo.fill(maxInput);
			return maxInput - amount + leftOver;
		} else {
			return this.addMojo(amount) - this.getCapacity();
		}
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
		return this.mojo.isFull();
	}

	@Override
	public boolean canReceiveMojo() {
		return !this.isFull() && this.maxInput != 0;
	}

	@Override
	public boolean canSendMojo() {
		return this.getMojo() != 0 && this.maxOutput != 0;
	}

	@Override
	public ItemStack getStackInSlot(int par1) {
		return this.inventory[par1];
	}
	
	@Override
	public String getInventoryName() {
		return "container.mojoStorage";
	}

	@Override
	public ItemStack decrStackSize(int par1, int par2) {
		if(this.inventory[par1] != null) {
			ItemStack var3;
			
			if(this.inventory[par1].stackSize <= par2) {
				var3 = this.inventory[par1];
				this.inventory[par1] = null;
				return var3;
			} else {
				var3 = this.inventory[par1].splitStack(par2);
				
				if(this.inventory[par1].stackSize == 0) {
					this.inventory[par1] = null;
				}
				
				return var3;
			}
		} else {
			return null;
		}
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int par1) {
		if(this.inventory[par1] != null) {
			ItemStack var2 = this.inventory[par1];
			this.inventory[par1] = null;
			return var2;
		} else {
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {
		this.inventory[par1] = par2ItemStack;
		
		if(par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit()) {
			par2ItemStack.stackSize = this.getInventoryStackLimit();
		}
	}
	
	public int getSizeInventory() {
		return this.inventory.length;
	}
	
	public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readFromNBT(par1NBTTagCompound);
		NBTTagList items = par1NBTTagCompound.getTagList("Items", 10);
		this.inventory = new ItemStack[this.getSizeInventory()];
		this.mojo = MojoBattery.readFromNBT(par1NBTTagCompound, "Mojo");
		this.cheaty = par1NBTTagCompound.getBoolean("cheaty");
		this.charging = par1NBTTagCompound.getBoolean("charging");
		this.maxInput = par1NBTTagCompound.getInteger("maxInput");
		this.maxOutput = par1NBTTagCompound.getInteger("maxOutput");
		
		for(int i = 0; i < items.tagCount(); i++) {
			NBTTagCompound tag = (NBTTagCompound)items.getCompoundTagAt(i);
			byte j = tag.getByte("Slot");
			
			if(j >= 0 && j < this.inventory.length) {
				this.inventory[j] = ItemStack.loadItemStackFromNBT(tag);
			}
		}
	}
	
	public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeToNBT(par1NBTTagCompound);
		NBTTagList items = new NBTTagList();
		
		for(int i = 0; i < this.inventory.length; i++) {
			if(this.inventory[i] != null) {
				NBTTagCompound tag = new NBTTagCompound();
				tag.setByte("Slot", (byte)i);
				this.inventory[i].writeToNBT(tag);
				items.appendTag(tag);
			}
		}
		
		par1NBTTagCompound.setTag("Items", items);
		this.mojo.writeToNBT(par1NBTTagCompound, "Mojo");
		par1NBTTagCompound.setBoolean("cheaty", this.cheaty);
		par1NBTTagCompound.setBoolean("charging", this.charging);
		par1NBTTagCompound.setInteger("maxInput", this.maxInput);
		par1NBTTagCompound.setInteger("maxOutput", this.maxOutput);
	}
	
	public static TileEntityMojoStorage getBatteryWithCapacity(int capacity) {
		TileEntityMojoStorage tile = new TileEntityMojoStorage();
		tile.mojo = new MojoBattery(capacity);
		
		return tile;
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

	@Override
	public ForgeDirection[] getDirectionsToSendTo() {
		ArrayList<ForgeDirection> ret = new ArrayList<ForgeDirection>();
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			if(ForgeDirection.VALID_DIRECTIONS[this.front] != dir)
				ret.add(dir);
		}
		
		return ret.toArray(new ForgeDirection[0]);
	}
	
	public void sendDataPacket(byte id, int data) {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setInteger("id", id);
		tag.setInteger("data", data);
		this.handlePacketData(tag, true);
		this.sendNBTPacket(tag);
	}
	
	@Override
	public void handlePacketData(NBTTagCompound tag, boolean isClient) {
		int id = tag.getInteger("id");
		switch(id) {
			case 0:
				this.charging = !this.charging;
				break;
			case 1:
				this.maxInput += tag.getInteger("data");
				if(this.maxInput > 250)
					this.maxInput = 250;
				else if(this.maxInput < 0)
					this.maxInput = 0;
				break;
			case 2:
				this.maxOutput += tag.getInteger("data");
				if(this.maxOutput > 250)
					this.maxOutput = 250;
				else if(this.maxOutput < 0)
					this.maxOutput = 0;
		}
	}
}
