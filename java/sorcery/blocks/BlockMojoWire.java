package sorcery.blocks;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import sorcery.core.Sorcery;
import sorcery.lib.Properties;
import sorcery.lib.SorceryItems;
import sorcery.mojo.EnumWireType;
import sorcery.tileentities.TileEntityElementalInfuser;
import sorcery.tileentities.TileEntityMojoWire;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockMojoWire extends Block {
	public BlockMojoWire() {
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
	
	public Item getItemDropped(int i, Random random, int j) {
		return super.getItemDropped(i, random, j);
	}
	
	public int damageDropped(int i) {
		return i;
	}

	@Override
	public int quantityDropped(int meta, int fortune, Random random) {
		return 0;
	}

	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
		for(int var4 = 0; var4 < 6; ++var4) {
			par3List.add(new ItemStack(par1, 1, var4));
		}
	}
	
	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer player, int i1, float f1, float f2, float f3) {
		if(player.getCurrentEquippedItem() != null) {
			if(player.getCurrentEquippedItem().getItem() == SorceryItems.colourTag) {
				TileEntityMojoWire wire = ((TileEntityMojoWire)world.getTileEntity(i, j, k));
				int meta = player.getCurrentEquippedItem().getItemDamage();

				if(wire.colour == meta)
					return false;

				int prevColour = wire.colour;
				boolean creative = player.capabilities.isCreativeMode;

				if(!creative)
					player.inventory.decrStackSize(player.inventory.currentItem, 1);

				wire.colour = meta;

				if(prevColour != -1 && !creative) {
					ItemStack item = new ItemStack(SorceryItems.colourTag, 1, prevColour);
					if(!world.isRemote)
						world.spawnEntityInWorld(new EntityItem(world, i + 0.5, j + 0.5, k + 0.5, item));
				}
				
				return true;
			}
		}

		return false;
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		TileEntityMojoWire wire = (TileEntityMojoWire)world.getTileEntity(x, y, z);
		boolean creative = false;
		EntityPlayer player = world.getClosestPlayer(x, y, z, 10);
		if(player != null)
			creative = player.capabilities.isCreativeMode;

		if(wire != null && !creative) {
			int pipeId = wire.type.id;
			ItemStack item = new ItemStack(Sorcery.mojoWire, 1, pipeId);
			world.spawnEntityInWorld(new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, item));

			int colour = wire.colour;
			if(colour != -1) {
				item = new ItemStack(SorceryItems.colourTag, 1, colour);
				world.spawnEntityInWorld(new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, item));
			}

		}

		world.removeTileEntity(x, y, z);
	}

	public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
		TileEntityMojoWire wire = ((TileEntityMojoWire)par1IBlockAccess.getTileEntity(par2, par3, par4));
		if(wire != null && wire != null) {
			float[] bounds1 = new float[] {0.3F, 0.3F, 0.3F};
			float[] bounds2 = new float[] {0.7F, 0.7F, 0.7F};

			if(!wire.isConnecting()) {
				bounds2[0] = 1F;
				bounds1[0] = 0F;
			} else {
				if(wire.canConnect(ForgeDirection.WEST))
					bounds1[0] = 0F;
				
				if(wire.canConnect(ForgeDirection.EAST))
					bounds2[0] = 1F;
				
				if(wire.canConnect(ForgeDirection.DOWN))
					bounds1[1] = 0F;
				
				if(wire.canConnect(ForgeDirection.UP))
					bounds2[1] = 1F;
				
				if(wire.canConnect(ForgeDirection.NORTH))
					bounds1[2] = 0F;
				
				if(wire.canConnect(ForgeDirection.SOUTH))
					bounds2[2] = 1F;
			}
			
			this.setBlockBounds(bounds1[0], bounds1[1], bounds1[2], bounds2[0], bounds2[1], bounds2[2]);
		}
	}
	
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
    	TileEntityMojoWire wire = ((TileEntityMojoWire)par1World.getTileEntity(par2, par3, par4));
		if(wire != null && wire != null) {
			float[] bounds1 = new float[] {0.3F, 0.3F, 0.3F};
			float[] bounds2 = new float[] {0.7F, 0.7F, 0.7F};

			if(!wire.isConnecting()) {
				bounds2[0] = 1F;
				bounds1[0] = 0F;
			} else {
				if(wire.canConnect(ForgeDirection.WEST))
					bounds1[0] = 0F;
				
				if(wire.canConnect(ForgeDirection.EAST))
					bounds2[0] = 1F;
				
				if(wire.canConnect(ForgeDirection.DOWN))
					bounds1[1] = 0F;
				
				if(wire.canConnect(ForgeDirection.UP))
					bounds2[1] = 1F;
				
				if(wire.canConnect(ForgeDirection.NORTH))
					bounds1[2] = 0F;
				
				if(wire.canConnect(ForgeDirection.SOUTH))
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
		TileEntityMojoWire wire = new TileEntityMojoWire();
		wire.colour = -1;
		wire.type = EnumWireType.getWireTypeFrom(metadata);
		return wire;
	}
	
	@Override
	public int getRenderType() {
		return Properties.wireRenderID;
	}
	
	@Override
	public boolean canConnectRedstone(IBlockAccess world, int x, int y, int z, int side) {
		return false;
	}
}
