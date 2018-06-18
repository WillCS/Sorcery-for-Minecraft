package sorcery.models;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import sorcery.client.render.RenderUtils;
import cpw.mods.fml.client.FMLClientHandler;

public class ModelDrill extends ModelBase {
	ModelRenderer gloveTop;
	ModelRenderer gloveFront;
	ModelRenderer drill6;
	ModelRenderer drill5;
	ModelRenderer drill1;
	ModelRenderer drill2;
	ModelRenderer drill3;
	ModelRenderer drill4;
	ModelRenderer gloveBottom;
	ModelRenderer gloveLeft;
	ModelRenderer gloveRight;
	
	ModelRenderer playerArm;
	
	public ModelDrill() {
		textureWidth = 64;
		textureHeight = 32;
		
		gloveTop = new ModelRenderer(this, 17, 0);
		gloveTop.addBox(-2F, -2F, -3F, 4, 0, 5);
		gloveTop.setRotationPoint(0F, 0F, 0F);
		gloveTop.setTextureSize(64, 32);

		gloveFront = new ModelRenderer(this, 0, 0);
		gloveFront.addBox(-2F, 2F, -2F, 4, 0, 4);
		gloveFront.setRotationPoint(0F, 0F, 0F);
		gloveFront.setTextureSize(64, 32);
		setRotation(gloveFront, 1.570796F, 0F, 0F);
		
		gloveBottom = new ModelRenderer(this, 0, 0);
		gloveBottom.addBox(-2F, 2F, -2F, 4, 0, 4);
		gloveBottom.setRotationPoint(0F, 0F, 0F);
		gloveBottom.setTextureSize(64, 32);

		gloveLeft = new ModelRenderer(this, 0, 0);
		gloveLeft.addBox(-2F, 2F, -2F, 4, 0, 4);
		gloveLeft.setRotationPoint(0F, 0F, 0F);
		gloveLeft.setTextureSize(64, 32);
		setRotation(gloveLeft, 0F, 0F, 1.570796F);
		
		gloveRight = new ModelRenderer(this, 0, 0);
		gloveRight.addBox(-2F, 2F, -2F, 4, 0, 4);
		gloveRight.setRotationPoint(0F, 0F, 0F);
		gloveRight.setTextureSize(64, 32);
		setRotation(gloveRight, 0F, 0F, -1.570796F);

		drill1 = new ModelRenderer(this, 0, 5);
		drill1.addBox(-3F, -3F, 3F, 6, 6, 1);
		drill1.setRotationPoint(0F, 0F, 0F);
		drill1.setTextureSize(64, 32);

		drill2 = new ModelRenderer(this, 0, 12);
		drill2.addBox(-2.5F, -2.5F, 4F, 5, 5, 1);
		drill2.setRotationPoint(0F, 0F, 0F);
		drill2.setTextureSize(64, 32);
		setRotation(drill2, 0F, 0F, 0.7853982F);
		
		drill3 = new ModelRenderer(this, 0, 18);
		drill3.addBox(-2F, -2F, 5F, 4, 4, 1);
		drill3.setRotationPoint(0F, 0F, 0F);
		drill3.setTextureSize(64, 32);
		
		drill4 = new ModelRenderer(this, 0, 23);
		drill4.addBox(-1.5F, -1.5F, 6F, 3, 3, 1);
		drill4.setRotationPoint(0F, 0F, 0F);
		drill4.setTextureSize(64, 32);
		setRotation(drill4, 0F, 0F, 0.7853982F);
		
		drill5 = new ModelRenderer(this, 0, 27);
		drill5.addBox(-1F, -1F, 7F, 2, 2, 1);
		drill5.setRotationPoint(0F, 0F, 0F);
		drill5.setTextureSize(64, 32);
		
		drill6 = new ModelRenderer(this, 0, 30);
		drill6.addBox(-0.5F, -0.5F, 8F, 1, 1, 1);
		drill6.setRotationPoint(0F, 0F, 0F);
		drill6.setTextureSize(64, 32);
		setRotation(drill6, 0F, 0F, 0.7853982F);
		
		playerArm = new ModelRenderer(this, 40, 16).setTextureSize(64, 32);
        playerArm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4);
        playerArm.setRotationPoint(0F, 0F, 0F);

	}
	
	public void renderFirstPerson(ItemStack item, int spin) {
		GL11.glTranslatef(0.0F, 0.0F, 0.2F);
		render(item, spin);
		
		GL11.glPushMatrix();
		ResourceLocation skin = ((AbstractClientPlayer)FMLClientHandler.instance().getClient().thePlayer).getLocationSkin();
		RenderUtils.bindTexture(skin);
		
		GL11.glTranslatef(0.05F, -0.02F, -0.4F);
		GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
		GL11.glScalef(0.7F, 0.7F, 0.7F);
		playerArm.render(0.0625F);
		GL11.glPopMatrix();
	}
	
	public void render(ItemStack item, int spin) {
		//GL11.glRotatef(10, 1.0F, 0.0F, 0.0F);
		gloveTop.render(0.0625F);
		gloveFront.render(0.0625F);
		gloveBottom.render(0.0625F);
		gloveLeft.render(0.0625F);
		gloveRight.render(0.0625F);

		GL11.glPushMatrix();
		GL11.glRotatef(spin, 0.0F, 0.0F, 1.0F);
		drill1.render(0.0625F);
		GL11.glRotatef(spin * 2, 0.0F, 0.0F, 1.0F);
		drill2.render(0.0625F);
		GL11.glRotatef(spin * 3, 0.0F, 0.0F, 1.0F);
		drill3.render(0.0625F);
		GL11.glRotatef(spin * 4, 0.0F, 0.0F, 1.0F);
		drill4.render(0.0625F);
		GL11.glRotatef(spin * 5, 0.0F, 0.0F, 1.0F);
		drill5.render(0.0625F);
		GL11.glRotatef(spin * 6, 0.0F, 0.0F, 1.0F);
		drill6.render(0.0625F);
		GL11.glPopMatrix();
	}
	
	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}
