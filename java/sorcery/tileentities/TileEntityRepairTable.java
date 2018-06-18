package sorcery.tileentities;

import java.util.ArrayList;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;
import sorcery.api.event.RepairTableEvent;

public class TileEntityRepairTable extends TileEntityMojo implements IInventory {
	public ItemStack[] inventory = new ItemStack[9];
	
	@Override
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
	
	@Override
	public void updateEntity() {
		for(int i = 0; i < this.inventory.length; i++) {
			ItemStack item = this.inventory[i];
			if(item != null && item.getItem().isItemTool(item) && item.getItem().isRepairable() && item.isItemDamaged()) {
				int mojoRequired = 10;
				
				RepairTableEvent event = new RepairTableEvent(item);
				MinecraftForge.EVENT_BUS.post(event);
				
				if(!event.isCanceled() && event.repairCost == -1) {
					if(item.isItemEnchanted()) {
						NBTTagList tag = item.getEnchantmentTagList();
						for(int j = 0; j < tag.tagCount(); j++) {
							int level = tag.getCompoundTagAt(j).getInteger("lvl");
							int id = tag.getCompoundTagAt(j).getInteger("id");
							int weight = Enchantment.enchantmentsList[id].getWeight();
							mojoRequired += level * weight;
							
							if(id == Enchantment.unbreaking.effectId) {
								mojoRequired = -1;
								break;
							}
						}
					}
					
					if(item.getItem() instanceof ItemTool && mojoRequired != -1) {
						ItemTool tool = (ItemTool)item.getItem();
						for(String toolClass : tool.getToolClasses(item)) {
							mojoRequired += 5 * tool.getHarvestLevel(item, toolClass);
						}
					}
				} else if(event.isCanceled()) {
					mojoRequired = event.repairCost;
				}

				if(mojoRequired == -1)
					break;
				
				if(this.getMojo() >= mojoRequired) {
					this.inventory[i].setItemDamage(item.getItemDamage() - 1);
					this.subtractMojo(mojoRequired);
				}
			}
		}
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int par1) {
		if(this.inventory[par1] != null) {
			ItemStack var2 = this.inventory[par1];
			this.inventory[par1] = null;
			return var2;
		} else {
			return null;
		}
	}

	@Override
	public ItemStack getStackInSlot(int var8) {
		return this.inventory[var8];
	}

	@Override
	public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {
		this.inventory[par1] = par2ItemStack;
		
		if(par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit()) {
			par2ItemStack.stackSize = this.getInventoryStackLimit();
		}
	}

	@Override
	public int getSizeInventory() {
		return this.inventory.length;
	}
	
	@Override
	public String getInventoryName() {
		return "sorcery.container.repair";
	}
	
	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readFromNBT(par1NBTTagCompound);
		NBTTagList var2 = par1NBTTagCompound.getTagList("Items", 10);
		this.inventory = new ItemStack[this.getSizeInventory()];
		
		for(int var3 = 0; var3 < var2.tagCount(); ++var3) {
			NBTTagCompound var4 = (NBTTagCompound)var2.getCompoundTagAt(var3);
			int var5 = var4.getByte("Slot") & 255;
			
			if(var5 >= 0 && var5 < this.inventory.length) {
				this.inventory[var5] = ItemStack.loadItemStackFromNBT(var4);
			}
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeToNBT(par1NBTTagCompound);
		NBTTagList var2 = new NBTTagList();
		
		for(int var3 = 0; var3 < this.inventory.length; ++var3) {
			if(this.inventory[var3] != null) {
				NBTTagCompound var4 = new NBTTagCompound();
				var4.setByte("Slot", (byte)var3);
				this.inventory[var3].writeToNBT(var4);
				var2.appendTag(var4);
			}
		}
		
		par1NBTTagCompound.setTag("Items", var2);
	}
	
	@Override
	public ForgeDirection[] getDirectionsToReceiveFrom() {
		ArrayList<ForgeDirection> ret = new ArrayList<ForgeDirection>();
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			if(ForgeDirection.UP != dir)
				ret.add(dir);
		}
		
		return ret.toArray(new ForgeDirection[0]);
	}
}
