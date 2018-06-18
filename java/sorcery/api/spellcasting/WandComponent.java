package sorcery.api.spellcasting;

import java.util.Iterator;
import java.util.Map;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import sorcery.lib.SpellHelper;
import sorcery.lib.WandComponentRegistry;

public class WandComponent {
	public ItemStack item;
	public final int tier;
	public final String name;
	
	private WandComponent(int tier, String name) {
		this.tier = tier;
		this.name = name;
	}
	
	public static class WandComponentBody extends WandComponent {
		public final ResourceLocation texture;

		public WandComponentBody(int tier, String name) {
			super(tier, name);
			this.texture = new ResourceLocation("sorceryapi:textures/wand/" + name + ".png");
		}
		
		public void modifyCastInfo(CastInfo info) {};
	}
	
	public static class WandComponentGenerator extends WandComponent {
		
		public WandComponentGenerator(int tier, String name) {
			super(tier, name);
		}
		
		public void modifyCastInfo(CastInfo info) {};
	}
	
	public static class WandComponentCore extends WandComponent {
		
		public WandComponentCore(int tier, String Iname) {
			super(tier, Iname);
		}
		
		public void modifyCastInfo(CastInfo info) {};
	}
	
	public static void addComponent(WandComponent component) {
		WandComponentRegistry.components.put(component.name, component);
	}
	
	public static WandComponent getWandComponentFromItem(ItemStack item) {
		Iterator iterator = SpellHelper.getInstance().getWandComponentsMap().entrySet().iterator();
		while(iterator.hasNext()) {
			Map.Entry pairs = (Map.Entry)iterator.next();
			WandComponent component = (WandComponent)pairs.getValue();
			
			if(component.item.getItem() == item.getItem() && component.item.getItemDamage() == item.getItemDamage()) {
				return component;
			}
		}
		return null;
	}
	
	public static boolean isItemWandComponent(ItemStack item) {
		return(getWandComponentFromItem(item) != null);
	}
	
	public static boolean isItemWandBody(ItemStack item) {
		if(!isItemWandComponent(item))
			return false;
		
		WandComponent component = getWandComponentFromItem(item);
		return(component instanceof WandComponentBody);
	}
	
	public static boolean isItemWandCore(ItemStack item) {
		if(!isItemWandComponent(item))
			return false;
		
		WandComponent component = getWandComponentFromItem(item);
		return(component instanceof WandComponentCore);
	}
	
	public static boolean isItemWandGenerator(ItemStack item) {
		if(!isItemWandComponent(item))
			return false;

		WandComponent component = getWandComponentFromItem(item);
		return(component instanceof WandComponentGenerator);
	}
}
