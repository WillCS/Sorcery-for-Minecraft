package sorcery.core;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import sorcery.lib.SpellHelper;

public class CreativeTabSpellPages extends CreativeTabs {
	public CreativeTabSpellPages(String par2Str) {
		super(par2Str);
	}
	
	public Item getTabIconItem() {
		return SpellHelper.instance.getPageFromSpellIDs(1, 2).getItem();
	}
	
    public String getTranslatedTabLabel() {
        return "sorcery.itemGroup." + this.getTabLabel();
    }
}
