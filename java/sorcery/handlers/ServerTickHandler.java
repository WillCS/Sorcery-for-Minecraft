package sorcery.handlers;

import java.util.EnumSet;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import sorcery.core.Sorcery;
import sorcery.entities.EntityPhoenix;
import sorcery.lib.SorceryItems;
import sorcery.lib.SpellHelper;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class ServerTickHandler {
	
	@SubscribeEvent
	public void onPlayerTick(PlayerTickEvent event) {
		EntityPlayer player = event.player;
		
		if(player.riddenByEntity != null && player.riddenByEntity instanceof EntityPhoenix && !((EntityPhoenix)player.riddenByEntity).isChild()) {
			player.fallDistance = 0F;
			if(!player.onGround && !player.capabilities.isFlying && !player.isOnLadder() && player.motionY < 0 && !player.isInWater()) {
				player.motionY = -0.1D;
			}
		}
	}
}
