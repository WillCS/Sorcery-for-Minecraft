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
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import sorcery.core.Sorcery;
import sorcery.lib.Properties;
import sorcery.lib.SorceryItems;
import sorcery.tileentities.TileEntityBarrel;
import sorcery.tileentities.TileEntityDesk;
import sorcery.tileentities.TileEntityForge;
import sorcery.tileentities.TileEntityHellFurnace;
import sorcery.tileentities.TileEntityMixer;
import sorcery.tileentities.TileEntityMojoStorage;
import sorcery.tileentities.TileEntityPipe;
import sorcery.tileentities.TileEntityRuneCrafting;
import sorcery.tileentities.TileEntitySorcery;
import sorcery.tileentities.TileEntityTinkering;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockMachine extends Block {
	private Random random = new Random();
	
	public BlockMachine() {
		super(Material.rock);
		this.setCreativeTab(Sorcery.tabSorceryTech);
		setHardness(3F);
	}
	
	private IIcon[][] IIcons;
	
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister par1IIconRegister) {
		this.IIcons = new IIcon[16][16];
		IIcons[0][0] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "smooth");
		IIcons[0][1] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "tinkeringTop");
		IIcons[0][2] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "tinkeringSide");
		
		//IIcons[1][0] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "infuserBottom");
		//IIcons[1][1] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "infuserTop");
		//IIcons[1][2] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "infuserSide");
		//IIcons[1][3] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "infuserFront");
		
		IIcons[2][0] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "smooth");
		IIcons[2][1] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "forgeTop");
		IIcons[2][2] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "forgeSide");
		IIcons[2][3] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "forgeFront");
		IIcons[2][7] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "forgeFrontActive");
		
		IIcons[3][0] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "smoothRed");
		IIcons[3][1] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "hellBrick");
		IIcons[3][2] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "hellBrickSide");
		IIcons[3][3] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "hellForgeFront");
		IIcons[3][7] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "hellForgeFrontActive");
		
		IIcons[4][0] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "smoothRed");
		IIcons[4][1] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "hellBrick");
		IIcons[4][2] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "hellBrickSide");
		IIcons[4][3] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "hellFurnaceFront");
		IIcons[4][7] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "hellFurnaceFrontActive");
		
		IIcons[5][0] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "mixerSide");
		IIcons[5][1] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "mixerTop");
		IIcons[5][2] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "mixerSide");
		IIcons[5][3] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "mixerFront");
		IIcons[5][7] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "mixerTopActive");
		
		IIcons[6][0] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "blank");
		
		IIcons[13][0] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "barrelWoodenTop");
		IIcons[13][1] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "barrelWoodenSide");
		
		IIcons[14][0] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "barrelIronTop");
		IIcons[14][1] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "barrelIronSide");
		
		IIcons[15][0] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "barrelVoidTop");
		IIcons[15][1] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "barrelVoidSide");
	}
	
	public IIcon getIcon(int side, int meta) {
		if(meta == 0) {
			switch(side) {
				case 0:
					return IIcons[meta][0];
				case 1:
					return IIcons[meta][1];
				default:
					return IIcons[meta][2];
			}
		/*} else if(meta == 1) {
			switch(side) {
				case 0:
					return IIcons[meta][0];
				case 1:
					return IIcons[meta][1];
				case 3:
					return IIcons[meta][3];
				default:
					return IIcons[meta][2];
			}
		*/} else if(meta == 2) {
			switch(side) {
				case 0:
					return IIcons[meta][0];
				case 1:
					return IIcons[meta][1];
				case 3:
					return IIcons[meta][3];
				default:
					return IIcons[meta][2];
			}
		} else if(meta == 3) {
			switch(side) {
				case 0:
					return IIcons[meta][0];
				case 1:
					return IIcons[meta][1];
				case 3:
					return IIcons[meta][3];
				default:
					return IIcons[meta][2];
			}
		} else if(meta == 4) {
			switch(side) {
				case 0:
					return IIcons[meta][0];
				case 1:
					return IIcons[meta][1];
				case 3:
					return IIcons[meta][3];
				default:
					return IIcons[meta][2];
			}
		} else if(meta == 5) {
			switch(side) {
				case 0:
					return IIcons[meta][0];
				case 1:
					return IIcons[meta][1];
				case 3:
					return IIcons[meta][3];
				default:
					return IIcons[meta][2];
			}
		} else if(meta == 6) {
			return IIcons[meta][0];
		} else if(meta == 13) {
			switch(side) {
				case 0:
					return IIcons[meta][0];
				case 1:
					return IIcons[meta][0];
				default:
					return IIcons[meta][1];
			}
		} else if(meta == 14) {
			switch(side) {
				case 0:
					return IIcons[meta][0];
				case 1:
					return IIcons[meta][0];
				default:
					return IIcons[meta][1];
			}
		} else if(meta == 15) {
			switch(side) {
				case 0:
					return IIcons[meta][0];
				case 1:
					return IIcons[meta][0];
				default:
					return IIcons[meta][1];
			}
		}
		return IIcons[0][0];
	}
	
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
		int meta = world.getBlockMetadata(x, y, z);
		TileEntitySorcery tile = (TileEntitySorcery)world.getTileEntity(x, y, z);
		
		if(meta == 0) {
			switch(side) {
				case 0:
					return IIcons[meta][0];
				case 1:
					return IIcons[meta][1];
				default:
					return IIcons[meta][2];
			}
		/*} else if(meta == 1) {
			switch(side) {
				case 0:
					return IIcons[meta][0];
				case 1:
					return IIcons[meta][1];
			}
			if(side == tile.front)
				return IIcons[meta][3];
			else
				return IIcons[meta][2];
		*/} else if(meta == 2) {
			switch(side) {
				case 0:
					return IIcons[meta][0];
				case 1:
					return IIcons[meta][1];
			}
			if(side == tile.front)
				return ((TileEntityForge)tile).isCooking() ? IIcons[meta][7] : IIcons[meta][3];
			else
				return IIcons[meta][2];
		} else if(meta == 3) {
			switch(side) {
				case 0:
					return IIcons[meta][0];
				case 1:
					return IIcons[meta][1];
			}
			if(side == tile.front)
				return ((TileEntityForge)tile).isCooking() ? IIcons[meta][7] : IIcons[meta][3];
			else
				return IIcons[meta][2];
		} else if(meta == 4) {
			switch(side) {
				case 0:
					return IIcons[meta][0];
				case 1:
					return IIcons[meta][1];
			}
			;
			if(side == tile.front)
				return ((TileEntityHellFurnace)tile).isBurning() ? IIcons[meta][7] : IIcons[meta][3];
			else
				return IIcons[meta][2];
		} else if(meta == 5) {
			switch(side) {
				case 1:
					return ((TileEntityMixer)tile).recipeCookTime <= 0 ? IIcons[meta][1] : IIcons[meta][7];
				case 0:
					return IIcons[meta][0];
			}
			if(side == tile.front)
				return IIcons[meta][3];
			else
				return IIcons[meta][2];
		} else if(meta == 6) {
			return IIcons[meta][0];
		} else if(meta == 13) {
			switch(side) {
				case 0:
					return IIcons[meta][0];
				case 1:
					return IIcons[meta][0];
				default:
					return IIcons[meta][1];
			}
		} else if(meta == 14) {
			switch(side) {
				case 0:
					return IIcons[meta][0];
				case 1:
					return IIcons[meta][0];
				default:
					return IIcons[meta][1];
			}
		} else if(meta == 15) {
			switch(side) {
				case 0:
					return IIcons[meta][0];
				case 1:
					return IIcons[meta][0];
				default:
					return IIcons[meta][1];
			}
		}
		return IIcons[0][0];
	}
	
	public Item getItemDropped(int i, Random random, int j) {
		if(i != 6)
			return super.getItemDropped(i, random, j);
		else return SorceryItems.desk;
	}
	
	public int quantityDropped(int meta, int fortune, Random random) {
		return 1;
	}
	
	public boolean hasTileEntity(int meta) {
		switch(meta) {
			case 0: return true;
			//case 1: return true;
			case 2: return true;
			case 3: return true;
			case 4: return true;
			case 5: return true;
			case 6: return true;
			
			case 10: return true;
			case 11: return true;
			case 12: return true;
				
			case 13: return true;
			case 14: return true;
			case 15: return true;
		}
		return false;
	}
	
	public TileEntity createTileEntity(World world, int metadata) {
		switch(metadata) {
			case 0: return new TileEntityTinkering();
			//case 1: return new TileEntityRuneCrafting();
			case 2: return new TileEntityForge();
			case 3: return new TileEntityForge();
			case 4: return new TileEntityHellFurnace();
			case 5: return new TileEntityMixer();
			case 6: return new TileEntityDesk();
			
			//case 10: return TileEntityMojoStorage.getBatteryWithCapacity(10000);
			//case 11: return TileEntityMojoStorage.getBatteryWithCapacity(100000);
			//case 12: return TileEntityMojoStorage.getBatteryWithCapacity(1000000);
			
			case 13: return TileEntityBarrel.getBarrelWithCapacity(Properties.BUCKET_VOLUME * 32);
			case 14: return TileEntityBarrel.getBarrelWithCapacity(Properties.BUCKET_VOLUME * 64);
			case 15: return TileEntityBarrel.getBarrelWithCapacity(Properties.BUCKET_VOLUME * 128);
			
			/** These three can go under a different block ID */
			//case 0: return new TileEntitySpellDispenser();
			//case 1: return new TileEntityJukeOMatic();
			//case 2: return new TileEntityFireworksFactory();
			//case 3: return new TileEntityMagicFurnace();
			//case 4: return new TileEntityMagicMixer();
			//case 5: return new TileEntityMagicForge();
			//case 6: return new TileEntityMagicMelter();
		}
		return null;
	}
	
	public int damageDropped(int i) {
		if(i != 6)
			return i;
		else return 0;
	}
	
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
		par3List.add(new ItemStack(this, 1, 0));
		//par3List.add(new ItemStack(this, 1, 1));
		par3List.add(new ItemStack(this, 1, 2));
		par3List.add(new ItemStack(this, 1, 3));
		par3List.add(new ItemStack(this, 1, 4));
		par3List.add(new ItemStack(this, 1, 5));

		par3List.add(new ItemStack(this, 1, 13));
		par3List.add(new ItemStack(this, 1, 14));
		par3List.add(new ItemStack(this, 1, 15));
	}
	
	public void breakBlock(World par1World, int par2, int par3, int par4, Block par5, int par6) {
		TileEntitySorcery var7 = (TileEntitySorcery)par1World.getTileEntity(par2, par3, par4);
		
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
	
	public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random) {
		if(par1World.getBlockMetadata(par2, par3, par4) <= 4 && par1World.getBlockMetadata(par2, par3, par4) == 4) {
			par1World.markBlockForUpdate(par2, par3, par4);
			TileEntitySorcery tile = (TileEntitySorcery)par1World.getTileEntity(par2, par3, par4);
			int front = tile.front;
			
			float var7 = (float)par2 + 0.5F;
			float var8 = (float)par3 + 0.0F + par5Random.nextFloat() * 6.0F / 16.0F;
			float var9 = (float)par4 + 0.5F;
			float var10 = 0.52F;
			float var11 = par5Random.nextFloat() * 0.6F - 0.3F;
			if(((TileEntityHellFurnace)tile).isBurning()) {
				if(front == 4) {
					par1World.spawnParticle("smoke", (double)(var7 - var10), (double)var8, (double)(var9 + var11), 0.0D, 0.0D, 0.0D);
					par1World.spawnParticle("flame", (double)(var7 - var10), (double)var8, (double)(var9 + var11), 0.0D, 0.0D, 0.0D);
				} else if(front == 5) {
					par1World.spawnParticle("smoke", (double)(var7 + var10), (double)var8, (double)(var9 + var11), 0.0D, 0.0D, 0.0D);
					par1World.spawnParticle("flame", (double)(var7 + var10), (double)var8, (double)(var9 + var11), 0.0D, 0.0D, 0.0D);
				} else if(front == 2) {
					par1World.spawnParticle("smoke", (double)(var7 + var11), (double)var8, (double)(var9 - var10), 0.0D, 0.0D, 0.0D);
					par1World.spawnParticle("flame", (double)(var7 + var11), (double)var8, (double)(var9 - var10), 0.0D, 0.0D, 0.0D);
				} else if(front == 3) {
					par1World.spawnParticle("smoke", (double)(var7 + var11), (double)var8, (double)(var9 + var10), 0.0D, 0.0D, 0.0D);
					par1World.spawnParticle("flame", (double)(var7 + var11), (double)var8, (double)(var9 + var10), 0.0D, 0.0D, 0.0D);
				}
			}
		}
		
		if(par1World.getBlockMetadata(par2, par3, par4) == 2 || par1World.getBlockMetadata(par2, par3, par4) == 3) {
			par1World.markBlockForUpdate(par2, par3, par4);
			TileEntitySorcery tile = (TileEntitySorcery)par1World.getTileEntity(par2, par3, par4);
			int front = tile.front;
			
			float var7 = (float)par2 + 0.5F;
			float var8 = (float)par3 + 0.0F + par5Random.nextFloat() * 6.0F / 16.0F;
			float var9 = (float)par4 + 0.5F;
			float var10 = 0.52F;
			float var11 = par5Random.nextFloat() * 0.6F - 0.3F;
			if(((TileEntityForge)tile).isCooking()) {
				if(front == 4) {
					par1World.spawnParticle("smoke", (double)(var7 - var10), (double)var8 + 0.4, (double)(var9 + var11), 0.0D, 0.0D, 0.0D);
					par1World.spawnParticle("flame", (double)(var7 - var10), (double)var8 + 0.4, (double)(var9 + var11), 0.0D, 0.0D, 0.0D);
				} else if(front == 5) {
					par1World.spawnParticle("smoke", (double)(var7 + var10), (double)var8 + 0.8, (double)(var9 + var11), 0.0D, 0.0D, 0.0D);
					par1World.spawnParticle("flame", (double)(var7 + var10), (double)var8 + 0.4, (double)(var9 + var11), 0.0D, 0.0D, 0.0D);
				} else if(front == 2) {
					par1World.spawnParticle("smoke", (double)(var7 + var11), (double)var8 + 0.4, (double)(var9 - var10), 0.0D, 0.0D, 0.0D);
					par1World.spawnParticle("flame", (double)(var7 + var11), (double)var8 + 0.4, (double)(var9 - var10), 0.0D, 0.0D, 0.0D);
				} else if(front == 3) {
					par1World.spawnParticle("smoke", (double)(var7 + var11), (double)var8 + 0.4, (double)(var9 + var10), 0.0D, 0.0D, 0.0D);
					par1World.spawnParticle("flame", (double)(var7 + var11), (double)var8 + 0.4, (double)(var9 + var10), 0.0D, 0.0D, 0.0D);
				}
			}
		}
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
		return 1;
	}
	
	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer player, int i1, float f1, float f2, float f3) {
		if(!player.isSneaking()) {
			int meta = world.getBlockMetadata(i, j, k);
			switch(meta) {
				case 0:
					player.openGui(Sorcery.instance, Properties.GUI_TINKERING, world, i, j, k);
					return true;
				//case 1:
				//	player.openGui(Sorcery.instance, Properties.GUI_RUNECRAFTING, world, i, j, k);
				//	return true;
				case 2:
					player.openGui(Sorcery.instance, Properties.GUI_FORGE, world, i, j, k);
					return true;
				case 3:
					player.openGui(Sorcery.instance, Properties.GUI_FORGE, world, i, j, k);
					return true;
				case 4:
					player.openGui(Sorcery.instance, Properties.GUI_HELLFURNACE, world, i, j, k);
					return true;
				case 5:
					player.openGui(Sorcery.instance, Properties.GUI_MIXER, world, i, j, k);
					return true;
				case 6:
					TileEntityDesk tile = (TileEntityDesk)world.getTileEntity(i, j, k);
					if(tile.isDummy) {
						i = tile.otherHalfPos.xCoord();
						j = tile.otherHalfPos.yCoord();
						k = tile.otherHalfPos.zCoord();
					}
					
					tile.isInUse = true;
					
					player.openGui(Sorcery.instance, Properties.GUI_DESK, world, i, j, k);
					return true;
					
				/*case 10: case 11: case 12:
					player.openGui(Sorcery.instance, Properties.GUI_MOJOSTORAGE, world, i, j, k);
					return true;*/
					
				case 13: case 14: case 15:
					player.openGui(Sorcery.instance, Properties.GUI_BARREL, world, i, j, k);
					return true;
			}
			return false;
		}
		return false;
	}
}
