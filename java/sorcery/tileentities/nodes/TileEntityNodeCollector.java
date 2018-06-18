package sorcery.tileentities.nodes;

import java.util.ArrayList;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import sorcery.entities.EntityNodeItem;
import sorcery.lib.ItemStackHelper;
import sorcery.lib.Pos3D;
import sorcery.lib.NodeTransportHelper.NodeGUIDetails;

public class TileEntityNodeCollector extends TileEntityNodeBase {
	public TileEntityNodeCollector() {
		this.bufferSize = 3;
		this.buffer = new ItemStack[this.getBufferSize()];
		this.colours = new ItemStack[2];
		this.filter = new ItemStack[9];
		this.isInput = false;
		this.isOutput = true;
		this.isAdvanced = true;
		this.useAdvancedModel = true;
		
		this.guiDetails = new NodeGUIDetails(110, 30, false, // BUFFER
				0, 0, false, // CODE
				127, 68, false, // TARGET
				0, 0, // REDSTONE CONTROL
				146, 7, // PRIORITY
				0, 100, // INVENTORY
				0, 30); // FILTER
	}
	
	@Override
	public String getInventoryName() {
		return "container.node.collector";
	}
	
	@Override
	public void updateNode() {
		TileEntityNodeBase node = this.getNodeToSendTo();
		AxisAlignedBB box = AxisAlignedBB.getBoundingBox(this.xCoord - 4, this.yCoord - 4, this.zCoord - 4, this.xCoord + 4, this.yCoord + 4, this.zCoord + 4);
		ArrayList<EntityItem> items = (ArrayList<EntityItem>)this.worldObj.getEntitiesWithinAABB(EntityItem.class, box);
		
		for(int i = 0; i < items.size(); i++) {
			if(this.isItemInFilter(items.get(i).getEntityItem()))
				ItemStackHelper.instance.tryToFillInvWithItem(this.getBuffer(), items.get(i).getEntityItem());
		}
		
		if(this.isSending && this.nextNodeToSendTo.isNode(this.worldObj))
			node = (TileEntityNodeBase)this.nextNodeToSendTo.getTileEntityAtPos(this.worldObj);
		
		if(node != null && this.isNodeAccessible(node) && node.isInput && this.canSendTo(node)) {
			if(this.isSending && this.getBufferItem(this.slotToSendFrom) != null && this.isItemInFilter(this.getBufferItem(this.slotToSendFrom))) {
				ItemStack item = this.getBufferItem(this.slotToSendFrom);
				EntityNodeItem entity = new EntityNodeItem(this.worldObj);
				entity.setItem(item);
				this.decrStackSize(this.slotToSendFrom, this.getBufferItem(this.slotToSendFrom).stackSize);
				
				Pos3D dirToNode = this.getDirectionToNode(node);
				entity.setPosition(this.xCoord + 0.5 + dirToNode.xCoord(), this.yCoord + 0.2 + dirToNode.yCoord(), this.zCoord + 0.5 + dirToNode.zCoord());
				entity.setVelocity(dirToNode.xCoord(), dirToNode.yCoord(), dirToNode.zCoord());
				entity.setDirections(node.xCoord, node.yCoord, node.zCoord);
				entity.setOrigin(this);
				entity.setColourCode(this.colourTarget);
				
				this.sentItems.add(entity);
				this.isSending = false;
				this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
			}
			
			node = this.getNodeToSendTo();
			
			for(int i = 0; i < this.getBufferSize(); i++) {
				if(this.getBufferItem(i) != null && this.isItemInFilter(this.getBufferItem(this.slotToSendFrom)) && node.canAcceptInput(this.getBufferItem(i))) {
					this.beginSendingTo(node, i);
					this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
					
					break;
				}
			}
		}
	}
}
