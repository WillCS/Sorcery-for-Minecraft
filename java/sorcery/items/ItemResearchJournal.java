package sorcery.items;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import sorcery.api.SorceryAPI;
import sorcery.core.Sorcery;
import sorcery.lib.Properties;
import sorcery.lib.SorceryItems;
import sorcery.lib.SpellHelper;

public class ItemResearchJournal extends ItemArcane {

	public ItemResearchJournal() {
		setMaxStackSize(1);
	}
	
	@Override
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
		ItemStack cheatyBook = new ItemStack(SorceryItems.researchJournal, 1);
		cheatyBook.stackTagCompound = new NBTTagCompound();
		cheatyBook.stackTagCompound.setString("owner", "cheaty book");
		par3List.add(cheatyBook);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player) {
		if(!world.isRemote)
			player.openGui(Sorcery.instance, Properties.GUI_RESEARCH, player.worldObj, (int)player.posX, (int)player.posY, (int)player.posZ);
		
		return item;
	}
	
	@Override
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		if(par1ItemStack.hasTagCompound() && par1ItemStack.stackTagCompound.hasKey("owner")) {
			if(par1ItemStack.stackTagCompound.getString("owner").equals("cheaty book"))
				par3List.add(StatCollector.translateToLocal("sorcery.research.creative"));
			else
				par3List.add(String.format(StatCollector.translateToLocal("sorcery.research.ownedBy"), par1ItemStack.stackTagCompound.getString("owner")));
		}
	}

	public static ItemStack getBeginnerBook() {
		ItemStack item = new ItemStack(SorceryItems.researchJournal, 1);
		item.stackTagCompound = new NBTTagCompound();
		return item;
		
	}
}