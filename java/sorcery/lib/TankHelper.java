package sorcery.lib;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public class TankHelper {
	public static TankHelper instance = new TankHelper();
	
	public boolean canThisItemContainThisFluid(ItemStack item, FluidStack liquid) {
		if(FluidContainerRegistry.fillFluidContainer(liquid.copy(), item.copy()) != null) {
			return true;
		}
		return false;
	}
	
	public ItemStack GetFilledContainer(ItemStack item, FluidStack liquid) {
		return FluidContainerRegistry.fillFluidContainer(liquid.copy(), item.copy());
	}
	
	public boolean isTankAccessible(FluidTank tank) {
		if(tank == null)
			return false;
		if(tank.getFluid() == null)
			return false;
		return true;
	}
}
