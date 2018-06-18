package sorcery.lib.utils;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.entity.player.EntityPlayer;

public class PlayerControls {
	private Map<EntityPlayer, Boolean> jumpState = new HashMap<EntityPlayer, Boolean>();
	
	public void updateKeys() {
	}
	
	public boolean isJumpPressed(EntityPlayer player) {
		if(this.jumpState.containsKey(player)) {
			return this.jumpState.get(player).booleanValue();
		}
		
		return false;
	}
	
	public void writeKeyUpdate(boolean state, EntityPlayer player) {
		this.jumpState.remove(player);
		this.jumpState.put(player, state);
	}
}
