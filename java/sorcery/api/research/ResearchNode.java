package sorcery.api.research;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import sorcery.api.spellcasting.SpellComponentBase;

public class ResearchNode extends Research {
	public String title;
	public String description;
	public String hint;
	public ResourceLocation icon;
	public ItemStack item;
	public int xCoord;
	public int yCoord;
	public boolean unlocked;
	public boolean autoUnlocked = false;
	public boolean hasSubNodes;
	public boolean hidden;
	public int tier;
	
	public ArrayList<SpellComponentBase> components = new ArrayList<SpellComponentBase>();
	public ArrayList<Page> pages = new ArrayList<Page>();
	public ArrayList<String> parents = new ArrayList<String>();
	public ArrayList<ResearchNode> subNodes = new ArrayList<ResearchNode>();
	
	public static ResearchNode readFromNBT(NBTTagCompound tag) {
		if(tag.hasNoTags())
			return null;
		
		ResearchNode node = Research.getNode(tag.getString("title"));
		
		if(node == null)
			return null;
		
		node.unlocked = tag.getBoolean("unlocked");
		
		if(node.autoUnlocked)
			node.unlocked = true;
		
		return node;
	}
	
	public NBTTagCompound writeToNBT() {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setString("title", this.title == null ? "_" : this.title);
		tag.setBoolean("unlocked", this.unlocked);
		
		return tag;
	}
}
