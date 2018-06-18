package sorcery.network;

import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.PacketBuffer;
import sorcery.api.spellcasting.Spell;
import sorcery.lib.SpellHelper;
import sorcery.mojo.PlayerMojoInfo;

public class PlayerMojoInfoPacket extends AbstractPacket {
	private PlayerMojoInfo info;
	private boolean isBasic = false;
	
	public PlayerMojoInfoPacket() {
		
	}
	
	public PlayerMojoInfoPacket(EntityPlayer player) {
		this.info = new PlayerMojoInfo();
		this.info.burnout = SpellHelper.getInstance().getPlayerBurnOutTimer(player);
		this.info.canRegen = SpellHelper.getInstance().getPlayerCanRegenMojo(player);
		this.info.maxMojo = SpellHelper.getInstance().getPlayerMaxMojo(player);
		this.info.mojo = SpellHelper.getInstance().getPlayerMojo(player);
		this.info.restricted = SpellHelper.getInstance().getPlayerRestrictedMojoSectors(player);
		this.info.spells = SpellHelper.getInstance().getPlayerSpells(player);
	}
	
	public PlayerMojoInfoPacket(PlayerMojoInfo info) {
		this.info = info;
	}
	
	public void setBasic() {
		this.isBasic = true;
	}
	
	@Override
	public void encodeInto(ChannelHandlerContext ctx, PacketBuffer buffer) {
		try{
			buffer.writeBoolean(this.isBasic);
			buffer.writeNBTTagCompoundToBuffer(this.info.writeToNBT());
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, PacketBuffer buffer) {
		try{
			this.isBasic = buffer.readBoolean();
			this.info = PlayerMojoInfo.readFromNBT(buffer.readNBTTagCompoundFromBuffer());
		} catch(Exception e) {
		}
	}

	@Override
	public void handleClientSide(EntityPlayer player) {
		this.handlePacket(true, player);
	}

	@Override
	public void handleServerSide(EntityPlayer player) {
		//this.handlePacket(false, player);
	}
	
	public void handlePacket(boolean isClient, EntityPlayer player) {
		SpellHelper.getInstance().setPlayerMojo(player, this.info.mojo);
		SpellHelper.getInstance().setPlayerMaxMojo(player, this.info.maxMojo);
		SpellHelper.getInstance().setPlayerBurnOutTimer(player, this.info.burnout);
		SpellHelper.getInstance().setPlayerCanRegenMojo(player, this.info.canRegen);
		
		if(!this.isBasic) {
			SpellHelper.getInstance().setPlayerRestrictedMojoSectors(player, this.info.restricted);
			SpellHelper.getInstance().setPlayerSpells(player, this.info.spells);
		}
	}
}
