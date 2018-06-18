package sorcery.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import sorcery.client.render.RenderUtils;
import sorcery.lib.utils.Utils;
import sorcery.tileentities.TileEntityElementalInfuser;

public class ModelElementalInfuser extends ModelBase {
	private static ResourceLocation texture = Utils.getResource("textures/entities/elementLaser.png");
	
	ModelRenderer Body_Back;
	ModelRenderer Right_Hinge;
	ModelRenderer Left_Hinge;
	ModelRenderer Body_Middle;
	ModelRenderer Body_Front;
	ModelRenderer Base;
	ModelRenderer Tip;
	ModelRenderer Laser;
	ModelRenderer Laser_Left;
	ModelRenderer Laser_Right;
	ModelRenderer Laser_Top;
	ModelRenderer Laser_Bottom;
	ModelRenderer Right_Arm_Top;
	ModelRenderer Mount;
	ModelRenderer Top_Support;
	ModelRenderer Left_Support;
	ModelRenderer Left_Arm_Top;
	ModelRenderer Right_Support;
	ModelRenderer Top_Hinge;
	
	public ModelElementalInfuser() {
		this.textureWidth = 128;
		this.textureHeight = 128;
		
		Body_Back = new ModelRenderer(this, 0, 100).setTextureSize(128, 128);
		Body_Back.addBox(-7F, -7F, -7F, 14, 14, 14);
		Body_Back.setRotationPoint(0F, 0F, 0F);
		
		Right_Hinge = new ModelRenderer(this, 0, 18).setTextureSize(128, 128);
		Right_Hinge.addBox(6F, -9F, -1F, 2, 2, 2);
		Right_Hinge.setRotationPoint(0F, 0F, 0F);
		
		Left_Hinge = new ModelRenderer(this, 0, 18).setTextureSize(128, 128);
		Left_Hinge.addBox(-8F, -9F, -1F, 2, 2, 2);
		Left_Hinge.setRotationPoint(0F, 0F, 0F);
		
		Body_Middle = new ModelRenderer(this, 0, 75).setTextureSize(128, 128);
		Body_Middle.addBox(-5F, -5F, -21F, 10, 10, 14);
		Body_Middle.setRotationPoint(0F, 0F, 0F);
		
		Body_Front = new ModelRenderer(this, 0, 52).setTextureSize(128, 128);
		Body_Front.addBox(-3F, -3F, -37F, 6, 6, 16);
		Body_Front.setRotationPoint(0F, 0F, 0F);
		
		Base = new ModelRenderer(this, 116, 122).setTextureSize(128, 128);
		Base.addBox(-1.5F, -1.5F, -40F, 3, 3, 3);
		Base.setRotationPoint(0F, 0F, 0F);
		
		Tip = new ModelRenderer(this, 120, 103).setTextureSize(128, 128);
		Tip.addBox(-1F, -1F, -47F, 2, 2, 2);
		Tip.setRotationPoint(0F, 0F, 0F);
		
		Laser = new ModelRenderer(this, 114, 114).setTextureSize(128, 128);
		Laser.addBox(-0.5F, -0.5F, -46F, 1, 1, 6);
		Laser.setRotationPoint(0F, 0F, 0F);
		
		Laser_Left = new ModelRenderer(this, 118, 108).setTextureSize(128, 128);
		Laser_Left.addBox(-2.5F, -0.5F, -42F, 1, 1, 4);
		Laser_Left.setRotationPoint(0F, 0F, 0F);

		Laser_Right = new ModelRenderer(this, 118, 108).setTextureSize(128, 128);
		Laser_Right.addBox(1.5F, -0.5F, -42F, 1, 1, 4);
		Laser_Right.setRotationPoint(0F, 0F, 0F);
	
		Laser_Top = new ModelRenderer(this, 118, 108).setTextureSize(128, 128);
		Laser_Top.addBox(-0.5F, -2.5F, -42F, 1, 1, 4);
		Laser_Top.setRotationPoint(0F, 0F, 0F);

		Laser_Bottom = new ModelRenderer(this, 118, 108).setTextureSize(128, 128);
		Laser_Bottom.addBox(-0.5F, 1.5F, -42F, 1, 1, 4);
		Laser_Bottom.setRotationPoint(0F, 0F, 0F);
		

		Mount = new ModelRenderer(this, 0, 34).setTextureSize(128, 128);
		Mount.addBox(-8F, -16F, -8F, 16, 2, 16);
		Mount.setRotationPoint(0F, -8F, 0F);

		
		Right_Arm_Top = new ModelRenderer(this, 9, 7).setTextureSize(128, 128);
		Right_Arm_Top.addBox(8F, -2F, -2F, 2, 4, 4);
		Right_Arm_Top.setRotationPoint(0F, -8F, 0F);

		Left_Arm_Top = new ModelRenderer(this, 9, 7).setTextureSize(128, 128);
		Left_Arm_Top.addBox(-10F, -2F, -2F, 2, 4, 4);
		Left_Arm_Top.setRotationPoint(0F, -8F, 0F);
		
		Top_Support = new ModelRenderer(this, 63, 32).setTextureSize(128, 128);
		Top_Support.addBox(-8F, -14F, -4F, 16, 1, 8);
		Top_Support.setRotationPoint(0F, -8F, 0F);
	
		Left_Support = new ModelRenderer(this, 55, 0).setTextureSize(128, 128);
		Left_Support.addBox(7F, -14F, -4F, 1, 18, 8);
		Left_Support.setRotationPoint(0F, -8F, 0F);
		Left_Support.setTextureSize(128, 128);
		
		Right_Support = new ModelRenderer(this, 55, 0).setTextureSize(128, 128);
		Right_Support.addBox(-8F, -14F, -4F, 1, 18, 8);
		Right_Support.setRotationPoint(0F, -8F, 0F);
		
		Top_Hinge = new ModelRenderer(this, 73, 8).setTextureSize(128, 128);
		Top_Hinge.addBox(-2F, -13F, -2F, 4, 2, 4);
		Top_Hinge.setRotationPoint(0F, -8F, 0F);
	}
	
	public void render(TileEntityElementalInfuser tile) {
		RenderUtils.bindTexture(this.texture);
		GL11.glPushMatrix();
		GL11.glTranslatef(0.0F, 1.875F, 0.0F);
		GL11.glScalef(0.0625F, 0.0625F, 0.0625F);
		//Mount.render(1F);
		
		GL11.glRotatef(tile.yaw - 90, 0.0F, 1.0F, 0.0F);
		Right_Arm_Top.render(1F);
		Top_Support.render(1F);
		Left_Support.render(1F);
		Left_Arm_Top.render(1F);
		Right_Support.render(1F);
		Top_Hinge.render(1F);
		Right_Hinge.render(1F);
		Left_Hinge.render(1F);
		
		GL11.glTranslatef(0.0F, -8.0F, 0.0F);
		GL11.glRotatef(tile.pitch, 1.0F, 0.0F, 0.0F);
		this.setRotation(Body_Back, 0.0F, 0.0F, 0.0F);
		Body_Back.render(1F);
		
		GL11.glRotatef(tile.spin, 0.0F, 0.0F, 1.0F);
		this.setRotation(Body_Middle, 0.0F, 0.0F, this.toRad(tile.spin));
		Body_Middle.render(1F);
		
		GL11.glRotatef(tile.spin * 2, 0.0F, 0.0F, 1.0F);
		this.setRotation(Body_Front, 0.0F, 0.0F, 0.0F);
		Body_Front.render(1F);
		this.setRotation(Base, 0.0F, 0.0F, 0.0F);
		Base.render(1F);
		this.setRotation(Tip, 0.0F, 0.0F, 0.0F);
		Tip.render(1F);
		this.setRotation(Laser, 0.0F, 0.0F, 0.0F);
		Laser.render(1F);
		this.setRotation(Laser_Left, 0.0F, 0.0F, 0.0F);
		Laser_Left.render(1F);
		this.setRotation(Laser_Right, 0.0F, 0.0F, 0.0F);
		Laser_Right.render(1F);
		this.setRotation(Laser_Top, 0.0F, 0.0F, 0.0F);
		Laser_Top.render(1F);
		this.setRotation(Laser_Bottom, 0.0F, 0.0F, 0.0F);
		Laser_Bottom.render(1F);
		GL11.glPopMatrix();
	}
	
	public void renderAsItem() {
		RenderUtils.bindTexture(this.texture);
		GL11.glTranslatef(-4.0F, 8.0F, 16.0F);
		Body_Back.render(0.0625F);
		Body_Middle.render(0.0625F);
		Body_Front.render(0.0625F);
		Base.render(0.0625F);
		Tip.render(0.0625F);
		Laser.render(0.0625F);
		Laser_Left.render(0.0625F);
		Laser_Right.render(0.0625F);
		Laser_Top.render(0.0625F);
		Laser_Bottom.render(0.0625F);
	}
	
	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
	
	private float toRad(int degree) {
		return (float)(((float)degree) * Math.PI) / 180.0F;
	}
}
