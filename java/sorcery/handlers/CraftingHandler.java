package sorcery.handlers;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import sorcery.core.Sorcery;
import sorcery.lib.ItemStackHelper;
import sorcery.lib.SorceryItems;
import sorcery.lib.SpellHelper;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemSmeltedEvent;

public class CraftingHandler {
	
	@SubscribeEvent
	public void onCrafting(ItemCraftedEvent event) {
		IInventory inv = (InventoryCrafting) event.craftMatrix;
		for(int i = 0; i < inv.getSizeInventory(); i++) {
			if(inv.getStackInSlot(i) != null) {
				ItemStack j = inv.getStackInSlot(i);
				if(j.getItem() != null && j.getItem() == SorceryItems.pestleandmortar) {
					ItemStack k = new ItemStack(SorceryItems.pestleandmortar, 2, (j.getItemDamage() + 1));
					inv.setInventorySlotContents(i, k);
				}
				
				if(j.getItem() != null && j.getItem() == SorceryItems.hammer) {
					ItemStack k = new ItemStack(SorceryItems.hammer, 2, (j.getItemDamage() + 1));
					inv.setInventorySlotContents(i, k);
				}
				
				if(j.getItem() != null && j.getItem() == SorceryItems.chisel) {
					ItemStack k = new ItemStack(SorceryItems.chisel, 2, (j.getItemDamage() + 1));
					inv.setInventorySlotContents(i, k);
				}
				
				if(j.getItem() != null && j.getItem() == SorceryItems.brush) {
					ItemStack k = new ItemStack(SorceryItems.brush, 2, (j.getItemDamage() + 1));
					inv.setInventorySlotContents(i, k);
				}
				
				if(j.getItem() != null && !(j.getItem().equals(SorceryItems.softClay))) {
					for(int i2 = 0; i2 < inv.getSizeInventory(); i2++) {
						if(inv.getStackInSlot(i2) != null && inv.getStackInSlot(i2).getItem().equals(SorceryItems.softClay)) {
							ItemStack k = j.copy();
							k.stackSize++;
							inv.setInventorySlotContents(i, k);
						}
					}
				}
				
				if(j != null && j.getItem().isDamageable() && j.getItemDamage() >= j.getItem().getMaxDamage()) {
					inv.setInventorySlotContents(i, null);
				}
			}
		}
		
		if(event.crafting.getItem() == new ItemBlock(Sorcery.machine) && event.crafting.getItemDamage() == 0) {
			event.player.addStat(Sorcery.inventor, 1);
		} else if(event.crafting.getItem() == new ItemBlock(Sorcery.machine) && event.crafting.getItemDamage() == 2) {
			event.player.addStat(Sorcery.blacksmith, 1);
		} else if(ItemStackHelper.getInstance().areItemStacksEqual(event.crafting, (new ItemStack(Sorcery.arcanehalf, 1, 0)))) {
			event.player.addStat(Sorcery.babbyFormed, 1);
		} else if(event.crafting.getItem() == SorceryItems.spellbook) {
			event.player.addStat(Sorcery.loveStory, 1);
		} else if(event.crafting.getItem() == new ItemBlock(Sorcery.machine) && event.crafting.getItemDamage() == 4) {
			event.player.addStat(Sorcery.meltingTopic, 1);
		} else if(event.crafting.getItem() == new ItemBlock(Sorcery.machine) && event.crafting.getItemDamage() == 3) {
			event.player.addStat(Sorcery.hellFire, 1);
		}
	}
	
	@SubscribeEvent
	public void onSmelting(ItemSmeltedEvent event) {
		if(event.smelting.getItem() == SorceryItems.ingotSilver) {
			event.player.addStat(Sorcery.soShiny, 1);
		} else if(event.smelting.getItem() == SorceryItems.ingotInfernite) {
			event.player.addStat(Sorcery.redHotMetal, 1);
		} else if(event.smelting.getItem() == SorceryItems.ingotMagic) {
			event.player.addStat(Sorcery.green, 1);
		} else if(event.smelting.getItem() == SorceryItems.plateMagic) {
			event.player.addStat(Sorcery.notForFood, 1);
		}
	}
	
}
