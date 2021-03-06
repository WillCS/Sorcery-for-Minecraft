package sorcery.spellcomponents.actions;

import sorcery.api.element.Element;
import sorcery.api.element.ElementStack;
import sorcery.api.spellcasting.SpellComponent;
import sorcery.api.spellcasting.SpellComponentBase;
import sorcery.api.spellcasting.SpellComponentBase.SpellAction;

public class SpellActionDetect extends SpellAction {
	
	@Override
	public int getMojoCost(SpellComponent component) {
		return 20;
	}
	
	@Override
	public ElementStack[] getElements(SpellComponent component) {
		return new ElementStack[] {new ElementStack(Element.energy, 1)};
	}
}
