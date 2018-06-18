package sorcery.core;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import sorcery.api.spellcasting.Spell;

public class DamageSourceSpell extends EntityDamageSource {
	public Entity caster;
	public Entity victim;
	public Spell spell;
	
	public DamageSourceSpell(String name, Entity caster, Entity victim, Spell spell) {
		super(name, caster);
		this.caster = caster;
		this.victim = victim;
		this.spell = spell;
		this.setDamageBypassesArmor();
		this.setMagicDamage();
	}
	
	public static DamageSource causeSpellDamage(Entity caster, Entity victim, Spell spell) {
		return new DamageSourceSpell("spell", caster, victim, spell);
	}
	
	public String getDeathMessage(EntityLiving par1EntityLiving) {
		return "poop";
	}
}
