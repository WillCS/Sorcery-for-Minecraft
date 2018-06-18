package sorcery.api.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import cpw.mods.fml.common.eventhandler.Cancelable;

/** Called every player tick to check if a player should regenerate Mojo.<br> 
 *  Cancel the event if they should not regenerate or change the regenAmount 
 *  if they should regenerate more or less than usual.
 * @author Vroominator */
@Cancelable
public class MojoRegenEvent extends PlayerEvent {
	public int regenAmount;
	
	public MojoRegenEvent(EntityPlayer player, int currentRegenAmount) {
		super(player);
		this.regenAmount = currentRegenAmount;
	}
}
