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
import net.minecraft.world.World;
import sorcery.core.Sorcery;
import sorcery.lib.Properties;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockMetalBricks extends Block {
	public BlockMetalBricks() {
		super(Material.iron);
		this.setCreativeTab(Sorcery.tabSorceryMaterials);
	}
	
	private IIcon[] Icons;
	
	public Item getItemDropped(int i, Random random, int j) {
		return super.getItemDropped(i, random, j);
	}
	
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister par1IIconRegister) {
		this.Icons = new IIcon[16];
		
		this.Icons[0] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "bricksIron");
		this.Icons[1] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "bricksSilver");
		this.Icons[2] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "bricksGold");
		this.Icons[3] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "bricksNetherrite");
		this.Icons[4] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "bricksInfernite");
		this.Icons[5] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "bricksMagic");
		this.Icons[6] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "bricksVoid");
		this.Icons[7] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "bricksEmerald");
		this.Icons[8] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "bricksLapis");
		this.Icons[9] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "bricksDiamond");
		this.Icons[10] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "bricksOnyx");
		this.Icons[11] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "bricksTin");
		this.Icons[12] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "bricksCopper");
		this.Icons[13] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "bricksBrass");
		this.Icons[14] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "bricksSteel");
	}
	
	public IIcon getIcon(int side, int meta) {
		if(this.Icons[meta] != null)
			return this.Icons[meta];
		else
			return this.Icons[0];
	}
	
	public int quantityDropped(int meta, int fortune, Random random) {
		return 1;
	}
	
	public int damageDropped(int i) {
		return i;
	}
	
	public void addCreativeItems(ArrayList itemList) {
		itemList.remove(this);
		for(int x = 0; x < 15; x++) {
			itemList.add(new ItemStack(this, 1, x));
		}
	}
	
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
		for(int var4 = 0; var4 < 15; ++var4) {
			par3List.add(new ItemStack(par1, 1, var4));
		}
	}
	
	public int getDamageValue(World par1World, int par2, int par3, int par4) {
		return par1World.getBlockMetadata(par2, par3, par4);
	}
	
	public float getBlockHardness(World par1World, int par2, int par3, int par4) {
		return 3F;
	}
}
