package sorcery.spellcomponents.foci.adj;

import sorcery.api.element.Element;
import sorcery.api.element.ElementStack;
import sorcery.api.spellcasting.ISpellEntity;
import sorcery.api.spellcasting.Spell;
import sorcery.api.spellcasting.SpellComponent;
import sorcery.api.spellcasting.SpellComponentBase.SpellAdjective;

public class SpellAdjectiveSpeed extends SpellAdjective {
	float speed = 0.0F;
	
	public SpellAdjectiveSpeed(float speed) {
		this.speed = speed;
	}
	
	@Override
	public ElementStack[] getElements(SpellComponent component) {
		return new ElementStack[] {new ElementStack(Element.energy, 10)};
	}
	
	@Override
	public int getMojoCost(SpellComponent component) {
		return 10;
	}
	
	@Override
	public void modifySpellEntity(ISpellEntity entity, Spell spell) {
		float speed = entity.getSpellSpeed();
		speed *= (1.F + this.speed);
		speed += ((float)(spell.focus.magnitude) * 0.1F);
		entity.setSpellSpeed(speed);
	}
}
