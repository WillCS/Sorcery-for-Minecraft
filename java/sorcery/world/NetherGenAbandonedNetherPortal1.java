package sorcery.world;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import sorcery.core.Sorcery;

public class NetherGenAbandonedNetherPortal1 extends WorldGenerator {
	protected Block[] GetValidSpawnBlocks() {
		return new Block[]{Blocks.netherrack, Blocks.soul_sand, Blocks.nether_brick, Sorcery.arcaneblock};
	}
	
	public boolean LocationIsValidSpawn(World world, int i, int j, int k) {
		int distanceToAir = 0;
		Block checkID = world.getBlock(i, j, k);
		
		while(!checkID.isAir(world, i, j, k)) {
			distanceToAir++;
			checkID = world.getBlock(i, j + distanceToAir, k);
		}
		
		if(distanceToAir > 8) {
			return false;
		}
		j += distanceToAir - 1;
		
		Block blockID = world.getBlock(i, j, k);
		Block blockIDAbove = world.getBlock(i, j + 1, k);
		Block blockIDBelow = world.getBlock(i, j - 1, k);
		for(Block x : GetValidSpawnBlocks()) {
			if(blockIDAbove.isAir(world, i, j, k)) {
				return false;
			}
			if(blockID == x) {
				return true;
			} else if(blockID == Blocks.snow && blockIDBelow == x) {
				return true;
			}
		}
		return false;
	}
	
	public NetherGenAbandonedNetherPortal1() {
	}
	
	public boolean generate(World world, Random rand, int i, int j, int k) {
		// check that each corner is one of the valid spawn blocks
		if(!LocationIsValidSpawn(world, i, j, k) || !LocationIsValidSpawn(world, i + 4, j, k) || !LocationIsValidSpawn(world, i + 4, j, k + 7) || !LocationIsValidSpawn(world, i, j, k + 7)) {
			return false;
		}
		
		world.setBlock(i + 0, j + 0, k + 0, Blocks.nether_brick, 0, 2);
		world.setBlock(i + 0, j + 0, k + 1, Blocks.nether_brick, 0, 2);
		world.setBlock(i + 0, j + 0, k + 2, Blocks.nether_brick, 0, 2);
		world.setBlock(i + 0, j + 0, k + 3, Blocks.nether_brick, 0, 2);
		world.setBlock(i + 0, j + 0, k + 4, Blocks.nether_brick, 0, 2);
		world.setBlock(i + 0, j + 0, k + 5, Blocks.nether_brick, 0, 2);
		world.setBlock(i + 0, j + 0, k + 6, Blocks.nether_brick, 0, 2);
		world.setBlock(i + 0, j + 0, k + 7, Blocks.nether_brick, 0, 2);
		world.setBlock(i + 0, j + 1, k + 0, Blocks.air, 0, 2);
		world.setBlock(i + 0, j + 1, k + 1, Blocks.air, 0, 2);
		world.setBlock(i + 0, j + 1, k + 2, Blocks.air, 0, 2);
		world.setBlock(i + 0, j + 1, k + 3, Blocks.air, 0, 2);
		world.setBlock(i + 0, j + 1, k + 4, Blocks.air, 0, 2);
		world.setBlock(i + 0, j + 1, k + 5, Blocks.air, 0, 2);
		world.setBlock(i + 0, j + 1, k + 6, Blocks.air, 0, 2);
		world.setBlock(i + 0, j + 1, k + 7, Blocks.air, 0, 2);
		world.setBlock(i + 0, j + 2, k + 0, Blocks.air, 0, 2);
		world.setBlock(i + 0, j + 2, k + 1, Blocks.air, 0, 2);
		world.setBlock(i + 0, j + 2, k + 2, Blocks.air, 0, 2);
		world.setBlock(i + 0, j + 2, k + 3, Blocks.air, 0, 2);
		world.setBlock(i + 0, j + 2, k + 4, Blocks.air, 0, 2);
		world.setBlock(i + 0, j + 2, k + 5, Blocks.air, 0, 2);
		world.setBlock(i + 0, j + 2, k + 6, Blocks.air, 0, 2);
		world.setBlock(i + 0, j + 2, k + 7, Blocks.air, 0, 2);
		world.setBlock(i + 0, j + 3, k + 0, Blocks.air, 0, 2);
		world.setBlock(i + 0, j + 3, k + 1, Blocks.air, 0, 2);
		world.setBlock(i + 0, j + 3, k + 2, Blocks.air, 0, 2);
		world.setBlock(i + 0, j + 3, k + 3, Blocks.air, 0, 2);
		world.setBlock(i + 0, j + 3, k + 4, Blocks.air, 0, 2);
		world.setBlock(i + 0, j + 3, k + 5, Blocks.air, 0, 2);
		world.setBlock(i + 0, j + 3, k + 6, Blocks.air, 0, 2);
		world.setBlock(i + 0, j + 3, k + 7, Blocks.air, 0, 2);
		world.setBlock(i + 0, j + 4, k + 0, Blocks.air, 0, 2);
		world.setBlock(i + 0, j + 4, k + 1, Blocks.air, 0, 2);
		world.setBlock(i + 0, j + 4, k + 2, Blocks.air, 0, 2);
		world.setBlock(i + 0, j + 4, k + 3, Blocks.air, 0, 2);
		world.setBlock(i + 0, j + 4, k + 4, Blocks.air, 0, 2);
		world.setBlock(i + 0, j + 4, k + 5, Blocks.air, 0, 2);
		world.setBlock(i + 0, j + 4, k + 6, Blocks.air, 0, 2);
		world.setBlock(i + 0, j + 4, k + 7, Blocks.air, 0, 2);
		world.setBlock(i + 0, j + 5, k + 0, Blocks.air, 0, 2);
		world.setBlock(i + 0, j + 5, k + 1, Blocks.air, 0, 2);
		world.setBlock(i + 0, j + 5, k + 2, Blocks.air, 0, 2);
		world.setBlock(i + 0, j + 5, k + 3, Blocks.air, 0, 2);
		world.setBlock(i + 0, j + 5, k + 4, Blocks.air, 0, 2);
		world.setBlock(i + 0, j + 5, k + 5, Blocks.air, 0, 2);
		world.setBlock(i + 0, j + 5, k + 6, Blocks.air, 0, 2);
		world.setBlock(i + 0, j + 5, k + 7, Blocks.air, 0, 2);
		world.setBlock(i + 1, j + 0, k + 0, Blocks.nether_brick, 0, 2);
		world.setBlock(i + 1, j + 0, k + 1, Blocks.nether_brick, 0, 2);
		world.setBlock(i + 1, j + 0, k + 2, Blocks.nether_brick, 0, 2);
		world.setBlock(i + 1, j + 0, k + 3, Blocks.nether_brick, 0, 2);
		world.setBlock(i + 1, j + 0, k + 4, Blocks.nether_brick, 0, 2);
		world.setBlock(i + 1, j + 0, k + 5, Blocks.nether_brick, 0, 2);
		world.setBlock(i + 1, j + 0, k + 6, Blocks.nether_brick, 0, 2);
		world.setBlock(i + 1, j + 0, k + 7, Blocks.nether_brick, 0, 2);
		world.setBlock(i + 1, j + 1, k + 0, Blocks.air, 0, 2);
		world.setBlock(i + 1, j + 1, k + 1, Blocks.nether_brick, 0, 2);
		world.setBlock(i + 1, j + 1, k + 2, Blocks.nether_brick, 0, 2);
		world.setBlock(i + 1, j + 1, k + 3, Blocks.nether_brick, 0, 2);
		world.setBlock(i + 1, j + 1, k + 4, Blocks.nether_brick, 0, 2);
		world.setBlock(i + 1, j + 1, k + 5, Blocks.nether_brick, 0, 2);
		world.setBlock(i + 1, j + 1, k + 6, Blocks.nether_brick, 0, 2);
		world.setBlock(i + 1, j + 1, k + 7, Blocks.air, 0, 2);
		world.setBlock(i + 1, j + 2, k + 0, Blocks.air, 0, 2);
		world.setBlock(i + 1, j + 2, k + 1, Blocks.air, 0, 2);
		world.setBlock(i + 1, j + 2, k + 2, Blocks.air, 0, 2);
		world.setBlock(i + 1, j + 2, k + 3, Blocks.air, 0, 2);
		world.setBlock(i + 1, j + 2, k + 4, Blocks.air, 0, 2);
		world.setBlock(i + 1, j + 2, k + 5, Blocks.air, 0, 2);
		world.setBlock(i + 1, j + 2, k + 6, Blocks.air, 0, 2);
		world.setBlock(i + 1, j + 2, k + 7, Blocks.air, 0, 2);
		world.setBlock(i + 1, j + 3, k + 0, Blocks.air, 0, 2);
		world.setBlock(i + 1, j + 3, k + 1, Blocks.air, 0, 2);
		world.setBlock(i + 1, j + 3, k + 2, Blocks.air, 0, 2);
		world.setBlock(i + 1, j + 3, k + 3, Blocks.air, 0, 2);
		world.setBlock(i + 1, j + 3, k + 4, Blocks.air, 0, 2);
		world.setBlock(i + 1, j + 3, k + 5, Blocks.air, 0, 2);
		world.setBlock(i + 1, j + 3, k + 6, Blocks.air, 0, 2);
		world.setBlock(i + 1, j + 3, k + 7, Blocks.air, 0, 2);
		world.setBlock(i + 1, j + 4, k + 0, Blocks.air, 0, 2);
		world.setBlock(i + 1, j + 4, k + 1, Blocks.air, 0, 2);
		world.setBlock(i + 1, j + 4, k + 2, Blocks.air, 0, 2);
		world.setBlock(i + 1, j + 4, k + 3, Blocks.air, 0, 2);
		world.setBlock(i + 1, j + 4, k + 4, Blocks.air, 0, 2);
		world.setBlock(i + 1, j + 4, k + 5, Blocks.air, 0, 2);
		world.setBlock(i + 1, j + 4, k + 6, Blocks.air, 0, 2);
		world.setBlock(i + 1, j + 4, k + 7, Blocks.air, 0, 2);
		world.setBlock(i + 1, j + 5, k + 0, Blocks.air, 0, 2);
		world.setBlock(i + 1, j + 5, k + 1, Blocks.air, 0, 2);
		world.setBlock(i + 1, j + 5, k + 2, Blocks.air, 0, 2);
		world.setBlock(i + 1, j + 5, k + 3, Blocks.air, 0, 2);
		world.setBlock(i + 1, j + 5, k + 4, Blocks.air, 0, 2);
		world.setBlock(i + 1, j + 5, k + 5, Blocks.air, 0, 2);
		world.setBlock(i + 1, j + 5, k + 6, Blocks.air, 0, 2);
		world.setBlock(i + 1, j + 5, k + 7, Blocks.air, 0, 2);
		world.setBlock(i + 2, j + 0, k + 0, Blocks.nether_brick, 0, 2);
		world.setBlock(i + 2, j + 0, k + 1, Blocks.nether_brick, 0, 2);
		world.setBlock(i + 2, j + 0, k + 2, Blocks.nether_brick, 0, 2);
		world.setBlock(i + 2, j + 0, k + 3, Blocks.nether_brick, 0, 2);
		world.setBlock(i + 2, j + 0, k + 4, Blocks.nether_brick, 0, 2);
		world.setBlock(i + 2, j + 0, k + 5, Blocks.nether_brick, 0, 2);
		world.setBlock(i + 2, j + 0, k + 6, Blocks.nether_brick, 0, 2);
		world.setBlock(i + 2, j + 0, k + 7, Blocks.nether_brick, 0, 2);
		world.setBlock(i + 2, j + 1, k + 0, Blocks.air, 0, 2);
		world.setBlock(i + 2, j + 1, k + 1, Blocks.nether_brick, 0, 2);
		world.setBlock(i + 2, j + 1, k + 2, Blocks.obsidian, 0, 2);
		world.setBlock(i + 2, j + 1, k + 3, Blocks.obsidian, 0, 2);
		world.setBlock(i + 2, j + 1, k + 4, Blocks.air, 0, 2);
		world.setBlock(i + 2, j + 1, k + 5, Blocks.obsidian, 0, 2);
		world.setBlock(i + 2, j + 1, k + 6, Blocks.nether_brick, 0, 2);
		world.setBlock(i + 2, j + 1, k + 7, Blocks.air, 0, 2);
		world.setBlock(i + 2, j + 2, k + 0, Blocks.air, 0, 2);
		world.setBlock(i + 2, j + 2, k + 1, Blocks.air, 0, 2);
		world.setBlock(i + 2, j + 2, k + 2, Blocks.obsidian, 0, 2);
		world.setBlock(i + 2, j + 2, k + 3, Blocks.air, 0, 2);
		world.setBlock(i + 2, j + 2, k + 4, Blocks.air, 0, 2);
		world.setBlock(i + 2, j + 2, k + 5, Blocks.obsidian, 0, 2);
		world.setBlock(i + 2, j + 2, k + 6, Blocks.air, 0, 2);
		world.setBlock(i + 2, j + 2, k + 7, Blocks.air, 0, 2);
		world.setBlock(i + 2, j + 3, k + 0, Blocks.air, 0, 2);
		world.setBlock(i + 2, j + 3, k + 1, Blocks.air, 0, 2);
		world.setBlock(i + 2, j + 3, k + 2, Blocks.obsidian, 0, 2);
		world.setBlock(i + 2, j + 3, k + 3, Blocks.air, 0, 2);
		world.setBlock(i + 2, j + 3, k + 4, Blocks.air, 0, 2);
		world.setBlock(i + 2, j + 3, k + 5, Blocks.obsidian, 0, 2);
		world.setBlock(i + 2, j + 3, k + 6, Blocks.air, 0, 2);
		world.setBlock(i + 2, j + 3, k + 7, Blocks.air, 0, 2);
		world.setBlock(i + 2, j + 4, k + 0, Blocks.air, 0, 2);
		world.setBlock(i + 2, j + 4, k + 1, Blocks.air, 0, 2);
		world.setBlock(i + 2, j + 4, k + 2, Blocks.obsidian, 0, 2);
		world.setBlock(i + 2, j + 4, k + 3, Blocks.air, 0, 2);
		world.setBlock(i + 2, j + 4, k + 4, Blocks.air, 0, 2);
		world.setBlock(i + 2, j + 4, k + 5, Blocks.air, 0, 2);
		world.setBlock(i + 2, j + 4, k + 6, Blocks.air, 0, 2);
		world.setBlock(i + 2, j + 4, k + 7, Blocks.air, 0, 2);
		world.setBlock(i + 2, j + 5, k + 0, Blocks.air, 0, 2);
		world.setBlock(i + 2, j + 5, k + 1, Blocks.air, 0, 2);
		world.setBlock(i + 2, j + 5, k + 2, Blocks.obsidian, 0, 2);
		world.setBlock(i + 2, j + 5, k + 3, Blocks.obsidian, 0, 2);
		world.setBlock(i + 2, j + 5, k + 4, Blocks.air, 0, 2);
		world.setBlock(i + 2, j + 5, k + 5, Blocks.air, 0, 2);
		world.setBlock(i + 2, j + 5, k + 6, Blocks.air, 0, 2);
		world.setBlock(i + 2, j + 5, k + 7, Blocks.air, 0, 2);
		world.setBlock(i + 3, j + 0, k + 0, Blocks.nether_brick, 0, 2);
		world.setBlock(i + 3, j + 0, k + 1, Blocks.nether_brick, 0, 2);
		world.setBlock(i + 3, j + 0, k + 2, Blocks.nether_brick, 0, 2);
		world.setBlock(i + 3, j + 0, k + 3, Blocks.nether_brick, 0, 2);
		world.setBlock(i + 3, j + 0, k + 4, Blocks.nether_brick, 0, 2);
		world.setBlock(i + 3, j + 0, k + 5, Blocks.nether_brick, 0, 2);
		world.setBlock(i + 3, j + 0, k + 6, Blocks.nether_brick, 0, 2);
		world.setBlock(i + 3, j + 0, k + 7, Blocks.nether_brick, 0, 2);
		world.setBlock(i + 3, j + 1, k + 0, Blocks.air, 0, 2);
		world.setBlock(i + 3, j + 1, k + 1, Blocks.nether_brick, 0, 2);
		world.setBlock(i + 3, j + 1, k + 2, Blocks.nether_brick, 0, 2);
		world.setBlock(i + 3, j + 1, k + 3, Blocks.nether_brick, 0, 2);
		world.setBlock(i + 3, j + 1, k + 4, Blocks.nether_brick, 0, 2);
		world.setBlock(i + 3, j + 1, k + 5, Blocks.nether_brick, 0, 2);
		world.setBlock(i + 3, j + 1, k + 6, Blocks.nether_brick, 0, 2);
		world.setBlock(i + 3, j + 1, k + 7, Blocks.air, 0, 2);
		world.setBlock(i + 3, j + 2, k + 0, Blocks.air, 0, 2);
		world.setBlock(i + 3, j + 2, k + 1, Blocks.air, 0, 2);
		world.setBlock(i + 3, j + 2, k + 2, Blocks.air, 0, 2);
		world.setBlock(i + 3, j + 2, k + 3, Blocks.air, 0, 2);
		world.setBlock(i + 3, j + 2, k + 4, Blocks.air, 0, 2);
		world.setBlock(i + 3, j + 2, k + 5, Blocks.air, 0, 2);
		world.setBlock(i + 3, j + 2, k + 6, Blocks.air, 0, 2);
		world.setBlock(i + 3, j + 2, k + 7, Blocks.air, 0, 2);
		world.setBlock(i + 3, j + 3, k + 0, Blocks.air, 0, 2);
		world.setBlock(i + 3, j + 3, k + 1, Blocks.air, 0, 2);
		world.setBlock(i + 3, j + 3, k + 2, Blocks.air, 0, 2);
		world.setBlock(i + 3, j + 3, k + 3, Blocks.air, 0, 2);
		world.setBlock(i + 3, j + 3, k + 4, Blocks.air, 0, 2);
		world.setBlock(i + 3, j + 3, k + 5, Blocks.air, 0, 2);
		world.setBlock(i + 3, j + 3, k + 6, Blocks.air, 0, 2);
		world.setBlock(i + 3, j + 3, k + 7, Blocks.air, 0, 2);
		world.setBlock(i + 3, j + 4, k + 0, Blocks.air, 0, 2);
		world.setBlock(i + 3, j + 4, k + 1, Blocks.air, 0, 2);
		world.setBlock(i + 3, j + 4, k + 2, Blocks.air, 0, 2);
		world.setBlock(i + 3, j + 4, k + 3, Blocks.air, 0, 2);
		world.setBlock(i + 3, j + 4, k + 4, Blocks.air, 0, 2);
		world.setBlock(i + 3, j + 4, k + 5, Blocks.air, 0, 2);
		world.setBlock(i + 3, j + 4, k + 6, Blocks.air, 0, 2);
		world.setBlock(i + 3, j + 4, k + 7, Blocks.air, 0, 2);
		world.setBlock(i + 3, j + 5, k + 0, Blocks.air, 0, 2);
		world.setBlock(i + 3, j + 5, k + 1, Blocks.air, 0, 2);
		world.setBlock(i + 3, j + 5, k + 2, Blocks.air, 0, 2);
		world.setBlock(i + 3, j + 5, k + 3, Blocks.air, 0, 2);
		world.setBlock(i + 3, j + 5, k + 4, Blocks.air, 0, 2);
		world.setBlock(i + 3, j + 5, k + 5, Blocks.air, 0, 2);
		world.setBlock(i + 3, j + 5, k + 6, Blocks.air, 0, 2);
		world.setBlock(i + 3, j + 5, k + 7, Blocks.air, 0, 2);
		world.setBlock(i + 4, j + 0, k + 0, Blocks.nether_brick, 0, 2);
		world.setBlock(i + 4, j + 0, k + 1, Blocks.nether_brick, 0, 2);
		world.setBlock(i + 4, j + 0, k + 2, Blocks.nether_brick, 0, 2);
		world.setBlock(i + 4, j + 0, k + 3, Blocks.nether_brick, 0, 2);
		world.setBlock(i + 4, j + 0, k + 4, Blocks.nether_brick, 0, 2);
		world.setBlock(i + 4, j + 0, k + 5, Blocks.nether_brick, 0, 2);
		world.setBlock(i + 4, j + 0, k + 6, Blocks.nether_brick, 0, 2);
		world.setBlock(i + 4, j + 0, k + 7, Blocks.nether_brick, 0, 2);
		world.setBlock(i + 4, j + 1, k + 0, Blocks.air, 0, 2);
		world.setBlock(i + 4, j + 1, k + 1, Blocks.air, 0, 2);
		world.setBlock(i + 4, j + 1, k + 2, Blocks.air, 0, 2);
		world.setBlock(i + 4, j + 1, k + 3, Blocks.air, 0, 2);
		world.setBlock(i + 4, j + 1, k + 4, Blocks.air, 0, 2);
		world.setBlock(i + 4, j + 1, k + 5, Blocks.air, 0, 2);
		world.setBlock(i + 4, j + 1, k + 6, Blocks.air, 0, 2);
		world.setBlock(i + 4, j + 1, k + 7, Blocks.air, 0, 2);
		world.setBlock(i + 4, j + 2, k + 0, Blocks.air, 0, 2);
		world.setBlock(i + 4, j + 2, k + 1, Blocks.air, 0, 2);
		world.setBlock(i + 4, j + 2, k + 2, Blocks.air, 0, 2);
		world.setBlock(i + 4, j + 2, k + 3, Blocks.air, 0, 2);
		world.setBlock(i + 4, j + 2, k + 4, Blocks.air, 0, 2);
		world.setBlock(i + 4, j + 2, k + 5, Blocks.air, 0, 2);
		world.setBlock(i + 4, j + 2, k + 6, Blocks.air, 0, 2);
		world.setBlock(i + 4, j + 2, k + 7, Blocks.air, 0, 2);
		world.setBlock(i + 4, j + 3, k + 0, Blocks.air, 0, 2);
		world.setBlock(i + 4, j + 3, k + 1, Blocks.air, 0, 2);
		world.setBlock(i + 4, j + 3, k + 2, Blocks.air, 0, 2);
		world.setBlock(i + 4, j + 3, k + 3, Blocks.air, 0, 2);
		world.setBlock(i + 4, j + 3, k + 4, Blocks.air, 0, 2);
		world.setBlock(i + 4, j + 3, k + 5, Blocks.air, 0, 2);
		world.setBlock(i + 4, j + 3, k + 6, Blocks.air, 0, 2);
		world.setBlock(i + 4, j + 3, k + 7, Blocks.air, 0, 2);
		world.setBlock(i + 4, j + 4, k + 0, Blocks.air, 0, 2);
		world.setBlock(i + 4, j + 4, k + 1, Blocks.air, 0, 2);
		world.setBlock(i + 4, j + 4, k + 2, Blocks.air, 0, 2);
		world.setBlock(i + 4, j + 4, k + 3, Blocks.air, 0, 2);
		world.setBlock(i + 4, j + 4, k + 4, Blocks.air, 0, 2);
		world.setBlock(i + 4, j + 4, k + 5, Blocks.air, 0, 2);
		world.setBlock(i + 4, j + 4, k + 6, Blocks.air, 0, 2);
		world.setBlock(i + 4, j + 4, k + 7, Blocks.air, 0, 2);
		world.setBlock(i + 4, j + 5, k + 0, Blocks.air, 0, 2);
		world.setBlock(i + 4, j + 5, k + 1, Blocks.air, 0, 2);
		world.setBlock(i + 4, j + 5, k + 2, Blocks.air, 0, 2);
		world.setBlock(i + 4, j + 5, k + 3, Blocks.air, 0, 2);
		world.setBlock(i + 4, j + 5, k + 4, Blocks.air, 0, 2);
		world.setBlock(i + 4, j + 5, k + 5, Blocks.air, 0, 2);
		world.setBlock(i + 4, j + 5, k + 6, Blocks.air, 0, 2);
		world.setBlock(i + 4, j + 5, k + 7, Blocks.air, 0, 2);
		
		return true;
	}
}