package sorcery.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import sorcery.client.render.RenderUtils;
import sorcery.items.ItemWizardHat;
import sorcery.lib.Properties;
import sorcery.lib.utils.Utils;
import cpw.mods.fml.client.FMLClientHandler;

public class ModelWizardHat extends ModelBase {
	ModelRenderer Base;
	ModelRenderer Buckle;
	ModelRenderer Middle;
	ModelRenderer Top;
	ModelRenderer Tip;
	
	ResourceLocation texture = Utils.getResource(Properties.ENTITY_TEXTURE_FOLDER + "wizardHatColour.png");
	ResourceLocation overlay = Utils.getResource(Properties.ENTITY_TEXTURE_FOLDER + "wizardHatOverlay.png");
	
	public ModelWizardHat() {
		Base = new ModelRenderer(this, 0, 0).setTextureSize(64, 64);
		Base.addBox(-6F, 1F, -6F, 12, 2, 12);
		Base.setRotationPoint(0F, 0, 0F);
		setRotation(Base, (float)Math.PI, 0F, 0F);
		
		Buckle = new ModelRenderer(this, 0, 14).setTextureSize(64, 64);
		Buckle.addBox(-4F, -3F, -4F, 8, 4, 8);
		Buckle.setRotationPoint(0F, 0, 0F);
		setRotation(Buckle, (float)Math.PI, 0F, 0F);
		
		Middle = new ModelRenderer(this, 0, 26).setTextureSize(64, 64);
		Middle.addBox(-3F, -8F, -3F, 6, 5, 6);
		Middle.setRotationPoint(0F, 0, 0F);
		setRotation(Middle, (float)Math.PI, 0F, 0F);
		
		Top = new ModelRenderer(this, 0, 37).setTextureSize(64, 64);
		Top.addBox(-2F, -13F, -2F, 4, 5, 4);
		Top.setRotationPoint(0F, 0, 0F);
		setRotation(Top, (float)Math.PI, 0F, 0F);
		
		Tip = new ModelRenderer(this, 0, 46).setTextureSize(64, 64);
		Tip.addBox(-1F, -16F, -1F, 2, 3, 2);
		Tip.setRotationPoint(0, 0, 0);
		setRotation(Tip, (float)Math.PI, 0F, 0F);
	}
	
	@Override
	public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
		Base.render(par7);
		Buckle.render(par7);
		Middle.render(par7);
		Top.render(par7);
	}
	
	public void render(ItemStack item, double x, double y, double z, float scale) {
		GL11.glPushMatrix();
		RenderUtils.bindTexture(texture);
		int colour = ((ItemWizardHat)item.getItem()).getColorFromItemStack(item, 0);
		float r = (float)(colour >> 16 & 255) / 255.0F;
		float g = (float)(colour >> 8 & 255) / 255.0F;
		float b = (float)(colour & 255) / 255.0F;
		GL11.glColor3f(r, g, b);
		Base.render(scale);
		Buckle.render(scale);
		Middle.render(scale);
		Top.render(scale);
		Tip.render(scale);
		GL11.glColor3f(1f, 1f, 1f);
		GL11.glPopMatrix();
		
		RenderUtils.bindTexture(overlay);
		Base.render(scale);
		Buckle.render(scale);
		Middle.render(scale);
		Top.render(scale);
		Tip.render(scale);
	}
	
	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}
