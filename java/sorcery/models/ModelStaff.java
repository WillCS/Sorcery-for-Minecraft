package sorcery.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import sorcery.api.element.Element;
import sorcery.client.render.RenderUtils;
import sorcery.lib.Properties;
import sorcery.lib.utils.Utils;

public class ModelStaff extends ModelBase {
	// fields
	ModelRenderer Shaft;
	ModelRenderer Orb;
	
	ResourceLocation texture = Utils.getResource(Properties.ENTITY_TEXTURE_FOLDER + "staff.png");
	
	public ModelStaff() {
		textureWidth = 64;
		textureHeight = 64;
		
		Shaft = new ModelRenderer(this, 0, 6);
		Shaft.addBox(0F, -17F, 0F, 2, 34, 2);
		Shaft.setRotationPoint(6F, -4F, -3F);
		Shaft.setTextureSize(64, 64);
		setRotation(Shaft, -0.2F, 0F, 3.3F);
		
		Orb = new ModelRenderer(this, 0, 0);
		Orb.addBox(-0.5F, -20F, -0.5F, 3, 3, 3);
		Orb.setRotationPoint(6F, -4F, -3F);
		Orb.setTextureSize(64, 64);
		setRotation(Orb, -0.2F, 0F, 3.3F);
	}
	
	public void render(ItemStack item, float f, float f1, float f2, float f3, float f4, float f5) {
		GL11.glPushMatrix();
		RenderUtils.bindTexture(texture);
		Shaft.render(f5);
		float[] rgb = Element.elementsList[item.getItemDamage()].getFloatColour();
		GL11.glColor4f(rgb[0], rgb[1], rgb[2], 0.5F);
		GL11.glEnable(GL11.GL_NORMALIZE);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		Orb.render(f5);
		GL11.glDisable(GL11.GL_NORMALIZE);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}
	
	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}
