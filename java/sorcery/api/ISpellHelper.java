package sorcery.api;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import sorcery.api.element.Element;
import sorcery.api.element.ElementStack;
import sorcery.api.research.ResearchNode;
import sorcery.api.spellcasting.Spell;
import sorcery.api.spellcasting.SpellComponent;
import sorcery.api.spellcasting.SpellComponentBase;
import sorcery.api.spellcasting.WandComponent;

/** Lots of helper methods for working with spells, elements, elementstacks,
 * spellbooks, etc.
 * 
 * @author Vroominator */
public interface ISpellHelper {
	
	/** Check if <code>player</code> has the elemental energy for
	 * <code>spell</code>. */
	boolean hasEnergy(EntityPlayer player, Spell spell);
	
	/** Consume everything needed to cast <code>spell</code> from
	 * <code>player</code>'s inventory. */
	void consumeElements(EntityPlayer player, Spell spell, ItemStack wand);
	
	/** Consume <code>element</code> from <code>inv</code>.
	 * 
	 * @return the amount that could not be consumed, if any. */
	int consumeElement(ItemStack[] inv, ElementStack element);
	
	/** Find the amount of <code>element</code> in <code>player</code>'s
	 * inventory */
	int getElementAmountInPlayerInv(EntityPlayer player, Element element);

	/** Gets <code>player</code>'s custom nbt data for sorcery */
	NBTTagCompound getPlayerSorceryCustomData(EntityPlayer player);
	
	/** Gets <code>player</code>'s research tag list */
	NBTTagList getPlayerResearchTag(EntityPlayer player);
	
	/** Returns whether or not <code>player</code> has unlocked <code>node</code> */
	boolean hasPlayerUnlockedNode(EntityPlayer player, ResearchNode node);
	
	/** Checks to see if <code>player</code> has enough Mojo to cast
	 * <code>spell</code>. */
	boolean doesPlayerHaveMojoForSpell(EntityPlayer player, Spell spell);
	
	/** Returns <code>player</code>'s current Mojo */
	int getPlayerMojo(EntityPlayer player);
	
	/** Sets <code>player</code>'s current Mojo. */
	void setPlayerMojo(EntityPlayer player, int mojo);
	
	/** Consumes <code>mojo</code> amount of <code>player</code>'s Mojo */
	void consumePlayerMojo(EntityPlayer player, int mojo);
	
	/** Sets <code>player</code>'s maximum amount of Mojo to <code>mojo</code> */
	void setPlayerMaxMojo(EntityPlayer player, int mojo);
	
	/** Returns <code>player</code>'s maximum Mojo */
	int getPlayerMaxMojo(EntityPlayer player);
	
	/** Returns <code>player</code>'s maximum Mojo, not counting restricted sectors */
	int getPlayerMaxMojoWithoutRestrictedSectors(EntityPlayer player);
	
	/** Sets whether or not <code>player</code> can regenerate Mojo */
	void setPlayerCanRegenMojo(EntityPlayer player, boolean regen);
	
	/** Returns whether or not <code>player</code> can regenerate Mojo */
	boolean getPlayerCanRegenMojo(EntityPlayer player);
	
	/** Returns whether or not <code>player</code> is able to use Mojo */
	boolean getPlayerCanUseMojo(EntityPlayer player);
	
	/** Returns a list of restriced sectors of <code>player</code>'s Mojo */
	HashMap<String, Integer> getPlayerRestrictedMojoSectors(EntityPlayer player);

	/** Sets the list of restriced sectors of <code>player</code>'s Mojo */
	void setPlayerRestrictedMojoSectors(EntityPlayer player, HashMap<String, Integer> sectors);
	
	/** Adds a restriced Mojo sector of size <code>sector</code> to <code>player</code> */
	void addPlayerRestrictedMojoSector(EntityPlayer player, String name, int sector);
	
	/** Removes a restriced Mojo sector named <code>name</code> from <code>player</code> */
	void removePlayerRestrictedMojoSector(EntityPlayer player, String name);
	
	/** Changes a restriced Mojo sector in <code>player</code> named <code>name</code> to new size <code>sector</code> */
	void changePlayerRestrictedMojoSector(EntityPlayer player, String name, int sector);
	
	/** Gets the total amount of restricted Mojo for <code>player</code> */
	int getPlayerTotalRestrictedMojo(EntityPlayer player);
	
	/** Returns whether or not <code>player</code> has access to Dark Mojo */
	boolean getPlayerHasDarkMojo(EntityPlayer player);
	
	/** Returns <code>player</code>'s total Dark Mojo */
	int getPlayerDarkMojo(EntityPlayer player);
	
	/** Sets how much Dark Mojo <code>player</code> has */
	void setPlayerDarkMojo(EntityPlayer player, int mp);
	
	/** Consumes <code>mojo</code> amount of <code>player</code>'s Dark Mojo */
	void consumePlayerDarkMojo(EntityPlayer player, int mojo);
	
	/** Sets <code>player</code>'s maximum Dark Mojo capacity to</code>mojo</code> */
	void setPlayerMaxDarkMojo(EntityPlayer player, int mojo);
	
	/** Returns <code>player</code>'s maximum Dark Mojo capacity */
	int getPlayerMaxDarkMojo(EntityPlayer player);
	
	/** Returns all the currently equipped spells of <code>player</code> */
	Spell[] getPlayerSpells(EntityPlayer player);
	
	/** Returns <code>player</code>'s currently equipped spell */
	Spell getPlayerEquippedSpell(EntityPlayer player);
	
	/** Sets all the currently equipped spells of <code>player</code> to <code>spells</code> */
	void setPlayerSpells(EntityPlayer player, Spell[] spells);
	
	/** Sets the currently equipped spell at <code>spellIndex</code> of <code>player</code>'s spells to <code>spell</code> */
	void setPlayerSpell(EntityPlayer player, Spell spell, int spellIndex);
	
	/** Returns whether or not <code>player</code> is burnt out */
	boolean getIsPlayerBurntOut(EntityPlayer player);
	
	/** Returns how burnt out <code>player</code> is */
	int getPlayerBurnOutTimer(EntityPlayer player);
	
	/** Adds <code>amount</code> to <code>player</code>'s burnout timer */
	void addToPlayerBurnOutTimer(EntityPlayer player, int amount);
	
	/** Adds <code>player</code>'s burnout timer to <code>amount</code> */
	void setPlayerBurnOutTimer(EntityPlayer player, int amount);
	
	/**
	ItemStack getPageFromSpellIDs(int... ids);
	 TODO fix SpellHelper page methods
	int[] getSpellIDsFromPage(ItemStack page);

	void setPageName(ItemStack page, String name);

	ItemStack getSpellbookFromSpellIDs(int... ids);

	int[] getSpellIDsFromBook(ItemStack book); 
	
	void addPageToBook(ItemStack page, ItemStack book); */
	
	/** Gets the Hashmap of registered Wand Components */
	HashMap<String, WandComponent> getWandComponentsMap();
	
	/** Gets the registered Wand Component with the name <code>name</code> */
	WandComponent getWandComponentByName(String name);
	
	/** Returns all the spell components the owner of <code>journal</code> has unlocked */
	ArrayList<SpellComponent> getSpellComponentsFromResearch(ItemStack journal, World world);
}