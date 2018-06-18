package sorcery.client.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import sorcery.entities.EntityIncubatorEgg;
import sorcery.lib.Properties;
import sorcery.lib.utils.Utils;

public class RenderIncubatorEgg extends RenderLiving {
	private static ResourceLocation chickenTexture = Utils.getResource(Properties.TEXTURE_FOLDER + "entities/chickenEgg.png");
	private static ResourceLocation goldenTexture = Utils.getResource(Properties.TEXTURE_FOLDER + "entities/goldenEgg.png");
	private static ResourceLocation phoenixTexture = Utils.getResource(Properties.TEXTURE_FOLDER + "entities/phoenixEgg.png");
	
	public RenderIncubatorEgg(ModelBase par1ModelBase, float par2) {
		super(par1ModelBase, par2);
	}
	
	public void renderIncubatorEgg(EntityIncubatorEgg par1EntityIncubatorEgg, double par2, double par4, double par6, float par8, float par9) {
		super.doRender(par1EntityIncubatorEgg, par2, par4, par6, par8, par9);
	}
	
	public void doRenderLiving(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9) {
		this.renderIncubatorEgg((EntityIncubatorEgg)par1EntityLiving, par2, par4, par6, par8, par9);
	}
	
	public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
		this.renderIncubatorEgg((EntityIncubatorEgg)par1Entity, par2, par4, par6, par8, par9);
	}
	
	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		switch(((EntityIncubatorEgg)entity).eggType) {
			case 3:
				return phoenixTexture;
			case 2:
				return goldenTexture;
			default:
				return chickenTexture;
		}
	}
}
