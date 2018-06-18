package sorcery.api;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface ITinkeringReclaimable {

	/** @return the items <code>item</code> should disassemble into when reclaimed */
	ArrayList<ItemStack> reclaim(ItemStack item, EntityPlayer player);
	
	/** @return whether or not <code>item</code> can be reclaimed */
	boolean canReclaim(ItemStack item);
	
}
