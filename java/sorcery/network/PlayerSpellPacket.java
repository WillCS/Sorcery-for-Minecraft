package sorcery.network;

import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fluids.FluidStack;
import sorcery.api.spellcasting.Spell;
import sorcery.lib.SpellHelper;
import sorcery.tileentities.TileEntitySorcery;

public class PlayerSpellPacket extends AbstractPacket {
	private Spell[] spells;
	private int number = -1;
	private boolean toggle;
	
	public PlayerSpellPacket() {
		
	}
	
	public PlayerSpellPacket(Spell[] spells) {
		this.spells = spells;
	}
	
	public PlayerSpellPacket(Spell spell, int number) {
		this.spells = new Spell[] {spell};
		this.number = number;
	}
	
	public PlayerSpellPacket(boolean toggle) {
		this.toggle = true;
	}
	
	@Override
	public void encodeInto(ChannelHandlerContext ctx, PacketBuffer buffer) {
		try{
			NBTTagCompound tag = new NBTTagCompound();
			if(this.toggle) {
				tag.setBoolean("toggle", this.toggle);
			} else {
				NBTTagList list = new NBTTagList();
				for(int i = 0; i < this.spells.length; i++) {
					list.appendTag(this.spells[i].writeToNBT());
				}
				tag.setTag("spells", list);
				tag.setInteger("slot", this.number);
			}
			buffer.writeNBTTagCompoundToBuffer(tag);
		} catch(Exception e) {
			
		}
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, PacketBuffer buffer) {
		try{
			NBTTagCompound tag = buffer.readNBTTagCompoundFromBuffer();
			if(tag.getBoolean("toggle")) {
				this.toggle = true;
			} else {
				NBTTagList list = tag.getTagList("spells", 10);
				this.spells = new Spell[list.tagCount()];
				for(int i = 0; i < list.tagCount(); i++) {
					this.spells[i] = Spell.readFromNBT(list.getCompoundTagAt(i));
				}
				this.number = tag.getInteger("slot");
			}
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
		if(this.toggle)
			SpellHelper.getInstance().toggleWrenchMode(player);
		else if(this.number == -1)
			SpellHelper.getInstance().setPlayerSpells(player, this.spells);
		else
			SpellHelper.getInstance().setPlayerSpell(player, this.spells[0], this.number);
	}
}
