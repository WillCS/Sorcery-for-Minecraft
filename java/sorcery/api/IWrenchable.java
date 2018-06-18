package sorcery.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/** Interface for tile entities that can interact with the Wand in wrench mode.
 *  @author Vroominator */
public interface IWrenchable {
	/** @return whether or not something has happened */
	boolean onWrenched(World world, EntityPlayer player, ItemStack item, int x, int y, int z, int side);
}
