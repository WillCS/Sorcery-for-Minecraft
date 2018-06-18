package sorcery.tileentities.nodes;

import net.minecraft.item.ItemStack;
import sorcery.lib.NodeTransportHelper.NodeGUIDetails;

public class TileEntityNodeExtractor extends TileEntityNodeBase {
	public TileEntityNodeExtractor() {
		this.bufferSize = 0;
		this.buffer = new ItemStack[this.getBufferSize()];
		this.colours = new ItemStack[2];
		this.filter = new ItemStack[9];
		this.isInput = false;
		this.isOutput = true;
		this.isAdvanced = false;
		this.useAdvancedModel = false;
		
		this.guiDetails = new NodeGUIDetails(0, 0, false, // BUFFER
				0, 0, false, // CODE
				127, 68, false, // TARGET
				0, 0, // REDSTONE CONTROL
				146, 7, // PRIORITY
				0, 100, // INVENTORY
				0, 30); // FILTER
	}
	
	@Override
	public String getInventoryName() {
		return "container.node.extractor";
	}
	
	@Override
	public void updateNode() {
		
	}
}
