package sorcery.containers;

import java.util.Iterator;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import sorcery.core.Sorcery;
import sorcery.tileentities.TileEntityForge;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerForge extends Container {
	private TileEntityForge tile;
	private World worldObj;
	private int posX;
	private int posY;
	private int posZ;
	
	public ContainerForge(InventoryPlayer player, TileEntityForge tile, World world, int i, int j, int k) {
		this.tile = tile;
		this.worldObj = world;
		this.posX = i;
		this.posY = j;
		this.posZ = k;
		this.addSlotToContainer(new Slot(tile, 0, 26, 19));
		this.addSlotToContainer(new Slot(tile, 1, 44, 19));
		this.addSlotToContainer(new Slot(tile, 2, 26, 37));
		this.addSlotToContainer(new Slot(tile, 3, 44, 37));
		this.addSlotToContainer(new Slot(tile, 4, 71, 28));
		this.addSlotToContainer(new SlotFurnace(player.player, tile, 5, 116, 28));
		this.addSlotToContainer(new Slot(tile, 6, 116, 61));
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
		par1ICrafting.sendProgressBarUpdate(this, 0, this.tile.furnaceCookTime);
		par1ICrafting.sendProgressBarUpdate(this, 1, this.tile.getTankAmount(0));
	}
	
	/** Updates crafting matrix; called from onCraftMatrixChanged. Args: none */
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		Iterator var1 = this.crafters.iterator();
		
		while(var1.hasNext()) {
			ICrafting var2 = (ICrafting)var1.next();
			var2.sendProgressBarUpdate(this, 0, this.tile.furnaceCookTime);
			var2.sendProgressBarUpdate(this, 1, this.tile.getTankAmount(0));
		}
	}
	
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int par1, int par2) {
		this.tile.furnaceCookTime = par1;
	}
	
	public boolean canInteractWith(EntityPlayer player) {
		return this.worldObj.getBlock(this.posX, this.posY, this.posZ) != Sorcery.machine ? false : player.getDistanceSq((double)this.posX + 0.5D, (double)this.posY + 0.5D, (double)this.posZ + 0.5D) <= 64.0D;
	}
	
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
		ItemStack var3 = null;
		Slot var4 = (Slot)this.inventorySlots.get(par2);
		
		if(var4 != null && var4.getHasStack()) {
			ItemStack var5 = var4.getStack();
			var3 = var5.copy();
			
			if(par2 < 7) {
				if(!this.mergeItemStack(var5, 7, 43, true)) {
					return null;
				}
			}
			
			/*
			 * if(var5 == Item.bucketLava) {
			 * if(!this.mergeItemStack(var5, 6, 7, false)); { return null; } }
			 * if(var5 == Sorcery.hardClay) {
			 * if(!this.mergeItemStack(var5, 4, 5, false)); { return null; } }
			 * if(!this.mergeItemStack(var5, 0, 4, false)) { return null; }
			 */
			if(var5.stackSize == 0) {
				var4.putStack((ItemStack)null);
			} else {
				var4.onSlotChanged();
			}
			
			if(var5.stackSize == var3.stackSize) {
				return null;
			}
			
			var4.onPickupFromSlot(par1EntityPlayer, var5);
		}
		
		return var3;
	}
}
