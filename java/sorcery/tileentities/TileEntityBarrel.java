package sorcery.tileentities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import sorcery.lib.ItemStackHelper;
import sorcery.lib.TankHelper;

public class TileEntityBarrel extends TileEntitySorcery implements IInventory, ISidedInventory, IFluidHandler {
	public ItemStack[] inventory = new ItemStack[2];
	
	public FluidTank[] tanks = new FluidTank[1];
	
	public int getSizeInventory() {
		return this.inventory.length;
	}
	
	public FluidTank[] getTanks() {
		return tanks;
	}
	
	public ItemStack getStackInSlot(int par1) {
		return this.inventory[par1];
	}
	
	public FluidStack getFluidInTank(int tank) {
		return this.tanks[tank].getFluid();
	}
	
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
	
	public ItemStack getStackInSlotOnClosing(int par1) {
		if(this.inventory[par1] != null) {
			ItemStack var2 = this.inventory[par1];
			this.inventory[par1] = null;
			return var2;
		} else {
			return null;
		}
	}
	
	public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {
		this.inventory[par1] = par2ItemStack;
		
		if(par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit()) {
			par2ItemStack.stackSize = this.getInventoryStackLimit();
		}
	}
	
	public String getInventoryName() {
		return "container.barrel";
	}
	
	public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readFromNBT(par1NBTTagCompound);
		NBTTagList items = par1NBTTagCompound.getTagList("Items", 10);
		NBTTagList tanks = par1NBTTagCompound.getTagList("Tanks", 10);
		this.inventory = new ItemStack[this.getSizeInventory()];
		
		for(int i = 0; i < items.tagCount(); i++) {
			NBTTagCompound tag = (NBTTagCompound)items.getCompoundTagAt(i);
			byte j = tag.getByte("Slot");
			
			if(j >= 0 && j < this.inventory.length) {
				this.inventory[j] = ItemStack.loadItemStackFromNBT(tag);
			}
		}
		
		for(int i = 0; i < tanks.tagCount(); i++) {
			NBTTagCompound tag = (NBTTagCompound)tanks.getCompoundTagAt(i);
			byte j = tag.getByte("Slot");
			
			if(j >= 0 && j < this.tanks.length) {
				this.tanks[j] = new FluidTank(FluidStack.loadFluidStackFromNBT(tag), (int)par1NBTTagCompound.getShort("Capacity") * 1000);
			}
		}
		
		if(tanks.tagCount() == 0) {
			this.tanks[0] = new FluidTank((int)par1NBTTagCompound.getShort("Capacity") * 1000);
		}
		
	}
	
	public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeToNBT(par1NBTTagCompound);
		NBTTagList items = new NBTTagList();
		NBTTagList tanks = new NBTTagList();
		if(this.tanks[0] != null)
			par1NBTTagCompound.setShort("Capacity", (short)(this.tanks[0].getCapacity() / 1000));
		
		for(int i = 0; i < this.inventory.length; i++) {
			if(this.inventory[i] != null) {
				NBTTagCompound tag = new NBTTagCompound();
				tag.setByte("Slot", (byte)i);
				this.inventory[i].writeToNBT(tag);
				items.appendTag(tag);
			}
		}
		
		for(int i = 0; i < this.tanks.length; i++) {
			if(this.tanks[i] != null && this.tanks[i].getFluid() != null) {
				NBTTagCompound tag = new NBTTagCompound();
				tag.setByte("Slot", (byte)i);
				this.tanks[i].getFluid().writeToNBT(tag);
				tanks.appendTag(tag);
			}
		}
		
		par1NBTTagCompound.setTag("Items", items);
		par1NBTTagCompound.setTag("Tanks", tanks);
	}
	
	public boolean hasFluid() {
		return this.tanks[0].getFluid().amount > 0;
	}
	
	public void updateEntity() {
		super.updateEntity();
		this.checkBucketSlots();
	}
	
	public void checkBucketSlots() {
		if(this.tanks[0] != null) {
			if((this.tanks[0].getFluid() == null && this.inventory[0] != null && FluidContainerRegistry.isFilledContainer(this.inventory[0])) || (this.tanks[0].getFluid() != null && this.inventory[0] != null && this.tanks[0].getFluid().isFluidEqual(FluidContainerRegistry.getFluidForFilledItem(this.inventory[0])) && this.tanks[0].getFluid().amount < this.tanks[0].getCapacity())) {
				if(this.inventory[0].getItem().getContainerItem(this.inventory[0]) != null) {
					if(this.tanks[0].getCapacity() == FluidContainerRegistry.BUCKET_VOLUME * 32 && FluidContainerRegistry.getFluidForFilledItem(this.inventory[0]).fluidID == FluidRegistry.getFluidID("lava")) {
						return;
					}
					
					if(this.inventory[1] == null) {
						this.inventory[1] = this.inventory[0].getItem().getContainerItem(this.inventory[0]);
						this.fill(0, FluidContainerRegistry.getFluidForFilledItem(this.inventory[0]), true);
						this.decrStackSize(0, 1);
					} else {
						if(!ItemStackHelper.instance.doItemsMatch(this.inventory[1], this.inventory[0].getItem().getContainerItem(this.inventory[0]))) {
							return;
						}
						if(inventory[1].stackSize == inventory[1].getMaxStackSize()) {
							return;
						}
						
						this.inventory[1].stackSize++;
						this.fill(0, FluidContainerRegistry.getFluidForFilledItem(this.inventory[0]), true);
						this.decrStackSize(0, 1);
					}
				} else {
					this.fill(0, FluidContainerRegistry.getFluidForFilledItem(this.inventory[0]), true);
					this.decrStackSize(0, 1);
				}
			} else if(this.tanks[0].getFluid() != null && this.inventory[0] != null && TankHelper.instance.canThisItemContainThisFluid(this.inventory[0], this.tanks[0].getFluid())) {
				ItemStack newStack = FluidContainerRegistry.fillFluidContainer(this.tanks[0].getFluid().copy(), this.inventory[0].copy());
				if(this.inventory[1] == null || this.inventory[1].stackSize < newStack.stackSize) {
					this.inventory[1] = FluidContainerRegistry.fillFluidContainer(this.tanks[0].getFluid(), this.inventory[0]);
					this.drain(0, FluidContainerRegistry.getFluidForFilledItem(newStack).amount, true);
					this.decrStackSize(0, 1);
					
				}
			}
		}
	}
	
	public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer) {
		return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : par1EntityPlayer.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
	}
	
	public void openChest() {
	}
	
	public void closeChest() {
	}
	
	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		if(this.getBlockMetadata() == 13 && resource.getFluid().getTemperature(resource) >= 1300)
			return 0;

		return fill(0, resource, doFill);
	}
	
	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		return drain(0, resource.amount, doDrain);
	}
	
	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return drain(0, maxDrain, doDrain);
	}
	
	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return new FluidTankInfo[]{new FluidTankInfo(this.tanks[0])};
	}
	
	public int getScaledTankQuantity(int i, int tank) {
		return this.tanks[tank].getFluid().amount * i / this.tanks[tank].getCapacity();
	}
	
	@Override
	public void handleFluidPacket(FluidStack stack, int tankID, boolean isClient) {
		this.tanks[tankID].setFluid(stack);
	}
	
	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return(i == 0);
	}
	
	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return(from != ForgeDirection.DOWN);
	}
	
	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return(from == ForgeDirection.DOWN);
	}
	
	public static TileEntityBarrel getBarrelWithCapacity(int capacity) {
		TileEntityBarrel barrel = new TileEntityBarrel();
		barrel.tanks[0] = new FluidTank(capacity);
		
		return barrel;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int var1) {
		ForgeDirection side = ForgeDirection.getOrientation(var1);
		if(side == ForgeDirection.DOWN)
			if(this.tanks[0].getFluidAmount() == this.tanks[0].getCapacity())
				return new int[] {0, 1};
			else return new int[] {1};
		else return new int[] {0};
	}

	@Override
	public boolean canInsertItem(int var1, ItemStack var2, int var3) {
		if(var1 == 0)
			return true;
		
		return false;
	}

	@Override
	public boolean canExtractItem(int var1, ItemStack var2, int var3) {
		return true;
	}
}
