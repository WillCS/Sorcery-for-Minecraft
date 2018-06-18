package sorcery.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelInfuser extends ModelBase {
	// fields
	ModelRenderer Base_1;
	ModelRenderer Leg;
	ModelRenderer Support_2;
	ModelRenderer Support_1;
	ModelRenderer Top;
	ModelRenderer Support_4;
	ModelRenderer Support_3;
	ModelRenderer Base_2;
	ModelRenderer Pillar_1;
	ModelRenderer Pillar_2;
	ModelRenderer Pillar_3;
	ModelRenderer Pillar_4;
	
	public ModelInfuser() {
		textureWidth = 64;
		textureHeight = 64;
		
		Base_1 = new ModelRenderer(this, 0, 0);
		Base_1.addBox(-2F, 23F, -2F, 6, 1, 6);
		Base_1.setRotationPoint(-1F, 0F, -1F);
		Base_1.setTextureSize(64, 64);
		Base_1.mirror = true;
		setRotation(Base_1, 0F, 0F, 0F);
		Leg = new ModelRenderer(this, 0, 14);
		Leg.addBox(-2F, 23F, -2F, 2, 8, 2);
		Leg.setRotationPoint(1F, -7F, 1F);
		Leg.setTextureSize(64, 64);
		Leg.mirror = true;
		setRotation(Leg, 0F, 0F, 0F);
		Support_2 = new ModelRenderer(this, 0, 0);
		Support_2.addBox(-3F, 23F, -3F, 6, 1, 6);
		Support_2.setRotationPoint(0F, -9F, 0F);
		Support_2.setTextureSize(64, 64);
		Support_2.mirror = true;
		setRotation(Support_2, 0F, 0F, 0F);
		Support_1 = new ModelRenderer(this, 0, 24);
		Support_1.addBox(-2F, 23F, -2F, 4, 1, 4);
		Support_1.setRotationPoint(0F, -8F, 0F);
		Support_1.setTextureSize(64, 64);
		Support_1.mirror = true;
		setRotation(Support_1, 0F, 0F, 0F);
		Top = new ModelRenderer(this, 0, 29);
		Top.addBox(-6F, 23F, -6F, 12, 2, 12);
		Top.setRotationPoint(0F, -11F, 0F);
		Top.setTextureSize(64, 64);
		Top.mirror = true;
		setRotation(Top, 0F, 0F, 0F);
		Support_4 = new ModelRenderer(this, 0, 43);
		Support_4.addBox(-2F, 23F, -2F, 2, 1, 10);
		Support_4.setRotationPoint(1F, -9F, -3F);
		Support_4.setTextureSize(64, 64);
		Support_4.mirror = true;
		setRotation(Support_4, 0F, 0F, 0F);
		Support_3 = new ModelRenderer(this, 0, 43);
		Support_3.addBox(-2F, 23F, -2F, 2, 1, 10);
		Support_3.setRotationPoint(-3F, -9F, -1F);
		Support_3.setTextureSize(64, 64);
		Support_3.mirror = true;
		setRotation(Support_3, 0F, 1.570796F, 0F);
		Base_2 = new ModelRenderer(this, 0, 24);
		Base_2.addBox(-2F, 23F, -2F, 4, 1, 4);
		Base_2.setRotationPoint(0F, -2F, 0F);
		Base_2.setTextureSize(64, 64);
		Base_2.mirror = true;
		setRotation(Base_2, 0F, 0F, 0F);
		Pillar_1 = new ModelRenderer(this, 0, 7);
		Pillar_1.addBox(-2F, 23F, -2F, 2, 1, 2);
		Pillar_1.setRotationPoint(-4F, -12F, -4F);
		Pillar_1.setTextureSize(64, 64);
		Pillar_1.mirror = true;
		setRotation(Pillar_1, 0F, 0F, 0F);
		Pillar_2 = new ModelRenderer(this, 0, 7);
		Pillar_2.addBox(-2F, 23F, -2F, 2, 1, 2);
		Pillar_2.setRotationPoint(6F, -12F, 6F);
		Pillar_2.setTextureSize(64, 64);
		Pillar_2.mirror = true;
		setRotation(Pillar_2, 0F, 0F, 0F);
		Pillar_3 = new ModelRenderer(this, 0, 7);
		Pillar_3.addBox(-2F, 23F, -2F, 2, 1, 2);
		Pillar_3.setRotationPoint(-4F, -12F, 6F);
		Pillar_3.setTextureSize(64, 64);
		Pillar_3.mirror = true;
		setRotation(Pillar_3, 0F, 0F, 0F);
		Pillar_4 = new ModelRenderer(this, 0, 7);
		Pillar_4.addBox(-2F, 23F, -2F, 2, 1, 2);
		Pillar_4.setRotationPoint(6F, -12F, -4F);
		Pillar_4.setTextureSize(64, 64);
		Pillar_4.mirror = true;
		setRotation(Pillar_4, 0F, 0F, 0F);
	}
	
	public void render(float scale) {
		Base_1.render(scale);
		Leg.render(scale);
		Support_2.render(scale);
		Support_1.render(scale);
		Top.render(scale);
		Support_4.render(scale);
		Support_3.render(scale);
		Base_2.render(scale);
		Pillar_1.render(scale);
		Pillar_2.render(scale);
		Pillar_3.render(scale);
		Pillar_4.render(scale);
	}
	
	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
	
}
