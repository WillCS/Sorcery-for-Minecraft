package sorcery.client.render;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import sorcery.entities.EntityLantern;
import sorcery.lib.Properties;
import sorcery.lib.utils.Utils;
import sorcery.models.ModelLantern;

public class RenderLanternEntity extends Render {
	private static final ResourceLocation texture = Utils.getResource(Properties.TEXTURE_FOLDER + "entities/lantern.png");
	private static final ModelLantern model = new ModelLantern();
	
	public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
		GL11.glPushMatrix();
		GL11.glTranslated(par2, par4, par6);
		EntityLantern lantern = (EntityLantern)par1Entity;
		GL11.glEnable(GL11.GL_NORMALIZE);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		model.renderEntity(lantern);
		GL11.glPopMatrix();
	}
	
	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return texture;
	}
}
