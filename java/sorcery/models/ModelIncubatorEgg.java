package sorcery.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelIncubatorEgg extends ModelBase {
	// fields
	ModelRenderer egg;
	
	public ModelIncubatorEgg() {
		egg = new ModelRenderer(this, 0, 0);
		egg.addBox(-2F, -6F, -2F, 4, 6, 4);
		egg.setRotationPoint(0F, 23F, 0F);
		egg.setTextureSize(64, 32);
	}
	
	public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
		egg.render(par7);
	}
}
