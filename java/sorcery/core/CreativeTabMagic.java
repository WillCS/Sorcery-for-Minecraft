package sorcery.core;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import sorcery.lib.SorceryItems;

public final class CreativeTabMagic extends CreativeTabs {
	public CreativeTabMagic(String par2Str) {
		super(par2Str);
	}
	
	public Item getTabIconItem() {
		if(this.getTabLabel().equals("sorceryMaterials"))
			return SorceryItems.gemOnyx;
		else if(this.getTabLabel().equals("sorceryTech"))
			return SorceryItems.cog;
		else if(this.getTabLabel().equals("sorceryTools"))
			return SorceryItems.phoenixBoots;
		else if(this.getTabLabel().equals("sorcerySpellcasting"))
			return SorceryItems.energyInfinite;

		return SorceryItems.battery;
	}
	
    public String getTranslatedTabLabel() {
        return "sorcery.itemGroup." + this.getTabLabel();
    }
}
