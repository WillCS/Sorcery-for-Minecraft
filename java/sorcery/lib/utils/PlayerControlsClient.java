package sorcery.lib.utils;

import sorcery.core.Sorcery;
import sorcery.network.PlayerControlsPacket;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.client.FMLClientHandler;

public class PlayerControlsClient extends PlayerControls {
	public boolean pastJumpState;
	
	public void updateKeys() {
		boolean isJumping = FMLClientHandler.instance().getClient().gameSettings.keyBindJump.getIsKeyPressed();
		
		if(!this.pastJumpState == isJumping) {
			this.pastJumpState = isJumping;
			NBTTagCompound tag = new NBTTagCompound();
			tag.setBoolean("isJumping", isJumping);
			PlayerControlsPacket packet = new PlayerControlsPacket(tag);
			Sorcery.packetPipeline.sendToServer(packet);
			super.writeKeyUpdate(isJumping, FMLClientHandler.instance().getClient().thePlayer);
		}
	}
}
