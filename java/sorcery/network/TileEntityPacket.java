package sorcery.network;

import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;

import sorcery.tileentities.TileEntitySorcery;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fluids.FluidStack;

public class TileEntityPacket extends AbstractPacket {
	
	public static final int DEFAULT_ID 	= 0;
	public static final int FLUID_ID 	= 1;
	public static final int PLAYER_ID	= 2;
	public static final int UPDATE_ID	= 3;
	public static final int REQUEST_ID	= 4;
	
	private int x, y, z;
	private NBTTagCompound data;
	private int specialID;
	
	public TileEntityPacket() {
		
	}
	
	public TileEntityPacket(NBTTagCompound data, int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.data = data;
		this.specialID = 0;
	}
	
	public void setSpecialID(int id) {
		this.specialID = id;
	}
	
	@Override
	public void encodeInto(ChannelHandlerContext ctx, PacketBuffer buffer) {
		try{
			buffer.writeInt(this.specialID);
			buffer.writeInt(this.x);
			buffer.writeInt(this.y);
			buffer.writeInt(this.z);
			buffer.writeNBTTagCompoundToBuffer(this.data);
		} catch(Exception e) {
			
		}
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, PacketBuffer buffer) {
		try{
			this.specialID = buffer.readInt();
			this.x = buffer.readInt();
			this.y = buffer.readInt();
			this.z = buffer.readInt();
			this.data = buffer.readNBTTagCompoundFromBuffer();
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
		TileEntitySorcery tile = (TileEntitySorcery)player.worldObj.getTileEntity(this.x, this.y, this.z);
		
		if(this.specialID == DEFAULT_ID) {
			tile.handlePacketData(this.data, isClient);
		}
		
		if(this.specialID == FLUID_ID) {
			int id = this.data.getInteger("tank");
			FluidStack fluid = FluidStack.loadFluidStackFromNBT(this.data.getCompoundTag("fluid"));
			tile.handleFluidPacket(fluid, id, isClient);
		}
		
		if(this.specialID == PLAYER_ID) {
			tile.handlePlayerPacket(player, data, isClient);
		}
		
		if(this.specialID == UPDATE_ID) {
			tile.handleUpdatePacket(player, this.data, isClient);
		}
		
		if(this.specialID == REQUEST_ID) {
			tile.sendUpdatePacket(player);
		}
	}
}
