package sorcery.network;

import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import sorcery.api.SorceryAPI;

public class UnlockResearchPacket extends AbstractPacket {
	private String researchName;
	
	public UnlockResearchPacket() {
		
	}
	
	public UnlockResearchPacket(String researchName) {
		this.researchName = researchName;
	}
	
	@Override
	public void encodeInto(ChannelHandlerContext ctx, PacketBuffer buffer) {
		try{
			buffer.writeStringToBuffer(this.researchName);
		} catch(Exception e) {
			
		}
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, PacketBuffer buffer) {
		try{
			this.researchName = buffer.readStringFromBuffer(99999);
		} catch(Exception e) {
			
		}
	}

	@Override
	public void handleClientSide(EntityPlayer player) {
		this.handlePacket(true, player);
	}

	@Override
	public void handleServerSide(EntityPlayer player) {
		this.handlePacket(false, player);
	}
	
	public void handlePacket(boolean isClient, EntityPlayer player) {
		SorceryAPI.unlockResearch(player, this.researchName);
	}
}
