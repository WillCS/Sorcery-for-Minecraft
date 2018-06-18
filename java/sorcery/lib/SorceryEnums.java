package sorcery.lib;

import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;

public class SorceryEnums {
	public static ArmorMaterial armorNETHERRITE;
	public static ArmorMaterial armorINFERNITE;
	public static ArmorMaterial armorMAGIC;
	public static ArmorMaterial armorMISC;
	
	public static ToolMaterial toolNETHERRITE;
	public static ToolMaterial toolINFERNITE;
	
	public static void registerEnums() {
		armorNETHERRITE = EnumHelper.addArmorMaterial("NETHERRITE", 15, new int[]{2, 6, 5, 2}, 12);
		armorINFERNITE = EnumHelper.addArmorMaterial("INFERNITE", 40, new int[]{4, 9, 7, 4}, 20);
		armorMISC = EnumHelper.addArmorMaterial("MISC", 5, new int[]{0, 0, 0, 0}, 0);
		
		toolNETHERRITE = EnumHelper.addToolMaterial("NETHERRITE", 2, 250, 6.0F, 2, 16);
		toolINFERNITE = EnumHelper.addToolMaterial("INFERNITE", 4, 2342, 10.0F, 4, 20);
	}
}
