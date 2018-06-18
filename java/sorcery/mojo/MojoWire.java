package sorcery.mojo;

import java.util.HashMap;

import sorcery.api.mojo.IMojoStorage;

public class MojoWire {
	public EnumWireType type;
	
	public MojoWire(EnumWireType type) {
		this.type = type;
	}
	
	public int getCapacity() {
		return this.type.capacity;
	}
}
