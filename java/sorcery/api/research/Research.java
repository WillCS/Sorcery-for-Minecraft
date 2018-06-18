package sorcery.api.research;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import sorcery.api.SorceryAPI;
import sorcery.api.spellcasting.SpellComponent;
import sorcery.api.spellcasting.SpellComponentBase;

public class Research {
	public ArrayList<ResearchNode> nodes = new ArrayList<ResearchNode>();
	public ArrayList<SpellComponent> customComponents = new ArrayList<SpellComponent>();
	
	public static Research readFromNBT(EntityPlayer player) {
		if(player == null || SorceryAPI.spellHelper.getPlayerSorceryCustomData(player) == null)
			return null;
		
		NBTTagList list = SorceryAPI.spellHelper.getPlayerSorceryCustomData(player).getTagList("research", 10);
		
		if(list.tagCount() == 0) {
			SorceryAPI.research.writeToNBT(SorceryAPI.spellHelper.getPlayerSorceryCustomData(player));
			list = SorceryAPI.spellHelper.getPlayerSorceryCustomData(player).getTagList("research", 10);
		}
		
		Research research = new Research();
		for(int i = 0; i < SorceryAPI.research.nodes.size(); i++) {
			research.nodes.add(SorceryAPI.research.nodes.get(i));
			
			for(int k = 0; k < list.tagCount(); k++) {
				ResearchNode temp = ResearchNode.readFromNBT((NBTTagCompound)list.getCompoundTagAt(k));
				if(temp != null && temp.title.equals(SorceryAPI.research.nodes.get(i).title))
					if(temp.unlocked || temp.autoUnlocked)
						research.nodes.get(i).unlocked = true;
			}
		}
		
		list = SorceryAPI.spellHelper.getPlayerSorceryCustomData(player).getTagList("customComponents", 10);
		for(int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound tag = list.getCompoundTagAt(i);
			SpellComponent component = SpellComponentBase.getComponent(
					SpellComponentBase.getComponentByName(tag.getString("type")));
			component.name = tag.getString("name");
			component.component.loadCustomDataFromNBT(component, tag.getCompoundTag("data"));
			research.customComponents.add(component);
		}
		
		return research;
	}
	
	public void writeToNBT(NBTTagCompound tag) {
		if(tag == null)
			return;
		
		NBTTagList list = new NBTTagList();
		for(int i = 0; i < this.nodes.size(); i++) {
			list.appendTag(this.nodes.get(i).writeToNBT());
		}
		
		NBTTagList list2 = new NBTTagList();
		for(int i = 0; i < this.customComponents.size(); i++) {
			NBTTagCompound temp = new NBTTagCompound();
			temp.setString("type", this.customComponents.get(i).component.getName());
			temp.setString("name", this.customComponents.get(i).name);
			temp.setTag("data", this.customComponents.get(i).customData);
			list2.appendTag(temp);
		}
		
		tag.setTag("research", list);
		tag.setTag("customComponents", list2);
	}
	
	public ArrayList<SpellComponent> getSpellComponents() {
		ArrayList<SpellComponent> ret = new ArrayList<SpellComponent>();
		
		for(int i = 0; i < this.nodes.size(); i++) {
			if(this.nodes.get(i) != null) {
				if(this.nodes.get(i).components != null) {
					for(int k = 0; k < this.nodes.get(i).components.size(); k++) {
						if(!this.nodes.get(i).components.get(k).shouldHaveCustomData()) {
							ret.add(SpellComponentBase.getComponent(this.nodes.get(i).components.get(k)));
						}
					}
				}
				
				if(this.nodes.get(i).hasSubNodes) {
					for(int k = 0; k < this.nodes.get(i).subNodes.size(); k++) {
						if(this.nodes.get(i).subNodes.get(k).components != null) {
							for(int j = 0; j < this.nodes.get(i).subNodes.get(k).components.size(); j++) {
								if(!this.nodes.get(i).subNodes.get(k).components.get(j).shouldHaveCustomData())
									ret.add(SpellComponentBase.getComponent(this.nodes.get(i).subNodes.get(k).components.get(j)));
							}
						}
					}
				}
			}
		}
		
		for(SpellComponent c : this.customComponents) {
			ret.add(c);
		}
		
		return ret;
	}
	
	public ArrayList<SpellComponent> getCustomComponentBases() {
		ArrayList<SpellComponent> ret = new ArrayList<SpellComponent>();
		for(int i = 0; i < this.nodes.size(); i++) {
			if(this.nodes.get(i) != null) {
				if(this.nodes.get(i).components != null) {
					for(int k = 0; k < this.nodes.get(i).components.size(); k++) {
						if(this.nodes.get(i).components.get(k).shouldHaveCustomData())
							ret.add(SpellComponentBase.getComponent(this.nodes.get(i).components.get(k)));
					}
				}
				
				if(this.nodes.get(i).hasSubNodes) {
					for(int k = 0; k < this.nodes.get(i).subNodes.size(); k++) {
						if(this.nodes.get(i).subNodes.get(k).components != null) {
							for(int j = 0; j < this.nodes.get(i).subNodes.get(k).components.size(); j++) {
								if(this.nodes.get(i).subNodes.get(k).components.get(j).shouldHaveCustomData())
									ret.add(SpellComponentBase.getComponent(this.nodes.get(i).subNodes.get(k).components.get(j)));
							}
						}
					}
				}
			}
		}
		return ret;
	}
	
	public static ResearchNode getNode(String name) {
		for(int i = 0; i < SorceryAPI.research.nodes.size(); i++) {
			if(SorceryAPI.research.nodes.get(i).title != null && SorceryAPI.research.nodes.get(i).title.equals(name))
				return SorceryAPI.research.nodes.get(i);
		}
		
		return null;
	}
}
