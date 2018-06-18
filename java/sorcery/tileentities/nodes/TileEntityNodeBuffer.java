package sorcery.tileentities.nodes;

import net.minecraft.item.ItemStack;
import sorcery.lib.NodeTransportHelper.NodeGUIDetails;

public class TileEntityNodeBuffer extends TileEntityNodeBase {
	public TileEntityNodeBuffer() {
		this.bufferSize = 9;
		this.buffer = new ItemStack[this.bufferSize];
		this.colours = new ItemStack[2];
		this.filter = new ItemStack[0];
		this.isInput = true;
		this.isOutput = true;
		this.isAdvanced = false;
		this.useAdvancedModel = true;
		
		this.guiDetails = new NodeGUIDetails(0, 30, false, // BUFFER
				127, 38, false, // CODE
				127, 68, false, // TARGET
				0, 0, // REDSTONE CONTROL
				146, 7, // PRIORITY
				0, 100, // INVENTORY
				0, 0); // FILTER
	}
	
	@Override
	public String getInventoryName() {
		return "container.node.buffer";
	}
	
	@Override
	public void updateNode() {
		
	}
}
