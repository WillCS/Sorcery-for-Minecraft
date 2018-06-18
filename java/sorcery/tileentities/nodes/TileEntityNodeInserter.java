package sorcery.tileentities.nodes;

import net.minecraft.item.ItemStack;
import sorcery.lib.NodeTransportHelper.NodeGUIDetails;

public class TileEntityNodeInserter extends TileEntityNodeBase {
	public TileEntityNodeInserter() {
		this.bufferSize = 3;
		this.buffer = new ItemStack[this.getBufferSize()];
		this.colours = new ItemStack[2];
		this.filter = new ItemStack[9];
		this.isInput = true;
		this.isOutput = false;
		this.isAdvanced = false;
		this.useAdvancedModel = false;
		
		this.guiDetails = new NodeGUIDetails(0, 30, false, // BUFFER
				127, 68, false, // CODE
				0, 0, false, // TARGET
				0, 0, // REDSTONE CONTROL
				146, 7, // PRIORITY
				0, 100, // INVENTORY
				0, 30); // FILTER
	}
	
	@Override
	public String getInventoryName() {
		return "container.node.inserter";
	}
	
	@Override
	public void updateNode() {
		
	}
}
