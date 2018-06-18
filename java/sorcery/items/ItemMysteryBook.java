package sorcery.items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import sorcery.lib.ItemStackHelper;
import sorcery.lib.SorceryItems;

public class ItemMysteryBook extends ItemArcane {
	
	public ItemMysteryBook() {
	}
	
	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) {
		if(!(par3Entity instanceof EntityPlayer))
			return;
		
		((EntityPlayer)par3Entity).inventory.setInventorySlotContents(par4, this.getBook(par1ItemStack, par2World));
	}
	
	public ItemStack getBook(ItemStack item, World world) {
		if(!world.isRemote) {
			if(item.hasTagCompound() && item.stackTagCompound.hasKey("items")) {
				NBTTagList list = item.stackTagCompound.getTagList("items", 10);
				ArrayList<ItemStack> pool = new ArrayList<ItemStack>();
				
				for(int i = 0; i < list.tagCount(); i++) {
					int rarity = list.getCompoundTagAt(i).getInteger("rarity");
					ItemStack tempItem = ItemStack.loadItemStackFromNBT(list.getCompoundTagAt(i).getCompoundTag("item"));
					
					for(int j = 0; j < rarity; j++) {
						pool.add(tempItem);
					}
				}
				
				return pool.get(world.rand.nextInt(pool.size()));
			} else {
				return ItemStackHelper.instance.getRandomBookItem();
			}
		}
		return new ItemStack(SorceryItems.unknownBook, 1);
	}
	
	public static ItemStack getBookWithItems(HashMap<ItemStack, Integer> items) {
		ItemStack ret = new ItemStack(SorceryItems.dustyBook);
		ret.stackTagCompound = new NBTTagCompound();
		NBTTagList list = new NBTTagList();
		
		Iterator iterator = items.entrySet().iterator();
		
		while(iterator.hasNext()) {
			Map.Entry<ItemStack, Integer> e = (Map.Entry<ItemStack, Integer>)iterator.next();
			ItemStack stack = e.getKey();
			int rarity = e.getValue();
			NBTTagCompound tag = new NBTTagCompound();
			tag.setTag("item", stack.writeToNBT(new NBTTagCompound()));
			tag.setInteger("rarity", rarity);
			list.appendTag(tag);
		}
		
		ret.stackTagCompound.setTag("items", list);
		return ret;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player) {
		return this.getBook(item, world);
	}
	
	@Override
	public void onCreated(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		par1ItemStack = this.getBook(par1ItemStack, par2World);
	}
}
