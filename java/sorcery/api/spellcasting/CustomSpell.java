package sorcery.api.spellcasting;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import sorcery.api.SorceryAPI;
import sorcery.api.element.ElementStack;
import sorcery.api.spellcasting.SpellComponentBase.CastType;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CustomSpell {
	public int mojoCost;
	public ElementStack[] elements;
	public String name;
	
	public CustomSpell(String name, int mojoCost, ElementStack[] elements) {
		this.name = name;
		this.mojoCost = mojoCost;
		this.elements = elements;
	}

	public void cast(Spell spell, World world, EntityLivingBase entity, Wand wand) {
		
	}
	
	public String getDisplayName() {
		return StatCollector.translateToLocal("sorcery.spell." + this.name + ".name");
	}
	
	public String getDynamicDisplayName(Spell spell, World world, EntityLivingBase entity, Wand wand) {
		return this.getDisplayName();
	}
	
	public String getDescription() {
		return StatCollector.translateToLocal("sorcery.spell." + this.name + ".desc");
	}
	
	public CastType getCastType(Spell spell) {
		return CastType.charge;
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getSpellIcon(Spell spell) {
		return null;
	}
	
	public int getCastTime() {
		return this.mojoCost / 5;
	}
	
	public static CustomSpell getCustomSpellByName(String name) {
		for(int i = 0; i < SorceryAPI.customSpells.size(); i++) {
			if(name.equals(SorceryAPI.customSpells.get(i).name))
				return SorceryAPI.customSpells.get(i);
		}
		
		return null;
	}

	public String getSpellCastSound() {
		return "sorcery:magic.spellBad";
	}
}
