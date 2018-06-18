package sorcery.recipes;

import java.util.ArrayList;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import sorcery.api.element.Element;
import sorcery.core.Sorcery;
import sorcery.items.ItemElementBottle;
import sorcery.items.ItemLantern;
import sorcery.items.ItemWand;
import sorcery.lib.ItemStackHelper;
import sorcery.lib.SorceryItems;
import sorcery.lib.utils.Utils;
import cpw.mods.fml.common.registry.GameRegistry;

public class CraftingRecipes {
	private String[][] recipePatternsTools = new String[][]{{"XXX", " # ", " # "}, {"X", "#", "#"}, {"XX", "X#", " #"}, {"XX", " #", " #"}, {"X", "X", "#"}};
	private String[][] recipePatternsArmor = new String[][]{{"XXX", "X X"}, {"X X", "XXX", "XXX"}, {"XXX", "X X", "X X"}, {"X X", "X X"}};
	private Object[][] recipeItemsBlaze;
	private Object[][] recipeItemsArmor;
	
	public CraftingRecipes() {
		this.recipeItemsBlaze = new Object[][]{{SorceryItems.ingotNetherrite, SorceryItems.ingotInfernite}, {SorceryItems.netherritepick, SorceryItems.infernitepick}, {SorceryItems.netherriteshovel, SorceryItems.inferniteshovel}, {SorceryItems.netherriteaxe, SorceryItems.inferniteaxe}, {SorceryItems.netherritehoe, SorceryItems.infernitehoe}, {SorceryItems.netherritesword, SorceryItems.infernitesword}};
		this.recipeItemsArmor = new Object[][]{{SorceryItems.ingotNetherrite, SorceryItems.ingotInfernite}, {SorceryItems.netherritehelmet, SorceryItems.infernitehelmet}, {SorceryItems.netherritebody, SorceryItems.infernitebody}, {SorceryItems.netherritelegs, SorceryItems.infernitelegs}, {SorceryItems.netherriteboots, SorceryItems.inferniteboots}};
	}
	
	public void addRecipes(Sorcery mod) {
		for(int var2 = 0; var2 < this.recipeItemsBlaze[0].length; ++var2) {
			Object var3 = this.recipeItemsBlaze[0][var2];
			
			for(int var4 = 0; var4 < this.recipeItemsBlaze.length - 1; ++var4) {
				Item var5 = (Item)this.recipeItemsBlaze[var4 + 1][var2];
				GameRegistry.addRecipe(new ItemStack(var5), new Object[]{this.recipePatternsTools[var4], '#', Items.blaze_rod, 'X', var3});
			}
		}
		for(int var2 = 0; var2 < this.recipeItemsArmor[0].length; ++var2) {
			Object var3 = this.recipeItemsArmor[0][var2];
			
			for(int var4 = 0; var4 < this.recipeItemsArmor.length - 1; ++var4) {
				Item var5 = (Item)this.recipeItemsArmor[var4 + 1][var2];
				GameRegistry.addRecipe(new ItemStack(var5), new Object[]{this.recipePatternsArmor[var4], 'X', var3});
			}
		}
		
		for(int i = 0; i < ItemDye.field_150923_a.length; i++) {
			Utils.addShapelessOreRecipe(ItemWand.getColouredItemStack(i), new Object[]{new ItemStack(SorceryItems.wand), new ItemStack(Items.dye, 1, i)});
		}
		
		for(int i = 0; i < ItemDye.field_150923_a.length; i++) {
			Utils.addShapelessOreRecipe(new ItemStack(Sorcery.slimeBlock, 1, i), new Object[]{new ItemStack(Sorcery.slimeBlock, 1, 32767), new ItemStack(Items.dye, 1, 15 - i)});
			Utils.addShapelessOreRecipe(new ItemStack(SorceryItems.colourTag, 1, i), new Object[]{Items.paper, new ItemStack(Items.dye, 1, i)});
		}
		
		for(int i = 1; i < Element.elementsList.length; i++) {
			if(Element.elementsList[i] != null) {
				GameRegistry.addShapelessRecipe(ItemElementBottle.getFullBottle(i), new Object[]{new ItemStack(Items.glass_bottle), new ItemStack(SorceryItems.elementCrystal, 1, i)});
			}
		}
		
		ItemStack[] metals = new ItemStack[]{
				new ItemStack(Items.iron_ingot), new ItemStack(SorceryItems.ingotSilver),
				new ItemStack(Items.gold_ingot), new ItemStack(SorceryItems.ingotNetherrite),
				new ItemStack(SorceryItems.ingotInfernite), new ItemStack(SorceryItems.ingotMagic),
				new ItemStack(SorceryItems.ingotEnder), new ItemStack(Items.emerald),
				new ItemStack(Items.dye, 1, 4), new ItemStack(Items.diamond),
				new ItemStack(SorceryItems.gemOnyx), new ItemStack(SorceryItems.ingotTin),
				new ItemStack(SorceryItems.ingotCopper), new ItemStack(SorceryItems.ingotBrass),
				new ItemStack(SorceryItems.ingotSteel)};
		
		for(int i = 0; i < metals.length; i++) {
			GameRegistry.addRecipe(new ItemStack(Sorcery.metalBricks, 1, i), new Object[]{"XX", "XX", Character.valueOf('X'), metals[i]});
			GameRegistry.addShapelessRecipe(new ItemStack(metals[i].getItem(), 4, metals[i].getItemDamage()), new Object[]{(new ItemStack(Sorcery.metalBricks, 1, i))});
		}
		
		GameRegistry.addRecipe(new ItemStack(Sorcery.arcaneblock, 1, 7), new Object[]{"XXX", "XXX", "XXX", Character.valueOf('X'), SorceryItems.ingotInfernite});
		GameRegistry.addRecipe(new ItemStack(SorceryItems.ingotInfernite, 9), new Object[]{"X", Character.valueOf('X'), new ItemStack(Sorcery.arcaneblock, 1, 7)});
		GameRegistry.addRecipe(new ItemStack(Sorcery.arcaneblock, 1, 6), new Object[]{"XXX", "XXX", "XXX", Character.valueOf('X'), SorceryItems.gemOnyx});
		GameRegistry.addRecipe(new ItemStack(SorceryItems.gemOnyx, 9), new Object[]{"X", Character.valueOf('X'), new ItemStack(Sorcery.arcaneblock, 1, 6)});
		GameRegistry.addRecipe(new ItemStack(Sorcery.arcaneblock, 1, 5), new Object[]{"XXX", "XXX", "XXX", Character.valueOf('X'), SorceryItems.ingotNetherrite});
		GameRegistry.addRecipe(new ItemStack(SorceryItems.ingotNetherrite, 9), new Object[]{"X", Character.valueOf('X'), new ItemStack(Sorcery.arcaneblock, 1, 5)});
		GameRegistry.addRecipe(new ItemStack(Sorcery.arcaneblock, 1, 10), new Object[]{"XXX", "XXX", "XXX", Character.valueOf('X'), SorceryItems.ingotEnder});
		GameRegistry.addRecipe(new ItemStack(SorceryItems.ingotEnder, 9), new Object[]{"X", Character.valueOf('X'), new ItemStack(Sorcery.arcaneblock, 1, 10)});
		GameRegistry.addRecipe(new ItemStack(Sorcery.arcaneblock, 1, 4), new Object[]{"XXX", "XXX", "XXX", Character.valueOf('X'), SorceryItems.ingotSilver});
		GameRegistry.addRecipe(new ItemStack(SorceryItems.ingotSilver, 9), new Object[]{"X", Character.valueOf('X'), new ItemStack(Sorcery.arcaneblock, 1, 4)});
		GameRegistry.addRecipe(new ItemStack(Sorcery.arcaneblock, 1, 11), new Object[]{"XXX", "XXX", "XXX", Character.valueOf('X'), new ItemStack(Items.coal, 1, 1)});
		GameRegistry.addRecipe(new ItemStack(Items.coal, 9, 1), new Object[]{"X", Character.valueOf('X'), new ItemStack(Sorcery.arcaneblock, 1, 11)});
		GameRegistry.addRecipe(new ItemStack(SorceryItems.sinew, 3), new Object[]{"X  ", " X ", "  X", Character.valueOf('X'), Items.rotten_flesh});
		GameRegistry.addRecipe(new ItemStack(SorceryItems.bonebow, 1), new Object[]{"YX ", "Y X", "YX ", Character.valueOf('X'), Items.bone, Character.valueOf('Y'), SorceryItems.sinew});
		GameRegistry.addRecipe(new ItemStack(SorceryItems.bonebow, 1), new Object[]{" XY", "X Y", " XY", Character.valueOf('X'), Items.bone, Character.valueOf('Y'), SorceryItems.sinew});
		GameRegistry.addRecipe(new ItemStack(SorceryItems.bonearrow, 4), new Object[]{"X", "Y", "Z", Character.valueOf('X'), Items.iron_ingot, Character.valueOf('Y'), Items.bone, Character.valueOf('Z'), SorceryItems.goldfeather});
		GameRegistry.addRecipe(new ItemStack(SorceryItems.blazebow, 1), new Object[]{"YX ", "Y X", "YX ", Character.valueOf('X'), Items.blaze_rod, Character.valueOf('Y'), SorceryItems.unicornhair});
		GameRegistry.addRecipe(new ItemStack(SorceryItems.blazebow, 1), new Object[]{" XY", "X Y", " XY", Character.valueOf('X'), Items.blaze_rod, Character.valueOf('Y'), SorceryItems.unicornhair});
		GameRegistry.addRecipe(new ItemStack(SorceryItems.blazearrow, 4), new Object[]{"X", "Y", "Z", Character.valueOf('X'), SorceryItems.gemOnyx, Character.valueOf('Y'), Items.blaze_rod, Character.valueOf('Z'), SorceryItems.phoenixfeather});
		GameRegistry.addRecipe(new ItemStack(SorceryItems.chisel, 1), new Object[]{"  Y", " X ", "X  ", Character.valueOf('X'), Items.stick, Character.valueOf('Y'), Items.iron_ingot});
		GameRegistry.addRecipe(new ItemStack(SorceryItems.hammer, 1), new Object[]{"  Y", " X ", "X  ", Character.valueOf('X'), Items.stick, Character.valueOf('Y'), Blocks.obsidian});
		GameRegistry.addRecipe(new ItemStack(SorceryItems.pestleandmortar, 1), new Object[]{" Y ", "X X", " X ", Character.valueOf('X'), Blocks.stone, Character.valueOf('Y'), Items.flint});
		GameRegistry.addRecipe(new ItemStack(SorceryItems.brush, 1), new Object[]{"X  ", "YX ", "ZYX", Character.valueOf('X'), Items.feather, Character.valueOf('Y'), Items.string, Character.valueOf('Z'), Items.stick});
		GameRegistry.addRecipe(new ItemStack(SorceryItems.nest, 1), new Object[]{"X X", " X ", Character.valueOf('X'), Items.stick});
		GameRegistry.addRecipe(new ItemStack(SorceryItems.chainLink, 4), new Object[]{" X ", "X X", " X ", Character.valueOf('X'), Blocks.iron_bars});
		GameRegistry.addRecipe(new ItemStack(SorceryItems.chain, 1), new Object[]{"XXX", "X X", "XXX", Character.valueOf('X'), SorceryItems.chainLink});
		GameRegistry.addRecipe(new ItemStack(Sorcery.machine, 1, 0), new Object[]{"XWX", "YZY", "###", Character.valueOf('W'), Items.gold_ingot, Character.valueOf('X'), Blocks.nether_brick, Character.valueOf('Y'), SorceryItems.cog, Character.valueOf('Z'), Blocks.crafting_table, Character.valueOf('#'), Blocks.stone});
		GameRegistry.addRecipe(new ItemStack(Sorcery.machine, 1, 2), new Object[]{"XXX", "XZX", "YYY", Character.valueOf('X'), Blocks.brick_block, Character.valueOf('Y'), Blocks.stone, Character.valueOf('Z'), Blocks.furnace});
		GameRegistry.addRecipe(new ItemStack(SorceryItems.softClay, 1, 0), "XX", Character.valueOf('X'), Items.clay_ball);
		GameRegistry.addRecipe(new ItemStack(Sorcery.arcanehalf, 1, 0), new Object[]{"XYX", "XZX", Character.valueOf('X'), Blocks.stone, Character.valueOf('Y'), SorceryItems.nest, Character.valueOf('Z'), Blocks.torch});
		GameRegistry.addRecipe(new ItemStack(Sorcery.machine, 1, 13), new Object[]{"XXX", "Y Y", "XXX", Character.valueOf('X'), new ItemStack(Blocks.planks, 1, 32767), Character.valueOf('Y'), Items.iron_ingot});
		GameRegistry.addRecipe(new ItemStack(Sorcery.machine, 1, 14), new Object[]{"XXX", "YZY", "XXX", Character.valueOf('X'), new ItemStack(Items.iron_ingot), Character.valueOf('Y'), SorceryItems.ingotBrass, Character.valueOf('Z'), new ItemStack(Sorcery.machine, 1, 13)});
		GameRegistry.addRecipe(new ItemStack(SorceryItems.wand, 1), new Object[]{" Y ", " Z ", "XYX", Character.valueOf('X'), Items.leather, Character.valueOf('Y'), Items.stick, Character.valueOf('Z'), SorceryItems.battery});
		GameRegistry.addRecipe(new ItemStack(SorceryItems.woodBall, 1), new Object[]{"X", "X", Character.valueOf('X'), Items.bowl});
		GameRegistry.addRecipe(new ItemStack(SorceryItems.ringWooden, 1), new Object[]{"YXY", "X X", "YXY", Character.valueOf('X'), Items.stick, Character.valueOf('Y'), new ItemStack(Blocks.planks, 1, 32767)});
		GameRegistry.addRecipe(new ItemStack(SorceryItems.cog, 1, 4), new Object[]{"X X", " Y ", "X X", Character.valueOf('X'), Items.stick, Character.valueOf('Y'), new ItemStack(Blocks.planks, 1, 32767)});
		GameRegistry.addRecipe(new ItemStack(SorceryItems.plateWood, 1), new Object[]{"XXX", "XYX", "XXX", Character.valueOf('X'), new ItemStack(Blocks.planks, 1, 32767), Character.valueOf('Y'), Blocks.obsidian});
		GameRegistry.addRecipe(new ItemStack(Sorcery.slimeBlock, 1, 13), new Object[]{"XX", "XX", Character.valueOf('X'), Items.slime_ball});
		GameRegistry.addRecipe(new ItemStack(SorceryItems.wizardHat, 1, 0), new Object[]{" X ", "YZY", "XXX", Character.valueOf('X'), new ItemStack(Blocks.wool, 1, 11), Character.valueOf('Y'), new ItemStack(Items.dye, 1, 0), Character.valueOf('Z'), new ItemStack(Items.gold_nugget, 1)});
		
		GameRegistry.addRecipe(new ItemStack(SorceryItems.phoenixBoots, 1), new Object[]{"XXX", "#Y$", "XXX", 'X', SorceryItems.phoenixfeather, 'Y', new ItemStack(SorceryItems.battery, 1), '#', SorceryItems.energyBoots, '$', SorceryItems.windBoots});
		
		GameRegistry.addRecipe(new ItemStack(SorceryItems.phoenixBoots, 1), new Object[]{"XXX", "#Y$", "XXX", 'X', SorceryItems.phoenixfeather, 'Y', new ItemStack(SorceryItems.battery, 1), '#', SorceryItems.windBoots, '$', SorceryItems.energyBoots});
		
		GameRegistry.addRecipe(new ItemStack(SorceryItems.frostBoots, 1), new Object[]{"XXX", "XYX", "XZX", 'X', Blocks.ice, 'Y', Items.leather_boots, 'Z', new ItemStack(SorceryItems.magicOrb, 1, 5)});
		
		GameRegistry.addRecipe(new ItemStack(SorceryItems.windBoots, 1), new Object[]{"XXX", "XYX", "XZX", 'X', Items.feather, 'Y', Items.leather_boots, 'Z', new ItemStack(SorceryItems.magicOrb, 1, 4)});
		
		GameRegistry.addRecipe(new ItemStack(SorceryItems.energyBoots, 1), new Object[]{"XXX", "XYX", "XZX", 'X', Items.glowstone_dust, 'Y', Items.leather_boots, 'Z', new ItemStack(SorceryItems.magicOrb, 1, 1)});
		
		GameRegistry.addRecipe(new ItemStack(SorceryItems.fireBoots, 1), new Object[]{"XXX", "XYX", "XZX", 'X', Items.fire_charge, 'Y', Items.leather_boots, 'Z', new ItemStack(SorceryItems.magicOrb, 1, 7)});
		
		GameRegistry.addRecipe(((ItemLantern)SorceryItems.lantern).getLanternWithFuel(0), new Object[]{" X ", "XYX", " X ", 'X', SorceryItems.ingotSteel, 'Y', Blocks.glass_pane});
		
		GameRegistry.addRecipe(new ItemStack(Items.chainmail_helmet, 1), new Object[]{"XYX", "X X", 'X', SorceryItems.chainLink, 'Y', SorceryItems.chain});
		
		GameRegistry.addRecipe(new ItemStack(Items.chainmail_chestplate, 1), new Object[]{"X X", "YXY", "YXY", 'X', SorceryItems.chainLink, 'Y', SorceryItems.chain});
		
		GameRegistry.addRecipe(new ItemStack(Items.chainmail_leggings, 1), new Object[]{"XYX", "Y Y", "X X", 'X', SorceryItems.chainLink, 'Y', SorceryItems.chain});
		
		GameRegistry.addRecipe(new ItemStack(Items.chainmail_boots, 1), new Object[]{"Y Y", "X X", 'X', SorceryItems.chainLink, 'Y', SorceryItems.chain});
		
		/*
		 * GameRegistry.addRecipe(new ItemStack(SorceryItems.battery, 1), new
		 * Object[] { "XYX", "X#X", "XZX", '#', Items.diamond, 'X',
		 * SorceryItems.magicDust, 'Y', Items.glowstone, 'Z',
		 * SorceryItems.ectoplasm});
		 */
		
		GameRegistry.addRecipe(new ItemStack(SorceryItems.battery, 1), new Object[]{"XYX", "X#X", "XZX", '#', SorceryItems.gemOnyx, 'X', SorceryItems.magicDust, 'Y', Items.glowstone_dust, 'Z', SorceryItems.ectoplasm});
		
		GameRegistry.addRecipe(new ItemStack(SorceryItems.goldegg, 1), new Object[]{"XXX", "XYX", "XXX", 'X', Items.gold_nugget, 'Y', Items.egg});
		
		GameRegistry.addRecipe(new ItemStack(SorceryItems.phoenixegg, 1), new Object[]{"X", "Y", 'X', SorceryItems.goldegg, 'Y', SorceryItems.phoenixdust});
		
		GameRegistry.addShapelessRecipe(new ItemStack(SorceryItems.enderdust, 4), new Object[]{Items.ender_pearl, new ItemStack(SorceryItems.pestleandmortar, 1, 32767)});
		GameRegistry.addShapelessRecipe(new ItemStack(SorceryItems.netherdust, 2), new Object[]{Blocks.netherrack, new ItemStack(SorceryItems.pestleandmortar, 1, 32767)});
		GameRegistry.addShapelessRecipe(new ItemStack(SorceryItems.dustCoal, 2), new Object[]{Items.coal, new ItemStack(SorceryItems.pestleandmortar, 1, 32767)});
		GameRegistry.addShapelessRecipe(new ItemStack(SorceryItems.unicorndust, 4), new Object[]{SorceryItems.unicornhorn, new ItemStack(SorceryItems.pestleandmortar, 1, 32767)});
		GameRegistry.addShapelessRecipe(new ItemStack(SorceryItems.energy, 12, 0), new Object[]{new ItemStack(SorceryItems.hardClay, 1, 0), new ItemStack(SorceryItems.chisel, 1, 32767), new ItemStack(SorceryItems.hammer, 1, 32767)});
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.sandstone, 1, 1), new Object[]{new ItemStack(Blocks.sandstone, 1, 2), new ItemStack(SorceryItems.chisel, 1, 32767), new ItemStack(SorceryItems.hammer, 1, 32767)});
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.stonebrick, 1, 3), new Object[]{new ItemStack(Blocks.stonebrick), new ItemStack(SorceryItems.chisel, 1, 32767), new ItemStack(SorceryItems.hammer, 1, 32767)});
		ArrayList<ItemStack> dusty = new ArrayList<ItemStack>();
		dusty.add(new ItemStack(SorceryItems.dustyBook));
		dusty.add(new ItemStack(SorceryItems.brush, 1, 32767));
		GameRegistry.addRecipe(new BookCleaningRecipe(new ItemStack(SorceryItems.unknownBook, 1), dusty));
		
		GameRegistry.addShapelessRecipe(new ItemStack(SorceryItems.oxygenRing, 1), new Object[]{new ItemStack(SorceryItems.jewellery, 1, 1), (new ItemStack(SorceryItems.magicOrb, 1, 4))});
		GameRegistry.addShapelessRecipe(new ItemStack(SorceryItems.hoarderRing, 1), new Object[]{new ItemStack(SorceryItems.jewellery, 1, 0), SorceryItems.magnet, SorceryItems.magicDust, SorceryItems.magicDust});
		GameRegistry.addShapelessRecipe(new ItemStack(SorceryItems.teleblockRing, 1), new Object[]{new ItemStack(SorceryItems.jewellery, 1, 2), (new ItemStack(SorceryItems.magicOrb, 1, 9))});
		
		Utils.removeRecipe(new ItemStack(Blocks.sandstone, 4, 2));
		GameRegistry.addRecipe(new ItemStack(Blocks.sandstone, 4, 2), new Object[]{"##", "##", Character.valueOf('#'), new ItemStack(Blocks.sandstone, 1, 0)});
		GameRegistry.addRecipe(new ItemStack(Sorcery.arcaneblock, 4, 9), new Object[]{"##", "##", Character.valueOf('#'), new ItemStack(Blocks.sandstone, 1, 2)});
		GameRegistry.addShapelessRecipe(new ItemStack(SorceryItems.magicDust, 4), new Object[]{Items.redstone, Items.glowstone_dust, SorceryItems.enderdust, SorceryItems.unicorndust});
		
		for(int i = 0; i < ItemStackHelper.ingots.length; i++) {
			GameRegistry.addShapelessRecipe(new ItemStack(SorceryItems.softClay, 1, 10), new Object[]{ItemStackHelper.ingots[i], new ItemStack(SorceryItems.softClay, 1, 0)});
		}
		GameRegistry.addShapelessRecipe(new ItemStack(SorceryItems.softClay, 1, 1), new Object[]{new ItemStack(SorceryItems.cog, 1, 32767), new ItemStack(SorceryItems.softClay, 1, 0)});
		GameRegistry.addShapelessRecipe(new ItemStack(SorceryItems.softClay, 1, 5), new Object[]{SorceryItems.ringWooden, new ItemStack(SorceryItems.softClay, 1, 0)});
		GameRegistry.addShapelessRecipe(new ItemStack(SorceryItems.softClay, 1, 5), new Object[]{new ItemStack(SorceryItems.jewellery, 1, 32767), new ItemStack(SorceryItems.softClay, 1, 0)});
		GameRegistry.addShapelessRecipe(new ItemStack(SorceryItems.softClay, 1, 9), new Object[]{SorceryItems.woodBall, new ItemStack(SorceryItems.softClay, 1, 0)});
		GameRegistry.addShapelessRecipe(new ItemStack(SorceryItems.softClay, 1, 9), new Object[]{new ItemStack(SorceryItems.magicOrb, 1, 32767), new ItemStack(SorceryItems.softClay, 1, 0)});
		GameRegistry.addShapelessRecipe(new ItemStack(SorceryItems.softClay, 1, 11), new Object[]{SorceryItems.plateMagic, new ItemStack(SorceryItems.softClay, 1, 0)});
		GameRegistry.addShapelessRecipe(new ItemStack(SorceryItems.softClay, 1, 11), new Object[]{SorceryItems.plateWood, new ItemStack(SorceryItems.softClay, 1, 0)});
	}
}
