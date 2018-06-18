package sorcery.core;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import sorcery.lib.Properties;
import sorcery.world.NetherGenAbandonedNetherPortal1;
import sorcery.world.NetherGenAbandonedNetherPortal2;
import sorcery.world.NetherGenAbandonedNetherPortal3;
import sorcery.world.NetherGenAbandonedNetherPortal4;
import sorcery.world.NetherGenDungeons;
import sorcery.world.NetherGenLavaHut;
import sorcery.world.WorldGenTestificateHead;
import cpw.mods.fml.common.IWorldGenerator;

public class WorldGen implements IWorldGenerator {
	private WorldGenerator silverGen = new WorldGenMinable(Sorcery.arcaneblock, 0, 8, Blocks.stone);
	private WorldGenerator tinGen = new WorldGenMinable(Sorcery.arcaneblock, 13, 8, Blocks.stone);
	private WorldGenerator copperGen = new WorldGenMinable(Sorcery.arcaneblock, 14, 8, Blocks.stone);
	private WorldGenerator elementalGen = new WorldGenMinable(Sorcery.arcaneblock, 12, 8, Blocks.stone);
	private WorldGenerator netherriteGen = new WorldGenMinable(Sorcery.arcaneblock, 1, 6, Blocks.netherrack);
	private WorldGenerator onyxGen = new WorldGenMinable(Sorcery.arcaneblock, 2, 4,  Blocks.netherrack);
	private WorldGenerator inferniteGen = new WorldGenMinable(Sorcery.arcaneblock, 3, 4,  Blocks.netherrack);
	
	private WorldGenerator limestoneGen = new WorldGenMinable(Sorcery.arcaneblock, 15, 24, Blocks.stone);
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		switch(world.provider.dimensionId) {
			case -1:
				generateNether(world, random, chunkX * 16, chunkZ * 16);
			case 0:
				generateSurface(world, random, chunkX * 16, chunkZ * 16);
			case 1:
				generateEnd(world, random, chunkX * 16, chunkZ * 16);
		}
	}
	
	public void generateSurface(World world, Random random, int chunkX, int chunkZ) {
		if(Properties.oreGenSilver) {
			for(int i = 0; i < 4; i++) {
				int randPosX = chunkX + random.nextInt(16);
				int randPosY = random.nextInt(32);
				int randPosZ = chunkZ + random.nextInt(16);
				this.silverGen.generate(world, random, randPosX, randPosY, randPosZ);
			}
		}
		
		if(Properties.oreGenTin) {
			for(int i = 0; i < 8; i++) {
				int randPosX = chunkX + random.nextInt(16);
				int randPosY = random.nextInt(40);
				int randPosZ = chunkZ + random.nextInt(16);
				this.tinGen.generate(world, random, randPosX, randPosY, randPosZ);
			}
		}
		
		if(Properties.oreGenCopper) {
			for(int i = 0; i < 8; i++) {
				int randPosX = chunkX + random.nextInt(16);
				int randPosY = random.nextInt(48);
				int randPosZ = chunkZ + random.nextInt(16);
				this.copperGen.generate(world, random, randPosX, randPosY, randPosZ);
			}
		}
		
		for(int i = 0; i < 4; i++) {
			int randPosX = chunkX + random.nextInt(16);
			int randPosY = random.nextInt(24);
			int randPosZ = chunkZ + random.nextInt(16);
			this.elementalGen.generate(world, random, randPosX, randPosY, randPosZ);
		}
		
		for(int i = 0; i < 1; i++) {
			int randPosX = chunkX + random.nextInt(16);
			int randPosY = random.nextInt(64);
			int randPosZ = chunkZ + random.nextInt(16);
			this.limestoneGen.generate(world, random, randPosX, randPosY, randPosZ);
		}
		
		if(world.getWorldInfo().isMapFeaturesEnabled()) {
			generateTestificateHead(chunkX, chunkZ, world, random);
		}
		
	}
	
	public void generateNether(World world, Random random, int chunkX, int chunkZ) {
		if(Properties.oreGenNetherrite) {
			for(int i = 0; i < 15; i++) {
				int randPosX = chunkX + random.nextInt(16);
				int randPosY = random.nextInt(128);
				int randPosZ = chunkZ + random.nextInt(16);
				this.netherriteGen.generate(world, random, randPosX, randPosY, randPosZ);
			}
		}
		if(Properties.oreGenOnyx) {
			for(int i = 0; i < 6; i++) {
				int randPosX = chunkX + random.nextInt(16);
				int randPosY = random.nextInt(128);
				int randPosZ = chunkZ + random.nextInt(16);
				this.onyxGen.generate(world, random, randPosX, randPosY, randPosZ);
			}
		}
		if(Properties.oreGenInfernite) {
			for(int i = 0; i < 2; i++) {
				int randPosX = chunkX + random.nextInt(16);
				int randPosY = random.nextInt(128);
				int randPosZ = chunkZ + random.nextInt(16);
				this.inferniteGen.generate(world, random, randPosX, randPosY, randPosZ);
			}
		}
		
		if(world.getWorldInfo().isMapFeaturesEnabled()) {
			generateAbandonedNetherPortal(chunkX, chunkZ, world, random);
		}
		
		if(world.getWorldInfo().isMapFeaturesEnabled()) {
			generateNetherDungeon(chunkX, chunkZ, world, random);
		}
		
		if(world.getWorldInfo().isMapFeaturesEnabled()) {
			generateNetherHut(chunkX, chunkZ, world, random);
		}
	}
	
	public void generateEnd(World world, Random random, int chunkX, int chunkZ) {
		
	}
	
	private void generateAbandonedNetherPortal(int chunkX, int chunkZ, World world, Random random) {
		int randPosX = chunkX + random.nextInt(16);
		int randPosY = random.nextInt(128);
		int randPosZ = chunkZ + random.nextInt(16);
		int portalToGen = random.nextInt(4);
		int chanceToGenerate = random.nextInt(3);
		if(chanceToGenerate == 1) {
			switch(portalToGen) {
				case 0:
					(new NetherGenAbandonedNetherPortal1()).generate(world, random, randPosX, randPosY, randPosZ);
					break;
				case 1:
					(new NetherGenAbandonedNetherPortal2()).generate(world, random, randPosX, randPosY, randPosZ);
					break;
				case 2:
					(new NetherGenAbandonedNetherPortal3()).generate(world, random, randPosX, randPosY, randPosZ);
					break;
				case 3:
					(new NetherGenAbandonedNetherPortal4()).generate(world, random, randPosX, randPosY, randPosZ);
					break;
			}
		}
	}
	
	private void generateNetherDungeon(int chunkX, int chunkZ, World world, Random random) {
		int randPosX = chunkX + random.nextInt(16);
		int randPosY = random.nextInt(128);
		int randPosZ = chunkZ + random.nextInt(16);
		int chanceToGenerate = random.nextInt(4);
		if(chanceToGenerate == 1) {
			(new NetherGenDungeons()).generate(world, random, randPosX, randPosY, randPosZ);
		}
	}
	
	private void generateNetherHut(int chunkX, int chunkZ, World world, Random random) {
		int randPosX = chunkX + random.nextInt(16);
		int randPosY = random.nextInt(128);
		int randPosZ = chunkZ + random.nextInt(16);
		int chanceToGenerate = random.nextInt(2);
		if(chanceToGenerate == 1) {
			(new NetherGenLavaHut()).generate(world, random, randPosX, randPosY, randPosZ);
		}
	}
	
	private WorldGenerator testificateHeadGen = new WorldGenTestificateHead();
	private void generateTestificateHead(int chunkX, int chunkZ, World world, Random random) {
		int randPosX = chunkX + random.nextInt(16);
		int randPosY = random.nextInt(256);
		int randPosZ = chunkZ + random.nextInt(16);
		int chanceToGenerate = random.nextInt(30);
		if(chanceToGenerate == 1) {
			this.testificateHeadGen.generate(world, random, randPosX, randPosY, randPosZ);
		}
	}
}
