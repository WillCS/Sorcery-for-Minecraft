package sorcery.api.spellcasting;

import sorcery.api.spellcasting.SpellComponentBase.SpellAction;
import sorcery.api.spellcasting.SpellComponentBase.SpellFocus;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**Handler for extra spell actions<br>
 * Use this to define how pre-existing foci should react to new actions
 * @author Vroominator
 */
public interface ISpellActionHandler {
	public void handleCast(Spell spell, World world, EntityLivingBase entity, Wand wand);
	
	public boolean isActionCompatible(SpellFocus focus, Spell spell, String action);
	
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(Spell spell);
}
