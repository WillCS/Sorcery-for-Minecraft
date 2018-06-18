package sorcery.client.render;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import sorcery.models.ModelLantern;
import sorcery.tileentities.TileEntityLantern;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderLanternTile extends TileEntitySpecialRenderer {
	private ModelLantern model;
	
	public RenderLanternTile() {
		this.model = new ModelLantern();
	}
	
	public void renderLanternAt(TileEntityLantern tile, double x, double y, double z, float partialTickTime) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float)x + 0.5f, (float)y + 0.5f, (float)z + 0.5f);
		GL11.glEnable(GL11.GL_NORMALIZE);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		this.model.renderTileEntity(tile);
		GL11.glPopMatrix();
	}
	
	public void renderTileEntityAt(TileEntity par1TileEntity, double par2, double par4, double par6, float par8) {
		this.renderLanternAt((TileEntityLantern)par1TileEntity, par2, par4, par6, par8);
	}
}
