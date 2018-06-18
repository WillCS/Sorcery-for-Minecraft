package sorcery.recipes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import org.apache.logging.log4j.Level;

import sorcery.api.recipe.ITinkeringRecipes;
import sorcery.core.Sorcery;
import sorcery.lib.SorceryItems;
import sorcery.lib.SpellHelper;
import sorcery.lib.utils.Utils;
import sorcery.tileentities.TileEntityTinkering.LocalInventoryCrafting;

public class TinkeringRecipes implements ITinkeringRecipes {
	private static final TinkeringRecipes instance = new TinkeringRecipes();
	
	private List<IRecipe> recipes = new ArrayList<IRecipe>();
	
	public static final TinkeringRecipes getInstance() {
		return instance;
	}
	
	public void addTinkeringRecipes() {
		this.addRecipe(new ItemStack(Sorcery.machine, 1, 4), new Object[]{"XYX", "XZX", "X#X", 'X', Blocks.nether_brick, 'Y', (new ItemStack(SorceryItems.cog, 1, 3)), 'Z', Blocks.furnace, '#', (new ItemStack(SorceryItems.powerGenerator, 1, 2))});
		
		this.addRecipe(new ItemStack(Sorcery.machine, 1, 3), new Object[]{"XYX", "XZX", "X#X", 'X', Blocks.nether_brick, 'Y', (new ItemStack(SorceryItems.cog, 1, 3)), 'Z', (new ItemStack(Sorcery.machine, 1, 2)), '#', (new ItemStack(SorceryItems.powerGenerator, 1, 2))});
		
		this.addRecipe(new ItemStack(SorceryItems.powerGenerator, 1, 0), new Object[]{"X$X", "YZY", "###", 'X', Blocks.redstone_torch, 'Y', new ItemStack(SorceryItems.cog, 1, 5), 'Z', Blocks.furnace, '#', Items.iron_ingot, '$', SorceryItems.chain});
		
		this.addRecipe(new ItemStack(SorceryItems.powerGenerator, 1, 1), new Object[]{"Y", "Z", "#", 'Y', new ItemStack(SorceryItems.cog, 1, 2), 'Z', new ItemStack(SorceryItems.powerGenerator, 1, 0), '#', SorceryItems.ingotMagic});
		
		this.addRecipe(new ItemStack(SorceryItems.powerGenerator, 1, 2), new Object[]{"Y", "Z", "#", 'Y', new ItemStack(SorceryItems.cog, 1, 3), 'Z', new ItemStack(SorceryItems.powerGenerator, 1, 1), '#', SorceryItems.plateMagic});
		
		this.addRecipe(new ItemStack(SorceryItems.magnet, 1), new Object[]{"XXX", "XYX", "X X", 'X', new ItemStack(Items.iron_ingot), 'Y', new ItemStack(Items.dye, 1, 1)});
		
		this.addRecipe(new ItemStack(Sorcery.machine, 1, 15), new Object[]{"XXX", "YZY", "X#X", 'X', new ItemStack(SorceryItems.ingotEnder), 'Y', SorceryItems.ingotInfernite, 'Z', new ItemStack(Sorcery.machine, 1, 14), '#', new ItemStack(SorceryItems.powerGenerator, 1, 1)});
		
		this.addRecipe(new ItemStack(SorceryItems.nodeComponents, 8, 0), new Object[]{"X X", "XXX", " X ", 'X', new ItemStack(Items.iron_ingot)});
		
		this.addRecipe(new ItemStack(SorceryItems.nodeComponents, 8, 2), new Object[]{"X  ", "YZ#", "X  ", 'X', new ItemStack(SorceryItems.ingotEnder), 'Y', new ItemStack(SorceryItems.powerGenerator, 1, 1), 'Z', new ItemStack(Items.blaze_rod), '#', new ItemStack(SorceryItems.battery, 1)});
		
		this.addRecipe(new ItemStack(SorceryItems.nodeComponents, 1, 1), new Object[]{" X ", "XYZ", " X ", 'X', new ItemStack(Blocks.planks, 1, 32767), 'Y', new ItemStack(SorceryItems.battery), 'Z', new ItemStack(SorceryItems.nodeComponents, 1, 2)});
		
		this.addRecipe(new ItemStack(Sorcery.itemNode, 1, 2), new Object[]{"X", "Y", "Z", 'X', new ItemStack(SorceryItems.battery, 1, 0), 'Y', new ItemStack(SorceryItems.nodeComponents, 1, 0), 'Z', new ItemStack(Blocks.wooden_slab, 1, 32767)});
		
		this.addRecipe(new ItemStack(Sorcery.itemNode, 1, 0), new Object[]{"X", "Y", "Z", 'X', new ItemStack(SorceryItems.nodeComponents, 1, 2), 'Y', new ItemStack(SorceryItems.nodeComponents, 1, 0), 'Z', new ItemStack(Blocks.wooden_slab, 1, 32767)});
		
		this.addRecipe(new ItemStack(Sorcery.itemNode, 1, 1), new Object[]{"X", "Y", "Z", 'X', new ItemStack(SorceryItems.nodeComponents, 1, 1), 'Y', new ItemStack(SorceryItems.nodeComponents, 1, 0), 'Z', new ItemStack(Blocks.wooden_slab, 1, 32767)});
		
		this.addRecipe(new ItemStack(SorceryItems.nodeComponents, 1, 8), new Object[]{" X ", "XYX", " X ", 'X', new ItemStack(Items.iron_ingot), 'Y', new ItemStack(SorceryItems.magnet)});
		
		this.addRecipe(new ItemStack(SorceryItems.nodeComponents, 1, 9), new Object[]{" X ", "XYX", " X ", 'X', new ItemStack(Items.iron_ingot), 'Y', new ItemStack(Blocks.chest)});
		
		this.addRecipe(new ItemStack(Sorcery.machine, 1, 5), new Object[]{"A A", "BCB", "ADA", 'A', new ItemStack(SorceryItems.ingotBrass), 'B', Items.bucket, 'C', Blocks.piston, 'D', SorceryItems.powerGenerator});
		
		this.addRecipe(new ItemStack(Sorcery.machine, 1, 1), new Object[]{" X ", "YZY", "Z#Z", 'X', new ItemStack(SorceryItems.battery, 1), 'Y', Items.gold_ingot, 'Z', new ItemStack(Sorcery.runeBlock, 1, 0), '#', new ItemStack(SorceryItems.powerGenerator, 1, 1)});
		
		this.addShapelessRecipe(new ItemStack(Sorcery.itemNode, 1, 5), new Object[]{new ItemStack(Sorcery.itemNode, 1, 1), new ItemStack(SorceryItems.nodeComponents, 1, 9)});
		
		this.addShapelessRecipe(new ItemStack(Sorcery.itemNode, 1, 3), new Object[]{new ItemStack(Sorcery.itemNode, 1, 1), new ItemStack(SorceryItems.nodeComponents, 1, 8)});
		
		this.addShapelessRecipe(new ItemStack(SorceryItems.spellbook, 1, 4), new Object[]{SorceryItems.spellbook, SorceryItems.spellPage});
		
		this.addShapelessRecipe(new ItemStack(SorceryItems.spellbook, 1, 4), new Object[]{Items.book, SorceryItems.spellPage});
		
		Utils.log(Level.INFO, recipes.size() + " Tinkering Recipes");
	}
	
	public void addRecipe(ItemStack itemStack, Object... objectArray) {
		this.recipes.add(new ShapedOreRecipe(itemStack, objectArray));
	}
	
	public void addShapelessRecipe(ItemStack itemStack, Object... objectArray) {
		this.recipes.add(new ShapelessOreRecipe(itemStack, objectArray));
	}
	
	public ItemStack findMatchingRecipe(LocalInventoryCrafting inv, World world) {
		int var2 = 0;
		ItemStack var3 = null;
		ItemStack var4 = null;
		
		for(int i = 0; i < inv.getSizeInventory(); i++) {
			if(inv.stackList != null && inv.stackList[i] != null && inv.stackList[i].getItem() == SorceryItems.spellPage) {
				for(int k = 0; k < inv.stackList.length; k++) {
					if(inv.stackList[k] != null && (inv.stackList[k].getItem() == SorceryItems.spellbook || inv.stackList[k].getItem() == Items.book)) {
						ItemStack page = inv.stackList[i].copy();
						ItemStack book = inv.stackList[k].copy();
						
						SpellHelper.instance.addPageToBook(page, book);
						return book;
					}
				}
			}
		}
		
		for(int var5 = 0; var5 < inv.getSizeInventory(); ++var5) {
			ItemStack var6 = inv.getStackInSlot(var5);
			
			if(var6 != null) {
				if(var2 == 0) {
					var3 = var6;
				}
				
				if(var2 == 1) {
					var4 = var6;
				}
				
				/*
				 * if(var2 == 2) { mod = var6; }
				 */
				
				++var2;
			}
		}
		
		Iterator<IRecipe> var11 = this.recipes.iterator();
		IRecipe var13;
		
		do {
			if(!var11.hasNext()) {
				return null;
			}
			
			var13 = (IRecipe)var11.next();
		} while(!var13.matches(inv, world));
		
		return var13.getCraftingResult(inv);
	}
	
	/** returns the List<> of all recipes */
	public List<IRecipe> getRecipeList() {
		return this.recipes;
	}
}
