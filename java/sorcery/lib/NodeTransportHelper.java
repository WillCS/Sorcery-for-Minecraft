package sorcery.lib;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import sorcery.lib.utils.Utils;

public class NodeTransportHelper {
	public static ResourceLocation[] textures = new ResourceLocation[]{Utils.getResource(Properties.ENTITY_TEXTURE_FOLDER + "nodeOutput.png"), Utils.getResource(Properties.ENTITY_TEXTURE_FOLDER + "nodeIO.png"), Utils.getResource(Properties.ENTITY_TEXTURE_FOLDER + "nodeInput.png"), Utils.getResource(Properties.ENTITY_TEXTURE_FOLDER + "nodeCollector.png"), Utils.getResource(Properties.ENTITY_TEXTURE_FOLDER + "nodeInput.png"), Utils.getResource(Properties.ENTITY_TEXTURE_FOLDER + "nodeBuffer.png")};
	
	public static ResourceLocation[] colouredTextures = new ResourceLocation[]{Utils.getResource(Properties.ENTITY_TEXTURE_FOLDER + "nodeOutput.png"), Utils.getResource(Properties.ENTITY_TEXTURE_FOLDER + "nodeIOColoured.png"), Utils.getResource(Properties.ENTITY_TEXTURE_FOLDER + "nodeInput.png"), Utils.getResource(Properties.ENTITY_TEXTURE_FOLDER + "nodeCollector.png"), Utils.getResource(Properties.ENTITY_TEXTURE_FOLDER + "nodeInput.png"), Utils.getResource(Properties.ENTITY_TEXTURE_FOLDER + "nodeBuffer.png")};
	
	public static boolean isDestinationReachable(Pos3D destination, Pos3D currentPos, World world) {
		if(destination.xCoord() != currentPos.xCoord()) {
			if(destination.xCoord() - currentPos.xCoord() > 0) {
				for(int i = 0; i < destination.xCoord() - currentPos.xCoord(); i++) {
					if(!world.isAirBlock(currentPos.xCoord() + i, currentPos.yCoord(), currentPos.zCoord())) {
						return false;
					}
				}
			} else {
				for(int i = 0; i < -(destination.xCoord() - currentPos.xCoord()); i++) {
					if(!world.isAirBlock(currentPos.xCoord() - i, currentPos.yCoord(), currentPos.zCoord())) {
						return false;
					}
				}
			}
		}
		
		if(destination.yCoord() != currentPos.yCoord()) {
			if(destination.yCoord() - currentPos.yCoord() > 0) {
				for(int i = 0; i < destination.yCoord() - currentPos.yCoord(); i++) {
					if(!world.isAirBlock(currentPos.xCoord(), currentPos.yCoord() + i, currentPos.zCoord())) {
						return false;
					}
				}
			} else {
				for(int i = 0; i < -(destination.yCoord() - currentPos.yCoord()); i++) {
					if(!world.isAirBlock(currentPos.xCoord(), currentPos.yCoord() - i, currentPos.zCoord())) {
						return false;
					}
				}
			}
		}
		
		if(destination.zCoord() != currentPos.zCoord()) {
			if(destination.zCoord() - currentPos.zCoord() > 0) {
				for(int i = 0; i < destination.zCoord() - currentPos.zCoord(); i++) {
					if(!world.isAirBlock(currentPos.xCoord(), currentPos.yCoord(), currentPos.zCoord() + i)) {
						return false;
					}
				}
			} else {
				for(int i = 0; i < -(destination.zCoord() - currentPos.zCoord()); i++) {
					if(!world.isAirBlock(currentPos.xCoord(), currentPos.yCoord(), currentPos.zCoord() - i)) {
						return false;
					}
				}
			}
		}
		
		return true;
	}
	
	public static ResourceLocation getTextureMapFromMeta(int meta) {
		return textures[meta];
	}
	
	public static ResourceLocation getColouredTextureMapFromMeta(int meta) {
		return colouredTextures[meta];
	}
	
	public static boolean isAdvancedNode(int meta) {
		switch(meta) {
			case 0:
			case 1:
			case 2:
			case 4:
				return false;
			default:
				return true;
		}
	}
	
	public static class NodeRequestDetails {
		private ItemStack item;
		
		private Pos3D[] directions;
		
		public NodeRequestDetails(ItemStack item, Pos3D[] directions) {
			this.item = item;
			this.directions = directions;
		}
		
		public NodeRequestDetails(NBTTagCompound tag) {
			this.readFromNBT(tag);
		}
		
		public ItemStack getItem() {
			return this.item;
		}
		
		public Pos3D[] getDirections() {
			return this.directions;
		}
		
		public Pos3D getDestination() {
			return this.directions[this.directions.length];
		}
		
		public void writeToNBT(NBTTagCompound tag) {
			NBTTagCompound items = new NBTTagCompound();
			this.item.writeToNBT(items);
			
			NBTTagList directions = new NBTTagList();
			for(int i = 0; i < this.directions.length; i++) {
				NBTTagCompound tags = new NBTTagCompound();
				this.directions[i].writeToNBT(tags, i + "");
				directions.appendTag(tags);
			}
			tag.setTag("item", items);
			tag.setTag("directions", directions);
		}
		
		public void readFromNBT(NBTTagCompound tag) {
			this.item = ItemStack.loadItemStackFromNBT((NBTTagCompound)tag.getTag("item"));
			
			this.directions = new Pos3D[tag.getTagList("directions", 10).tagCount()];
			
			for(int i = 0; i < tag.getTagList("directions", 10).tagCount(); i++) {
				NBTTagCompound tags = (NBTTagCompound)tag.getTagList("directions", 10).getCompoundTagAt(i);
				this.directions[i] = new Pos3D(tags.getIntArray(i + ""));
			}
		}
	}
	
	public static class NodeGUIDetails {
		public final int bufferX;
		public final int bufferY;
		public final boolean bufferVertical;
		
		public final int codeX;
		public final int codeY;
		public final boolean codeVertical;
		
		public final int targetX;
		public final int targetY;
		public final boolean targetVertical;
		
		public final int redControlX;
		public final int redControlY;
		public boolean redControlVertical = false;
		
		public final int priorityX;
		public final int priorityY;
		
		public final int inventoryX;
		public final int inventoryY;
		
		public final int filterX;
		public final int filterY;
		
		public NodeGUIDetails(int bufferX, int bufferY, boolean bufferVertical, int codeX, int codeY, boolean codeVertical, int targetX, int targetY, boolean targetVertical, int redControlX, int redControlY, int priorityX, int priorityY, int inventoryX, int inventoryY, int filterX, int filterY) {
			this.bufferX = bufferX;
			this.bufferY = bufferY;
			this.bufferVertical = bufferVertical;
			
			this.codeX = codeX;
			this.codeY = codeY;
			this.codeVertical = codeVertical;
			
			this.targetX = targetX;
			this.targetY = targetY;
			this.targetVertical = targetVertical;
			
			this.redControlX = redControlX;
			this.redControlY = redControlY;
			
			this.priorityX = priorityX;
			this.priorityY = priorityY;
			
			this.inventoryX = inventoryX;
			this.inventoryY = inventoryY;
			
			this.filterX = filterX;
			this.filterY = filterY;
		}
		
		public void setRedControlVertical(boolean b) {
			this.redControlVertical = b;
		}
	}
}
