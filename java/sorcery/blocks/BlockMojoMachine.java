package sorcery.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import sorcery.api.mojo.IMojoStorage;
import sorcery.core.Sorcery;
import sorcery.lib.Properties;
import sorcery.lib.SorceryItems;
import sorcery.tileentities.TileEntityElementalInfuser;
import sorcery.tileentities.TileEntityForge;
import sorcery.tileentities.TileEntityHellFurnace;
import sorcery.tileentities.TileEntityInfusionStand;
import sorcery.tileentities.TileEntityMojoStorage;
import sorcery.tileentities.TileEntityRepairTable;
import sorcery.tileentities.TileEntitySorcery;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockMojoMachine extends Block {
	private Random random = new Random();
	
	public BlockMojoMachine() {
		super(Material.iron);
		this.setCreativeTab(Sorcery.tabSorceryTech);
		setHardness(3F);
	}
	
	private IIcon[][] icons;
	
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister par1IIconRegister) {
		this.icons = new IIcon[17][16];
		
		this.icons[0][0] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "battery1Side");
		this.icons[0][1] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "machineTier1");
		this.icons[0][2] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "battery1Front0");
		this.icons[0][3] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "battery1Front1");
		this.icons[0][4] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "battery1Front2");
		this.icons[0][5] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "battery1Front3");
		this.icons[0][6] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "battery1Front4");
		
		this.icons[1][0] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "machineTier2");
		this.icons[1][1] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "machineTier2");
		this.icons[1][2] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "battery2Front0");
		this.icons[1][3] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "battery2Front1");
		this.icons[1][4] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "battery2Front2");
		this.icons[1][5] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "battery2Front3");
		this.icons[1][6] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "battery2Front4");
		
		this.icons[2][0] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "machineTier3");
		this.icons[2][1] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "machineTier3");
		this.icons[2][2] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "battery3Front0");
		this.icons[2][3] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "battery3Front1");
		this.icons[2][4] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "battery3Front2");
		this.icons[2][5] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "battery3Front3");
		this.icons[2][6] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "battery3Front4");
				
		this.icons[3][0] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "infuserBottom");
		this.icons[3][1] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "infuserTop");
		this.icons[3][2] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "infuserSide");
		this.icons[3][3] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "infuserFront");
		
		this.icons[5][0] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "machineTier3");
		this.icons[5][1] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "repairinatorTop");
		
		this.icons[16][0] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "blank");
		this.icons[16][1] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "machineTier1");
		this.icons[16][2] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "machineTier2");
		this.icons[16][3] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "machineTier3");
		this.icons[16][4] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "machineTier4");
	}
	
	public IIcon getIcon(int side, int meta) {
		if(meta == 0) {
			switch(side) {
				case 0: case 1: return this.icons[0][1];
				case 3: return this.icons[0][2];
				default: return this.icons[0][0];
			}
		} else if(meta == 1) {
			switch(side) {
				case 0: case 1: return this.icons[1][1];
				case 3: return this.icons[1][2];
				default: return this.icons[1][0];
			}
		} else if(meta == 2) {
			switch(side) {
				case 0: case 1: return this.icons[2][1];
				case 3: return this.icons[2][2];
				default: return this.icons[2][0];
			}
		} else if(meta == 4) {
			return this.icons[16][3];
		} else if(meta == 5) {
			switch(side) {
				case 1:
					return this.icons[meta][1];
				default:
					return this.icons[meta][0];
			}
		}
		
		if(meta == 20)
			return this.icons[16][1];
		else if(meta == 21)
			return this.icons[16][2];
		else if(meta == 22)
			return this.icons[16][3];
		else if(meta == 23)
			return this.icons[16][4];
		
		return this.icons[16][3];
	}
	
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
		int meta = world.getBlockMetadata(x, y, z);
		TileEntitySorcery tile = (TileEntitySorcery)world.getTileEntity(x, y, z);
		
		if(meta == 0) {
			if(side == tile.front) {
				int mojo = ((IMojoStorage)tile).getMojo();
				if(mojo >= 10000)
					return this.icons[0][2];
				else if(mojo > 7500)
					return this.icons[0][3];
				else if(mojo > 5000)
					return this.icons[0][4];
				else if(mojo > 2500)
					return this.icons[0][5];
				
				return this.icons[0][6];
			}
			switch(side) {
				case 0: case 1: return this.icons[0][1];
				default: return this.icons[0][0];
			}
		} else if(meta == 1) {
			if(side == tile.front) {
				int mojo = ((IMojoStorage)tile).getMojo();
				if(mojo >= 100000)
					return this.icons[1][6];
				else if(mojo > 75000)
					return this.icons[1][5];
				else if(mojo > 50000)
					return this.icons[1][4];
				else if(mojo > 25000)
					return this.icons[1][3];
				
				return this.icons[1][2];
			}
			switch(side) {
				case 0: case 1: return this.icons[1][1];
				default: return this.icons[1][0];
			}
		} else if(meta == 2) {
			if(side == tile.front) {
				int mojo = ((IMojoStorage)tile).getMojo();
				if(mojo >= 1000000)
					return this.icons[2][6];
				else if(mojo > 750000)
					return this.icons[2][5];
				else if(mojo > 500000)
					return this.icons[2][4];
				else if(mojo > 250000)
					return this.icons[2][3];
				
				return this.icons[2][2];
			}
			switch(side) {
				case 0: case 1: return this.icons[2][1];
				default: return this.icons[2][0];
			}
		} else if(meta == 4) {
			return this.icons[16][3];
		} else if(meta == 5) {
			switch(side) {
				case 1:
					return this.icons[meta][1];
				default:
					return this.icons[meta][0];
			}
		}
		return this.icons[16][3];
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		return super.getDrops(world, x, y, z, metadata, fortune);
	}

	public int quantityDropped(int meta, int fortune, Random random) {
		if(meta < 3)
			return 0;
		return 1;
	}
	
	public boolean hasTileEntity(int meta) {
		return true;
	}
	
	public TileEntity createTileEntity(World world, int metadata) {
		switch(metadata) {
			case 0: return TileEntityMojoStorage.getBatteryWithCapacity(25000);
			case 1: return TileEntityMojoStorage.getBatteryWithCapacity(250000);
			case 2: return TileEntityMojoStorage.getBatteryWithCapacity(1500000);
			case 3: return new TileEntityInfusionStand();
			case 4: return new TileEntityElementalInfuser();
			case 5: return new TileEntityRepairTable();

			//case 6: return new TileEntitySpellDispenser();
			//case 7: return new TileEntityOreDuplicator();
			//case 8: return new TileEntityMagicForge();
			//case 9: return new TileEntityMagicFurnace();
			//case 10: return new TileEntityMagicMixer();
			//case 11: return new TileEntityFireworksFactory();
			//case 12: return new TileEntityJukeOMatic();
			//case 13: return new TileEntityTerraformatron();
			//case 14: return new TileEntityDimensionator();
		}
		return null;
	}
	
	public int damageDropped(int i) {
		return i;
	}
	
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
		par3List.add(new ItemStack(this, 1, 0));
		par3List.add(new ItemStack(this, 1, 1));
		par3List.add(new ItemStack(this, 1, 2));
		par3List.add(new ItemStack(this, 1, 3));
		par3List.add(new ItemStack(this, 1, 4));
		par3List.add(new ItemStack(this, 1, 5));
	}
	
	public void breakBlock(World par1World, int par2, int par3, int par4, Block par5, int par6) {
		TileEntitySorcery var7 = (TileEntitySorcery)par1World.getTileEntity(par2, par3, par4);
		
		if(par6 < 3) {
			TileEntityMojoStorage tile = (TileEntityMojoStorage)par1World.getTileEntity(par2, par3, par4);
			int amount = tile.getMojo();
			int storage = tile.getCapacity();
			String name = tile.getInventoryName();
			ItemStack item = new ItemStack(SorceryItems.mojoStorage, 1);
			item.stackTagCompound = new NBTTagCompound();
			item.stackTagCompound.setInteger("capacity", storage);
			item.stackTagCompound.setInteger("amount", amount);
			item.stackTagCompound.setBoolean("cheaty", tile.cheaty);
			item.stackTagCompound.setInteger("damage", par6);
			
			float var10 = this.random.nextFloat() * 0.8F + 0.1F;
			float var11 = this.random.nextFloat() * 0.8F + 0.1F;
			float var12 = this.random.nextFloat() * 0.8F + 0.1F;
			EntityItem entity = new EntityItem(par1World, (double)((float)par2 + var10), (double)((float)par3 + var11), (double)((float)par4 + var12), item);

			float var15 = 0.05F;
			entity.motionX = (double)((float)this.random.nextGaussian() * var15);
			entity.motionY = (double)((float)this.random.nextGaussian() * var15 + 0.2F);
			entity.motionZ = (double)((float)this.random.nextGaussian() * var15);
			par1World.spawnEntityInWorld(entity);
		}
		
		
		if(var7 != null) {
			for(int var8 = 0; var8 < var7.getSizeInventory(); ++var8) {
				ItemStack var9 = var7.getStackInSlot(var8);
				
				if(var9 != null) {
					float var10 = this.random.nextFloat() * 0.8F + 0.1F;
					float var11 = this.random.nextFloat() * 0.8F + 0.1F;
					float var12 = this.random.nextFloat() * 0.8F + 0.1F;
					
					while(var9.stackSize > 0) {
						int var13 = this.random.nextInt(21) + 10;
						
						if(var13 > var9.stackSize) {
							var13 = var9.stackSize;
						}
						
						var9.stackSize -= var13;
						EntityItem var14 = new EntityItem(par1World, (double)((float)par2 + var10), (double)((float)par3 + var11), (double)((float)par4 + var12), new ItemStack(var9.getItem(), var13, var9.getItemDamage()));
						
						if(var9.hasTagCompound()) {
							var14.getEntityItem().setTagCompound((NBTTagCompound)var9.getTagCompound().copy());
						}
						
						float var15 = 0.05F;
						var14.motionX = (double)((float)this.random.nextGaussian() * var15);
						var14.motionY = (double)((float)this.random.nextGaussian() * var15 + 0.2F);
						var14.motionZ = (double)((float)this.random.nextGaussian() * var15);
						par1World.spawnEntityInWorld(var14);
					}
				}
			}
		}
		par1World.removeTileEntity(par2, par3, par4);
		super.breakBlock(par1World, par2, par3, par4, par5, par6);
	}
	
	public int getLightValue(IBlockAccess world, int x, int y, int z) {
		if(world.getBlockMetadata(x, y, z) == 2 || world.getBlockMetadata(x, y, z) == 3)
			if(world.getTileEntity(x, y, z) != null && world.getTileEntity(x, y, z) instanceof TileEntityForge) {
				if((boolean)((TileEntityForge)world.getTileEntity(x, y, z)).isCooking())
					return 15;
			}
		
		if(world.getBlockMetadata(x, y, z) == 4)
			if(world.getTileEntity(x, y, z) != null && world.getTileEntity(x, y, z) instanceof TileEntityForge) {
				if((boolean)((TileEntityHellFurnace)world.getTileEntity(x, y, z)).isBurning())
					return 15;
			}
		return 0;
	}
	
	public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack) {
		int direction = MathHelper.floor_double((double)(par5EntityLivingBase.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		TileEntitySorcery tile = (TileEntitySorcery)par1World.getTileEntity(par2, par3, par4);
		int meta = par1World.getBlockMetadata(par2, par3, par4);
		switch(direction) {
			case 0:
				tile.front = 2;
				break;
			case 1:
				tile.front = 5;
				break;
			case 2:
				tile.front = 3;
				break;
			case 3:
				tile.front = 4;
		}
	}
	
	public boolean isOpaqueCube() {
		return false;
	}
	
	public int getRenderBlockPass() {
		return 0;
	}

	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer player, int i1, float f1, float f2, float f3) {
		if(!player.isSneaking()) {
			int meta = world.getBlockMetadata(i, j, k);
			switch(meta) {
				case 0: case 1: case 2:
					player.openGui(Sorcery.instance, Properties.GUI_MOJOSTORAGE, world, i, j, k);
					return true;
					
				case 5:
					player.openGui(Sorcery.instance, Properties.GUI_REPAIR, world, i, j, k);
					return true;
			}
			return false;
		}
		return false;
	}
}
