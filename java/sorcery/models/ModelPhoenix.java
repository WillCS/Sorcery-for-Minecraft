package sorcery.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

import org.lwjgl.opengl.GL11;

import sorcery.entities.EntityPhoenix;
import cpw.mods.fml.client.FMLClientHandler;

public class ModelPhoenix extends ModelBase {
	// fields
	ModelRenderer wing2;
	ModelRenderer head;
	ModelRenderer body;
	ModelRenderer leg1;
	ModelRenderer leg2;
	ModelRenderer wing1;
	// ModelRenderer feather11;
	// ModelRenderer feather12;
	ModelRenderer beak;
	// ModelRenderer feather21;
	// ModelRenderer feather22;
	ModelRenderer tail1;
	ModelRenderer tail2;
	ModelRenderer tail3;
	
	public ModelPhoenix() {
		wing1 = new ModelRenderer(this, 0, 0).setTextureSize(64, 32);
		wing1.addBox(-1F, 0F, -3F, 1, 6, 3);
		wing1.rotationPointX = -2F;
		wing1.rotationPointY = 15F;
		wing1.rotationPointZ = -0.5F;
		wing1.rotateAngleX = 0.25F;
		// setRotation(wing1, 0.2617994F, 0F, 0F);
		
		wing2 = new ModelRenderer(this, 0, 0).setTextureSize(64, 32);
		wing2.addBox(0F, 0F, -3F, 1, 6, 3);
		wing2.rotationPointX = 2F;
		wing2.rotationPointY = 15F;
		wing2.rotationPointZ = -0.5F;
		wing2.rotateAngleX = 0.25F;
		wing2.mirror = true;
		// setRotation(wing2, 0.2617994F, 0F, 0F);
		
		head = new ModelRenderer(this, 0, 26).setTextureSize(64, 32);
		head.addBox(-1.5F, -2F, -3F, 3, 3, 3);
		head.rotationPointX = 0F;
		head.rotationPointY = 15F;
		head.rotationPointZ = -2F;
		// setRotation(head, 0F, 0F, 0F);
		
		body = new ModelRenderer(this, 21, 0).setTextureSize(64, 32);
		body.addBox(-3F, -3F, -6F, 4, 4, 6);
		body.rotationPointX = 1F;
		body.rotationPointY = 15F;
		body.rotationPointZ = -1F;
		body.rotateAngleX = 1.8F;
		// setRotation(body, 1.832596F, 0F, 0F);
		
		leg1 = new ModelRenderer(this, 0, 16).setTextureSize(64, 32);
		leg1.addBox(-1F, 0F, -1F, 1, 4, 1);
		leg1.rotationPointX = -1F;
		leg1.rotationPointY = 20F;
		leg1.rotationPointZ = 0F;
		// setRotation(leg1, 0F, 0F, 0F);
		
		leg2 = new ModelRenderer(this, 0, 16).setTextureSize(64, 32);
		leg2.addBox(0F, 0F, -1F, 1, 4, 1);
		leg2.rotationPointX = 1F;
		leg2.rotationPointY = 20F;
		leg2.rotationPointZ = 0F;
		leg2.mirror = true;
		// setRotation(leg2, 0F, 0F, 0F);
		
		/*
		 * feather11 = new ModelRenderer(this, 20, 16).setTextureSize(64, 32);
		 * feather11.addBox(-1.5F, 0F, 2F, 1, 3, 0); feather11.rotationPointX =
		 * 0F; feather11.rotationPointY = 15F; feather11.rotationPointZ = -2F;
		 * feather11.rotateAngleX = 1.8F; //setRotation(feather11, 1.780236F,
		 * 0F, 0F); feather12 = new ModelRenderer(this, 20,
		 * 20).setTextureSize(64, 32); feather12.addBox(-1.533333F, 1.7F, 3.2F,
		 * 1, 3, 0); feather12.rotationPointX = 0F; feather12.rotationPointY =
		 * 15F; feather12.rotationPointZ = -2F; feather12.rotateAngleX = 1.3F;
		 * //setRotation(feather12, 1.294037F, 0F, 0F);
		 */
		
		beak = new ModelRenderer(this, 15, 30).setTextureSize(64, 32);
		beak.addBox(-1F, -0.5F, -4F, 2, 1, 1);
		beak.rotationPointX = 0F;
		beak.rotationPointY = 15F;
		beak.rotationPointZ = -2F;
		// setRotation(beak, 0F, 0F, 0F);
		
		/*
		 * feather21 = new ModelRenderer(this, 20, 16).setTextureSize(64, 32);
		 * feather21.addBox(0.5F, 0F, 2F, 1, 3, 0); feather21.rotationPointX =
		 * 0F; feather21.rotationPointY = 15F; feather21.rotationPointZ = -2F;
		 * feather21.rotateAngleX = 1.8F; feather21.mirror = true;
		 * //setRotation(feather21, 1.780236F, 0F, 0F); feather22 = new
		 * ModelRenderer(this, 20, 20).setTextureSize(64, 32);
		 * feather22.addBox(0.5F, 1.7F, 3.2F, 1, 3, 0); feather22.rotationPointX
		 * = 0F; feather22.rotationPointY = 15F; feather22.rotationPointZ = -2F;
		 * feather22.rotateAngleX = 1.3F; feather22.mirror = true;
		 * //setRotation(feather22, 1.294037F, 0F, 0F);
		 */
		
		tail1 = new ModelRenderer(this, 31, 20).setTextureSize(64, 32);
		tail1.addBox(-2F, 0F, 0F, 3, 3, 0);
		tail1.rotationPointX = 0.5F;
		tail1.rotationPointY = 19F;
		tail1.rotationPointZ = 1F;
		tail1.rotateAngleX = 0.7F;
		// setRotation(tail1, 0.6956384F, 0F, 0F);
		
		tail2 = new ModelRenderer(this, 47, 20).setTextureSize(64, 32);
		tail2.addBox(-2F, 0F, 0F, 3, 3, 0);
		tail2.rotationPointX = 0.5F;
		tail2.rotationPointY = 22.5F;
		tail2.rotationPointZ = 5F;
		tail2.rotateAngleX = 1.4F;
		// setRotation(tail2, 1.406237F, 0F, 0F);
		
		tail3 = new ModelRenderer(this, 39, 20).setTextureSize(64, 32);
		tail3.addBox(-2F, 0F, 0F, 3, 3, 0);
		tail3.rotationPointX = 0.5F;
		tail3.rotationPointY = 21F;
		tail3.rotationPointZ = 2.5F;
		tail3.rotateAngleX = 1.0F;
		// setRotation(tail3, 1.032238F, 0F, 0F);
	}
	
	public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
		this.setRotationAngles(par2, par3, par4, par5, par6, par7);
		if(this.isChild) {
			wing2.render(par7);
			head.render(par7);
			body.render(par7);
			leg1.render(par7);
			leg2.render(par7);
			wing1.render(par7);
			// feather11.render(par7);
			// feather12.render(par7);
			beak.render(par7);
			// feather21.render(par7);
			// feather22.render(par7);
			tail1.render(par7);
			tail2.render(par7);
			tail3.render(par7);
		} else {
			float var8 = 0.5F;
			float var9 = par1Entity.ridingEntity != null ? -1.0F : -12.0F;
			
			if(this.isRiding && par1Entity.ridingEntity != FMLClientHandler.instance().getClient().thePlayer) {
				var9 = -13F;
			}
			
			GL11.glPushMatrix();
			GL11.glScalef(1.0F / var8, 1.0F / var8, 1.0F / var8);
			GL11.glTranslatef(0.0F, var9 * par7, 0.0F);
			wing2.render(par7);
			head.render(par7);
			body.render(par7);
			leg1.render(par7);
			leg2.render(par7);
			wing1.render(par7);
			// feather11.render(par7);
			// feather12.render(par7);
			beak.render(par7);
			// feather21.render(par7);
			// feather22.render(par7);
			tail1.render(par7);
			if(!((EntityPhoenix)par1Entity).getSheared() || ((EntityPhoenix)par1Entity).featherGrowBackTime == 0) {
				tail2.render(par7);
				tail3.render(par7);
			}
			GL11.glPopMatrix();
		}
	}
	
	public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6) {
		this.head.rotateAngleX = par5 / (180F / (float)Math.PI);
		this.head.rotateAngleY = par4 / (180F / (float)Math.PI);
		this.beak.rotateAngleX = this.head.rotateAngleX;
		this.beak.rotateAngleY = this.head.rotateAngleY;
		/*
		 * this.feather11.rotateAngleX = this.feather11.rotateAngleX +
		 * this.head.rotateAngleX; this.feather11.rotateAngleY =
		 * this.feather11.rotateAngleY + this.head.rotateAngleY;
		 * this.feather12.rotateAngleX = this.feather12.rotateAngleX +
		 * this.head.rotateAngleX; this.feather12.rotateAngleY =
		 * this.feather12.rotateAngleY + this.head.rotateAngleY;
		 * this.feather21.rotateAngleX = this.feather21.rotateAngleX +
		 * this.head.rotateAngleX; this.feather21.rotateAngleY =
		 * this.feather21.rotateAngleY + this.head.rotateAngleY;
		 * this.feather22.rotateAngleX = this.feather22.rotateAngleX +
		 * this.head.rotateAngleX; this.feather22.rotateAngleY =
		 * this.feather22.rotateAngleY + this.head.rotateAngleY;
		 */
		this.leg1.rotateAngleX = MathHelper.cos(par1 * 0.6662F) * 1.4F * par2;
		this.leg2.rotateAngleX = MathHelper.cos(par1 * 0.6662F + (float)Math.PI) * 1.4F * par2;
		this.wing2.rotateAngleZ = -par3;
		this.wing1.rotateAngleZ = par3;
	}
	
}
