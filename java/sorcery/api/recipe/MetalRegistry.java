package sorcery.api.recipe;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import sorcery.api.SorceryAPI;

public class MetalRegistry {
	private static ArrayList<MetalRegistry> metals = new ArrayList<MetalRegistry>();
	
	public final ItemStack ingot;
	
	public final ItemStack ore;
	
	public final FluidStack liquid;
	
	public final int meltingPoint;
	
	public MetalRegistry(ItemStack ingot, ItemStack ore, FluidStack liquid, int meltingPoint) {
		this.ingot = ingot;
		this.ore = ore;
		this.liquid = liquid;
		
		if(meltingPoint > 100)
			this.meltingPoint = 100;
		else if(meltingPoint <= 0)
			this.meltingPoint = 50;
		else
			this.meltingPoint = meltingPoint;
	}
	
	public MetalRegistry(ItemStack ingot, FluidStack liquid, int meltingPoint) {
		this(ingot, null, liquid, meltingPoint);
	}
	
	public static ArrayList<MetalRegistry> getMetals() {
		return metals;
	}
	
	public static void addMetal(MetalRegistry metal) {
		metals.add(metal);
	}
	
	public static MetalRegistry getMetal(ItemStack item) {
		for(int i = 0; i < metals.size(); i++) {
			if(metals.get(i).ingot != null)
				if(SorceryAPI.areOreItemsEqual(metals.get(i).ingot, item) || SorceryAPI.areSameItem(metals.get(i).ingot, item))
					return metals.get(i);
			
			if(metals.get(i).ore != null)
				if(SorceryAPI.areOreItemsEqual(metals.get(i).ore, item) || SorceryAPI.areSameItem(metals.get(i).ore, item))
					return metals.get(i);
		}
		
		return null;
	}
	
	public static MetalRegistry getMetal(FluidStack liquid) {
		for(int i = 0; i < metals.size(); i++) {
			if(metals.get(i).liquid != null)
				if(metals.get(i).liquid.isFluidEqual(liquid))
					return metals.get(i);
		}
		
		return null;
	}
}
