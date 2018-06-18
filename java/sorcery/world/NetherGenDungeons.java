package sorcery.world;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import sorcery.core.Sorcery;
import sorcery.lib.SorceryItems;
import sorcery.lib.SorcerySpells;
import sorcery.lib.SpellHelper;

public class NetherGenDungeons extends WorldGenerator {
	protected Block[] GetValidSpawnBlocks() {
		return new Block[]{Blocks.nether_brick, Blocks.netherrack, Blocks.soul_sand, Blocks.gravel, Sorcery.arcaneblock, Blocks.fire, Blocks.brown_mushroom, Blocks.red_mushroom};
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
	
	public NetherGenDungeons() {
	}
	
	public boolean generate(World world, Random rand, int i, int j, int k) {
		if(!LocationIsValidSpawn(world, i, j, k) || !LocationIsValidSpawn(world, i + 2, j, k) || !LocationIsValidSpawn(world, i + 2, j, k + 2) || !LocationIsValidSpawn(world, i, j, k + 2)) {
			return false;
		}
		
		world.setBlock(i + 0, j, k + 0, Blocks.nether_brick, 0, 2);
		world.setBlock(i + 0, j, k + 1, Blocks.nether_brick, 0, 2);
		world.setBlock(i + 0, j, k + 2, Blocks.nether_brick, 0, 2);
		world.setBlock(i + 1, j, k + 0, Blocks.nether_brick, 0, 2);
		world.setBlock(i + 1, j, k + 1, Blocks.nether_brick, 0, 2);
		world.setBlock(i + 1, j, k + 2, Blocks.nether_brick, 0, 2);
		world.setBlock(i + 2, j, k + 0, Blocks.nether_brick, 0, 2);
		world.setBlock(i + 2, j, k + 1, Blocks.nether_brick, 0, 2);
		world.setBlock(i + 2, j, k + 2, Blocks.nether_brick, 0, 2);
		
		world.setBlock(i + 1, j + 1, k + 1, Blocks.chest, 3, 2);
		TileEntityChest tile = (TileEntityChest)world.getTileEntity(i + 1, j + 1, k + 1);
		
		if(tile != null) {
			for(int in = 0; in < 12; ++in) {
				ItemStack item = this.pickCheckLootItem(rand);
				
				if(item != null) {
					tile.setInventorySlotContents(rand.nextInt(tile.getSizeInventory()), item);
				}
			}
		}
		
		world.setBlock(i + 1, j - 1, k + 1, Blocks.mob_spawner, 0, 2);
		TileEntityMobSpawner mobTile = (TileEntityMobSpawner)world.getTileEntity(i + 1, j - 1, k + 1);
		
		if(mobTile != null) {
			int randInt = rand.nextInt(3);
			
			switch(randInt) {
				case 0:
					mobTile.func_145881_a().setEntityName("Shade");
					break;
				case 1:
					mobTile.func_145881_a().setEntityName("Skeleton");
					break;
				case 2:
					mobTile.func_145881_a().setEntityName("Blaze");
			}
		} else {
			System.err.println("Failed to fetch mob spawner entity at (" + i + 1 + ", " + j + 1 + ", " + k + 1 + ")");
		}
		return true;
	}
	
	private ItemStack pickCheckLootItem(Random par1Random) {
		int var2 = par1Random.nextInt(12);
		switch(var2) {
			case 0:
				return new ItemStack(SorceryItems.ectoplasm, 2);
			case 1:
				return null;
			case 2:
				int randInt = par1Random.nextInt(5);
				if(randInt == 1)
					return new ItemStack(Items.skull, 1, 1);
			case 3:
				return new ItemStack(SorceryItems.magicDust, 4);
			case 4:
				return new ItemStack(Items.golden_apple, 1);
			case 5:
				return new ItemStack(Items.nether_wart, 2);
			case 6:
				int randInt2 = par1Random.nextInt(5);
				if(randInt2 == 1)
					return new ItemStack(SorceryItems.phoenixegg, 1);
			case 7:
				return new ItemStack(SorceryItems.gemOnyx, 1);
			case 8:
				return new ItemStack(Items.blaze_rod, 1);
			case 9:
				return new ItemStack(Items.experience_bottle, 3);
			case 10:
				return new ItemStack(SorceryItems.bucketLiquidMagic, 1);
			case 11:
				int randInt3 = par1Random.nextInt(3);
				//if(randInt3 == 1)
					//return SpellHelper.instance.getPageFromSpellIDs(SorcerySpells.explode.spellID);
		}
		return null;
	}
}