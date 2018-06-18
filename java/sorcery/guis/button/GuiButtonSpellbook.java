package sorcery.guis.button;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import sorcery.client.render.RenderUtils;
import sorcery.lib.Properties;
import sorcery.lib.utils.Utils;

public class GuiButtonSpellbook extends GuiButton {
	public ItemStack spellbook;
	
	public GuiButtonSpellbook(int ID, ItemStack book, int X, int Y) {
		super(ID, X, Y, 32, 32, "");
		this.spellbook = book;
	}
	
	@Override
	public void drawButton(Minecraft par1Minecraft, int par2, int par3) {
		GL11.glPushMatrix();
		GL11.glColor3f(1.0F, 1.0F, 1.0F);
		//TODO Fix spellbook button rendering
		//RenderUtils.itemRenderer.renderItemIntoGUI(RenderUtils.fontRenderer, RenderUtils.renderEngine, this.spellbook, this.xPosition + 8, this.yPosition + 8);
		GL11.glPopMatrix();
	}
}
