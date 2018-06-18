package sorcery.tileentities.nodes;

import net.minecraft.item.ItemStack;
import sorcery.lib.NodeTransportHelper.NodeGUIDetails;

public class TileEntityNodeVoid extends TileEntityNodeBase {
	public TileEntityNodeVoid() {
		this.bufferSize = 0;
		this.buffer = new ItemStack[this.getBufferSize()];
		this.colours = new ItemStack[2];
		this.filter = new ItemStack[0];
		this.isInput = true;
		this.isOutput = false;
		this.isAdvanced = true;
		this.useAdvancedModel = false;
		
		this.guiDetails = new NodeGUIDetails(0, 0, false, // BUFFER
				127, 68, false, // CODE
				0, 0, false, // TARGET
				0, 0, // REDSTONE CONTROL
				146, 7, // PRIORITY
				0, 100, // INVENTORY
				0, 0); // FILTER
	}
	
	@Override
	public String getInventoryName() {
		return "container.node.void";
	}
	
	@Override
	public void doRenderUpdate() {
		for(int i = 0; i < 32; ++i) {
			if(this.worldObj.rand.nextInt(50) == 25)
				this.worldObj.spawnParticle("portal", this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5, this.worldObj.rand.nextGaussian(), 0.0D, this.worldObj.rand.nextGaussian());
		}
	}
	
	@Override
	public void updateNode() {
		
	}
	
	@Override
	public void insertIntoBuffer(ItemStack item) {
		
	}
	
	@Override
	public boolean canAcceptInput(ItemStack item) {
		return true;
	}
}
