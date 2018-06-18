package sorcery.spellcomponents.foci.adj;

import sorcery.api.element.Element;
import sorcery.api.element.ElementStack;
import sorcery.api.spellcasting.ISpellEntity;
import sorcery.api.spellcasting.Spell;
import sorcery.api.spellcasting.SpellComponent;
import sorcery.api.spellcasting.SpellComponentBase;
import sorcery.api.spellcasting.SpellComponentBase.SpellAdjective;

public class SpellAdjectiveElement extends SpellAdjective {
	public final Element element;
	
	public SpellAdjectiveElement(Element element) {
		this.element = element;
	}
	
	@Override
	public ElementStack[] getElements(SpellComponent component) {
		return new ElementStack[] {new ElementStack(this.element, 2 * component.magnitude)};
	}
	
	@Override
	public int getMojoCost(SpellComponent component) {
		return 5;
	}
	
	@Override
	public void modifySpellEntity(ISpellEntity entity, Spell spell) {
		
	}

	@Override
	public boolean isComponentCompatible(SpellComponentBase component) {
		return !(component instanceof SpellAdjectiveElement);
	}
}
