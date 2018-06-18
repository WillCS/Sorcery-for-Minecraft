package sorcery.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelCog extends ModelBase {
	ModelRenderer Body1;
	ModelRenderer Body2;
	ModelRenderer Body3;
	ModelRenderer Body4;
	ModelRenderer Body5;
	ModelRenderer Body6;
	ModelRenderer Tooth2;
	ModelRenderer Tooth1;
	ModelRenderer Tooth3;
	ModelRenderer Tooth4;
	ModelRenderer Tooth5;
	ModelRenderer Tooth6;
	ModelRenderer Tooth7;
	ModelRenderer Tooth8;
	
	public ModelCog() {
		textureWidth = 64;
		textureHeight = 32;
		
		Body1 = new ModelRenderer(this, 0, 0).setTextureSize(64, 32);
		Body1.addBox(-2F, -4F, 0F, 4, 8, 1);
		Body1.setRotationPoint(0F, 0F, 0F);
		setRotation(Body1, 0F, 0F, -0.7853982F);
		
		Body2 = new ModelRenderer(this, 0, 0).setTextureSize(64, 32);
		Body2.addBox(-2F, -4F, 0F, 4, 8, 1);
		Body2.setRotationPoint(0F, 0F, 0F);
		setRotation(Body2, 0F, 0F, 0.7853982F);
		
		Body3 = new ModelRenderer(this, 0, 0).setTextureSize(64, 32);
		Body3.addBox(-1.5F, -4.2F, 0F, 3, 2, 1);
		Body3.setRotationPoint(0F, 0F, 0F);
		setRotation(Body3, 0F, 0F, 0F);
		
		Body4 = new ModelRenderer(this, 0, 0).setTextureSize(64, 32);
		Body4.addBox(-4.2F, -1.4F, 0F, 2, 3, 1);
		Body4.setRotationPoint(0F, 0F, 0F);
		setRotation(Body4, 0F, 0F, 0F);
		
		Body5 = new ModelRenderer(this, 0, 0).setTextureSize(64, 32);
		Body5.addBox(-1.5F, 2.2F, 0F, 3, 2, 1);
		Body5.setRotationPoint(0F, 0F, 0F);
		setRotation(Body5, 0F, 0F, 0F);
		
		Body6 = new ModelRenderer(this, 0, 0).setTextureSize(64, 32);
		Body6.addBox(2.2F, -1.5F, 0F, 2, 3, 1);
		Body6.setRotationPoint(0F, 0F, 0F);
		setRotation(Body6, 0F, 0F, 0F);
		
		Tooth2 = new ModelRenderer(this, 0, 0).setTextureSize(64, 32);
		Tooth2.addBox(-1F, 3.8F, 0F, 2, 2, 1);
		Tooth2.setRotationPoint(0F, 0F, 0F);
		setRotation(Tooth2, 0F, 0F, 0F);
		
		Tooth1 = new ModelRenderer(this, 0, 0).setTextureSize(64, 32);
		Tooth1.addBox(-1F, -5.8F, 0F, 2, 2, 1);
		Tooth1.setRotationPoint(0F, 0F, 0F);
		setRotation(Tooth1, 0F, 0F, 0F);
		
		Tooth3 = new ModelRenderer(this, 0, 0).setTextureSize(64, 32);
		Tooth3.addBox(-5.8F, -1F, 0F, 2, 2, 1);
		Tooth3.setRotationPoint(0F, 0F, 0F);
		setRotation(Tooth3, 0F, 0F, 0F);
		
		Tooth4 = new ModelRenderer(this, 0, 0).setTextureSize(64, 32);
		Tooth4.addBox(3.8F, -1F, 0F, 2, 2, 1);
		Tooth4.setRotationPoint(0F, 0F, 0F);
		setRotation(Tooth4, 0F, 0F, 0F);
		
		Tooth5 = new ModelRenderer(this, 0, 0).setTextureSize(64, 32);
		Tooth5.addBox(-5.5F, -1F, 0F, 2, 2, 1);
		Tooth5.setRotationPoint(0F, 0F, 0F);
		setRotation(Tooth5, 0F, 0F, 0.7853982F);
		
		Tooth6 = new ModelRenderer(this, 0, 0).setTextureSize(64, 32);
		Tooth6.addBox(-1F, 3.5F, 0F, 2, 2, 1);
		Tooth6.setRotationPoint(0F, 0F, 0F);
		setRotation(Tooth6, 0F, 0F, 0.7853982F);
		
		Tooth7 = new ModelRenderer(this, 0, 0).setTextureSize(64, 32);
		Tooth7.addBox(-1F, -5.5F, 0F, 2, 2, 1);
		Tooth7.setRotationPoint(0F, 0F, 0F);
		setRotation(Tooth7, 0F, 0F, 0.7853982F);
		
		Tooth8 = new ModelRenderer(this, 0, 0).setTextureSize(64, 32);
		Tooth8.addBox(3.5F, -1F, 0F, 2, 2, 1);
		Tooth8.setRotationPoint(0F, 0F, 0F);
		setRotation(Tooth8, 0F, 0F, 0.7853982F);
	}
	
	public void render(int rotation, float scale) {
		setRotation(rotation);
		Body1.render(scale);
		Body2.render(scale);
		Body3.render(scale);
		Body4.render(scale);
		Body5.render(scale);
		Body6.render(scale);
		Tooth2.render(scale);
		Tooth1.render(scale);
		Tooth3.render(scale);
		Tooth4.render(scale);
		Tooth5.render(scale);
		Tooth6.render(scale);
		Tooth7.render(scale);
		Tooth8.render(scale);
	}
	
	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
	
	public void setRotation(int rotation) {
		this.Body1.rotateAngleZ += rotation;
		this.Body2.rotateAngleZ += rotation;
		this.Body3.rotateAngleZ += rotation;
		this.Body4.rotateAngleZ += rotation;
		this.Body5.rotateAngleZ += rotation;
		this.Body6.rotateAngleZ += rotation;
		this.Tooth2.rotateAngleZ += rotation;
		this.Tooth1.rotateAngleZ += rotation;
		this.Tooth3.rotateAngleZ += rotation;
		this.Tooth4.rotateAngleZ += rotation;
		this.Tooth5.rotateAngleZ += rotation;
		this.Tooth6.rotateAngleZ += rotation;
		this.Tooth7.rotateAngleZ += rotation;
		this.Tooth8.rotateAngleZ += rotation;
	}
}
