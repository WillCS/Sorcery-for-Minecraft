package sorcery.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.item.ItemDye;
import net.minecraftforge.common.util.ForgeDirection;

import org.lwjgl.opengl.GL11;

import sorcery.client.render.RenderUtils;
import sorcery.fluid.Pipe;
import sorcery.fluid.PipeHelper;
import sorcery.tileentities.TileEntityPipe;

public class ModelPipe extends ModelBase {
	ModelRenderer Left_Joint;
	ModelRenderer Right_Joint;
	ModelRenderer Back_Joint;
	ModelRenderer Front_Joint;
	ModelRenderer Middle_Pipe;
	ModelRenderer Bottom_Joint;
	ModelRenderer Top_Joint;

	ModelRenderer Top_Pipe;
	ModelRenderer Right_Pipe;
	ModelRenderer Bottom_Pipe;
	ModelRenderer Back_Pipe;
	ModelRenderer Left_Pipe;
	ModelRenderer Front_Pipe;

	public ModelPipe() {
		this.textureWidth = 64;
		this.textureHeight = 32;

		Right_Joint = new ModelRenderer(this, 0, 0);
		Right_Joint.addBox(7F, -3F, -3F, 1, 6, 6);
		Right_Joint.setRotationPoint(0F, 0F, 0F);
		Right_Joint.setTextureSize(64, 32);
		setRotation(Right_Joint, 0F, 0F, -3.141593F);

		Back_Joint = new ModelRenderer(this, 0, 0);
		Back_Joint.addBox(7F, -3F, -3F, 1, 6, 6);
		Back_Joint.setRotationPoint(0F, 0F, 0F);
		Back_Joint.setTextureSize(64, 32);
		setRotation(Back_Joint, 0F, 1.570796F, -3.141593F);

		Middle_Pipe = new ModelRenderer(this, 0, 22);
		Middle_Pipe.addBox(-2F, -2F, -2F, 4, 4, 4);
		Middle_Pipe.setRotationPoint(0F, 0F, 0F);
		Middle_Pipe.setTextureSize(64, 32);
		setRotation(Middle_Pipe, 1.570796F, 0F, 1.570796F);

		Top_Joint = new ModelRenderer(this, 0, 0);
		Top_Joint.addBox(7F, -3F, -3F, 1, 6, 6);
		Top_Joint.setRotationPoint(0F, 0F, 0F);
		Top_Joint.setTextureSize(64, 32);
		setRotation(Top_Joint, 0F, 0F, -1.570796F);

		Top_Pipe = new ModelRenderer(this, 0, 13);
		Top_Pipe.addBox(2F, -2F, -2F, 6, 4, 4);
		Top_Pipe.setRotationPoint(0F, 0F, 0F);
		Top_Pipe.setTextureSize(64, 32);
		setRotation(Top_Pipe, 1.570796F, 0, -1.570796F);

		Back_Pipe = new ModelRenderer(this, 0, 13);
		Back_Pipe.addBox(2F, -2F, -2F, 6, 4, 4);
		Back_Pipe.setRotationPoint(0F, 0F, 0F);
		Back_Pipe.setTextureSize(64, 32);
		setRotation(Back_Pipe, -1.570796F, 1.570796F, 0F);

		Right_Pipe = new ModelRenderer(this, 0, 13);
		Right_Pipe.addBox(2F, -2F, -2F, 6, 4, 4);
		Right_Pipe.setRotationPoint(0F, 0F, 0F);
		Right_Pipe.setTextureSize(64, 32);
		setRotation(Right_Pipe, -1.570796F, 3.141593F, 0F);

		Left_Joint = new ModelRenderer(this, 0, 0);
		Left_Joint.addBox(7F, -3F, -3F, 1, 6, 6);
		Left_Joint.setRotationPoint(0F, 0F, 0F);
		setRotation(Left_Joint, 0F, 0F, 0F);

		Front_Joint = new ModelRenderer(this, 0, 0);
		Front_Joint.addBox(7F, -3F, -3F, 1, 6, 6);
		Front_Joint.setRotationPoint(0F, 0F, 0F);
		Front_Joint.setTextureSize(64, 32);
		setRotation(Front_Joint, 0F, -1.570796F, 0F);

		Bottom_Joint = new ModelRenderer(this, 0, 0);
		Bottom_Joint.addBox(7F, -3F, -3F, 1, 6, 6);
		Bottom_Joint.setRotationPoint(0F, 0F, 0F);
		Bottom_Joint.setTextureSize(64, 32);
		setRotation(Bottom_Joint, 0F, 0F, 1.570796F);

		Bottom_Pipe = new ModelRenderer(this, 0, 13);
		Bottom_Pipe.addBox(2F, -2F, -2F, 6, 4, 4);
		Bottom_Pipe.setRotationPoint(0F, 0F, 0F);
		Bottom_Pipe.setTextureSize(64, 32);
		setRotation(Bottom_Pipe, 1.570796F, 0, 1.570796F);

		Left_Pipe = new ModelRenderer(this, 0, 13);
		Left_Pipe.addBox(2F, -2F, -2F, 6, 4, 4);
		Left_Pipe.setRotationPoint(0F, 0F, 0F);
		Left_Pipe.setTextureSize(64, 32);
		setRotation(Left_Pipe, 1.570796F, 0F, 0F);

		Front_Pipe = new ModelRenderer(this, 0, 13);
		Front_Pipe.addBox(2F, -2F, -2F, 6, 4, 4);
		Front_Pipe.setRotationPoint(0F, 0F, 0F);
		Front_Pipe.setTextureSize(64, 32);
		setRotation(Front_Pipe, 1.570796F, -1.570796F, 0F);
	}
	
	public void render(TileEntityPipe pipe, float scale) {
		if(pipe != null && pipe.pipe != null) {
			if(pipe.pipe.type != null)
				RenderUtils.bindTexture(PipeHelper.textures[pipe.pipe.type.id]);
			
			if(!pipe.isConnecting()) {
				Left_Joint.render(scale);
				Left_Pipe.render(scale);
				Right_Joint.render(scale);
				Right_Pipe.render(scale);
			} else if(pipe.getConnections() == 1) {
				if(pipe.canConnect(ForgeDirection.WEST)) {
					Left_Joint.render(scale);
					Left_Pipe.render(scale);
					Right_Joint.render(scale);
					Right_Pipe.render(scale);
				} else if(pipe.canConnect(ForgeDirection.EAST)) {
					Left_Joint.render(scale);
					Left_Pipe.render(scale);
					Right_Joint.render(scale);
					Right_Pipe.render(scale);
				} else if(pipe.canConnect(ForgeDirection.NORTH)) {
					Front_Joint.render(scale);
					Front_Pipe.render(scale);
					Back_Joint.render(scale);
					Back_Pipe.render(scale);
				} else if(pipe.canConnect(ForgeDirection.SOUTH)) {
					Front_Joint.render(scale);
					Front_Pipe.render(scale);
					Back_Joint.render(scale);
					Back_Pipe.render(scale);
				} else if(pipe.canConnect(ForgeDirection.DOWN)) {
					Top_Joint.render(scale);
					Top_Pipe.render(scale);
					Bottom_Joint.render(scale);
					Bottom_Pipe.render(scale);
				} else if(pipe.canConnect(ForgeDirection.UP)) {
					Top_Joint.render(scale);
					Top_Pipe.render(scale);
					Bottom_Joint.render(scale);
					Bottom_Pipe.render(scale);
				}
			} else {
				if(pipe.canConnect(ForgeDirection.WEST)) {
					Left_Joint.render(scale);
					Left_Pipe.render(scale);
				}
				
				if(pipe.canConnect(ForgeDirection.EAST)) {
					Right_Joint.render(scale);
					Right_Pipe.render(scale);
				} 
				
				if(pipe.canConnect(ForgeDirection.NORTH)) {
					Back_Joint.render(scale);
					Back_Pipe.render(scale);
				}
				
				if(pipe.canConnect(ForgeDirection.SOUTH)) {
					Front_Joint.render(scale);
					Front_Pipe.render(scale);
				}
				
				if(pipe.canConnect(ForgeDirection.DOWN)) {
					Bottom_Joint.render(scale);
					Bottom_Pipe.render(scale);
				}
					
				if(pipe.canConnect(ForgeDirection.UP)) {
					Top_Joint.render(scale);
					Top_Pipe.render(scale);
				}
			}
			
			Middle_Pipe.render(scale);
			
			if(pipe.colour != -1) {
				int colour = ItemDye.field_150922_c[pipe.colour];
				float r = (float)(colour >> 16 & 255) / 255.0F;
				float g = (float)(colour >> 8 & 255) / 255.0F;
				float b = (float)(colour & 255) / 255.0F;
				GL11.glColor3f(r, g, b);
				RenderUtils.bindTexture(PipeHelper.colours);
			
				if(pipe.canConnect(ForgeDirection.WEST))
					Left_Pipe.render(scale);
				
				if(pipe.canConnect(ForgeDirection.EAST))
					Right_Pipe.render(scale);
				
				if(pipe.canConnect(ForgeDirection.NORTH))
					Back_Pipe.render(scale);
				
				if(pipe.canConnect(ForgeDirection.SOUTH))
					Front_Pipe.render(scale);
				
				if(pipe.canConnect(ForgeDirection.DOWN))
					Bottom_Pipe.render(scale);
				
				if(pipe.canConnect(ForgeDirection.UP))
					Top_Pipe.render(scale);
				
				Middle_Pipe.render(scale);
			}
		}
	}

	public void render(Pipe pipe, float scale) {
		if(pipe != null) {
			if(pipe.type != null)
				RenderUtils.bindTexture(PipeHelper.textures[pipe.type.id]);
			
			Bottom_Joint.render(scale);
			Top_Joint.render(scale);
			
			Top_Pipe.render(scale);
			Bottom_Pipe.render(scale);
			Middle_Pipe.render(scale);
		}
		
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}
