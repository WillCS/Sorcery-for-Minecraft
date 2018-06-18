package sorcery.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import sorcery.api.spellcasting.Wand;
import sorcery.api.spellcasting.WandComponent.WandComponentBody;
import sorcery.client.render.MagicRenderHelper;
import sorcery.client.render.RenderUtils;
import sorcery.items.ItemWand;
import sorcery.lib.SorceryItems;
import sorcery.lib.SpellHelper;
import sorcery.lib.utils.Utils;

public class ModelWand extends ModelBase {
	ModelRenderer Hilt;
	ModelRenderer Body;
	
	ModelRenderer Wrench;
	ModelRenderer Base1;
	ModelRenderer Prong1;
	ModelRenderer Base2;
	ModelRenderer Prong2;
	
    ResourceLocation wrenchTexture = Utils.getResource("textures/entities/wand/wrench.png");
	
	ResourceLocation wandHiltTexture = Utils.getResource("textures/entities/wand/wandHilt.png");

	public ModelWand() {
		this.textureWidth = 64;
		this.textureHeight = 32;

		Hilt = new ModelRenderer(this, 0, 26);
		Hilt.addBox(-0.5F, -5F, -0.5F, 1, 5, 1);
		Hilt.setRotationPoint(0F, 0F, 0F);
		Hilt.setTextureSize(64, 32);
		setRotation(Hilt, 0F, 0F, 0F);
		
		Body = new ModelRenderer(this, 0, 0);
		Body.addBox(-0.5F, -16F, -0.5F, 1, 16, 1);
		Body.setRotationPoint(0F, 0F, 0F);
		Body.setTextureSize(64, 32);
		setRotation(Body, 0F, 0F, 0F);
		
		
		Wrench = new ModelRenderer(this, 0, 0).setTextureSize(64, 32);
		Wrench.addBox(-0.5F, -13F, -0.5F, 1, 7, 1);
		Wrench.setRotationPoint(0F, 0F, 0F);
		setRotation(Wrench, 0F, 0F, 0F);
		
		Base1 = new ModelRenderer(this, 0, 14).setTextureSize(64, 32);
		Base1.addBox(-0.5F, -2F, -0.5F, 1, 2, 1);
		Base1.setRotationPoint(0F, -13F, 0F);
		setRotation(Base1, 0F, 0F, -1.22173F);
		
		Prong1 = new ModelRenderer(this, 0, 9).setTextureSize(64, 32);
		Prong1.addBox(1.9F, -0.5F, -0.5F, 1, 3, 1);
		Prong1.setRotationPoint(0F, -13F, 0F);
		setRotation(Prong1, 0F, 0F, -2.792527F);
		
		Base2 = new ModelRenderer(this, 0, 14).setTextureSize(64, 32);
		Base2.addBox(-0.5F, -2F, -0.5F, 1, 2, 1);
		Base2.setRotationPoint(0F, -13F, 0F);
		setRotation(Base2, 0F, 0F, 1.22173F);
		
		Prong2 = new ModelRenderer(this, 0, 9).setTextureSize(64, 32);
		Prong2.addBox(1.9F, -2.5F, -0.5F, 1, 3, 1);
		Prong2.setRotationPoint(0F, -13F, 0F);
		setRotation(Prong2, 0F, 0F, -0.3490659F);
	}

	public void render(ItemStack item, boolean transparency) {
		Wand wand = ((ItemWand)SorceryItems.wand).getWandFromItem(item);
		
		GL11.glRotatef(220, 0F, 0F, 1.0F);
		GL11.glScalef(0.1F, 0.1F, 0.1F);
		GL11.glTranslatef(-7.0F, 4.0F, 0.0F);
		
		RenderUtils.bindTexture(this.wandHiltTexture);
		if(wand.colour != -1) {
			float[] colours = Utils.decodeColourF(ItemDye.field_150922_c[wand.colour]);
			GL11.glColor3f(colours[0], colours[1], colours[2]);
		} else GL11.glColor3f(158F / 1F, 73F / 1F, 42F / 1F);
		Hilt.render(0.65F);
		
		if(ItemWand.isWrench(item)) {
			GL11.glPushMatrix();
			if(transparency)
				GL11.glColor4f(1F, 1F, 1F, 0.4F);
			else
				GL11.glColor3f(1F, 1F, 1F);
			RenderUtils.bindTexture(this.wrenchTexture);
			GL11.glEnable(GL11.GL_NORMALIZE);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			
			Wrench.render(0.625F);
			Base1.render(0.625F);
			Prong1.render(0.625F);
			Base2.render(0.625F);
			Prong2.render(0.625F);
			
			GL11.glDisable(GL11.GL_NORMALIZE);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glPopMatrix();
		} else {
			GL11.glColor4f(1F, 1F, 1F, 1.0F);
			if(wand.body != null) {
				RenderUtils.bindTexture(wand.body.texture);
			} else RenderUtils.bindTexture(((WandComponentBody)SpellHelper.getInstance().getWandComponentByName("wandBodyWood")).texture);
			Body.render(0.625F);
		}
		
		if(wand.generator != null)
			MagicRenderHelper.models.get(wand.generator.name).render(item);
		if(wand.core != null)
			MagicRenderHelper.models.get(wand.core.name).render(item);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

}