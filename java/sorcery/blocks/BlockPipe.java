package sorcery.blocks;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import sorcery.core.Sorcery;
import sorcery.fluid.Pipe;
import sorcery.fluid.PipeHelper;
import sorcery.lib.Properties;
import sorcery.lib.SorceryItems;
import sorcery.tileentities.TileEntityElementalInfuser;
import sorcery.tileentities.TileEntityPipe;

public class BlockPipe extends Block {
	public BlockPipe() {
		super(Material.circuits);
		this.setCreativeTab(Sorcery.tabSorceryTech);
	}

	public IIcon getIcon(int side, int meta) {
		return Blocks.iron_block.getIcon(0, 0);
	}

	public void getSubBlocks(Block par1, CreativeTabs par2CreativeTabs, List par3List) {
		for(int i = 0; i < PipeHelper.pipes.length; i++) {
			if(PipeHelper.pipes[i] != null)
				par3List.add(new ItemStack(par1, 1, i));
		}
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		TileEntityPipe pipe = (TileEntityPipe)world.getTileEntity(x, y, z);
		boolean creative = false;
		EntityPlayer player = world.getClosestPlayer(x, y, z, 10);
		if(player != null)
			creative = player.capabilities.isCreativeMode;

		if(pipe != null && !creative) {
			int pipeId = pipe.pipe.type.id;
			ItemStack item = new ItemStack(Sorcery.fluidPipe, 1, pipeId);
			world.spawnEntityInWorld(new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, item));

			int colour = pipe.colour;
			if(colour != -1) {
				item = new ItemStack(SorceryItems.colourTag, 1, colour);
				world.spawnEntityInWorld(new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, item));
			}

		}

		world.removeTileEntity(x, y, z);
	}

	public int quantityDropped(Random par1Random) {
		return 0;
	}

	public float getBlockHardness(World par1World, int par2, int par3, int par4) {
		return 0.3F;
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
		return new TileEntityPipe();
	}

	@Override
	public int getRenderType() {
		return Properties.pipeRenderID;
	}

	public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, Block par5) {
		super.onNeighborBlockChange(par1World, par2, par3, par4, par5);
	}

	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer player, int i1, float f1, float f2, float f3) {
		if(player.getCurrentEquippedItem() != null) {
			if(player.getCurrentEquippedItem().getItem() == SorceryItems.colourTag) {
				TileEntityPipe pipe = ((TileEntityPipe)world.getTileEntity(i, j, k));
				int meta = player.getCurrentEquippedItem().getItemDamage();

				if(pipe.colour == meta)
					return false;

				int prevColour = pipe.colour;
				boolean creative = player.capabilities.isCreativeMode;

				if(!creative)
					player.inventory.decrStackSize(player.inventory.currentItem, 1);

				pipe.colour = meta;

				if(prevColour != -1 && !creative) {
					ItemStack item = new ItemStack(SorceryItems.colourTag, 1, prevColour);
					if(!world.isRemote)
						world.spawnEntityInWorld(new EntityItem(world, i + 0.5, j + 0.5, k + 0.5, item));
				}
				
				return true;
			}
		} else {
			//Pipe pipe = ((TileEntityPipe)world.getTileEntity(i, j, k)).pipe;
			//System.out.println(pipe.colour);
		}

		return false;
	}

	public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
		TileEntityPipe pipe = ((TileEntityPipe)par1IBlockAccess.getTileEntity(par2, par3, par4));
		if(pipe != null && pipe.pipe != null) {
			float[] bounds1 = new float[] {0.3F, 0.3F, 0.3F};
			float[] bounds2 = new float[] {0.7F, 0.7F, 0.7F};

			if(!pipe.isConnecting()) {
				bounds2[0] = 1F;
				bounds1[0] = 0F;
			} else if(pipe.getConnections() == 1) {
				if(pipe.canConnect(ForgeDirection.WEST)) {
					bounds1[0] = 0F;
					bounds2[0] = 1F;
				} else if(pipe.canConnect(ForgeDirection.EAST)) {
					bounds1[0] = 0F;
					bounds2[0] = 1F;
				} else if(pipe.canConnect(ForgeDirection.NORTH)) {
					bounds1[2] = 0F;
					bounds2[2] = 1F;
				} else if(pipe.canConnect(ForgeDirection.SOUTH)) {
					bounds1[2] = 0F;
					bounds2[2] = 1F;
				} else if(pipe.canConnect(ForgeDirection.DOWN)) {
					bounds1[1] = 0F;
					bounds2[1] = 1F;
				} else if(pipe.canConnect(ForgeDirection.UP)) {
					bounds1[1] = 0F;
					bounds2[1] = 1F;
				}
			} else {
				if(pipe.canConnect(ForgeDirection.WEST))
					bounds1[0] = 0F;
				
				if(pipe.canConnect(ForgeDirection.EAST))
					bounds2[0] = 1F;
				
				if(pipe.canConnect(ForgeDirection.DOWN))
					bounds1[1] = 0F;
				
				if(pipe.canConnect(ForgeDirection.UP))
					bounds2[1] = 1F;
				
				if(pipe.canConnect(ForgeDirection.NORTH))
					bounds1[2] = 0F;
				
				if(pipe.canConnect(ForgeDirection.SOUTH))
					bounds2[2] = 1F;
			}
			
			this.setBlockBounds(bounds1[0], bounds1[1], bounds1[2], bounds2[0], bounds2[1], bounds2[2]);
		}
	}
	
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
    	TileEntityPipe pipe = ((TileEntityPipe)par1World.getTileEntity(par2, par3, par4));
		if(pipe != null && pipe.pipe != null) {
			float[] bounds1 = new float[] {0.3F, 0.3F, 0.3F};
			float[] bounds2 = new float[] {0.7F, 0.7F, 0.7F};

			if(!pipe.isConnecting()) {
				bounds2[0] = 1F;
				bounds1[0] = 0F;
			} else if(pipe.getConnections() == 1) {
				if(pipe.canConnect(ForgeDirection.WEST)) {
					bounds1[0] = 0F;
					bounds2[0] = 1F;
				} else if(pipe.canConnect(ForgeDirection.EAST)) {
					bounds1[0] = 0F;
					bounds2[0] = 1F;
				} else if(pipe.canConnect(ForgeDirection.NORTH)) {
					bounds1[2] = 0F;
					bounds2[2] = 1F;
				} else if(pipe.canConnect(ForgeDirection.SOUTH)) {
					bounds1[2] = 0F;
					bounds2[2] = 1F;
				} else if(pipe.canConnect(ForgeDirection.DOWN)) {
					bounds1[1] = 0F;
					bounds2[1] = 1F;
				} else if(pipe.canConnect(ForgeDirection.UP)) {
					bounds1[1] = 0F;
					bounds2[1] = 1F;
				}
			} else {
				if(pipe.canConnect(ForgeDirection.WEST))
					bounds1[0] = 0F;
				
				if(pipe.canConnect(ForgeDirection.EAST))
					bounds2[0] = 1F;
				
				if(pipe.canConnect(ForgeDirection.DOWN))
					bounds1[1] = 0F;
				
				if(pipe.canConnect(ForgeDirection.UP))
					bounds2[1] = 1F;
				
				if(pipe.canConnect(ForgeDirection.NORTH))
					bounds1[2] = 0F;
				
				if(pipe.canConnect(ForgeDirection.SOUTH))
					bounds2[2] = 1F;
			}
			return AxisAlignedBB.getBoundingBox(par2 + bounds1[0], par3 + bounds1[1], par4 + bounds1[2],
												par2 + bounds2[0], par3 + bounds2[1], par4 + bounds2[2]);
		}
		return super.getCollisionBoundingBoxFromPool(par1World, par2, par3, par4);
    }
	
	public void onNeighborTileChange(World world, int x, int y, int z, int tileX, int tileY, int tileZ) {
		this.setBlockBoundsBasedOnState(world, x, y, z);
    }

	@Override
	public boolean canConnectRedstone(IBlockAccess world, int x, int y, int z, int side) {
		TileEntityPipe pipe = (TileEntityPipe)world.getTileEntity(x, y, z);

		return pipe.pipe.extra != 0;
	}
}
