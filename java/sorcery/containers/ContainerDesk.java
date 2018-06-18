package sorcery.containers;

import java.util.Iterator;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import sorcery.core.Sorcery;
import sorcery.guis.slot.SlotExclusiveItem;
import sorcery.guis.slot.SlotExclusiveNoSwitch;
import sorcery.guis.slot.SlotNoInsert;
import sorcery.guis.slot.SlotNoSwitch;
import sorcery.guis.slot.SlotSpellCollection;
import sorcery.guis.slot.SlotSpellbook;
import sorcery.guis.slot.SlotSpellbookColour;
import sorcery.guis.slot.SlotWand;
import sorcery.lib.SorceryItems;
import sorcery.tileentities.TileEntityDesk;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerDesk extends Container {
	private TileEntityDesk tile;
	private World worldObj;
	private int posX;
	private int posY;
	private int posZ;
	private int lastTickAct = -1;
	private InventoryPlayer player;
	private int offset;
	
	public ContainerDesk(InventoryPlayer player, TileEntityDesk tile, World world, int i, int j, int k) {
		this.tile = tile;
		this.player = player;
		this.worldObj = world;
		this.posX = i;
		this.posY = j;
		this.posZ = k;
		
		this.tile.isInUse = true;

		this.inventoryItemStacks.clear();
		this.inventorySlots.clear();
		int act = this.tile.currentActivity;
		
		if(act == 0) {
			this.offset = 20;
			this.addSlotToContainer(new SlotWand(this.tile, 0, 80 + this.offset, 49, this.tile));
			this.addSlotToContainer(new SlotNoSwitch(this.tile, -19 + this.offset, 61, 49));
			this.addSlotToContainer(new SlotNoSwitch(this.tile, 2, 119 + this.offset, 49));
			this.addSlotToContainer(new SlotExclusiveNoSwitch(this.tile, 3, 80 + this.offset, 76, SorceryItems.colourTag));
		} else if(act == 1) {
			this.offset = 176;
			this.addSlotToContainer(new SlotSpellbook(this.tile, 0, 21 + this.offset, 43, this.tile));
			this.addSlotToContainer(new Slot(this.tile, 1, 152 + this.offset, 43));
			this.addSlotToContainer(new SlotNoInsert(this.tile, 2, 152 + this.offset, 68));
			this.addSlotToContainer(new SlotSpellbookColour(this.tile, 3, -15 + this.offset, 25, this.tile));
		} else if(act == 2) {
			this.offset = 176;
			this.addSlotToContainer(new SlotExclusiveItem(this.tile, 0, 21 + this.offset, 43, SorceryItems.researchJournal));
			this.addSlotToContainer(new SlotExclusiveItem(this.tile, 1, 21 + this.offset, 66, Items.paper));
			this.addSlotToContainer(new SlotNoInsert(this.tile, 2, 152 + this.offset, 43));
		}
		
		int var3;
		
		if(act != 2) {
			for(var3 = 0; var3 < 3; ++var3) {
				for(int var4 = 0; var4 < 9; ++var4) {
					this.addSlotToContainer(new Slot(this.player, var4 + var3 * 9 + 9, this.offset + 8 + var4 * 18, 104 + var3 * 18));
				}
			}
		}
		
		for(var3 = 0; var3 < 9; ++var3) {
			this.addSlotToContainer(new Slot(this.player, var3, this.offset - 12 +  var3 * 18 + 20, 162));
		}
	}

	public void addCraftingToCrafters(ICrafting par1ICrafting) {
		super.addCraftingToCrafters(par1ICrafting);
	}

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
		return this.worldObj.getBlock(this.posX, this.posY, this.posZ) != Sorcery.machine ? false : player.getDistanceSq((double)this.posX + 0.5D, (double)this.posY + 0.5D, (double)this.posZ + 0.5D) <= 64.0D;
	}
	
	@Override
	public void onCraftMatrixChanged(IInventory par1IInventory) {
		this.tile.markDirty();
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
