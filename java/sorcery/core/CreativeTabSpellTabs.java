package sorcery.core;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import sorcery.lib.SorceryItems;

public class CreativeTabSpellTabs extends CreativeTabs {
	public CreativeTabSpellTabs(String par2Str) {
		super(par2Str);
	}
	
	public Item getTabIconItem() {
		return new ItemStack(SorceryItems.spellTab, 1, 1).getItem();
	}
	
    public String getTranslatedTabLabel() {
        return "sorcery.itemGroup." + this.getTabLabel();
    }
}
