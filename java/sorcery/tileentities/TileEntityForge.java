package sorcery.tileentities;

import ibxm.Player;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

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
import sorcery.lib.ItemStackHelper;
import sorcery.lib.SorceryItems;
import sorcery.recipes.ForgeRecipe;
import sorcery.recipes.ForgeRecipes;

import com.google.common.io.ByteArrayDataInput;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityForge extends TileEntitySorcery implements IInventory, ISidedInventory, IFluidHandler {
	public ItemStack[] inventory = new ItemStack[7];
	
	public FluidTank[] fuel = new FluidTank[1];
	
	// public static int this.getTankCapacity(0) =
	// FluidContainerRegistry.BUCKET_VOLUME * 10;
	
	// public int tankQuantity = 0;
	
	public int furnaceCookTime = 0;
	
	public int delay;
	
	public int temperature;
	
	public TileEntityForge() {
		this.fuel[0] = new FluidTank(FluidContainerRegistry.BUCKET_VOLUME * 10);
	}
	
	public int getSizeInventory() {
		return this.inventory.length;
	}
	
	public ItemStack getStackInSlot(int par1) {
		return this.inventory[par1];
	}
	
	public FluidTank[] getTanks() {
		return this.fuel;
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
		return "container.forge";
	}
	
	public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readFromNBT(par1NBTTagCompound);
		NBTTagList var2 = par1NBTTagCompound.getTagList("Items", 10);
		NBTTagList tanks = par1NBTTagCompound.getTagList("Tanks", 10);
		this.inventory = new ItemStack[this.getSizeInventory()];
		
		for(int var3 = 0; var3 < var2.tagCount(); ++var3) {
			NBTTagCompound var4 = (NBTTagCompound)var2.getCompoundTagAt(var3);
			byte var5 = var4.getByte("Slot");
			
			if(var5 >= 0 && var5 < this.inventory.length) {
				this.inventory[var5] = ItemStack.loadItemStackFromNBT(var4);
			}
		}
		
		for(int i = 0; i < tanks.tagCount(); i++) {
			NBTTagCompound tag = (NBTTagCompound)tanks.getCompoundTagAt(i);
			byte j = tag.getByte("Slot");
			
			if(j >= 0 && j < this.fuel.length) {
				this.fuel[j].setFluid(FluidStack.loadFluidStackFromNBT(tag));
			}
		}
		
		this.furnaceCookTime = par1NBTTagCompound.getShort("CookTime");
		// this.getTankAmount(0) =
		// par1NBTTagCompound.getInteger("TankQuantity");
		this.delay = par1NBTTagCompound.getInteger("delay");
		this.temperature = par1NBTTagCompound.getInteger("temp");
	}
	
	public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setShort("CookTime", (short)this.furnaceCookTime);
		// par1NBTTagCompound.setInteger("TankQuantity", this.getTankAmount(0));
		par1NBTTagCompound.setInteger("delay", this.delay);
		par1NBTTagCompound.setInteger("temp", this.temperature);
		NBTTagList var2 = new NBTTagList();
		NBTTagList tanks = new NBTTagList();
		
		for(int var3 = 0; var3 < this.inventory.length; ++var3) {
			if(this.inventory[var3] != null) {
				NBTTagCompound var4 = new NBTTagCompound();
				var4.setByte("Slot", (byte)var3);
				this.inventory[var3].writeToNBT(var4);
				var2.appendTag(var4);
			}
		}
		
		for(int i = 0; i < this.fuel.length; i++) {
			if(this.fuel[i].getFluid() != null) {
				NBTTagCompound tag = new NBTTagCompound();
				tag.setByte("Slot", (byte)i);
				this.fuel[i].getFluid().writeToNBT(tag);
				tanks.appendTag(tag);
				// System.out.println(i);
			}
		}
		
		par1NBTTagCompound.setTag("Items", var2);
		par1NBTTagCompound.setTag("Tanks", tanks);
	}
	
	public int getInventoryStackLimit() {
		return 64;
	}
	
	public int getCurrentItemCookTime() {
		if(ForgeRecipes.getInstance().findMatchingRecipe(this) != null) {
			return(ForgeRecipes.getInstance().findMatchingRecipe(this).burnTime);
		}
		return 0;
	}
	
	@SideOnly(Side.CLIENT)
	public int getCookProgressScaled(int par1) {
		if(this.getCurrentItemCookTime() == 0) {
			return 0;
		}
		return this.furnaceCookTime * par1 / this.getCurrentItemCookTime();
	}
	
	public int getHeatScaled(int par1) {
		if(this.temperature == 0) {
			return 0;
		}
		return this.temperature * par1 / 100;
	}
	
	public boolean isCooking() {
		return this.furnaceCookTime > 0;
	}
	
	public boolean hasFuel() {
		return this.fuel[0].getFluidAmount() > 0;
	}
	
	@Override
	public void markDirty() {
		if(this.inventory[6] != null && this.inventory[6].getItem() == Items.lava_bucket) {
			FluidStack Fluid = new FluidStack(FluidRegistry.getFluid("lava"), FluidContainerRegistry.BUCKET_VOLUME);
			
			if(Fluid != null && this.fuel[0].getFluidAmount() < this.fuel[0].getCapacity()) {
				fill(ForgeDirection.UNKNOWN, Fluid, true);
				this.setInventorySlotContents(6, (new ItemStack(Items.bucket, 1)));
			}
		}
		
		if(this.howManyItemsInCookingSlots() == 0) {
			this.furnaceCookTime = 0;
		}
	}
	
	public void updateEntity() {
		super.updateEntity();
		if(this.isTier2() && this.worldObj.provider.isHellWorld) {
			// Keep the tank from overflowing
			if(this.getTankAmount(0) > this.getTankCapacity(0)) {
				this.getTanks()[0].getFluid().amount = this.getTankCapacity(0);
			}
			
			if(this.getTankAmount(0) < 0) {
				this.getTanks()[0].getFluid().amount = 0;
			}
			
			if(this.furnaceCookTime < 0) {
				this.furnaceCookTime = 0;
			}
			
			if(this.delay >= 30 && this.temperature < 100) {
				this.temperature++;
				this.getTanks()[0].getFluid().amount--;
				this.delay = 0;
			}
			
			if(this.temperature > 100)
				this.temperature = 100;
			
			// Cook and Use up lava
			if(this.hasFuel() && this.canSmelt()) {
				if(this.furnaceCookTime == this.getCurrentItemCookTime() && this.getCurrentItemCookTime() != 0) {
					this.smeltItem();
				} else {
					this.furnaceCookTime += 2;
					this.getTanks()[0].getFluid().amount--;
				}
			}
			
			// Reverse the cooking if there's no recipe available
			if(!ForgeRecipes.getInstance().isMatchingRecipeAvailable(this) && this.furnaceCookTime > 0) {
				this.furnaceCookTime = 0;
			}
			
			// Reverse the cooking if there's no lava
			if(this.canSmelt() && !this.hasFuel() && this.furnaceCookTime != 0) {
				this.furnaceCookTime--;
			}
			
			// Reverse the cooking if it can't smelt
			if(!this.canSmelt() && this.hasFuel() && this.furnaceCookTime != 0) {
				this.furnaceCookTime = 0;
			}
			
			// Completely undo the cooking if it can't cook or has no fuel
			if(!this.canSmelt() && !this.hasFuel()) {
				this.furnaceCookTime = 0;
			}
		} else if(!this.isTier2()) {
			// Keep the tank from overflowing
			if(this.getTankAmount(0) > this.getTankCapacity(0)) {
				this.getTanks()[0].getFluid().amount = this.getTankCapacity(0);
			}
			
			if(this.getTankAmount(0) < 0) {
				this.getTanks()[0].getFluid().amount = 0;
			}
			
			if(this.furnaceCookTime < 0) {
				this.furnaceCookTime = 0;
			}
			
			if(this.delay == 40 && this.getTankAmount(0) > 0) {
				this.temperature++;
				this.getTanks()[0].getFluid().amount--;
				this.delay = 0;
			} else if(this.delay == 40 && this.getTankAmount(0) == 0) {
				if(this.worldObj.rand.nextInt(2) == 0 && this.worldObj.isRemote)
					this.temperature--;
				this.delay = 0;
			}
			
			if(this.temperature > 100)
				this.temperature = 100;
			
			if(this.temperature < 0)
				this.temperature = 0;
			
			this.delay++;
			
			// Cook and Use up lava
			if(this.hasFuel() && this.canSmelt()) {
				if(this.furnaceCookTime == this.getCurrentItemCookTime() && this.getCurrentItemCookTime() != 0) {
					this.smeltItem();
				} else {
					this.furnaceCookTime++;
					this.getTanks()[0].getFluid().amount--;
				}
			}
			
			// Reverse the cooking if there's no recipe available
			if(!ForgeRecipes.getInstance().isMatchingRecipeAvailable(this) && this.furnaceCookTime > 0) {
				this.furnaceCookTime = 0;
			}
			
			// Reverse the cooking if there's no lava
			if(this.canSmelt() && !this.hasFuel() && this.furnaceCookTime != 0) {
				this.furnaceCookTime--;
			}
			
			// Reverse the cooking if it can't smelt
			if(!this.canSmelt() && this.hasFuel() && this.furnaceCookTime != 0) {
				this.furnaceCookTime = 0;
			}
			
			// Completely undo the cooking if it can't cook or has no fuel
			if(!this.canSmelt() && !this.hasFuel()) {
				this.furnaceCookTime = 0;
			}
		}
	}
	
	private boolean isTier2() {
		if(this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord) != 3) {
			return false;
		} else {
			return true;
		}
	}
	
	private boolean canSmelt() {
		if(this.inventory[0] == null && this.inventory[1] == null && this.inventory[2] == null && this.inventory[3] == null) {
			return false;
		} else if(this.inventory[4] == null) {
			return false;
		} else {
			ForgeRecipe recipe = ForgeRecipes.getInstance().findMatchingRecipe(this);
			if(recipe == null)
				return false;
			if(this.temperature < recipe.temperature)
				return false;
			ItemStack var1 = recipe.recipeResult;
			if(this.inventory[5] == null)
				return true;
			if(!this.inventory[5].isItemEqual(var1))
				return false;
			int result = inventory[5].stackSize + var1.stackSize;
			return(result <= getInventoryStackLimit() && result <= var1.getMaxStackSize());
		}
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
	
	public void smeltItem() {
		if(this.canSmelt()) {
			ForgeRecipe recipe = ForgeRecipes.getInstance().findMatchingRecipe(this);
			ItemStack result = recipe.recipeResult;
			ItemStack[] ingredients = new ItemStack[recipe.recipeIngredients.size()];
			
			for(int i = 0; i < ingredients.length; i++) {
				ingredients[i] = (ItemStack)recipe.recipeIngredients.get(i);
			}
			
			if(result != null) {
				if(this.inventory[5] == null) {
					this.inventory[5] = new ItemStack(result.getItem(), result.stackSize, result.getItemDamage());
				} else if(this.inventory[5].isItemEqual(result)) {
					inventory[5].stackSize += result.stackSize;
				}
				
				for(int i = 0; i < ingredients.length; i++) {
					ItemStackHelper.instance.removeItemsFromInventoryInRange(this.inventory, ingredients[i], 0, 4);
				}
				
				if(FluidContainerRegistry.isFilledContainer(recipe.recipeResult) && recipe.mould != null &&
						recipe.recipeResult.getItem().getContainerItem().equals(recipe.mould.getItem())) {
					this.decrStackSize(4, 1);
				}
				
				for(int i = 0; i < 4; i++) {
					if(this.inventory[i] != null && this.inventory[i].stackSize <= 0) {
						this.inventory[i] = null;
					}
				}
				
				if(this.inventory[4] != null && this.inventory[4].getItem().equals(SorceryItems.hardClay)) {
					if(this.worldObj.rand.nextInt(5) == 0 && this.worldObj.isRemote)
						this.decrStackSize(4, 1);
				}
			}
		}
		
		this.furnaceCookTime = 0;
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
		return drain(0, maxDrain, doDrain);
	}
	
	public int getScaledBurnTime(int i) {
		return this.getTankAmount(0) * i / this.getTankCapacity(0);
	}
	
	@Override
	public void handleFluidPacket(FluidStack stack, int tankID, boolean isClient) {
		this.fuel[0].setFluid(stack);
	}
	
	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		return drain(0, resource.amount, doDrain);
	}
	
	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		if(fluid.getName() != "lava")
			return false;
		return(from != ForgeDirection.DOWN);
	}
	
	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return(from == ForgeDirection.DOWN);
	}
	
	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return new FluidTankInfo[]{new FluidTankInfo(new FluidStack(FluidRegistry.getFluidID("lava"), this.getTankAmount(0)), this.getTankCapacity(0))};
	}
	
	@Override
	public int[] getAccessibleSlotsFromSide(int var1) {
		ForgeDirection side = ForgeDirection.getOrientation(var1);
		
		if(side == ForgeDirection.DOWN)
			return new int[]{5};
		
		if(side == ForgeDirection.UP)
			return new int[]{0, 1, 2, 3};
		
		return new int[]{4};
	}
	
	@Override
	public boolean canInsertItem(int i, ItemStack itemstack, int j) {
		if(i == 5)
			return false;
		
		if(i == 6 && itemstack.getItem() != Items.bucket)
			return false;
		
		return true;
	}
	
	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return this.canInsertItem(i, itemstack, 0);
	}
	
	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		if(i == 4 || i == 5 || i == 6)
			return true;
		
		return false;
	}
}
