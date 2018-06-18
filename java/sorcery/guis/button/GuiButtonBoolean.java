package sorcery.guis.button;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import sorcery.client.render.RenderUtils;
import sorcery.lib.Properties;
import sorcery.lib.utils.Utils;

public class GuiButtonBoolean extends GuiButton {
	public IIcon IIcon;
	private boolean bool;
	private static ResourceLocation texture = Utils.getResource("textures/guis/itemtransport.png");
	
	public GuiButtonBoolean(int ID, int X, int Y, IIcon IIcon, boolean bool) {
		super(ID, X, Y, 18, 18, "");
		this.IIcon = IIcon;
		this.bool = bool;
	}
	
	@Override
	public void drawButton(Minecraft par1Minecraft, int par2, int par3) {
		RenderUtils.bindTexture(texture);
		if(this.bool)
			this.drawTexturedModalRect(this.xPosition, this.yPosition, 182, 18, 18, 18);
		else
			this.drawTexturedModalRect(this.xPosition, this.yPosition, 182, 0, 18, 18);
		
		RenderUtils.bindTexture(Properties.itemTexture);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		if(this.IIcon != null)
			this.drawTexturedModelRectFromIcon(this.xPosition + 1, this.yPosition + 1, this.IIcon, 16, 16);
	}
	
	public boolean isMouseOverThisButton(int x, int y) {
		if(x > this.xPosition && x < this.xPosition + 18) {
			if(y > this.yPosition && y < this.yPosition + 18) {
				return true;
			}
		}
		return false;
	}
}
