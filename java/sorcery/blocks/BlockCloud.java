package sorcery.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import sorcery.core.Sorcery;
import sorcery.lib.Properties;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockCloud extends Block {
	
	public BlockCloud() {
		super(Material.cloth);
		this.setCreativeTab(Sorcery.tabSorceryMaterials);
	}
	
	private IIcon IIcon;
	
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister par1IIconRegister) {
		IIcon = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "cloud");
	}
	
	public IIcon getIcon(int side, int meta) {
		return IIcon;
	}
	
	public int quantityDropped(int meta, int fortune, Random random) {
		return 0;
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
		return 0.1F;
	}
}
