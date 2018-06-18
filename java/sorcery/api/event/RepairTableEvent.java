package sorcery.api.event;

import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.eventhandler.Cancelable;
import cpw.mods.fml.common.eventhandler.Event;

/** Called every time a repair table tries to repair an item. <br>
 *  Cancel if it should not be repaired, otherwise, set <code>repairCost</code> to
 *  the cost of the repair in Mojo.
 *  @author Vroominator */
@Cancelable
public class RepairTableEvent extends Event {
	public ItemStack item;
	public int repairCost = -1;
	
	public RepairTableEvent(ItemStack item) {
		this.repairCost = -1;
		this.item = item;
	}
}
