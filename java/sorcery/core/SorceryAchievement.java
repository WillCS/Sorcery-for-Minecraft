package sorcery.core;

import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;

public class SorceryAchievement extends Achievement {

	public SorceryAchievement(String name, int x, int y, ItemStack item, Achievement parent) {
		super("sorcery.achievemt." + name, name, x, y, item, parent);
	}

}
