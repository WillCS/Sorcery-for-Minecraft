package sorcery.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.world.World;

public class ContainerResearchJournal extends Container {
	
	public ContainerResearchJournal(InventoryPlayer inventoryPlayer, World world, int x, int y, int z) {
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}
	
	@Override
	public void onContainerClosed(EntityPlayer player) {
		super.onContainerClosed(player);
	}
}
