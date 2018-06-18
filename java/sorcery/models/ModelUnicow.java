package sorcery.models;

import net.minecraft.client.model.ModelCow;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

import org.lwjgl.opengl.GL11;

public class ModelUnicow extends ModelCow {
	ModelRenderer horn;
	
	public ModelUnicow() {
		super();
		horn = new ModelRenderer(this, 53, 8).setTextureSize(64, 32);
		horn.addBox(-0.5F, -9F, -3.5F, 1, 5, 1);
		horn.setRotationPoint(this.head.rotationPointX, this.head.rotationPointY, this.head.rotationPointZ);
	}
	
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotation(horn, this.head.rotateAngleX, this.head.rotateAngleY, this.head.rotateAngleZ);
		
		if(this.isChild) {
			GL11.glPushMatrix();
			GL11.glTranslatef(0.0F, this.field_78145_g * f5, this.field_78151_h * f5);
			horn.render(f5);
			GL11.glPopMatrix();
		} else
			horn.render(f5);
	}
	
	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}
