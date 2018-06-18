package sorcery.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeavesBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import sorcery.core.Sorcery;
import sorcery.lib.Properties;
import sorcery.lib.utils.Utils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockMagicLeaves extends BlockLeavesBase implements IShearable {
	public static final String[] LEAF_TYPES = new String[]{"blue", "purple", "yellow", "red"};
	public static final String[][] field_94396_b = new String[][]{{"leavesBlueFancy", "leavesPurpleFancy", "leavesYellowFancy", "leavesRedFancy"}, {"leavesBlueFast", "leavesPurpleFast", "leavesYellowFast", "leavesRedFast"}};
	private IIcon[][] IIconArray = new IIcon[2][];
	int[] adjacentTreeBlocks;
	
	public BlockMagicLeaves() {
		super(Material.leaves, false);
		this.setTickRandomly(true);
		this.setCreativeTab(Sorcery.tabSorceryMaterials);
	}
	
	@SideOnly(Side.CLIENT)
	public int getBlockColor() {
		return 16777215;
	}
	
	@SideOnly(Side.CLIENT)
	public int getRenderColor(int par1) {
		return 16777215;
	}
	
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
		return 16777215;
	}
	
	public void registerBlockIcons(IIconRegister par1IIconRegister) {
		for(int i = 0; i < field_94396_b.length; ++i) {
			this.IIconArray[i] = new IIcon[field_94396_b[i].length];
			
			for(int j = 0; j < field_94396_b[i].length; ++j) {
				this.IIconArray[i][j] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + field_94396_b[i][j]);
			}
		}
	}
	
	public IIcon getIcon(int par1, int par2) {
		int graphics = !this.field_150121_P ? 0 : 1;
		switch(par2) {
			case 0:
			case 4:
				return this.IIconArray[graphics][0];
			case 1:
			case 5:
				return this.IIconArray[graphics][1];
			case 2:
			case 6:
				return this.IIconArray[graphics][2];
			case 3:
			case 7:
				return this.IIconArray[graphics][3];
			default:
				return Blocks.planks.getIcon(0, 0);
		}
	}
	
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random) {
		if(par1World.canLightningStrikeAt(par2, par3 + 1, par4) && !par1World.doesBlockHaveSolidTopSurface(par1World, par2, par3 - 1, par4) && par5Random.nextInt(15) == 1) {
			double d0 = (double)((float)par2 + par5Random.nextFloat());
			double d1 = (double)par3 - 0.05D;
			double d2 = (double)((float)par4 + par5Random.nextFloat());
			par1World.spawnParticle("dripWater", d0, d1, d2, 0.0D, 0.0D, 0.0D);
		}
		
		if(par5Random.nextInt(20) == 0) {
			float[] colours;
			
			switch(par1World.getBlockMetadata(par2, par3, par4)) {
				case 0:
				case 4:
					colours = Utils.decodeColourF(ItemDye.field_150922_c[4]);
					break;
				case 1:
				case 5:
					colours = Utils.decodeColourF(ItemDye.field_150922_c[5]);
					break;
				case 2:
				case 6:
					colours = Utils.decodeColourF(ItemDye.field_150922_c[11]);
					break;
				default:
					colours = Utils.decodeColourF(ItemDye.field_150922_c[1]);
					break;
			}
			
			for(int i = 1; i < 3; i++) {
				if(par5Random.nextInt(3) == 0)
					Sorcery.proxy.spawnParticleEffect("sparkle", par1World, (double)(par2 + (i * par5Random.nextDouble()) / 5), (double)(par3 + (i * par5Random.nextDouble()) / 5), (double)(par4 + (i * par5Random.nextDouble()) / 5), 0.1F, colours[0], colours[1], colours[2]);
				if(par5Random.nextInt(3) == 0)
					Sorcery.proxy.spawnParticleEffect("sparkle", par1World, (double)(par2 - (i * par5Random.nextDouble()) / 5), (double)(par3 - (i * par5Random.nextDouble()) / 5), (double)(par4 - (i * par5Random.nextDouble()) / 5), 0.1F, colours[0], colours[1], colours[2]);
			}
		}
	}
	
	public Item getItemDropped(int par1, Random par2Random, int par3) {
		return super.getItemDropped(par1, par2Random, par3);
	}
	
	public void dropBlockAsItemWithChance(World par1World, int par2, int par3, int par4, int par5, float par6, int par7) {
		if(!par1World.isRemote) {
			int j1 = 20;
			
			if((par5 & 3) == 3) {
				j1 = 40;
			}
			
			if(par7 > 0) {
				j1 -= 2 << par7;
				
				if(j1 < 10) {
					j1 = 10;
				}
			}
			
			if(par1World.rand.nextInt(j1) == 0) {
				Item k1 = this.getItemDropped(par5, par1World.rand, par7);
				this.dropBlockAsItem(par1World, par2, par3, par4, new ItemStack(k1, 1, 0));
			}
		}
	}
	
	public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6) {
		byte b0 = 1;
		int j1 = b0 + 1;
		
		if(par1World.checkChunksExist(par2 - j1, par3 - j1, par4 - j1, par2 + j1, par3 + j1, par4 + j1)) {
			for(int k1 = -b0; k1 <= b0; ++k1) {
				for(int l1 = -b0; l1 <= b0; ++l1) {
					for(int i2 = -b0; i2 <= b0; ++i2) {
						
						if(par1World.getBlock(par2 + k1, par3 + l1, par4 + i2) != null) {
							par1World.getBlock(par2 + k1, par3 + l1, par4 + i2).beginLeavesDecay(par1World, par2 + k1, par3 + l1, par4 + i2);
						}
					}
				}
			}
		}
	}
	
	public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random) {
		if(!par1World.isRemote) {
			int l = par1World.getBlockMetadata(par2, par3, par4);
			
			if(l <= 3) {
				byte b0 = 4;
				int i1 = b0 + 1;
				byte b1 = 32;
				int j1 = b1 * b1;
				int k1 = b1 / 2;
				
				if(this.adjacentTreeBlocks == null) {
					this.adjacentTreeBlocks = new int[b1 * b1 * b1];
				}
				
				int l1;
				
				if(par1World.checkChunksExist(par2 - i1, par3 - i1, par4 - i1, par2 + i1, par3 + i1, par4 + i1)) {
					int i2;
					int j2;
					int k2;
					
					for(l1 = -b0; l1 <= b0; ++l1) {
						for(i2 = -b0; i2 <= b0; ++i2) {
							for(j2 = -b0; j2 <= b0; ++j2) {
								
								Block block = par1World.getBlock(par2 + l1, par3 + i2, par4 + j2);
								
								if(block != null && this.canSustainLeaves(par1World, par2 + l1, par3 + i2, par4 + j2)) {
									this.adjacentTreeBlocks[(l1 + k1) * j1 + (i2 + k1) * b1 + j2 + k1] = 0;
								} else if(block != null && this.isLeaves(par1World, par2 + l1, par3 + i2, par4 + j2)) {
									this.adjacentTreeBlocks[(l1 + k1) * j1 + (i2 + k1) * b1 + j2 + k1] = -2;
								} else {
									this.adjacentTreeBlocks[(l1 + k1) * j1 + (i2 + k1) * b1 + j2 + k1] = -1;
								}
							}
						}
					}
					
					for(l1 = 1; l1 <= 4; ++l1) {
						for(i2 = -b0; i2 <= b0; ++i2) {
							for(j2 = -b0; j2 <= b0; ++j2) {
								for(k2 = -b0; k2 <= b0; ++k2) {
									if(this.adjacentTreeBlocks[(i2 + k1) * j1 + (j2 + k1) * b1 + k2 + k1] == l1 - 1) {
										if(this.adjacentTreeBlocks[(i2 + k1 - 1) * j1 + (j2 + k1) * b1 + k2 + k1] == -2) {
											this.adjacentTreeBlocks[(i2 + k1 - 1) * j1 + (j2 + k1) * b1 + k2 + k1] = l1;
										}
										
										if(this.adjacentTreeBlocks[(i2 + k1 + 1) * j1 + (j2 + k1) * b1 + k2 + k1] == -2) {
											this.adjacentTreeBlocks[(i2 + k1 + 1) * j1 + (j2 + k1) * b1 + k2 + k1] = l1;
										}
										
										if(this.adjacentTreeBlocks[(i2 + k1) * j1 + (j2 + k1 - 1) * b1 + k2 + k1] == -2) {
											this.adjacentTreeBlocks[(i2 + k1) * j1 + (j2 + k1 - 1) * b1 + k2 + k1] = l1;
										}
										
										if(this.adjacentTreeBlocks[(i2 + k1) * j1 + (j2 + k1 + 1) * b1 + k2 + k1] == -2) {
											this.adjacentTreeBlocks[(i2 + k1) * j1 + (j2 + k1 + 1) * b1 + k2 + k1] = l1;
										}
										
										if(this.adjacentTreeBlocks[(i2 + k1) * j1 + (j2 + k1) * b1 + (k2 + k1 - 1)] == -2) {
											this.adjacentTreeBlocks[(i2 + k1) * j1 + (j2 + k1) * b1 + (k2 + k1 - 1)] = l1;
										}
										
										if(this.adjacentTreeBlocks[(i2 + k1) * j1 + (j2 + k1) * b1 + k2 + k1 + 1] == -2) {
											this.adjacentTreeBlocks[(i2 + k1) * j1 + (j2 + k1) * b1 + k2 + k1 + 1] = l1;
										}
									}
								}
							}
						}
					}
				}
				
				l1 = this.adjacentTreeBlocks[k1 * j1 + k1 * b1 + k1];
				
				if(l1 >= 0) {
					par1World.setBlockMetadataWithNotify(par2, par3, par4, l + 4, 4);
				} else {
					this.removeLeaves(par1World, par2, par3, par4);
				}
			}
		}
	}
	
	private void removeLeaves(World par1World, int par2, int par3, int par4) {
		this.dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
		par1World.setBlockToAir(par2, par3, par4);
	}
	
	public int quantityDropped(Random par1Random) {
		return par1Random.nextInt(20) == 0 ? 1 : 0;
	}
	
	@Override
	public boolean isShearable(ItemStack item, IBlockAccess world, int x, int y, int z) {
		return true;
	}
	
	@Override
	public ArrayList<ItemStack> onSheared(ItemStack item, IBlockAccess world, int x, int y, int z, int fortune) {
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		ret.add(new ItemStack(this, 1, world.getBlockMetadata(x, y, z) & 3));
		return ret;
	}
	
	public void harvestBlock(World par1World, EntityPlayer par2EntityPlayer, int par3, int par4, int par5, int par6) {
		super.harvestBlock(par1World, par2EntityPlayer, par3, par4, par5, par6);
	}
	
	public int damageDropped(int par1) {
		return par1 & 3;
	}
	
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
		par3List.add(new ItemStack(par1, 1, 0));
		par3List.add(new ItemStack(par1, 1, 1));
		par3List.add(new ItemStack(par1, 1, 2));
		par3List.add(new ItemStack(par1, 1, 3));
	}
	
	protected ItemStack createStackedBlock(int par1) {
		return new ItemStack(this, 1, par1 & 3);
	}
	
	@Override
	public void beginLeavesDecay(World world, int x, int y, int z) {
		// world.setBlockMetadataWithNotify(x, y, z, world.getBlockMetadata(x,
		// y, z) | 4, 4);
	}
	
	@Override
	public boolean isLeaves(IBlockAccess world, int x, int y, int z) {
		return true;
	}
	
	public boolean isOpaqueCube() {
		return this.field_150121_P;
	}
}