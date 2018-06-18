package sorcery.fluid;

import sorcery.lib.Properties;

public enum EnumPipeType {
	IRON(1000),
	BRASS(1500),
	STEEL(2000);
	
	public final int capacity;
	public final int id;
	private EnumPipeType(int capacity) {
		this.id = Properties.enumPipeIds++;
		this.capacity = capacity;
	}
	
	public static EnumPipeType getPipeType(int id) {
		for(EnumPipeType type : EnumPipeType.values()) {
			if(type.id == id)
				return type;
		}
		
		return null;
	}
}
