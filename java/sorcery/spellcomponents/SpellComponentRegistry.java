package sorcery.spellcomponents;

import sorcery.api.element.Element;
import sorcery.api.spellcasting.SpellComponentBase;
import sorcery.spellcomponents.actions.SpellActionConjure;
import sorcery.spellcomponents.foci.SpellFocusBeam;
import sorcery.spellcomponents.foci.SpellFocusProjectile;
import sorcery.spellcomponents.foci.adj.SpellAdjectiveElement;
import sorcery.spellcomponents.foci.adj.SpellAdjectiveSize;

public class SpellComponentRegistry {
	
	public static void registerComponents() {
		SpellComponentBase.registerComponent(new SpellActionConjure(), "action_conjure",  SpellComponentBase.ComponentType.action);
		
		SpellComponentBase.registerComponent(new SpellFocusProjectile(), "focus_projectile",  SpellComponentBase.ComponentType.focus);
		SpellComponentBase.registerComponent(new SpellFocusBeam(), "focus_beam",  SpellComponentBase.ComponentType.focus);
		
		for(Element e : Element.elementsList) {
			if(e != null) {
				SpellComponentBase.registerComponent(new SpellAdjectiveElement(e),
					"focus_adj_" + e.getName(),  SpellComponentBase.ComponentType.focusAdj);
			}
		}
		
		SpellComponentBase.registerComponent(new SpellAdjectiveSize(0.5F), "focus_adj_fast",  SpellComponentBase.ComponentType.focusAdj);
	}
}
