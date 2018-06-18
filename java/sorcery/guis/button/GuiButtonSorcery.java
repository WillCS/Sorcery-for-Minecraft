package sorcery.guis.button;

import org.lwjgl.opengl.GL11;

import sorcery.lib.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.ResourceLocation;

public class GuiButtonSorcery extends GuiButtonFixed {
	
	protected static final ResourceLocation buttonTextures = Utils.getResource("textures/guis/itemtransport.png");

	public GuiButtonSorcery(int par1, int par2, int par3, String par6Str) {
		super(par1, par2, par3, 18, 18, par6Str);
	}
	
	public GuiButtonSorcery(int par1, int par2, int par3, int par4, int par5, String par6Str) {
		super(par1, par2, par3, par4, par5, par6Str);
	}
	
	public void drawButton(Minecraft p_146112_1_, int p_146112_2_, int p_146112_3_) {
		GL11.glPushMatrix();
		if(this.visible) {
			FontRenderer fontrenderer = p_146112_1_.fontRenderer;
			p_146112_1_.getTextureManager().bindTexture(buttonTextures);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			this.field_146123_n = p_146112_2_ >= this.xPosition
					&& p_146112_3_ >= this.yPosition
					&& p_146112_2_ < this.xPosition + this.width
					&& p_146112_3_ < this.yPosition + this.height;
			int k = this.getHoverState(this.field_146123_n);
			
			if(this.enabled) {
	 			this.drawTexturedModalRect(this.xPosition, this.yPosition,
						182,  k * 18 - 18,
						this.width, this.height);
			} else {
				this.drawTexturedModalRect(this.xPosition, this.yPosition,
						238,  66,
						this.width, this.height);
			}
			
			this.mouseDragged(p_146112_1_, p_146112_2_, p_146112_3_);
			int l = 14737632;
			
			if(packedFGColour != 0) {
				l = packedFGColour;
			} else if(!this.enabled) {
				l = 10526880;
			} else if(this.field_146123_n) {
				l = 16777120;
			}
			
			this.drawCenteredString(fontrenderer, this.displayString,
					this.xPosition + this.width / 2, this.yPosition
					+ (this.height - 8) / 2, l);
		}
		GL11.glPopMatrix();
	}
}
