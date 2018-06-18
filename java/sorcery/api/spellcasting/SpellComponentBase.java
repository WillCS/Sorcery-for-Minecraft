package sorcery.api.spellcasting;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import sorcery.api.SorceryAPI;
import sorcery.api.element.ElementStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SpellComponentBase {
	private String name;
	public ComponentType type;
	
	public static void registerComponent(SpellComponentBase component, String name, ComponentType type) {
		component.name = name;
		component.type = type;
		SorceryAPI.components.add(component);
	}
	
	public static SpellComponentBase getComponentByName(String name) {
		for(int i = 0; i < SorceryAPI.components.size(); i++) {
			if(SorceryAPI.components.get(i).name.equals(name)) {
				return SorceryAPI.components.get(i);
			}
		}
		return null;
	}
	
	public static SpellComponent getComponent(SpellComponentBase b) {
		return new SpellComponent(b);
	}

	public static class SpellAction extends SpellComponentBase {
		public CastType getCastType(Spell spell) {
			return spell.focus.component.getCastType(spell);
		}
	}
	
	public static class SpellEffect extends SpellComponentBase {
		/** Called after the base Icon is rendered; use this for
		 *  drawing overlay Icons and modifiers */
		@SideOnly(Side.CLIENT)
		public void drawIconOverlay(Spell spell) {
			
		}
		
		/** Determines whether the spell's secondary action should call
		 *  once at the end or once every tick
		 * @return CastType.instant or CastType.continuous - returning anything else defaults to instant*/
		public CastType getCastType(Spell spell) {
			return CastType.instant;
		}
	}
	
	public static class SpellAdjective extends SpellComponentBase {
		/** Called before the base Icon is rendered; use this for
		 *  colouring and such */
		@SideOnly(Side.CLIENT)
		public void modifyIcon(Spell spell) {
			
		}
		
		/** Called after the base Icon is rendered; use this for
		 *  drawing overlay Icons and modifiers */
		@SideOnly(Side.CLIENT)
		public void drawIconOverlay(Spell spell) {
			
		}
	}
	
	public static class SpellFocus extends SpellComponentBase {
		public void cast(Spell spell, World world, EntityLivingBase entity, Wand wand) {
			for(int i = 0; i < SorceryAPI.spellActionHandlers.size(); i++) {
				SorceryAPI.spellActionHandlers.get(i).handleCast(spell, world, entity, wand);
			}
		}
		
		public CastType getCastType(Spell spell) {
			return CastType.charge;
		}
		
		/** Called to determine the Icon to use for this spell */
		@SideOnly(Side.CLIENT)
		public IIcon getSpellBaseIcon(Spell spell) {
			for(int i = 0; i < SorceryAPI.spellActionHandlers.size(); i++) {
				if(SorceryAPI.spellActionHandlers.get(i).getIcon(spell) != null)
					return SorceryAPI.spellActionHandlers.get(i).getIcon(spell);
			}
			
			return null;
		}

		@Override
		public int getMojoCost(SpellComponent component) {
			int ret = 0;
			
			if(component.adjectives != null) {
				for(SpellAdjective adj : component.adjectives) {
					if(adj != null)
						ret += (adj.getMojoCost(component) * component.magnitude);
				}
			}
			
			return ret;
		}

		@Override
		public ElementStack[] getElements(SpellComponent component) {
			ElementStack[] ret = null;
			if(component.adjectives != null) {
				for(SpellAdjective adj : component.adjectives) {
					if(adj != null) {
						ElementStack[] stacks = adj.getElements(component);
						for(int i = 0; i < stacks.length; i++) {
							if(ret == null)
								ret = new ElementStack[] {stacks[i]};
							else ret = ElementStack.mergeLists(ret, stacks);
						}
					}
				}
			}
			
			if(ret == null)
				ret = new ElementStack[] {};
			
			return ret;
		}
		
		public boolean needsAdjectives() {
			return true;
		}
		
		public boolean canHaveEffect() {
			return true;
		}
	}
	
	public static enum ComponentType {
		action,
		effect,
		effectCondition,
		focus,
		focusAdj
	}
	
	public static enum CastType {
		instant,
		charge,
		continuous,
		eot
	}
	
	public static enum WordUsage {
		past,
		present,
		future,
		adj
	}
	
	public String getProperUsage(WordUsage usage, Spell spell) {
		return "sorcery.spellcomponent." + this.name + ".usage." + usage.name();
	}
	
	public int getMojoCost(SpellComponent component) {
		return 0;
	}
	
	public ElementStack[] getElements(SpellComponent component) {
		return null;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public ComponentType getType() {
		return this.type;
	}
	
	public void drawIcon() {
		
	}
	
	/** Modify the spell's mojo and energy cost */
	public CastInfo modifyCastInfo(SpellComponent component, CastInfo info) {
		info.mojoCost += this.getMojoCost(component);
		info.elements = ElementStack.mergeLists(info.elements, this.getElements(component));
		
		return info;
	}
	
	/** Passed to all components before casting */
	public void modifySpellEntity(ISpellEntity entity, Spell spell) {
		
	}
	
	/** Called every render tick as long as the spell is active */
	public void onRenderTick(Spell spell, World world, float x, float y, float z, RenderInfo info) {
		
	}
	
	/** Called every tick as long as the spell is active */
	public void onSpellTick(Spell spell, World world, float x, float y, float z, SpellInfo info) {
		
	}
	
	public boolean isComponentCompatible(SpellComponentBase component) {
		return true;
	}
	
	public boolean canCast(World world, EntityLivingBase entity, Wand wand) {
		return true;
	}
	
	public boolean shouldHaveCustomData() {
		return false;
	}
	
	public void loadCustomDataFromNBT(SpellComponent component, NBTTagCompound tag) {
		component.customData = tag;
	}
	
	public NBTTagCompound writeCustomDataToNBT(SpellComponent component, NBTTagCompound tag) {
		return tag;
	}
}
