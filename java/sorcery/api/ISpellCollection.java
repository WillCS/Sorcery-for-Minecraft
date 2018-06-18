package sorcery.api;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import sorcery.api.spellcasting.Spell;

/** @author Vroominator */
public interface ISpellCollection {
	public Spell[] getSpells(ItemStack item);
	public Spell[] removeSpells(ItemStack item);
	
	public Spell removeSpell(ItemStack item, sorcery.api.spellcasting.Spell spell);
	
	public boolean hasSpells(ItemStack item);
	public boolean setSpells(ItemStack item, sorcery.api.spellcasting.Spell[] spells);
	public boolean addSpell(ItemStack item, sorcery.api.spellcasting.Spell spell);
	public boolean hasSpell(ItemStack item, sorcery.api.spellcasting.Spell spell);
}
