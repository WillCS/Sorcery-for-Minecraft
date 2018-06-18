package sorcery.client.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import sorcery.entities.EntityUnicorn;
import sorcery.lib.Properties;
import sorcery.lib.utils.Utils;

public class RenderUnicorn extends RenderLiving {
	private static final ResourceLocation texture = Utils.getResource(Properties.TEXTURE_FOLDER + "mobs/unicorn.png");
	
	public RenderUnicorn(ModelBase par1ModelBase, float par2) {
		super(par1ModelBase, par2);
	}
	
	public void renderUnicorn(EntityUnicorn par1EntityUnicorn, double par2, double par4, double par6, float par8, float par9) {
		super.doRender(par1EntityUnicorn, par2, par4, par6, par8, par9);
	}
	
	public void doRenderLiving(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9) {
		this.renderUnicorn((EntityUnicorn)par1EntityLiving, par2, par4, par6, par8, par9);
	}
	
	public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
		this.renderUnicorn((EntityUnicorn)par1Entity, par2, par4, par6, par8, par9);
	}
	
	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return texture;
	}
}
