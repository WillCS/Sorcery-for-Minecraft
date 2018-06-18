package sorcery.tileentities;

import ibxm.Player;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import sorcery.lib.ItemStackHelper;
import sorcery.lib.TankHelper;
import sorcery.network.TileEntityPacket;
import sorcery.recipes.MixerRecipe;
import sorcery.recipes.MixerRecipes;

import com.google.common.io.ByteArrayDataInput;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityMixer extends TileEntitySorcery implements IInventory, ISidedInventory, IFluidHandler {
	public ItemStack[] inventory = new ItemStack[6];
	
	public FluidTank[] tanks = new FluidTank[2];
	
	public int recipeCookTime;
	
	public boolean shouldFillTank1 = true;
	
	public TileEntityMixer() {
		this.tanks[0] = new FluidTank(FluidContainerRegistry.BUCKET_VOLUME * 16);
		this.tanks[1] = new FluidTank(FluidContainerRegistry.BUCKET_VOLUME * 16);
	}
	
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
		return "container.mixer";
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
				this.tanks[j].setFluid(FluidStack.loadFluidStackFromNBT(tag));
			}
		}
		
		this.recipeCookTime = par1NBTTagCompound.getShort("CookTime");
		this.shouldFillTank1 = par1NBTTagCompound.getBoolean("tankFlag");
	}
	
	public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeToNBT(par1NBTTagCompound);
		NBTTagList items = new NBTTagList();
		NBTTagList tanks = new NBTTagList();
		par1NBTTagCompound.setShort("CookTime", (short)this.recipeCookTime);
		par1NBTTagCompound.setBoolean("tankFlag", this.shouldFillTank1);
		
		for(int i = 0; i < this.inventory.length; i++) {
			if(this.inventory[i] != null) {
				NBTTagCompound tag = new NBTTagCompound();
				tag.setByte("Slot", (byte)i);
				this.inventory[i].writeToNBT(tag);
				items.appendTag(tag);
			}
		}
		
		for(int i = 0; i < this.tanks.length; i++) {
			if(this.tanks[i].getFluid() != null) {
				NBTTagCompound tag = new NBTTagCompound();
				tag.setByte("Slot", (byte)i);
				this.tanks[i].getFluid().writeToNBT(tag);
				tanks.appendTag(tag);
			}
		}
		
		par1NBTTagCompound.setTag("Items", items);
		par1NBTTagCompound.setTag("Tanks", tanks);
	}
	
	public int getInventoryStackLimit() {
		return 64;
	}
	
	public int getCurrentRecipeCookTime() {
		return MixerRecipes.getInstance().findMatchingRecipe(this) == null ? 0 : MixerRecipes.getInstance().findMatchingRecipe(this).cookTime;
	}
	
	@SideOnly(Side.CLIENT)
	public int getCookProgressScaled(int par1) {
		if(this.getCurrentRecipeCookTime() == 0) {
			return 0;
		}
		return this.recipeCookTime * par1 / this.getCurrentRecipeCookTime();
	}
	
	public boolean isCooking() {
		return this.recipeCookTime > 0;
	}
	
	public boolean hasFuel() {
		return this.tanks[0].getFluid().amount > 0;
	}

	public void updateEntity() {
		super.updateEntity();
		this.checkBucketSlots();
		if(this.canCook()) {
			if(this.recipeCookTime >= MixerRecipes.getInstance().findMatchingRecipe(this).cookTime) {
				this.cookRecipe();
			} else {
				this.recipeCookTime++;
			}
			this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
		}
		
		if(MixerRecipes.getInstance().findMatchingRecipe(this) == null) {
			this.recipeCookTime = 0;
		}
	}
	
	public void checkBucketSlots() {
		if(this.tanks[1].getFluid() != null & this.inventory[5] != null && this.tanks[1].getFluid() != null && TankHelper.instance.canThisItemContainThisFluid(this.inventory[5], this.tanks[1].getFluid())) {
			if(this.tanks[1].getFluid() != null && TankHelper.instance.canThisItemContainThisFluid(this.inventory[5], this.tanks[1].getFluid())) {
				if(TankHelper.instance.GetFilledContainer(this.inventory[5], this.tanks[1].getFluid()) != null && this.inventory[5].stackSize == 1) {
					this.inventory[5] = FluidContainerRegistry.fillFluidContainer(this.tanks[1].getFluid().copy(), this.inventory[5].copy());
					this.drain(1, FluidContainerRegistry.getFluidForFilledItem(this.inventory[5]).amount, true);
					return;
				}
			}
		}
		
		if(this.inventory[4] == null) {
			return;
		}
		
		if(this.shouldFillTank1 && FluidContainerRegistry.isFilledContainer(this.inventory[4])) {
			if(this.tanks[0].getFluid() != null && FluidContainerRegistry.containsFluid(this.inventory[4], this.tanks[0].getFluid())) {
				if(this.tanks[0].getFluid().amount < this.tanks[0].getCapacity()) {
					this.fill(0, FluidContainerRegistry.getFluidForFilledItem(this.inventory[4]), true);
					if(this.inventory[4].getItem().getContainerItem() != null && this.inventory[4].stackSize == 1) {
						this.inventory[4] = this.inventory[4].getItem().getContainerItem(this.inventory[4]);
						return;
					} else {
						this.inventory[4] = null;
						return;
					}
				}
			} else if(this.tanks[0].getFluid() == null) {
				this.fill(0, FluidContainerRegistry.getFluidForFilledItem(this.inventory[4]), true);
				if(this.inventory[4].getItem().getContainerItem() != null && this.inventory[4].stackSize == 1) {
					this.inventory[4] = this.inventory[4].getItem().getContainerItem(this.inventory[4]);
					return;
				} else {
					this.inventory[4] = null;
					return;
				}
			}
		} else if(!this.shouldFillTank1 && FluidContainerRegistry.isEmptyContainer(inventory[4])) {
			if(!this.shouldFillTank1 && this.tanks[0].getFluid() != null && TankHelper.instance.canThisItemContainThisFluid(this.inventory[4], this.tanks[0].getFluid())) {
				if(!this.shouldFillTank1 && TankHelper.instance.GetFilledContainer(this.inventory[4], this.tanks[0].getFluid()) != null && this.inventory[4].stackSize == 1) {
					this.inventory[4] = FluidContainerRegistry.fillFluidContainer(this.tanks[0].getFluid().copy(), this.inventory[4].copy());
					this.drain(0, FluidContainerRegistry.getFluidForFilledItem(this.inventory[4]).amount, true);
					return;
				}
			}
		}
	}
	
	private boolean canCook() {
		if(this.inventory[0] == null && this.inventory[1] == null && this.inventory[2] == null && this.inventory[3] == null) {
			return false;
		} else {
			MixerRecipe recipe = MixerRecipes.getInstance().findMatchingRecipe(this);
			if(recipe == null || (this.tanks[0].getFluid() == null || this.tanks[0].getFluid().amount < recipe.starterFluid.amount)) {
				return false;
			}
			if(this.tanks[1].getFluid() == null || this.tanks[1].getFluid().amount < FluidContainerRegistry.BUCKET_VOLUME) {
				return true;
			}
			if(this.tanks[1].getFluid().isFluidEqual(recipe.recipeResult)) {
				return true;
			}
		}
		return false;
	}
	
	public int howManyItemsInCookingSlots() {
		int result = 0;
		
		for(int i = 0; i < 4; i++) {
			if(this.inventory[i] != null) {
				result++;
			}
		}
		
		return result;
	}
	
	public ItemStack[] getItemsInCookingSlots() {
		ItemStack[] item = new ItemStack[this.howManyItemsInCookingSlots()];
		
		int slot = 0;
		
		for(int i = 0; i < 4; i++) {
			if(this.inventory[i] != null) {
				item[slot] = this.inventory[i];
				slot++;
			}
		}
		
		return item;
	}
	
	public void cookRecipe() {
		if(this.canCook()) {
			MixerRecipe recipe = MixerRecipes.getInstance().findMatchingRecipe(this);
			FluidStack result = recipe.recipeResult;
			ItemStack[] ingredients = new ItemStack[recipe.recipeIngredients.size()];
			
			for(int i = 0; i < ingredients.length; i++) {
				ingredients[i] = (ItemStack)recipe.recipeIngredients.get(i);
			}
			
			if(result != null) {
				this.fill(1, result, true);
				this.drain(0, recipe.starterFluid.amount, true);
				// int j;
				for(int i = 0; i < ingredients.length; i++) {
					ItemStackHelper.instance.removeItemsFromInventoryInRange(this.inventory, ingredients[i], 0, 4);
					/*
					 * if(this.inventory[i].getItem().getContainerItem() ==
					 * Item.bucketEmpty) { this.inventory[i] = new
					 * ItemStack(Item.bucketEmpty, 1); } else
					 * if(this.inventory[i].stackSize ==
					 * ingredients[j].stackSize) { this.inventory[i].stackSize =
					 * 0; } else { this.inventory[i].stackSize -=
					 * ingredients[j].stackSize; }
					 */
				}
				
				for(int i = 0; i < 4; i++) {
					if(this.inventory[i] != null && this.inventory[i].stackSize <= 0) {
						this.inventory[i] = null;
					}
				}
			}
		}
		
		this.recipeCookTime = 0;
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
		return fill(0, resource, doFill);
	}
	
	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		if(from == ForgeDirection.UP)
			return drain(1, maxDrain, doDrain);
		else
			return drain(0, maxDrain, doDrain);
	}
	
	public int getScaledTankQuantity(int i, int tank) {
		return this.tanks[tank].getFluid().amount * i / this.tanks[tank].getCapacity();
	}
	
	public void sendNormalPacket(int data) {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setInteger("data", data);
		this.sendNBTPacket(tag);
	}
	
	@Override
	public void handlePacketData(NBTTagCompound tag, boolean isClient) {
		int data = tag.getInteger("data");
		
		switch(data) {
			case 2:
				if(this.shouldFillTank1)
					this.shouldFillTank1 = false;
				else
					this.shouldFillTank1 = true;
				
				break;
			case 3:
				if(this.tanks[0].getFluid() != null) {
					this.drain(0, this.tanks[0].getFluid().amount, true);
				}
				if(!this.worldObj.isRemote)
					this.sendNormalPacket(3);
				break;
			case 4:
				if(this.tanks[1].getFluid() != null) {
					this.drain(1, this.tanks[1].getFluid().amount, true);
				}
				if(!this.worldObj.isRemote)
					this.sendNormalPacket(4);
				break;
		}
	}
	
	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return true;
	}
	
	@Override
	public int[] getAccessibleSlotsFromSide(int var1) {
		return new int[]{0, 1, 2, 3};
	}
	
	@Override
	public boolean canInsertItem(int i, ItemStack itemstack, int j) {
		return true;
	}
	
	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
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
		return new FluidTankInfo[]{new FluidTankInfo(this.tanks[0]), new FluidTankInfo(this.tanks[1])};
	}
}
