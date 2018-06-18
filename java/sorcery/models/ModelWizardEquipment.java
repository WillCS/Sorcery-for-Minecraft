package sorcery.models;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import sorcery.client.render.RenderUtils;
import sorcery.items.ItemWizardHat;
import sorcery.lib.Properties;
import sorcery.lib.utils.Utils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelWizardEquipment extends ModelBiped {
	ModelRenderer Base;
	ModelRenderer Buckle;
	ModelRenderer Middle;
	ModelRenderer Top;
	ModelRenderer Tip;
	EntityLivingBase entity;
	
	ResourceLocation texture = Utils.getResource(Properties.ENTITY_TEXTURE_FOLDER + "wizardHatColour.png");
	ResourceLocation overlay = Utils.getResource(Properties.ENTITY_TEXTURE_FOLDER + "wizardHatOverlay.png");
	
	public ModelWizardEquipment(EntityLivingBase entity) {
		this.entity = entity;
		Base = new ModelRenderer(this, 0, 0).setTextureSize(64, 64);
		Base.addBox(-6F, -9F, -6F, 12, 2, 12);
		Base.setRotationPoint(this.bipedHead.rotationPointX, this.bipedHead.rotationPointY, this.bipedHead.rotationPointZ);
		setRotation(Base, 0F, 0F, 0F);
		
		Buckle = new ModelRenderer(this, 0, 14).setTextureSize(64, 64);
		Buckle.addBox(-4F, -12.5F, -4F, 8, 4, 8);
		Buckle.setRotationPoint(this.bipedHead.rotationPointX, this.bipedHead.rotationPointY, this.bipedHead.rotationPointZ);
		setRotation(Buckle, -0.1115358F, 0F, 0F);
		
		Middle = new ModelRenderer(this, 0, 26).setTextureSize(64, 64);
		Middle.addBox(-3F, -16.5F, -3F, 6, 5, 6);
		Middle.setRotationPoint(this.bipedHead.rotationPointX, this.bipedHead.rotationPointY, this.bipedHead.rotationPointZ);
		setRotation(Middle, -0.2230717F, 0F, 0F);
		
		Top = new ModelRenderer(this, 0, 37).setTextureSize(64, 64);
		Top.addBox(-2F, -21F, -2F, 4, 5, 4);
		Top.setRotationPoint(this.bipedHead.rotationPointX, this.bipedHead.rotationPointY, this.bipedHead.rotationPointZ);
		setRotation(Top, -0.3346075F, 0F, 0F);
		
		Tip = new ModelRenderer(this, 0, 46).setTextureSize(64, 64);
		Tip.addBox(-1F, -24F, -1F, 2, 3, 2);
		Tip.setRotationPoint(this.bipedHead.rotationPointX, this.bipedHead.rotationPointY, this.bipedHead.rotationPointZ);
		setRotation(Tip, -0.4089647F, 0F, 0F);
	}
	
	public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity) {
		this.bipedHead.rotateAngleY = par4 / (180F / (float)Math.PI);
		this.bipedHead.rotateAngleX = par5 / (180F / (float)Math.PI);
		
		this.setRotation(this.Base, this.bipedHead.rotateAngleX - (0.03F), this.bipedHead.rotateAngleY, this.bipedHead.rotateAngleZ);
		this.setRotation(this.Buckle, this.bipedHead.rotateAngleX - (0.1115358F / 2), this.bipedHead.rotateAngleY, this.bipedHead.rotateAngleZ);
		this.setRotation(this.Middle, this.bipedHead.rotateAngleX - (0.2230717F / 2), this.bipedHead.rotateAngleY, this.bipedHead.rotateAngleZ);
		this.setRotation(this.Tip, this.bipedHead.rotateAngleX - (0.4089647F / 2), this.bipedHead.rotateAngleY, this.bipedHead.rotateAngleZ);
		this.setRotation(this.Top, this.bipedHead.rotateAngleX - (0.3346075F / 2), this.bipedHead.rotateAngleY, this.bipedHead.rotateAngleZ);
	}
	
	@Override
	public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
		ItemStack item = (entity).getEquipmentInSlot(4);
		if(item == null || !(item.getItem() instanceof ItemWizardHat))
			return;
		this.setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
		GL11.glPushMatrix();
		RenderUtils.bindTexture(texture);
		int colour = ((ItemWizardHat)item.getItem()).getColorFromItemStack(item, 0);
		float r = (float)(colour >> 16 & 255) / 255.0F;
		float g = (float)(colour >> 8 & 255) / 255.0F;
		float b = (float)(colour & 255) / 255.0F;
		GL11.glColor3f(r, g, b);
		Base.render(par7);
		Buckle.render(par7);
		Middle.render(par7);
		Top.render(par7);
		Tip.render(par7);
		GL11.glColor3f(1f, 1f, 1f);
		GL11.glPopMatrix();
		
		RenderUtils.bindTexture(overlay);
		Base.render(par7);
		Buckle.render(par7);
		Middle.render(par7);
		Top.render(par7);
		Tip.render(par7);
	}
	
	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}
