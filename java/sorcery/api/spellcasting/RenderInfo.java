package sorcery.api.spellcasting;

import java.util.Comparator;

import net.minecraft.world.World;

public class RenderInfo {
	public float red;
	public float green;
	public float blue;
	public float alpha;
	
	public RenderInfo(float r, float g, float b, float a) {
		this.red = r;
		this.green = g;
		this.blue = b;
		this.alpha = a;
	}
	
	public RenderInfo(float r, float g, float b) {
		this.red = r;
		this.green = g;
		this.blue = b;
		this.alpha = 1.0F;
	}
}
