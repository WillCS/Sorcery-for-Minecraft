package sorcery.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import sorcery.core.Sorcery;
import sorcery.lib.Properties;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockSlime extends Block {
	public BlockSlime() {
		super(Sorcery.materialSlime);
		this.setCreativeTab(Sorcery.tabSorceryMaterials);
		this.setHardness(0.25F);
	}
	
	private IIcon[] IIcons;
	
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister par1IIconRegister) {
		this.IIcons = new IIcon[16];
		this.IIcons[0] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "slimeWhite");
		this.IIcons[1] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "slimeOrange");
		this.IIcons[2] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "slimeMagenta");
		this.IIcons[3] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "slimeLightBlue");
		this.IIcons[4] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "slimeYellow");
		this.IIcons[5] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "slimeLimeGreen");
		this.IIcons[6] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "slimePink");
		this.IIcons[7] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "slimeGrey");
		this.IIcons[8] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "slimeLightGrey");
		this.IIcons[9] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "slimeCyan");
		this.IIcons[10] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "slimePurple");
		this.IIcons[11] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "slimeBlue");
		this.IIcons[12] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "slimeBrown");
		this.IIcons[13] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "slimeGreen");
		this.IIcons[14] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "slimeRed");
		this.IIcons[15] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "slimeBlack");
	}
	
	public IIcon getIcon(int side, int meta) {
		return this.IIcons[meta];
	}
	
	public int damageDropped(int i) {
		return i;
	}
	
	public int quantityDropped(int meta, int fortune, Random random) {
		return 1;
	}
	
	public void getSubBlocks(Block par1, CreativeTabs par2CreativeTabs, List par3List) {
		for(int var4 = 0; var4 < 16; ++var4) {
			par3List.add(new ItemStack(par1, 1, var4));
		}
	}
	
	public boolean isOpaqueCube() {
		return false;
	}
	
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	public int getRenderBlockPass() {
		return 1;
	}
	
	public float getBlockHardness(World par1World, int par2, int par3, int par4) {
		int meta = par1World.getBlockMetadata(par2, par3, par4);
		return 1F;
	}
}
