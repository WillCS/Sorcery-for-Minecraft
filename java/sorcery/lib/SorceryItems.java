package sorcery.lib;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import sorcery.core.Sorcery;
import sorcery.items.ItemAntidotePotion;
import sorcery.items.ItemArcane;
import sorcery.items.ItemArcaneArmor;
import sorcery.items.ItemArcaneAxe;
import sorcery.items.ItemArcaneHoe;
import sorcery.items.ItemArcanePick;
import sorcery.items.ItemArcaneShovel;
import sorcery.items.ItemArcaneSword;
import sorcery.items.ItemBattery;
import sorcery.items.ItemBlazeBow;
import sorcery.items.ItemBlockMojoStorage;
import sorcery.items.ItemBoneBow;
import sorcery.items.ItemChocolateMilk;
import sorcery.items.ItemCog;
import sorcery.items.ItemColourTag;
import sorcery.items.ItemCraftingTool;
import sorcery.items.ItemDesk;
import sorcery.items.ItemDrill;
import sorcery.items.ItemElementBottle;
import sorcery.items.ItemElementalCore;
import sorcery.items.ItemElementalCrystal;
import sorcery.items.ItemEnergy;
import sorcery.items.ItemJewellery;
import sorcery.items.ItemLantern;
import sorcery.items.ItemLanternFuel;
import sorcery.items.ItemMachineHull;
import sorcery.items.ItemMagicBoots;
import sorcery.items.ItemMagicBucket;
import sorcery.items.ItemMagicOrb;
import sorcery.items.ItemMagicTab;
import sorcery.items.ItemMagnifyingGlass;
import sorcery.items.ItemMojoIncreaser;
import sorcery.items.ItemMojoPotion;
import sorcery.items.ItemMould;
import sorcery.items.ItemMysteryBook;
import sorcery.items.ItemNodeComponents;
import sorcery.items.ItemPowerRing;
import sorcery.items.ItemResearchJournal;
import sorcery.items.ItemSimpleRarity;
import sorcery.items.ItemSpellPage;
import sorcery.items.ItemSpellTome;
import sorcery.items.ItemSpellbook;
import sorcery.items.ItemStaff;
import sorcery.items.ItemUnicornMeat;
import sorcery.items.ItemWand;
import sorcery.items.ItemWizardHat;
import cpw.mods.fml.common.registry.GameRegistry;

public class SorceryItems {
	public static Item energy;
	public static Item wand;
	public static Item spellbook;
	public static Item spellTome;
	public static Item bonebow;
	public static Item bonearrow;
	public static Item blazebow;
	public static Item blazearrow;
	public static Item ingotSilver;
	public static Item ingotNetherrite;
	public static Item gemOnyx;
	public static Item ingotInfernite;
	public static Item netherritehelmet;
	public static Item netherritebody;
	public static Item netherritelegs;
	public static Item netherriteboots;
	public static Item infernitehelmet;
	public static Item infernitebody;
	public static Item infernitelegs;
	public static Item inferniteboots;
	public static Item netherritesword;
	public static Item netherriteshovel;
	public static Item netherritepick;
	public static Item netherriteaxe;
	public static Item netherritehoe;
	public static Item infernitesword;
	public static Item inferniteshovel;
	public static Item infernitepick;
	public static Item inferniteaxe;
	public static Item infernitehoe;
	public static Item pestleandmortar;
	public static Item chisel;
	public static Item hammer;
	public static Item enderdust;
	public static Item netherdust;
	public static Item nest;
	public static Item goldegg;
	public static Item goldfeather;
	public static Item phoenixegg;
	public static Item phoenixfeather;
	public static Item phoenixdust;
	public static Item phoenixtear;
	public static Item unicornhair;
	public static Item unicornhorn;
	public static Item unicorndust;
	public static Item unicornmeat;
	public static Item ectoplasm;
	public static Item energyInfinite;
	public static Item battery;
	public static Item magicDust;
	public static Item cog;
	public static Item chainLink;
	public static Item chain;
	public static Item silverDust;
	public static Item netherriteDust;
	public static Item inferniteDust;
	public static Item softClay;
	public static Item hardClay;
	public static Item magicOrb;
	public static Item ingotMagic;
	public static Item plateMagic;
	public static Item ingotEnder;
	public static Item sinew;
	public static Item bucketLiquidMagic;
	public static Item dustLimestone;
	public static Item powerGenerator;
	public static Item lantern;
	public static Item lanternFuel;
	public static Item brush;
	public static Item dustyBook;
	public static Item unknownBook;
	public static Item phoenixBoots;
	public static Item elementCrystal;
	public static Item woodBall;
	public static Item plateWood;
	public static Item staff;
	public static Item elementalCore;
	public static Item frostBoots;
	public static Item spellTab;
	public static Item energyBoots;
	public static Item windBoots;
	public static Item ringWooden;
	public static Item jewellery;
	public static Item oxygenRing;
	public static Item hoarderRing;
	public static Item magnet;
	public static Item teleblockRing;
	public static Item mojoPotion;
	public static Item spellPage;
	public static Item fireBoots;
	public static Item wizardHat;
	public static Item colourTag;
	public static Item nodeComponents;
	public static Item milkChocolate;
	public static Item antidotePotion;
	public static Item magnifyingGlass;
	public static Item researchJournal;
	public static Item ingotTin;
	public static Item ingotCopper;
	public static Item ingotBrass;
	public static Item ingotSteel;
	public static Item dustCoal;
	public static Item brassCast;
	public static Item desk;
	public static Item energyBottle;
	public static Item mojoStorage;
	public static Item dustTranscendent;
	public static Item mojoIncreaser;
	public static Item drillGlove;
	
	public static void registerItems() {
		// Wands and Runes
		energy = new ItemEnergy().setUnlocalizedName("runestone");
		GameRegistry.registerItem(energy, "runestone", "sorcery");
		energyBottle = new ItemElementBottle().setUnlocalizedName("energyBottle");
		GameRegistry.registerItem(energyBottle, "energyBottle", "sorcery");
		wand = new ItemWand().setFull3D().setUnlocalizedName("wand");
		GameRegistry.registerItem(wand, "wand", "sorcery");
		staff = new ItemStaff().setCreativeTab(Sorcery.tabSorcerySpellcasting).setUnlocalizedName("staff");
		GameRegistry.registerItem(staff, "staff", "sorcery");
		spellbook = new ItemSpellbook().setUnlocalizedName("spellBook");
		GameRegistry.registerItem(spellbook, "spellBook", "sorcery");
		spellPage = new ItemSpellPage().setUnlocalizedName("spellPage");
		GameRegistry.registerItem(spellPage, "spellPage", "sorcery");
		spellTab = new ItemMagicTab().setUnlocalizedName("spellTab"); //TODO redo Spell Tablets
		GameRegistry.registerItem(spellTab, "spellTab", "sorcery");
		energyInfinite = new ItemSimpleRarity(EnumRarity.epic).setMaxStackSize(1).setCreativeTab(Sorcery.tabSorcerySpellcasting).setUnlocalizedName("runeInfinite");
		GameRegistry.registerItem(energyInfinite, "runeInfinite", "sorcery");
		
		// Bows
		bonebow = new ItemBoneBow().setFull3D().setUnlocalizedName("bowBone").setCreativeTab(Sorcery.tabSorceryTools);
		GameRegistry.registerItem(bonebow, "bowBone", "sorcery");
		bonearrow = new ItemArcane().setCreativeTab(CreativeTabs.tabCombat).setFull3D().setUnlocalizedName("arrowBone").setCreativeTab(Sorcery.tabSorceryTools);
		GameRegistry.registerItem(bonearrow, "arrowBone", "sorcery");
		blazebow = new ItemBlazeBow().setFull3D().setUnlocalizedName("bowBlaze").setCreativeTab(Sorcery.tabSorceryTools);
		GameRegistry.registerItem(blazebow, "bowBlaze", "sorcery");
		blazearrow = new ItemArcane().setCreativeTab(CreativeTabs.tabCombat).setFull3D().setUnlocalizedName("arrowBlaze").setCreativeTab(Sorcery.tabSorceryTools);
		GameRegistry.registerItem(blazearrow, "arrowBlaze", "sorcery");
		
		// Metals/Gems
		ingotSilver = new ItemArcane().setCreativeTab(Sorcery.tabSorceryMaterials).setUnlocalizedName("ingotSilver");
		GameRegistry.registerItem(ingotSilver, "ingotSilver", "sorcery");
		ingotNetherrite = new ItemArcane().setCreativeTab(Sorcery.tabSorceryMaterials).setUnlocalizedName("ingotNetherrite");
		GameRegistry.registerItem(ingotNetherrite, "ingotNetherrite", "sorcery");
		gemOnyx = new ItemArcane().setCreativeTab(Sorcery.tabSorceryMaterials).setUnlocalizedName("gemOnyx");
		GameRegistry.registerItem(gemOnyx, "gemOnyx", "sorcery");
		ingotInfernite = new ItemArcane().setCreativeTab(Sorcery.tabSorceryMaterials).setUnlocalizedName("ingotInfernite");
		GameRegistry.registerItem(ingotInfernite, "ingotInfernite", "sorcery");
		ingotMagic = new ItemArcane().setCreativeTab(Sorcery.tabSorceryMaterials).setUnlocalizedName("ingotMagic");
		GameRegistry.registerItem(ingotMagic, "ingotMagic", "sorcery");
		plateMagic = new ItemArcane().setCreativeTab(Sorcery.tabSorceryMaterials).setUnlocalizedName("plateMagic");
		GameRegistry.registerItem(plateMagic, "plateMagic", "sorcery");
		ingotEnder = new ItemArcane().setCreativeTab(Sorcery.tabSorceryMaterials).setUnlocalizedName("ingotEnder");
		GameRegistry.registerItem(ingotEnder, "ingotEnder", "sorcery");
		ingotTin = new ItemArcane().setCreativeTab(Sorcery.tabSorceryMaterials).setUnlocalizedName("ingotTin");
		GameRegistry.registerItem(ingotTin, "ingotTin", "sorcery");
		ingotCopper = new ItemArcane().setCreativeTab(Sorcery.tabSorceryMaterials).setUnlocalizedName("ingotCopper");
		GameRegistry.registerItem(ingotCopper, "ingotCopper", "sorcery");
		ingotBrass = new ItemArcane().setCreativeTab(Sorcery.tabSorceryMaterials).setUnlocalizedName("ingotBrass");
		GameRegistry.registerItem(ingotBrass, "ingotBrass", "sorcery");
		ingotSteel = new ItemArcane().setCreativeTab(Sorcery.tabSorceryMaterials).setUnlocalizedName("ingotSteel");
		GameRegistry.registerItem(ingotSteel, "ingotSteel", "sorcery");
		
		// Metal Dusts
		silverDust = new ItemArcane().setCreativeTab(Sorcery.tabSorceryMaterials).setUnlocalizedName("dustSilver");
		GameRegistry.registerItem(silverDust, "dustSilver", "sorcery");
		netherriteDust = new ItemArcane().setCreativeTab(Sorcery.tabSorceryMaterials).setUnlocalizedName("dustNetherrite");
		GameRegistry.registerItem(netherriteDust, "dustNetherrite", "sorcery");
		inferniteDust = new ItemArcane().setCreativeTab(Sorcery.tabSorceryMaterials).setUnlocalizedName("dustInfernite");
		GameRegistry.registerItem(inferniteDust, "dustInfernite", "sorcery");
		
		// Tools and Armour
		netherritehelmet = new ItemArcaneArmor(SorceryEnums.armorNETHERRITE, Sorcery.proxy.addArmor("Netherrite"), 0).setUnlocalizedName("helmetNetherrite").setCreativeTab(Sorcery.tabSorceryTools);
		GameRegistry.registerItem(netherritehelmet, "helmetNetherrite", "sorcery");
		netherritebody = new ItemArcaneArmor(SorceryEnums.armorNETHERRITE, Sorcery.proxy.addArmor("Netherrite"), 1).setUnlocalizedName("bodyNetherrite").setCreativeTab(Sorcery.tabSorceryTools);
		GameRegistry.registerItem(netherritebody, "bodyNetherrite", "sorcery");
		netherritelegs = new ItemArcaneArmor(SorceryEnums.armorNETHERRITE, Sorcery.proxy.addArmor("Netherrite"), 2).setUnlocalizedName("legsNetherrite").setCreativeTab(Sorcery.tabSorceryTools);
		GameRegistry.registerItem(netherritelegs, "legsNetherrite", "sorcery");
		netherriteboots = new ItemArcaneArmor(SorceryEnums.armorNETHERRITE, Sorcery.proxy.addArmor("Netherrite"), 3).setUnlocalizedName("bootsNetherrite").setCreativeTab(Sorcery.tabSorceryTools);
		GameRegistry.registerItem(netherriteboots, "bootsNetherrite", "sorcery");
		
		infernitehelmet = new ItemArcaneArmor(SorceryEnums.armorINFERNITE, Sorcery.proxy.addArmor("Infernite"), 0).setUnlocalizedName("helmetInfernite").setCreativeTab(Sorcery.tabSorceryTools);
		GameRegistry.registerItem(infernitehelmet, "helmetInfernite", "sorcery");
		infernitebody = new ItemArcaneArmor(SorceryEnums.armorINFERNITE, Sorcery.proxy.addArmor("Infernite"), 1).setUnlocalizedName("bodyInfernite").setCreativeTab(Sorcery.tabSorceryTools);
		GameRegistry.registerItem(infernitebody, "bodyInfernite", "sorcery");
		infernitelegs = new ItemArcaneArmor(SorceryEnums.armorINFERNITE, Sorcery.proxy.addArmor("Infernite"), 2).setUnlocalizedName("legsInfernite").setCreativeTab(Sorcery.tabSorceryTools);
		GameRegistry.registerItem(infernitelegs, "legsInfernite", "sorcery");
		inferniteboots = new ItemArcaneArmor(SorceryEnums.armorINFERNITE, Sorcery.proxy.addArmor("Infernite"), 3).setUnlocalizedName("bootsInfernite").setCreativeTab(Sorcery.tabSorceryTools);
		GameRegistry.registerItem(inferniteboots, "bootsInfernite", "sorcery");
				
		netherritesword = new ItemArcaneSword(SorceryEnums.toolNETHERRITE).setUnlocalizedName("swordNetherrite").setCreativeTab(Sorcery.tabSorceryTools);
		GameRegistry.registerItem(netherritesword, "swordNetherrite", "sorcery");
		netherriteshovel = new ItemArcaneShovel(SorceryEnums.toolNETHERRITE).setUnlocalizedName("shovelNetherrite").setCreativeTab(Sorcery.tabSorceryTools);
		GameRegistry.registerItem(netherriteshovel, "shovelNetherrite", "sorcery");
		netherritepick = new ItemArcanePick(SorceryEnums.toolNETHERRITE).setUnlocalizedName("pickaxeNetherrite").setCreativeTab(Sorcery.tabSorceryTools);
		GameRegistry.registerItem(netherritepick, "pickNetherrite", "sorcery");
		netherriteaxe = new ItemArcaneAxe(SorceryEnums.toolNETHERRITE).setUnlocalizedName("axeNetherrite").setCreativeTab(Sorcery.tabSorceryTools);
		GameRegistry.registerItem(netherriteaxe, "axeNetherrite", "sorcery");
		netherritehoe = new ItemArcaneHoe(SorceryEnums.toolNETHERRITE).setUnlocalizedName("hoeNetherrite").setCreativeTab(Sorcery.tabSorceryTools);
		GameRegistry.registerItem(netherritehoe, "hoeNetherrite", "sorcery");
		
		infernitesword = new ItemArcaneSword(SorceryEnums.toolINFERNITE).setUnlocalizedName("swordInfernite").setCreativeTab(Sorcery.tabSorceryTools);
		GameRegistry.registerItem(infernitesword, "swordInfernite", "sorcery");
		inferniteshovel = new ItemArcaneShovel(SorceryEnums.toolINFERNITE).setUnlocalizedName("shovelInfernite").setCreativeTab(Sorcery.tabSorceryTools);
		GameRegistry.registerItem(inferniteshovel, "shovelInfernite", "sorcery");
		infernitepick = new ItemArcanePick(SorceryEnums.toolINFERNITE).setUnlocalizedName("pickaxeInfernite").setCreativeTab(Sorcery.tabSorceryTools);
		GameRegistry.registerItem(infernitepick, "pickInfernite", "sorcery");
		inferniteaxe = new ItemArcaneAxe(SorceryEnums.toolINFERNITE).setUnlocalizedName("axeInfernite").setCreativeTab(Sorcery.tabSorceryTools);
		GameRegistry.registerItem(inferniteaxe, "axeInfernite", "sorcery");
		infernitehoe = new ItemArcaneHoe(SorceryEnums.toolINFERNITE).setUnlocalizedName("hoeInfernite").setCreativeTab(Sorcery.tabSorceryTools);
		GameRegistry.registerItem(infernitehoe, "hoeInfernite", "sorcery");
		
		drillGlove = new ItemDrill().setUnlocalizedName("drillGlove");
		GameRegistry.registerItem(drillGlove, "drillGlove", "sorcery");
		
		lantern = new ItemLantern().setCreativeTab(Sorcery.tabSorceryTools).setNoRepair().setMaxStackSize(1).setUnlocalizedName("lantern");
		GameRegistry.registerItem(lantern, "lantern", "sorcery");
		
		// Crafting Tools
		pestleandmortar = new ItemCraftingTool().setCreativeTab(Sorcery.tabSorceryTools).setNoRepair().setMaxStackSize(1).setUnlocalizedName("craftingToolMortar");
		GameRegistry.registerItem(pestleandmortar, "craftingToolMortar", "sorcery");
		chisel = new ItemCraftingTool().setCreativeTab(Sorcery.tabSorceryTools).setMaxStackSize(1).setNoRepair().setFull3D().setUnlocalizedName("craftingToolChisel");
		GameRegistry.registerItem(chisel, "craftingToolChisel", "sorcery");
		hammer = new ItemCraftingTool().setCreativeTab(Sorcery.tabSorceryTools).setMaxStackSize(1).setNoRepair().setFull3D().setUnlocalizedName("craftingToolHammer");
		GameRegistry.registerItem(hammer, "craftingToolHammer", "sorcery");
		brush = new ItemCraftingTool().setFull3D().setCreativeTab(Sorcery.tabSorceryTools).setUnlocalizedName("craftingToolDuster");
		GameRegistry.registerItem(brush, "craftingToolDuster", "sorcery");
		
		// Books
		dustyBook = new ItemArcane().setCreativeTab(Sorcery.tabSorceryMaterials).setMaxStackSize(1).setUnlocalizedName("bookDusty");
		GameRegistry.registerItem(dustyBook, "bookDusty", "sorcery");
		unknownBook = new ItemMysteryBook().setMaxStackSize(1).setUnlocalizedName("bookUnknown");
		GameRegistry.registerItem(unknownBook, "bookUnknown", "sorcery");
		spellTome = new ItemSpellTome().setUnlocalizedName("spellTome");
		GameRegistry.registerItem(spellTome, "spellTome", "sorcery");
		
		// Crafting Ingredients
		enderdust = new ItemArcane().setCreativeTab(Sorcery.tabSorceryMaterials).setUnlocalizedName("dustEnder");
		GameRegistry.registerItem(enderdust, "dustEnder", "sorcery");
		netherdust = new ItemArcane().setCreativeTab(Sorcery.tabSorceryMaterials).setUnlocalizedName("dustNetherrack");
		GameRegistry.registerItem(netherdust, "dustNether", "sorcery");
		dustCoal = new ItemArcane().setCreativeTab(Sorcery.tabSorceryMaterials).setUnlocalizedName("dustCoal");
		GameRegistry.registerItem(dustCoal, "dustCoal", "sorcery");
		nest = new ItemArcane().setCreativeTab(Sorcery.tabSorceryMaterials).setUnlocalizedName("nest");
		GameRegistry.registerItem(nest, "nest", "sorcery");
		goldegg = new ItemArcane().setCreativeTab(Sorcery.tabSorceryMaterials).setMaxStackSize(16).setUnlocalizedName("chickengEgg");
		GameRegistry.registerItem(goldegg, "chickengEgg", "sorcery");
		goldfeather = new ItemArcane().setCreativeTab(Sorcery.tabSorceryMaterials).setUnlocalizedName("chickengFeather");
		GameRegistry.registerItem(goldfeather, "chickengFeather", "sorcery");
		phoenixegg = new ItemArcane().setCreativeTab(Sorcery.tabSorceryMaterials).setMaxStackSize(16).setUnlocalizedName("phoenixEgg");
		GameRegistry.registerItem(phoenixegg, "phoenixEgg", "sorcery");
		phoenixfeather = new ItemArcane().setCreativeTab(Sorcery.tabSorceryMaterials).setUnlocalizedName("phoenixFeather");
		GameRegistry.registerItem(phoenixfeather, "phoenixFeather", "sorcery");
		phoenixdust = new ItemArcane().setCreativeTab(Sorcery.tabSorceryMaterials).setUnlocalizedName("phoenixDust");
		GameRegistry.registerItem(phoenixdust, "phoenixDust", "sorcery");
		phoenixtear = new ItemArcane().setCreativeTab(Sorcery.tabSorceryMaterials).setUnlocalizedName("phoenixTear");
		GameRegistry.registerItem(phoenixtear, "phoenixTear", "sorcery");
		unicornhair = new ItemArcane().setCreativeTab(Sorcery.tabSorceryMaterials).setUnlocalizedName("unicornHair");
		GameRegistry.registerItem(unicornhair, "unicornHair", "sorcery");
		unicornhorn = new ItemArcane().setCreativeTab(Sorcery.tabSorceryMaterials).setFull3D().setUnlocalizedName("unicornHorn");
		GameRegistry.registerItem(unicornhorn, "unicornHorn", "sorcery");
		unicorndust = new ItemArcane().setCreativeTab(Sorcery.tabSorceryMaterials).setUnlocalizedName("unicornDust");
		GameRegistry.registerItem(unicorndust, "unicornDust", "sorcery");
		ectoplasm = new ItemArcane().setCreativeTab(Sorcery.tabSorceryMaterials).setUnlocalizedName("shadeEctoplasm");
		GameRegistry.registerItem(ectoplasm, "shadeEctoplasm", "sorcery");
		magicDust = new ItemArcane().setCreativeTab(Sorcery.tabSorceryMaterials).setUnlocalizedName("dustMagic");
		GameRegistry.registerItem(magicDust, "dustMagic", "sorcery");
		cog = new ItemCog().setCreativeTab(Sorcery.tabSorceryMaterials).setUnlocalizedName("cog");
		GameRegistry.registerItem(cog, "cog", "sorcery");
		chainLink = new ItemArcane().setCreativeTab(Sorcery.tabSorceryMaterials).setUnlocalizedName("chainLink");
		GameRegistry.registerItem(chainLink, "chainLink", "sorcery");
		chain = new ItemArcane().setCreativeTab(Sorcery.tabSorceryMaterials).setUnlocalizedName("chainItem");
		GameRegistry.registerItem(chain, "chainItem", "sorcery");
		woodBall = new ItemArcane().setCreativeTab(Sorcery.tabSorceryMaterials).setUnlocalizedName("orbWooden");
		GameRegistry.registerItem(woodBall, "orbWooden", "sorcery");
		plateWood = new ItemArcane().setCreativeTab(Sorcery.tabSorceryMaterials).setUnlocalizedName("plateWooden");
		GameRegistry.registerItem(plateWood, "plateWooden", "sorcery");
		ringWooden = new ItemArcane().setCreativeTab(Sorcery.tabSorceryMaterials).setUnlocalizedName("jewelleryRingWooden");
		GameRegistry.registerItem(ringWooden, "jewelleryRingWooden", "sorcery");
		powerGenerator = new ItemMachineHull().setUnlocalizedName("machineHull");
		GameRegistry.registerItem(powerGenerator, "powerGenerator", "sorcery");
		magnet = new ItemArcane().setMaxStackSize(1).setCreativeTab(Sorcery.tabSorceryMaterials).setUnlocalizedName("magnet");
		GameRegistry.registerItem(magnet, "magnet", "sorcery");
		sinew = new ItemArcane().setCreativeTab(Sorcery.tabSorceryMaterials).setUnlocalizedName("sinew");
		GameRegistry.registerItem(sinew, "sinew", "sorcery");
		dustLimestone = new ItemArcane().setCreativeTab(Sorcery.tabSorceryMaterials).setUnlocalizedName("dustLimestone");
		GameRegistry.registerItem(dustLimestone, "dustLimestone", "sorcery");
		dustTranscendent = new ItemSimpleRarity(EnumRarity.uncommon).setCreativeTab(Sorcery.tabSorceryMaterials).setUnlocalizedName("dustTranscendent");
		GameRegistry.registerItem(dustTranscendent, "dustTranscendent", "sorcery");
		
		// Food
		unicornmeat = new ItemUnicornMeat().setUnlocalizedName("unicornMeat");
		GameRegistry.registerItem(unicornmeat, "unicornMeat", "sorcery");
		
		// Moulds
		softClay = new ItemMould().setUnlocalizedName("softMould");
		GameRegistry.registerItem(softClay, "softMould", "sorcery");
		hardClay = new ItemMould().setUnlocalizedName("mould");
		GameRegistry.registerItem(hardClay, "mould", "sorcery");
		brassCast = new ItemMould().setUnlocalizedName("cast");
		GameRegistry.registerItem(brassCast, "cast", "sorcery");
		
		// Liquids
		bucketLiquidMagic = new ItemMagicBucket().setContainerItem(Items.bucket).setMaxStackSize(1).setUnlocalizedName("sorceryBucket");
		GameRegistry.registerItem(bucketLiquidMagic, "sorceryBucket", "sorcery");
		mojoPotion = new ItemMojoPotion().setUnlocalizedName("mojoPotion");
		GameRegistry.registerItem(mojoPotion, "mojoPotion", "sorcery");
		milkChocolate = new ItemChocolateMilk().setUnlocalizedName("bottleChocolateMilk");
		GameRegistry.registerItem(milkChocolate, "bottleChocolateMilk", "sorcery");
		antidotePotion = new ItemAntidotePotion().setUnlocalizedName("antidotePotion");
		GameRegistry.registerItem(antidotePotion, "antidotePotion", "sorcery");
		lanternFuel = new ItemLanternFuel().setUnlocalizedName("lanternFuel");
		GameRegistry.registerItem(lanternFuel, "lanternFuel", "sorcery");
		
		// Seeds
		//greenWartSeeds = new ItemWartSeeds(Sorcery.greenWart).setCreativeTab(Sorcery.tabSorceryMaterials).setUnlocalizedName("seedsGreenWart");
		
		// voidLink = new
		// ItemVoidLink(voidLinkItemID)(1).setCreativeTab(Sorcery.tabSorceryMaterials).setUnlocalizedName("voidLink");
		
		// Elemental Objects
		elementCrystal = new ItemElementalCrystal().setUnlocalizedName("elementalCrystal");
		GameRegistry.registerItem(elementCrystal, "elementalCrystal", "sorcery");
		magicOrb = new ItemMagicOrb().setUnlocalizedName("elementalOrb");
		GameRegistry.registerItem(magicOrb, "elementalOrb", "sorcery");
		elementalCore = new ItemElementalCore().setUnlocalizedName("elementalCore");
		GameRegistry.registerItem(elementalCore, "elementalCore", "sorcery");
		
		// Mojo
		mojoStorage = new ItemBlockMojoStorage().setUnlocalizedName("mojoStorageBlockItem");
		GameRegistry.registerItem(mojoStorage, "mojoStorageBlockItem", "sorcery");
		battery = new ItemBattery().setCreativeTab(Sorcery.tabSorceryMaterials).setUnlocalizedName("crystalMagic");
		GameRegistry.registerItem(battery, "crystalMagic", "sorcery");
		mojoIncreaser = new ItemMojoIncreaser().setUnlocalizedName("mojoIncreaser");
		GameRegistry.registerItem(mojoIncreaser, "mojoIncreaser", "sorcery");
		
		// Power Armour
		frostBoots = new ItemMagicBoots(Sorcery.proxy.addArmor("frost")).setUnlocalizedName("elementBootsFrost");
		GameRegistry.registerItem(frostBoots, "elementBootsFrost", "sorcery");
		energyBoots = new ItemMagicBoots(Sorcery.proxy.addArmor("energy")).setUnlocalizedName("elementBootsEnergy");
		GameRegistry.registerItem(energyBoots, "elementBootsEnergy", "sorcery");
		windBoots = new ItemMagicBoots(Sorcery.proxy.addArmor("wind")).setUnlocalizedName("elementBootsWind");
		GameRegistry.registerItem(windBoots, "elementBootsWind", "sorcery");
		fireBoots = new ItemMagicBoots(Sorcery.proxy.addArmor("fire")).setUnlocalizedName("elementBootsFire");
		GameRegistry.registerItem(fireBoots, "elementBootsFire", "sorcery");
		phoenixBoots = new ItemMagicBoots(Sorcery.proxy.addArmor("phoenix")).setUnlocalizedName("powerBootsPhoenix");
		GameRegistry.registerItem(phoenixBoots, "powerBootsPhoenix", "sorcery");
		
		wizardHat = new ItemWizardHat(Sorcery.proxy.addArmor("wizardHat")).setUnlocalizedName("wizardHat");
		GameRegistry.registerItem(wizardHat, "wizardHat", "sorcery");
		
		jewellery = new ItemJewellery().setCreativeTab(Sorcery.tabSorceryMaterials).setUnlocalizedName("jewellery");
		GameRegistry.registerItem(jewellery, "jewellery", "sorcery");
		
		// Power Items
		oxygenRing = new ItemPowerRing().setUnlocalizedName("powerRingOxygen");
		GameRegistry.registerItem(oxygenRing, "powerRingOxygen", "sorcery");
		hoarderRing = new ItemPowerRing().setUnlocalizedName("powerRingHoarding");
		GameRegistry.registerItem(hoarderRing, "powerRingHoarding", "sorcery");
		teleblockRing = new ItemPowerRing().setUnlocalizedName("powerRingTeleblock");
		GameRegistry.registerItem(teleblockRing, "powerRingTeleblock", "sorcery");
		
		// Node Transport
		colourTag = new ItemColourTag().setUnlocalizedName("colourTag");
		GameRegistry.registerItem(colourTag, "colourTag", "sorcery");
		nodeComponents = new ItemNodeComponents().setUnlocalizedName("nodeComponents");
		GameRegistry.registerItem(nodeComponents, "nodeComponents", "sorcery");
		
		// Research
		magnifyingGlass = new ItemMagnifyingGlass().setUnlocalizedName("magnifyingGlass").setCreativeTab(Sorcery.tabSorceryTools);
		GameRegistry.registerItem(magnifyingGlass, "magnifyingGlass", "sorcery");
		researchJournal = new ItemResearchJournal().setUnlocalizedName("researchJournal").setCreativeTab(Sorcery.tabSorceryTools);
		GameRegistry.registerItem(researchJournal, "researchJournal", "sorcery");
		desk = new ItemDesk().setUnlocalizedName("desk").setCreativeTab(Sorcery.tabSorceryTech);
		GameRegistry.registerItem(desk, "desk", "sorcery");
	}
}
