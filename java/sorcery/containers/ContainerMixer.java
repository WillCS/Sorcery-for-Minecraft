package sorcery.containers;

import java.util.Iterator;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import sorcery.core.Sorcery;
import sorcery.tileentities.TileEntityMixer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerMixer extends Container {
	private TileEntityMixer tile;
	private World worldObj;
	private int posX;
	private int posY;
	private int posZ;
	
	public ContainerMixer(InventoryPlayer player, TileEntityMixer tile, World world, int i, int j, int k) {
		this.tile = tile;
		this.worldObj = world;
		this.posX = i;
		this.posY = j;
		this.posZ = k;
		this.addSlotToContainer(new Slot(tile, 0, 71, 43));
		this.addSlotToContainer(new Slot(tile, 1, 89, 43));
		this.addSlotToContainer(new Slot(tile, 2, 71, 61));
		this.addSlotToContainer(new Slot(tile, 3, 89, 61));
		this.addSlotToContainer(new Slot(tile, 4, 44, 19));
		this.addSlotToContainer(new Slot(tile, 5, 116, 19));
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
		par1ICrafting.sendProgressBarUpdate(this, 0, this.tile.recipeCookTime);
		if(this.tile.shouldFillTank1)
			par1ICrafting.sendProgressBarUpdate(this, 1, 0);
		else
			par1ICrafting.sendProgressBarUpdate(this, 1, 1);
		
	}
	
	/** Updates crafting matrix; called from onCraftMatrixChanged. Args: none */
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		Iterator var1 = this.crafters.iterator();
		
		while(var1.hasNext()) {
			ICrafting var2 = (ICrafting)var1.next();
			var2.sendProgressBarUpdate(this, 0, this.tile.recipeCookTime);
			if(this.tile.shouldFillTank1)
				var2.sendProgressBarUpdate(this, 1, 0);
			else
				var2.sendProgressBarUpdate(this, 1, 1);
		}
	}
	
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int index, int data) {
		switch(index) {
			case 0:
				this.tile.recipeCookTime = data;
				break;
			case 1:
				this.tile.shouldFillTank1 = data == 0 ? true : false;
				break;
		}
	}
	
	public boolean canInteractWith(EntityPlayer player) {
		return this.worldObj.getBlock(this.posX, this.posY, this.posZ) != Sorcery.machine ? false : player.getDistanceSq((double)this.posX + 0.5D, (double)this.posY + 0.5D, (double)this.posZ + 0.5D) <= 64.0D;
	}
	
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
		ItemStack var2 = null;
		Slot var3 = (Slot)this.inventorySlots.get(par2);
		
		if(var3 != null && var3.getHasStack()) {
			ItemStack var4 = var3.getStack();
			var2 = var4.copy();
			
			if(par2 < 6) {
				if(!this.mergeItemStack(var4, 6, 42, true)) {
					return null;
				}
				var3.onSlotChange(var4, var2);
			}
			
			/*
			 * if(FluidContainerRegistry.isFilledContainer(var4)) { if
			 * (!this.mergeItemStack(var4, 4, 5, false)) { return null; } }
			 * if(FluidContainerRegistry.isEmptyContainer(var4)) { if
			 * (!this.mergeItemStack(var4, 5, 6, false)) { return null; } }
			 */
			
			/*
			 * if(!this.mergeItemStack(var4, 0, 4, false)) { return null; }
			 */
			
			/*
			 * if (par2 >= 33 && par2 < 42 && !this.mergeItemStack(var4, 6, 42,
			 * false)) { return null; }
			 */
			
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
