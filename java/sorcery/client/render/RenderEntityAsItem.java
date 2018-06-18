package sorcery.client.render;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import sorcery.lib.Properties;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderEntityAsItem extends Render {
	private Item item;
	private int IIconIndex;
	private int renderPass;
	
	public RenderEntityAsItem(Item item, int damage) {
		this.item = item;
		this.IIconIndex = damage;
		this.renderPass = 1;
	}
	
	public RenderEntityAsItem(Item item, int damage, int renderPass) {
		this.item = item;
		this.IIconIndex = damage;
		this.renderPass = renderPass;
	}
	
	public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
		IIcon IIcon = item.getIconFromDamageForRenderPass(this.IIconIndex, this.renderPass);
		if(IIcon != null) {
			GL11.glPushMatrix();
			GL11.glTranslatef((float)par2, (float)par4, (float)par6);
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			GL11.glScalef(0.5F, 0.5F, 0.5F);
			this.bindEntityTexture(par1Entity);
			Tessellator tessellator = Tessellator.instance;
			this.render(tessellator, IIcon);
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
			GL11.glPopMatrix();
		}
	}
	
	private void render(Tessellator par1Tessellator, IIcon par2IIcon) {
		float f = par2IIcon.getMinU();
		float f1 = par2IIcon.getMaxU();
		float f2 = par2IIcon.getMinV();
		float f3 = par2IIcon.getMaxV();
		float f4 = 1.0F;
		float f5 = 0.5F;
		float f6 = 0.25F;
		GL11.glRotatef(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
		par1Tessellator.startDrawingQuads();
		par1Tessellator.setNormal(0.0F, 1.0F, 0.0F);
		par1Tessellator.addVertexWithUV((double)(0.0F - f5), (double)(0.0F - f6), 0.0D, (double)f, (double)f3);
		par1Tessellator.addVertexWithUV((double)(f4 - f5), (double)(0.0F - f6), 0.0D, (double)f1, (double)f3);
		par1Tessellator.addVertexWithUV((double)(f4 - f5), (double)(f4 - f6), 0.0D, (double)f1, (double)f2);
		par1Tessellator.addVertexWithUV((double)(0.0F - f5), (double)(f4 - f6), 0.0D, (double)f, (double)f2);
		par1Tessellator.draw();
	}
	
	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return Properties.itemTexture;
	}
}
