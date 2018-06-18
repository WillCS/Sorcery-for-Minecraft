package sorcery.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import sorcery.api.mojo.IMojoStorageItem;
import sorcery.lib.Properties;
import sorcery.lib.utils.Utils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBattery extends ItemArcane implements IMojoStorageItem {
	public static int[] capacities = {10000, 50000, 100000};
	public static IIcon[] icons;
	public ItemBattery() {
		this.setCreativeTab(CreativeTabs.tabTools);
		this.setMaxStackSize(1);
		this.setNoRepair();
		this.setHasSubtypes(true);
	}
	
	@Override
	public IIcon getIconFromDamage(int i) {
		if(this.icons[i] != null)
			return this.icons[i];
		else
			return this.icons[0];
	}

	@Override
	public void registerIcons(IIconRegister par1IIconRegister) {
		this.icons = new IIcon[256];
		this.icons[0] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "crystalEnder");
		this.icons[1] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + "crystalBlazing");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		list.add(this.getBattery(0, 0, false));
		list.add(this.getBattery(0, 10000, false));
		list.add(this.getBattery(1, 0, false));
		list.add(this.getBattery(1, 50000, false));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack item, EntityPlayer player, List list, boolean debug) {
		if(item.hasTagCompound()) {
			int capacity = item.stackTagCompound.getInteger("capacity");
			int amount = item.stackTagCompound.getInteger("mojo");
			
			if(item.stackTagCompound.getBoolean("cheaty")) {
				list.add(StatCollector.translateToLocal("sorcery.block.mojoStorage.infinite"));
			} else {
				list.add(StatCollector.translateToLocalFormatted(
						"sorcery.block.mojoStorage.info", Utils.formatInteger(amount), Utils.formatInteger(capacity)));
			}
		} else {
			item.stackTagCompound = new NBTTagCompound();
			item.stackTagCompound.setInteger("capacity", 1000);
			item.stackTagCompound.setInteger("mojo", 0);
			list.add(StatCollector.translateToLocalFormatted("sorcery.block.mojoStorage.info", 0, 1000));
		}
	}
	
	public ItemStack getBattery(int meta, int mojo, boolean cheaty) {
		ItemStack ret = new ItemStack(this, 1, meta);
		ret.stackTagCompound = new NBTTagCompound();
		ret.stackTagCompound.setInteger("capacity", capacities[meta]);
		this.setMojo(ret, mojo);
		ret.stackTagCompound.setBoolean("cheaty", cheaty);
		
		return ret;
	}
	
	public ItemStack getCustomBattery(int meta, int capacity, int mojo, boolean cheaty) {
		ItemStack ret = new ItemStack(this, 1, meta);
		ret.stackTagCompound = new NBTTagCompound();
		ret.stackTagCompound.setInteger("capacity", capacity);
		this.setMojo(ret, mojo);
		ret.stackTagCompound.setBoolean("cheaty", cheaty);
		
		return ret;
	}
	
	public boolean isCheaty(ItemStack stack) {
		if(stack.hasTagCompound())
			return(stack.stackTagCompound.getBoolean("cheaty"));
		
		return false;
	}
	
	@Override
    public boolean showDurabilityBar(ItemStack stack) {
		return !this.isCheaty(stack);
    }
	
	@Override
	public boolean isItemTool(ItemStack par1ItemStack) {
		return false;
	}

	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		if(!stack.hasTagCompound()) {
			stack.stackTagCompound = new NBTTagCompound();
			stack.stackTagCompound.setInteger("capacity", capacities[stack.getItemDamage()]);
			stack.stackTagCompound.setInteger("mojo", 0);
		}
		
		int capacity = stack.stackTagCompound.getInteger("capacity");
		int amount = stack.stackTagCompound.getInteger("mojo");
		
		double percentage = (double)amount / (double)capacity;
		return 1 - percentage;
	}

	@Override
	public boolean isRepairable() {
		return false;
	}

	@Override
	public int getMojo(ItemStack item) {
		return this.getTagCompound(item).getInteger("mojo");
	}

	@Override
	public int addMojo(ItemStack item, int amount) {
		this.getTagCompound(item).setInteger("mojo", this.getMojo(item) + amount);
		
		if(this.getMojo(item) > this.getCapacity(item))
			this.setMojo(item, this.getCapacity(item));
		
		return this.getMojo(item);
	}

	@Override
	public int subtractMojo(ItemStack item, int amount) {
		this.getTagCompound(item).setInteger("mojo", this.getMojo(item) - amount);
		
		if(this.getMojo(item) < 0)
			this.setMojo(item, 0);
		
		return this.getMojo(item);
	}

	@Override
	public int setMojo(ItemStack item, int amount) {
		this.getTagCompound(item).setInteger("mojo", amount);
		
		return amount;
	}

	@Override
	public int getCapacity(ItemStack item) {
		if(this.getTagCompound(item).getInteger("capacity") == 0)
			this.getTagCompound(item).setInteger("capacity", capacities[item.getItemDamage()]);
		
		return this.getTagCompound(item).getInteger("capacity");
	}
	
	public NBTTagCompound getTagCompound(ItemStack item) {
		if(!item.hasTagCompound())
			item.setTagCompound(new NBTTagCompound());
		
		return item.getTagCompound();
	}
	
	@Override
    public String getItemStackDisplayName(ItemStack par1ItemStack) {
    	return StatCollector.translateToLocal(
    			this.getUnlocalizedName() + "." + par1ItemStack.getItemDamage() + ".name");
    }
}
