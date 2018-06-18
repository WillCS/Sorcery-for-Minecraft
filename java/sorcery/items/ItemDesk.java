package sorcery.items;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import sorcery.api.spellcasting.Wand;
import sorcery.api.spellcasting.WandComponent.WandComponentBody;
import sorcery.api.spellcasting.WandComponent.WandComponentGenerator;
import sorcery.core.Sorcery;
import sorcery.lib.Pos3D;
import sorcery.lib.Properties;
import sorcery.lib.SpellHelper;
import sorcery.tileentities.TileEntityDesk;

public class ItemDesk extends ItemArcane {
	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
		Block block = par3World.getBlock(par4, par5, par6);

		if(par7 == 0) {
			--par5;
		}

		if(par7 == 1) {
			++par5;
		}

		if(par7 == 2) {
			--par6;
		}

		if(par7 == 3) {
			++par6;
		}

		if(par7 == 4) {
			--par4;
		}

		if(par7 == 5) {
			++par4;
		}
		
		int xOff = 0;
		int zOff = 0;
		
		int direction = MathHelper.floor_double((double)(par2EntityPlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		switch(direction) {
			case 0:
				xOff = -1;
				break;
			case 2:
				xOff = 1;
				break;
			case 1:
				zOff = -1;
				break;
			case 3:
				zOff = 1;
				break;
		}
		
		if(!par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack)) {
			return false;
		} else if(!par2EntityPlayer.canPlayerEdit(par4 + xOff, par5, par6 + zOff, par7, par1ItemStack)) {
			return false;
		} else if(!par3World.isAirBlock(par4, par5, par6)) {
			return false;
		} else if(!par3World.isAirBlock(par4 + xOff, par5, par6 + zOff)) {
			return false;
		} else if(par1ItemStack.stackSize == 0) {
			return false;
		}
		
		par3World.setBlock(par4, par5, par6, Sorcery.machine, 6, 7);
		TileEntityDesk master = new TileEntityDesk();
		master.isDummy = false;
		master.rotation = direction;
		master.otherHalfPos = new Pos3D(par4 + xOff, par5, par6 + zOff);
		par3World.setTileEntity(par4, par5, par6, master);
		par3World.setBlock(par4, par5, par6, Sorcery.machine, 6, 3);
		
		par3World.setBlock(par4 + xOff, par5, par6 + zOff, Sorcery.machine, 6, 3);
		TileEntityDesk dummy = new TileEntityDesk();
		dummy.isDummy = true;
		dummy.rotation = direction;
		dummy.otherHalfPos = new Pos3D(par4, par5, par6);
		par3World.setTileEntity(par4 + xOff, par5, par6 + zOff, dummy);
		return true;
	}
	
	public void registerIcons(IIconRegister par1IIconRegister) {
		this.itemIcon = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "blankIcon");
	}
}
