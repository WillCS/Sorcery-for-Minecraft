package sorcery.api;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

/** @author Vroominator */
public interface ISpellBook {
	public ResourceLocation getSpellbookBackground(ItemStack item);
	public ResourceLocation getSpellbookForeground(ItemStack item);
	public boolean drawPagesSeperately(ItemStack item);
	public int colourBackground(ItemStack item);
}
