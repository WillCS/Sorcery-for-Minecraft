package sorcery.handlers;

import java.util.Random;

import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import sorcery.lib.SorceryItems;
import sorcery.lib.SpellHelper;
import cpw.mods.fml.common.registry.VillagerRegistry.IVillageTradeHandler;

public class VillagerTradingHandlerWizard implements IVillageTradeHandler {
	@Override
	@SuppressWarnings("unchecked")
	public void manipulateTradesForVillager(EntityVillager villager, MerchantRecipeList recipeList, Random random) {
		recipeList.clear();
		recipeList.add(new MerchantRecipe(new ItemStack(Items.emerald, 2 + random.nextInt(4)), new ItemStack(SorceryItems.phoenixegg)));
		recipeList.add(new MerchantRecipe(new ItemStack(Items.emerald, 2 + random.nextInt(2)), new ItemStack(SorceryItems.unicornhorn, 4 + random.nextInt(4))));
		recipeList.add(new MerchantRecipe(new ItemStack(Items.emerald, 1), new ItemStack(SorceryItems.energy, 4 + random.nextInt(6), 0)));
		recipeList.add(new MerchantRecipe(new ItemStack(Items.emerald, 1), new ItemStack(SorceryItems.magicDust, 4 + random.nextInt(6))));
		recipeList.add(new MerchantRecipe(new ItemStack(Items.emerald, 1), new ItemStack(Items.experience_bottle, 2 + random.nextInt(3))));
		recipeList.add(new MerchantRecipe(new ItemStack(Items.emerald, 2 + random.nextInt(4)), new ItemStack(SorceryItems.hardClay, 1, 9)));
		//recipeList.add(new MerchantRecipe(new ItemStack(Items.emerald, 5 + random.nextInt(10)), new ItemStack(Items.paper, 41), SpellHelper.instance.getPageFromSpellIDs(SpellHelper.instance.getTestificateSpellbookIDs())));
		recipeList.add(new MerchantRecipe(new ItemStack(Items.emerald, 2 + random.nextInt(2)), new ItemStack(SorceryItems.unicornhair, 2 + random.nextInt(2))));
	}
}