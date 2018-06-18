package sorcery.models;

import org.lwjgl.opengl.GL11;

import sorcery.client.render.RenderUtils;
import sorcery.fluid.Pipe;
import sorcery.fluid.PipeHelper;
import sorcery.mojo.EnumWireType;
import sorcery.tileentities.TileEntityMojoWire;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemDye;
import net.minecraftforge.common.util.ForgeDirection;

public class ModelWire extends ModelBase {
	ModelRenderer Bottom;
	ModelRenderer Top;
	ModelRenderer Right;
	ModelRenderer Front;
	ModelRenderer Back;
	ModelRenderer Left;
	ModelRenderer Middle;

	public ModelWire() {
		this.textureWidth = 64;
		this.textureHeight = 32;
		
		Middle = new ModelRenderer(this, 0, 22);
		Middle.addBox(-2F, -2F, -2F, 4, 4, 4);
		Middle.setRotationPoint(0F, 0F, 0F);
		Middle.setTextureSize(64, 32);
		setRotation(Middle, 1.570796F, 0F, 1.570796F);

		Top = new ModelRenderer(this, 0, 13);
		Top.addBox(2F, -2F, -2F, 6, 4, 4);
		Top.setRotationPoint(0F, 0F, 0F);
		Top.setTextureSize(64, 32);
		setRotation(Top, 1.570796F, 0, -1.570796F);

		Back = new ModelRenderer(this, 0, 13);
		Back.addBox(2F, -2F, -2F, 6, 4, 4);
		Back.setRotationPoint(0F, 0F, 0F);
		Back.setTextureSize(64, 32);
		setRotation(Back, -1.570796F, 1.570796F, 0F);

		Right = new ModelRenderer(this, 0, 13);
		Right.addBox(2F, -2F, -2F, 6, 4, 4);
		Right.setRotationPoint(0F, 0F, 0F);
		Right.setTextureSize(64, 32);
		setRotation(Right, -1.570796F, 3.141593F, 0F);

		Bottom = new ModelRenderer(this, 0, 13);
		Bottom.addBox(2F, -2F, -2F, 6, 4, 4);
		Bottom.setRotationPoint(0F, 0F, 0F);
		Bottom.setTextureSize(64, 32);
		setRotation(Bottom, 1.570796F, 0, 1.570796F);

		Left = new ModelRenderer(this, 0, 13);
		Left.addBox(2F, -2F, -2F, 6, 4, 4);
		Left.setRotationPoint(0F, 0F, 0F);
		Left.setTextureSize(64, 32);
		setRotation(Left, 1.570796F, 0F, 0F);

		Front = new ModelRenderer(this, 0, 13);
		Front.addBox(2F, -2F, -2F, 6, 4, 4);
		Front.setRotationPoint(0F, 0F, 0F);
		Front.setTextureSize(64, 32);
		setRotation(Front, 1.570796F, -1.570796F, 0F);

		/*Bottom = new ModelRenderer(this, 15, 0);
		Bottom.addBox(1F, -0.5F, -0.5F, 7, 1, 1);
		Bottom.setRotationPoint(0F, 0F, 0F);
		Bottom.setTextureSize(64, 32);
		setRotation(Bottom, 0F, 0F, 1.570796F);
		
		Top = new ModelRenderer(this, 15, 0);
		Top.addBox(-8F, -0.5F, -0.5F, 7, 1, 1);
		Top.setRotationPoint(0F, 0F, 0F);
		Top.setTextureSize(64, 32);
		setRotation(Top, 0F, 0F, 1.570796F);
		
		Right = new ModelRenderer(this, 15, 0);
		Right.addBox(-8F, -0.5F, -0.5F, 8, 1, 1);
		Right.setRotationPoint(0F, 0F, 0F);
		Right.setTextureSize(64, 32);
		setRotation(Right, 1.570796F, 0F, 0F);
		
		Front = new ModelRenderer(this, 15, 0);
		Front.addBox(1F, -0.5F, -0.5F, 7, 1, 1);
		Front.setRotationPoint(0F, 0F, 0F);
		Front.setTextureSize(64, 32);
		setRotation(Front, 0F, 1.570796F, 0F);
		
		Back = new ModelRenderer(this, 15, 0);
		Back.addBox(-8F, -0.5F, -0.5F, 7, 1, 1);
		Back.setRotationPoint(0F, 0F, 0F);
		Back.setTextureSize(64, 32);
		setRotation(Back, 0F, 1.570796F, 0F);
		
		Left = new ModelRenderer(this, 15, 0);
		Left.addBox(1F, -0.5F, -0.5F, 7, 1, 1);
		Left.setRotationPoint(0F, 0F, 0F);
		Left.setTextureSize(64, 32);
		setRotation(Left, 1.570796F, 0F, 0F);
		
		Middle = new ModelRenderer(this, 15, 3);
		Middle.addBox(-1F, -1F, -1F, 2, 2, 2);
		Middle.setRotationPoint(0F, 0F, 0F);
		Middle.setTextureSize(64, 32);
		setRotation(Middle, 0F, 0F, 0F);*/
	}

	public void render(TileEntityMojoWire wire, float scale) {
		if(wire != null) {
			if(wire.type != null)
				RenderUtils.bindTexture(wire.type.texture);
			
			if(!wire.isConnecting()) {
				Left.render(scale);
				Left.render(scale);
				Right.render(scale);
				Right.render(scale);
			} else {
				if(wire.canConnect(ForgeDirection.WEST))
					Left.render(scale);
				
				if(wire.canConnect(ForgeDirection.EAST))
					Right.render(scale);
				
				if(wire.canConnect(ForgeDirection.NORTH))
					Back.render(scale);
				
				if(wire.canConnect(ForgeDirection.SOUTH))
					Front.render(scale);
				
				if(wire.canConnect(ForgeDirection.DOWN))
					Bottom.render(scale);
				
				if(wire.canConnect(ForgeDirection.UP))
					Top.render(scale);
			}
			
			Middle.render(scale);
			
			if(wire.colour != -1) {
				int colour = ItemDye.field_150922_c[wire.colour];
				float r = (float)(colour >> 16 & 255) / 255.0F;
				float g = (float)(colour >> 8 & 255) / 255.0F;
				float b = (float)(colour & 255) / 255.0F;
				GL11.glColor3f(r, g, b);
				RenderUtils.bindTexture(PipeHelper.colours);
			
				if(wire.canConnect(ForgeDirection.WEST))
					Left.render(scale);
				
				if(wire.canConnect(ForgeDirection.EAST))
					Right.render(scale);
				
				if(wire.canConnect(ForgeDirection.NORTH))
					Back.render(scale);
				
				if(wire.canConnect(ForgeDirection.SOUTH))
					Front.render(scale);
				
				if(wire.canConnect(ForgeDirection.DOWN))
					Bottom.render(scale);
				
				if(wire.canConnect(ForgeDirection.UP))
					Top.render(scale);
				
				Middle.render(scale);
			}
		}
	}
	
	public void render(int metadata, float scale) {
		RenderUtils.bindTexture(EnumWireType.getWireTypeFrom(metadata).texture);
		Right.render(scale);
		Left.render(scale);
		Middle.render(scale);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}
