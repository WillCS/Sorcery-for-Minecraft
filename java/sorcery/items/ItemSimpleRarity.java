package sorcery.items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import sorcery.lib.Properties;

public class ItemSimpleRarity extends ItemArcane {
	private EnumRarity rarity;
	
	public ItemSimpleRarity(EnumRarity rarity) {
		this.rarity = rarity;
	}

	@Override
	public EnumRarity getRarity(ItemStack par1ItemStack) {
		return this.rarity;
	}
}
