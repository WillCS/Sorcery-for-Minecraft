package sorcery.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import sorcery.entities.EntityLantern;
import sorcery.entities.EntityUnicorn;
import sorcery.items.ItemLantern;
import sorcery.lib.SorceryItems;

public class BlockAir extends Block {
	public BlockAir() {
		super(Material.air);
		this.setBlockBounds(0, 0, 0, 0, 0, 0);
        this.setTickRandomly(true);
	}
	
	@Override
    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random) {
		System.out.println("dfsd");
		List<Entity> players = par1World.getEntitiesWithinAABBExcludingEntity(new EntityUnicorn(par1World),
				AxisAlignedBB.getBoundingBox(par2 - 1, par3 - 1, par4 - 1, par2 + 1, par3 + 1, par4 + 1));
		
		boolean shouldRemove = true;
		if(players.size() != 0)
			shouldRemove = true;
		
		for(int i = 0; i < players.size(); i++) {
			if(players.get(i) instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer)players.get(i);
				if(player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() instanceof ItemLantern) {
							if(((ItemLantern)SorceryItems.lantern).getFuel(player.getCurrentEquippedItem()) != 0)
								shouldRemove = false;
				}
			} else if(players.get(i) instanceof EntityLantern) {
				EntityLantern lantern = (EntityLantern)players.get(i);
					if(lantern.hasFuel() || lantern.isCheaty())
						shouldRemove = false;
			}
		}
		if(shouldRemove && !par1World.isRemote) {
			par1World.setBlockToAir(par2, par3, par4);
			//par1World.markBlockForUpdate(par2, par3, par4);
			par1World.updateLightByType(EnumSkyBlock.Block, par2, par3, par4);
		} else if(!shouldRemove && !par1World.isRemote) {
			par1World.scheduleBlockUpdate(par2, par3, par4, this, 5);
			par1World.markBlockForUpdate(par2, par3, par4);
		}
		
    }

	public int tickRate(World p_149738_1_) {
		return 1;
	}
	
	@Override
    public boolean isBlockNormalCube() {
        return false;
    }
	
    @Override
    public int getRenderType() {
        return -1;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean canEntityDestroy(IBlockAccess world, int x, int y, int z, Entity entity) {
        return false;
    }

    @Override
    public boolean canCollideCheck(int par1, boolean par2) {
        return false;
    }

    @Override
    public boolean canBeReplacedByLeaves(IBlockAccess world, int x, int y, int z) {
        return true;
    }

    @Override
    public boolean canDropFromExplosion(Explosion par1Explosion) {
        return false;
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
        return new ArrayList();
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
        return null;
    }

    @Override
    public boolean isAir(IBlockAccess world, int x, int y, int z) {
        return true;
    }

	@Override
	public int getLightValue(IBlockAccess par1iBlockAccess, int par2, int par3, int par4) {
		int meta = par1iBlockAccess.getBlockMetadata(par2, par3, par4);
		switch(meta) {
			case 0: return 10;
			default: return 0;
		}
	}
}
