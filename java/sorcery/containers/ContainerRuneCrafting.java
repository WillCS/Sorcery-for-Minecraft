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
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import sorcery.core.Sorcery;
import sorcery.guis.slot.SlotExclusiveItem;
import sorcery.lib.SorceryItems;
import sorcery.tileentities.TileEntityRuneCrafting;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerRuneCrafting extends Container {
	private TileEntityRuneCrafting tile;
	private World worldObj;
	private int posX;
	private int posY;
	private int posZ;
	private int lastCookTime;
	private int lastRuneCookTime;
	private int lastTankQuantity;
	private int lastRune;
	
	public ContainerRuneCrafting(InventoryPlayer player, TileEntityRuneCrafting tile, World world, int i, int j, int k) {
		this.tile = tile;
		this.worldObj = world;
		this.posX = i;
		this.posY = j;
		this.posZ = k;
		this.addSlotToContainer(new Slot(tile, 0, 41, 28));
		this.addSlotToContainer(new SlotExclusiveItem(tile, 1, 82, 28, SorceryItems.magicOrb));
		this.addSlotToContainer(new SlotFurnace(player.player, tile, 2, 116, 28));
		this.addSlotToContainer(new Slot(tile, 3, 116, 61));
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
	
	@Override
	public void addCraftingToCrafters(ICrafting par1ICrafting) {
		super.addCraftingToCrafters(par1ICrafting);
		par1ICrafting.sendProgressBarUpdate(this, 0, this.tile.currentCookTime);
		par1ICrafting.sendProgressBarUpdate(this, 1, this.tile.runeCookTime);
		par1ICrafting.sendProgressBarUpdate(this, 2, this.tile.tankQuantity);
		par1ICrafting.sendProgressBarUpdate(this, 3, this.tile.currentRune);
	}
	
	/** Updates crafting matrix; called from onCraftMatrixChanged. Args: none */
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		Iterator i = this.crafters.iterator();
		
		while(i.hasNext()) {
			ICrafting crafter = (ICrafting)i.next();
			
			if(this.lastCookTime != this.tile.currentCookTime) {
				crafter.sendProgressBarUpdate(this, 0, this.tile.currentCookTime);
			}
			
			if(this.lastRuneCookTime != this.tile.runeCookTime) {
				crafter.sendProgressBarUpdate(this, 1, this.tile.runeCookTime);
			}
			
			if(this.lastTankQuantity != this.tile.tankQuantity) {
				crafter.sendProgressBarUpdate(this, 2, this.tile.tankQuantity);
			}
			
			if(this.lastRune != this.tile.currentRune) {
				crafter.sendProgressBarUpdate(this, 3, this.tile.currentRune);
			}
		}
		
		this.lastCookTime = this.tile.currentCookTime;
		this.lastRuneCookTime = this.tile.runeCookTime;
		this.lastTankQuantity = this.tile.tankQuantity;
		this.lastRune = this.tile.currentRune;
	}
	
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int progress) {
		this.tile.currentCookTime = progress;
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
			
			if(par2 == 2) {
				if(!this.mergeItemStack(var4, 4, 40, true)) {
					return null;
				}
				
				var3.onSlotChange(var4, var2);
			} else if(par2 != 1 && par2 != 0) {
				if(var4.getItem() == SorceryItems.energy || var4.getItem() == SorceryItems.elementCrystal) {
					if(!this.mergeItemStack(var4, 0, 1, false)) {
						return null;
					}
				} else if(var4.getItem() == SorceryItems.magicOrb) {
					if(!this.mergeItemStack(var4, 1, 2, false)) {
						return null;
					}
				} else if(FluidContainerRegistry.isFilledContainer(var4) && FluidContainerRegistry.getFluidForFilledItem(var4).fluidID == FluidRegistry.getFluidID("mojo")) {
					if(!this.mergeItemStack(var4, 3, 4, false)) {
						return null;
					}
				} else if(par2 >= 4 && par2 < 31) {
					if(!this.mergeItemStack(var4, 31, 40, false)) {
						return null;
					}
				} else if(par2 >= 31 && par2 < 40 && !this.mergeItemStack(var4, 4, 40, false)) {
					return null;
				}
			} else if(!this.mergeItemStack(var4, 4, 40, false)) {
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
