package sorcery.lib.utils;

import java.io.DataOutput;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import org.apache.logging.log4j.Level;

import sorcery.lib.Properties;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class Utils {
	public static DecimalFormat numberFormat = new DecimalFormat("#,###");
	
	public static void printInventoryLine(String string, TileEntity tile) {
		String side = tile.getWorldObj().isRemote ? "Client" : "Server";
		String tileName = StatCollector.translateToLocal(((IInventory)tile).getInventoryName());
		String tileCoords = tile.xCoord + ", " + tile.yCoord + ", " + tile.zCoord;
		
		log(Level.INFO, "[" + side + "]: " + tileName + " at " + tileCoords + ": " + string);
	}
	
	public static void log(Level level, String string) {
		FMLLog.log(level, "[Sorcery] " + string);
	}
	
	public static void addAchievementName(String ach, String name, String desc) {
		LanguageRegistry.instance().addStringLocalization("achievement." + ach, "en_US", name);
		LanguageRegistry.instance().addStringLocalization("achievement." + ach + ".desc", "en_US", desc);
	}
	
	public static int getNextAvailablePotionID() {
		for(int i = 1; i < Potion.potionTypes.length; i++) {
			if(Potion.potionTypes[i] == null) {
				return i;
			}
		}
		return -1;
	}
	
	/*public static int getNextAvailableBlockID() {
		for(int i = 0; i < Block.blocksList.length; i++) {
			if(Block.blocksList[i] == null) {
				return i;
			}
		}
		return -1;
	}
	
	public static int getNextAvailableItemID() {
		for(int i = 0; i < Item.itemsList.length; i++) {
			if(Item.itemsList[i] == null) {
				return i;
			}
		}
		return -1;
	}*/
	
	public static int getNextAvailableEnchantmentID() {
		for(int i = 0; i < Enchantment.enchantmentsList.length; i++) {
			if(Enchantment.enchantmentsList[i] == null) {
				return i;
			}
		}
		return -1;
	}
	
	public static void removeRecipe(ItemStack resultItem) {
		ItemStack recipeResult = null;
		ArrayList<IRecipe> recipes = ((ArrayList<IRecipe>)CraftingManager.getInstance().getRecipeList());
		for(int i = 0; i < recipes.size(); i++) {
			IRecipe tmpRecipe = (IRecipe)recipes.get(i);
			if(tmpRecipe instanceof ShapedRecipes) {
				ShapedRecipes recipe = (ShapedRecipes)tmpRecipe;
				recipeResult = recipe.getRecipeOutput();
			}
			if(tmpRecipe instanceof ShapelessRecipes) {
				ShapelessRecipes recipe = (ShapelessRecipes)tmpRecipe;
				recipeResult = recipe.getRecipeOutput();
			}
			if(ItemStack.areItemStacksEqual(resultItem, recipeResult)) {
				Utils.log(Level.INFO, "Removed Recipe: " + recipes.get(i) + " -> " + recipeResult);
				recipes.remove(i);
			}
		}
	}
	
	public static void addShapelessOreRecipe(ItemStack output, Object... params) {
		CraftingManager.getInstance().getRecipeList().add(new ShapelessOreRecipe(output, params));
	}
	
	public static void addOreRecipe(ItemStack output, Object... params) {
		CraftingManager.getInstance().getRecipeList().add(new ShapedOreRecipe(output, params));
	}
	
	public static boolean doesListContainObj(int obj, int[] list) {
		for(int i = 0; i < list.length; i++) {
			if(list[i] == obj)
				return true;
		}
		
		return false;
	}
	
	public static boolean isEntityInRange(Entity entity, Entity target) {
		AxisAlignedBB box = AxisAlignedBB.getBoundingBox(entity.posX - (2), entity.posY - (2), entity.posZ - (2), entity.posX + (2), entity.posY + (2), entity.posZ + (2));
		List<Entity> entities = entity.worldObj.getEntitiesWithinAABBExcludingEntity(entity, box);
		return(entities.contains(target));
	}
	
	public static GuiButton getGuiButtonWithID(List buttonList, int id) {
		for(int i = 0; i < buttonList.size(); i++) {
			if(((GuiButton)buttonList.get(i)).id == id)
				return((GuiButton)buttonList.get(i));
		}
		return null;
	}
	
	public static int encodeColour(float r, float g, float b, float alpha) {
		return ((int)(alpha * 255) << 24) + ((int)(r * 255) << 16) + ((int)(g * 255) << 8) + ((int)(b * 255));
	}
	
	public static int encodeColour(int r, int g, int b, int alpha) {
		return (alpha << 24) + (r << 16) + (g << 8) + b;
	}
	
	public static int encodeColour(int r, int g, int b) {
		return ((int)r << 16) + ((int)g << 8) + b;
	}
	
	public static float[] decodeColourF(int colour) {
		float[] rgba = new float[4];
		rgba[3] = (float)((float)(colour >> 24 & 255) / 255F);
		rgba[0] = (float)((float)(colour >> 16 & 255) / 255F);
		rgba[1] = (float)((float)(colour >> 8 & 255) / 255F);
		rgba[2] = (float)((float)(colour & 255) / 255F);
		
		return rgba;
	}
	
	public static double[] decodeColourD(int colour) {
		double[] rgba = new double[4];
		rgba[3] = (double)((double)(colour >> 24 & 255) / 255D);
		rgba[0] = (double)((double)(colour >> 16 & 255) / 255D);
		rgba[1] = (double)((double)(colour >> 8 & 255) / 255D);
		rgba[2] = (double)((double)(colour & 255) / 255D);
		
		return rgba;
	}
	
	public static String formatInteger(int num) {
		return numberFormat.format(num);
	}
	
	public void chatWith(EntityPlayer player, String msg) {
		player.addChatMessage(new ChatComponentText(msg));
	}
	
	public static ResourceLocation getResource(String string) {
		return new ResourceLocation(Properties.ASSET_PREFIX + string);
	}
	
	public static String[] splitStringWithWrapping(String string, int lineLimit) {
		char[] chars = string.toCharArray();
		ArrayList<String> lines = new ArrayList<String>();
		boolean finished = false;
		int start = 0;
		int end = start;
		while(start < chars.length - 1) {
		    int charCount = 0;
		    int lastSpace = 0;
		    while(charCount < lineLimit) {
		        if(chars[charCount+start] == ' ')
		            lastSpace = charCount;
		        
		        charCount++;
		        if(charCount + start == string.length()) {
		            finished = true;
		            break;
		        }
		    } 
		    end = finished ? string.length() : (lastSpace > 0) ? lastSpace + start : charCount + start;
		    lines.add(string.substring(start, end));
		    start = end + 1;
		}
		
		return (String[])lines.toArray();
	}
	
	public static void writeNBTTagCompoundToDataOutput(NBTTagCompound par0NBTTagCompound, DataOutput par1DataOutput) throws IOException {
		if(par0NBTTagCompound == null) {
			par1DataOutput.writeShort(-1);
		}else {
			byte[] abyte = CompressedStreamTools.compress(par0NBTTagCompound);
			par1DataOutput.writeShort((short)abyte.length);
			par1DataOutput.write(abyte);
		}
	}
}
