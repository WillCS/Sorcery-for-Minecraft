package sorcery.lib;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import sorcery.api.spellcasting.ModelWandComponent;
import sorcery.api.spellcasting.WandComponent;
import sorcery.api.spellcasting.WandComponent.WandComponentBody;
import sorcery.api.spellcasting.WandComponent.WandComponentGenerator;
import sorcery.client.render.MagicRenderHelper;
import sorcery.lib.utils.Utils;
import sorcery.models.wand.ModelCrystalGeneratorComponent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class WandComponentRegistry {
	
	public static HashMap<String, WandComponent> components = new HashMap<String, WandComponent>();
	
	public static void registerComponents() {
		WandComponent.addComponent(
				new WandComponentBody(0, "wandBodyWood"));
		WandComponent.addComponent(
				new WandComponentBody(1, "wandBodyBone"));
		WandComponent.addComponent(
				new WandComponentBody(3, "wandBodyUnicorn"));
		WandComponent.addComponent(
				new WandComponentBody(3, "wandBodyBlaze"));
		
		WandComponent.addComponent(
				new WandComponentGenerator(0, "wandGeneratorCrystal"));
	}
	
	public static void registerItems() {
		SpellHelper.getInstance().getWandComponentByName("wandBodyWood").item = new ItemStack(Items.stick);
		SpellHelper.getInstance().getWandComponentByName("wandBodyBone").item = new ItemStack(Items.bone);
		SpellHelper.getInstance().getWandComponentByName("wandBodyUnicorn").item = new ItemStack(SorceryItems.unicornhorn);
		SpellHelper.getInstance().getWandComponentByName("wandBodyBlaze").item = new ItemStack(Items.blaze_rod);
		
		SpellHelper.getInstance().getWandComponentByName("wandGeneratorCrystal").item = new ItemStack(SorceryItems.battery);
	}
	
	public static ResourceLocation getWandComponentTexture(String tex) {
		return Utils.getResource("textures/wand/" + tex + ".png");
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerModelForComponent(ModelWandComponent model, String name) {
		MagicRenderHelper.models.put(name, model);
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerModels() {
		registerModelForComponent(new ModelCrystalGeneratorComponent(), "wandGeneratorCrystal");
	}
}
