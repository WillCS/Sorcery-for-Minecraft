package sorcery.models;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import sorcery.client.render.RenderUtils;
import sorcery.entities.EntityLantern;
import sorcery.lib.utils.Utils;
import sorcery.tileentities.TileEntityLantern;
import cpw.mods.fml.client.FMLClientHandler;

public class ModelLantern extends ModelBase {
	
	ModelRenderer top;
	ModelRenderer base;
	ModelRenderer topConnector;
	ModelRenderer container;
    ModelRenderer containerBottom;
    ModelRenderer containerTop;
    ModelRenderer containerSide1;
    ModelRenderer containerSide2;
    ModelRenderer containerSide3;
    ModelRenderer containerSide4;
	ModelRenderer fireHolder;
	ModelRenderer sideCage1;
	ModelRenderer topCage2;
	ModelRenderer bottomCage2;
	ModelRenderer bottomCage1;
	ModelRenderer topCage1;
	ModelRenderer sideCage2;
	ModelRenderer sideCage3;
	ModelRenderer sideCage4;
	ModelRenderer handle1;
	ModelRenderer handle2;
	ModelRenderer handle3;
	ModelRenderer sideHolder1;
	ModelRenderer sideHolder2;
	ModelRenderer sideHolder3;
	ModelRenderer topHolder2;
	ModelRenderer topHolder1;
	ModelRenderer topHolder5;
	ModelRenderer topHolder4;
	ModelRenderer topHolder3;
	ModelRenderer topHolder6;
	ModelRenderer baseConnector;
	
	ModelRenderer playerArm;
	
	ResourceLocation texture = Utils.getResource("textures/entities/lantern.png");

	public ModelLantern() {
		top = new ModelRenderer(this, 0, 6).setTextureSize(64, 32);
		top.addBox(-2F, 0F, -2F, 4, 1, 4);
		top.setRotationPoint(0F, -7F, 0F);
		
		base = new ModelRenderer(this, 0, 0).setTextureSize(64, 32);
		base.addBox(-2.5F, 7F, -2.5F, 5, 1, 5);
		base.setRotationPoint(0F, -7F, 0F);
		base.setTextureSize(64, 32);
		
		topConnector = new ModelRenderer(this, 0, 21).setTextureSize(64, 32);
		topConnector.addBox(-1F, 1F, -1F, 2, 1, 2);
		topConnector.setRotationPoint(0F, -7F, 0F);

		container = new ModelRenderer(this, 0, 24).setTextureSize(64, 32);
		container.addBox(-2F, 2F, -2F, 4, 4, 4);
		container.setRotationPoint(0F, -7F, 0F);
		
		containerBottom = new ModelRenderer(this, 0, 28).setTextureSize(64, 32);
		containerBottom.addBox(-2F, 6F, -2F, 4, 0, 4);
		containerBottom.setRotationPoint(0F, -7F, 0F);
		
		containerTop = new ModelRenderer(this, 0, 28).setTextureSize(64, 32);
		containerTop.addBox(-2F, 2F, -2F, 4, 0, 4);
		containerTop.setRotationPoint(0F, -7F, 0F);
		
		containerSide1 = new ModelRenderer(this, 0, 24).setTextureSize(64, 32);
		containerSide1.addBox(-2F, 2F, -2F, 0, 4, 4);
		containerSide1.setRotationPoint(0F, -7F, 0F);

		containerSide2 = new ModelRenderer(this, 0, 24).setTextureSize(64, 32);
		containerSide2.addBox(-2F, 2F, -2F, 0, 4, 4);
		containerSide2.setRotationPoint(0F, -7F, 0F);
		setRotation(containerSide2, 0F, 1.570796F, 0F);
		
		containerSide3 = new ModelRenderer(this, 0, 24).setTextureSize(64, 32);
		containerSide3.addBox(-2F, 2F, -2F, 0, 4, 4);
		containerSide3.setRotationPoint(0F, -7F, 0F);
		setRotation(containerSide3, 0F, 3.141593F, 0F);
		
		containerSide4 = new ModelRenderer(this, 0, 24).setTextureSize(64, 32);
		containerSide4.addBox(-2F, 2F, -2F, 0, 4, 4);
		containerSide4.setRotationPoint(0F, -7F, 0F);
		setRotation(containerSide4, 0F, -1.570796F, 0F);

		fireHolder = new ModelRenderer(this, 8, 22).setTextureSize(64, 32);
		fireHolder.addBox(-0.5F, 5F, -0.5F, 1, 1, 1);
		fireHolder.setRotationPoint(0F, -7F, 0F);

		sideCage1 = new ModelRenderer(this, 0, 13).setTextureSize(64, 32);
		sideCage1.addBox(-3F, 1.5F, -0.5F, 1, 5, 1);
		sideCage1.setRotationPoint(0F, -7F, 0F);
		setRotation(sideCage1, 0F, -0.7853982F, 0F);
		
		topCage2 = new ModelRenderer(this, 0, 19).setTextureSize(64, 32);
		topCage2.addBox(-3F, 1.5F, -0.5F, 6, 1, 1);
		topCage2.setRotationPoint(0F, -7F, 0F);
		setRotation(topCage2, 0F, -0.7853982F, 0F);
		
		bottomCage2 = new ModelRenderer(this, 0, 19).setTextureSize(64, 32);
		bottomCage2.addBox(-3F, 5.5F, -0.5F, 6, 1, 1);
		bottomCage2.setRotationPoint(0F, -7F, 0F);
		setRotation(bottomCage2, 0F, -0.7853982F, 0F);
		
		bottomCage1 = new ModelRenderer(this, 0, 19).setTextureSize(64, 32);
		bottomCage1.addBox(-3F, 5.5F, -0.5F, 6, 1, 1);
		bottomCage1.setRotationPoint(0F, -7F, 0F);
		setRotation(bottomCage1, 0F, 0.7853982F, 0F);
		
		topCage1 = new ModelRenderer(this, 0, 19).setTextureSize(64, 32);
		topCage1.addBox(-3F, 1.5F, -0.5F, 6, 1, 1);
		topCage1.setRotationPoint(0F, -7F, 0F);
		setRotation(topCage1, 0F, 0.7853982F, 0F);
		
		sideCage2 = new ModelRenderer(this, 0, 13).setTextureSize(64, 32);
		sideCage2.addBox(-3F, 1.5F, -0.5F, 1, 5, 1);
		sideCage2.setRotationPoint(0F, -7F, 0F);
		setRotation(sideCage2, 0F, 0.7853982F, 0F);
		
		sideCage3 = new ModelRenderer(this, 0, 13).setTextureSize(64, 32);
		sideCage3.addBox(2F, 1.5F, -0.5F, 1, 5, 1);
		sideCage3.setRotationPoint(0F, -7F, 0F);
		setRotation(sideCage3, 0F, -0.7853982F, 0F);
		
		sideCage4 = new ModelRenderer(this, 0, 13).setTextureSize(64, 32);
		sideCage4.addBox(2F, 1.5F, -0.5F, 1, 5, 1);
		sideCage4.setRotationPoint(0F, -7F, 0F);
		setRotation(sideCage4, 0F, 0.7853982F, 0F);
		
		handle1 = new ModelRenderer(this, 16, 29).setTextureSize(64, 32);
		handle1.addBox(1F, -2F, -0.5F, 1, 2, 1);
		handle1.setRotationPoint(0F, -7F, 0F);
		
		handle2 = new ModelRenderer(this, 16, 29).setTextureSize(64, 32);
		handle2.addBox(-2F, -2F, -0.5F, 1, 2, 1);
		handle2.setRotationPoint(0F, -7F, 0F);

		handle3 = new ModelRenderer(this, 16, 27).setTextureSize(64, 32);
		handle3.addBox(-2F, -3F, -0.5F, 4, 1, 1);
		handle3.setRotationPoint(0F, -7F, 0F);

		sideHolder1 = new ModelRenderer(this, 21, 4).setTextureSize(64, 32);
		sideHolder1.addBox(-0.5F, -2F, -2F, 1, 1, 6);
		sideHolder1.setRotationPoint(0F, -7F, 0F);

		sideHolder2 = new ModelRenderer(this, 21, 0).setTextureSize(64, 32);
		sideHolder2.addBox(-0.5F, -3F, -2F, 1, 1, 1);
		sideHolder2.setRotationPoint(0F, -7F, 0F);

		sideHolder3 = new ModelRenderer(this, 27, 0).setTextureSize(64, 32);
		sideHolder3.addBox(-1.5F, -3F, 4F, 3, 3, 1);
		sideHolder3.setRotationPoint(0F, -7F, 0F);

		topHolder2 = new ModelRenderer(this, 36, 0).setTextureSize(64, 32);
		topHolder2.addBox(-0.5F, -2F, -2F, 1, 1, 4);
		topHolder2.setRotationPoint(0F, -7F, 0F);

		topHolder1 = new ModelRenderer(this, 46, 0).setTextureSize(64, 32);
		topHolder1.addBox(-0.5F, -3F, -2F, 1, 1, 1);
		topHolder1.setRotationPoint(0F, -7F, 0F);

		topHolder5 = new ModelRenderer(this, 36, 5).setTextureSize(64, 32);
		topHolder5.addBox(-0.5F, -7F, -0.5F, 1, 3, 1);
		topHolder5.setRotationPoint(0F, -7F, 0F);

		topHolder4 = new ModelRenderer(this, 40, 5).setTextureSize(64, 32);
		topHolder4.addBox(-0.5F, -2F, 0F, 1, 1, 2);
		topHolder4.setRotationPoint(0F, -10F, 0F);

		topHolder3 = new ModelRenderer(this, 46, 2).setTextureSize(64, 32);
		topHolder3.addBox(-0.5F, -4F, 1F, 1, 2, 1);
		topHolder3.setRotationPoint(0F, -7F, 0F);

		topHolder6 = new ModelRenderer(this, 36, 9).setTextureSize(64, 32);
		topHolder6.addBox(-1.5F, -7F, -1.5F, 3, 1, 3);
		topHolder6.setRotationPoint(0F, -7F, 0F);
		
		baseConnector = new ModelRenderer(this, 0, 21).setTextureSize(64, 32);
		baseConnector.addBox(-1F, 6F, -1F, 2, 1, 2);
		baseConnector.setRotationPoint(0F, -7F, 0F);
		
		playerArm = new ModelRenderer(this, 40, 16).setTextureSize(64, 32);
        playerArm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4);
        playerArm.setRotationPoint(0F, 0F, 0F);
	}

	public void renderItemInHand(ItemStack item) {
		RenderUtils.bindTexture(this.texture);
		GL11.glScalef(0.10F, 0.10F, 0.10F);
		GL11.glRotatef(130, 1.0F, 0F, 0F);
		GL11.glRotatef(-20, 0.0F, 0.0F, 1.0F);
		GL11.glTranslatef(4.5F, 11.0F, 0.0F);
		
		top.render(1.0F);
		base.render(1.0F);
		topConnector.render(1.0F);
		baseConnector.render(1.0F);
		fireHolder.render(1.0F);
		
		containerTop.render(1.0F);
		containerBottom.render(1.0F);
		containerSide1.render(1.0F);
		containerSide2.render(1.0F);
		containerSide3.render(1.0F);
		containerSide4.render(1.0F);

		bottomCage1.render(1.0F);
		bottomCage2.render(1.0F);
		
		topCage1.render(1.0F);
		topCage2.render(1.0F);
		
		sideCage1.render(1.0F);
		sideCage2.render(1.0F);
		sideCage3.render(1.0F);
		sideCage4.render(1.0F);
		
		handle1.render(1.0F);
		handle2.render(1.0F);
		handle3.render(1.0F);
	}
	
	public void renderItemFirstPerson(ItemStack item) {
		GL11.glPushMatrix();
		ResourceLocation skin = ((AbstractClientPlayer)FMLClientHandler.instance().getClient().thePlayer).getLocationSkin();
		RenderUtils.bindTexture(skin);
		GL11.glTranslatef(5.0F, 4.0F, 10.0F);
		GL11.glRotatef(90, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(50, 1.0F, 0.0F, 0.0F);
		GL11.glRotatef(-10, 0.0F, 0.0F, 1.0F);
		playerArm.render(1.0F);
		
		RenderUtils.bindTexture(this.texture);
		GL11.glTranslatef(-1.0F, 6.0F, 10.0F);
		GL11.glRotatef(100, 1.0F, 0.0F, 0.0F);
		//GL11.glRotatef(90, 0.0F, 1.0F, 0.0F);
		top.render(1.0F);
		base.render(1.0F);
		topConnector.render(1.0F);
		baseConnector.render(1.0F);
		fireHolder.render(1.0F);

		containerTop.render(1.0F);
		containerBottom.render(1.0F);
		containerSide1.render(1.0F);
		containerSide2.render(1.0F);
		containerSide3.render(1.0F);
		containerSide4.render(1.0F);

		bottomCage1.render(1.0F);
		bottomCage2.render(1.0F);
		
		topCage1.render(1.0F);
		topCage2.render(1.0F);
		
		sideCage1.render(1.0F);
		sideCage2.render(1.0F);
		sideCage3.render(1.0F);
		sideCage4.render(1.0F);
		
		handle1.render(1.0F);
		handle2.render(1.0F);
		handle3.render(1.0F);
		GL11.glPopMatrix();
		
	}
	
	public void renderItemInventory(ItemStack item) {
		RenderUtils.bindTexture(this.texture);
		
		GL11.glTranslatef(8F, 11F, 0F);
		GL11.glScalef(1.2F, 1.2F, 1.2F);
		GL11.glRotatef(45, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-45, 1.0F, 0.0F, 1.0F);
		
		top.render(1.0F);
		base.render(1.0F);
		topConnector.render(1.0F);
		baseConnector.render(1.0F);
		fireHolder.render(1.0F);

		containerTop.render(1.0F);
		containerBottom.render(1.0F);
		containerSide1.render(1.0F);
		containerSide2.render(1.0F);
		containerSide3.render(1.0F);
		containerSide4.render(1.0F);

		bottomCage1.render(1.0F);
		bottomCage2.render(1.0F);
		
		topCage1.render(1.0F);
		topCage2.render(1.0F);
		
		sideCage1.render(1.0F);
		sideCage2.render(1.0F);
		sideCage3.render(1.0F);
		sideCage4.render(1.0F);
		
		handle1.render(1.0F);
		handle2.render(1.0F);
		handle3.render(1.0F);
	}
	
	public void renderEntity(EntityLantern entity) {
		RenderUtils.bindTexture(this.texture);
		
		GL11.glRotatef(180, 1.0F, 0.0F, 0.0F);
		GL11.glScalef(0.05F, 0.05F, 0.05F);
		//GL11.glRotatef(130, 1.0F, 0F, 0F);
		//GL11.glRotatef(-20, 0.0F, 0.0F, 1.0F);
		//GL11.glTranslatef(3.0F, 2.0F, 0.0F);
		GL11.glTranslatef(0.0F, -1.0F, 0.0F);
		
		top.render(1.0F);
		base.render(1.0F);
		topConnector.render(1.0F);
		baseConnector.render(1.0F);
		fireHolder.render(1.0F);
		
		if(!Minecraft.getMinecraft().isGamePaused()) {
			World world = FMLClientHandler.instance().getClient().theWorld;
			if(world.rand.nextInt(20) == 0 && entity.hasFuel()) {
				 //world.spawnParticle("smoke", entity.posX, entity.posY + 0.6, entity.posZ, 0.0D, 0.0D, 0.0D);
		         world.spawnParticle("flame", entity.posX, entity.posY + 0.25, entity.posZ, 0.0D, 0.0D, 0.0D);
			}
		}

		containerTop.render(1.0F);
		containerBottom.render(1.0F);
		containerSide1.render(1.0F);
		containerSide2.render(1.0F);
		containerSide3.render(1.0F);
		containerSide4.render(1.0F);

		bottomCage1.render(1.0F);
		bottomCage2.render(1.0F);
		
		topCage1.render(1.0F);
		topCage2.render(1.0F);
		
		sideCage1.render(1.0F);
		sideCage2.render(1.0F);
		sideCage3.render(1.0F);
		sideCage4.render(1.0F);
		
		handle1.render(1.0F);
		handle2.render(1.0F);
		handle3.render(1.0F);
	}
	
	public void renderItemEntity(EntityItem entity) {
		RenderUtils.bindTexture(this.texture);
		
		GL11.glRotatef(180, 1.0F, 0.0F, 0.0F);
		GL11.glScalef(0.15F, 0.15F, 0.15F);
		//GL11.glRotatef(130, 1.0F, 0F, 0F);
		//GL11.glRotatef(-20, 0.0F, 0.0F, 1.0F);
		//GL11.glTranslatef(3.0F, 2.0F, 0.0F);
		
		top.render(1.0F);
		base.render(1.0F);
		topConnector.render(1.0F);
		baseConnector.render(1.0F);
		fireHolder.render(1.0F);
		if(!Minecraft.getMinecraft().isGamePaused()) {
			World world = FMLClientHandler.instance().getClient().theWorld;
			if(world.rand.nextInt(20) == 0) {
				 //world.spawnParticle("smoke", entity.posX, entity.posY + 0.6, entity.posZ, 0.0D, 0.0D, 0.0D);
		         world.spawnParticle("flame", entity.posX, entity.posY + 0.4, entity.posZ, 0.0D, 0.0D, 0.0D);
			}
		}

		containerTop.render(1.0F);
		containerBottom.render(1.0F);
		containerSide1.render(1.0F);
		containerSide2.render(1.0F);
		containerSide3.render(1.0F);
		containerSide4.render(1.0F);

		bottomCage1.render(1.0F);
		bottomCage2.render(1.0F);
		
		topCage1.render(1.0F);
		topCage2.render(1.0F);
		
		sideCage1.render(1.0F);
		sideCage2.render(1.0F);
		sideCage3.render(1.0F);
		sideCage4.render(1.0F);
		
		handle1.render(1.0F);
		handle2.render(1.0F);
		handle3.render(1.0F);
	}
	
	public void renderTileEntity(TileEntityLantern tile) {
		RenderUtils.bindTexture(this.texture);
		
		switch(tile.front) {
			case 0:
				GL11.glTranslatef(0.0F, -0.9F, 0.0F);
				break;
			case 1:
				GL11.glTranslatef(0.0F, -0.68F, 0.0F);
				break;
			case 2:
				GL11.glRotatef(0, 0.0F, 10F, 0.0F);
				GL11.glTranslatef(0.0F, -0.5F, -0.4F);
				break;
			case 3:
				GL11.glRotatef(180, 0.0F, 10F, 0.0F);
				GL11.glTranslatef(0.0F, -0.5F, -0.4F);
				break;
			case 4:
				GL11.glRotatef(90, 0.0F, 10F, 0.0F);
				GL11.glTranslatef(0.0F, -0.5F, -0.4F);
				break;
			case 5:
				GL11.glRotatef(-90, 0.0F, 10F, 0.0F);
				GL11.glTranslatef(0.0F, -0.5F, -0.4F);
				break;
		}
		
		GL11.glRotatef(180, 1.0F, 0.0F, 0.0F);
		GL11.glScalef(0.12F, 0.12F, 0.12F);
		
		top.render(1.0F);
		base.render(1.0F);
		topConnector.render(1.0F);
		baseConnector.render(1.0F);
		fireHolder.render(1.0F);

		containerTop.render(1.0F);
		containerBottom.render(1.0F);
		containerSide1.render(1.0F);
		containerSide2.render(1.0F);
		containerSide3.render(1.0F);
		containerSide4.render(1.0F);

		bottomCage1.render(1.0F);
		bottomCage2.render(1.0F);
		
		topCage1.render(1.0F);
		topCage2.render(1.0F);
		
		sideCage1.render(1.0F);
		sideCage2.render(1.0F);
		sideCage3.render(1.0F);
		sideCage4.render(1.0F);
		
		handle1.render(1.0F);
		handle2.render(1.0F);
		handle3.render(1.0F);
		
		if(tile.front == 1) {
			topHolder1.render(1.0F);
			topHolder2.render(1.0F);
			topHolder3.render(1.0F);
			topHolder4.render(1.0F);
			topHolder5.render(1.0F);
			topHolder6.render(1.0F);
		} else if(tile.front != 0) {
			sideHolder1.render(1.0F);
			sideHolder2.render(1.0F);
			sideHolder3.render(1.0F);
		}
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}
