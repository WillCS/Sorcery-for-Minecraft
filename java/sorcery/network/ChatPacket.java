package sorcery.network;

import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ChatComponentText;

public class ChatPacket extends AbstractPacket {
	private String text;
	
	public ChatPacket() {
		
	}
	
	public ChatPacket(String text) {
		this.text = text;
	}
	
	@Override
	public void encodeInto(ChannelHandlerContext ctx, PacketBuffer buffer) {
		try{
			buffer.writeStringToBuffer(this.text);
		} catch(Exception e) {
			
		}
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, PacketBuffer buffer) {
		try{
			this.text = buffer.readStringFromBuffer(99999);
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
		player.addChatMessage(new ChatComponentText(this.text));
	}
}
