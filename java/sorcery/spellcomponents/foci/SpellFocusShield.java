package sorcery.spellcomponents.foci;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import sorcery.api.element.Element;
import sorcery.api.element.ElementStack;
import sorcery.api.spellcasting.Spell;
import sorcery.api.spellcasting.SpellCasterInfo;
import sorcery.api.spellcasting.SpellComponent;
import sorcery.api.spellcasting.SpellComponentBase.SpellFocus;
import sorcery.api.spellcasting.Wand;
import sorcery.entities.spells.EntitySpellProjectile;

public class SpellFocusShield extends SpellFocus {
	
	@Override
	public int getMojoCost(SpellComponent component) {
		int ret = 20 * component.magnitude;
		return ret + super.getMojoCost(component);
	}
	
	@Override
	public ElementStack[] getElements(SpellComponent component) {
		ElementStack[] ret = new ElementStack[] {
				new ElementStack(Element.energy, 1 * component.magnitude),
				new ElementStack(Element.arcane, 1 * component.magnitude)};
		
		return ElementStack.mergeLists(ret, super.getElements(component));
	}
	
	@Override
	public void cast(Spell spell, World world, EntityLivingBase entity, Wand wand) {
		//super.cast(spell, world, entity, wand);
		EntitySpellProjectile projectile = new EntitySpellProjectile(world, entity, spell);
		projectile.magnitude = spell.focus.magnitude;
		projectile.spell = spell;
		projectile.casterInfo = new SpellCasterInfo();
		projectile.casterInfo.casterName = entity.getCommandSenderName();
		projectile.casterInfo.isPlayer = entity instanceof EntityPlayer;
		projectile.casterInfo.x = (float)entity.posX;
		projectile.casterInfo.x = (float)entity.posY;
		projectile.casterInfo.x = (float)entity.posZ;
		
		for(SpellAdjective adj : spell.focus.adjectives) {
			adj.modifySpellEntity(projectile, spell);
		}
			
		if(!world.isRemote) {	
			world.spawnEntityInWorld(projectile);
		}
	}
}
