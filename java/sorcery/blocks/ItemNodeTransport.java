package sorcery.blocks;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Facing;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import sorcery.core.Sorcery;
import sorcery.tileentities.nodes.TileEntityNodeBase;

public class ItemNodeTransport extends ItemBlock {
	public ItemNodeTransport(Block block) {
		super(block);
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
		this.setCreativeTab(CreativeTabs.tabTransport);
	}
	
	public IIcon getIconFromDamage(int par1) {
		return ((Block)Sorcery.itemNode).getIcon(1, par1);
	}
	
	public int getMetadata(int par1) {
		return par1;
	}
	
	public String getUnlocalizedName(ItemStack par1ItemStack) {
		return "sorcery.block.node." + par1ItemStack.getItemDamage();
	}
	
	@Override
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata) {
		if(!world.setBlock(x, y, z, this.field_150939_a, metadata, 3)) {
			return false;
		}
		
		if(world.getBlock(x, y, z) == this.field_150939_a) {
			this.field_150939_a.onBlockPlacedBy(world, x, y, z, player, stack);
			this.field_150939_a.onPostBlockPlaced(world, x, y, z, metadata);
			TileEntityNodeBase node = (TileEntityNodeBase)world.getTileEntity(x, y, z);
			if(node != null) {
				node.setConnectedSide(Facing.oppositeSide[side]);
			}
		}
		
		return true;
	}
}
