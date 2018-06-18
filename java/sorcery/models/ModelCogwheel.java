package sorcery.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;
import sorcery.client.render.RenderUtils;
import sorcery.lib.utils.Utils;

public class ModelCogwheel extends ModelBase {
	ResourceLocation[] textures = {
			Utils.getResource("textures/entities/cogIron.png"),
			Utils.getResource("textures/entities/cogBrass.png"),
			Utils.getResource("textures/entities/cogSteel.png")
		};
	ModelRenderer tooth1;
	ModelRenderer ring1;
	ModelRenderer tooth2;
	ModelRenderer ring2;
	ModelRenderer tooth3;
	ModelRenderer ring3;
	ModelRenderer tooth4;
	ModelRenderer ring4;
	ModelRenderer tooth5;
	ModelRenderer ring5;
	ModelRenderer tooth6;
	ModelRenderer ring6;
	ModelRenderer tooth7;
	ModelRenderer ring7;
	ModelRenderer tooth8;
	ModelRenderer ring8;
	ModelRenderer middle;
	ModelRenderer bolt;
	
	public ModelCogwheel() {
		textureWidth = 64;
		textureHeight = 32;
		
		tooth1 = new ModelRenderer(this, 13, 0).setTextureSize(64, 32);
		tooth1.addBox(-7F, 0F, -1.5F, 2, 1, 3);
		tooth1.setRotationPoint(0F, 0F, 0F);
		setRotation(tooth1, 0F, 1.570796F, 0F);
		
		ring1 = new ModelRenderer(this, 0, 0).setTextureSize(64, 32);
		ring1.addBox(-5F, 0F, -2F, 2, 1, 4);
		ring1.setRotationPoint(0F, 0F, 0F);
		setRotation(ring1, 0F, 1.570796F, 0F);
		
		tooth2 = new ModelRenderer(this, 13, 0).setTextureSize(64, 32);
		tooth2.addBox(-7F, 0F, -1.5F, 2, 1, 3);
		tooth2.setRotationPoint(0F, 0F, 0F);
		setRotation(tooth2, 0F, 0.7853982F, 0F);
		
		ring2 = new ModelRenderer(this, 0, 0).setTextureSize(64, 32);
		ring2.addBox(-5F, 0F, -2F, 2, 1, 4);
		ring2.setRotationPoint(0F, 0F, 0F);
		setRotation(ring2, 0F, 0.7853982F, 0F);
		
		tooth3 = new ModelRenderer(this, 13, 0).setTextureSize(64, 32);
		tooth3.addBox(-7F, 0F, -1.5F, 2, 1, 3);
		tooth3.setRotationPoint(0F, 0F, 0F);
		setRotation(tooth3, 0F, 2.356194F, 0F);
		
		ring3 = new ModelRenderer(this, 0, 0).setTextureSize(64, 32);
		ring3.addBox(-5F, 0F, -2F, 2, 1, 4);
		ring3.setRotationPoint(0F, 0F, 0F);
		setRotation(ring3, 0F, 2.356194F, 0F);
		
		tooth4 = new ModelRenderer(this, 13, 0).setTextureSize(64, 32);
		tooth4.addBox(-7F, 0F, -1.5F, 2, 1, 3);
		tooth4.setRotationPoint(0F, 0F, 0F);
		setRotation(tooth4, 0F, 3.141593F, 0F);
		
		ring4 = new ModelRenderer(this, 0, 0).setTextureSize(64, 32);
		ring4.addBox(-5F, 0F, -2F, 2, 1, 4);
		ring4.setRotationPoint(0F, 0F, 0F);
		setRotation(ring4, 0F, 3.141593F, 0F);
		
		tooth5 = new ModelRenderer(this, 13, 0).setTextureSize(64, 32);
		tooth5.addBox(-7F, 0F, -1.5F, 2, 1, 3);
		tooth5.setRotationPoint(0F, 0F, 0F);
		setRotation(tooth5, 0F, -0.7853982F, 0F);
		
		ring5 = new ModelRenderer(this, 0, 0).setTextureSize(64, 32);
		ring5.addBox(-5F, 0F, -2F, 2, 1, 4);
		ring5.setRotationPoint(0F, 0F, 0F);
		setRotation(ring5, 0F, -0.7853982F, 0F);
		
		tooth6 = new ModelRenderer(this, 13, 0).setTextureSize(64, 32);
		tooth6.addBox(-7F, 0F, -1.5F, 2, 1, 3);
		tooth6.setRotationPoint(0F, 0F, 0F);
		setRotation(tooth6, 0F, -1.570796F, 0F);
		
		ring6 = new ModelRenderer(this, 0, 0).setTextureSize(64, 32);
		ring6.addBox(-5F, 0F, -2F, 2, 1, 4);
		ring6.setRotationPoint(0F, 0F, 0F);
		setRotation(ring6, 0F, -1.570796F, 0F);
		
		tooth7 = new ModelRenderer(this, 13, 0).setTextureSize(64, 32);
		tooth7.addBox(-7F, 0F, -1.5F, 2, 1, 3);
		tooth7.setRotationPoint(0F, 0F, 0F);
		setRotation(tooth7, 0F, -2.356194F, 0F);
		
		ring7 = new ModelRenderer(this, 0, 0).setTextureSize(64, 32);
		ring7.addBox(-5F, 0F, -2F, 2, 1, 4);
		ring7.setRotationPoint(0F, 0F, 0F);
		setRotation(ring7, 0F, -2.356194F, 0F);
		
		tooth8 = new ModelRenderer(this, 13, 0).setTextureSize(64, 32);
		tooth8.addBox(-7F, 0F, -1.5F, 2, 1, 3);
		tooth8.setRotationPoint(0F, 0F, 0F);
		setRotation(tooth8, 0F, 0F, 0F);
		
		ring8 = new ModelRenderer(this, 0, 0).setTextureSize(64, 32);
		ring8.addBox(-5F, 0F, -2F, 2, 1, 4);
		ring8.setRotationPoint(0F, 0F, 0F);
		setRotation(ring8, 0F, 0F, 0F);
		
		middle = new ModelRenderer(this, 0, 6).setTextureSize(64, 32);
		middle.addBox(-3F, 0F, -3F, 6, 1, 6);
		middle.setRotationPoint(0F, 0F, 0F);
		setRotation(middle, 0F, 0F, 0F);
		
		bolt = new ModelRenderer(this, 24, 0).setTextureSize(64, 32);
		bolt.addBox(-1F, -0.5F, -1F, 2, 1, 2);
		bolt.setRotationPoint(0F, 0F, 0F);
		setRotation(bolt, 0F, 0F, 0F);
	}
	
	public void render(boolean hole, int type) {
		RenderUtils.bindTexture(textures[type]);
		tooth1.render(0.625F);
		ring1.render(0.625F);
		
		tooth2.render(0.625F);
		ring2.render(0.625F);
		
		tooth3.render(0.625F);
		ring3.render(0.625F);
		
		tooth4.render(0.625F);
		ring4.render(0.625F);
		
		tooth5.render(0.625F);
		ring5.render(0.625F);
		
		tooth6.render(0.625F);
		ring6.render(0.625F);
		
		tooth7.render(0.625F);
		ring7.render(0.625F);
		
		tooth8.render(0.625F);
		ring8.render(0.625F);
		if(hole) {
			middle.render(0.625F);
			bolt.render(0.625F);
		}
	}
	
	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}
