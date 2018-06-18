package sorcery.api.research;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;

/** Simple class for keeping track of information about a block
 * 
 * @author Vroominator */
public class BlockInfo {
	public int xPos;
	public int yPos;
	public int zPos;
	
	public Block block;
	public int metadata;
	
	public TileEntity tile;
	
	public void setPosition(int x, int y, int z) {
		this.xPos = x;
		this.yPos = y;
		this.zPos = z;
	}
	
	public void setBlockAndMeta(Block block, int meta) {
		this.block = block;
		this.metadata = meta;
	}
	
	public void setBlock(Block block) {
		this.block = block;
		this.metadata = 0;
	}
	
	public void setMeta(int meta) {
		this.metadata = meta;
	}
	
	public void setTileEntity(TileEntity tile) {
		this.tile = tile;
	}
}
