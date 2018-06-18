package sorcery.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.ItemStack;
import sorcery.items.ItemWand;

public class EnchantmentAuraBoost extends Enchantment {
	public EnchantmentAuraBoost(int id) {
		super(id, 4, EnumEnchantmentType.all);
	}
	
	public int getMinLevel() {
		return 1;
	}
	
	public int getMaxLevel() {
		return 2;
	}
	
	public int getMinEnchantability(int par1) {
		return (par1 - 1) * 8;
	}
	
	public int getMaxEnchantability(int par1) {
		return super.getMinEnchantability(par1) + 50;
	}
	
	@Override
	public boolean canApply(ItemStack stack) {
		return stack.getItem() instanceof ItemWand;
	}
	
	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack) {
		return stack.getItem() instanceof ItemWand;
	}
	
	@Override
    public String getName() {
        return "sorcery.enchantment." + this.name;
    }
}
