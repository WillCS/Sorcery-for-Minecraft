package sorcery.guis.button;

import java.util.Iterator;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import sorcery.api.spellcasting.Spell;
import sorcery.client.render.RenderUtils;
import sorcery.lib.Properties;

public class GuiButtonSpell extends GuiButton {
	public Spell spell;
	private RenderItem itemRenderer = new RenderItem();
	
	public GuiButtonSpell(int ID, Spell thisSpell, int X, int Y) {
		super(ID, X, Y, 16, 16, "");
		this.spell = thisSpell;
	}
	
	@Override
	public void drawButton(Minecraft par1Minecraft, int par2, int par3) {
		RenderUtils.bindTexture(Properties.itemTexture);
		// GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		// this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, 0, 16,
		// 16);
		par1Minecraft.fontRenderer.drawString(this.spell.name, this.xPosition, this.yPosition, 4210752);
		this.spell.drawIcon();
		//this.drawTexturedModelRectFromIcon(this.xPosition, this.yPosition, Spell.IIcons[this.spell.spellID], 16, 16);
	}
	
	public boolean isMouseOverThisButton(int x, int y) {
		if(x > this.xPosition && x < this.xPosition + 16) {
			if(y > this.yPosition && y < this.yPosition + 16) {
				return true;
			}
		}
		return false;
	}
	
	protected void drawToolTip(List tooltip, int par2, int par3, Minecraft minecraft) {
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		RenderHelper.disableStandardItemLighting();
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		
		if(!tooltip.isEmpty()) {
			int var5 = 0;
			Iterator var6 = tooltip.iterator();
			
			while(var6.hasNext()) {
				String var7 = (String)var6.next();
				int var8 = minecraft.fontRenderer.getStringWidth(var7);
				
				if(var8 > var5) {
					var5 = var8;
				}
			}
			
			int var15 = par2 + 12;
			int var16 = par3 - 12;
			int var9 = 8;
			
			if(tooltip.size() > 1) {
				var9 += 2 + (tooltip.size() - 1) * 10;
			}
			
			this.zLevel = 300.0F;
			itemRenderer.zLevel = 300.0F;
			int var10 = -267386864;
			this.drawGradientRect(var15 - 3, var16 - 4, var15 + var5 + 3, var16 - 3, var10, var10);
			this.drawGradientRect(var15 - 3, var16 + var9 + 3, var15 + var5 + 3, var16 + var9 + 4, var10, var10);
			this.drawGradientRect(var15 - 3, var16 - 3, var15 + var5 + 3, var16 + var9 + 3, var10, var10);
			this.drawGradientRect(var15 - 4, var16 - 3, var15 - 3, var16 + var9 + 3, var10, var10);
			this.drawGradientRect(var15 + var5 + 3, var16 - 3, var15 + var5 + 4, var16 + var9 + 3, var10, var10);
			int var11 = 1347420415;
			int var12 = (var11 & 16711422) >> 1 | var11 & -16777216;
			this.drawGradientRect(var15 - 3, var16 - 3 + 1, var15 - 3 + 1, var16 + var9 + 3 - 1, var11, var12);
			this.drawGradientRect(var15 + var5 + 2, var16 - 3 + 1, var15 + var5 + 3, var16 + var9 + 3 - 1, var11, var12);
			this.drawGradientRect(var15 - 3, var16 - 3, var15 + var5 + 3, var16 - 3 + 1, var11, var11);
			this.drawGradientRect(var15 - 3, var16 + var9 + 2, var15 + var5 + 3, var16 + var9 + 3, var12, var12);
			
			for(int var13 = 0; var13 < tooltip.size(); ++var13) {
				String var14 = (String)tooltip.get(var13);
				
				minecraft.fontRenderer.drawStringWithShadow(var14, var15, var16, -1);
				
				if(var13 == 0) {
					var16 += 2;
				}
				
				var16 += 10;
			}
			
			this.zLevel = 0.0F;
			itemRenderer.zLevel = 0.0F;
		}
	}
}
