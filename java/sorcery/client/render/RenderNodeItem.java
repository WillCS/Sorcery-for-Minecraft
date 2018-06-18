package sorcery.client.render;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemDye;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import sorcery.client.render.geometry.Circle;
import sorcery.entities.EntityNodeItem;
import sorcery.lib.utils.Utils;
import cpw.mods.fml.client.FMLClientHandler;

public class RenderNodeItem extends Render {
	private Circle circle;
	private Circle circle2;
	private Circle circle3;
	private Circle circle4;
	
	private RenderItem itemRender;
	
	public RenderNodeItem() {
		this.circle = new Circle();
		this.circle2 = new Circle();
		this.circle3 = new Circle();
		this.circle4 = new Circle();
		this.itemRender = new RenderItem() {
			@Override
			public boolean shouldBob() {
				return false;
			}
			
			@Override
			public boolean shouldSpreadItems() {
				return false;
			}
		};
		this.itemRender.setRenderManager(RenderManager.instance);
	}
	
	@Override
	public void doRender(Entity entity, double d0, double d1, double d2, float f, float f1) {
		if(entity instanceof EntityNodeItem) {
			EntityNodeItem item = (EntityNodeItem)entity;
			
			if(item.getItemEntity() != null && item.getItemEntity().getEntityItem() != null) {
				GL11.glPushMatrix();
				GL11.glTranslated(d0, d1 + 0.3, d2);
				GL11.glEnable(GL12.GL_RESCALE_NORMAL);
				
				GL11.glScalef(1.5F, 1.5F, 1.5F);
				GL11.glPushMatrix();
				GL11.glScalef(0.5F, 0.5F, 0.5F);
				if(item.colourCode != -1) {
					int colour = ItemDye.field_150922_c[item.colourCode];
					float r = (float)(colour >> 16 & 255) / 255.0F;
					float g = (float)(colour >> 8 & 255) / 255.0F;
					float b = (float)(colour & 255) / 255.0F;
					
					this.circle.setColour(Utils.encodeColour(r, g, b, 0.95F), Utils.encodeColour(r, g, b, 0.95F));
					this.circle2.setColour(Utils.encodeColour(r, g, b, 0.6F), Utils.encodeColour(r, g, b, 0.6F));
					this.circle3.setColour(Utils.encodeColour(r, g, b, 0.4F), Utils.encodeColour(r, g, b, 0.4F));
				} else {
					this.circle.setColour(Utils.encodeColour(153F / 255F, 0F / 255F, 255F / 255F, 0.95F), Utils.encodeColour(153F / 255F, 0F / 255F, 255F / 255F, 0.95F));
					this.circle2.setColour(Utils.encodeColour(153F / 255F, 0F / 255F, 255F / 255F, 0.6F), Utils.encodeColour(153F / 255F, 0F / 255F, 255F / 255F, 0.6F));
					this.circle3.setColour(Utils.encodeColour(153F / 255F, 0F / 255F, 255F / 255F, 0.4F), Utils.encodeColour(153F / 255F, 0F / 255F, 255F / 255F, 0.4F));
				}
				
				this.circle4.setColour(Utils.encodeColour(1F, 1F, 1F, 0.0F), Utils.encodeColour(1F, 1F, 1F, 0.95F));
				
				float rad = (float)(item.rotation % 20) / 20F;
				float pulse = 0F;
				
				if(item.rotation % 10 < 5)
					pulse = (float)(item.rotation % 10) / 50F;
				else
					pulse = (float)(10 - (item.rotation % 10)) / 50F;
				
				if(item.rotation <= 10 && item.rotation != 0) {
					float scale = item.rotation * 1F / 10F;
					GL11.glScalef(scale, scale, scale);
				}
				
				if(item.isDeInit && item.deInitTicks != 0) {
					float scale = 1F / item.deInitTicks;
					GL11.glScalef(scale, scale, scale);
				}
				
				EntityPlayer player = FMLClientHandler.instance().getClient().thePlayer;
				Vec3 vec = player.getLookVec();
				
				GL11.glPushMatrix();
				GL11.glTranslated(vec.xCoord * 0.2, vec.yCoord * 0.2, vec.zCoord * 0.2);
				GL11.glScalef(0.5F, 0.5F, 0.5F);
				RenderUtils.facePlayer();
				this.circle.draw(0.6F + pulse, 0, 0);
				this.circle2.draw(0.7F + pulse, 0, 0);
				this.circle3.draw(0.8F + pulse, 0, 0);
				this.circle4.draw(0.5F + pulse, 0, 0);
				GL11.glPopMatrix();
				
				GL11.glScalef(0.6F, 0.6F, 0.6F);
				itemRender.doRender(item.getItemEntity(), 0, 0, 0, 0, 0F);
				
				GL11.glPopMatrix();
				
				GL11.glDisable(GL12.GL_RESCALE_NORMAL);
				GL11.glPopMatrix();
			}
		}
	}
	
	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return null;
	}
}
