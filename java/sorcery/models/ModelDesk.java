package sorcery.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import sorcery.client.render.RenderUtils;
import sorcery.lib.utils.Utils;
import sorcery.tileentities.TileEntityDesk;

public class ModelDesk extends ModelBase {
	
	ModelRenderer top;
	ModelRenderer rightLeg1;
	ModelRenderer rightLeg4;
	ModelRenderer rightLeg3;
	ModelRenderer rightLeg2;
	ModelRenderer leftLeg4;
	ModelRenderer leftLeg3;
	ModelRenderer leftLeg2;
	ModelRenderer leftLeg1;
	public ResourceLocation texture = Utils.getResource("textures/entities/desk.png");

	public ModelDesk() {
		textureWidth = 128;
		textureHeight = 64;

		top = new ModelRenderer(this, 0, 0).setTextureSize(128, 64);
		top.addBox(0F, 0F, 0F, 32, 4, 16);
		top.setRotationPoint(-8.0F, -16.0F, -8.0F);
		
		rightLeg1 = new ModelRenderer(this, 0, 20).setTextureSize(128, 64);
		rightLeg1.addBox(26F, 4F, 2F, 4, 2, 12);
		rightLeg1.setRotationPoint(-8.0F, -16.0F, -8.0F);

		rightLeg4 = new ModelRenderer(this, 0, 34).setTextureSize(128, 64);
		rightLeg4.addBox(26F, 14F, 4F, 4, 2, 8);
		rightLeg4.setRotationPoint(-8.0F, -16.0F, -8.0F);
		
		rightLeg3 = new ModelRenderer(this, 0, 44).setTextureSize(128, 64);
		rightLeg3.addBox(26F, 8F, 6F, 4, 6, 4);
		rightLeg3.setRotationPoint(-8.0F, -16.0F, -8.0F);
		
		rightLeg2 = new ModelRenderer(this, 0, 34).setTextureSize(128, 64);
		rightLeg2.addBox(26F, 6F, 4F, 4, 2, 8);
		rightLeg2.setRotationPoint(-8.0F, -16.0F, -8.0F);

		leftLeg4 = new ModelRenderer(this, 0, 34).setTextureSize(128, 64);
		leftLeg4.mirror = true;
		leftLeg4.addBox(2F, 14F, 4F, 4, 2, 8);
		leftLeg4.setRotationPoint(-8.0F, -16.0F, -8.0F);
		
		leftLeg3 = new ModelRenderer(this, 0,44).setTextureSize(128, 64);
		leftLeg3.mirror = true;
		leftLeg3.addBox(2F, 8F, 6F, 4, 6, 4);
		leftLeg3.setRotationPoint(-8.0F, -16.0F, -8.0F);

		leftLeg2 = new ModelRenderer(this, 0, 34).setTextureSize(128, 64);
		leftLeg2.mirror = true;
		leftLeg2.addBox(2F, 6F, 4F, 4, 2, 8);
		leftLeg2.setRotationPoint(-8.0F, -16.0F, -8.0F);

		leftLeg1 = new ModelRenderer(this, 0, 20).setTextureSize(128, 64);
		leftLeg1.mirror = true;
		leftLeg1.addBox(2F, 4F, 2F, 4, 2, 12);
		leftLeg1.setRotationPoint(-8.0F, -16.0F, -8.0F);
	}

	public void renderItemInHand(ItemStack item) {
		RenderUtils.bindTexture(this.texture);
		GL11.glScalef(0.08F, 0.08F, 0.08F);
		GL11.glRotatef(240, 1.0F, 0F, 0F);
		GL11.glRotatef(-50, 0.0F, 0.0F, 1.0F);
		GL11.glTranslatef(4.5F, 11.0F, 0.0F);
		GL11.glRotatef(30, 0.0F, 1.0F, 0.0F);
		
		top.render(0.625F);
		rightLeg1.render(0.625F);
		rightLeg4.render(0.625F);
		rightLeg3.render(0.625F);
		rightLeg2.render(0.625F);
		leftLeg4.render(0.625F);
		leftLeg3.render(0.625F);
		leftLeg2.render(0.625F);
		leftLeg1.render(0.625F);
	}

	public void renderTileEntity(TileEntityDesk tile) {
		RenderUtils.bindTexture(this.texture);
		GL11.glScalef(0.1F, 0.1F, 0.1F);
		GL11.glRotatef(180, 1.0F, 0.0F, 0.0F);
		GL11.glTranslatef(0.0F, 5.0F, 0.0F);
		
		switch(tile.rotation) {
			case 0: GL11.glRotatef(180, 0.0F, 1.0F, 0.0F);
				break;
			case 1: GL11.glRotatef(270, 0.0F, 1.0F, 0.0F);
				break;
			case 2: GL11.glRotatef(0, 0.0F, 1.0F, 0.0F);
				break;
			case 3: GL11.glRotatef(90, 0.0F, 1.0F, 0.0F);
				break;
		}
		
		GL11.glPushMatrix();
		top.render(0.625F);
		rightLeg1.render(0.625F);
		rightLeg4.render(0.625F);
		rightLeg3.render(0.625F);
		rightLeg2.render(0.625F);
		leftLeg4.render(0.625F);
		leftLeg3.render(0.625F);
		leftLeg2.render(0.625F);
		leftLeg1.render(0.625F);
		GL11.glPopMatrix();
	}
	
	public void renderItemEntity(EntityItem entity) {
		RenderUtils.bindTexture(this.texture);
		
		GL11.glRotatef(180, 1.0F, 0.0F, 0.0F);
		GL11.glScalef(0.05F, 0.05F, 0.05F);
		GL11.glTranslatef(-5F, 2F, 0F);
		
		top.render(0.625F);
		rightLeg1.render(0.625F);
		rightLeg4.render(0.625F);
		rightLeg3.render(0.625F);
		rightLeg2.render(0.625F);
		leftLeg4.render(0.625F);
		leftLeg3.render(0.625F);
		leftLeg2.render(0.625F);
		leftLeg1.render(0.625F);
	}
	
	public void renderItemFirstPerson(ItemStack item) {
		RenderUtils.bindTexture(this.texture);
		GL11.glScalef(0.91F, 0.91F, 0.91F);
		GL11.glTranslatef(10.0F, -10.0F, 10.0F);
		GL11.glRotatef(180, 1.0F, 0.0F, 0.0F);
		GL11.glRotatef(-30, 0.0F, 1.0F, 1.0F);
		top.render(0.625F);
		rightLeg1.render(0.625F);
		rightLeg4.render(0.625F);
		rightLeg3.render(0.625F);
		rightLeg2.render(0.625F);
		leftLeg4.render(0.625F);
		leftLeg3.render(0.625F);
		leftLeg2.render(0.625F);
		leftLeg1.render(0.625F);
	}
	
	public void renderItemInventory(ItemStack item) {
		RenderUtils.bindTexture(this.texture);
		GL11.glPushMatrix();
		
		GL11.glTranslatef(6.0F, 12.5F, 0.0F);
		GL11.glScalef(0.7F, 0.7F, 0.7F);
		GL11.glRotatef(45, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(315, 1.0F, 0.0F, 1.0F);

		rightLeg1.render(0.625F);
		rightLeg4.render(0.625F);
		rightLeg3.render(0.625F);
		rightLeg2.render(0.625F);
		leftLeg4.render(0.625F);
		leftLeg3.render(0.625F);
		leftLeg2.render(0.625F);
		leftLeg1.render(0.625F);
		top.render(0.625F);
		GL11.glPopMatrix();
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}
