package sorcery.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import sorcery.core.Sorcery;
import sorcery.tileentities.TileEntityTinkering;

public class ContainerTinkering extends Container {
	public TileEntityTinkering items;
	public IInventory craftResult = new InventoryCraftResult();
	private World worldObj;
	private int posX;
	private int posY;
	private int posZ;
	
	public ContainerTinkering(InventoryPlayer player, TileEntityTinkering tile, World world, int par3, int par4, int par5) {
		this.worldObj = world;
		this.posX = par3;
		this.posY = par4;
		this.posZ = par5;
		this.items = tile;
		this.addSlotToContainer(new SlotCrafting(player.player, this.items, this.craftResult, 0, 124, 35));
		int var6;
		int var7;
		
		for(var6 = 0; var6 < 3; ++var6) {
			for(var7 = 0; var7 < 3; ++var7) {
				this.addSlotToContainer(new Slot(this.items, var7 + var6 * 3, 30 + var7 * 18, 17 + var6 * 18));
			}
		}
		
		for(var6 = 0; var6 < 3; ++var6) {
			for(var7 = 0; var7 < 9; ++var7) {
				this.addSlotToContainer(new Slot(player, var7 + var6 * 9 + 9, 8 + var7 * 18, 84 + var6 * 18));
			}
		}
		
		for(var6 = 0; var6 < 9; ++var6) {
			this.addSlotToContainer(new Slot(player, var6, 8 + var6 * 18, 142));
		}
		
		this.onCraftMatrixChanged(this.items);
	}
	
	public void addCraftingToCrafters(ICrafting par1ICrafting) {
		super.addCraftingToCrafters(par1ICrafting);
	}
	
	/*
	 * public void onitemsChanged(IInventory par1IInventory) {
	 * this.craftResult.setInventorySlotContents(0,
	 * TinkeringRecipes.getInstance().findMatchingRecipe(this.items)); }
	 */
	
	@Override
	public ItemStack slotClick(int i, int j, int k, EntityPlayer entityplayer) {
		craftResult.setInventorySlotContents(0, items.findRecipe());
		
		ItemStack stack = super.slotClick(i, j, k, entityplayer);
		onCraftMatrixChanged(items);
		
		return stack;
	}
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		craftResult.setInventorySlotContents(0, items.findRecipe());
	}
	
	@Override
	public void onCraftMatrixChanged(IInventory par1IInventory) {
		this.detectAndSendChanges();
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
			
			if(par2 == 0) {
				if(!this.mergeItemStack(var4, 10, 46, true)) {
					return null;
				}
				
				var3.onSlotChange(var4, var2);
			} else if(par2 >= 10 && par2 < 37) {
				if(!this.mergeItemStack(var4, 37, 46, false)) {
					return null;
				}
			} else if(par2 >= 37 && par2 < 46) {
				if(!this.mergeItemStack(var4, 10, 37, false)) {
					return null;
				}
			} else if(!this.mergeItemStack(var4, 10, 46, false)) {
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
