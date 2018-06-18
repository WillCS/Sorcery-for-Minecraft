package sorcery.tileentities;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import sorcery.recipes.TinkeringRecipes;

public class TileEntityTinkering extends TileEntitySorcery implements IInventory {
	private ItemStack[] items = new ItemStack[9];
	
	public EntityItem[] entities = new EntityItem[9];
	
	private int age;
	
	@Override
	public int getSizeInventory() {
		return 9;
	}
	
	public class LocalInventoryCrafting extends InventoryCrafting {
		public ItemStack[] stackList = items;
		
		public LocalInventoryCrafting() {
			super(new Container() {
				public boolean isUsableByPlayer(EntityPlayer entityplayer) {
					return false;
				}
				
				public boolean canInteractWith(EntityPlayer entityplayer) {
					return false;
				}
			}, 3, 3);
		}
		
	}
	
	public ItemStack findRecipe() {
		LocalInventoryCrafting inv = new LocalInventoryCrafting();
		
		for(int i = 0; i < getSizeInventory(); ++i) {
			ItemStack stack = getStackInSlot(i);
			
			inv.setInventorySlotContents(i, stack);
		}
		
		ItemStack recipe = TinkeringRecipes.getInstance().findMatchingRecipe(inv, this.worldObj);
		return recipe;
	}
	
	@Override
	public ItemStack getStackInSlot(int par1) {
		return this.items[par1];
	}
	
	public void updateEntity() {
		super.updateEntity();
		
		for(int i = 0; i < this.entities.length; i++) {
			if(entities[i] != null) {
				entities[i].age = this.age;
			}
			
			if(this.items[i] == null) {
				this.entities[i] = null;
			}
		}
		
		// this.age++;
	}
	
	public ItemStack getStackInRowAndColumn(int par1, int par2) {
		if(par1 >= 0 && par1 < 4) {
			int var3 = par1 + par2 * 3;
			return this.getStackInSlot(var3);
		} else {
			return null;
		}
	}
	
	public ItemStack decrStackSize(int par1, int par2) {
		if(this.items[par1] != null) {
			ItemStack var3;
			
			if(this.items[par1].stackSize <= par2) {
				var3 = this.items[par1];
				this.items[par1] = null;
				return var3;
			} else {
				var3 = this.items[par1].splitStack(par2);
				
				if(this.items[par1].stackSize == 0) {
					this.items[par1] = null;
				}
				return var3;
			}
		} else {
			return null;
		}
	}
	
	public ItemStack getStackInSlotOnClosing(int par1) {
		if(this.items[par1] != null) {
			ItemStack var2 = this.items[par1];
			this.items[par1] = null;
			return var2;
		} else {
			return null;
		}
	}
	
	public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {
		this.items[par1] = par2ItemStack;
		
		if(par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit()) {
			par2ItemStack.stackSize = this.getInventoryStackLimit();
		}
	}
	
	public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readFromNBT(par1NBTTagCompound);
		NBTTagList var2 = par1NBTTagCompound.getTagList("Items", 10);
		this.items = new ItemStack[this.getSizeInventory()];
		
		for(int var3 = 0; var3 < var2.tagCount(); ++var3) {
			NBTTagCompound var4 = (NBTTagCompound)var2.getCompoundTagAt(var3);
			int var5 = var4.getByte("Slot") & 255;
			
			if(var5 >= 0 && var5 < this.items.length) {
				this.items[var5] = ItemStack.loadItemStackFromNBT(var4);
				
				if(ItemStack.loadItemStackFromNBT(var4) != null) {
					this.entities[var5] = new EntityItem(this.worldObj, this.xCoord + 0.5, this.yCoord + 1.25, this.zCoord + 0.5, ItemStack.loadItemStackFromNBT(var4));
				}
			}
		}
	}
	
	public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeToNBT(par1NBTTagCompound);
		NBTTagList var2 = new NBTTagList();
		
		for(int var3 = 0; var3 < this.items.length; ++var3) {
			if(this.items[var3] != null) {
				NBTTagCompound var4 = new NBTTagCompound();
				var4.setByte("Slot", (byte)var3);
				this.items[var3].writeToNBT(var4);
				var2.appendTag(var4);
			}
		}
		
		par1NBTTagCompound.setTag("Items", var2);
	}
	
	public int getInventoryStackLimit() {
		return 64;
	}
	
	public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer) {
		return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : par1EntityPlayer.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
	}
	
	public void openInventory() {
	}
	
	public void closeInventory() {
	}
	
	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return false;
	}

	@Override
	public String getInventoryName() {
		return "container.tinkering";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}
}
