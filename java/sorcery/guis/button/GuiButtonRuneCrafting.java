package sorcery.guis.button;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import sorcery.client.render.RenderUtils;
import sorcery.lib.utils.Utils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiButtonRuneCrafting extends GuiButton {
	public final boolean field_73749_j;
	private static final ResourceLocation texture = Utils.getResource("guis/runecrafting.png");
	
	public GuiButtonRuneCrafting(int par1, int par2, int par3, boolean par4) {
		super(par1, par2, par3, 15, 10, "");
		this.field_73749_j = par4;
	}
	
	/** Draws this button to the screen. */
	public void drawButton(Minecraft par1Minecraft, int par2, int par3) {
		if(this.visible) {
			RenderUtils.bindTexture(texture);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			int posX = 176;
			int posY = 10;
			
			if(this.enabled) {
				posY = 0;
			}
			
			if(!this.field_73749_j) {
				posX += 15;
			}
			
			this.drawTexturedModalRect(this.xPosition, this.yPosition, posX, posY, this.width, this.height);
		}
	}
}
