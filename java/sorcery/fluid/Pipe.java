package sorcery.fluid;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;

public class Pipe {
	public final EnumPipeType type;
	public int extra;
	public int extraSide;
	public int extraValue;
	public FluidStack fluid;
	
	public Pipe(EnumPipeType type) {
		this.type = type;
	}
	
	public static Pipe readFromNBT(NBTTagCompound tag) {
		int pipeID = tag.getInteger("type");
		FluidStack fluid = FluidStack.loadFluidStackFromNBT(tag.getCompoundTag("fluid"));
		
		Pipe pipe = new Pipe(EnumPipeType.getPipeType(pipeID));
		pipe.extra = tag.getInteger("extras");
		pipe.extraValue = tag.getInteger("extraValue");
		pipe.extraSide = tag.getInteger("extraSide");

		pipe.fluid = fluid;
		
		return pipe;
	}
	
	public void writeToNBT(NBTTagCompound tag) {
		if(this.type != null)
			tag.setInteger("type", this.type.id);

		tag.setInteger("extra", this.extra);
		tag.setInteger("extraValue", this.extraValue);
		tag.setInteger("extraSide", this.extraSide);
		
		if(this.fluid != null) {
			NBTTagCompound fluidTag = new NBTTagCompound();
			this.fluid.writeToNBT(fluidTag);
			tag.setTag("fluid", fluidTag);
		}
	}
}
