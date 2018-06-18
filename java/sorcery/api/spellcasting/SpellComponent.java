package sorcery.api.spellcasting;

import net.minecraft.nbt.NBTTagCompound;
import sorcery.api.spellcasting.SpellComponentBase.SpellAdjective;

public class SpellComponent<ComponentType extends SpellComponentBase> {
	public final ComponentType component;
	public NBTTagCompound customData;
	public SpellAdjective[] adjectives;
	public int magnitude = 0;
	public SpellAdjective condition;
	public String name;
	
	public SpellComponent(ComponentType component) {
		this.component = component;
	}
	
	public boolean hasCustomData() {
		return this.customData != null;
	}
}
