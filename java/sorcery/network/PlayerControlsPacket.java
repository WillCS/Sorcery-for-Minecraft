package sorcery.network;

import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import sorcery.core.Sorcery;

public class PlayerControlsPacket extends AbstractPacket {
	private NBTTagCompound tag;
	
	public PlayerControlsPacket() {
		
	}
	
	public PlayerControlsPacket(NBTTagCompound tag) {
		this.tag = tag;
	}
	
	@Override
	public void encodeInto(ChannelHandlerContext ctx, PacketBuffer buffer) {
		try{
			buffer.writeNBTTagCompoundToBuffer(this.tag);
		} catch(Exception e) {
			
		}
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, PacketBuffer buffer) {
		try{
			this.tag = buffer.readNBTTagCompoundFromBuffer();
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
		boolean isJumping = this.tag.getBoolean("isJumping");
		Sorcery.playerControls.writeKeyUpdate(isJumping, (EntityPlayer)player);
	}
}
