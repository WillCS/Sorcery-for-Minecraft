package sorcery.blocks;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import sorcery.core.Sorcery;
import sorcery.items.ItemLantern;
import sorcery.lib.Properties;
import sorcery.lib.SorceryItems;
import sorcery.tileentities.TileEntityIncubator;
import sorcery.tileentities.TileEntityLantern;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockArcaneHalf extends Block {
	public BlockArcaneHalf() {
		super(Material.rock);
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
		this.setCreativeTab(CreativeTabs.tabDecorations);
	}
	
	private IIcon[][] IIcons;
	
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister par1IIconRegister) {
		this.IIcons = new IIcon[17][16];
		IIcons[0][0] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "smooth");
		IIcons[0][1] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "incubatorTop");
		IIcons[0][2] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "incubatorSide");
		
		IIcons[16][0] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "blank");
	}
	
	public IIcon getIcon(int side, int meta) {
		if(meta == 0) {
			switch(side) {
				case 0:
					return IIcons[meta][side];
				case 1:
					return IIcons[meta][side];
				default:
					return IIcons[meta][2];
			}
		}
		return IIcons[16][0];
	}
	
    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
            ArrayList<ItemStack> list = new ArrayList<ItemStack>();
            switch(metadata) {
            	case 0:
            		list.add(new ItemStack(Sorcery.arcanehalf, 1, 0));
            		break;
            	case 1:
            		TileEntityLantern tile = (TileEntityLantern)world.getTileEntity(x, y, z);
            		ItemStack lantern = ((ItemLantern)SorceryItems.lantern).getLanternWithFuel(tile.fuel);
            		list.add(lantern);
            		break;
            }
            return list;
    }

	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
		for(int var4 = 0; var4 < 1; ++var4) {
			par3List.add(new ItemStack(par1, 1, var4));
		}
	}
	
	public float getBlockHardness(World par1World, int par2, int par3, int par4) {
		int meta = par1World.getBlockMetadata(par2, par3, par4);
		switch(meta) {
			default:
				return 1F;
		}
	}
	
	public void breakBlock(World par1World, int par2, int par3, int par4, Block par5, int par6) {
		par1World.removeTileEntity(par2, par3, par4);
		super.breakBlock(par1World, par2, par3, par4, par5, par6);
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
		switch(meta) {
			case 0: return true;
			case 1: return true;
		}
		return false;
	}
	
	@Override
	public TileEntity createTileEntity(World world, int metadata) {
		switch(metadata) {
			case 0:
				return new TileEntityIncubator();
			case 1:
				return new TileEntityLantern();
		}
		return null;
	}
	
	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer player, int i1, float f1, float f2, float f3) {
		int meta = world.getBlockMetadata(i, j, k);
		if(!player.isSneaking()) {
			if(meta == 0 && player.getCurrentEquippedItem() != null) {
				TileEntityIncubator tile = (TileEntityIncubator)world.getTileEntity(i, j, k);
				
				int egg = 0;
				if(player.getCurrentEquippedItem().getItem() == Items.egg) {
					egg = 1;
				} else if(player.getCurrentEquippedItem().getItem() == SorceryItems.goldegg) {
					player.addStat(Sorcery.geneticEngineering, 1);
					egg = 2;
				} else if(player.getCurrentEquippedItem().getItem() == SorceryItems.phoenixegg) {
					egg = 3;
				} else {
					egg = 0;
				}
				if(egg != 0 && tile.eggTime == 0) {
					tile.addEggToIncubator(egg);
					world.markBlockForUpdate(i, j, k);
					if(!player.capabilities.isCreativeMode) {
						--player.getCurrentEquippedItem().stackSize;
					}
				}
			}
			
			return true;
		} else if(!world.isRemote &&  player.canPlayerEdit(i, j, k, 0, player.getCurrentEquippedItem())) {
			if(meta == 1 && player.getCurrentEquippedItem() == null) {
				TileEntityLantern lantern = (TileEntityLantern)world.getTileEntity(i, j, k);
				player.setCurrentItemOrArmor(0, ((ItemLantern)SorceryItems.lantern).getLanternWithFuel(lantern.fuel));
				world.setBlockToAir(i, j, k);
			}
		}
		return false;
	}
	
	public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, Block par5) {
		if(par1World.getBlockMetadata(par2, par3, par4) == 1) {
			TileEntityLantern lantern = ((TileEntityLantern)par1World.getTileEntity(par2, par3, par4));
		
			if(par1World.isAirBlock(lantern.getConnectedBlock().xCoord(),
									lantern.getConnectedBlock().yCoord(),
									lantern.getConnectedBlock().zCoord())) {
				this.dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
				par1World.setBlockToAir(par2, par3, par4);
				par1World.removeTileEntity(par2, par3, par4);
			}	
		}
		super.onNeighborBlockChange(par1World, par2, par3, par4, par5);
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
		switch(par1IBlockAccess.getBlockMetadata(par2, par3, par4)) {
			case 0:
				this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
				return;
			case 1:
				int side = ((TileEntityLantern)par1IBlockAccess.getTileEntity(par2, par3, par4)).front;
				switch(side) {
					case 0:
						this.setBlockBounds(0.35F, 0.0F, 0.35F, 0.65F, 0.65F, 0.65F);
						return;
					case 1:
						this.setBlockBounds(0.35F, 0.1F, 0.35F, 0.65F, 1.0F, 0.65F);
						return;
					case 2:
						this.setBlockBounds(0.35F, 0.2F, 0.15F, 0.65F, 0.85F, 0.45F);
						return;
					case 3:
						this.setBlockBounds(0.35F, 0.2F, 0.55F, 0.65F, 0.85F, 0.85F);
						return;
					case 4:
						this.setBlockBounds(0.15F, 0.2F, 0.35F, 0.45F, 0.85F, 0.65F);
						return;
					case 5:
						this.setBlockBounds(0.55F, 0.2F, 0.35F, 0.85F, 0.85F, 0.65F);
						return;
				}
				return;
		}
		super.setBlockBoundsBasedOnState(par1IBlockAccess, par2, par3, par4);
	}
	
    public boolean canCollideCheck(int par1, boolean par2){
        return true;
    }
	
	@Override
	public int getLightValue(IBlockAccess par1iBlockAccess, int par2, int par3, int par4) {
		int meta = par1iBlockAccess.getBlockMetadata(par2, par3, par4);
		switch(meta) {
			case 1: return 10;
			default: return 0;
		}
	}
	
	@Override
	public void setBlockBoundsForItemRender() {
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
	}
}
