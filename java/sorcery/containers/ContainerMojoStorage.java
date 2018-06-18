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
import sorcery.tileentities.TileEntityMojoStorage;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerMojoStorage extends Container {
	private TileEntityMojoStorage tile;
	private World worldObj;
	private int posX;
	private int posY;
	private int posZ;
	
	public ContainerMojoStorage(InventoryPlayer player, TileEntityMojoStorage tile, World world, int i, int j, int k) {
		this.tile = tile;
		this.worldObj = world;
		this.posX = i;
		this.posY = j;
		this.posZ = k;
		this.addSlotToContainer(new Slot(tile, 0, 116, 36));
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
		Iterator var1 = this.crafters.iterator();
		
		while(var1.hasNext()) {
			ICrafting var2 = (ICrafting)var1.next();
			if(this.tile != null)
				var2.sendProgressBarUpdate(this, 0, this.tile.getMojo());
		}
	}

	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		Iterator var1 = this.crafters.iterator();
		
		while(var1.hasNext()) {
			ICrafting var2 = (ICrafting)var1.next();
			if(this.tile != null)
				var2.sendProgressBarUpdate(this, 0, this.tile.getMojo());
		}
	}
	
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int index, int data) {
		switch(index) {
			case 0: this.tile.setMojo(data);
		}
	}
	
	public boolean canInteractWith(EntityPlayer player) {
		return this.worldObj.getBlock(this.posX, this.posY, this.posZ) != Sorcery.mojoMachine ? false : player.getDistanceSq((double)this.posX + 0.5D, (double)this.posY + 0.5D, (double)this.posZ + 0.5D) <= 64.0D;
	}
	
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
		ItemStack var2 = null;
		Slot var3 = (Slot)this.inventorySlots.get(par2);
		
		if(var3 != null && var3.getHasStack()) {
			ItemStack var4 = var3.getStack();
			var2 = var4.copy();
			
			if(par2 < 2) {
				if(!this.mergeItemStack(var4, 2, 38, true)) {
					return null;
				}
				var3.onSlotChange(var4, var2);
			}
			/*
			 * else if (par2 >= 29 && par2 < 38 && !this.mergeItemStack(var4, 2,
			 * 38, false)) { return null; } else if (!this.mergeItemStack(var4,
			 * 2, 38, false)) { return null; }
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
