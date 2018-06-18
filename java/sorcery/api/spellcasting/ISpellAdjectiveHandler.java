package sorcery.api.spellcasting;

import sorcery.api.spellcasting.SpellComponentBase.CastType;
import sorcery.api.spellcasting.SpellComponentBase.SpellAdjective;

/**Handler for new spell words<br>
 * Use this to define how pre-existing adjectives should react to new words
 * @author Vroominator
 */
public interface ISpellAdjectiveHandler {
	public boolean isAdjectiveCompatible(SpellAdjective adj1, Spell spell, String adj2);
	
	public boolean isCastTypeCompatible(SpellAdjective adj, Spell spell, CastType castType);
}
