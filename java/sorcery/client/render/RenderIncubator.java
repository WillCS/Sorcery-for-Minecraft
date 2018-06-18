package sorcery.client.render;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import sorcery.entities.EntityIncubatorEgg;
import sorcery.tileentities.TileEntityIncubator;

public class RenderIncubator extends TileEntitySpecialRenderer {
	public void renderTileEntityIncubator(TileEntityIncubator tile, double par2, double par4, double par6, float par8) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float)par2 + 0.5F, (float)par4, (float)par6 + 0.5F);
		// EntityItem item = new EntityItem(tile.worldObj, (int)par2, (int)par4,
		// (int)par6, (new ItemStack(tile.getEggFromID(tile.getMobID()), 1)));
		EntityIncubatorEgg item = new EntityIncubatorEgg(tile.getWorldObj(), tile.getMobID());
		if(tile.getMobID() != 0) {
			float var10 = 0.4375F;
			/*
			 * GL11.glTranslatef(0.0F, 0.4F, 0.0F);
			 * GL11.glRotatef((float)(tile.yaw2 + (tile.yaw - tile.yaw2) *
			 * (double)par8) * 10.0F, 0.0F, 1.0F, 0.0F); GL11.glRotatef(-30.0F,
			 * 1.0F, 0.0F, 0.0F); GL11.glTranslatef(0.0F, -0.4F, 0.0F);
			 * GL11.glScalef(var10, var10, var10);
			 */
			// item.setPosition(par2, par4, par6);
			RenderManager.instance.renderEntityWithPosYaw(item, 0.0D, 0.4D, 0.0D, 0.0F, par8);
		}
		
		GL11.glPopMatrix();
	}
	
	public void renderTileEntityAt(TileEntity par1TileEntity, double par2, double par4, double par6, float par8) {
		this.renderTileEntityIncubator((TileEntityIncubator)par1TileEntity, par2, par4, par6, par8);
	}
}
