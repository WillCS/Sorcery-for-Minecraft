package sorcery.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;

public class EnchantmentFrost extends Enchantment {
	public EnchantmentFrost(int id) {
		super(id, 2, EnumEnchantmentType.weapon);
	}
	
	public int getMinLevel() {
		return 1;
	}
	
	public int getMaxLevel() {
		return 1;
	}
	
	public int getMinEnchantability(int par1) {
		return 10 + 20 * (par1 - 1);
	}
	
	public int getMaxEnchantability(int par1) {
		return super.getMinEnchantability(par1) + 50;
	}
	
	public boolean canApplyTogether(Enchantment par1Enchantment) {
		return super.canApplyTogether(par1Enchantment) && par1Enchantment.effectId != fireAspect.effectId;
	}
	
	@Override
    public String getName() {
        return "sorcery.enchantment." + this.name;
    }
}
