package sorcery.tileentities;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import com.jcraft.jogg.Packet;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityHellFurnace extends TileEntitySorcery implements IInventory, ISidedInventory {
	private static final int[] slots_top = new int[]{0};
	private static final int[] slots_bottom = new int[]{2, 1};
	private static final int[] slots_sides = new int[]{1};
	
	private ItemStack[] furnaceItemStacks = new ItemStack[3];
	
	public int furnaceBurnTime = 0;
	
	public int currentItemBurnTime = 0;
	
	public int furnaceCookTime = 0;
	
	public int getSizeInventory() {
		return this.furnaceItemStacks.length;
	}
	
	public ItemStack getStackInSlot(int par1) {
		return this.furnaceItemStacks[par1];
	}
	
	public ItemStack decrStackSize(int par1, int par2) {
		if(this.furnaceItemStacks[par1] != null) {
			ItemStack var3;
			
			if(this.furnaceItemStacks[par1].stackSize <= par2) {
				var3 = this.furnaceItemStacks[par1];
				this.furnaceItemStacks[par1] = null;
				return var3;
			} else {
				var3 = this.furnaceItemStacks[par1].splitStack(par2);
				
				if(this.furnaceItemStacks[par1].stackSize == 0) {
					this.furnaceItemStacks[par1] = null;
				}
				
				return var3;
			}
		} else {
			return null;
		}
	}
	
	public ItemStack getStackInSlotOnClosing(int par1) {
		if(this.furnaceItemStacks[par1] != null) {
			ItemStack var2 = this.furnaceItemStacks[par1];
			this.furnaceItemStacks[par1] = null;
			return var2;
		} else {
			return null;
		}
	}
	
	public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {
		this.furnaceItemStacks[par1] = par2ItemStack;
		
		if(par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit()) {
			par2ItemStack.stackSize = this.getInventoryStackLimit();
		}
	}
	
	public String getInventoryName() {
		return "container.hellFurnace";
	}
	
	public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readFromNBT(par1NBTTagCompound);
		NBTTagList var2 = par1NBTTagCompound.getTagList("Items", 10);
		this.furnaceItemStacks = new ItemStack[this.getSizeInventory()];
		
		for(int var3 = 0; var3 < var2.tagCount(); ++var3) {
			NBTTagCompound var4 = (NBTTagCompound)var2.getCompoundTagAt(var3);
			byte var5 = var4.getByte("Slot");
			
			if(var5 >= 0 && var5 < this.furnaceItemStacks.length) {
				this.furnaceItemStacks[var5] = ItemStack.loadItemStackFromNBT(var4);
			}
		}
		
		this.furnaceBurnTime = par1NBTTagCompound.getShort("BurnTime");
		this.furnaceCookTime = par1NBTTagCompound.getShort("CookTime");
		this.currentItemBurnTime = getItemBurnTime(this.furnaceItemStacks[1]);
	}
	
	public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setShort("BurnTime", (short)this.furnaceBurnTime);
		par1NBTTagCompound.setShort("CookTime", (short)this.furnaceCookTime);
		NBTTagList var2 = new NBTTagList();
		
		for(int var3 = 0; var3 < this.furnaceItemStacks.length; ++var3) {
			if(this.furnaceItemStacks[var3] != null) {
				NBTTagCompound var4 = new NBTTagCompound();
				var4.setByte("Slot", (byte)var3);
				this.furnaceItemStacks[var3].writeToNBT(var4);
				var2.appendTag(var4);
			}
		}
		
		par1NBTTagCompound.setTag("Items", var2);
	}
	
	public int getInventoryStackLimit() {
		return 64;
	}
	
	@SideOnly(Side.CLIENT)
	public int getCookProgressScaled(int par1) {
		return this.furnaceCookTime * par1 / 200;
	}
	
	@SideOnly(Side.CLIENT)
	public int getBurnTimeRemainingScaled(int par1) {
		if(this.currentItemBurnTime == 0) {
			this.currentItemBurnTime = 200;
		}
		
		return this.furnaceBurnTime * par1 / this.currentItemBurnTime;
	}
	
	public boolean isBurning() {
		return this.furnaceBurnTime > 0;
	}
	
	public void updateEntity() {
		super.updateEntity();
		if(this.worldObj.provider.isHellWorld) {
			boolean var1 = this.furnaceBurnTime > 0;
			boolean var2 = false;
			
			if(this.furnaceBurnTime > 0) {
				--this.furnaceBurnTime;
			}
			
			if(!this.worldObj.isRemote) {
				if(this.furnaceBurnTime == 0 && this.canSmelt()) {
					this.currentItemBurnTime = this.furnaceBurnTime = getItemBurnTime(this.furnaceItemStacks[1]);
					
					if(this.furnaceBurnTime > 0) {
						var2 = true;
						
						if(this.furnaceItemStacks[1] != null) {
							--this.furnaceItemStacks[1].stackSize;
							
							if(this.furnaceItemStacks[1].stackSize == 0) {
								this.furnaceItemStacks[1] = this.furnaceItemStacks[1].getItem().getContainerItem(furnaceItemStacks[1]);
							}
						}
					}
				}
				
				if(this.isBurning() && this.canSmelt()) {
					this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
					this.furnaceCookTime = this.furnaceCookTime + 2;
					
					if(this.furnaceCookTime == 200) {
						this.furnaceCookTime = 0;
						this.smeltItem();
						var2 = true;
					}
				} else {
					this.furnaceCookTime = 0;
				}
				
				if(var1 != this.furnaceBurnTime > 0) {
					var2 = true;
					// BlockHellFurnace.updateFurnaceBlockState(this.furnaceBurnTime
					// > 0, this.worldObj, this.xCoord, this.yCoord,
					// this.zCoord);
				}
			}
			
			if(var2) {
				this.markDirty();
			}
		}
	}
	
	private boolean canSmelt() {
		if(this.furnaceItemStacks[0] == null) {
			return false;
		} else {
			ItemStack var1 = FurnaceRecipes.smelting().getSmeltingResult(this.furnaceItemStacks[0]);
			if(var1 == null)
				return false;
			if(this.furnaceItemStacks[2] == null)
				return true;
			if(!this.furnaceItemStacks[2].isItemEqual(var1))
				return false;
			int result = furnaceItemStacks[2].stackSize + var1.stackSize;
			return(result <= getInventoryStackLimit() && result <= var1.getMaxStackSize());
		}
	}
	
	public void smeltItem() {
		if(this.canSmelt()) {
			ItemStack var1 = FurnaceRecipes.smelting().getSmeltingResult(this.furnaceItemStacks[0]);
			
			if(this.furnaceItemStacks[2] == null) {
				this.furnaceItemStacks[2] = var1.copy();
			} else if(this.furnaceItemStacks[2].isItemEqual(var1)) {
				furnaceItemStacks[2].stackSize += var1.stackSize;
			}
			
			--this.furnaceItemStacks[0].stackSize;
			
			if(this.furnaceItemStacks[0].stackSize <= 0) {
				this.furnaceItemStacks[0] = null;
			}
		}
	}
	
	public static int getItemBurnTime(ItemStack par0ItemStack) {
		if(par0ItemStack == null) {
			return 0;
		} else {
			Item var2 = par0ItemStack.getItem();
			
			if(par0ItemStack.getItem() instanceof ItemBlock) {
				Block var3 = ((ItemBlock)par0ItemStack.getItem()).field_150939_a;
				
				if(var3 == Blocks.wooden_slab) {
					return 150 * 2;
				}
				
				if(var3.getMaterial() == Material.wood) {
					return 300 * 2;
				}
			}
			if(var2 instanceof ItemTool && ((ItemTool)var2).getToolMaterialName().equals("WOOD"))
				return 200 * 2;
			if(var2 instanceof ItemSword && ((ItemSword)var2).getToolMaterialName().equals("WOOD"))
				return 200 * 2;
			if(var2 instanceof ItemHoe && ((ItemHoe)var2).getToolMaterialName().equals("WOOD"))
				return 200 * 2;
			if(var2 == Items.stick)
				return 10 * 2;
			if(var2 == Items.coal)
				return 1600 * 2;
			if(var2 == Items.lava_bucket)
				return 20000 * 2;
			if(var2 instanceof ItemBlock && ((ItemBlock)var2).field_150939_a == Blocks.sapling)
				return 100 * 2;
			if(var2 == Items.blaze_rod)
				return 2400 * 2;
			return GameRegistry.getFuelValue(par0ItemStack) * 2;
		}
	}
	
	public static boolean isItemFuel(ItemStack par0ItemStack) {
		return getItemBurnTime(par0ItemStack) > 0;
	}
	
	public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer) {
		return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : par1EntityPlayer.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
	}
	
	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return false;
	}
	
	@Override
	public int[] getAccessibleSlotsFromSide(int par1) {
		return par1 == 0 ? slots_bottom : (par1 == 1 ? slots_top : slots_sides);
	}
	
	@Override
	public boolean canInsertItem(int par1, ItemStack par2ItemStack, int par3) {
		return this.isItemValidForSlot(par1, par2ItemStack);
	}
	
	@Override
	public boolean canExtractItem(int par1, ItemStack par2ItemStack, int par3) {
		return par3 != 0 || par1 != 1 || par2ItemStack.getItem() == Items.bucket;
	}
}
