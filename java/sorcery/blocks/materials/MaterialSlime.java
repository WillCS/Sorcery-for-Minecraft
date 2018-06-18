package sorcery.blocks.materials;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class MaterialSlime extends Material {
	public MaterialSlime(MapColor par1MapColor) {
		super(par1MapColor);
	}
	
	public boolean getCanBlockGrass() {
		return false;
	}
}
