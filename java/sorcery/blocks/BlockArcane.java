package sorcery.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import sorcery.api.element.Element;
import sorcery.core.Sorcery;
import sorcery.lib.Properties;
import sorcery.lib.SorceryFluids;
import sorcery.lib.SorceryItems;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockArcane extends Block {
	public BlockArcane() {
		super(Material.rock);
		this.setCreativeTab(Sorcery.tabSorceryMaterials);
	}
	
	private IIcon[] IIcons;
	
	public Item getItemDropped(int i, Random random, int j) {
		switch(i) {
			case 2:
				return SorceryItems.gemOnyx;
			case 12:
				return SorceryItems.elementCrystal;
			case 15:
				return SorceryItems.dustLimestone;
			default:
				return super.getItemDropped(i, random, j);
		}
	}
	
	@Override
	public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int i) {
		if(i == 2 || i == 12) {
			int xp = MathHelper.getRandomIntegerInRange(world.rand, 3, 7);
			this.dropXpOnBlockBreak(world, x, y, z, xp);
		}
	}
	
	public int getLightValue(IBlockAccess world, int x, int y, int z) {
		if(world.getBlockMetadata(x, y, z) == 12)
			return 5;
		return 0;
	}
	
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister par1IIconRegister) {
		this.IIcons = new IIcon[16];
		
		this.IIcons[0] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "oreSilver");
		this.IIcons[1] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "oreNetherrite");
		this.IIcons[2] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "oreOnyx");
		this.IIcons[3] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "oreInfernite");
		this.IIcons[4] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "storageSilver");
		this.IIcons[5] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "storageNetherrite");
		this.IIcons[6] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "storageOnyx");
		this.IIcons[7] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "storageInfernite");
		this.IIcons[8] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "brickSnow");
		this.IIcons[9] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "brickSandstone");
		this.IIcons[10] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "storageEnder");
		this.IIcons[11] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "storageCoal");
		this.IIcons[12] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "oreElemental");
		this.IIcons[13] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "oreTin");
		this.IIcons[14] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "oreCopper");
		this.IIcons[15] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "limestone");
		
		SorceryFluids.registerFluidIIcons(par1IIconRegister);
	}
	
	public IIcon getIcon(int side, int meta) {
		if(this.IIcons[meta] != null)
			return this.IIcons[meta];
		else
			return this.IIcons[0];
	}
	
	public int quantityDropped(int meta, int fortune, Random random) {
		switch(meta) {
			case 2:
				return quantityDroppedWithBonus(fortune, random);
			case 12:
				return 1 + random.nextInt(4);
			case 15:
				return 2 + random.nextInt(3);
			default:
				return 1;
		}
	}
	
	public int damageDropped(int i) {
		switch(i) {
			case 2: case 15:
				return 0;
			case 12:
				return new Random().nextInt(Element.elementsList.length);
			default:
				return i;
		}
	}
	
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
		for(int var4 = 0; var4 < 16; ++var4) {
			par3List.add(new ItemStack(par1, 1, var4));
		}
	}
	
	public int getDamageValue(World par1World, int par2, int par3, int par4) {
		return par1World.getBlockMetadata(par2, par3, par4);
	}
	
	public float getBlockHardness(World par1World, int par2, int par3, int par4) {
		int meta = par1World.getBlockMetadata(par2, par3, par4);
		switch(meta) {
			case 4:
				return 5F;
			case 5:
				return 5F;
			case 6:
				return 5F;
			case 7:
				return 5F;
			case 8:
				return 1.5F;
			case 9:
				return 1.5F;
			case 12:
				return 5F;
			case 15:
				return 0.5F;
			default:
				return 3F;
		}
	}
}
