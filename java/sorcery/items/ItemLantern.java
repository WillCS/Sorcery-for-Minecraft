package sorcery.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidContainerItem;
import sorcery.core.Sorcery;
import sorcery.entities.EntityLantern;
import sorcery.lib.Properties;
import sorcery.tileentities.TileEntityLantern;

public class ItemLantern extends ItemArcane implements IFluidContainerItem {

	public ItemLantern() {
		this.setMaxDamage(1001);
	}

	public void registerIcons(IIconRegister par1IIconRegister) {
		this.itemIcon = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "blankIcon");
	}

	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
		if(par3World.isRemote) return false;
		
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
		
		if(par2EntityPlayer.isSneaking() && par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack)) {
			if(par3World.isAirBlock(par4, par5, par6)) {
				par3World.setBlock(par4, par5, par6, Sorcery.arcanehalf, 1, 7);
				TileEntityLantern lantern = new TileEntityLantern();
				
				lantern.front = ForgeDirection.OPPOSITES[par7];
				lantern.fuel = this.getFuel(par1ItemStack);
				
				if(!par2EntityPlayer.capabilities.isCreativeMode) {
					par2EntityPlayer.setCurrentItemOrArmor(0, null);
					lantern.isCheaty = true;
				}
				
				par3World.setTileEntity(par4, par5, par6, lantern);
			}
		}
		
		if(this.getFuel(par1ItemStack) - 2 >= 0) {
			if(!par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack)) {
				return false;
			} else {
				if(par3World.isAirBlock(par4, par5, par6)) {
					par3World.playSoundEffect((double)par4 + 0.5D, (double)par5 + 0.5D, (double)par6 + 0.5D, "fire.ignite", 1.0F, itemRand.nextFloat() * 0.4F + 0.8F);
					par3World.setBlock(par4, par5, par6, Blocks.fire);
					if(!par3World.isRemote && !par2EntityPlayer.capabilities.isCreativeMode)
						this.consumeFuel(par1ItemStack, 2);
				}
	
				return true;
			}
		}
		
		return false;
	}
	
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
    	if(this.getFuel(stack) - 2 >= 0 && entity instanceof EntityLiving) {
    		((EntityLiving)entity).setFire(5);
			if(!player.worldObj.isRemote && !player.capabilities.isCreativeMode)
				this.consumeFuel(stack, 2);
		}
    	return false;
    }
	
	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) {
		if(par2World.isRemote || !par5)
			return;
		
		boolean consumeFuel = true;
		if(par3Entity instanceof EntityPlayer) {
			if(((EntityPlayer)par3Entity).capabilities.isCreativeMode)
				consumeFuel = false;
		} else consumeFuel = false;
			
		
		if(!this.hasFuel(par1ItemStack)) return;
		else if(this.hasFuel(par1ItemStack) && par5)
			if(par2World.rand.nextInt(21) == 0 && consumeFuel)
				this.consumeFuel(par1ItemStack, 1);

		int x = (int)par3Entity.posX;
		int y = (int)par3Entity.posY + 1;
		int z = (int)par3Entity.posZ;

		if(par2World.isAirBlock(x, y, z) && !par5) {
			par2World.setBlockToAir(x, y, z);
		}

		if(!par5)
			return;
		
		if(par2World.isAirBlock(x, y, z)) {
			par2World.setBlock(x, y, z, Sorcery.airBlock);
			par2World.scheduleBlockUpdate(x, y, z, Sorcery.airBlock, 5);
		}
	}

	public boolean hasFuel(ItemStack item) {
		if(item.getItem() instanceof ItemLantern)
			if(this.getFuel(item) != 0)
				return true;
		return false;
	}
	
	public int getFuel(ItemStack item) {
		int fuel = 0;
		if(item.hasTagCompound())
			fuel = item.stackTagCompound.getInteger("fuel");
		else {
			item.stackTagCompound = new NBTTagCompound();
			item.stackTagCompound.setInteger("fuel", 0);
		}
		return fuel;
	}
	
	public void consumeFuel(ItemStack item, int amount) {
		int fuel = item.stackTagCompound.getInteger("fuel");
		item.stackTagCompound.setInteger("fuel", fuel - amount);
		if(item.stackTagCompound.getInteger("fuel") < 0)
			item.stackTagCompound.setInteger("fuel", 0);
	}
	
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
		par3List.add(this.getLanternWithFuel(1000));
		par3List.add(this.getLanternWithFuel(0));
	}

	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		par3List.add(StatCollector.translateToLocalFormatted(
				"sorcery.item.lantern.info", this.getFuel(par1ItemStack)));
	}
	
	public ItemStack getLanternWithFuel(int amount) {
		ItemStack item = new ItemStack(this);
		item.stackTagCompound = new NBTTagCompound();
		item.stackTagCompound.setInteger("fuel", amount);
		return item;
	}

	@Override
    public boolean isDamaged(ItemStack stack) {
        return true;
    }

	@Override
	public int getDisplayDamage(ItemStack stack) {
		return 1001 - (this.getFuel(stack) + 1);
	}

	@Override
	public FluidStack getFluid(ItemStack container) {
		if(this.getFuel(container) != 0)
			return new FluidStack(FluidRegistry.getFluid("glowstone"), this.getFuel(container));
		else return null;
	}

	@Override
	public int getCapacity(ItemStack container) {
		return 1000;
	}

	@Override
	public int fill(ItemStack container, FluidStack resource, boolean doFill) {
		int freeSpace = 1000 - this.getFuel(container);
		int resourceAmount = resource.amount;
		if(freeSpace > resourceAmount)
			freeSpace = resourceAmount;
		
		resource.amount -= freeSpace;
		this.consumeFuel(container, -freeSpace);
		
		return freeSpace;
	}

	@Override
	public FluidStack drain(ItemStack container, int maxDrain, boolean doDrain) {
		int fuel = this.getFuel(container);
		if(fuel > maxDrain)
			fuel = maxDrain;
		
		return new FluidStack(FluidRegistry.getFluid("glowstone"), fuel);
	}

	@Override
	public boolean hasCustomEntity(ItemStack stack) {
		return true;
	}

	@Override
	public Entity createEntity(World world, Entity location, ItemStack itemstack) {
		EntityLantern entity = new EntityLantern(world);
		entity.setFuel(this.getFuel(itemstack));
		entity.setPosition(location.posX, location.posY, location.posZ);
		entity.motionX = location.motionX;
		entity.motionY = location.motionY;
		entity.motionZ = location.motionZ;
        
		return entity;
	}
}
