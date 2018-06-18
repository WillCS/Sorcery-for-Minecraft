package sorcery.network;

import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.PacketBuffer;
import sorcery.api.SorceryAPI;
import sorcery.api.research.Research;
import sorcery.lib.SpellHelper;

public class PlayerResearchPacket extends AbstractPacket {
	private NBTTagCompound research;
	
	public PlayerResearchPacket() {
		
	}
	
	public PlayerResearchPacket(EntityPlayer player) {
		this.research = new NBTTagCompound();
		this.research.setTag("research", SorceryAPI.spellHelper.getPlayerSorceryCustomData(player).getTagList("research", 10));
	}
	
	public PlayerResearchPacket(Research research) {
		this.research = new NBTTagCompound();
		research.writeToNBT(this.research);
	}
	
	@Override
	public void encodeInto(ChannelHandlerContext ctx, PacketBuffer buffer) {
		try{
			buffer.writeNBTTagCompoundToBuffer(this.research);
		} catch(Exception e) {
			
		}
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, PacketBuffer buffer) {
		try{
			this.research = buffer.readNBTTagCompoundFromBuffer();
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
		NBTTagList list = this.research.getTagList("research", 10);
		SpellHelper.getInstance().getPlayerSorceryCustomData(player).setTag("research", list);
	}
}
