package sorcery.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;

public class EnchantmentInsight extends Enchantment {
	public EnchantmentInsight(int id) {
		super(id, 2, EnumEnchantmentType.weapon);
	}
	
	public int getMinLevel() {
		return 1;
	}
	
	public int getMaxLevel() {
		return 3;
	}
	
	public int getMinEnchantability(int par1) {
		return 15 + (par1 - 1) * 9;
	}
	
	public int getMaxEnchantability(int par1) {
		return super.getMinEnchantability(par1) + 50;
	}
	
	public boolean canApplyTogether(Enchantment par1Enchantment) {
		return super.canApplyTogether(par1Enchantment) && par1Enchantment.effectId != looting.effectId;
	}
	
	@Override
    public String getName() {
        return "sorcery.enchantment." + this.name;
    }
}
