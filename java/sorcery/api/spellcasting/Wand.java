package sorcery.api.spellcasting;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import sorcery.api.spellcasting.WandComponent.WandComponentBody;
import sorcery.api.spellcasting.WandComponent.WandComponentCore;
import sorcery.api.spellcasting.WandComponent.WandComponentGenerator;
import sorcery.lib.SpellHelper;
import sorcery.lib.WandComponentRegistry;

public class Wand {
	public WandComponentBody body;
	public WandComponentGenerator generator;
	public WandComponentCore core;
	public int colour;
	
	public Wand() {
		this.colour = -1;
	}
	
	public ItemStack getBody() {
		if(this.body == null) return null;
		return this.body.item;
	}
	
	public ItemStack getGenerator() {
		if(this.generator == null) return null;
		return this.generator.item;
	}
	
	public ItemStack getCore() {
		if(this.core == null) return null;
		return this.core.item;
	}
	
	public int getColour() {
		return this.colour;
	}
	
	public void writeToNBT(NBTTagCompound tag, String name) {
		NBTTagCompound newTag = new NBTTagCompound();
		newTag.setInteger("colour", this.colour);
		
		newTag.setString("body", this.body != null ? this.body.name : "null");
		newTag.setString("core", this.core != null ? this.core.name : "null");
		newTag.setString("generator", this.generator != null ? this.generator.name : "null");
		
		tag.setTag(name, newTag);
	}
	
	public static Wand readFromNBT(NBTTagCompound tag, String name) {
		if(tag.hasNoTags())
			return null;
		
		NBTTagCompound newTag = tag.getCompoundTag(name);
		Wand ret = new Wand();
		ret.colour = newTag.getInteger("colour");
		if(newTag.getString("body") != "null")
			ret.body = (WandComponentBody)SpellHelper.getInstance().getWandComponentByName(newTag.getString("body"));
		if(newTag.getString("core") != "null")
			ret.core = (WandComponentCore)SpellHelper.getInstance().getWandComponentByName(newTag.getString("core"));
		if(newTag.getString("generator") != "null")
			ret.generator = (WandComponentGenerator)SpellHelper.getInstance().getWandComponentByName(newTag.getString("generator"));
		
		
		return ret;
	}
	
	public CastInfo modifyCastInfo(CastInfo info) {
		if(this.body != null)
			this.body.modifyCastInfo(info);
		if(this.generator != null)
			this.generator.modifyCastInfo(info);
		if(this.core != null)
			this.core.modifyCastInfo(info);
		
		return info;
	}
	
	@Override
	public String toString() {
		return "Wand: " + (this.body != null ? this.body.name : "no body") + ", "
						+ (this.core != null ? this.core.name : "no core") + ", "
						+ (this.generator != null ? this.generator.name : "no generator");
	}
}