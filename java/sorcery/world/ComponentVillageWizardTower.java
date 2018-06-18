package sorcery.world;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePieces;
import sorcery.items.ItemMysteryBook;
import sorcery.lib.Properties;
import sorcery.lib.SorceryItems;

public class ComponentVillageWizardTower extends StructureVillagePieces.Village{
	private int averageGroundLevel = -1;
    private int villagersSpawned;
	
	public static WeightedRandomChestContent[] villageTowerChestContents = new WeightedRandomChestContent[] {
			new WeightedRandomChestContent(SorceryItems.energy, 0, 3, 6, 6),
			new WeightedRandomChestContent(SorceryItems.goldegg, 0, 1, 2, 2),
			new WeightedRandomChestContent(SorceryItems.magicDust, 0, 3, 6, 5),
			new WeightedRandomChestContent(SorceryItems.ectoplasm, 0, 3, 6, 6),
			new WeightedRandomChestContent(Items.bread, 0, 1, 3, 8),
			new WeightedRandomChestContent(Items.apple, 0, 1, 3, 7),
			new WeightedRandomChestContent(Items.cookie, 0, 5, 10, 6),
			new WeightedRandomChestContent(new ItemBlock(Blocks.sapling), 0, 3, 7, 5),
			new WeightedRandomChestContent(SorceryItems.dustyBook, 0, 1, 1, 5),
			new WeightedRandomChestContent(Items.skull, 0, 1, 1, 4)};
	
	public ComponentVillageWizardTower() {}
	
	public ComponentVillageWizardTower(StructureVillagePieces.Start par1ComponentVillageStartPiece, int par2, Random par3Random, StructureBoundingBox par4StructureBoundingBox, int par5) {
		super(par1ComponentVillageStartPiece, par2);
		this.coordBaseMode = par5;
		this.boundingBox = par4StructureBoundingBox;
	}
	
	public static ComponentVillageWizardTower buildComponent(StructureVillagePieces.Start par0ComponentVillageStartPiece, List par1List, Random par2Random, int par3, int par4, int par5, int par6, int par7) {
		StructureBoundingBox var8 = StructureBoundingBox.getComponentToAddBoundingBox(par3, par4, par5, 0, 0, 0, 12, 17, 6, par6);
		return canVillageGoDeeper(var8) && StructureComponent.findIntersecting(par1List, var8) == null ? new ComponentVillageWizardTower(par0ComponentVillageStartPiece, par7, par2Random, var8, par6) : null;
	}
	
	@Override
	public boolean addComponentParts(World world, Random rand, StructureBoundingBox box) {
		if(this.averageGroundLevel < 0) {
			this.averageGroundLevel = this.getAverageGroundLevel(world, box);
			
			if(this.averageGroundLevel < 0) {
				return true;
			}
			
			this.boundingBox.offset(0, this.averageGroundLevel - this.boundingBox.maxY + 12 - 1, 0);
		}
		
		this.fillWithBlocks(world, box, 0, 4, 0, 11, 8, 5, Blocks.cobblestone, Blocks.air, false);
		this.fillWithBlocks(world, box, 1, 5, 1, 10, 5, 4, Blocks.planks, Blocks.cobblestone, false);
		this.fillWithBlocks(world, box, 1, 7, 0, 10, 8, 5, Blocks.planks, Blocks.cobblestone, false);
		this.fillWithBlocks(world, box, 7, 5, 3, 10, 5, 4, Blocks.double_stone_slab, Blocks.planks, false);
		this.fillWithBlocks(world, box, 0, 7, 1, 11, 7, 4, Blocks.log, Blocks.cobblestone, false);
		this.fillWithBlocks(world, box, 0, 7, 2, 11, 7, 3, Blocks.glass_pane, Blocks.planks, false);
		this.fillWithAir(world, box, 1, 6, 1, 10, 9, 4);
		this.fillWithBlocks(world, box, 1, 9, 2, 1, 9, 3, Blocks.bookshelf, Blocks.planks, false);
		this.fillWithBlocks(world, box, 4, 10, 2, 11, 10, 3, Blocks.planks, Blocks.cobblestone, false);
		this.fillWithBlocks(world, box, 8, 6, 4, 10, 6, 4, Blocks.double_stone_slab, Blocks.planks, false);
		this.fillWithBlocks(world, box, 1, 6, 1, 1, 9, 1, Blocks.bookshelf, Blocks.planks, false);
		this.fillWithBlocks(world, box, 1, 6, 4, 1, 9, 4, Blocks.bookshelf, Blocks.planks, false);
		this.fillWithBlocks(world, box, 2, 6, 1, 2, 9, 1, Blocks.planks, Blocks.planks, false);
		this.fillWithBlocks(world, box, 2, 6, 4, 2, 9, 4, Blocks.planks, Blocks.planks, false);
		this.fillWithBlocks(world, box, 3, 6, 1, 4, 6, 1, Blocks.bookshelf, Blocks.planks, false);
		this.fillWithBlocks(world, box, 3, 7, 0, 4, 7, 0, Blocks.glass_pane, Blocks.planks, false);
		this.fillWithBlocks(world, box, 4, 6, 4, 4, 9, 4, Blocks.planks, Blocks.planks, false);
		this.fillWithBlocks(world, box, 0, 9, 1, 0, 10, 4, Blocks.planks, Blocks.oak_stairs, false);
		this.fillWithBlocks(world, box, 11, 9, 1, 11, 10, 4, Blocks.planks, Blocks.oak_stairs, false);
		
		int var4 = this.getMetadataWithOffset(Blocks.oak_stairs, 3);
		int var5 = this.getMetadataWithOffset(Blocks.oak_stairs, 2);
		int var6;
		int var7;
		
		for(var6 = -1; var6 <= 2; ++var6) {
			for(var7 = 0; var7 <= 11; ++var7) {
				this.placeBlockAtCurrentPosition(world, Blocks.oak_stairs, var4, var7, 9 + var6, var6, box);
				this.placeBlockAtCurrentPosition(world, Blocks.oak_stairs, var5, var7, 9 + var6, 5 - var6, box);
			}
		}
		
		this.fillWithBlocks(world, box, 1, 10, 1, 6, 10, 4, Blocks.planks, Blocks.oak_stairs, false);
		this.fillWithBlocks(world, box, 1, 15, 0, 6, 15, 5, Blocks.fence, Blocks.cobblestone, false);
		this.fillWithBlocks(world, box, 2, 11, 1, 5, 16, 4, Blocks.cobblestone, Blocks.planks, false);
		this.fillWithBlocks(world, box, 1, 16, 0, 6, 19, 5, Blocks.cobblestone, Blocks.planks, false);
		this.fillWithBlocks(world, box, 1, 17, 2, 6, 17, 3, Blocks.glass_pane, Blocks.cobblestone, false);
		this.fillWithBlocks(world, box, 3, 17, 0, 4, 17, 5, Blocks.glass_pane, Blocks.cobblestone, false);
		this.fillWithBlocks(world, box, 1, 19, 1, 1, 20, 4, Blocks.cobblestone, Blocks.cobblestone, false);
		this.fillWithBlocks(world, box, 6, 19, 1, 6, 20, 4, Blocks.cobblestone, Blocks.cobblestone, false);
		this.fillWithBlocks(world, box, 1, 17, 0, 1, 18, 0, Blocks.log, Blocks.cobblestone, false);
		this.fillWithBlocks(world, box, 1, 17, 5, 1, 18, 5, Blocks.log, Blocks.cobblestone, false);
		this.fillWithBlocks(world, box, 6, 17, 0, 6, 18, 0, Blocks.log, Blocks.cobblestone, false);
		this.fillWithBlocks(world, box, 6, 17, 5, 6, 18, 5, Blocks.log, Blocks.cobblestone, false);
		this.fillWithBlocks(world, box, 1, 16, 0, 1, 16, 0, Blocks.fence, Blocks.cobblestone, false);
		this.fillWithBlocks(world, box, 1, 16, 5, 1, 16, 5, Blocks.fence, Blocks.cobblestone, false);
		this.fillWithBlocks(world, box, 6, 16, 0, 6, 16, 0, Blocks.fence, Blocks.cobblestone, false);
		this.fillWithBlocks(world, box, 6, 16, 5, 6, 16, 5, Blocks.fence, Blocks.cobblestone, false);
		this.fillWithBlocks(world, box, 1, 11, 1, 1, 15, 1, Blocks.fence, Blocks.cobblestone, false);
		this.fillWithBlocks(world, box, 1, 11, 4, 1, 15, 4, Blocks.fence, Blocks.cobblestone, false);
		this.fillWithBlocks(world, box, 6, 11, 1, 6, 15, 1, Blocks.fence, Blocks.cobblestone, false);
		this.fillWithBlocks(world, box, 6, 11, 4, 6, 15, 4, Blocks.fence, Blocks.cobblestone, false);
		this.fillWithAir(world, box, 3, 10, 2, 4, 14, 3);
		this.fillWithAir(world, box, 2, 16, 1, 5, 19, 4);
		this.fillWithBlocks(world, box, 3, 15, 2, 4, 15, 3, Blocks.cobblestone, Blocks.planks, false);
		
		int ladderID = this.getMetadataWithOffset(Blocks.ladder, 3);
		
		for(var5 = 6; var5 <= 15; ++var5) {
			this.placeBlockAtCurrentPosition(world, Blocks.ladder, ladderID, 4, var5, 3, box);
		}
		
		int var8 = this.getMetadataWithOffset(Blocks.oak_stairs, 3);
		int var9 = this.getMetadataWithOffset(Blocks.oak_stairs, 2);
		int var10;
		int var11;
		
		for(var10 = 0; var10 <= 2; ++var10) {
			for(var11 = 1; var11 <= 6; ++var11) {
				this.placeBlockAtCurrentPosition(world, Blocks.stone_stairs, var8, var11, 19 + var10, var10, box);
				this.placeBlockAtCurrentPosition(world, Blocks.stone_stairs, var9, var11, 19 + var10, 5 - var10, box);
			}
		}
		
		this.fillWithBlocks(world, box, 2, 18, 1, 2, 18, 1, Blocks.torch, Blocks.planks, false);
		this.fillWithBlocks(world, box, 5, 18, 4, 5, 18, 4, Blocks.torch, Blocks.planks, false);
		this.fillWithBlocks(world, box, 3, 8, 1, 3, 8, 1, Blocks.torch, Blocks.planks, false);
		this.fillWithBlocks(world, box, 9, 8, 1, 9, 8, 1, Blocks.torch, Blocks.planks, false);
		
		this.generateStructureChestContents(world, box, rand, 3, 6, 4, villageTowerChestContents, 5 + rand.nextInt(6));
		
		this.placeDoorAtCurrentPosition(world, box, rand, 9, 6, 0, this.getMetadataWithOffset(Blocks.wooden_door, 1));
		
		if(this.getBlockAtCurrentPosition(world, 9, 5, -1, box) == Blocks.air && this.getBlockAtCurrentPosition(world, 9, 4, -1, box) != Blocks.air) {
			this.placeBlockAtCurrentPosition(world, Blocks.stone_stairs, this.getMetadataWithOffset(Blocks.stone_stairs, 3), 9, 5, -1, box);
		}
		
		this.spawnVillagers(world, box, 3, 16, 3, 1);
		return true;
		
	}
	
	protected int getVillagerType(int par1) {
		return Properties.villagerWizardID;
	}

	@Override
	protected void func_143012_a(NBTTagCompound var1) {
		
	}

	@Override
	protected void func_143011_b(NBTTagCompound var1) {

	}
}
