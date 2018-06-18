package sorcery.api;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import sorcery.api.recipe.IForgeRecipes;
import sorcery.api.recipe.IInfuserRecipes;
import sorcery.api.recipe.IMixerRecipes;
import sorcery.api.recipe.ITinkeringRecipes;
import sorcery.api.research.Research;
import sorcery.api.research.ResearchNode;
import sorcery.api.research.ResearchRecipe;
import sorcery.api.spellcasting.CustomSpell;
import sorcery.api.spellcasting.ISpellActionHandler;
import sorcery.api.spellcasting.ISpellAdjectiveHandler;
import sorcery.api.spellcasting.SpellComponentBase;

/** Provides access to the Forge, Tinkering Station, Mixer, and Runic Infuser.
 * There are a couple of helper methods here too.
 * 
 * @Author Vroominator */
public class SorceryAPI {
	/** Access to forge recipes */
	public static IForgeRecipes forgeRecipeManager;
	/** Access to tinkering recipes */
	public static ITinkeringRecipes tinkeringRecipeManager;
	/** Access to mixer recipes */
	public static IMixerRecipes mixerRecipeManager;
	/** Access to elemental infuser recipes */
	public static IInfuserRecipes infuserRecipeManager;
	
	/** Lots of useful helper methods */
	public static ISpellHelper spellHelper;
	
	/** A completed research object */
	public static Research research;
	
	/** The complete list of SpellComponentBases */
	public static ArrayList<SpellComponentBase> components = new ArrayList<SpellComponentBase>();
	
	/** The complete list of Custom Spells */
	public static ArrayList<CustomSpell> customSpells = new ArrayList<CustomSpell>();
	
	/** The complete list of Spell Action Handlers */
	public static ArrayList<ISpellActionHandler> spellActionHandlers = new ArrayList<ISpellActionHandler>();
	
	/** The complete list of Spell Adjective Handlers */
	public static ArrayList<ISpellAdjectiveHandler> spellAdjectiveHandlers = new ArrayList<ISpellAdjectiveHandler>();
	
	/** The three research recipe lists */
	public static ArrayList<ResearchRecipe> obtainResearchRecipes = new ArrayList<ResearchRecipe>();
	public static ArrayList<ResearchRecipe> fieldResearchRecipes = new ArrayList<ResearchRecipe>();
	public static ArrayList<ResearchRecipe> deskResearchRecipes = new ArrayList<ResearchRecipe>();
	
	public static final String TEXTURE_FOLDER = "sorceryapi";
	public static final String ASSET_PREFIX = TEXTURE_FOLDER + ":";
	public static final String SPELL_TEXTURE_FOLDER = "spell/";
	public static final String ELEMENT_TEXTURE_FOLDER = "element/";
	
	/** Instance of the Sorcery mod object */
	public static Object sorcery;
	
	/** Converts a colour from 4 floats (r, g, b, alpha) to the integer format
	 * Minecraft uses */
	public static int encodeColour(float r, float g, float b, float alpha) {
		return ((int)(alpha * 255) << 24) + ((int)(r * 255) << 16) + ((int)(g * 255) << 8) + ((int)(b * 255));
	}
	
	/** Converts a colour from 3 integers (r, g, b) to the integer format
	 * Minecraft uses */
	public static int encodeColour(int r, int g, int b) {
		return ((int)r << 16) + ((int)g << 8) + b;
	}
	
	/** Converts a colour from the integer format Minecraft uses to 4 floats (r,
	 * g, b, alpha) */
	public static float[] decodeColour(int colour) {
		float[] rgba = new float[4];
		rgba[3] = (float)((float)(colour >> 24 & 255) / 255F);
		rgba[0] = (float)((float)(colour >> 16 & 255) / 255F);
		rgba[1] = (float)((float)(colour >> 8 & 255) / 255F);
		rgba[2] = (float)((float)(colour & 255) / 255F);
		
		return rgba;
	}
	
	/** Register a new Spell Action Handler, for handling how pre-existing spell foci
	 *  react when combined with new spell actions */
	public static void registerSpellActionHandler(ISpellActionHandler handler) {
		spellActionHandlers.add(handler);
	}
	
	/** Register a new Spell Action Handler, for handling how pre-existing spell adjectives
	 *  react when combined with new spell words */
	public static void registerSpellActionHandler(ISpellAdjectiveHandler handler) {
		spellAdjectiveHandlers.add(handler);
	}
	
	/** Opens the spellbook GUI */
	public static void openSpellbookGUI(World world, EntityPlayer player) {
		if(!world.isRemote)
			player.openGui(SorceryAPI.sorcery, 6, player.worldObj, (int)player.posX, (int)player.posY, (int)player.posZ);
	}
	
	/** Checks to see if stackOne and stackTwo are the same Ore Dicionary entry */
	public static boolean areOreItemsEqual(ItemStack stackOne, ItemStack stackTwo) {
		if(stackOne == null || stackTwo == null)
			return false;
		int oreID1 = OreDictionary.getOreID(stackOne);
		int oreID2 = OreDictionary.getOreID(stackTwo);
		if(oreID1 == -1 || oreID2 == -1)
			return false;
		if(oreID1 == oreID2)
			return true;
		return false;
	}
	
	public static boolean areSameItem(ItemStack stackOne, ItemStack stackTwo) {
		if(stackOne == stackTwo)
			if(stackOne.getItemDamage() == stackTwo.getItemDamage())
				return true;
		
		return false;
	}
	
	public static boolean areSameItemNBT(ItemStack stackOne, ItemStack stackTwo) {
		if(stackOne == stackTwo)
			if(stackOne.getItemDamage() == stackTwo.getItemDamage())
				if(stackOne.getTagCompound().equals(stackTwo.getTagCompound()))
					return true;
		
		return false;
	}
	
	public static ResourceLocation getResource(String string) {
		return new ResourceLocation(TEXTURE_FOLDER + ":" + string);
	}
	
	public static ResearchNode getResearchByName(String researchName) {
		for(int i = 0; i < research.nodes.size(); i++) {
			if(research.nodes.get(i).title.equals(researchName)) 
				return research.nodes.get(i);
		}
		return null;
	}
	
	public static boolean canPlayerUnlockResearch(EntityPlayer player, String researchName) {
		ResearchNode node = getResearchByName(researchName);
		if(!spellHelper.hasPlayerUnlockedNode(player, node)) {
			Research research = Research.readFromNBT(player);
			if(research.nodes.containsAll(node.parents)) {
				return true;
			}
		}
		
		return false;
	}
	
	/** Unlocks the research node named <code>researchName</code> in all the
	 *  research journals the player is carrying that are eligible to receive it. */
	public static boolean unlockResearch(EntityPlayer player, String researchName) {
		boolean actuallyUnlocked = false;
		Research research = Research.readFromNBT(player);
		if(canPlayerUnlockResearch(player, researchName)) {
			research.nodes.add(getResearchByName(researchName));
			actuallyUnlocked = true;
		}
		
		research.writeToNBT(spellHelper.getPlayerSorceryCustomData(player));
		
		return actuallyUnlocked;
	}
}
