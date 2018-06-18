package sorcery.recipes;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import org.apache.logging.log4j.Level;

import sorcery.api.element.Element;
import sorcery.api.element.ElementStack;
import sorcery.api.recipe.IForgeRecipes;
import sorcery.api.recipe.MetalRegistry;
import sorcery.items.ItemMagicOrb;
import sorcery.lib.ItemStackHelper;
import sorcery.lib.Properties;
import sorcery.lib.SorceryItems;
import sorcery.lib.utils.Utils;
import sorcery.tileentities.TileEntityForge;

public class ForgeRecipes implements IForgeRecipes {
	private static final ForgeRecipes instance = new ForgeRecipes();
	
	private List<ForgeRecipe> recipeList = new ArrayList<ForgeRecipe>();
	
	/** Used to call methods addSmelting and getSmeltingResult. */
	public static final ForgeRecipes getInstance() {
		return instance;
	}
	
	public void addForgeRecipes() {
		this.addRecipe((new ItemStack(SorceryItems.hardClay, 1, 1)),
				(new ItemStack(SorceryItems.cog, 1, 0)), 20 * 20,
				new ItemStack[]{(new ItemStack(Items.iron_ingot, 3))});
		
		this.addRecipe((new ItemStack(SorceryItems.hardClay, 1, 1)),
				(new ItemStack(SorceryItems.cog, 1, 5)), 20 * 25,
				new ItemStack[]{(new ItemStack(SorceryItems.cog, 1, 0)), (new ItemStack(SorceryItems.ingotBrass, 3))});
		
		this.addRecipe((new ItemStack(SorceryItems.hardClay, 1, 1)),
				(new ItemStack(SorceryItems.cog, 1, 6)), 20 * 10,
				new ItemStack[]{(new ItemStack(SorceryItems.cog, 1, 5)), (new ItemStack(SorceryItems.ingotSteel, 3))});
		
		this.addRecipe((new ItemStack(SorceryItems.hardClay, 1, 1)),
				(new ItemStack(SorceryItems.cog, 1, 1)), 20 * 15,
				new ItemStack[]{(new ItemStack(SorceryItems.cog, 1, 0)), (new ItemStack(Items.gold_ingot, 3))});
		
		this.addRecipe((new ItemStack(SorceryItems.hardClay, 1, 1)),
				(new ItemStack(SorceryItems.cog, 1, 2)), 20 * 20,
				new ItemStack[]{(new ItemStack(SorceryItems.cog, 1, 1)), (new ItemStack(SorceryItems.ingotNetherrite, 3))});
		
		this.addRecipe((new ItemStack(SorceryItems.hardClay, 1, 1)),
				(new ItemStack(SorceryItems.cog, 1, 3)), 20 * 25,
				new ItemStack[]{(new ItemStack(SorceryItems.cog, 1, 2)), (new ItemStack(SorceryItems.ingotInfernite, 3))});

		this.addRecipe((new ItemStack(SorceryItems.hardClay, 1, 10)),
				(new ItemStack(SorceryItems.ingotBrass, 4, 0)), 20 * 20,
				new ItemStack[]{(new ItemStack(SorceryItems.ingotTin, 1)), (new ItemStack(SorceryItems.ingotCopper, 3))});
		
		if(Properties.enableSteelRecipe)
			this.addRecipe((new ItemStack(SorceryItems.hardClay, 1, 10)),
					(new ItemStack(SorceryItems.ingotSteel, 1, 0)), 20 * 40,
					new ItemStack[]{(new ItemStack(Items.iron_ingot, 1)), (new ItemStack(SorceryItems.dustCoal, 2, 0)),
						(new ItemStack(SorceryItems.dustLimestone, 4))});
		
		this.addRecipe((new ItemStack(SorceryItems.hardClay, 1, 10)),
				(new ItemStack(SorceryItems.ingotEnder, 1, 0)), 20 * 20,
				new ItemStack[]{(new ItemStack(SorceryItems.ingotSilver, 1)), (new ItemStack(SorceryItems.enderdust, 4))});
		
		this.addRecipe((new ItemStack(SorceryItems.hardClay, 1, 10)),
				(new ItemStack(SorceryItems.ingotMagic, 3, 0)), 20 * 20,
				new ItemStack[]{(new ItemStack(Items.gold_ingot, 1)), (new ItemStack(SorceryItems.ingotSilver, 1)),
					(new ItemStack(SorceryItems.ingotNetherrite, 1)), (new ItemStack(SorceryItems.magicDust, 4))});
		
		this.addRecipe((new ItemStack(SorceryItems.hardClay, 1, 11)),
				(new ItemStack(SorceryItems.plateMagic, 1, 0)), 20 * 45,
				new ItemStack[]{(new ItemStack(SorceryItems.ingotMagic, 3)), (new ItemStack(SorceryItems.ingotInfernite, 1))});
		
		this.addRecipe((new ItemStack(SorceryItems.hardClay, 1, 5)),
				(new ItemStack(SorceryItems.jewellery, 1, 0)), 20 * 20,
				new ItemStack[]{(new ItemStack(SorceryItems.ingotSilver, 2))});
		
		this.addRecipe((new ItemStack(SorceryItems.hardClay, 1, 5)),
				(new ItemStack(SorceryItems.jewellery, 1, 1)), 20 * 25,
				new ItemStack[]{(new ItemStack(SorceryItems.jewellery, 1, 0)), (new ItemStack(Items.gold_ingot, 2))});
		
		this.addRecipe((new ItemStack(SorceryItems.hardClay, 1, 5)),
				(new ItemStack(SorceryItems.jewellery, 1, 2)), 20 * 30,
				new ItemStack[]{(new ItemStack(SorceryItems.jewellery, 1, 1)), (new ItemStack(SorceryItems.ingotNetherrite, 2))});
		
		this.addRecipe((new ItemStack(SorceryItems.hardClay, 1, 5)),
				(new ItemStack(SorceryItems.jewellery, 1, 3)), 20 * 35,
				new ItemStack[]{(new ItemStack(SorceryItems.jewellery, 1, 2)), (new ItemStack(SorceryItems.ingotInfernite, 2))});
		
		for(int i = 1; i < Element.elementsList.length; i++) {
			if(Element.elementsList[i] != null)
				this.addRecipe((new ItemStack(SorceryItems.hardClay, 1, 9)),
						(new ItemStack(SorceryItems.magicOrb, 1, i)), 20 * 30,
						new ItemStack[]{(new ItemStack(Blocks.glass, 4)), (new ItemStack(SorceryItems.battery, 1)),
							(new ItemStack(SorceryItems.elementCrystal, 8, i))});
		}
		
		for(Element element : Element.elementsList) {
			if(element != null) {
				this.addRecipe((new ItemStack(SorceryItems.hardClay, 1, 9)),
						(((ItemMagicOrb)SorceryItems.magicOrb).getElementalItem(new ElementStack(element, 1000))), 20 * 30,
						new ItemStack[]{(new ItemStack(Blocks.glass, 4)), (new ItemStack(SorceryItems.battery, 1)),
							(new ItemStack(SorceryItems.elementCrystal, 8, element.ID))});
			}
		}
		
		this.addRecipe(new ItemStack(Items.bucket),
				new ItemStack(SorceryItems.bucketLiquidMagic, 1, 4), 20, 20 * 10,
				new ItemStack[]{new ItemStack(Blocks.glowstone, 1)});
		
		this.addRecipe(new ItemStack(SorceryItems.cog, 1, 32767),
				new ItemStack(SorceryItems.brassCast, 1, 1), 20 * 30,
				new ItemStack[]{new ItemStack(SorceryItems.ingotBrass, 3)});
		
		this.addRecipe(new ItemStack(SorceryItems.jewellery, 1, 32767),
				new ItemStack(SorceryItems.brassCast, 1, 5), 20 * 30,
				new ItemStack[]{new ItemStack(SorceryItems.ingotBrass, 3)});
		
		this.addRecipe(new ItemStack(SorceryItems.plateMagic),
				new ItemStack(SorceryItems.brassCast, 1, 11), 20 * 30,
				new ItemStack[]{new ItemStack(SorceryItems.ingotBrass, 3)});
		
		this.addRecipe(new ItemStack(SorceryItems.magicOrb, 1, 32767),
				new ItemStack(SorceryItems.brassCast, 1, 9), 20 * 30,
				new ItemStack[]{new ItemStack(SorceryItems.ingotBrass, 3)});

		for(int i = 0; i < ItemStackHelper.ingots.length; i++) {
			this.addRecipe(new ItemStack(ItemStackHelper.ingots[i], 1),
					new ItemStack(SorceryItems.brassCast, 1, 10), 20 * 30,
					new ItemStack[]{new ItemStack(SorceryItems.ingotBrass, 3)});
		}
		
		Utils.log(Level.INFO, recipeList.size() + " Forge Recipes");
	}
	
	public List<ForgeRecipe> getSmeltingList() {
		return this.recipeList;
	}
	
	/** Add a recipe for use in the Forge Block
	 * 
	 * @param mould
	 *            Itemstack that must be present in the mould slot
	 * @param result
	 *            Itemstack that is produced as a result of this recipe
	 * @param burnTime
	 *            How many ticks it takes for this recipe to smelt
	 * @param ingredients
	 *            Up to 4 ItemStacks that are used as ingredients for this
	 *            recipe */
	public void addRecipe(ItemStack mould, ItemStack result, int burnTime, ItemStack... ingredients) {
		ArrayList list = new ArrayList();
		ItemStack[] ingredientItems = ingredients;
		int ingredientAmount = ingredients.length;
		
		for(int i = 0; i < ingredientAmount; i++) {
			ItemStack ingredientToAdd = ingredientItems[i];
			list.add(ingredientToAdd);
		}
		
		int maxIndex = 0;
		
		for(int i = 0; i < ingredients.length; i++) {
			if(MetalRegistry.getMetal(ingredients[i]) != null) {
				maxIndex = i;
				break;
			}
		}
		
		for(int i = 0; i < ingredients.length; i++) {
			if(MetalRegistry.getMetal(ingredients[i]) != null)
				if(MetalRegistry.getMetal(ingredients[i]).meltingPoint > MetalRegistry.getMetal(ingredients[maxIndex]).meltingPoint)
					maxIndex = i;
		}
		
		int temperature = 50;
		if(MetalRegistry.getMetal(ingredients[maxIndex]) != null) {
			temperature = MetalRegistry.getMetal(ingredients[maxIndex]).meltingPoint;
		}
		
		this.recipeList.add(new ForgeRecipe(mould, result, temperature, burnTime, list));
	}
	
	public void addRecipe(ItemStack mould, ItemStack result, int temperature, int burnTime, ItemStack... ingredients) {
		ArrayList list = new ArrayList();
		ItemStack[] ingredientItems = ingredients;
		int ingredientAmount = ingredients.length;
		
		for(int i = 0; i < ingredientAmount; i++) {
			ItemStack ingredientToAdd = ingredientItems[i];
			list.add(ingredientToAdd);
		}
		
		this.recipeList.add(new ForgeRecipe(mould, result, temperature, burnTime, list));
	}
	
	public ForgeRecipe findMatchingRecipe(TileEntityForge tile) {
		try {
			int j = 0;
			
			for(int i = 0; i < 4; i++) {
				if(tile.inventory[i] != null) {
					j++;
				}
			}
			
			if(j == tile.howManyItemsInCookingSlots() && j != 0) {
				int k;
				int l;
				int ab;
				int m = 0;
				
				for(int i = 0; i < this.recipeList.size(); i++) {
					for(l = 0; l < ((ForgeRecipe)this.recipeList.get(i)).recipeIngredients.size(); l++) {
						if(ItemStackHelper.instance.doesInventoryContainItemStackInRange(tile.inventory, (ItemStack)(((ForgeRecipe)this.recipeList.get(i)).recipeIngredients.get(l)), 0, 4))
							m++;
					}
					if(m == ((ForgeRecipe)this.recipeList.get(i)).recipeIngredients.size()) {
						if(ItemStackHelper.getInstance().areItemsEqual(tile.inventory[4], (((ForgeRecipe)this.recipeList.get(i)).mould))) {
							return((ForgeRecipe)this.recipeList.get(i));
						} else if(ItemStackHelper.getInstance().areOreItemStacksEqual(tile.inventory[4], (((ForgeRecipe)this.recipeList.get(i)).mould))) {
							return((ForgeRecipe)this.recipeList.get(i));
						} else if(this.recipeList.get(i).mould.getItem().equals(SorceryItems.hardClay) && tile.inventory[4] != null && tile.inventory[4].getItem().equals(SorceryItems.brassCast)) {
							if(this.recipeList.get(i).mould.getItemDamage() == tile.inventory[4].getItemDamage()) {
								return(this.recipeList.get(i));
							}
						}
					} else {
						m = 0;
					}
				}
			}
			return null;
		} catch(Exception exception) {
			exception.printStackTrace();
		}
		return null;
	}
	
	public boolean isMatchingRecipeAvailable(TileEntityForge tile) {
		ForgeRecipe recipe = this.findMatchingRecipe(tile);
		
		return(recipe != null);
	}
}
