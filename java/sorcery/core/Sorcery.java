package sorcery.core;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.command.CommandHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.AchievementPage;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.oredict.OreDictionary;

import org.apache.logging.log4j.Level;

import sorcery.api.SorceryAPI;
import sorcery.api.recipe.MetalRegistry;
import sorcery.blocks.BlockAir;
import sorcery.blocks.BlockArcane;
import sorcery.blocks.BlockArcaneHalf;
import sorcery.blocks.BlockCloud;
import sorcery.blocks.BlockMachine;
import sorcery.blocks.BlockMagicLeaves;
import sorcery.blocks.BlockMagicSapling;
import sorcery.blocks.BlockMetalBricks;
import sorcery.blocks.BlockMojoMachine;
import sorcery.blocks.BlockMojoWire;
import sorcery.blocks.BlockNodeTransport;
import sorcery.blocks.BlockPipe;
import sorcery.blocks.BlockRune;
import sorcery.blocks.BlockSlime;
import sorcery.blocks.ItemArcaneBlock;
import sorcery.blocks.ItemArcaneHalf;
import sorcery.blocks.ItemBlockMojoMachine;
import sorcery.blocks.ItemMachineBlock;
import sorcery.blocks.ItemMagicLeaves;
import sorcery.blocks.ItemMetalBricks;
import sorcery.blocks.ItemMojoWire;
import sorcery.blocks.ItemNodeTransport;
import sorcery.blocks.ItemPipe;
import sorcery.blocks.ItemRuneBlock;
import sorcery.blocks.ItemSlimeBlock;
import sorcery.blocks.materials.MaterialSlime;
import sorcery.blocks.materials.StepSoundSorcery;
import sorcery.commands.CommandMojo;
import sorcery.commands.CommandSorcery;
import sorcery.entities.EntityBlazeArrow;
import sorcery.entities.EntityBoneArrow;
import sorcery.entities.EntityGhost;
import sorcery.entities.EntityGoldenChicken;
import sorcery.entities.EntityLantern;
import sorcery.entities.EntityPhoenix;
import sorcery.entities.EntityUnicorn;
import sorcery.entities.EntityUnicow;
import sorcery.entities.spells.EntitySpellProjectile;
import sorcery.fluid.PipeHelper;
import sorcery.lib.Config;
import sorcery.lib.EventScheduler;
import sorcery.lib.Lang;
import sorcery.lib.Properties;
import sorcery.lib.ResearchLoader;
import sorcery.lib.SorceryEnums;
import sorcery.lib.SorceryFluids;
import sorcery.lib.SorceryItems;
import sorcery.lib.SorcerySpells;
import sorcery.lib.SpellHelper;
import sorcery.lib.WandComponentRegistry;
import sorcery.lib.utils.Utils;
import sorcery.network.PacketPipeline;
import sorcery.recipes.CraftingRecipes;
import sorcery.recipes.ForgeRecipes;
import sorcery.recipes.InfuserRecipes;
import sorcery.recipes.MixerRecipes;
import sorcery.recipes.SmeltingRecipes;
import sorcery.recipes.TinkeringRecipes;
import sorcery.spellcomponents.SpellComponentRegistry;
import sorcery.world.BiomeGenEnchantedForest;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = "Sorcery", name = "Sorcery", version = Properties.VERSION)
public class Sorcery {
	@Mod.Instance
	public static Sorcery instance = new Sorcery();
	
	@SidedProxy(clientSide = "sorcery.network.ClientProxy", serverSide = "sorcery.network.CommonProxy")
	public static sorcery.network.CommonProxy proxy;
	
	@SidedProxy(clientSide = "sorcery.lib.utils.PlayerControlsClient", serverSide = "sorcery.lib.utils.PlayerControls")
	public static sorcery.lib.utils.PlayerControls playerControls;
	
	public static EventScheduler eventScheduler = new EventScheduler();
	public static PacketPipeline packetPipeline = new PacketPipeline();
	
	public static CreativeTabs tabSorceryMaterials;
	public static CreativeTabs tabSorceryTech;
	public static CreativeTabs tabSorceryTools;
	public static CreativeTabs tabSorcerySpellcasting;
	public static Material materialSlime;
	public static Block.SoundType stepSoundSlime;
	public static DamageSource spellDMG;
	public static BiomeGenBase enchantedForest;
	
	public static Block arcaneblock;
	public static Block cloud;
	public static Block arcanehalf;
	public static Block machine;
	public static Block runeBlock;
	public static Block airBlock;
	public static Block slimeBlock;
	public static Block itemNode;
	public static Block magicLeaves;
	public static Block magicSapling;
	public static Block metalBricks;
	public static Block fluidPipe;
	public static Block mojoMachine;
	public static Block mojoWire;
	
	public static AchievementPage sorceryAchPage;
	public static Achievement soShiny;
	public static Achievement wizardHarry;
	public static Achievement loveStory;
	public static Achievement babbyFormed;
	// public static Achievement anotherTable;
	public static Achievement inventor;
	public static Achievement geneticEngineering;
	public static Achievement aChickenNoMore;
	public static Achievement meltingTopic;
	public static Achievement hellFire;
	public static Achievement blacksmith;
	public static Achievement redHotMetal;
	public static Achievement green;
	public static Achievement notForFood;
	public static Achievement elementMaster;
	public static Achievement shakeIt;
	
	public static Sorcery getInstance() {
		return instance;
	}
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		// Sounds
		proxy.registerSounds();
		
		// Guis
		NetworkRegistry.INSTANCE.registerGuiHandler(this, proxy);
		
		Config.instance.config(event);
		
		SorceryAPI.forgeRecipeManager = ForgeRecipes.getInstance();
		SorceryAPI.tinkeringRecipeManager = TinkeringRecipes.getInstance();
		SorceryAPI.mixerRecipeManager = MixerRecipes.getInstance();
		SorceryAPI.infuserRecipeManager = InfuserRecipes.getInstance();
		
		SorceryAPI.sorcery = Sorcery.getInstance();
		SorceryAPI.spellHelper = SpellHelper.getInstance();
		
		SorceryEnums.registerEnums();
		
		tabSorceryMaterials = new CreativeTabMagic("sorceryMaterials");
		tabSorceryTech = new CreativeTabMagic("sorceryTech");
		tabSorceryTools = new CreativeTabMagic("sorceryTools");
		tabSorcerySpellcasting = new CreativeTabMagic("sorcerySpellcasting");
		materialSlime = new MaterialSlime(MapColor.foliageColor);
		stepSoundSlime = new StepSoundSorcery("slime", 1.0F, 1.0F);
		
		SorceryFluids.registerFluids();
		proxy.registerWandComponents();
		SorcerySpells.registerElements();
		SpellComponentRegistry.registerComponents();
		registerBlocks();
		SorceryItems.registerItems();
		ResearchLoader.loadResearch();
		registerAchievements();
		registerMetals();
		SorceryFluids.registerFluidContainers();
		PipeHelper.registerPipes();
		WandComponentRegistry.registerItems();
		
		proxy.registerEventHandlers();
		
		// spellDMG = new DamageSource("spell");
		enchantedForest = new BiomeGenEnchantedForest(Properties.biomeEnchantedForestID).setBiomeName("Enchanted Forest").setTemperatureRainfall(0.7F, 0.8F);
	}
	
	@Mod.EventHandler
	public void load(FMLInitializationEvent event) {
		SorcerySpells.registerEnchantments();
		SorcerySpells.registerPotions();
		
		if(Properties.biomeGenEnchantedForest) {
			BiomeDictionary.registerBiomeType(this.enchantedForest, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.MAGICAL);
		}
		
		Lang.loadLanguage("en_US");
		
		packetPipeline.initialise();
		
		proxy.registerVillageComponents();
		
		// Smelting recipes
		SmeltingRecipes.getInstance().addRecipes();
		
		// Recipe Manager
		new CraftingRecipes().addRecipes(this);
		
		// Dungeon Loot
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(SorceryItems.energy, 0, 1, 4, 20));
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(SorceryItems.goldfeather), 1, 4, 30));
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(Items.experience_bottle), 1, 4, 40));
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(SorceryItems.cog), 1, 4, 20));
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(SorceryItems.dustyBook), 1, 2, 30));
		
		//TODO re-add custom spell pages
		//ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(SpellHelper.instance.getPageFromSpellIDs(SorcerySpells.enderChest.spellID), 1, 2, 20));
		//ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(SpellHelper.instance.getPageFromSpellIDs(SorcerySpells.enderpearl.spellID), 1, 2, 20));
		
		// Ore Dictionary
		OreDictionary.registerOre("ingotSilver", new ItemStack(SorceryItems.ingotSilver));
		OreDictionary.registerOre("oreSilver", new ItemStack(Sorcery.arcaneblock, 1, 0));
		OreDictionary.registerOre("dustSilver", new ItemStack(SorceryItems.silverDust));
		OreDictionary.registerOre("ingotNetherrite", new ItemStack(SorceryItems.ingotNetherrite));
		OreDictionary.registerOre("oreNetherrite", new ItemStack(Sorcery.arcaneblock, 1, 1));
		OreDictionary.registerOre("dustNetherrite", new ItemStack(SorceryItems.netherriteDust));
		OreDictionary.registerOre("gemOnyx", new ItemStack(SorceryItems.gemOnyx));
		OreDictionary.registerOre("oreOnyx", new ItemStack(Sorcery.arcaneblock, 1, 2));
		OreDictionary.registerOre("ingotInfernite", new ItemStack(SorceryItems.ingotInfernite));
		OreDictionary.registerOre("oreInfernite", new ItemStack(Sorcery.arcaneblock, 1, 3));
		OreDictionary.registerOre("dustInfernite", new ItemStack(SorceryItems.inferniteDust));
		OreDictionary.registerOre("ingotEnder", new ItemStack(SorceryItems.ingotEnder));
		OreDictionary.registerOre("ingotMagic", new ItemStack(SorceryItems.ingotMagic));
		OreDictionary.registerOre("plateMagic", new ItemStack(SorceryItems.plateMagic));
		OreDictionary.registerOre("ingotTin", new ItemStack(SorceryItems.ingotTin));
		OreDictionary.registerOre("oreTin", new ItemStack(Sorcery.arcaneblock, 1, 13));
		OreDictionary.registerOre("ingotCopper", new ItemStack(SorceryItems.ingotCopper));
		OreDictionary.registerOre("oreCopper", new ItemStack(Sorcery.arcaneblock, 1, 14));
		OreDictionary.registerOre("ingotBrass", new ItemStack(SorceryItems.ingotBrass));
		OreDictionary.registerOre("ingotSteel", new ItemStack(SorceryItems.ingotSteel));
		OreDictionary.registerOre("dustNetherrack", new ItemStack(SorceryItems.netherdust));
		OreDictionary.registerOre("dustEnder", new ItemStack(SorceryItems.enderdust));
		OreDictionary.registerOre("dustMagic", new ItemStack(SorceryItems.magicDust));
		OreDictionary.registerOre("dustCoal", new ItemStack(SorceryItems.dustCoal));
		OreDictionary.registerOre("dustLimestone", new ItemStack(SorceryItems.dustLimestone));
		
		// Mobs and Textures
		proxy.registerRenderInformation();
		
		// World Gen
		GameRegistry.registerWorldGenerator(new WorldGen(), 1);
		
		// Unicorn Related Stuff
		int unicorncolour1 = (204 << 16) + (255 << 8) + 204;
		int unicorncolour2 = (255 << 16) + (82 << 8) + 255;
		EntityRegistry.registerGlobalEntityID(EntityUnicorn.class, "unicorn", EntityRegistry.findGlobalUniqueEntityId(), unicorncolour1, unicorncolour2);
		EntityRegistry.registerModEntity(EntityUnicorn.class, "unicorn", Properties.unicornEntityID, this, 64, 1, true);
		// EntityRegistry.addSpawn(EntityUnicorn.class, 2, 2, 2,
		// EnumCreatureType.creature, WorldType.base12Biomes);
		
		// Phoenix Related Stuff
		int phoenixcolour1 = (255 << 16) + (132 << 8);
		int phoenixcolour2 = (255 << 16);
		EntityRegistry.registerGlobalEntityID(EntityPhoenix.class, "phoenix", EntityRegistry.findGlobalUniqueEntityId(), phoenixcolour1, phoenixcolour2);
		EntityRegistry.registerModEntity(EntityPhoenix.class, "phoenix", Properties.phoenixEntityID, this, 64, 1, true);
		
		// Golden Chicken Related Stuff
		int goldchickencolour1 = (244 << 16) + (255 << 8) + 31;
		int goldchickencolour2 = 0;
		EntityRegistry.registerGlobalEntityID(EntityGoldenChicken.class, "goldenChicken", EntityRegistry.findGlobalUniqueEntityId(), goldchickencolour1, goldchickencolour2);
		EntityRegistry.registerModEntity(EntityGoldenChicken.class, "goldenChicken", Properties.chickenGoldEntityID, this, 64, 1, true);
		
		// Unicow Related Stuff
		int unicowColour1 = (255 << 16) + (255 << 8) + 255;
		int unicowColour2 = 0;
		EntityRegistry.registerGlobalEntityID(EntityUnicow.class, "unicow", EntityRegistry.findGlobalUniqueEntityId(), unicowColour1, unicowColour2);
		EntityRegistry.registerModEntity(EntityUnicow.class, "unicow", Properties.unicowEntityID, this, 64, 1, true);
		EntityRegistry.addSpawn(EntityUnicow.class, 2, 2, 3, EnumCreatureType.creature, BiomeGenBase.forest, BiomeGenBase.plains, BiomeGenBase.extremeHills);
		
		// Shade Related Stuff
		int ghostcolour1 = (23 << 16) + (79 << 8) + 12;
		int ghostcolour2 = (74 << 16) + (63 << 8) + 52;
		EntityRegistry.registerGlobalEntityID(EntityGhost.class, "shade", EntityRegistry.findGlobalUniqueEntityId(), ghostcolour1, ghostcolour2);
		EntityRegistry.registerModEntity(EntityGhost.class, "shade", Properties.shadeEntityID, this, 64, 1, true);
		EntityRegistry.addSpawn(EntityGhost.class, 20, 4, 4, EnumCreatureType.monster, BiomeGenBase.hell);
		
		EntityRegistry.registerModEntity(EntityLantern.class, "lantern", Properties.lanternEntityID, this, 64, 1, true);
		// EntityRegistry.registerGlobalEntityID(EntityBoneArrow.class,"Bone Arrow",
		// EntityRegistry.findGlobalUniqueEntityId());
		EntityRegistry.registerModEntity(EntityBoneArrow.class, "boneArrow", Properties.arrowBoneEntityID, this, 64, 1, true);
		
		// EntityRegistry.registerGlobalEntityID(EntityBlazeArrow.class,
		// "Blaze Arrow", EntityRegistry.findGlobalUniqueEntityId());
		EntityRegistry.registerModEntity(EntityBlazeArrow.class, "blazeArrow", Properties.arrowBlazeEntityID, this, 64, 1, true);
		
		EntityRegistry.registerModEntity(EntitySpellProjectile.class, "sorcerySpellProjectile", Properties.spellProjectileEntityID, this, 64, 1, true);

		// Modifying Vanilla Content
		Items.blaze_rod.setFull3D();
		Items.shears.setFull3D();
		
		arcaneblock.setHarvestLevel("pickaxe", 2, 0);
		arcaneblock.setHarvestLevel("pickaxe", 4, 1);
		arcaneblock.setHarvestLevel("pickaxe", 4, 2);
		arcaneblock.setHarvestLevel("pickaxe", 5, 3);
		arcaneblock.setHarvestLevel("pickaxe", 2, 4);
		arcaneblock.setHarvestLevel("pickaxe", 2, 5);
		arcaneblock.setHarvestLevel("pickaxe", 2, 6);
		arcaneblock.setHarvestLevel("pickaxe", 3, 7);
		arcaneblock.setHarvestLevel("pickaxe", 0, 8);
		arcaneblock.setHarvestLevel("pickaxe", 0, 9);
		arcaneblock.setHarvestLevel("pickaxe", 2, 10);
		arcaneblock.setHarvestLevel("pickaxe", 0, 11);
		arcaneblock.setHarvestLevel("pickaxe", 2, 12);
		
		cloud.setHarvestLevel("shovel", 0);
		runeBlock.setHarvestLevel("pickaxe", 1);
		slimeBlock.setHarvestLevel("shovel", 0);
		
		itemNode.setHarvestLevel("shovel", 0);
		
		arcanehalf.setHarvestLevel("pickaxe", 0);
		
		machine.setHarvestLevel("pickaxe", 2, 0);
		machine.setHarvestLevel("pickaxe", 2, 1);
		machine.setHarvestLevel("pickaxe", 2, 2);
		machine.setHarvestLevel("pickaxe", 3, 3);
		machine.setHarvestLevel("pickaxe", 3, 4);
		machine.setHarvestLevel("pickaxe", 2, 5);
		machine.setHarvestLevel("axe", 1, 13);
		machine.setHarvestLevel("pickaxe", 1, 14);
		machine.setHarvestLevel("pickaxe", 2, 15);
		
		// Tile Entities
		proxy.registerTileEntities();
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		packetPipeline.postInitialise();
		String log = "Successfully Initialized.";
		
		MixerRecipes.getInstance().addMixerRecipes();
		ForgeRecipes.getInstance().addForgeRecipes();
		TinkeringRecipes.getInstance().addTinkeringRecipes();
		InfuserRecipes.getInstance().addInfuserRecipes();
		Utils.log(Level.INFO, log);
	}
	
	private void registerBlocks() {
		arcaneblock = new BlockArcane().setHardness(3F).setBlockName("arcaneBlock");
		GameRegistry.registerBlock(arcaneblock, ItemArcaneBlock.class, "arcaneBlock", "sorcery");
		
		cloud = new BlockCloud().setStepSound(Block.soundTypeCloth).setBlockName("cloud");
		GameRegistry.registerBlock(cloud, "cloud");
		
		arcanehalf = new BlockArcaneHalf().setHardness(3F).setBlockName("arcaneHalf");
		GameRegistry.registerBlock(arcanehalf, ItemArcaneHalf.class, "sorceryHalfBlock");
		
		machine = new BlockMachine().setHardness(3F).setBlockName("machine");
		GameRegistry.registerBlock(machine, ItemMachineBlock.class, "sorceryMachine");
		
		runeBlock = new BlockRune().setBlockName("runeBlock");
		GameRegistry.registerBlock(runeBlock, ItemRuneBlock.class, "runeStoneBlock");
		
		airBlock = new BlockAir().setBlockName("airBlock");
		GameRegistry.registerBlock(airBlock, "airBlock");
		
		slimeBlock = new BlockSlime().setBlockName("slimeBlock").setLightOpacity(0).setStepSound(stepSoundSlime);
		GameRegistry.registerBlock(slimeBlock, ItemSlimeBlock.class, "slimeBlock", "sorcery");
		
		itemNode = new BlockNodeTransport().setBlockName("transportNode").setLightOpacity(0);
		GameRegistry.registerBlock(itemNode, ItemNodeTransport.class, "transportNode", "sorcery");
		
		magicLeaves = new BlockMagicLeaves().setHardness(0.2F).setLightOpacity(1).setLightLevel(0.5375F).setStepSound(Block.soundTypeGrass).setBlockName("magicLeaves");
		GameRegistry.registerBlock(magicLeaves, ItemMagicLeaves.class, "magicLeaves", "sorcery");
		
		magicSapling = new BlockMagicSapling().setHardness(0.0F).setLightOpacity(1).setLightLevel(0.5375F).setStepSound(Block.soundTypeGrass).setBlockName("magicSapling");
		GameRegistry.registerBlock(magicSapling, "magicSapling");
		
		metalBricks = new BlockMetalBricks().setStepSound(Block.soundTypeMetal).setBlockName("metalbricks");
		GameRegistry.registerBlock(metalBricks, ItemMetalBricks.class, "metalBricks", "sorcery");
		
		fluidPipe = new BlockPipe().setStepSound(Block.soundTypeMetal).setBlockName("fluidPipe");
		GameRegistry.registerBlock(fluidPipe, ItemPipe.class, "fluidPipe", "sorcery");
		
		mojoMachine = new BlockMojoMachine().setStepSound(Block.soundTypeMetal).setBlockName("mojoMachine");
		GameRegistry.registerBlock(mojoMachine, ItemBlockMojoMachine.class, "mojoMachine", "sorcery");
		
		mojoWire = new BlockMojoWire().setStepSound(Block.soundTypeStone).setBlockName("mojoWire");
		GameRegistry.registerBlock(mojoWire, ItemMojoWire.class, "mojoWire", "sorcery");
	}
	
	private void registerMetals() {
		MetalRegistry.addMetal(new MetalRegistry(new ItemStack(SorceryItems.ingotSilver), new ItemStack(Sorcery.arcaneblock, 1, 0), null, 40));
		MetalRegistry.addMetal(new MetalRegistry(new ItemStack(Items.iron_ingot), new ItemStack(Blocks.iron_ore), null, 50));
		MetalRegistry.addMetal(new MetalRegistry(new ItemStack(Items.gold_ingot), new ItemStack(Blocks.gold_ore), null, 60));
		MetalRegistry.addMetal(new MetalRegistry(new ItemStack(SorceryItems.ingotNetherrite), new ItemStack(Sorcery.arcaneblock, 1, 1), null, 70));
		MetalRegistry.addMetal(new MetalRegistry(new ItemStack(SorceryItems.ingotInfernite), new ItemStack(Sorcery.arcaneblock, 1, 3), null, 80));
	}
	
	private void registerAchievements() {
		blacksmith = new SorceryAchievement("blacksmith", 0, 2, new ItemStack(machine, 1, 2), AchievementList.buildFurnace).registerStat();
		Utils.addAchievementName("blacksmith", "Blacksmith", "Craft a Forge");
		
		inventor = new SorceryAchievement("inventor", 0, 0, new ItemStack(machine, 1, 0), blacksmith).registerStat();
		Utils.addAchievementName("inventor", "Inventor", "Craft a Tinkering Table");
		
		babbyFormed = new SorceryAchievement("babbyFormed", -1, -2, new ItemStack(arcanehalf, 1, 0), Sorcery.inventor).registerStat();
		Utils.addAchievementName("babbyFormed", "How is Babby Formed?", "Craft an Incubator");
		
		soShiny = new SorceryAchievement("soShiny", 2, 2, new ItemStack(SorceryItems.ingotSilver), AchievementList.acquireIron).registerStat();
		Utils.addAchievementName("soShiny", "It's so shiny!", "Smelt a Silver Ingot");
		
		redHotMetal = new SorceryAchievement( "redHotMetal", 2, 3, new ItemStack(SorceryItems.ingotInfernite), AchievementList.portal).registerStat();
		Utils.addAchievementName("redHotMetal", "Red Hot Metal", "Smelt an Infernite Ingot");
		
		green = new SorceryAchievement("green", 2, 4, new ItemStack(SorceryItems.ingotMagic), blacksmith).registerStat();
		Utils.addAchievementName("green", "Why is it green?", "Smelt a Magic Alloy Ingot");
		
		loveStory = new SorceryAchievement("loveStory", 1, -2, new ItemStack(SorceryItems.spellbook), inventor).registerStat();
		Utils.addAchievementName("loveStory", "Behold the Power of Reading!", "Craft a Spellbook");
		
		geneticEngineering = new SorceryAchievement("geneticEngineering", -3, -2, new ItemStack(SorceryItems.goldegg), babbyFormed).registerStat();
		Utils.addAchievementName("geneticEngineering", "Genetic Engineering", "Create and Incubate a Golden Egg");
		
		aChickenNoMore = new SorceryAchievement("aChickenNoMore", -5, -2, new ItemStack(SorceryItems.phoenixegg), geneticEngineering).registerStat();
		Utils.addAchievementName("aChickenNoMore", "A Chicken no more", "Turn a Golden Egg into a Phoenix Egg");
		
		meltingTopic = new SorceryAchievement("meltingTopic", -2, 0, new ItemStack(machine, 1, 4), inventor).registerStat();
		Utils.addAchievementName("meltingTopic", "Melting Topic", "Create a Hellfire Furnace");
		
		hellFire = new SorceryAchievement("hellFire", -4, 0, new ItemStack(machine, 1, 3), meltingTopic).setSpecial().registerStat();
		Utils.addAchievementName("hellFire", "Hellfire", "Create a Hellfire Forge");
		
		wizardHarry = new SorceryAchievement("wizardHarry", 1, -4, new ItemStack(SorceryItems.wand), inventor).setSpecial().registerStat();
		Utils.addAchievementName("wizardHarry", "You're a Wizard, Harry", "Craft a Wand");
		
		elementMaster = new SorceryAchievement("elementMaster", -1, -4, new ItemStack(machine, 1, 1), inventor).registerStat();
		Utils.addAchievementName("elementMaster", "Master of the Elements", "Craft a Runic Infuser");
		
		notForFood = new SorceryAchievement("notForFood", 3, 4, new ItemStack(SorceryItems.plateMagic), green).registerStat();
		Utils.addAchievementName("notForFood", "Not for eating off!", "Smelt a Magic Alloy Plate");
		
		shakeIt = new SorceryAchievement("shakeIt", 2, 0, new ItemStack(machine, 1, 5), inventor).registerStat();
		Utils.addAchievementName("shakeIt", "Shake It!", "Craft a Mixer");
		
		sorceryAchPage = new AchievementPage("Sorcery", blacksmith, inventor, babbyFormed, soShiny, redHotMetal, green, notForFood, loveStory, geneticEngineering, aChickenNoMore, meltingTopic, hellFire, wizardHarry, elementMaster, shakeIt);
		AchievementPage.registerAchievementPage(sorceryAchPage);
	}
	
	@Mod.EventHandler
	public void serverStarting(FMLServerStartingEvent event) {
		CommandHandler commandManager = (CommandHandler)event.getServer().getCommandManager();
		commandManager.registerCommand(new CommandSorcery());
		//commandManager.registerCommand(new CommandSpellCast());
		commandManager.registerCommand(new CommandMojo());
	}
	
	public String getVersion() {
		return Properties.VERSION;
	}
}