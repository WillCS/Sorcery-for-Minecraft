package sorcery.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.item.ItemDye;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;

import org.lwjgl.opengl.GL11;

import sorcery.client.render.RenderUtils;
import sorcery.lib.NodeTransportHelper;
import sorcery.tileentities.nodes.TileEntityNodeBase;

public class ModelNodeBase extends ModelBase {
	ModelRenderer Support_2;
	ModelRenderer Support_1;
	ModelRenderer Base;
	ModelRenderer Support_4;
	ModelRenderer Support_3;
	ModelRenderer Support_5;
	ModelRenderer Support_6;
	
	// IO NODE
	ModelRenderer IO_Center_1;
	ModelRenderer IO_Center_2;
	ModelRenderer IO_Beam_1;
	ModelRenderer IO_Beam_2;
	ModelRenderer IO_Colour;
	
	// INPUT
	ModelRenderer I_Center_Crystal;
	
	// OUTPUT
	ModelRenderer O_Center_1;
	ModelRenderer O_Beam_1;
	ModelRenderer O_Beam_2;
	ModelRenderer O_Rim;
	
	// ADVANCED
	ModelRenderer Advanced;
	
	public ModelNodeBase() {
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
		
		Support_5 = new ModelRenderer(this, 9, 13).setTextureSize(64, 64);
		Support_5.addBox(-4F, -0.5F, -0.5F, 2, 1, 1);
		Support_5.setRotationPoint(0F, 0F, 0F);
		setRotation(Support_5, 0F, 0F, 0F);
		
		Support_6 = new ModelRenderer(this, 9, 13).setTextureSize(64, 64);
		Support_6.addBox(2F, -0.5F, -0.5F, 2, 1, 1);
		Support_6.setRotationPoint(0F, 0F, 0F);
		setRotation(Support_6, 0F, 0F, 0F);
		
		// IO
		IO_Center_1 = new ModelRenderer(this, 0, 0).setTextureSize(64, 64);
		IO_Center_1.addBox(-3F, -3F, -3F, 6, 6, 6);
		IO_Center_1.setRotationPoint(0F, 0F, 0F);
		setRotation(IO_Center_1, 0F, 0F, 0F);
		
		IO_Center_2 = new ModelRenderer(this, 7, 26).setTextureSize(64, 64);
		IO_Center_2.addBox(-1.5F, -1.5F, -4F, 3, 3, 1);
		IO_Center_2.setRotationPoint(0F, 0F, 0F);
		setRotation(IO_Center_2, 0F, 0F, 0F);
		
		IO_Beam_1 = new ModelRenderer(this, 16, 13).setTextureSize(64, 64);
		IO_Beam_1.addBox(-0.5F, -0.5F, -7.5F, 1, 1, 4);
		IO_Beam_1.setRotationPoint(0F, 0F, 0F);
		setRotation(IO_Beam_1, 0F, 0F, 0F);
		
		IO_Beam_2 = new ModelRenderer(this, 16, 26).setTextureSize(64, 64);
		IO_Beam_2.addBox(-1F, -1F, -9F, 2, 2, 2);
		IO_Beam_2.setRotationPoint(0F, 0F, 0F);
		setRotation(IO_Beam_2, 0F, 0F, 0F);
		
		IO_Colour = new ModelRenderer(this, 25, 0).setTextureSize(64, 64);
		IO_Colour.addBox(-3F, -3F, -3F, 6, 6, 6);
		IO_Colour.setRotationPoint(0F, 0F, 0F);
		setRotation(IO_Colour, 0F, 0F, 0F);
		
		// INPUT
		I_Center_Crystal = new ModelRenderer(this, 0, 0).setTextureSize(64, 64);
		I_Center_Crystal.addBox(-2F, -2F, -2F, 4, 4, 4);
		I_Center_Crystal.setRotationPoint(0F, 0F, 0F);
		setRotation(I_Center_Crystal, 0F, 0.7853982F, 0F);
		
		// OUTPUT
		O_Center_1 = new ModelRenderer(this, 7, 25).setTextureSize(64, 64);
		O_Center_1.addBox(-1.5F, -1.5F, -1.5F, 3, 3, 3);
		O_Center_1.setRotationPoint(0F, 0F, 0F);
		setRotation(O_Center_1, 0F, 0F, 0F);
		
		O_Beam_1 = new ModelRenderer(this, 16, 13).setTextureSize(64, 64);
		O_Beam_1.addBox(-0.5F, -0.5F, -5.5F, 1, 1, 4);
		O_Beam_1.setRotationPoint(0F, 0F, 0F);
		setRotation(O_Beam_1, 0F, 0F, 0F);
		
		O_Beam_2 = new ModelRenderer(this, 0, 8).setTextureSize(64, 64);
		O_Beam_2.addBox(-1F, -1F, -7F, 2, 2, 2);
		O_Beam_2.setRotationPoint(0F, 0F, 0F);
		setRotation(O_Beam_2, 0F, 0F, 0F);
		
		O_Rim = new ModelRenderer(this, 0, 0).setTextureSize(64, 64);
		O_Rim.addBox(-2.5F, -2.5F, -0.5F, 5, 5, 1);
		O_Rim.setRotationPoint(0F, 0F, 0F);
		setRotation(O_Rim, 0F, 0F, 0F);
		
		// ADVANCED
		Advanced = new ModelRenderer(this, 0, 40).setTextureSize(64, 64);
		Advanced.addBox(-2F, -2F, 2.5F, 4, 4, 1);
		Advanced.setRotationPoint(0F, 0F, 0F);
		setRotation(Advanced, 0F, 0F, 0F);
	}
	
	public void render(TileEntityNodeBase node, float scale) {
		int metadata = node.getBlockMetadata();
		
		GL11.glPushMatrix();
		RenderUtils.bindTexture(NodeTransportHelper.getTextureMapFromMeta(node.getBlockMetadata()));
		setRotationAngles(node, scale);
		Support_2.render(scale);
		Support_1.render(scale);
		Base.render(scale);
		Support_4.render(scale);
		Support_3.render(scale);
		Support_5.render(scale);
		Support_6.render(scale);
		if(metadata == 0) {
			O_Center_1.render(scale);
			O_Beam_1.render(scale);
			O_Beam_2.render(scale);
			O_Rim.render(scale);
		} else if(metadata == 2 || metadata == 4) {
			
		} else {
			IO_Center_1.render(scale);
			IO_Center_2.render(scale);
			IO_Beam_1.render(scale);
		}
		
		if(node.colourTarget != -1) {
			int colour = ItemDye.field_150922_c[node.colourTarget];
			float r = (float)(colour >> 16 & 255) / 255.0F;
			float g = (float)(colour >> 8 & 255) / 255.0F;
			float b = (float)(colour & 255) / 255.0F;
			GL11.glColor3f(r, g, b);
		}
		
		if(metadata == 0)
			O_Beam_2.render(scale);
		else if(metadata == 2 || metadata == 4) {
			
		} else
			IO_Beam_2.render(scale);
		GL11.glColor3f(1.0F, 1.0F, 1.0F);
		
		if(node.colourCode != -1) {
			if(node.useAdvancedModel)
				RenderUtils.bindTexture(NodeTransportHelper.getColouredTextureMapFromMeta(1));
			else
				RenderUtils.bindTexture(NodeTransportHelper.getColouredTextureMapFromMeta(node.getBlockMetadata()));
			int colour = ItemDye.field_150922_c[node.colourCode];
			float r = (float)(colour >> 16 & 255) / 255.0F;
			float g = (float)(colour >> 8 & 255) / 255.0F;
			float b = (float)(colour & 255) / 255.0F;
			GL11.glColor3f(r, g, b);
		}
		
		if(metadata == 0) {
			
		} else if(metadata == 2 || metadata == 4)
			I_Center_Crystal.render(scale);
		else
			IO_Colour.render(scale);
		
		GL11.glColor3f(1.0F, 1.0F, 1.0F);
		
		if(node.useAdvancedModel) {
			RenderUtils.bindTexture(NodeTransportHelper.getTextureMapFromMeta(metadata));
			
			Advanced.render(scale);
		}
		
		GL11.glPopMatrix();
	}
	
	public void render(int meta, float scale) {
		GL11.glPushMatrix();
		RenderUtils.bindTexture(NodeTransportHelper.getTextureMapFromMeta(meta));
		setRotationAngles(null, scale);
		Support_2.render(scale);
		Support_1.render(scale);
		Base.render(scale);
		Support_4.render(scale);
		Support_3.render(scale);
		Support_5.render(scale);
		Support_6.render(scale);
		if(meta == 0) {
			O_Center_1.render(scale);
			O_Beam_1.render(scale);
			O_Beam_2.render(scale);
			O_Rim.render(scale);
			O_Beam_2.render(scale);
		} else if(meta == 2 || meta == 4)
			I_Center_Crystal.render(scale);
		else {
			IO_Center_1.render(scale);
			IO_Center_2.render(scale);
			IO_Beam_1.render(scale);
			IO_Beam_2.render(scale);
			IO_Colour.render(scale);
		}
		
		if(meta != 0 && meta != 1 && meta != 2 && meta != 4) {
			Advanced.render(scale);
		}
		
		GL11.glPopMatrix();
	}
	
	public void renderComponents(int meta, float scale, ItemRenderType type) {
		GL11.glPushMatrix();
		setRotationAngles(null, scale);
		GL11.glTranslatef(0.0F, 0.0F, 0.0F);
		
		GL11.glRotatef(-90, 0F, 1F, 0F);
		
		if(type != ItemRenderType.ENTITY)
			GL11.glTranslatef(0F, 0.3F, -0.3F);
		switch(meta) {
			case 0:
				RenderUtils.bindTexture(NodeTransportHelper.getTextureMapFromMeta(1));
				Support_2.render(scale);
				Support_1.render(scale);
				Base.render(scale);
				Support_4.render(scale);
				Support_3.render(scale);
				Support_5.render(scale);
				Support_6.render(scale);
				break;
			case 1:
				RenderUtils.bindTexture(NodeTransportHelper.getTextureMapFromMeta(1));
				IO_Center_1.render(scale);
				IO_Center_2.render(scale);
				IO_Beam_1.render(scale);
				IO_Beam_2.render(scale);
				IO_Colour.render(scale);
				break;
			case 2:
				RenderUtils.bindTexture(NodeTransportHelper.getTextureMapFromMeta(0));
				O_Center_1.render(scale);
				O_Beam_1.render(scale);
				O_Beam_2.render(scale);
				O_Rim.render(scale);
				O_Beam_2.render(scale);
				break;
		}
		
		/*
		 * if(meta != 0 && meta != 1 && meta != 2 && meta != 4) {
		 * Advanced.render(scale); }
		 */
		
		GL11.glPopMatrix();
	}
	
	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
	
	public void setRotationAngles(TileEntityNodeBase node, float scale) {
		if(node != null) {
			if(node.getConnectedSide() != 2 || node.getConnectedSide() != 3)
				this.rotateStand(node.standRotation);
			
			this.setFace(node.getConnectedSide());
			this.rotateNode(node.nodeRotation + this.getZRotationFromFace(node.getConnectedSide()));
			
			if(node.getConnectedSide() == 2 || node.getConnectedSide() == 3)
				this.rotateStandSpecial(node);
			
			if(!node.isOutput && node.isInput) {
				this.I_Center_Crystal.rotateAngleY = (float)((float)(node.delay) / (Math.PI * 2));
			}
		} else {
			this.rotateStand(RenderUtils.rightAngle);
			this.setFace(1);
		}
		
		/*
		 * if(node != null && node.isSending && node.delay >= 40) {
		 * switch(node.getSideNodeIsOn
		 * ((TileEntityNodeBase)node.nextNodeToSendTo.
		 * getTileEntityAtPos(node.worldObj))) { case 0: case 1: //IO
		 * IO_Beam_1.rotateAngleY += (float)((node.delay - 40) * ((Math.PI * 2)
		 * / 20)); IO_Beam_2.rotateAngleY += (float)((node.delay - 40) *
		 * ((Math.PI * 2) / 20)); //OUTPUT O_Beam_1.rotateAngleY +=
		 * (float)((node.delay - 40) * ((Math.PI * 2) / 20));
		 * O_Beam_2.rotateAngleY += (float)((node.delay - 40) * ((Math.PI * 2) /
		 * 20)); break; case 2: case 3: //IO IO_Beam_1.rotateAngleZ +=
		 * (float)((node.delay - 40) * ((Math.PI * 2) / 20));
		 * IO_Beam_2.rotateAngleZ += (float)((node.delay - 40) * ((Math.PI * 2)
		 * / 20)); //OUTPUT O_Beam_1.rotateAngleZ += (float)((node.delay - 40) *
		 * ((Math.PI * 2) / 20)); O_Beam_2.rotateAngleZ += (float)((node.delay -
		 * 40) * ((Math.PI * 2) / 20)); break; case 4: case 5: //IO
		 * IO_Beam_1.rotateAngleZ + (float)((node.delay - 40) * ((Math.PI * 2) /
		 * 20)); IO_Beam_2.rotateAngleZ += (float)((node.delay - 40) * ((Math.PI
		 * * 2) / 20)); //OUTPUT O_Beam_1.rotateAngleZ += (float)((node.delay -
		 * 40) * ((Math.PI * 2) / 20)); O_Beam_2.rotateAngleZ +=
		 * (float)((node.delay - 40) * ((Math.PI * 2) / 20)); break; } }
		 */
	}
	
	public void rotateStand(float rotation) {
		this.Support_1.rotateAngleY = rotation;
		this.Support_2.rotateAngleY = rotation;
		this.Support_3.rotateAngleY = rotation;
		this.Support_4.rotateAngleY = rotation;
		this.Support_5.rotateAngleY = rotation;
		this.Support_6.rotateAngleY = rotation;
		
		// IO
		this.IO_Center_1.rotateAngleY = rotation;
		this.IO_Center_2.rotateAngleY = rotation;
		this.IO_Beam_1.rotateAngleY = rotation;
		this.IO_Beam_2.rotateAngleY = rotation;
		this.IO_Colour.rotateAngleY = rotation;
		
		// INPUT
		this.I_Center_Crystal.rotateAngleY = rotation;
		
		// OUTPUT
		this.O_Center_1.rotateAngleY = rotation;
		this.O_Beam_1.rotateAngleY = rotation;
		this.O_Beam_2.rotateAngleY = rotation;
		this.O_Rim.rotateAngleY = rotation;
		
		// ADVANCED
		this.Advanced.rotateAngleY = rotation;
	}
	
	public void rotateStandSpecial(TileEntityNodeBase node) {
		int side = node.getConnectedSide();
		int sideToNode = node.getSideNodeIsOn(node);
		float rotation = node.standRotation;
		float yRotation = 0F;
		
		if(side == 4) {
			switch(sideToNode) {
				case 0:
					yRotation = -(RenderUtils.rightAngle * 1);
				case 1:
					yRotation = 0F;
				case 2:
					yRotation = RenderUtils.rightAngle * 2;
				case 3:
					yRotation = RenderUtils.rightAngle * 1;
				case 4:
				case 5:
					yRotation = 0F;
			}
		}
		if(side == 5) {
			switch(sideToNode) {
				case 0:
					yRotation = RenderUtils.rightAngle * 1;
				case 1:
					yRotation = -(RenderUtils.rightAngle * 1);
				case 2:
					yRotation = 0F;
				case 3:
					yRotation = RenderUtils.rightAngle * 2;
				case 4:
				case 5:
					yRotation = 0F;
			}
		}
		
		this.Support_1.rotateAngleY = yRotation;
		this.Support_2.rotateAngleY = yRotation;
		this.Support_3.rotateAngleY = yRotation;
		this.Support_4.rotateAngleY = yRotation;
		this.Support_5.rotateAngleY = yRotation;
		this.Support_6.rotateAngleY = yRotation;
		
		// IO
		this.IO_Center_1.rotateAngleY = yRotation;
		this.IO_Center_2.rotateAngleY = yRotation;
		this.IO_Beam_1.rotateAngleY = yRotation;
		this.IO_Beam_2.rotateAngleY = yRotation;
		this.IO_Colour.rotateAngleY = yRotation;
		
		// INPUT
		this.I_Center_Crystal.rotateAngleY = yRotation;
		
		// OUTPUT
		this.O_Center_1.rotateAngleY = yRotation;
		this.O_Beam_1.rotateAngleY = yRotation;
		this.O_Beam_2.rotateAngleY = yRotation;
		this.O_Rim.rotateAngleY = yRotation;
		
		// ADVANCED
		this.Advanced.rotateAngleY = yRotation;
		
		this.Support_1.rotateAngleZ = rotation;
		this.Support_2.rotateAngleZ = rotation;
		this.Support_3.rotateAngleZ = rotation;
		this.Support_4.rotateAngleZ = rotation;
		this.Support_5.rotateAngleZ = rotation;
		this.Support_6.rotateAngleZ = rotation;
		
		// IO
		this.IO_Center_1.rotateAngleZ = rotation;
		this.IO_Center_2.rotateAngleZ = rotation;
		this.IO_Beam_1.rotateAngleZ = rotation;
		this.IO_Beam_2.rotateAngleZ = rotation;
		this.IO_Colour.rotateAngleZ = rotation;
		
		// INPUT
		this.I_Center_Crystal.rotateAngleZ = rotation;
		
		// OUTPUT
		this.O_Center_1.rotateAngleZ = rotation;
		this.O_Beam_1.rotateAngleZ = rotation;
		this.O_Beam_2.rotateAngleZ = rotation;
		this.O_Rim.rotateAngleZ = rotation;
		
		// ADVANCED
		this.Advanced.rotateAngleZ = rotation;
	}
	
	public void rotateNode(float rotation) {
		// IO
		this.IO_Center_1.rotateAngleX = rotation;
		this.IO_Center_2.rotateAngleX = rotation;
		this.IO_Beam_1.rotateAngleX = rotation;
		this.IO_Beam_2.rotateAngleX = rotation;
		this.IO_Colour.rotateAngleX = rotation;
		
		// INPUT
		this.I_Center_Crystal.rotateAngleX = rotation;
		
		// OUTPUT
		this.O_Center_1.rotateAngleX = rotation;
		this.O_Beam_1.rotateAngleX = rotation;
		this.O_Beam_2.rotateAngleX = rotation;
		this.O_Rim.rotateAngleX = rotation;
		
		// ADVANCED
		this.Advanced.rotateAngleX = rotation;
	}
	
	public float getZRotationFromFace(int face) {
		switch(face) {
			case 2:
				return -RenderUtils.rightAngle;
			case 3:
				return RenderUtils.rightAngle;
		}
		
		return 0F;
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
		this.Support_5.rotateAngleZ = zRotation;
		this.Support_6.rotateAngleZ = zRotation;
		this.Base.rotateAngleZ = zRotation;
		
		// IO
		this.IO_Center_1.rotateAngleZ = zRotation;
		this.IO_Center_2.rotateAngleZ = zRotation;
		this.IO_Beam_1.rotateAngleZ = zRotation;
		this.IO_Beam_2.rotateAngleZ = zRotation;
		this.IO_Colour.rotateAngleZ = zRotation;
		
		// INPUT
		this.I_Center_Crystal.rotateAngleZ = zRotation;
		
		// OUTPUT
		this.O_Center_1.rotateAngleZ = zRotation;
		this.O_Beam_1.rotateAngleZ = zRotation;
		this.O_Beam_2.rotateAngleZ = zRotation;
		this.O_Rim.rotateAngleZ = zRotation;
		
		// ADVANCED
		this.Advanced.rotateAngleZ = zRotation;
		
		this.Support_1.rotateAngleX = xRotation;
		this.Support_2.rotateAngleX = xRotation;
		this.Support_3.rotateAngleX = xRotation;
		this.Support_4.rotateAngleX = xRotation;
		this.Support_5.rotateAngleX = xRotation;
		this.Support_6.rotateAngleX = xRotation;
		this.Base.rotateAngleX = xRotation;
		
		// IO
		this.IO_Center_1.rotateAngleX = xRotation;
		this.IO_Center_2.rotateAngleX = xRotation;
		this.IO_Beam_1.rotateAngleX = xRotation;
		this.IO_Beam_2.rotateAngleX = xRotation;
		this.IO_Colour.rotateAngleX = xRotation;
		
		// INPUT
		this.I_Center_Crystal.rotateAngleX = xRotation;
		
		// OUTPUT
		this.O_Center_1.rotateAngleX = xRotation;
		this.O_Beam_1.rotateAngleX = xRotation;
		this.O_Beam_2.rotateAngleX = xRotation;
		this.O_Rim.rotateAngleX = xRotation;
		
		// ADVANCED
		this.Advanced.rotateAngleX = xRotation;
	}
}
