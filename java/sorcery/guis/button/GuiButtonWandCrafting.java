package sorcery.guis.button;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import sorcery.client.render.RenderUtils;
import sorcery.lib.utils.Utils;

class GuiButtonWandCrafting extends GuiButton {
	public boolean canUse;
	private static ResourceLocation texture = Utils.getResource("guis/wandcrafting.png");
	
	public GuiButtonWandCrafting(int par1, int par2, int par3, boolean par4) {
		super(par1, par2, par3, 20, 10, "");
		this.canUse = par4;
	}
	
	/** Draws this button to the screen. */
	public void drawButton(Minecraft par1Minecraft, int par2, int par3) {
		if(this.visible) {
			RenderUtils.bindTexture(texture);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			boolean var4 = par2 >= this.xPosition && par3 >= this.yPosition && par2 < this.xPosition + this.width && par3 < this.yPosition + this.height;
			int var5 = 0;
			int var6 = 176;
			
			if(!this.enabled) {
				var5 += this.height + 20;
			} else if(var4) {
				var5 = this.height;
			}
			
			if(!this.canUse) {
				var5 = this.height + 10;
			}
			
			this.drawTexturedModalRect(this.xPosition, this.yPosition, var6, var5, this.width, this.height);
		}
	}
}
