package sorcery.api.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;
import sorcery.api.spellcasting.Spell;
import cpw.mods.fml.common.eventhandler.Cancelable;

/** This gets fired off before a player starts casting a spell and can be
 * cancelled. This event is used to cancel teleport spells if there is a player
 * with a Ring of the Void nearby.
 * 
 * @author Vroominator */
@Cancelable
public class SpellCastEvent extends PlayerEvent {
	public ItemStack wand;
	public Spell spell;
	
	public SpellCastEvent(EntityPlayer player, ItemStack wand, Spell spell) {
		super(player);
		this.wand = wand;
		this.spell = spell;
	}
}
