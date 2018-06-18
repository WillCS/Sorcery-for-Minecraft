package sorcery.api.element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.nbt.NBTTagCompound;

/** Element equivalent of an ItemStack
 * 
 * @author Vroominator */
public class ElementStack {
	public Element element;
	
	public int amount;
	
	public ElementStack(int ID, int amount) {
		this.element = Element.elementsList[ID];
		this.amount = amount;
	}
	
	public ElementStack(String name, int amount) {
		this.element = Element.getElementFromName(name);
		this.amount = amount;
	}
	
	public ElementStack(Element element, int amount) {
		this.element = element;
		this.amount = amount;
	}
	
	public int getID() {
		return this.element.ID;
	}
	
	public ElementStack copy() {
		return new ElementStack(this.element, this.amount);
	}
	
	public void decrease() {
		this.amount--;
	}
	
	public void decrease(int i) {
		this.amount -= i;
	}
	
	public void increase() {
		this.amount++;
	}
	
	public void increase(int i) {
		this.amount += i;
	}
	
	public void writeToNBT(NBTTagCompound tag, String name) {
		NBTTagCompound stack = new NBTTagCompound();
		stack.setInteger("element", this.element.ID);
		stack.setInteger("amount", this.amount);
		
		tag.setTag(name, stack);
	}
	
	public static ElementStack readFromNBT(NBTTagCompound tag, String name) {
		if(tag.getCompoundTag(name).hasNoTags())
			return null;
		
		NBTTagCompound stack = tag.getCompoundTag(name);
		return new ElementStack(stack.getInteger("element"), stack.getInteger("amount"));
	}
	
	public static ElementStack[] mergeLists(ArrayList<ElementStack> list1, ArrayList<ElementStack> list2) {
		return mergeLists((ElementStack[])list1.toArray(), (ElementStack[])list2.toArray());
	}
	
	public static ElementStack[] mergeLists(ArrayList<ElementStack> list1, ElementStack[] list2) {
		return mergeLists((ElementStack[])list1.toArray(), list2);
	}
	
	public static ElementStack[] mergeLists(ElementStack[] list1, ElementStack[] list2) {
		HashMap<Element, Integer> stacks = new HashMap<Element, Integer>();
		for(ElementStack e : list1) {
			if(stacks.containsKey(e.element)) {
				stacks.put(e.element, stacks.get(e.element) + e.amount);
			} else {
				stacks.put(e.element, e.amount);
			}
		}
		
		for(ElementStack e : list2) {
			if(stacks.containsKey(e.element)) {
				stacks.put(e.element, stacks.get(e.element) + e.amount);
			} else {
				stacks.put(e.element, e.amount);
			}
		}
		ArrayList<ElementStack> ret = new ArrayList<ElementStack>();
		Iterator iterator = stacks.entrySet().iterator();
		while(iterator.hasNext()) {
			Map.Entry<Element, Integer> entry = (Entry<Element, Integer>)iterator.next();
			ret.add(new ElementStack(entry.getKey(), entry.getValue()));
		}
		
		return ret.toArray(new ElementStack[ret.size()]);
	}
	
	public static ElementStack[] sortList(ElementStack[] list) {
		for(int i = 0; i < list.length; i++) {
			for(int j = 0; j < list.length; j++) {
				if(i != j) {
					if(list[i] != null && list[j] != null) {
						if(list[i].element == list[j].element) {
							list[i].amount += list[j].amount;
							list[j].amount = 0;
						}
					}
				}
				if(list[j] != null && list[j].amount == 0)
					list[j] = null;
			}
		}
		
		ArrayList<ElementStack> tempList = new ArrayList<ElementStack>();
		for(ElementStack element : list) {
			if(element != null && element.amount != 0 && element.element != null)
				tempList.add(element);
		}
		
		return tempList.toArray(list);
	}
}
