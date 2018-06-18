package sorcery.lib;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import sorcery.api.element.Element;
import sorcery.api.element.ElementStack;
import sorcery.api.spellcasting.Spell;
import sorcery.enchantments.EnchantmentAuraBoost;
import sorcery.enchantments.EnchantmentConsumeLess;
import sorcery.enchantments.EnchantmentFrost;
import sorcery.enchantments.EnchantmentInsight;
import sorcery.enchantments.EnchantmentSpeedCasting;
import sorcery.lib.utils.Utils;
import sorcery.potions.PotionFrostbite;

public class SorcerySpells {
	public static void registerEnchantments() {
		coolDown = new EnchantmentSpeedCasting(Properties.coolDownEnchantmentID).setName("speedCasting");
		Enchantment.addToBookList(coolDown);
		
		consumeLess = new EnchantmentConsumeLess(Properties.consumeLessEnchantmentID).setName("consumeLess");
		Enchantment.addToBookList(consumeLess);
		
		auraBoost = new EnchantmentAuraBoost(Properties.auraBoostEnchantmentID).setName("auraBoost");
		Enchantment.addToBookList(auraBoost);
		
		insight = new EnchantmentInsight(Properties.insightEnchantmentID).setName("insight");
		Enchantment.addToBookList(insight);
		
		frost = new EnchantmentFrost(Properties.frostEnchantmentID).setName("frost");
		Enchantment.addToBookList(frost);
	}
	
	public static void registerPotions() {
		frostbite = new PotionFrostbite(Utils.getNextAvailablePotionID(), true, (42 << 16) + (190 << 8) + 210);
	}
	
	public static void registerElements() {
		Element.energy = new Element("energy", 1, 14548767);
		Element.stone = new Element("stone", 2, 10045196);
		Element.water = new Element("water", 3, 1417446);
		Element.wind = new Element("wind", 4, 10921638);
		Element.frost = new Element("frost", 5, 2883583);
		Element.life = new Element("life", 6, 3268388);
		Element.fire = new Element("fire", 7, 15763748);
		Element.voodoo = new Element("voodoo", 8, 10688982);
		Element.ender = new Element("void", 9, 0);
		Element.arcane = new Element("arcane", 10, 15896556);
	}
	
	public static Spell disarm;
	public static Spell lightning;
	
	public static Spell harvest;
	public static Spell collect;
	
	public static Spell enderpearl;
	public static Spell cloudBridge;

	public static Spell excavate;
	
	public static Spell enderChest;
	
	public static Enchantment coolDown;
	public static Enchantment consumeLess;
	public static Enchantment auraBoost;
	public static Enchantment insight;
	public static Enchantment frost;
	
	public static Potion frostbite;
}
