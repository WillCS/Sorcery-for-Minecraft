package sorcery.handlers;

import org.apache.logging.log4j.Level;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import sorcery.api.event.MojoRegenEvent;
import sorcery.lib.SpellHelper;
import sorcery.lib.utils.Utils;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class MojoTickHandler {

	@SubscribeEvent
	public void OnPlayerTick(PlayerTickEvent event) {
		this.handleMojoTick(event.player);
	}
	
	public void handleMojoTick(EntityPlayer player) {
		int mojo = SpellHelper.instance.getPlayerMojo(player);
		int maxMojo = SpellHelper.instance.getPlayerMaxMojo(player);
		
		if(maxMojo == 0) {
			maxMojo = 200;
			mojo = 200;
			SpellHelper.instance.setPlayerMaxMojo(player, 200);
			SpellHelper.instance.setPlayerMojo(player, 200);
		}
		
		if(mojo <= 0 && !SpellHelper.getInstance().getIsPlayerBurntOut(player)) {
			SpellHelper.getInstance().setPlayerBurnOutTimer(player, 100);
			SpellHelper.getInstance().setPlayerMojo(player, 1);
			return;
		}
		
		if(mojo < maxMojo) {
			MojoRegenEvent event = new MojoRegenEvent(player, 1);
			MinecraftForge.EVENT_BUS.post(event);
			
			if(!event.isCanceled() && SpellHelper.instance.getPlayerCanRegenMojo(player)) {
				SpellHelper.instance.setPlayerMojo(player, mojo + event.regenAmount);
			}
		}
		
		if(SpellHelper.getInstance().getIsPlayerBurntOut(player))
			SpellHelper.getInstance().addToPlayerBurnOutTimer(player, -1);
	}
}
