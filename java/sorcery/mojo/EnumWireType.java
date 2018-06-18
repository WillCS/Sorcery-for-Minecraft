package sorcery.mojo;

import net.minecraft.util.ResourceLocation;
import sorcery.lib.Properties;
import sorcery.lib.utils.Utils;

public enum EnumWireType {
	SILVER(10),
	GOLD(25),
	NETHERRITE(50);
	
	public final int id;
	public final int capacity;
	public final ResourceLocation texture;
	
	private EnumWireType(int capacity) {
		this.id = Properties.enumWireIds++;
		this.capacity = capacity;
		this.texture = Utils.getResource("textures/entities/wire" + this.id + ".png");
	}
	
	public static EnumWireType getWireTypeFrom(int id) {
		for(EnumWireType type : EnumWireType.values()) {
			if(type.id == id)
				return type;
		}
		
		return SILVER;
	}
}
