package sorcery.lib;

import org.apache.logging.log4j.Level;

import net.minecraftforge.common.config.Configuration;
import sorcery.lib.utils.Utils;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class Config {
	public static Config instance = new Config();
	
	public void config(FMLPreInitializationEvent event) {
		Configuration cfg = new Configuration(event.getSuggestedConfigurationFile());
		try {
			cfg.load();
			configGeneral(cfg);
			configEnchants(cfg);
		} catch(Exception exception) {
			Utils.log(Level.ERROR, "Error loading config!");
		} finally {
			cfg.save();
		}
	}
	
	public void configGeneral(Configuration cfg) {
		String cat = Properties.GENERAL_CATEGORY;
		Properties.oreGenSilver = (cfg.get(cat, " Generate Silver Ore ", true).getBoolean(true));
		Properties.oreGenTin = (cfg.get(cat, " Generate Tin Ore ", true).getBoolean(true));
		Properties.oreGenCopper = (cfg.get(cat, " Generate Copper Ore ", true).getBoolean(true));
		Properties.oreGenNetherrite = (cfg.get(cat, " Generate Netherrite Ore ", true).getBoolean(true));
		Properties.oreGenOnyx = (cfg.get(cat, " Generate Onyx Ore ", true).getBoolean(true));
		Properties.oreGenInfernite = (cfg.get(cat, " Generate Infernite Ore ", true).getBoolean(true));
		Properties.biomeGenEnchantedForest = (cfg.get(cat, " Generate Enchanted Forests ", true).getBoolean(true));
		
		Properties.villagerWizardID = (cfg.get(cat, " Villager ID - Wizard ", 185).getInt());
		Properties.biomeEnchantedForestID = (cfg.get(cat, " Enchanted Forest Biome ID ", 230).getInt());
		
		Properties.enableSteelRecipe = (cfg.get(cat, " Enable the Sorcery Steel recipe ", true).getBoolean(true));
	}
	
	public void configEnchants(Configuration cfg) {
		String cat = Properties.ENCHANTMENT_CATEGORY;
		Properties.coolDownEnchantmentID = (cfg.get(cat, " Speed Casting ", 140).getInt());
		Properties.consumeLessEnchantmentID = (cfg.get(cat, " Elemental Conjuring ", 141).getInt());
		Properties.auraBoostEnchantmentID = (cfg.get(cat, " Aura Boost ", 142).getInt());
		Properties.insightEnchantmentID = (cfg.get(cat, " Insight ", 143).getInt());
		Properties.frostEnchantmentID = (cfg.get(cat, " Sub Zero", 144).getInt());
	}
}
