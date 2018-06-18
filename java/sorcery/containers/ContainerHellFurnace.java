package sorcery.containers;

import java.util.Iterator;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.world.World;
import sorcery.core.Sorcery;
import sorcery.tileentities.TileEntityHellFurnace;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerHellFurnace extends Container {
	private TileEntityHellFurnace furnace;
	private World worldObj;
	private int posX;
	private int posY;
	private int posZ;
	private int lastCookTime = 0;
	private int lastBurnTime = 0;
	private int lastItemBurnTime = 0;
	
	public ContainerHellFurnace(InventoryPlayer player, TileEntityHellFurnace tile, World world, int i, int j, int k) {
		this.furnace = tile;
		this.worldObj = world;
		this.posX = i;
		this.posY = j;
		this.posZ = k;
		this.addSlotToContainer(new Slot(tile, 0, 56, 17));
		this.addSlotToContainer(new Slot(tile, 1, 56, 53));
		this.addSlotToContainer(new SlotFurnace(player.player, tile, 2, 116, 35));
		int var3;
		
		for(var3 = 0; var3 < 3; ++var3) {
			for(int var4 = 0; var4 < 9; ++var4) {
				this.addSlotToContainer(new Slot(player, var4 + var3 * 9 + 9, 8 + var4 * 18, 84 + var3 * 18));
			}
		}
		
		for(var3 = 0; var3 < 9; ++var3) {
			this.addSlotToContainer(new Slot(player, var3, 8 + var3 * 18, 142));
		}
	}
	
	public void addCraftingToCrafters(ICrafting par1ICrafting) {
		super.addCraftingToCrafters(par1ICrafting);
		par1ICrafting.sendProgressBarUpdate(this, 0, this.furnace.furnaceCookTime);
		par1ICrafting.sendProgressBarUpdate(this, 1, this.furnace.furnaceBurnTime);
		par1ICrafting.sendProgressBarUpdate(this, 2, this.furnace.currentItemBurnTime);
		par1ICrafting.sendProgressBarUpdate(this, 3, this.furnace.front);
	}
	
	/** Updates crafting matrix; called from onCraftMatrixChanged. Args: none */
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		Iterator var1 = this.crafters.iterator();
		
		while(var1.hasNext()) {
			ICrafting var2 = (ICrafting)var1.next();
			
			if(this.lastCookTime != this.furnace.furnaceCookTime) {
				var2.sendProgressBarUpdate(this, 0, this.furnace.furnaceCookTime);
			}
			
			if(this.lastBurnTime != this.furnace.furnaceBurnTime) {
				var2.sendProgressBarUpdate(this, 1, this.furnace.furnaceBurnTime);
			}
			
			if(this.lastItemBurnTime != this.furnace.currentItemBurnTime) {
				var2.sendProgressBarUpdate(this, 2, this.furnace.currentItemBurnTime);
			}
			var2.sendProgressBarUpdate(this, 3, this.furnace.front);
		}
		
		this.lastCookTime = this.furnace.furnaceCookTime;
		this.lastBurnTime = this.furnace.furnaceBurnTime;
		this.lastItemBurnTime = this.furnace.currentItemBurnTime;
	}
	
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int par1, int par2) {
		if(par1 == 0) {
			this.furnace.furnaceCookTime = par2;
		}
		
		if(par1 == 1) {
			this.furnace.furnaceBurnTime = par2;
		}
		
		if(par1 == 2) {
			this.furnace.currentItemBurnTime = par2;
		}
		
		if(par1 == 3) {
			this.furnace.front = par2;
		}
	}
	
	public boolean canInteractWith(EntityPlayer player) {
		return this.worldObj.getBlock(this.posX, this.posY, this.posZ) != Sorcery.machine ? false : player.getDistanceSq((double)this.posX + 0.5D, (double)this.posY + 0.5D, (double)this.posZ + 0.5D) <= 64.0D;
	}
	
	/** Called to transfer a stack from one inventory to the other eg. when shift
	 * clicking. */
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
		ItemStack var2 = null;
		Slot var3 = (Slot)this.inventorySlots.get(par2);
		
		if(var3 != null && var3.getHasStack()) {
			ItemStack var4 = var3.getStack();
			var2 = var4.copy();
			
			if(par2 == 2) {
				if(!this.mergeItemStack(var4, 3, 39, true)) {
					return null;
				}
				
				var3.onSlotChange(var4, var2);
			} else if(par2 != 1 && par2 != 0) {
				if(FurnaceRecipes.smelting().getSmeltingResult(var4) != null) {
					if(!this.mergeItemStack(var4, 0, 1, false)) {
						return null;
					}
				} else if(TileEntityHellFurnace.isItemFuel(var4)) {
					if(!this.mergeItemStack(var4, 1, 2, false)) {
						return null;
					}
				} else if(par2 >= 3 && par2 < 30) {
					if(!this.mergeItemStack(var4, 30, 39, false)) {
						return null;
					}
				} else if(par2 >= 30 && par2 < 39 && !this.mergeItemStack(var4, 3, 30, false)) {
					return null;
				}
			} else if(!this.mergeItemStack(var4, 3, 39, false)) {
				return null;
			}
			
			if(var4.stackSize == 0) {
				var3.putStack((ItemStack)null);
			} else {
				var3.onSlotChanged();
			}
			
			if(var4.stackSize == var2.stackSize) {
				return null;
			}
			
			var3.onSlotChanged();
		}
		
		return var2;
	}
}
