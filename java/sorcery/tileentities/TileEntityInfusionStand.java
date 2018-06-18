package sorcery.tileentities;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import sorcery.api.IInfusable;
import sorcery.api.element.ElementStack;
import sorcery.recipes.InfuserRecipe;

public class TileEntityInfusionStand extends TileEntitySorcery implements IInventory, ISidedInventory, IInfusable {
	public ItemStack[] inventory = new ItemStack[9];
	public ArrayList<ElementStack> elements = new ArrayList<ElementStack>();
	public InfuserRecipe matchedRecipe;
	
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
		return "container.infusionStand";
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
			}
		}
	}
	
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
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
	
	@Override
	public void markDirty() {

	}
	
	public void updateEntity() {
		super.updateEntity();
	}

	public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer) {
		return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : par1EntityPlayer.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
	}
	
	public void openChest() {
	}
	
	public void closeChest() {
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return true;
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

	@Override
	public boolean canInfuse(ElementStack stack) {
		return true;
	}

	@Override
	public int infuse(ElementStack stack) {
		return stack.amount;
	}

	@Override
	public float getInfusionCenter() {
		return 1.0F;
	}

	@Override
	public boolean onWrenched(World world, EntityPlayer player, ItemStack item, int x, int y, int z, int side) {
		if(!item.hasTagCompound())
			item.stackTagCompound = new NBTTagCompound();
		
		NBTTagCompound tag = new NBTTagCompound();
		tag.setInteger("x", x);
		tag.setInteger("y", y);
		tag.setInteger("z", z);
		item.stackTagCompound.setTag("storedPos", tag);
		return true;
	}
}
