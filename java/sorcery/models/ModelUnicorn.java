package sorcery.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

import org.lwjgl.opengl.GL11;

import sorcery.entities.EntityUnicorn;

public class ModelUnicorn extends ModelBase {
	// fields
	public ModelRenderer neck;
	public ModelRenderer body;
	public ModelRenderer saddle;
	public ModelRenderer leg1;
	public ModelRenderer leg2;
	public ModelRenderer leg3;
	public ModelRenderer leg4;
	public ModelRenderer head;
	public ModelRenderer horn;
	public ModelRenderer tail;
	public ModelRenderer ear1;
	public ModelRenderer mane1;
	public ModelRenderer ear2;
	public ModelRenderer mane2;
	public ModelRenderer mane3;
	
	public ModelUnicorn() {
		head = (new ModelRenderer(this, 0, 34)).setTextureSize(64, 64);
		head.addBox(-2F, -2F, -7F, 4, 4, 10);
		head.rotationPointX = 0F;
		head.rotationPointY = 3F;
		head.rotationPointZ = -12F;
		head.rotateAngleX = 0.4F;
		head.mirror = true;
		
		neck = (new ModelRenderer(this, 0, 48)).setTextureSize(64, 64);
		neck.addBox(-2F, -2F, -4.5F, 4, 6, 10);
		neck.rotationPointX = 0F;
		neck.rotationPointY = 5F;
		neck.rotationPointZ = -8F;
		neck.rotateAngleX = -0.785F;
		neck.mirror = true;
		
		body = (new ModelRenderer(this, 32, 0)).setTextureSize(64, 64);
		body.addBox(-6F, -10F, -7F, 8, 16, 8);
		body.rotationPointX = 2F;
		body.rotationPointY = 6F;
		body.rotationPointZ = 2F;
		body.rotateAngleX = 1.57F;
		
		saddle = (new ModelRenderer(this, 28, 24)).setTextureSize(64, 64);
		saddle.addBox(-7F, -10F, -7F, 10, 9, 8);
		saddle.rotationPointX = 2F;
		saddle.rotationPointY = 5.9F;
		saddle.rotationPointZ = 6F;
		saddle.rotateAngleX = 1.57F;
		
		leg1 = (new ModelRenderer(this, 0, 0)).setTextureSize(64, 64);
		leg1.addBox(-3F, 0F, -2F, 3, 12, 3);
		leg1.rotationPointX = -1F;
		leg1.rotationPointY = 12F;
		leg1.rotationPointZ = 7F;
		leg1.mirror = true;
		
		leg2 = (new ModelRenderer(this, 0, 0)).setTextureSize(64, 64);
		leg2.addBox(-1F, 0F, -2F, 3, 12, 3);
		leg2.rotationPointX = 2;
		leg2.rotationPointY = 12F;
		leg2.rotationPointZ = 7F;
		
		leg3 = (new ModelRenderer(this, 0, 0)).setTextureSize(64, 64);
		leg3.addBox(-3F, 0F, -3F, 3, 12, 3);
		leg3.rotationPointX = -1F;
		leg3.rotationPointY = 12F;
		leg3.rotationPointZ = -5F;
		leg3.mirror = true;
		
		leg4 = (new ModelRenderer(this, 0, 0)).setTextureSize(64, 64);
		leg4.addBox(-2F, 0F, -4F, 3, 12, 3);
		leg4.rotationPointX = 3F;
		leg4.rotationPointY = 12F;
		leg4.rotationPointZ = -4F;
		
		horn = (new ModelRenderer(this, 0, 24)).setTextureSize(64, 64);
		horn.addBox(-0.5F, -0.5F, -3.5F, 1, 1, 4);
		horn.rotationPointX = 0F;
		horn.rotationPointY = -1F;
		horn.rotationPointZ = -15F;
		horn.rotateAngleX = 2.3F;
		
		tail = (new ModelRenderer(this, 36, 50)).setTextureSize(64, 64);
		tail.addBox(-2F, 0F, -6F, 4, 4, 10);
		tail.rotationPointX = 0F;
		tail.rotationPointY = 11F;
		tail.rotationPointZ = 6F;
		tail.rotateAngleX = 1.9F;
		
		ear1 = (new ModelRenderer(this, 0, 21)).setTextureSize(64, 64);
		ear1.addBox(-0.5F, -0.5F, -2.5F, 1, 1, 2);
		ear1.rotationPointX = -1.3F;
		ear1.rotationPointY = 1.5F;
		ear1.rotationPointZ = -10F;
		ear1.rotateAngleX = -0.9F;
		ear1.mirror = true;
		
		ear2 = (new ModelRenderer(this, 0, 21)).setTextureSize(64, 64);
		ear2.addBox(-0.5F, -0.5F, -2.5F, 1, 1, 2);
		ear2.rotationPointX = 1.3F;
		ear2.rotationPointY = 1.5F;
		ear2.rotationPointZ = -10F;
		ear2.rotateAngleX = -0.9F;
		ear2.mirror = true;
		
		mane1 = (new ModelRenderer(this, 16, 12)).setTextureSize(64, 64);
		mane1.addBox(-0.5F, -0.5F, -3.5F, 0, 3, 3);
		mane1.rotationPointX = 0.5F;
		mane1.rotationPointY = 1.25F;
		mane1.rotationPointZ = -8.5F;
		mane1.rotateAngleX = -1.2F;
		
		mane2 = (new ModelRenderer(this, 12, 0)).setTextureSize(64, 64);
		mane2.addBox(0F, -0.5F, -3.5F, 0, 3, 9);
		mane2.rotationPointX = 0F;
		mane2.rotationPointY = 1.5F;
		mane2.rotationPointZ = -7F;
		mane2.rotateAngleX = -0.785F;
		
		mane3 = (new ModelRenderer(this, 12, 12)).setTextureSize(64, 64);
		mane3.addBox(0F, 0F, 0F, 0, 3, 2);
		mane3.rotationPointX = 0F;
		mane3.rotationPointY = 3F;
		mane3.rotationPointZ = -4F;
	}
	
	public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
		this.setRotationAngles(par2, par3, par4, par5, par6, par7);
		if(this.isChild) {
			float var8 = 2.0F;
			GL11.glPushMatrix();
			GL11.glScalef(1.0F / var8, 1.0F / var8, 1.0F / var8);
			GL11.glTranslatef(0.0F, 24.0F * par7, 0.0F);
			neck.render(par7);
			body.render(par7);
			leg1.render(par7);
			leg2.render(par7);
			leg3.render(par7);
			leg4.render(par7);
			head.render(par7);
			horn.render(par7);
			tail.render(par7);
			ear1.render(par7);
			mane1.render(par7);
			ear2.render(par7);
			mane2.render(par7);
			mane3.render(par7);
			GL11.glPopMatrix();
		} else {
			GL11.glPushMatrix();
			GL11.glScalef(1.2F, 1.2F, 1.2F);
			GL11.glTranslatef(0.0F, -4.0F * par7, 0.0F);
			neck.render(par7);
			body.render(par7);
			leg1.render(par7);
			leg2.render(par7);
			leg3.render(par7);
			leg4.render(par7);
			head.render(par7);
			horn.render(par7);
			tail.render(par7);
			ear1.render(par7);
			mane1.render(par7);
			ear2.render(par7);
			mane2.render(par7);
			mane3.render(par7);
			
			if(((EntityUnicorn)par1Entity).getSaddled()) {
				saddle.render(par7);
			}
			GL11.glPopMatrix();
		}
	}
	
	public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6) {
		this.head.rotateAngleX = par5 / (180F / (float)Math.PI);
		this.head.rotateAngleY = par4 / (180F / (float)Math.PI);
		/*
		 * this.horn.rotateAngleX = par5 / (180F / (float)Math.PI);
		 * this.horn.rotateAngleY = par4 / (180F / (float)Math.PI);
		 * this.mane1.rotateAngleX = par5 / (180F / (float)Math.PI);
		 * this.mane1.rotateAngleY = par4 / (180F / (float)Math.PI);
		 * this.ear1.rotateAngleX = par5 / (180F / (float)Math.PI);
		 * this.ear1.rotateAngleY = par4 / (180F / (float)Math.PI);
		 * this.ear2.rotateAngleX = par5 / (180F / (float)Math.PI);
		 * this.ear2.rotateAngleY = par4 / (180F / (float)Math.PI);
		 */
		this.body.rotateAngleX = ((float)Math.PI / 2F);
		this.leg1.rotateAngleX = MathHelper.cos(par1 * 0.6662F) * 1.4F * par2;
		this.leg2.rotateAngleX = MathHelper.cos(par1 * 0.6662F + (float)Math.PI) * 1.4F * par2;
		this.leg3.rotateAngleX = MathHelper.cos(par1 * 0.6662F + (float)Math.PI) * 1.4F * par2;
		this.leg4.rotateAngleX = MathHelper.cos(par1 * 0.6662F) * 1.4F * par2;
	}
	
	// public void setRotationAngles(float par1, float par2, float par3, float
	// par4, float par5, float par6) {}
}
