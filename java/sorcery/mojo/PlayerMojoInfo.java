package sorcery.mojo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import sorcery.api.spellcasting.Spell;

public class PlayerMojoInfo {
	public int mojo;
	public int maxMojo;
	public int burnout;
	public boolean canRegen;
	public HashMap<String, Integer> restricted;
	public Spell[] spells;
	
	public NBTTagCompound writeToNBT() {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setInteger("mojo", this.mojo);
		tag.setInteger("maxMojo", this.maxMojo);
		tag.setInteger("burnout", this.burnout);
		tag.setBoolean("canRegen", this.canRegen);
		
		NBTTagList list = new NBTTagList();
		if(this.restricted != null) {
			Iterator iterator = this.restricted.entrySet().iterator();
			while(iterator.hasNext()) {
				Map.Entry pairs = (Map.Entry)iterator.next();
				NBTTagCompound tempTag = new NBTTagCompound();
				tempTag.setString("name", (String)pairs.getKey());
				tempTag.setInteger("amount", (Integer)pairs.getValue());
				list.appendTag(tempTag);
			}
			tag.setTag("restricted", list);
		}
		
		list = new NBTTagList();
		if(this.spells != null) {
			for(int i = 0; i < this.spells.length; i++) {
				if(this.spells[i] != null)
					list.appendTag(this.spells[i].writeToNBT());
				else
					list.appendTag(new NBTTagCompound());
			}
			tag.setTag("spells", list);
		}
		
		return tag;
	}
	
	public static PlayerMojoInfo readFromNBT(NBTTagCompound tag) {
		PlayerMojoInfo info = new PlayerMojoInfo();
		info.mojo = tag.getInteger("mojo");
		info.maxMojo = tag.getInteger("maxMojo");
		info.burnout = tag.getInteger("burnout");
		info.canRegen = tag.getBoolean("canRegen");
		
		HashMap<String, Integer> ret = new HashMap();
		NBTTagList list = tag.getTagList("restricted", 10);
		for(int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound tempTag = (NBTTagCompound)list.getCompoundTagAt(i);
			ret.put(tempTag.getString("name"), tempTag.getInteger("amount"));
		}
		
		list = tag.getTagList("spells", 10);
		info.spells = new Spell[9];
		for(int i = 0; i < list.tagCount(); i++) {
			if(!list.getCompoundTagAt(i).hasNoTags())
				info.spells[i] = Spell.readFromNBT(list.getCompoundTagAt(i));
			else
				info.spells[i] = null;
		}
		
		return info;
	}
}
