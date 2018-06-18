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
import sorcery.guis.slot.SlotExclusiveItem;
import sorcery.lib.SorceryItems;
import sorcery.tileentities.nodes.TileEntityNodeBase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerNode extends Container {
	private TileEntityNodeBase node;
	private World worldObj;
	private int posX;
	private int posY;
	private int posZ;
	
	public ContainerNode(InventoryPlayer player, TileEntityNodeBase node, World world, int x, int y, int z) {
		this.node = node;
		this.worldObj = world;
		this.posX = x;
		this.posY = y;
		this.posZ = z;
		
		if(this.node.hasBuffer()) {
			int bufferSize = this.node.getBufferSize() == 3 ? 1 : 3;
			int xRows = this.node.guiDetails.bufferVertical ? 1 : 3;
			int yRows = this.node.guiDetails.bufferVertical ? 3 : 1;
			if(node.getBufferSize() != 3) {
				yRows = this.node.guiDetails.bufferVertical ? 1 : bufferSize;
				xRows = this.node.guiDetails.bufferVertical ? 3 : bufferSize;
			}
			
			for(int i = 0; i < yRows; ++i) {
				for(int j = 0; j < xRows; ++j) {
					this.addSlotToContainer(new Slot(this.node, j + i * 3, this.node.guiDetails.bufferX + j * 18, this.node.guiDetails.bufferY + i * 18));
				}
			}
		}
		
		if(!this.node.isInput && this.node.isOutput && node.getFilter().length != 0) {
			for(int i = 0; i < 3; ++i) {
				for(int j = 0; j < 3; ++j) {
					this.addSlotToContainer(new Slot(this.node, TileEntityNodeBase.filterSlotIDs + j + i * 3, this.node.guiDetails.filterX + j * 18, this.node.guiDetails.filterY + i * 18));
				}
			}
		}
		
		if(this.node.isInput)
			this.addSlotToContainer(new SlotExclusiveItem(this.node, TileEntityNodeBase.codeSlotID, this.node.guiDetails.codeX, this.node.guiDetails.codeY, SorceryItems.colourTag));
		
		if(this.node.isOutput)
			this.addSlotToContainer(new SlotExclusiveItem(this.node, TileEntityNodeBase.targetSlotID, this.node.guiDetails.targetX, this.node.guiDetails.targetY, SorceryItems.colourTag));
		
		int var3;
		
		for(var3 = 0; var3 < 3; ++var3) {
			for(int var4 = 0; var4 < 9; ++var4) {
				this.addSlotToContainer(new Slot(player, var4 + var3 * 9 + 9, this.node.guiDetails.inventoryX + var4 * 18, this.node.guiDetails.inventoryY + var3 * 18));
			}
		}
		
		for(var3 = 0; var3 < 9; ++var3) {
			this.addSlotToContainer(new Slot(player, var3, this.node.guiDetails.inventoryX + var3 * 18, this.node.guiDetails.inventoryY + 18 * 3 + 4));
		}
	}
	
	public void addCraftingToCrafters(ICrafting par1ICrafting) {
		super.addCraftingToCrafters(par1ICrafting);
	}
	
	/** Updates crafting matrix; called from onCraftMatrixChanged. Args: none */
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		Iterator var1 = this.crafters.iterator();
		
		while(var1.hasNext()) {
			ICrafting var2 = (ICrafting)var1.next();
		}
	}
	
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int index, int data) {
	}
	
	public boolean canInteractWith(EntityPlayer player) {
		return this.worldObj.getBlock(this.posX, this.posY, this.posZ) != Sorcery.itemNode ? false : player.getDistanceSq((double)this.posX + 0.5D, (double)this.posY + 0.5D, (double)this.posZ + 0.5D) <= 64.0D;
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
