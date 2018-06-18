package sorcery.lib;

import java.util.Random;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class ItemStackHelper {
	public static ItemStackHelper instance = new ItemStackHelper();
	
	public static Item[] ingots = new Item[]{
		Items.iron_ingot, Items.gold_ingot, Items.brick, SorceryItems.ingotSilver, SorceryItems.ingotNetherrite,
		SorceryItems.ingotInfernite, SorceryItems.ingotMagic, SorceryItems.ingotEnder, Items.netherbrick,
		SorceryItems.ingotTin, SorceryItems.ingotCopper, SorceryItems.ingotBrass, SorceryItems.ingotSteel};
	
	public static ItemStackHelper getInstance() {
		return instance;
	}
	
	/** Used to check if two ItemStacks are exactly the same
	 * 
	 * @param stackOne
	 *            The first Stack to be compared
	 * @param stackTwo
	 *            The second Stack to be compared
	 * @return true or false, depending on whether or not the stacks are equal */
	public boolean areItemStacksEqual(ItemStack stackOne, ItemStack stackTwo) {
		if(stackOne == null || stackTwo == null) {
			return false;
		} else if(stackOne.getItem() == stackTwo.getItem()) {
			if(stackOne.stackSize == stackTwo.stackSize) {
				if(stackOne.getItemDamage() == stackTwo.getItemDamage()) {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean areItemStacksEqualFull(ItemStack stackOne, ItemStack stackTwo) {
		if(stackOne == null || stackTwo == null) {
			return false;
		} else if(stackOne.getItem() == stackTwo.getItem()) {
			if(stackOne.stackSize == stackTwo.stackSize && stackOne.getItemDamage() == stackTwo.getItemDamage()) {
				if(this.areOreItemStacksEqual(stackOne, stackTwo)) {
					if(stackOne.hasTagCompound() && stackTwo.hasTagCompound()) {
						if(stackOne.stackTagCompound.equals(stackTwo.hasTagCompound()))
							return true;
					} else if(!stackTwo.hasTagCompound() && !stackOne.hasTagCompound()){
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public boolean areItemsEqual(ItemStack stackOne, ItemStack stackTwo) {
		if(stackOne == null || stackTwo == null) {
			return false;
		} else if(stackOne.getItem() == stackTwo.getItem()) {
			if(stackOne.stackSize >= stackTwo.stackSize) {
				if(stackOne.getItemDamage() == stackTwo.getItemDamage())
					return true;
				
				if(stackOne.getItemDamage() == 32767 || stackTwo.getItemDamage() == 32767)
					return true;
			}
		}
		return false;
	}
	
	public boolean doItemsMatch(ItemStack stackOne, ItemStack stackTwo) {
		if(stackOne == null || stackTwo == null) {
			return false;
		} else if(stackOne.getItem() == stackTwo.getItem()) {
			if(stackOne.getItemDamage() == stackTwo.getItemDamage()) 
				return true;
			
			if(stackOne.getItemDamage() == 32767 || stackTwo.getItemDamage() == 32767)
				return true;
		}
		return false;
	}
	
	public boolean isItemIngot(ItemStack item) {
		for(int i = 0; i < ingots.length; i++) {
			if(item != null && item.getItem() == ingots[i]) {
				return true;
			}
		}
		
		return false;
	}
	
	/** Checks to see if stackOne and stackTwo are the same Ore Dicionary entry */
	public boolean areOreItemsEqual(ItemStack stackOne, ItemStack stackTwo) {
		if(stackOne == null || stackTwo == null)
			return false;
		int oreID1 = OreDictionary.getOreID(stackOne);
		int oreID2 = OreDictionary.getOreID(stackTwo);
		if(oreID1 == -1 || oreID2 == -1)
			return false;
		else if(oreID1 == oreID2)
			return true;
		return false;
	}
	
	public boolean areOreItemStacksEqual(ItemStack stackOne, ItemStack stackTwo) {
		if(stackOne == null || stackTwo == null)
			return false;
		if(areOreItemsEqual(stackOne, stackTwo)) {
			if(stackOne.stackSize >= stackTwo.stackSize) {
				return true;
			}
		}
		return false;
	}
	
	public boolean doesInventoryContainItemStack(ItemStack[] inv, ItemStack item) {
		int amountFound = 0;
		for(int i = 0; i < inv.length; i++) {
			if(inv[i] != null && inv[i].getItem() == item.getItem() && inv[i].getItemDamage() == item.getItemDamage()) {
				amountFound = amountFound + inv[i].stackSize;
			}
		}
		
		if(item.stackSize <= amountFound) {
			return true;
		}
		return false;
	}
	
	public boolean doesInventoryContainItemStackFull(ItemStack[] inv, ItemStack item) {
		int amountFound = 0;
		for(ItemStack invItem : inv) {
			if(invItem != null && invItem.getItem() == item.getItem() && invItem.getItemDamage() == item.getItemDamage()) {
				if(this.areOreItemsEqual(invItem, item)) {
					if(item.hasTagCompound() && invItem.hasTagCompound()) {
						if(item.stackTagCompound.equals(invItem.hasTagCompound()))
							amountFound = amountFound + invItem.stackSize;
					} else if(!invItem.hasTagCompound() && !item.hasTagCompound()){
						amountFound = amountFound + invItem.stackSize;
					}
				}
			}
		}
		
		if(item.stackSize <= amountFound) {
			return true;
		}
		return false;
	}
	
	public boolean isItemInInventory(ItemStack[] inv, ItemStack item) {
		for(int i = 0; i < inv.length; i++) {
			if(inv[i] != null && inv[i].getItem() == item.getItem() && inv[i].getItemDamage() == item.getItemDamage()) {
				return true;
			}
		}
		
		return false;
	}
	
	public int howManyItemsDoesInventoryContain(ItemStack[] inv, ItemStack item) {
		int amountFound = 0;
		for(int i = 0; i < inv.length; i++) {
			if(inv[i] != null && inv[i].getItem() == item.getItem() && inv[i].getItemDamage() == item.getItemDamage()) {
				amountFound = amountFound + inv[i].stackSize;
			}
		}
		
		return amountFound;
	}
	
	public void removeItemsFromInventory(ItemStack[] inv, ItemStack item) {
		if(this.doesInventoryContainItemStack(inv, item)) {
			int j = 0;
			for(int i = 0; i < inv.length; i++) {
				if(inv[i] != null && inv[i].getItem() == item.getItem() && inv[i].getItemDamage() == item.getItemDamage())
					;
				{
					j++;
				}
			}
			int[] listOfSlots = new int[j];
			int k = 0;
			for(int i = 0; i < inv.length; i++) {
				if(inv[i] != null && inv[i].getItem() == item.getItem() && inv[i].getItemDamage() == item.getItemDamage()) {
					listOfSlots[k] = i;
					k++;
				}
			}
			
			k = 0;
			for(int i = 0; i < item.stackSize; i++) {
				if(inv[listOfSlots[k]] != null) {
					if(inv[listOfSlots[k]].stackSize == 1) {
						inv[listOfSlots[k]] = null;
					} else {
						--inv[listOfSlots[k]].stackSize;
					}
				} else {
					k++;
					if(inv[listOfSlots[k]].stackSize == 1) {
						inv[listOfSlots[k]] = null;
					} else {
						--inv[listOfSlots[k]].stackSize;
					}
				}
			}
		}
	}
	
	public void removeItemsFromInventoryInRange(ItemStack[] inv, ItemStack item, int firstSlot, int lastSlot) {
		if(this.doesInventoryContainItemStackInRange(inv, item, firstSlot, lastSlot)) {
			int j = 0;
			for(int i = firstSlot; i < lastSlot; i++) {
				if(inv[i] != null && this.doItemsMatchOD(inv[i], item))
					;
				{
					j++;
				}
			}
			
			int[] listOfSlots = new int[j];
			int k = 0;
			for(int i = firstSlot; i < lastSlot; i++) {
				if(inv[i] != null && this.doItemsMatchOD(inv[i], item)) {
					listOfSlots[k] = i;
					k++;
				}
			}
			
			k = 0;
			for(int i = 0; i < item.stackSize; i++) {
				if(inv[listOfSlots[k]] != null) {
					if(inv[listOfSlots[k]].stackSize == 1) {
						inv[listOfSlots[k]] = null;
					} else {
						--inv[listOfSlots[k]].stackSize;
					}
				} else {
					k++;
					if(inv[listOfSlots[k]].stackSize == 1) {
						inv[listOfSlots[k]] = null;
					} else {
						--inv[listOfSlots[k]].stackSize;
					}
				}
			}
		}
	}
	
	public boolean doesInventoryContainItemStackInRange(ItemStack[] inv, ItemStack item, int firstSlot, int lastSlot) {
		int amountFound = 0;
		for(int i = firstSlot; i < lastSlot; i++) {
			if(inv[i] != null && this.doItemsMatchOD(inv[i], item)) {
				amountFound = amountFound + inv[i].stackSize;
			}
		}
		
		if(item.stackSize <= amountFound) {
			return true;
		}
		return false;
	}
	
	public boolean doesInventoryContainItemTypeInRange(ItemStack[] inv, Item itemtype, int firstSlot, int lastSlot) {
		for(int i = firstSlot; i < lastSlot; i++) {
			if(inv[i] != null && inv[i].getItem() == itemtype) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isInventoryEmpty(ItemStack[] inv) {
		int items = 0;
		
		for(int i = 0; i < inv.length; i++) {
			if(inv[i] != null)
				items += inv[i].stackSize;
		}
		
		return items == 0;
	}
	
	public boolean doItemsMatchOD(ItemStack item1, ItemStack item2) {
		if(this.areOreItemStacksEqual(item1, item2))
			return true;
		if(this.doItemsMatch(item1, item2))
			return true;
		return false;
	}
	
	public ItemStack getRandomBookItem() {
		ItemStack book = null;
		Random rand = new Random();
		int randInt = rand.nextInt(100);
		
		if(randInt <= 40)
			book = new ItemStack(Items.book, 1);
		else if(randInt <= 50)
			book = new ItemStack(SorceryItems.spellbook);
		else if(randInt <= 100)
			book = getRandomEnchantedBook(rand);
		
		return book;
	}
	
	public ItemStack getRandomEnchantedBook(Random rand) {
		Enchantment enchantment = Enchantment.enchantmentsBookList[rand.nextInt(Enchantment.enchantmentsBookList.length)];
		int level = MathHelper.getRandomIntegerInRange(rand, enchantment.getMinLevel(), enchantment.getMaxLevel());
		return Items.enchanted_book.getEnchantedItemStack(new EnchantmentData(enchantment, level));
	}
	
	public int howManySlotsForItemInInventory(ItemStack[] inventory, ItemStack item) {
		int slots = 0;
		
		for(int i = 0; i < inventory.length; i++) {
			if(inventory[i] == null)
				slots += 64;
			
			if(inventory[i] != null && doItemsMatch(inventory[i], item)) {
				slots += (64 - inventory[i].stackSize);
			}
		}
		
		return slots;
	}
	
	public boolean canItemStackFitIntoInventory(ItemStack[] inventory, ItemStack item) {
		int slots = howManySlotsForItemInInventory(inventory, item);
		
		if(slots > item.stackSize)
			return true;
		return false;
	}
	
	public void tryToFillInvWithItem(ItemStack[] inventory, ItemStack item) {
		for(int i = 0; i < inventory.length; i++) {
			if(item.stackSize == 0)
				return;
			
			if(inventory[i] == null) {
				inventory[i] = item.copy();
				item.stackSize = 0;
			} else if(doItemsMatch(inventory[i], item)) {
				if(item.stackSize + inventory[i].stackSize <= item.getMaxStackSize()) {
					inventory[i].stackSize += item.stackSize;
					item.stackSize = 0;
				}
			}
		}
	}
}
