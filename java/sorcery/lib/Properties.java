package sorcery.lib;

import java.util.HashMap;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.FluidContainerRegistry;
import sorcery.core.Sorcery;
import sorcery.lib.utils.Utils;

public class Properties {
	public static final String VERSION = "Beta 1.1";
	public static final String MOD = "Sorcery";
	public static final String MODNAME = "sorcery";
	public static final String CHANNEL = "Sorcery";
	public static final String ASSET_PREFIX = MODNAME + ":";
	public static final Sorcery instance = Sorcery.instance;
	
	public static int BUCKET_VOLUME = FluidContainerRegistry.BUCKET_VOLUME;
	
	public static final String TEXTURE_FOLDER = "textures/";
	public static final String GUI_TEXTURE_FOLDER = TEXTURE_FOLDER + "guis/";
	public static final String MOB_TEXTURE_FOLDER = TEXTURE_FOLDER + "mobs/";
	public static final String ARMOUR_TEXTURE_FOLDER = TEXTURE_FOLDER + "armor/";
	public static final String ENTITY_TEXTURE_FOLDER = TEXTURE_FOLDER + "entities/";
	public static final String MISC_TEXTURE_FOLDER = TEXTURE_FOLDER + "misc/";
	public static final String SPELL_TEXTURE_FOLDER = "spells";
	
	public static final String ITEM_TEXTURE_FILE = TEXTURE_FOLDER + "items.png";
	public static final String BLOCK_TEXTURE_FILE = TEXTURE_FOLDER + "Block.png";
	public static final String SPELL_TEXTURE_FILE = TEXTURE_FOLDER + "spells.png";
	public static final String MISC_TEXTURE_FILE = TEXTURE_FOLDER + "misc.png";
	public static final String POTION_TEXTURE_FILE = TEXTURE_FOLDER + "potions.png";
	public static final String HUD_TEXTURE_FILE = GUI_TEXTURE_FOLDER + "hud.png";
	
	public static final ResourceLocation terrainTexture = Sorcery.proxy.getTerrainTexture();
	public static final ResourceLocation itemTexture = Sorcery.proxy.getItemTexture();
	public static final ResourceLocation miscTexture = Utils.getResource(MISC_TEXTURE_FILE);
	public static final ResourceLocation potionTexture = Utils.getResource(POTION_TEXTURE_FILE);
	public static final ResourceLocation hudTexture = Utils.getResource(HUD_TEXTURE_FILE);
	
	public static final String SPELL_BLACKLIST_CATEGORY = "Spell Blacklist";
	public static final String ITEM_ID_CATEGORY = "Item IDs";
	public static final String BLOCK_ID_CATEGORY = "Block IDs";
	public static final String GENERAL_CATEGORY = "General";
	public static final String ENCHANTMENT_CATEGORY = "Enchantment IDs";
	
	public static final String mL = /*"mB";*/ StatCollector.translateToLocal("sorcery.measurements.mililitre");
	public static final String L = /*"B";*/ StatCollector.translateToLocal("sorcery.measurements.litre");
	public static final String DEGREES = /*"\u00B0" + "M";*/ StatCollector.translateToLocal("sorcery.measurements.degree");
	
	public static final String[] ELEMENTS = new String[]{"Blank", "Potential", "Rock", "Water", "Wind", "Frost", "Life", "Fire", "Voodoo", "Void", "Arcane"};
	public static final String[] COLOURS = new String[]{"black", "red", "green", "brown", "blue", "purple", "cyan", "lightGray", "gray", "pink", "lime", "yellow", "lightBlue", "magenta", "orange", "white"};
	public static final String[] COG_TYPES = new String[]{"iron", "golden", "netherrite", "infernite", "wooden", "brass", "steel"};
	public static final String[] FLUIDS = new String[]{"mojo", "milk", "chocmilk", "antidote", "glowstone"};
	
	public static HashMap<String, HashMap<String, String>> spellNames = new HashMap<String, HashMap<String, String>>();
	
	public static final int GUI_TINKERING = 0;
	public static final int GUI_WANDCRAFTING = 1;
	public static final int GUI_HELLFURNACE = 2;
	public static final int GUI_FORGE = 3;
	public static final int GUI_RUNECRAFTING = 4;
	public static final int GUI_IMPRINTER = 5;
	public static final int GUI_SPELLBOOK = 6;
	public static final int GUI_MIXER = 7;
	public static final int GUI_BARREL = 8;
	public static final int GUI_NODE = 9;
	public static final int GUI_MOJOSTORAGE = 10;
	public static final int GUI_RESEARCH = 11;
	public static final int GUI_DESK = 12;
	public static final int GUI_REPAIR = 13;
	
	public static final int nodeRenderID = Sorcery.proxy.getRenderID();
	public static final int pipeRenderID = Sorcery.proxy.getRenderID();
	public static final int wireRenderID = Sorcery.proxy.getRenderID();
	
	public static final int unicornEntityID = 0;
	public static final int phoenixEntityID = 1;
	public static final int chickenGoldEntityID = 2;
	public static final int shadeEntityID = 3;
	public static final int unicowEntityID = 4;
	
	public static final int arrowBoneEntityID = 10;
	public static final int arrowBlazeEntityID = 11;
	public static final int nodeItemEntityID = 15;
	public static final int lanternEntityID = 16;
	
	public static final int spellProjectileEntityID = 20;
	
	public static final byte KEY_STATE_UPDATE_PACKET = 1;
	public static final byte ITEM_NODE_PACKET = 2;
	public static final byte TILE_ENTITY_PACKET = 3;
	public static final byte SPELL_PACKET = 4;
	
	public static boolean[] spellBlackList = new boolean[50000];
	
	public static boolean oreGenSilver;
	public static boolean oreGenNetherrite;
	public static boolean oreGenOnyx;
	public static boolean oreGenInfernite;
	public static boolean oreGenTin;
	public static boolean oreGenCopper;
	
	public static boolean biomeGenEnchantedForest;
	public static boolean enableSteelRecipe;
	
	public static int coolDownEnchantmentID;
	public static int consumeLessEnchantmentID;
	public static int auraBoostEnchantmentID;
	public static int insightEnchantmentID;
	public static int frostEnchantmentID;
	
	public static int villagerWizardID;
	public static int biomeEnchantedForestID;
	
	public static int fluidWaterID;
	public static int fluidLavaID;

	public static int enumWireIds = 0;
	public static int enumPipeIds = 0;
}
