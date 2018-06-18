package sorcery.world;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.util.ForgeDirection;
import sorcery.core.Sorcery;

public class WorldGenMagicTrees extends WorldGenerator {
	/** The minimum height of a generated tree. */
	private final int minTreeHeight;
	
	public WorldGenMagicTrees(boolean par1) {
		this(par1, 4);
	}
	
	public WorldGenMagicTrees(boolean par1, int par2) {
		super(par1);
		this.minTreeHeight = par2;
	}
	
	public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5) {
		if(par1World.isRemote)
			return false;
		int meta = par2Random.nextInt(4);
		int treeType = par2Random.nextInt(3);
		boolean flag = true;
		
		if(treeType == 0 || treeType == 1) {
			int l = par2Random.nextInt(3) + this.minTreeHeight;
			if(par4 >= 1 && par4 + l + 1 <= 256) {
				int i1;
				byte b0;
				int j1;
				Block k1;
				
				for(i1 = par4; i1 <= par4 + 1 + l; ++i1) {
					b0 = 1;
					
					if(i1 == par4) {
						b0 = 0;
					}
					
					if(i1 >= par4 + 1 + l - 2) {
						b0 = 2;
					}
					
					for(int l1 = par3 - b0; l1 <= par3 + b0 && flag; ++l1) {
						for(j1 = par5 - b0; j1 <= par5 + b0 && flag; ++j1) {
							if(i1 >= 0 && i1 < 256) {
								k1 = par1World.getBlock(l1, i1, j1);
								
								if(!k1.isAir(par1World, l1, i1, j1) && !k1.isLeaves(par1World, l1, i1, j1) && k1 != Blocks.grass && k1 != Blocks.dirt && !k1.isWood(par1World, l1, i1, j1)) {
									flag = false;
								}
							} else {
								flag = false;
							}
						}
					}
				}
				
				if(!flag) {
					return false;
				} else {
					Block soil = par1World.getBlock(par3, par4 - 1, par5);
					boolean isSoil = (soil != null && soil.canSustainPlant(par1World, par3, par4 - 1, par5, ForgeDirection.UP, (BlockSapling)Blocks.sapling));
					
					if(isSoil && par4 < 256 - l - 1) {
						soil.onPlantGrow(par1World, par3, par4 - 1, par5, par3, par4, par5);
						b0 = 3;
						byte b1 = 0;
						int i2;
						int j2;
						int k2;
						int k22;
						
						for(j1 = par4 - b0 + l; j1 <= par4 + l; ++j1) {
							k22 = j1 - (par4 + l);
							i2 = b1 + 1 - k22 / 2;
							
							for(j2 = par3 - i2; j2 <= par3 + i2; ++j2) {
								k2 = j2 - par3;
								
								for(int l2 = par5 - i2; l2 <= par5 + i2; ++l2) {
									int i3 = l2 - par5;
									
									if(Math.abs(k2) != i2 || Math.abs(i3) != i2 || par2Random.nextInt(2) != 0 && k22 != 0) {
										Block block = par1World.getBlock(j2, j1, l2);
										
										if(block == null || block.canBeReplacedByLeaves(par1World, j2, j1, l2)) {
											this.setBlockAndNotifyAdequately(par1World, j2, j1, l2, Sorcery.magicLeaves, meta);
										}
									}
								}
							}
						}
						
						for(j1 = 0; j1 < l; ++j1) {
							Block block = par1World.getBlock(par3, par4 + j1, par5);
							
							if(!block.isAir(par1World, par3, par4 + j1, par5) || block == null || block.isLeaves(par1World, par3, par4 + j1, par5)) {
								this.setBlockAndNotifyAdequately(par1World, par3, par4 + j1, par5, Blocks.log, 0);
							}
						}
						
						return true;
					} else {
						return false;
					}
				}
			} else {
				return false;
			}
		} else if(treeType == 2) {
			int l = par2Random.nextInt(4) + 6;
			int i1 = 1 + par2Random.nextInt(2);
			int j1 = l - i1;
			int k1 = 2 + par2Random.nextInt(2);
			
			if(par4 >= 1 && par4 + l + 1 <= 256) {
				int l1;
				int i2;
				int j2;
				int k2;
				
				for(l1 = par4; l1 <= par4 + 1 + l && flag; ++l1) {
					if(l1 - par4 < i1) {
						k2 = 0;
					} else {
						k2 = k1;
					}
					
					for(i2 = par3 - k2; i2 <= par3 + k2 && flag; ++i2) {
						for(int l2 = par5 - k2; l2 <= par5 + k2 && flag; ++l2) {
							if(l1 >= 0 && l1 < 256) {
								Block block = par1World.getBlock(i2, l1, l2);
								
								if(!block.isAir(par1World, i2, l1, l2) && block != null && !block.isLeaves(par1World, i2, l1, l2)) {
									flag = false;
								}
							} else {
								flag = false;
							}
						}
					}
				}
				
				if(!flag) {
					return false;
				} else {
					Block soil = par1World.getBlock(par3, par4 - 1, par5);
					boolean isValidSoil = soil != null && soil.canSustainPlant(par1World, par3, par4 - 1, par5, ForgeDirection.UP, (BlockSapling)Blocks.sapling);
					
					if(isValidSoil && par4 < 256 - l - 1) {
						soil.onPlantGrow(par1World, par3, par4 - 1, par5, par3, par4, par5);
						k2 = par2Random.nextInt(2);
						i2 = 1;
						byte b0 = 0;
						int i3;
						int j3;
						
						for(j2 = 0; j2 <= j1; ++j2) {
							j3 = par4 + l - j2;
							
							for(i3 = par3 - k2; i3 <= par3 + k2; ++i3) {
								int k3 = i3 - par3;
								
								for(int l3 = par5 - k2; l3 <= par5 + k2; ++l3) {
									int i4 = l3 - par5;
									
									Block block = par1World.getBlock(i3, j3, l3);
									
									if((Math.abs(k3) != k2 || Math.abs(i4) != k2 || k2 <= 0) && (block == null || block.canBeReplacedByLeaves(par1World, i3, j3, l3))) {
										this.setBlockAndNotifyAdequately(par1World, i3, j3, l3, Sorcery.magicLeaves, meta);
									}
								}
							}
							
							if(k2 >= i2) {
								k2 = b0;
								b0 = 1;
								++i2;
								
								if(i2 > k1) {
									i2 = k1;
								}
							} else {
								++k2;
							}
						}
						
						j2 = par2Random.nextInt(3);
						
						for(j3 = 0; j3 < l - j2; ++j3) {
							Block block = par1World.getBlock(par3, par4 + j3, par5);
							
							if(!block.isAir(par1World, par3, par4 + j3, par5) || block == null || block.isLeaves(par1World, par3, par4 + j3, par5)) {
								this.setBlockAndNotifyAdequately(par1World, par3, par4 + j3, par5, Blocks.log, 0);
							}
						}
						
						return true;
					} else {
						return false;
					}
				}
			} else {
				return false;
			}
		}
		return false;
	}
}
