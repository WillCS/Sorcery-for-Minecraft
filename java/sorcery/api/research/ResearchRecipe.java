package sorcery.api.research;

import cpw.mods.fml.common.registry.LanguageRegistry;

/** Basic recipe system for unlocking new research.<br>
 * 	These should be generated automatically by the research loader
 * @author Vroominator */
public class ResearchRecipe {
	
	/** The object that must be examined to unlock this recipe<br>
	 *  Can be: BlockInfo (only id and metadata matter), Entity, ItemStack */
	public final Object researchObject;
	
	/** The title of the Research Node to be unlocked via this recipe */
	public final String researchTitle;
	
	public ResearchRecipe(Object researchObject, String researchTitle) {
		this.researchObject = researchObject;
		this.researchTitle = researchTitle;
	}
}
