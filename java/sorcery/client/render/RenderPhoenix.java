package sorcery.client.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import sorcery.entities.EntityPhoenix;
import sorcery.lib.Properties;
import sorcery.lib.utils.Utils;

public class RenderPhoenix extends RenderLiving {
	private static final ResourceLocation texture = Utils.getResource(Properties.TEXTURE_FOLDER + "mobs/Phoenix.png");
	
	public RenderPhoenix(ModelBase par1ModelBase, float par2) {
		super(par1ModelBase, par2);
	}
	
	public void renderPhoenix(EntityPhoenix par1EntityPhoenix, double par2, double par4, double par6, float par8, float par9) {
		super.doRender(par1EntityPhoenix, par2, par4, par6, par8, par9);
	}
	
	public void doRenderLiving(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9) {
		this.renderPhoenix((EntityPhoenix)par1EntityLiving, par2, par4, par6, par8, par9);
	}
	
	protected float getWingRotation(EntityPhoenix par1EntityChicken, float par2) {
		float f1 = par1EntityChicken.field_70888_h + (par1EntityChicken.field_70886_e - par1EntityChicken.field_70888_h) * par2;
		float f2 = par1EntityChicken.field_70884_g + (par1EntityChicken.destPos - par1EntityChicken.field_70884_g) * par2;
		return (MathHelper.sin(f1) + 1.0F) * f2;
	}
	
	@Override
	protected float handleRotationFloat(EntityLivingBase par1EntityLivingBase, float par2) {
		return this.getWingRotation((EntityPhoenix)par1EntityLivingBase, par2);
	}
	
	public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
		this.renderPhoenix((EntityPhoenix)par1Entity, par2, par4, par6, par8, par9);
	}
	
	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return texture;
	}
}
