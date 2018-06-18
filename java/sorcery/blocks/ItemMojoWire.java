package sorcery.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import sorcery.core.Sorcery;
import sorcery.mojo.EnumWireType;
import sorcery.tileentities.TileEntityMojoWire;

public class ItemMojoWire extends ItemBlock {
	public ItemMojoWire(Block block) {
		super(block);
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
		this.setCreativeTab(Sorcery.tabSorceryTech);
	}
	
	public IIcon getIconFromDamage(int par1) {
		return ((Block)Sorcery.itemNode).getIcon(1, par1);
	}
	
	public int getMetadata(int par1) {
		return par1;
	}
	
	public String getUnlocalizedName(ItemStack par1ItemStack) {
		return "sorcery.block.mojoWire." + par1ItemStack.getItemDamage();
	}
	
	@Override
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata) {
		if(world.isRemote)
			return false;
		
		if(!world.setBlock(x, y, z, this.field_150939_a, metadata, 3))
			return false;
		
		if(world.getBlock(x, y, z) == this.field_150939_a) {
			this.field_150939_a.onBlockPlacedBy(world, x, y, z, player, stack);
			TileEntityMojoWire wire = (TileEntityMojoWire)world.getTileEntity(x, y, z);
			if(wire != null) {
				wire.type = EnumWireType.getWireTypeFrom(stack.getItemDamage());
				wire.colour = -1;
			}
			this.field_150939_a.onPostBlockPlaced(world, x, y, z, 0);
		}
		
		return true;
	}
	
	@Override
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
		for(int i = 0; i < EnumWireType.values().length; i++)
			par3List.add(new ItemStack(par1, 1, i));
	}
}
