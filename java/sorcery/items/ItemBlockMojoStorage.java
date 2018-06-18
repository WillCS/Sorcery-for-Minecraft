package sorcery.items;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import sorcery.api.mojo.IMojoStorageItem;
import sorcery.api.mojo.MojoBattery;
import sorcery.core.Sorcery;
import sorcery.lib.utils.Utils;
import sorcery.tileentities.TileEntityMojoStorage;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBlockMojoStorage extends Item implements IMojoStorageItem {
	public ItemBlockMojoStorage() {
		this.setMaxDamage(1000);
		this.setMaxStackSize(1);
		this.setCreativeTab(Sorcery.tabSorceryTech);
	}

	@Override
	public IIcon getIconFromDamage(int par1) {
		return ((Block)Sorcery.machine).getIcon(1, par1);
	}

	@Override
	public int getMetadata(int par1) {
		return par1;
	}
	
	@Override
    public boolean showDurabilityBar(ItemStack stack) {
		if(stack.hasTagCompound())
			return !stack.stackTagCompound.getBoolean("cheaty");
    	return true;
    }
	
	@Override
	public boolean isItemTool(ItemStack par1ItemStack) {
		return false;
	}

	@Override
	public boolean isRepairable() {
		return false;
	}

	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		if(!stack.hasTagCompound()) {
			stack.stackTagCompound = new NBTTagCompound();
			stack.stackTagCompound.setInteger("capacity", 25000);
			stack.stackTagCompound.setInteger("amount", 0);
		}
		
		int capacity = stack.stackTagCompound.getInteger("capacity");
		int amount = stack.stackTagCompound.getInteger("amount");
		
		double percentage = (double)amount / (double)capacity;
		return 1 - percentage;
	}
	
	@Override
	public int getMojo(ItemStack item) {
		return this.getTagCompound(item).getInteger("amount");
	}

	@Override
	public int addMojo(ItemStack item, int amount) {
		this.getTagCompound(item).setInteger("amount", this.getMojo(item) + amount);
		
		if(this.getMojo(item) > this.getCapacity(item))
			this.setMojo(item, this.getCapacity(item));
		
		return this.getMojo(item);
	}

	@Override
	public int subtractMojo(ItemStack item, int amount) {
		this.getTagCompound(item).setInteger("amount", this.getMojo(item) - amount);
		
		if(this.getMojo(item) < 0)
			this.setMojo(item, 0);
		
		return this.getMojo(item);
	}

	@Override
	public int setMojo(ItemStack item, int amount) {
		this.getTagCompound(item).setInteger("amount", amount);
		
		return amount;
	}

	@Override
	public int getCapacity(ItemStack item) {
		return this.getTagCompound(item).getInteger("capacity");
	}
	
	public NBTTagCompound getTagCompound(ItemStack item) {
		if(!item.hasTagCompound())
			item.setTagCompound(new NBTTagCompound());
		
		return item.getTagCompound();
	}
	
	@Override
	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
		Block block = par3World.getBlock(par4, par5, par6);
		Block toPlace = Sorcery.mojoMachine;
		
		if(block == Blocks.snow_layer
				&& (par3World.getBlockMetadata(par4, par5, par6) & 7) < 1) {
			par7 = 1;
		} else if(block != Blocks.vine && block != Blocks.tallgrass
				&& block != Blocks.deadbush
				&& !block.isReplaceable(par3World, par4, par5, par6)) {
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
		}
		
		if(par1ItemStack.stackSize == 0) {
			return false;
		} else if(!par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7,
				par1ItemStack)) {
			return false;
		} else if(par5 == 255 && toPlace.getMaterial().isSolid()) {
			return false;
		} else if(par3World.canPlaceEntityOnSide(toPlace, par4,
				par5, par6, false, par7, par2EntityPlayer, par1ItemStack)) {
			int i1 = par1ItemStack.stackTagCompound.getInteger("damage");
			int j1 = toPlace.onBlockPlaced(par3World, par4, par5,
					par6, par7, par8, par9, par10, i1);
			
			if(placeBlockAt(par1ItemStack, par2EntityPlayer, par3World, par4, par5, par6, par7, par8, par9, par10, j1)) {
				par3World.playSoundEffect(
								(double)((float)par4 + 0.5F),
								(double)((float)par5 + 0.5F),
								(double)((float)par6 + 0.5F),
								toPlace.stepSound.func_150496_b(),
								(toPlace.stepSound.getVolume() + 1.0F) / 2.0F,
								toPlace.stepSound.getPitch() * 0.8F);
				--par1ItemStack.stackSize;
			}
			//par3World.markBlockForUpdate(par4, par5, par6);
			//par3World.markBlocksDirtyVertical(par4, par5, par6, par6);
			return true;
		} else {
			return false;
		}
	}
	
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata) {
		if(world.isRemote)
			return false;
		
		Block toPlace = Sorcery.mojoMachine;
		
		if(!world.setBlock(x, y, z, toPlace, stack.stackTagCompound.getInteger("damage"), 3) || metadata > 2)
			return false;
		
		if(world.getBlock(x, y, z) == toPlace) {
			toPlace.onBlockPlacedBy(world, x, y, z, player, stack);
			TileEntityMojoStorage tile = (TileEntityMojoStorage)world.getTileEntity(x, y, z);
			if(tile != null) {
				tile.mojo = new MojoBattery(stack.stackTagCompound.getInteger("capacity"));
				tile.mojo.setMojo(stack.stackTagCompound.getInteger("amount"));
				tile.cheaty = stack.stackTagCompound.getBoolean("cheaty");
			}
			toPlace.onPostBlockPlaced(world, x, y, z, 0);
			//world.markBlockForUpdate(x, y, z);
		}
		
		return true;
	}
	
	@Override
	public String getUnlocalizedName(ItemStack par1ItemStack) {
		if(par1ItemStack.hasTagCompound()) {
			int capacity = par1ItemStack.stackTagCompound.getInteger("capacity");
			return "sorcery.block.mojoStorage." + capacity;
		} else {
			par1ItemStack.stackTagCompound = new NBTTagCompound();
			par1ItemStack.stackTagCompound.setInteger("capacity", 25000);
			par1ItemStack.stackTagCompound.setInteger("amount", 0);
			return "sorcery.block.mojoStorage." + 25000;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		if(par1ItemStack.hasTagCompound()) {
			int capacity = par1ItemStack.stackTagCompound.getInteger("capacity");
			int amount = par1ItemStack.stackTagCompound.getInteger("amount");
			
			if(par1ItemStack.stackTagCompound.getBoolean("cheaty")) {
				par3List.add(StatCollector.translateToLocal("sorcery.block.mojoStorage.infinite"));
				par3List.add("");
				par3List.add(EnumChatFormatting.GOLD.toString() + 
						StatCollector.translateToLocal("sorcery.block.mojoStorage.zing"));
			} else {
				par3List.add(StatCollector.translateToLocalFormatted(
						"sorcery.block.mojoStorage.info", Utils.formatInteger(amount), Utils.formatInteger(capacity)));
			}
		} else {
			par1ItemStack.stackTagCompound = new NBTTagCompound();
			par1ItemStack.stackTagCompound.setInteger("capacity", 25000);
			par1ItemStack.stackTagCompound.setInteger("amount", 0);
			par3List.add(StatCollector.translateToLocalFormatted(
					"sorcery.block.mojoStorage.info", 0, 25000));
		}
	}
	
	@Override
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
		par3List.add(this.getItemStackWithCapacity(25000, 0));
		par3List.add(this.getFilledItemStackWithCapacity(25000, 25000, 0));
		
		par3List.add(this.getItemStackWithCapacity(250000, 1));
		par3List.add(this.getFilledItemStackWithCapacity(250000, 250000, 1));
		
		par3List.add(this.getItemStackWithCapacity(1500000, 2));
		par3List.add(this.getFilledItemStackWithCapacity(1500000, 1500000, 2));
		
		ItemStack cheaty = this.getFilledItemStackWithCapacity(1333337, 1333337, 2);
		cheaty.stackTagCompound.setBoolean("cheaty", true);
		par3List.add(cheaty);
	}
	
	public ItemStack getItemStackWithCapacity(int capacity, int itemDamage) {
		ItemStack ret = new ItemStack(this);
		ret.stackTagCompound = new NBTTagCompound();
		ret.stackTagCompound.setInteger("capacity", capacity);
		ret.stackTagCompound.setInteger("amount", 0);
		ret.stackTagCompound.setInteger("damage", itemDamage);
		
		return ret;
	}
	
	public ItemStack getFilledItemStackWithCapacity(int amount, int capacity, int itemDamage) {
		ItemStack ret = new ItemStack(this);
		ret.stackTagCompound = new NBTTagCompound();
		ret.stackTagCompound.setInteger("capacity", capacity);
		ret.stackTagCompound.setInteger("amount", amount);
		ret.stackTagCompound.setInteger("damage", itemDamage);
		
		return ret;
	}
}
