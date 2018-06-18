package sorcery.tileentities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import sorcery.api.IWrenchable;
import sorcery.core.Sorcery;
import sorcery.network.TileEntityPacket;

public class TileEntitySorcery extends TileEntity implements IWrenchable {
	public int front;
	
	public void sendNBTPacket(NBTTagCompound tag) {
		TileEntityPacket packet = new TileEntityPacket(tag, this.xCoord, this.yCoord, this.zCoord);
		if(this.worldObj.isRemote)
			Sorcery.packetPipeline.sendToServer(packet);
		else
			Sorcery.packetPipeline.sendToAll(packet);
	}
	
	public void sendNBTPacket(NBTTagCompound tag, int specialID) {
		TileEntityPacket packet = new TileEntityPacket(tag, this.xCoord, this.yCoord, this.zCoord);
		packet.setSpecialID(specialID);
		if(this.worldObj.isRemote)
			Sorcery.packetPipeline.sendToServer(packet);
		else
			Sorcery.packetPipeline.sendToAll(packet);
	}
	
	public void sendUpdatePacket(EntityPlayer player) {
		NBTTagCompound tag = new NBTTagCompound();
		this.writeToNBT(tag);
		TileEntityPacket pack = new TileEntityPacket(tag, this.xCoord, this.yCoord, this.zCoord);
		pack.setSpecialID(TileEntityPacket.UPDATE_ID);
		Sorcery.packetPipeline.sendTo(pack, (EntityPlayerMP)player);
	}
	
	public void handlePacketData(NBTTagCompound tag, boolean isClient) {

	}
	
	public void handleFluidPacket(FluidStack stack, int tankID, boolean isClient) {
		
	}

	public void handlePlayerPacket(EntityPlayer player, NBTTagCompound data, boolean isClient) {
		
	}
	
	public void handleUpdatePacket(EntityPlayer player, NBTTagCompound data, boolean isClient) {
		this.readFromNBT(data);
	}
	
	public int getSizeInventory() {
		return 0;
	}
	
	// public void sendPacketToServer
	
	/** Creates a custom packet */
	
	public ItemStack getStackInSlot(int var8) {
		return null;
	}
	
	public void openChest() {
	}

	public void closeChest() {
	}
	
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return true;
	}
	
	public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer) {
		return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : par1EntityPlayer.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
	}
	
	@Override
	public void updateEntity() {
		if(this.worldObj.isAirBlock(this.xCoord, this.yCoord, this.zCoord)) {
			this.worldObj.removeTileEntity(this.xCoord, this.yCoord, this.zCoord);
			this.updateContainingBlockInfo();
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readFromNBT(par1NBTTagCompound);
		this.front = par1NBTTagCompound.getInteger("front");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setInteger("front", this.front);
	}
	
	public void sendFluidPacket(int tankID, FluidStack stack) {
		NBTTagCompound tag = new NBTTagCompound();
		NBTTagCompound fluidTag = new NBTTagCompound();
		stack.writeToNBT(fluidTag);
		tag.setTag("fluid", fluidTag);
		tag.setInteger("tank", tankID);
		this.sendNBTPacket(tag, TileEntityPacket.FLUID_ID);
	}
	
	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound tag = new NBTTagCompound();
		this.writeToNBT(tag);
		return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, tag);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		this.readFromNBT(pkt.func_148857_g());
	}

	public boolean hasCustomInventoryName() {
		return false;
	}

	public void openInventory() {
	}

	public void closeInventory() {
	}

	public int getInventoryStackLimit() {
		return 64;
	}
	
	public FluidTank[] getTanks() {
		return null;
	}
	
	public int fill(int tankIndex, FluidStack resource, boolean doFill) {
		if(this.getTanks()[tankIndex].getFluid() == null || this.getTanks()[tankIndex].getFluid().amount != this.getTanks()[tankIndex].getCapacity()) {
			if(this.getTanks()[tankIndex].getCapacity() == FluidContainerRegistry.BUCKET_VOLUME && resource.isFluidEqual(new FluidStack(FluidRegistry.getFluid("lava"), 0)))
				return 0;
			
			this.getTanks()[tankIndex].fill(resource, true);
			int fluidID = getTanks()[tankIndex].getFluid().fluidID;
			int amount = this.getTanks()[tankIndex].getFluid() == null ? 0 : this.getTanks()[tankIndex].getFluid().amount;
			if(!worldObj.isRemote)
				this.sendFluidPacket(tankIndex, new FluidStack(fluidID, amount));
			
			return resource.amount;
		}
		return 0;
	}
	
	public FluidStack drain(int tankIndex, int maxDrain, boolean doDrain) {
		if(this.getTanks()[tankIndex].getFluid() == null)
			return null;
		
		if(this.getTanks()[tankIndex].getFluid().amount > 0) {
			int fluidID = this.getTanks()[tankIndex].getFluid().fluidID;
			this.getTanks()[tankIndex].drain(maxDrain, true);
			int amount = this.getTanks()[tankIndex].getFluid() == null ? 0 : this.getTanks()[tankIndex].getFluid().amount;
			if(!worldObj.isRemote)
				this.sendFluidPacket(tankIndex, new FluidStack(fluidID, amount));
			
			return new FluidStack(fluidID, maxDrain);
		}
		return null;
	}
	
	public int getTankAmount(int tank) {
		return this.getTanks()[tank].getFluidAmount();
	}
	
	public int getTankCapacity(int tank) {
		return this.getTanks()[tank].getCapacity();
	}

	@Override
	public boolean onWrenched(World world, EntityPlayer player, ItemStack item, int x, int y, int z, int side) {
		return false;
	}
}