package sorcery.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.item.ItemDye;

import org.lwjgl.opengl.GL11;

import sorcery.client.render.RenderUtils;
import sorcery.lib.NodeTransportHelper;
import sorcery.lib.Properties;
import sorcery.tileentities.nodes.TileEntityNodeBase;
import cpw.mods.fml.client.FMLClientHandler;

public class ModelNodeOutput extends ModelBase {
	ModelRenderer Support_2;
	ModelRenderer Support_1;
	ModelRenderer Base;
	ModelRenderer Support_4;
	ModelRenderer Support_3;
	ModelRenderer Center_1;
	ModelRenderer Beam_1;
	ModelRenderer Beam_2;
	ModelRenderer Rim;
	
	public ModelNodeOutput() {
		Support_2 = new ModelRenderer(this, 0, 19).setTextureSize(64, 64);
		Support_2.addBox(-5F, 4F, -1F, 10, 1, 2);
		Support_2.setRotationPoint(0F, 0F, 0F);
		setRotation(Support_2, 0F, 0F, 0F);
		
		Support_1 = new ModelRenderer(this, 0, 13).setTextureSize(64, 64);
		Support_1.addBox(-1F, 5F, -1F, 2, 3, 2);
		Support_1.setRotationPoint(0F, 0F, 0F);
		setRotation(Support_1, 0F, 0F, 0F);
		
		Base = new ModelRenderer(this, 0, 32).setTextureSize(64, 64);
		Base.addBox(-3F, 7F, -3F, 6, 1, 6);
		Base.setRotationPoint(0F, 0F, 0F);
		setRotation(Base, 0F, 0F, 0F);
		
		Support_4 = new ModelRenderer(this, 0, 23).setTextureSize(64, 64);
		Support_4.addBox(-5F, -1F, -1F, 1, 6, 2);
		Support_4.setRotationPoint(0F, 0F, 0F);
		setRotation(Support_4, 0F, 0F, 0F);
		
		Support_3 = new ModelRenderer(this, 0, 23).setTextureSize(64, 64);
		Support_3.addBox(4F, -1F, -1F, 1, 6, 2);
		Support_3.setRotationPoint(0F, 0F, 0F);
		setRotation(Support_3, 0F, 0F, 0F);
		
		Center_1 = new ModelRenderer(this, 7, 25).setTextureSize(64, 64);
		Center_1.addBox(-1.5F, -1.5F, -1.5F, 3, 3, 3);
		Center_1.setRotationPoint(0F, 0F, 0F);
		setRotation(Center_1, 0F, 0F, 0F);
		
		Beam_1 = new ModelRenderer(this, 16, 13).setTextureSize(64, 64);
		Beam_1.addBox(-0.5F, -0.5F, -5.5F, 1, 1, 4);
		Beam_1.setRotationPoint(0F, 0F, 0F);
		setRotation(Beam_1, 0F, 0F, 0F);
		
		Beam_2 = new ModelRenderer(this, 0, 8).setTextureSize(64, 64);
		Beam_2.addBox(-1F, -1F, -7F, 2, 2, 2);
		Beam_2.setRotationPoint(0F, 0F, 0F);
		setRotation(Beam_2, 0F, 0F, 0F);
		
		Rim = new ModelRenderer(this, 0, 0).setTextureSize(64, 64);
		Rim.addBox(-2.5F, -2.5F, -0.5F, 5, 5, 1);
		Rim.setRotationPoint(0F, 0F, 0F);
		setRotation(Rim, 0F, 0F, 0F);
	}
	
	public void render(TileEntityNodeBase node, float scale) {
		GL11.glPushMatrix();
		RenderUtils.bindTexture(NodeTransportHelper.textures[0]);
		setRotationAngles(node, scale);
		Support_2.render(scale);
		Support_1.render(scale);
		Base.render(scale);
		Support_4.render(scale);
		Support_3.render(scale);
		Center_1.render(scale);
		Beam_1.render(scale);
		Beam_2.render(scale);
		Rim.render(scale);
		
		if(node != null && node.colourTarget != -1) {
			int colour = ItemDye.field_150922_c[node.colourTarget];
			float r = (float)(colour >> 16 & 255) / 255.0F;
			float g = (float)(colour >> 8 & 255) / 255.0F;
			float b = (float)(colour & 255) / 255.0F;
			GL11.glColor3f(r, g, b);
		}
		Beam_2.render(scale);
		GL11.glColor3f(1.0F, 1.0F, 1.0F);
		GL11.glPopMatrix();
	}
	
	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
	
	public void setRotationAngles(TileEntityNodeBase node, float scale) {
		if(node != null) {
			this.rotateStand(node.standRotation);
			this.rotateNode(node.nodeRotation);
			this.setFace(node.getConnectedSide());
		} else {
			this.rotateStand(RenderUtils.rightAngle);
			this.setFace(1);
		}
	}
	
	public void rotateStand(float rotation) {
		this.Support_1.rotateAngleY = rotation;
		this.Support_2.rotateAngleY = rotation;
		this.Support_3.rotateAngleY = rotation;
		this.Support_4.rotateAngleY = rotation;
		this.Center_1.rotateAngleY = rotation;
		this.Beam_1.rotateAngleY = rotation;
		this.Beam_2.rotateAngleY = rotation;
		this.Rim.rotateAngleY = rotation;
	}
	
	public void rotateNode(float rotation) {
		this.Center_1.rotateAngleX += rotation;
		this.Beam_1.rotateAngleX += rotation;
		this.Beam_2.rotateAngleX += rotation;
		this.Rim.rotateAngleX += rotation;
	}
	
	public void setFace(int face) {
		float zRotation = 0F;
		float xRotation = 0F;
		
		switch(face) {
			case 0:
				break;
			case 1:
				zRotation = RenderUtils.rightAngle * 2;
				break;
			case 2:
				xRotation = -RenderUtils.rightAngle;
				break;
			case 3:
				xRotation = RenderUtils.rightAngle;
				break;
			case 4:
				zRotation = -RenderUtils.rightAngle;
				break;
			case 5:
				zRotation = RenderUtils.rightAngle;
				break;
		}
		
		this.Support_1.rotateAngleZ = zRotation;
		this.Support_2.rotateAngleZ = zRotation;
		this.Support_3.rotateAngleZ = zRotation;
		this.Support_4.rotateAngleZ = zRotation;
		this.Center_1.rotateAngleZ = zRotation;
		this.Beam_1.rotateAngleZ = zRotation;
		this.Beam_2.rotateAngleZ = zRotation;
		this.Rim.rotateAngleZ = zRotation;
		this.Base.rotateAngleZ = zRotation;
		
		this.Support_1.rotateAngleX = xRotation;
		this.Support_2.rotateAngleX = xRotation;
		this.Support_3.rotateAngleX = xRotation;
		this.Support_4.rotateAngleX = xRotation;
		this.Center_1.rotateAngleX = xRotation;
		this.Beam_1.rotateAngleX = xRotation;
		this.Beam_2.rotateAngleX = xRotation;
		this.Rim.rotateAngleX = xRotation;
		this.Base.rotateAngleX = xRotation;
	}
}
