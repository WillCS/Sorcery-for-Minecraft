package sorcery.world;

import java.util.Random;

import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenTallGrass;
import net.minecraft.world.gen.feature.WorldGenerator;
import sorcery.entities.EntityUnicorn;
import sorcery.entities.EntityUnicow;
import sorcery.lib.utils.Utils;

public class BiomeGenEnchantedForest extends BiomeGenBase {
	public BiomeGenEnchantedForest(int par1) {
		super(par1);
		this.spawnableCreatureList.clear();
		
		this.spawnableCreatureList.add(new SpawnListEntry(EntityUnicorn.class, 5, 2, 6));
		this.spawnableCreatureList.add(new SpawnListEntry(EntityUnicow.class, 8, 4, 4));
		this.spawnableCreatureList.add(new SpawnListEntry(EntityChicken.class, 8, 4, 4));
		this.spawnableCreatureList.add(new SpawnListEntry(EntityCow.class, 8, 4, 4));
		this.spawnableCreatureList.add(new SpawnListEntry(EntityMooshroom.class, 1, 4, 4));
		
		this.spawnableMonsterList.add(new SpawnListEntry(EntitySpider.class, 10, 4, 4));
		this.spawnableMonsterList.add(new SpawnListEntry(EntityZombie.class, 10, 4, 4));
		this.spawnableMonsterList.add(new SpawnListEntry(EntitySkeleton.class, 10, 4, 4));
		this.spawnableMonsterList.add(new SpawnListEntry(EntityCreeper.class, 10, 4, 4));
		this.spawnableMonsterList.add(new SpawnListEntry(EntitySlime.class, 10, 4, 4));
		this.spawnableMonsterList.add(new SpawnListEntry(EntityEnderman.class, 1, 1, 4));
		this.spawnableWaterCreatureList.add(new SpawnListEntry(EntitySquid.class, 10, 4, 4));
		this.spawnableCaveCreatureList.add(new SpawnListEntry(EntityBat.class, 10, 8, 8));
		
		this.theBiomeDecorator.treesPerChunk = 5;
		this.theBiomeDecorator.grassPerChunk = 5;
		this.theBiomeDecorator.bigMushroomsPerChunk = 1;
		this.theBiomeDecorator.flowersPerChunk = 15;
		this.theBiomeDecorator.mushroomsPerChunk = 5;
		this.theBiomeDecorator.generateLakes = false;
		this.setHeight(new Height(-0.1F, 0.1F));
	}
	
	public WorldGenerator getRandomWorldGenForTrees(Random par1Random) {
		if(par1Random.nextInt(3) == 0)
			return this.worldGeneratorTrees;
		return new WorldGenMagicTrees(false, 7);
	}
	
	public WorldGenerator getRandomWorldGenForGrass(Random par1Random) {
		return par1Random.nextInt(4) == 0 ? new WorldGenTallGrass(Blocks.tallgrass, 2) : new WorldGenTallGrass(Blocks.tallgrass, 1);
	}
	
	public int getBiomeGrassColor() {
		return Utils.encodeColour(19F / 255F, 223F / 255F, 180F / 255F, 1F);
	}
	
	public int getBiomeFoliageColor() {
		return Utils.encodeColour(33F / 255F, 157F / 255F, 255F / 255F, 1F);
	}
	
	public int getWaterColorMultiplier() {
		return Utils.encodeColour(255F / 255, 0.7F, 155F / 255F, 1F);
	}
	
	public BiomeDecorator createBiomeDecorator() {
		return getModdedBiomeDecorator(new BiomeDecorator());
	}
}
