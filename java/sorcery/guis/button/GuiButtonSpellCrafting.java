package sorcery.guis.button;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;

import org.lwjgl.opengl.GL11;

import sorcery.api.spellcasting.SpellComponent;
import sorcery.api.spellcasting.SpellComponentBase;
import sorcery.api.spellcasting.SpellComponentBase.ComponentType;
import sorcery.tileentities.TileEntityDesk;

public class GuiButtonSpellCrafting extends GuiButtonFixed {
	public ComponentType type;
	public TileEntityDesk desk;
	
	public GuiButtonSpellCrafting(int ID, ComponentType type, int X, int Y, TileEntityDesk desk) {
		super(ID, X, Y, 16, 16, "");
		this.type = type;
		this.desk = desk;
	}
	
	@Override
	public void drawButton(Minecraft par1Minecraft, int par2, int par3) {
		GL11.glPushMatrix();
		GL11.glColor3f(1.0F, 1.0F, 1.0F);
		if(this.desk.spell == null || this.isButtonActive()) {
			
		} else {
			this.getComponent().drawIcon();
		}
		GL11.glPopMatrix();
	}
	
	public boolean isButtonActive() {
		return this.getComponent() != null;
	}
	
	public SpellComponentBase getComponent() {
		if(this.desk.spell == null) return null;
		
		switch(this.type) {
			case focus: 			return this.desk.spell.focus.component;
			case action: 			return this.desk.spell.action.component;
			case effect:		 	return this.desk.spell.effect.component;
			default:				return null;
		}
	}
}
