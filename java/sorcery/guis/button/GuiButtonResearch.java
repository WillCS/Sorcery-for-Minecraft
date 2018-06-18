package sorcery.guis.button;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import sorcery.api.research.ResearchNode;
import sorcery.client.render.RenderUtils;
import sorcery.guis.GuiResearch;
import sorcery.guis.GuiResearchJournal;
import sorcery.lib.Properties;
import sorcery.lib.utils.Utils;

public class GuiButtonResearch extends GuiButton {
	public ResearchNode research;
	public GuiResearch gui;
	
	public static ResourceLocation background = Utils.getResource("textures/guis/researchViewer.png");
	
	public GuiButtonResearch(int ID, ResearchNode research, int X, int Y, GuiResearch gui) {
		super(ID, X, Y, 18, 10, "");
		this.research = research;
		this.gui = gui;
	}
	
	@Override
	public void drawButton(Minecraft par1Minecraft, int par2, int par3) {
		GL11.glDisable(GL11.GL_LIGHTING);
		RenderUtils.bindTexture(this.background);
		
		int xU = 0;
		int yU = 205;
		
		if(this.isMouseInsideArea(par2, par3, this.xPosition, this.yPosition, this.width, this.height))
			xU = 23;
		
		if(this.id == 1)
			yU = 216;
		
		this.drawTexturedModalRect(this.xPosition, this.yPosition, xU, yU, 18, 10);
	}
	
	public boolean mousePressed(Minecraft par1Minecraft, int par2, int par3) {
		return this.enabled && this.visible && this.isMouseInsideArea(par2, par3, this.xPosition, this.yPosition, this.width, this.height);
	}
	 
	public boolean isMouseInsideArea(int mouseX, int mouseY, int areaLeft, int areaTop, int width, int height) {
		if(mouseX >= this.gui.res.getScaledWidth() / 2 + areaLeft && mouseX <= this.gui.res.getScaledWidth() / 2 + areaLeft + width)
			if(mouseY >= this.gui.res.getScaledHeight() / 2 + areaTop && mouseY <= this.gui.res.getScaledHeight() / 2 + areaTop + height)
				return true;

		return false;
	}
}
