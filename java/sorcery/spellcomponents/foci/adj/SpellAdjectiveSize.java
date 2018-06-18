package sorcery.spellcomponents.foci.adj;

import sorcery.api.element.Element;
import sorcery.api.element.ElementStack;
import sorcery.api.spellcasting.ISpellEntity;
import sorcery.api.spellcasting.Spell;
import sorcery.api.spellcasting.SpellComponent;
import sorcery.api.spellcasting.SpellComponentBase.SpellAdjective;

public class SpellAdjectiveSize extends SpellAdjective {
	float size = 0.0F;
	
	public SpellAdjectiveSize(float size) {
		this.size = size;
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
		float size = entity.getSpellSpeed();
		size *= (1.F + this.size);
		size += ((float)(spell.focus.magnitude) * 0.1F);
		entity.setSpellSize(size);
	}
}
