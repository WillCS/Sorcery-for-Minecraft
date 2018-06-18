package sorcery.blocks;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import sorcery.core.Sorcery;
import sorcery.lib.Properties;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockRune extends Block {
	public BlockRune() {
		super(Material.rock);
		this.setCreativeTab(Sorcery.tabSorceryMaterials);
	}
	
	private IIcon[] IIcons;
	
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister par1IIconRegister) {
		this.IIcons = new IIcon[17];
		for(int i = 0; i < Properties.ELEMENTS.length; i++) {
			this.IIcons[i] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "rune" + Properties.ELEMENTS[i]);
		}
		
		this.IIcons[16] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "runeBlank");
	}
	
	public IIcon getIcon(int side, int meta) {
		switch(side) {
			case 0:
				return this.IIcons[16];
			case 1:
				return this.IIcons[16];
			default:
				return this.IIcons[meta];
		}
	}
	
	public int damageDropped(int i) {
		return i;
	}
	
	public void getSubBlocks(Block par1, CreativeTabs par2CreativeTabs, List par3List) {
		for(int var4 = 0; var4 < Properties.ELEMENTS.length; ++var4) {
			par3List.add(new ItemStack(par1, 1, var4));
		}
	}
	
	public float getBlockHardness(World par1World, int par2, int par3, int par4) {
		int meta = par1World.getBlockMetadata(par2, par3, par4);
		return 2F;
	}
}
