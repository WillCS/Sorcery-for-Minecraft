package sorcery.recipes;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import sorcery.core.Sorcery;
import sorcery.lib.SorceryItems;
import cpw.mods.fml.common.registry.GameRegistry;

public class SmeltingRecipes {
	public static SmeltingRecipes instance = new SmeltingRecipes();
	
	public static SmeltingRecipes getInstance() {
		return instance;
	}
	
	public void addRecipes() {
		FurnaceRecipes.smelting().func_151394_a(new ItemStack(SorceryItems.netherdust, 1, 0), new ItemStack(Items.netherbrick, 1), 0.1F);
		FurnaceRecipes.smelting().func_151394_a(new ItemStack(Sorcery.arcaneblock, 1, 3), new ItemStack(SorceryItems.ingotInfernite, 1), 1.3F);
		FurnaceRecipes.smelting().func_151394_a(new ItemStack(Sorcery.arcaneblock, 1, 2), new ItemStack(SorceryItems.gemOnyx, 1), 1F);
		FurnaceRecipes.smelting().func_151394_a(new ItemStack(Sorcery.arcaneblock, 1, 1), new ItemStack(SorceryItems.ingotNetherrite, 1), 1F);
		FurnaceRecipes.smelting().func_151394_a(new ItemStack(Sorcery.arcaneblock, 1, 0), new ItemStack(SorceryItems.ingotSilver, 1), 0.7F);
		FurnaceRecipes.smelting().func_151394_a(new ItemStack(Sorcery.arcaneblock, 1, 13), new ItemStack(SorceryItems.ingotTin, 1), 1F);
		FurnaceRecipes.smelting().func_151394_a(new ItemStack(Sorcery.arcaneblock, 1, 14), new ItemStack(SorceryItems.ingotCopper, 1), 0.7F);
		
		GameRegistry.addSmelting(SorceryItems.silverDust, new ItemStack(SorceryItems.ingotSilver, 1), 0.7F);
		GameRegistry.addSmelting(SorceryItems.netherriteDust, new ItemStack(SorceryItems.ingotNetherrite, 1), 1F);
		GameRegistry.addSmelting(SorceryItems.inferniteDust, new ItemStack(SorceryItems.ingotInfernite, 1), 1.3F);
		
		for(int i = 0; i < 15; i++) {
			FurnaceRecipes.smelting().func_151394_a(new ItemStack(SorceryItems.softClay, 1, i), new ItemStack(SorceryItems.hardClay, 1, i), 0.1F);
		}
	}
	
}
