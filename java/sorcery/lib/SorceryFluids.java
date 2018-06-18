package sorcery.lib;

import java.util.ArrayList;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidContainerRegistry.FluidContainerData;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class SorceryFluids {
	public static ArrayList<Fluid> fluids = new ArrayList<Fluid>();
	
	public static void registerFluids() {
		addFluid(new Fluid("mojo").setLuminosity(5));
		addFluid(new Fluid("milk").setDensity(600).setViscosity(300));
		addFluid(new Fluid("chocmilk").setDensity(600).setViscosity(300));
		addFluid(new Fluid("antidote").setDensity(600).setViscosity(300));
		addFluid(new Fluid("glowstone").setDensity(600).setViscosity(100).setLuminosity(3));
	}
	
	public static void registerFluidContainers() {
		FluidContainerRegistry.registerFluidContainer(new FluidContainerData(
				new FluidStack(FluidRegistry.getFluid("mojo"), FluidContainerRegistry.BUCKET_VOLUME), 
				new ItemStack(SorceryItems.bucketLiquidMagic, 1), new ItemStack(Items.bucket, 1)));
		FluidContainerRegistry.registerFluidContainer(new FluidContainerData(
				new FluidStack(FluidRegistry.getFluid("mojo"), FluidContainerRegistry.BUCKET_VOLUME / 2),
				new ItemStack(SorceryItems.mojoPotion, 1), new ItemStack(Items.glass_bottle, 1)));

		FluidContainerRegistry.registerFluidContainer(new FluidContainerData(
				new FluidStack(FluidRegistry.getFluid("milk"), FluidContainerRegistry.BUCKET_VOLUME),
				new ItemStack(Items.milk_bucket, 1), new ItemStack(Items.bucket, 1)));

		FluidContainerRegistry.registerFluidContainer(new FluidContainerData(
				new FluidStack(FluidRegistry.getFluid("chocmilk"), FluidContainerRegistry.BUCKET_VOLUME),
				new ItemStack(SorceryItems.bucketLiquidMagic, 1, 2), new ItemStack(Items.bucket, 1)));
		FluidContainerRegistry.registerFluidContainer(new FluidContainerData(
				new FluidStack(FluidRegistry.getFluid("chocmilk"), FluidContainerRegistry.BUCKET_VOLUME / 2),
				new ItemStack(SorceryItems.milkChocolate, 1), new ItemStack(Items.glass_bottle, 1)));

		FluidContainerRegistry.registerFluidContainer(new FluidContainerData(
				new FluidStack(FluidRegistry.getFluid("antidote"), FluidContainerRegistry.BUCKET_VOLUME),
				new ItemStack(SorceryItems.bucketLiquidMagic, 1, 3), new ItemStack(Items.bucket, 1)));
		FluidContainerRegistry.registerFluidContainer(new FluidContainerData(
				new FluidStack(FluidRegistry.getFluid("antidote"), FluidContainerRegistry.BUCKET_VOLUME / 2),
				new ItemStack(SorceryItems.antidotePotion, 1), new ItemStack(Items.glass_bottle, 1)));
		
		FluidContainerRegistry.registerFluidContainer(new FluidContainerData(
				new FluidStack(FluidRegistry.getFluid("glowstone"), FluidContainerRegistry.BUCKET_VOLUME),
				new ItemStack(SorceryItems.bucketLiquidMagic, 1, 4), new ItemStack(Items.bucket, 1)));
		FluidContainerRegistry.registerFluidContainer(new FluidContainerData(
				new FluidStack(FluidRegistry.getFluid("glowstone"), FluidContainerRegistry.BUCKET_VOLUME),
				new ItemStack(SorceryItems.lanternFuel, 1), new ItemStack(Items.glass_bottle, 1)));
	}
	
	public static void addFluid(Fluid fluid) {
		if(!FluidRegistry.isFluidRegistered(fluid.getName())) {
			FluidRegistry.registerFluid(fluid);
			fluids.add(fluid);
		}
	}
	
	public static void registerFluidIIcons(IIconRegister IIconRegister) {
		for(int i = 0; i < fluids.size(); i++) {
			if(fluids.get(i) != null) {
				Fluid fluid = fluids.get(i);
				fluid.setIcons(IIconRegister.registerIcon(Properties.ASSET_PREFIX + "fluids/" + fluid.getName()));
			}
		}
		
	}
}
