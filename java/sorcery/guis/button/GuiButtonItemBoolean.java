package sorcery.guis.button;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import sorcery.client.render.RenderUtils;
import sorcery.lib.Properties;
import sorcery.lib.utils.Utils;
import net.minecraft.item.ItemStack;

public class GuiButtonItemBoolean extends GuiButton {
	public ItemStack Icon;
	public boolean bool;
	public static ResourceLocation texture = Utils.getResource("textures/guis/itemtransport.png");
	
	public GuiButtonItemBoolean(int id, int X, int Y, ItemStack Icon, boolean bool) {
		super(id, X, Y, 18, 18, "");
		this.Icon = Icon;
		this.bool = bool;
		this.zLevel = 100.0F;
	}
	
	@Override
	public void drawButton(Minecraft par1Minecraft, int par2, int par3) {
		GL11.glPushMatrix();
		RenderUtils.bindTexture(Properties.itemTexture);
		if(this.Icon != null)
			RenderUtils.itemRenderer.renderItemIntoGUI(
					par1Minecraft.fontRenderer, par1Minecraft.renderEngine, this.Icon, this.xPosition + 1, this.yPosition + 1);
		GL11.glPopMatrix();
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
