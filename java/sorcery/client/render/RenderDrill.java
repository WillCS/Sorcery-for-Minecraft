package sorcery.client.render;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import sorcery.handlers.ClientTickHandler;
import sorcery.items.ItemDrill;
import sorcery.lib.utils.Utils;
import sorcery.models.ModelCogwheel;
import sorcery.models.ModelCrystal;
import sorcery.models.ModelDrill;

public class RenderDrill implements IItemRenderer {
	private ModelDrill model;
	private ResourceLocation[] textures = {
			Utils.getResource("textures/entities/drill.png"),
			Utils.getResource("textures/entities/drillInfernite.png")
	};
	private ModelCogwheel cog;
	private ModelCrystal crystal;
	
	public RenderDrill() {
		this.model = new ModelDrill();
		this.cog = new ModelCogwheel();
		this.crystal = new ModelCrystal();
	}
	
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		if(type != ItemRenderType.FIRST_PERSON_MAP)
			return true;
		return false;
	}
	
	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return type == ItemRenderType.ENTITY && 
				(helper == ItemRendererHelper.ENTITY_BOBBING || helper == ItemRendererHelper.ENTITY_ROTATION);
	}
	
	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		GL11.glPushMatrix();
		int spin = type != ItemRenderType.INVENTORY ? ClientTickHandler.rotation * 2 : -1;
		int cogType = 0;
		int crystalType = 0;
		boolean infernite = false;
		if(item.hasTagCompound()) {
			if(((ItemDrill)item.getItem()).getMojo(item) == 0)
				spin = -1;
			
			if(item.stackTagCompound.getInteger("tier") >= 4)
				infernite = true;
			
			cogType = item.stackTagCompound.getInteger("cogType");
			crystalType = item.stackTagCompound.getInteger("crystalType");
		}
		
		if(type == ItemRenderType.ENTITY) {
			GL11.glTranslatef(-0.9F, 0.4F, 0.0F);
			GL11.glRotatef(-35.0F, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(3.0F, 0.0F, 1.0F, 0.0F);
		} else if(type == ItemRenderType.INVENTORY) {
			GL11.glTranslatef(-2.5F, 17.5F, 0.0F);
			GL11.glScalef(16.0F, 16.0F, 16.0F);
			GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(18F, 0.0F, 0.0F, 1.0F);
		}
		
		GL11.glTranslatef(0.7F, 0.2F, -0.01F);
		GL11.glRotatef(205.0F, 0.0F, 0.0F, 1.0F);
		GL11.glRotatef(275.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(10.0F, 1.0F, 0.0F, 0.0F);
		GL11.glScalef(1.3F, 1.3F, 1.3F);
		RenderUtils.bindTexture(this.textures[infernite ? 1 : 0]);
		
		switch(type) {
			case EQUIPPED_FIRST_PERSON:
				this.model.renderFirstPerson(item, spin);
				break;
			default:
				this.model.render(item, spin);
				break;
		}
		
		GL11.glPushMatrix();
		GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
		GL11.glScalef(0.05F, 0.1F, 0.05F);
		GL11.glTranslatef(0.0F, 1.3F, 0.0F);
		GL11.glRotatef(spin, 0.0F, 1.0F, 0.0F);
		this.cog.render(false, cogType);
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		GL11.glScalef(0.04F, 0.1F, 0.04F);
		GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
		GL11.glTranslatef(0.0F, -2.0F, 0.0F);
		GL11.glRotatef(22.5F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(spin, 0.0F, 1.0F, 0.0F);
		this.cog.render(false, cogType);
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		GL11.glScalef(0.8F, 0.8F, 0.8F);
		float height = 0.0F;
		if(spin != -1) {
			height = (360.0F - (float)((spin + 231) % 720)) / 2880.0F;
			if(height > 0)
				height *= -1.0F;
			height -= 0.3F;
			GL11.glTranslatef(0.0F, height, 0.0F);
			GL11.glRotatef(spin, 1.0F, 2.0F, 3.0F);
		} else {
			GL11.glTranslatef(0.0F, -0.25F, 0.0F);
			GL11.glRotatef(45.0F, 0.0F, 0.0F, 1.0F);
		}
		this.crystal.render(crystalType);
		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}	
}