package sorcery.blocks;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import sorcery.core.Sorcery;
import sorcery.lib.Pos3D;
import sorcery.lib.Properties;
import sorcery.tileentities.nodes.TileEntityNode;
import sorcery.tileentities.nodes.TileEntityNodeBase;
import sorcery.tileentities.nodes.TileEntityNodeBuffer;
import sorcery.tileentities.nodes.TileEntityNodeCollector;
import sorcery.tileentities.nodes.TileEntityNodeExtractor;
import sorcery.tileentities.nodes.TileEntityNodeInserter;
import sorcery.tileentities.nodes.TileEntityNodeVoid;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockNodeTransport extends Block {
	public BlockNodeTransport() {
		super(Material.circuits);
		setBlockBounds(0.33F, 0.33F, 0.33F, 0.66F, 0.66F, 0.66F);
		this.setCreativeTab(Sorcery.tabSorceryTech);
	}
	
	public IIcon getIcon(int side, int meta) {
		return Blocks.planks.getIcon(0, 0);
	}
	
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister par1IIconRegister) {
	}
	
	public int damageDropped(int i) {
		return i;
	}
	
	public void addCreativeItems(ArrayList itemList) {
		itemList.remove(this);
		for(int x = 0; x < 6; x++) {
			itemList.add(new ItemStack(this, 1, x));
		}
	}
	
	public void getSubBlocks(Block par1, CreativeTabs par2CreativeTabs, List par3List) {
		for(int var4 = 0; var4 < 6; ++var4) {
			par3List.add(new ItemStack(par1, 1, var4));
		}
	}
	
	public float getBlockHardness(World par1World, int par2, int par3, int par4) {
		return 0.3F;
	}
	
	public boolean canPlaceBlockOnSide(World par1World, int par2, int par3, int par4, int par5) {
		ForgeDirection dir = ForgeDirection.getOrientation(par5);
		return (dir == ForgeDirection.DOWN && this.isSuitable(par1World, par2, par3 + 1, par4, ForgeDirection.DOWN)) || (dir == ForgeDirection.UP && this.isSuitable(par1World, par2, par3 - 1, par4, ForgeDirection.UP)) || (dir == ForgeDirection.NORTH && this.isSuitable(par1World, par2, par3, par4 + 1, ForgeDirection.NORTH)) || (dir == ForgeDirection.SOUTH && this.isSuitable(par1World, par2, par3, par4 - 1, ForgeDirection.SOUTH)) || (dir == ForgeDirection.WEST && this.isSuitable(par1World, par2 + 1, par3, par4, ForgeDirection.WEST)) || (dir == ForgeDirection.EAST && this.isSuitable(par1World, par2 - 1, par3, par4, ForgeDirection.EAST));
	}
	
	/** Checks to see if its valid to put this block at the specified
	 * coordinates. Args: world, x, y, z */
	public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4) {
		return this.isSuitable(par1World, par2 - 1, par3, par4, ForgeDirection.EAST) || this.isSuitable(par1World, par2 + 1, par3, par4, ForgeDirection.WEST) || this.isSuitable(par1World, par2, par3, par4 - 1, ForgeDirection.SOUTH) || this.isSuitable(par1World, par2, par3, par4 + 1, ForgeDirection.NORTH) || this.isSuitable(par1World, par2, par3 - 1, par4, ForgeDirection.UP) || this.isSuitable(par1World, par2, par3 + 1, par4, ForgeDirection.DOWN);
	}
	
	public boolean isSuitable(World world, int x, int y, int z, ForgeDirection side) {
		if(new Pos3D(x, y, z).getBlockAtPos(world) == null)
			return false;
		
		if(world.getBlock(x, y, z) == Sorcery.itemNode)
			return false;
		
		if(world.getTileEntity(x, y, z) != null && world.getTileEntity(x, y, z) instanceof IInventory)
			return true;
		
		if(this.isSideSolid(world, x, y, z, side))
			return true;
		
		return false;
	}
	
	public boolean isOpaqueCube() {
		return false;
	}
	
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	public int getRenderBlockPass() {
		return 1;
	}
	
	public boolean hasTileEntity(int meta) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, int metadata) {
		switch(metadata) {
			case 0:
				return new TileEntityNodeExtractor();
			case 1:
				return new TileEntityNode();
			case 2:
				return new TileEntityNodeInserter();
			case 3:
				return new TileEntityNodeCollector();
			case 4:
				return new TileEntityNodeVoid();
			case 5:
				return new TileEntityNodeBuffer();
		}
		
		return null;
	}
	
	@Override
	public int getRenderType() {
		return Properties.nodeRenderID;
	}
	
	public void breakBlock(World par1World, int par2, int par3, int par4, Block par5, int par6) {
		TileEntityNodeBase var7 = (TileEntityNodeBase)par1World.getTileEntity(par2, par3, par4);
		
		if(var7 != null) {
			if(!var7.sentItems.isEmpty()) {
				for(int i = 0; i < var7.sentItems.size(); i++) {
					var7.sentItems.get(i).dropItemAndKill();
				}
			}
			
			for(int var8 = 0; var8 < var7.getBufferSize(); ++var8) {
				ItemStack var9 = var7.getBufferItem(var8);
				
				if(var9 != null) {
					float var10 = par1World.rand.nextFloat() * 0.8F + 0.1F;
					float var11 = par1World.rand.nextFloat() * 0.8F + 0.1F;
					float var12 = par1World.rand.nextFloat() * 0.8F + 0.1F;
					
					while(var9.stackSize > 0) {
						int var13 = par1World.rand.nextInt(21) + 10;
						
						if(var13 > var9.stackSize) {
							var13 = var9.stackSize;
						}
						
						var9.stackSize -= var13;
						EntityItem var14 = new EntityItem(par1World, (double)((float)par2 + var10), (double)((float)par3 + var11), (double)((float)par4 + var12), new ItemStack(var9.getItem(), var13, var9.getItemDamage()));
						
						if(var9.hasTagCompound()) {
							var14.getEntityItem().setTagCompound((NBTTagCompound)var9.getTagCompound().copy());
						}
						
						float var15 = 0.05F;
						var14.motionX = (double)((float)par1World.rand.nextGaussian() * var15);
						var14.motionY = (double)((float)par1World.rand.nextGaussian() * var15 + 0.2F);
						var14.motionZ = (double)((float)par1World.rand.nextGaussian() * var15);
						par1World.spawnEntityInWorld(var14);
					}
				}
			}
			
			for(int var8 = 0; var8 < var7.getColours().length; ++var8) {
				ItemStack var9 = var7.getColours()[var8];
				
				if(var9 != null) {
					float var10 = par1World.rand.nextFloat() * 0.8F + 0.1F;
					float var11 = par1World.rand.nextFloat() * 0.8F + 0.1F;
					float var12 = par1World.rand.nextFloat() * 0.8F + 0.1F;
					
					while(var9.stackSize > 0) {
						int var13 = par1World.rand.nextInt(21) + 10;
						
						if(var13 > var9.stackSize) {
							var13 = var9.stackSize;
						}
						
						var9.stackSize -= var13;
						EntityItem var14 = new EntityItem(par1World, (double)((float)par2 + var10), (double)((float)par3 + var11), (double)((float)par4 + var12), new ItemStack(var9.getItem(), var13, var9.getItemDamage()));
						
						if(var9.hasTagCompound()) {
							var14.getEntityItem().setTagCompound((NBTTagCompound)var9.getTagCompound().copy());
						}
						
						float var15 = 0.05F;
						var14.motionX = (double)((float)par1World.rand.nextGaussian() * var15);
						var14.motionY = (double)((float)par1World.rand.nextGaussian() * var15 + 0.2F);
						var14.motionZ = (double)((float)par1World.rand.nextGaussian() * var15);
						par1World.spawnEntityInWorld(var14);
					}
				}
			}
			
			for(int var8 = 0; var8 < var7.getFilter().length; ++var8) {
				ItemStack var9 = var7.getFilter()[var8];
				
				if(var9 != null) {
					float var10 = par1World.rand.nextFloat() * 0.8F + 0.1F;
					float var11 = par1World.rand.nextFloat() * 0.8F + 0.1F;
					float var12 = par1World.rand.nextFloat() * 0.8F + 0.1F;
					
					while(var9.stackSize > 0) {
						int var13 = par1World.rand.nextInt(21) + 10;
						
						if(var13 > var9.stackSize) {
							var13 = var9.stackSize;
						}
						
						var9.stackSize -= var13;
						EntityItem var14 = new EntityItem(par1World, (double)((float)par2 + var10), (double)((float)par3 + var11), (double)((float)par4 + var12), new ItemStack(var9.getItem(), var13, var9.getItemDamage()));
						
						if(var9.hasTagCompound()) {
							var14.getEntityItem().setTagCompound((NBTTagCompound)var9.getTagCompound().copy());
						}
						
						float var15 = 0.05F;
						var14.motionX = (double)((float)par1World.rand.nextGaussian() * var15);
						var14.motionY = (double)((float)par1World.rand.nextGaussian() * var15 + 0.2F);
						var14.motionZ = (double)((float)par1World.rand.nextGaussian() * var15);
						par1World.spawnEntityInWorld(var14);
					}
				}
			}
		}
		par1World.removeTileEntity(par2, par3, par4);
		super.breakBlock(par1World, par2, par3, par4, par5, par6);
	}
	
	public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, Block par5) {
		TileEntityNodeBase tile = ((TileEntityNodeBase)par1World.getTileEntity(par2, par3, par4));
		
		if(par1World.isAirBlock(tile.getConnectedBlock().xCoord(), tile.getConnectedBlock().yCoord(), tile.getConnectedBlock().zCoord())) {
			this.dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
			par1World.setBlockToAir(par2, par3, par4);
			par1World.removeTileEntity(par2, par3, par4);
		}
		
		super.onNeighborBlockChange(par1World, par2, par3, par4, par5);
	}
	
	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer player, int i1, float f1, float f2, float f3) {
		if(!player.isSneaking()) {
			player.openGui(Sorcery.instance, Properties.GUI_NODE, world, i, j, k);
			return true;
		}
		return false;
	}
	
	@Override
	public boolean canConnectRedstone(IBlockAccess world, int x, int y, int z, int side) {
		return true;
	}
}
