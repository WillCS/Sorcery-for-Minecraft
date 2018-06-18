package sorcery.fluid;

import net.minecraft.util.ResourceLocation;
import sorcery.lib.utils.Utils;

public class PipeHelper {
	public static Pipe[] pipes = new Pipe[256];
	public static String[] extras = new String[256];
	public static ResourceLocation[] textures = new ResourceLocation[256];
	
	public static ResourceLocation colours = Utils.getResource("textures/entities/pipeColour.png");
	
	public static Pipe getPipeFromID(int ID) {
		return pipes[ID];
	}
	
	public static void registerPipes() {
		pipes[0] = new Pipe(EnumPipeType.IRON);
		pipes[1] = new Pipe(EnumPipeType.BRASS);
		pipes[2] = new Pipe(EnumPipeType.STEEL);
		
		for(int i = 0; i < 256; i++) {
			if(EnumPipeType.getPipeType(i) != null) {
				String name = "pipe" + 
								EnumPipeType.getPipeType(i).name().substring(0, 1).toUpperCase() + 
								EnumPipeType.getPipeType(i).name().substring(1, EnumPipeType.getPipeType(i).name().length()) +
								".png";
				
				textures[i] = Utils.getResource("textures/entities/" + name);
			}
		}
	}
	
	public static void registerExtras() {
		extras[0] = "none";
		extras[1] = "valve";
		extras[2] = "comparator";
		extras[3] = "repeater";
		extras[4] = "filter";
		extras[5] = "pump";
	}
}
