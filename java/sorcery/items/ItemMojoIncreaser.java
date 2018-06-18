package sorcery.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import sorcery.core.Sorcery;
import sorcery.lib.Properties;
import sorcery.lib.SorceryItems;
import sorcery.lib.SpellHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemMojoIncreaser extends ItemArcane {
	
	public ItemMojoIncreaser() {
		this.setHasSubtypes(true);
		this.setCreativeTab(Sorcery.tabSorcerySpellcasting);
		this.setMaxStackSize(1);
	}
	
	public IIcon[] icons;
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IIconRegister) {
		this.icons = new IIcon[256];
		String newName = this.getUnlocalizedName().replace("sorcery.item.", "");
		this.icons[0] = par1IIconRegister.registerIcon(Properties.ASSET_PREFIX + newName + 0);
	}
    
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining) {
		return this.icons[stack.getItemDamage()];
	}
	
	public IIcon getIconFromDamage(int i) {
		return this.icons[i];
	}

	@Override
    public String getItemStackDisplayName(ItemStack par1ItemStack) {
       return StatCollector.translateToLocal(this.getUnlocalizedName() + "." + par1ItemStack.getItemDamage() + ".name");
    }
	
	@Override
	public EnumRarity getRarity(ItemStack item) {
		if(!item.hasTagCompound()) {
			item.stackTagCompound = new NBTTagCompound();
		}
		
		int rarity = item.getTagCompound().getInteger("rarity");
		return EnumRarity.values()[rarity];
	}
	
	public int getAmountToIncrease(ItemStack item) {
		if(!item.hasTagCompound()) {
			item.stackTagCompound = new NBTTagCompound();
			item.stackTagCompound.setInteger("amount", 10);
		}
		
		return item.getTagCompound().getInteger("amount");
	}
	
	public int getMax(ItemStack item) {
		if(!item.hasTagCompound()) {
			item.stackTagCompound = new NBTTagCompound();
			item.stackTagCompound.setInteger("max", 250);
		}
		
		return item.getTagCompound().getInteger("max");
	}
	
	public static ItemStack getItem(int meta, int amount, int max, int rarity) {
		ItemStack ret = new ItemStack(SorceryItems.mojoIncreaser, 1, meta);
		NBTTagCompound tag = new NBTTagCompound();
		tag.setInteger("amount", amount);
		tag.setInteger("max", max);
		tag.setInteger("rarity", rarity);
		ret.stackTagCompound = tag;
		return ret;
	}
	
    @Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
    	int currentMax = SpellHelper.instance.getPlayerMaxMojo(par3EntityPlayer);
    	int maxForItem = this.getMax(par1ItemStack);
    	
    	if(currentMax >= maxForItem)
    		return par1ItemStack;
    	
		if(par1ItemStack.getItemDamage() == 0) {
			par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
		} else {
			int toAdd = this.getAmountToIncrease(par1ItemStack);
			if((currentMax + toAdd) > maxForItem)
				toAdd = maxForItem - (currentMax + toAdd);
			
			SpellHelper.instance.setPlayerMaxMojo(par3EntityPlayer, currentMax + toAdd);
			
			if(!par3EntityPlayer.capabilities.isCreativeMode)
				par1ItemStack.stackSize--;
			
			if(par2World.isRemote)
				par3EntityPlayer.addChatMessage(new ChatComponentText(StatCollector.translateToLocalFormatted(
						"sorcery.item.mojoIncreaser.message", currentMax + toAdd)));
		}
		
		return par1ItemStack;
	}
    
    
	
    @Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack) {
		if(par1ItemStack.getItemDamage() == 0)
			return EnumAction.drink;
		else return EnumAction.none;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		return 32;
	}

	@Override
	public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		int currentMax = SpellHelper.instance.getPlayerMaxMojo(par3EntityPlayer);
    	int maxForItem = this.getMax(par1ItemStack);
    	
    	if(currentMax >= maxForItem)
    		return par1ItemStack;

		int toAdd = this.getAmountToIncrease(par1ItemStack);
		if((currentMax + toAdd) > maxForItem)
			toAdd = maxForItem - (currentMax + toAdd);
		
		SpellHelper.instance.setPlayerMaxMojo(par3EntityPlayer, currentMax + toAdd);
		
		if(!par3EntityPlayer.capabilities.isCreativeMode)
			par1ItemStack.stackSize--;
		
		if(par2World.isRemote)
			par3EntityPlayer.addChatMessage(new ChatComponentText(StatCollector.translateToLocalFormatted(
					"sorcery.item.mojoIncreaser.message", currentMax + toAdd)));
		
		return par1ItemStack;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack item, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		par3List.add("");
		par3List.add(EnumChatFormatting.GOLD.toString() + StatCollector.translateToLocal("sorcery.item.mojoIncreaser.info.1"));
		par3List.add(StatCollector.translateToLocalFormatted("sorcery.item.mojoIncreaser.info.2", this.getAmountToIncrease(item)));
		par3List.add(StatCollector.translateToLocalFormatted("sorcery.item.mojoIncreaser.info.3", this.getMax(item)));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		list.add(this.getItem(0, 10, 250, 1));
	}
}
