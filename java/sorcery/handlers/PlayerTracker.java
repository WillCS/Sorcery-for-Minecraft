package sorcery.handlers;

import java.util.HashMap;

import net.minecraft.entity.player.EntityPlayer;
import sorcery.lib.SpellHelper;
import sorcery.mojo.PlayerMojoInfo;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;

public class PlayerTracker {

	@SubscribeEvent
	public void onPlayerLogin(PlayerLoggedInEvent event) {
		EntityPlayer player = event.player;
		PlayerMojoInfo info = new PlayerMojoInfo();
		info.mojo = SpellHelper.getInstance().getPlayerMojo(player);
		info.maxMojo = SpellHelper.getInstance().getPlayerMaxMojoWithoutRestrictedSectors(player);
		info.burnout = SpellHelper.getInstance().getPlayerBurnOutTimer(player);
		info.canRegen = SpellHelper.getInstance().getPlayerCanRegenMojo(player);
		info.restricted = SpellHelper.getInstance().getPlayerRestrictedMojoSectors(player);
		info.spells = SpellHelper.getInstance().getPlayerSpells(player);
		SpellHelper.getInstance().sendAdvancedMojoPacketToPlayer(player, info);
		SpellHelper.getInstance().sendPlayerResearchPacketToPlayer(player);
	}

	@SubscribeEvent
	public void onPlayerLogout(PlayerLoggedOutEvent event) {
		EntityPlayer player = event.player;
	}

	@SubscribeEvent
	public void onPlayerChangeDimension(PlayerChangedDimensionEvent event) {
		EntityPlayer player = event.player;
		PlayerMojoInfo info = new PlayerMojoInfo();
		info.mojo = SpellHelper.getInstance().getPlayerMojo(player);
		info.maxMojo = SpellHelper.getInstance().getPlayerMaxMojoWithoutRestrictedSectors(player);
		info.burnout = SpellHelper.getInstance().getPlayerBurnOutTimer(player);
		info.canRegen = SpellHelper.getInstance().getPlayerCanRegenMojo(player);
		info.restricted = SpellHelper.getInstance().getPlayerRestrictedMojoSectors(player);
		info.spells = SpellHelper.getInstance().getPlayerSpells(player);
		SpellHelper.getInstance().sendAdvancedMojoPacketToPlayer(player, info);
		SpellHelper.getInstance().sendPlayerResearchPacketToPlayer(player);
	}

	@SubscribeEvent
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		EntityPlayer player = event.player;
		PlayerMojoInfo info = new PlayerMojoInfo();
		SpellHelper.getInstance().setPlayerMojo(player, SpellHelper.getInstance().getPlayerMaxMojoWithoutRestrictedSectors(player));
		SpellHelper.getInstance().setPlayerRestrictedMojoSectors(player, new HashMap<String, Integer>());
		SpellHelper.getInstance().setPlayerBurnOutTimer(player, 0);
		info.mojo = SpellHelper.getInstance().getPlayerMaxMojoWithoutRestrictedSectors(player);
		info.maxMojo = SpellHelper.getInstance().getPlayerMaxMojoWithoutRestrictedSectors(player);
		info.burnout = 0;
		info.canRegen = SpellHelper.getInstance().getPlayerCanRegenMojo(player);
		info.restricted = new HashMap<String, Integer>();
		info.spells = SpellHelper.getInstance().getPlayerSpells(player);
		SpellHelper.getInstance().sendAdvancedMojoPacketToPlayer(player, info);
		SpellHelper.getInstance().sendPlayerResearchPacketToPlayer(player);
	}

}
