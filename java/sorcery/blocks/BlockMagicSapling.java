package sorcery.blocks;

import static net.minecraftforge.common.EnumPlantType.Cave;
import static net.minecraftforge.common.EnumPlantType.Crop;
import static net.minecraftforge.common.EnumPlantType.Desert;
import static net.minecraftforge.common.EnumPlantType.Nether;
import static net.minecraftforge.common.EnumPlantType.Plains;
import static net.minecraftforge.common.EnumPlantType.Water;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.event.terraingen.TerrainGen;
import sorcery.core.Sorcery;
import sorcery.lib.Properties;
import sorcery.world.WorldGenMagicTrees;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockMagicSapling extends BlockBush implements IPlantable {
	private IIcon Icon;
	
	public BlockMagicSapling() {
		super();
		float f = 0.4F;
		this.setBlockBounds(0.1F, 0.0F, 0.1F, 0.9F, 0.7F, 0.9F);
		this.setCreativeTab(Sorcery.tabSorceryMaterials);
	}
	
	/** Ticks the block if it's been scheduled */
	public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random) {
		if(!par1World.isRemote) {
			super.updateTick(par1World, par2, par3, par4, par5Random);
			
			if(par1World.getBlockLightValue(par2, par3 + 1, par4) >= 9 && par5Random.nextInt(7) == 0) {
				this.markOrGrowMarked(par1World, par2, par3, par4, par5Random);
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	/**
	 * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
	 */
	public IIcon getIcon(int par1, int par2) {
		return this.Icon;
	}
	
	public void markOrGrowMarked(World par1World, int par2, int par3, int par4, Random par5Random) {
		int l = par1World.getBlockMetadata(par2, par3, par4);
		
		if((l & 8) == 0) {
			par1World.setBlockMetadataWithNotify(par2, par3, par4, l | 8, 4);
		} else {
			this.growTree(par1World, par2, par3, par4, par5Random);
		}
	}
	
	/** Attempts to grow a sapling into a tree */
	public void growTree(World par1World, int par2, int par3, int par4, Random par5Random) {
		if(!TerrainGen.saplingGrowTree(par1World, par5Random, par2, par3, par4))
			return;
		
		int l = par1World.getBlockMetadata(par2, par3, par4);
		Object tree = new WorldGenMagicTrees(true);
		
		int x1 = 0;
		int z1 = 0;
		
		par1World.setBlockToAir(par2, par3, par4);
		
		if(!((WorldGenerator)tree).generate(par1World, par5Random, par2, par3, par4))
			par1World.setBlock(par2, par3, par4, this, l, 4);
	}
	
	@Override
	public EnumPlantType getPlantType(IBlockAccess world, int x, int y, int z) {
		return Plains;
	}

	@Override
	public int damageDropped(int par1) {
		return par1;
	}
	
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
		par3List.add(new ItemStack(par1, 1, 0));
	}
	
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister par1IIconRegister) {
		this.Icon = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "saplingMagic");
	}
}
