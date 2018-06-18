package sorcery.api.spellcasting;

import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import sorcery.api.element.ElementStack;
import sorcery.api.spellcasting.SpellComponentBase.SpellAction;
import sorcery.api.spellcasting.SpellComponentBase.SpellAdjective;
import sorcery.api.spellcasting.SpellComponentBase.SpellEffect;
import sorcery.api.spellcasting.SpellComponentBase.SpellFocus;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class Spell {
	public SpellComponent<SpellAction> action;
	public SpellComponent<SpellFocus> focus;
	public SpellComponent<SpellEffect> effect;
	
	public boolean isCustomSpell;
	public String name;
	public String author;
	public CustomSpell customSpell;
	
	public void castSpell(World world, EntityLivingBase entity, Wand wand) {
		CastInfo info = this.getCastInfo();
		wand.modifyCastInfo(info);
		if(this.isCustomSpell)
			this.customSpell.cast(this, world, entity, wand);
		this.focus.component.cast(this, world, entity, wand);
	}

	public CastInfo getCastInfo() {
		if(this.isCustomSpell)
			return new CastInfo(this.customSpell.elements, this.customSpell.mojoCost);
		
		CastInfo info = new CastInfo(new ElementStack[0], 0);
		if(this.action != null)
			this.action.component.modifyCastInfo(this.action, info);
		
		if(this.effect != null) {
			this.effect.component.modifyCastInfo(this.effect, info);
			
			if(this.effect.condition != null)
				this.effect.condition.modifyCastInfo(this.effect, info);
		}
		
		if(this.focus != null) {
			this.focus.component.modifyCastInfo(this.focus, info);
		}
		
		return info;
	}
	
	public int getCastTime() {
		if(this.isCustomSpell)
			return this.customSpell.getCastTime();
		return this.getCastInfo().mojoCost / 5;
	}
	
	public boolean canBeCast(World world, EntityLivingBase entity, Wand wand) {
		if(!this.action.component.canCast(world, entity, wand)) return false;

		if(this.effect != null) {
			if(!this.effect.component.canCast(world, entity, wand)) return false;
		}
		
		if(!this.focus.component.canCast(world, entity, wand)) return false;
		for(int i = 0; i < this.focus.adjectives.length; i++) {
			if(!this.focus.adjectives[i].canCast(world, entity, wand)) return false;
		}
		
		return true;
	}
	
	@SideOnly(Side.CLIENT)
	RenderItem render = new RenderItem();
	
	@SideOnly(Side.CLIENT)
	public void drawIcon() {
		if(this.isCustomSpell) {
			render.renderIcon(0, 0, this.customSpell.getSpellIcon(this), 16, 16);
			return;
		}

		if(this.focus != null && this.focus.component.getSpellBaseIcon(this) != null)
			render.renderIcon(0, 0, this.focus.component.getSpellBaseIcon(this), 16, 16);

		if(this.effect != null) {
			if(this.effect != null)
				this.effect.component.drawIconOverlay(this);
		}
	}
	
	public static Spell readFromNBT(NBTTagCompound tag) {
		Spell spell = new Spell();
		spell.name = tag.getString("name");
		spell.author = tag.getString("author");
		
		if(tag.getBoolean("customSpell")) {
			spell.isCustomSpell = true;
			spell.customSpell = CustomSpell.getCustomSpellByName(spell.name);
			return spell;
		}
		
		NBTTagCompound actionTag = tag.getCompoundTag("action");
		if(actionTag != null && !actionTag.hasNoTags()) {
			spell.action = SpellComponentBase.getComponent(SpellComponentBase.getComponentByName(actionTag.getString("name")));
			if(actionTag.getBoolean("hasCustomData")) {
				spell.action.component.loadCustomDataFromNBT(spell.action, actionTag.getCompoundTag("customData"));
			}
		}
			
		NBTTagCompound effectTag = tag.getCompoundTag("effect");
		if(effectTag != null && !effectTag.hasNoTags()) {
			spell.effect = SpellComponentBase.getComponent(SpellComponentBase.getComponentByName(effectTag.getString("name")));
			spell.effect.magnitude = effectTag.getInteger("magnitude");
			spell.effect.condition = (SpellAdjective)SpellComponentBase.getComponentByName(effectTag.getString("condition"));
			if(effectTag.getBoolean("hasCustomData")) {
				spell.effect.component.loadCustomDataFromNBT(spell.effect, effectTag.getCompoundTag("customData"));
			}
		}
		
		NBTTagCompound focusTag = tag.getCompoundTag("focus");
		if(focusTag != null && !focusTag.hasNoTags()) {
			spell.focus = SpellComponentBase.getComponent(SpellComponentBase.getComponentByName(focusTag.getString("name")));
				spell.focus.magnitude = focusTag.getInteger("magnitude");
				if(focusTag.getBoolean("hasCustomData")) {
					spell.focus.component.loadCustomDataFromNBT(spell.focus, focusTag.getCompoundTag("customData"));
				}
				
				NBTTagList focusAdjList = focusTag.getTagList("adjs", 10);
				spell.focus.adjectives = new SpellAdjective[focusAdjList.tagCount()];
				for(int i = 0; i < focusAdjList.tagCount(); i++) {
					NBTTagCompound adjTag = (NBTTagCompound)focusAdjList.getCompoundTagAt(i);
					spell.focus.adjectives[i] = (SpellAdjective)SpellComponentBase.getComponentByName(adjTag.getString("name"));
				}
		}
		
		return spell;
	}
	
	public NBTTagCompound writeToNBT() {
		NBTTagCompound spellTag = new NBTTagCompound();
		
		if(this.isCustomSpell) {
			spellTag.setBoolean("customSpell", true);
			return spellTag;
		}
		
		NBTTagCompound actionCompound = new NBTTagCompound();
		if(this.action != null) {
			if(this.action.hasCustomData()) {
				actionCompound.setBoolean("hasCustomData", true);
				actionCompound.setTag("customData", this.action.component.writeCustomDataToNBT(this.action, new NBTTagCompound()));
			}
			actionCompound.setString("name", this.action.component.getName());
			spellTag.setTag("action", actionCompound);
		}
		
		if(this.effect != null) {
			NBTTagCompound effectCompound = new NBTTagCompound();
			if(this.effect.hasCustomData()) {
				effectCompound.setBoolean("hasCustomData", true);
				effectCompound.setTag("customData", this.effect.component.writeCustomDataToNBT(this.effect, new NBTTagCompound()));
			}
			
			effectCompound.setInteger("magnitude", this.effect.magnitude);
			effectCompound.setString("condition", this.effect.condition.getName());
			effectCompound.setString("name", this.effect.component.getName());
			
			spellTag.setTag("effect", effectCompound);
		}
		
		if(this.focus != null) {
			NBTTagCompound focusCompound = new NBTTagCompound();
			if(this.focus.hasCustomData()) {
				focusCompound.setBoolean("hasCustomData", true);
				focusCompound.setTag("customData", this.focus.component.writeCustomDataToNBT(this.focus, new NBTTagCompound()));
				focusCompound.setString("name", this.focus.component.getName());
			}
			focusCompound.setInteger("magnitude", this.focus.magnitude);
			focusCompound.setString("name", this.focus.component.getName());
			
			if(this.focus.adjectives != null) {
				NBTTagList focusAdjList = new NBTTagList();
				for(int i = 0; i < this.focus.adjectives.length; i++) {
					if(this.focus.adjectives[i] != null) {
						NBTTagCompound adjectiveCompound = new NBTTagCompound();
						adjectiveCompound.setString("name", this.focus.adjectives[i].getName());
						focusAdjList.appendTag(adjectiveCompound);
					}
				}
				focusCompound.setTag("adjs", focusAdjList);
			}
			
			spellTag.setTag("focus", focusCompound);
		}
		
		if(this.name != null && !this.name.isEmpty())
			spellTag.setString("name", this.name);
		
		if(this.author != null && !this.author.isEmpty())
			spellTag.setString("author", this.author);
		return spellTag;
	}

	public String getSpellCastSound() {
		if(this.isCustomSpell)
			return this.customSpell.getSpellCastSound();
		return "sorcery:magic.spellBad";
	}
}
