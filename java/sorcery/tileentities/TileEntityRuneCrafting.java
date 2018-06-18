package sorcery.tileentities;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.Packet;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import sorcery.items.ItemElementalCrystal;
import sorcery.items.ItemEnergy;
import sorcery.items.ItemMagicOrb;
import sorcery.lib.ItemStackHelper;
import sorcery.lib.SorceryItems;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityRuneCrafting extends TileEntitySorcery implements IInventory, ISidedInventory, IFluidHandler {
	public ItemStack[] inventory = new ItemStack[4];
	
	public FluidTank[] fuel = new FluidTank[1];
	
	public int currentCookTime = 0;
	
	public int runeCookTime = 0;
	
	public int currentRune = 1;
	
	public int tankQuantity;
	
	public static int tankMaxCapacity = 10000;
	
	public EntityItem itemToRender;
	
	public int getSizeInventory() {
		return this.inventory.length;
	}
	
	public ItemStack getStackInSlot(int slot) {
		return this.inventory[slot];
	}
	
	public ItemStack decrStackSize(int slot, int size) {
		if(this.inventory[slot] != null) {
			ItemStack var3;
			
			if(this.inventory[slot].stackSize <= size) {
				var3 = this.inventory[slot];
				this.inventory[slot] = null;
				return var3;
			} else {
				var3 = this.inventory[slot].splitStack(size);
				
				if(this.inventory[slot].stackSize == 0) {
					this.inventory[slot] = null;
				}
				
				return var3;
			}
		} else {
			return null;
		}
	}
	
	public ItemStack getStackInSlotOnClosing(int slot) {
		if(this.inventory[slot] != null) {
			ItemStack var2 = this.inventory[slot];
			this.inventory[slot] = null;
			return var2;
		} else {
			return null;
		}
	}
	
	public void setInventorySlotContents(int slot, ItemStack item) {
		this.inventory[slot] = item;
		
		if(item != null && item.stackSize > this.getInventoryStackLimit()) {
			item.stackSize = this.getInventoryStackLimit();
		}
	}
	
	public String getInventoryName() {
		return "container.runecrafting";
	}
	
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		NBTTagList var2 = tag.getTagList("Items", 10);
		this.inventory = new ItemStack[this.getSizeInventory()];
		
		for(int var3 = 0; var3 < var2.tagCount(); ++var3) {
			NBTTagCompound var4 = (NBTTagCompound)var2.getCompoundTagAt(var3);
			byte var5 = var4.getByte("Slot");
			
			if(var5 >= 0 && var5 < this.inventory.length) {
				this.inventory[var5] = ItemStack.loadItemStackFromNBT(var4);
				
				if(var5 == 1 && ItemStack.loadItemStackFromNBT(var4) != null) {
					this.itemToRender = new EntityItem(this.worldObj, this.xCoord + 0.5, this.yCoord + 1.25, this.zCoord + 0.5, ItemStack.loadItemStackFromNBT(var4));
				}
			}
		}
		
		this.currentCookTime = tag.getShort("CookTime");
		this.runeCookTime = tag.getInteger("RuneCookTime");
		this.tankQuantity = tag.getInteger("TankQuantity");
		this.currentRune = tag.getInteger("CurrentRune");
	}
	
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		tag.setShort("CookTime", (short)this.currentCookTime);
		tag.setInteger("RuneCookTime", this.runeCookTime);
		tag.setInteger("TankQuantity", this.tankQuantity);
		tag.setInteger("CurrentRune", this.currentRune);
		NBTTagList var2 = new NBTTagList();
		
		for(int var3 = 0; var3 < this.inventory.length; ++var3) {
			if(this.inventory[var3] != null) {
				NBTTagCompound var4 = new NBTTagCompound();
				var4.setByte("Slot", (byte)var3);
				this.inventory[var3].writeToNBT(var4);
				var2.appendTag(var4);
			}
		}
		
		tag.setTag("Items", var2);
	}
	
	public int getInventoryStackLimit() {
		return 64;
	}
	
	public int getCurrentItemCookTime() {
		if(this.inventory[1] != null) {
			return(30);
		}
		return 0;
	}
	
	@SideOnly(Side.CLIENT)
	public int getCookProgressScaled(int scale) {
		if(this.currentRune == 0 || this.currentCookTime == 0) {
			return 0;
		} else {
			return this.currentCookTime * scale / 30;
		}
	}
	
	public boolean isCooking() {
		return this.currentCookTime > 0;
	}
	
	public boolean hasFuel() {
		return this.tankQuantity > 0;
	}
	
	@Override
	public void markDirty() {
		if(this.inventory[3] != null && this.inventory[3].getItem() == SorceryItems.bucketLiquidMagic) {
			FluidStack Fluid = new FluidStack(FluidRegistry.getFluid("mojo"), FluidContainerRegistry.BUCKET_VOLUME);
			
			if(Fluid != null && this.tankQuantity < tankMaxCapacity) {
				fill(ForgeDirection.UNKNOWN, Fluid, true);
				this.setInventorySlotContents(3, (new ItemStack(Items.bucket, 1)));
			}
		}
		
		if(this.inventory[1] != null) {
			this.itemToRender = new EntityItem(this.worldObj, this.xCoord + 0.5, this.yCoord + 1.25, this.zCoord + 0.5, new ItemStack(this.inventory[1].getItem(), 0, this.inventory[1].getItemDamage()));
		}
		
	}
	
	public void updateEntity() {
		super.updateEntity();
		// int meta = this.worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
		
		if(this.currentRune < 1) {
			this.currentCookTime = 0;
		}
		
		if(this.itemToRender != null) {
			this.itemToRender.age++;
		}
		
		if(this.inventory[1] == null) {
			this.itemToRender = null;
		}
		
		if(this.canCook() && this.tankQuantity > 0 && this.currentRune != 0) {
			if(this.currentCookTime >= 30 && this.tankQuantity != 0) {
				this.smeltItem();
				this.currentCookTime = 0;
				this.decrStackSize(0, 1);
			} else {
				++this.currentCookTime;
				--this.tankQuantity;
			}
		}
		
		if(this.inventory[1] != null && this.inventory[1].getItem() instanceof ItemMagicOrb) {
			if(this.inventory[1].getItemDamage() != this.currentRune) {
				this.currentCookTime = 0;
			}
		}
		
		if(this.inventory[1] != null && this.inventory[1].getItem() == SorceryItems.magicOrb) {
			this.currentRune = this.inventory[1].getItemDamage();
		}
		
		if(this.tankQuantity < 0) {
			this.tankQuantity = 0;
		}
		
		if(this.inventory[0] != null && this.inventory[2] != null && inventory[0] != inventory[2]) {
			this.currentCookTime = 0;
		}
		
		if(this.tankQuantity > tankMaxCapacity) {
			this.tankQuantity = tankMaxCapacity;
		}
	}
	
	private boolean canCook() {
		if(this.inventory[1] == null || this.tankQuantity == 0 || this.inventory[0] == null)
			return false;
		else if(this.inventory[2] != null && this.inventory[2].stackSize == 64 && this.currentRune > 0)
			return false;
		else if(this.inventory[2] != null && this.inventory[2].getItemDamage() != this.currentRune)
			return false;
		else if(this.inventory[1] != null && !(this.inventory[1].getItem() instanceof ItemMagicOrb))
			return false;
		
		if(ItemStackHelper.getInstance().areItemsEqual(this.inventory[0], new ItemStack(SorceryItems.energy, 1, 0)) || ItemStackHelper.getInstance().areItemsEqual(this.inventory[2], new ItemStack(SorceryItems.energy, 1, 0))) {
			if((this.inventory[0] != null && this.inventory[0].getItem() instanceof ItemEnergy) && (this.inventory[2] != null && this.inventory[2].getItem() instanceof ItemEnergy) && this.inventory[1].getItem() == SorceryItems.magicOrb) {
				return true;
			}
			if(this.inventory[0] != null && this.inventory[0].getItem() instanceof ItemEnergy && (this.inventory[2] == null || !(this.inventory[2].getItem() instanceof ItemElementalCrystal)))
				return true;
			/*
			 * if(ItemStackHelper.getInstance().areItemsEqual(this.inventory[0],
			 * new ItemStack(Sorcery.rune, 1, 0)) && this.inventory[1].getItem()
			 * == Sorcery.magicOrb) { return true; } else
			 * if(ItemStackHelper.getInstance().areItemsEqual(this.inventory[2],
			 * new ItemStack(Sorcery.rune, 1, this.currentRune))) { return true;
			 * }
			 */
		}
		
		if(ItemStackHelper.getInstance().areItemsEqual(this.inventory[0], new ItemStack(SorceryItems.elementCrystal, 1, 0)) || ItemStackHelper.getInstance().areItemsEqual(this.inventory[2], new ItemStack(SorceryItems.elementCrystal, 1, 0))) {
			if((this.inventory[0] != null && this.inventory[0].getItem() instanceof ItemElementalCrystal) && (this.inventory[2] != null && this.inventory[2].getItem() instanceof ItemElementalCrystal) && this.inventory[1].getItem() == SorceryItems.magicOrb) {
				return true;
			}
			
			if(this.inventory[0] != null && this.inventory[0].getItem() instanceof ItemElementalCrystal && (this.inventory[2] == null || !(this.inventory[2].getItem() instanceof ItemEnergy)))
				return true;
			/*
			 * if(ItemStackHelper.getInstance().areItemsEqual(this.inventory[0],
			 * new ItemStack(Sorcery.elementCrystal, 1, 0)) &&
			 * this.inventory[1].getItem() == Sorcery.magicOrb) { return true; }
			 * else
			 * if(ItemStackHelper.getInstance().areItemsEqual(this.inventory[2],
			 * new ItemStack(Sorcery.elementCrystal, 1, this.currentRune))) {
			 * return true; }
			 */
		}
		return false;
	}
	
	public void smeltItem() {
		if(this.canCook()) {
			ItemStack result;
			if(this.inventory[0].getItem() instanceof ItemEnergy)
				result = new ItemStack(SorceryItems.energy, 1, this.currentRune);
			else
				result = new ItemStack(SorceryItems.elementCrystal, 1, this.currentRune);
			
			if(this.inventory[2] == null) {
				this.inventory[2] = result;
			} else {
				++this.inventory[2].stackSize;
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
	public int fill(int tankIndex, FluidStack resource, boolean doFill) {
		if(this.tankQuantity > tankMaxCapacity) {
			this.tankQuantity = tankMaxCapacity;
		}
		
		if(resource.fluidID == FluidRegistry.getFluidID("mojo") && this.tankQuantity < tankMaxCapacity) {
			this.tankQuantity += resource.amount;
			if(FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) {
				this.sendFluidPacket(0, new FluidStack(FluidRegistry.getFluidID("water"), this.tankQuantity));
			}
			return resource.amount;
		}
		return 0;
	}
	
	@Override
	public FluidStack drain(int tankIndex, int maxDrain, boolean doDrain) {
		if(this.tankQuantity < 0) {
			this.tankQuantity = 0;
		}
		
		if(this.tankQuantity > 0) {
			this.tankQuantity = this.tankQuantity - maxDrain;
			if(!worldObj.isRemote) {
				this.sendFluidPacket(0, new FluidStack(FluidRegistry.getFluidID("water"), this.tankQuantity));
			}
			return new FluidStack(FluidRegistry.getFluid("mojo"), maxDrain);
		}
		return new FluidStack(FluidRegistry.getFluid("mojo"), 0);
	}
	
	public void handleFluidPacket(FluidStack stack, int tankID, boolean isClient) {
		this.tankQuantity = stack.amount;
	}
	
	public int getScaledBurnTime(int i) {
		return this.tankQuantity * i / tankMaxCapacity;
	}
	
	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		return fill(0, resource, doFill);
	}
	
	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return drain(0, maxDrain, doDrain);
	}
	
	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return true;
	}
	
	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		return drain(0, resource.amount, doDrain);
	}
	
	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return(from != ForgeDirection.DOWN);
	}
	
	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return(from == ForgeDirection.DOWN);
	}
	
	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return new FluidTankInfo[]{new FluidTankInfo(new FluidStack(FluidRegistry.getFluidID("mojo"), this.tankQuantity), TileEntityRuneCrafting.tankMaxCapacity)};
	}
	
	@Override
	public int[] getAccessibleSlotsFromSide(int var1) {
		ForgeDirection side = ForgeDirection.getOrientation(var1);
		
		if(side == ForgeDirection.DOWN)
			return new int[]{1};
		
		if(side == ForgeDirection.UP)
			return new int[]{0};
		
		return new int[]{2};
	}
	
	@Override
	public boolean canInsertItem(int i, ItemStack itemstack, int j) {
		return(i != 2);
	}
	
	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return(i == 2);
	}
}
